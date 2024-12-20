package com.service.product.Service.RequestHelper;

import com.service.product.Dtos.ProductStocksDto;
import com.service.product.Dtos.ProductsRequestDto;
import com.service.product.Dtos.RequestDto;
import com.service.product.Dtos.UserResponse;
import com.service.product.Entities.Product;
import com.service.product.Entities.Stock;
import com.service.product.Entities.StockProduct;
import com.service.product.FeignClient.UserFeign;
import com.service.product.Mappers.StockMapper;
import com.service.product.Service.ProductService.ProductService;
import com.service.product.Service.ProductsRequestService.ProductsRequestService;
import com.service.product.Service.StockService.StockService;
import com.service.product.exceptions.ProductNotFoundException;
import com.service.product.exceptions.StockInsufficientException;
import com.service.product.exceptions.StockSufficientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class RequestHelperServiceImp implements RequestHelperService{

    private final StockService stockService;
    private final UserFeign userFeign;
    private final StreamBridge streamBridge;
    private final StockMapper stockMapper;
    private final ProductsRequestService productsRequestService;
    private final ProductService productService;


    @Override
    @Transactional
    public String sendProductRequest(String idProduct, String idStock, long quantity) {
        UserResponse manager = userFeign.getCurrentUser().getBody();
        if(manager == null)
            return "Error in current user";
        Stock myStock = stockService.stockByIdManager(manager.getId());
        StockProduct myStockProduct = myStock.getStockProducts()
                .stream()
                .filter(sp ->sp.getProduct() != null && sp.getProduct().getId().equals(idProduct))
                .findFirst()
                .orElseThrow(()->new ProductNotFoundException("id "+idProduct));
        if(myStockProduct.getQuantity()>30)
            throw new StockSufficientException(idProduct);
        Stock managerStock = stockService.getById(idStock);
        StockProduct managerStockProduct = managerStock.getStockProducts()
                .stream()
                .filter(  sp ->sp.getProduct() != null &&  sp.getProduct().getId().equals(idProduct))
                .findFirst()
                .orElseThrow(()->new ProductNotFoundException("id "+idProduct));
        if(managerStockProduct.getQuantity()-quantity<=30)
            throw new StockInsufficientException();
        ProductsRequestDto productsRequestDto = productsRequestService.add(
                new ProductsRequestDto("",idProduct,
                        productService.productById(idProduct).getName()
                        ,quantity,manager,
                        new UserResponse(managerStock.getIdManager(),"","","")
                        ,null));
        streamBridge.send("productsRequestEmail-topic",
                new RequestDto(productsRequestDto.getId(),managerStock.getEmailManager(),manager.getEmail(),myStockProduct.getProduct().getName(),
                        quantity));
        return "Request sent successfully";
    }
}
