package com.service.product.exceptions;

public class ProductsRequestNotFoundException extends RuntimeException {

    public ProductsRequestNotFoundException(String sku) {
        super(ExceptionMessages.PRODUCTS_REQUEST_NOT_FOUND.getMessage(sku));
    }
}
