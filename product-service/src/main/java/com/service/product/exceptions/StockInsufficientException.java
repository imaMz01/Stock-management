package com.service.product.exceptions;

public class StockInsufficientException extends RuntimeException{
    public StockInsufficientException(){
        super(ExceptionMessages.STOCK_INSUFFICIENT.getMessage());
    }
}
