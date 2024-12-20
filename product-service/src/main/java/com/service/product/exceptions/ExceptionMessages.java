package com.service.product.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ExceptionMessages {

    PRODUCT_NOT_FOUND("Product not found with %s ."),
    STOCK_NOT_FOUND("Stock not found with id %s ."),
    FAILED_TO_FIND_SERVICE("Oups something wrong, try later."),
    STOCK_SUFFICIENT("The stock still contains sufficient quantity of the product with %s"),
    STOCK_INSUFFICIENT("Insufficient stock to send the request."),
    PRODUCTS_REQUEST_NOT_FOUND("Product not found with %s"),
    STOCK_ALREADY_EXIST("You have already a stock");
    private final String message;


    public String getMessage() {
        return message;
    }

    public String getMessage(Object... args) {
        return String.format(message, args);
    }
}