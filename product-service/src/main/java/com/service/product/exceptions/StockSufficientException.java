package com.service.product.exceptions;

public class StockSufficientException extends RuntimeException{
    public StockSufficientException(String id){
        super(ExceptionMessages.STOCK_SUFFICIENT.getMessage(id));
    }
}
