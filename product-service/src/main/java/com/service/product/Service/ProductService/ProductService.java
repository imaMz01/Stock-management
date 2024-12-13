package com.service.product.Service.ProductService;

import com.service.product.Dtos.ProductDto;
import com.service.product.Entities.Product;

import java.util.List;

public interface ProductService {

    ProductDto add(ProductDto productDto);
    List<ProductDto> all();
    ProductDto update(ProductDto productDto);
    String delete(String id);
    ProductDto productById(String id);
    public Product getById(String id);
    boolean checkProduct(String id);
}
