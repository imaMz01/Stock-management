package com.service.product.Mappers;

import com.service.product.Dtos.ProductDto;
import com.service.product.Entities.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",uses = StockProductMapper.class)
public interface ProductMapper {

    ProductDto toDto(Product user);
    Product toEntity(ProductDto productDto);
    List<ProductDto> toDto(List<Product> products);
}
