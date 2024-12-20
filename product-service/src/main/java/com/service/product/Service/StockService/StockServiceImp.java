package com.service.product.Service.StockService;

import com.service.product.Dtos.*;
import com.service.product.Entities.Product;
import com.service.product.Entities.Stock;
import com.service.product.Entities.StockProduct;
import com.service.product.Util.StockProductId;
import com.service.product.FeignClient.UserFeign;
import com.service.product.Mappers.StockMapper;
import com.service.product.Mappers.StockProductMapper;
import com.service.product.Repositories.StockRepository;
import com.service.product.Service.ProductService.ProductService;
import com.service.product.exceptions.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockServiceImp implements StockService{

    private final StockRepository stockRepository;
    private final StockMapper mapper;
    private final ProductService productService;
    private final StockProductMapper stockProductMapper;
    private final UserFeign userFeign;
    private final StreamBridge streamBridge;

    @Override
    @Transactional
    public StockDto add(StockDto stockDto) {
        UserResponse managerDto = userFeign.getCurrentUser().getBody();
        if(managerDto == null)
            return null;
        if (stockRepository.findByIdManager(managerDto.getId()).isPresent())
            throw new StockAlreadyExist();
        Stock stock = mapper.toEntity(stockDto);
        stock.setId(UUID.randomUUID().toString());
        stock.setIdManager(managerDto.getId());
        stock.setEmailManager(managerDto.getEmail());
        stock.setStockProducts(stockDto.getStockProducts()
                .stream()
                .map(
                    element-> new StockProduct(new StockProductId(
                            productService.getById(element.getSku()).getId(),stock.getId()),
                            element.getQuantity(),stock,productService.getById(element.getSku())))
                .toList());
        Stock saved = stockRepository.save(stock);
        StockDto stockDto1 = getStockDto(mapper.toDto(saved), saved);
        stockDto1.setManager(managerDto);
        return stockDto1;
    }

    private static StockDto getStockDto(StockDto stockDto1, Stock saved) {
        stockDto1.setStockProducts(saved.getStockProducts()
                .stream()
                .map(stockProduct -> new StockProductDto(stockProduct.getQuantity(),
                        stockProduct.getProduct().getId()))
                .toList());
        return stockDto1;
    }

    public Stock getById(String id){
        return stockRepository.findById(id).orElseThrow(()->
                new StockNotFoundException("id "+id));
    }
    @Override
    public StockDto update(StockDto stockDto) {
        Stock stock = getById(stockDto.getId());
        stock.setStoreName(stockDto.getStoreName());
        Stock saved = stockRepository.save(stock);
        StockDto stockDto1 = getStockDto(mapper.toDto(saved),saved);
        stockDto1.setManager(userFeign.getCurrentUser().getBody());
        return stockDto1;
    }

    @Override
    public StockDto stockById(String id) {
        Stock stock = getById(id);
        StockDto stockDto = getStockDto(mapper.toDto(stock),stock);
        stockDto.setManager(userFeign.userById(stock.getIdManager()).getBody());
        return stockDto;
    }

    @Override
    public Stock stockByIdManager(String id) {
        return stockRepository.findByIdManager(id).orElseThrow(()-> new StockNotFoundException("id manager "+id));
    }

    @Override
    public List<StockDto> stocks() {
        return stockRepository.findAll()
                .stream()
                .map(stock -> {
                    StockDto stockDto = getStockDto(mapper.toDto(stock), stock);
                    stockDto.setManager(userFeign.userById(stock.getIdManager()).getBody());
                    return stockDto;
                })
                .toList();
    }

    @Override
    public List<ProductStocksDto> productStocks(String sku) {

        if(!productService.checkProduct(sku)) throw new ProductNotFoundException(sku);
        return stockRepository.findAll().stream()
                .map(stock -> stock.getStockProducts().stream()
                                    .filter(stockProduct -> stockProduct.getId().getIdProduct().equals(sku))
                                    .findFirst()
                                    .map(stockProduct -> new ProductStocksDto(stock.getStoreName(),stockProduct.getQuantity()))
                                    .orElse(null))
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public StockDto addProductToStock(String id,StockProductDto stockProductDto) {
        Stock stock = getById(id);
        Product product = productService.getById(stockProductDto.getSku());
        StockProduct stockProduct = stock.getStockProducts()
                .stream()
                .filter(sp -> sp.getProduct().equals(product))
                .findFirst()
                .orElse(null);
        if (stockProduct == null){
            stockProduct = stockProductMapper.toEntity(stockProductDto);
            stockProduct.setId(new StockProductId(stockProductDto.getSku(),id));
            stockProduct.setStock(stock);
            stockProduct.setProduct(product);
            stock.getStockProducts().add(stockProduct);
        }else{
            stockProduct.setQuantity(stockProduct.getQuantity()+stockProductDto.getQuantity());
        }
        Stock saved = stockRepository.save(stock);
        StockDto stockDto1 = getStockDto(mapper.toDto(saved),saved);
        stockDto1.setManager(userFeign.getCurrentUser().getBody());
        return stockDto1;
    }

    @Override
    @Scheduled(fixedRate = 43200000)
    @Transactional
    public void verifyStockAndSendNotification() {
        stockRepository.findAll().forEach(
                stock -> stock.getStockProducts().stream()
                        .filter(stockProduct -> stockProduct.getQuantity()<=10)
                        .forEach(stockProduct -> {
                                        streamBridge.send("stockVerificationEmail-topic",
                                                new NotificationDto(
                                                        stock.getEmailManager(),
                                                        stockProduct.getProduct().getName(),
                                                        stockProduct.getQuantity()
                                                ));
                                        log.info("send email {}",stock.getEmailManager());
                                }
                )
        );
    }


}
