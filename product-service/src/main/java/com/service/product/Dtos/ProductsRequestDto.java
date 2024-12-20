package com.service.product.Dtos;

import com.service.product.Enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductsRequestDto {

    private String id;
    private String idProduct;
    private String productName;
    private long quantity;
    private UserResponse requestingManager;
    private UserResponse receivingManager;
    private Status status;

}
