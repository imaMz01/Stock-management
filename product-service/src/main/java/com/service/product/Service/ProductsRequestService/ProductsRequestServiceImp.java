package com.service.product.Service.ProductsRequestService;

import com.service.product.Dtos.ProductsRequestDto;
import com.service.product.Dtos.RequestDecisionDto;
import com.service.product.Entities.ProductsRequest;
import com.service.product.Enums.Status;
import com.service.product.FeignClient.UserFeign;
import com.service.product.Mappers.ProductsRequestMapper;
import com.service.product.Repositories.ProductsRequestRepository;
import com.service.product.Service.ProductService.ProductService;
import com.service.product.Service.StockService.StockService;
import com.service.product.exceptions.ProductsRequestNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductsRequestServiceImp implements ProductsRequestService{


    private final ProductService productService;
    private final ProductsRequestRepository repository;
    private final ProductsRequestMapper mapper;
    private final UserFeign userFeign;
    private final StockService stockService;
    private final StreamBridge streamBridge;

    @Override
    public ProductsRequestDto add(ProductsRequestDto productsRequestDto) {
        ProductsRequest productsRequest = mapper.toEntity(productsRequestDto);
        productsRequest.setId(UUID.randomUUID().toString());
        productsRequest.setStatus(Status.PENDING);
        productsRequest.setIdManager(productsRequestDto.getRequestingManager().getId());
        productsRequest.setIdStock(stockService.stockByIdManager(productsRequestDto.getReceivingManager().getId()).getId());
        ProductsRequestDto saved = mapper.toDto(repository.save(productsRequest));
        return getDto(saved, productsRequest);
    }

    @Override
    public List<ProductsRequestDto> all() {
        return repository.findAll().stream().map(
                productsRequest -> getDto(mapper.toDto(productsRequest),productsRequest)
        ).toList();
    }

    private ProductsRequestDto getDto(ProductsRequestDto saved, ProductsRequest productsRequest) {
        saved.setProductName(productService.getById(saved.getIdProduct()).getName());
        saved.setRequestingManager(userFeign.userById(productsRequest.getIdManager()).getBody());
        saved.setReceivingManager(stockService.stockById(productsRequest.getIdStock()).getManager());
        return saved;
    }

    @Override
    public ProductsRequestDto approveRequest(String id) {
        ProductsRequest productsRequest = getById(id);
        productsRequest.setStatus(Status.APPROVED);
        ProductsRequest saved = repository.save(productsRequest);
        return getDto(mapper.toDto(saved),saved);
    }

    @Override
    @Transactional
    public String rejectRequest(String id,String from) {
        ProductsRequest productsRequest = getById(id);
        productsRequest.setStatus(Status.REJECTED);
        ProductsRequest saved = repository.save(productsRequest);
        streamBridge.send("productsRequestDecisionEmail-topic", RequestDecisionDto.builder()
                    .email(from)
                    .status(Status.REJECTED.toString())
                    .build());
        return "The request has been rejeted successfully";
    }

    private ProductsRequest getById(String id) {
        return repository.findById(id).orElseThrow(()-> new ProductsRequestNotFoundException("id "+id));
    }

    @Override
    public ProductsRequestDto requestById(String id) {
        ProductsRequest productsRequest = getById(id);
        return getDto(mapper.toDto(productsRequest),productsRequest);
    }
}
