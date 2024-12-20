package com.service.product.exceptions;

public class StockNotFoundException extends RuntimeException {

    public StockNotFoundException(String sku) {
        super(ExceptionMessages.STOCK_NOT_FOUND.getMessage(sku));
    }
}
