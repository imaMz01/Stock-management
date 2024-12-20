package com.service.product.Service.ProductsRequestService;

import com.service.product.Dtos.ProductsRequestDto;

import java.util.List;

public interface ProductsRequestService {
    ProductsRequestDto add(ProductsRequestDto productsRequestDto);
    List<ProductsRequestDto> all();
    ProductsRequestDto approveRequest(String id);
    String rejectRequest(String id, String from);
    ProductsRequestDto requestById(String id);
}
