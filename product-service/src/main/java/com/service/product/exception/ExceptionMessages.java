package com.service.product.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ExceptionMessages {

    PRODUCT_NOT_FOUND("Product not found with sku code %s "),
    STOCK_NOT_FOUND("Stock not found with id %s "),
    FAILED_TO_FIND_SERVICE("Oups something wrong, try later");

    private final String message;


    public String getMessage() {
        return message;
    }

    public String getMessage(Object... args) {
        return String.format(message, args);
    }
}