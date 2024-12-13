package com.service.product.Service.StockService;

import com.service.product.Dtos.ProductStocksDto;
import com.service.product.Dtos.StockDto;
import com.service.product.Dtos.StockProductDto;
import com.service.product.Entities.StockProduct;

import java.util.List;

public interface StockService {

    StockDto add(StockDto stockDto);
    StockDto update(StockDto stockDto);
    StockDto stockById(String id);
    List<StockDto> stocks();
    List<ProductStocksDto> productStocks(String sku);
    StockDto addProductToStock(String id, StockProductDto stockProductDto);
    void verifyStockAndSendNotification();
}
