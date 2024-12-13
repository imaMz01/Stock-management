package com.service.product.Dtos;

import com.service.product.Entities.StockProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class StockDto {

    private String id;
    private String storeName;
    private List<StockProductDto> stockProducts;
    private UserResponse manager;
}
