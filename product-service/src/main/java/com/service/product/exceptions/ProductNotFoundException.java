package com.service.product.exceptions;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String sku) {
        super(ExceptionMessages.PRODUCT_NOT_FOUND.getMessage(sku));
    }
}
