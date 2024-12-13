package com.service.product.exception;

public class StockNotFundException extends RuntimeException {

    public StockNotFundException(String sku) {
        super(ExceptionMessages.STOCK_NOT_FOUND.getMessage(sku));
    }
}
