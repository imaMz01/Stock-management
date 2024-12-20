package com.service.product.Mappers;

import com.service.product.Dtos.ProductsRequestDto;
import com.service.product.Entities.ProductsRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductsRequestMapper {

    ProductsRequestDto toDto(ProductsRequest user);
    ProductsRequest toEntity(ProductsRequestDto productDto);
    List<ProductsRequestDto> toDto(List<ProductsRequest> products);

}
