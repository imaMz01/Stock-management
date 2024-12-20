package com.service.product.exceptions;

public class StockAlreadyExist extends RuntimeException {
    public StockAlreadyExist() {
        super(ExceptionMessages.STOCK_ALREADY_EXIST.getMessage());
    }
}