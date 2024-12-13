package com.service.product.exception;

public class ProductNotFundException extends RuntimeException {

    public ProductNotFundException(String sku) {
        super(ExceptionMessages.PRODUCT_NOT_FOUND.getMessage(sku));
    }
}
