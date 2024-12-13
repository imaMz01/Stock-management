package com.service.product.Mappers;

import com.service.product.Dtos.StockProductDto;
import com.service.product.Entities.StockProduct;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",uses = ProductMapper.class)
public interface StockProductMapper {

    StockProductDto toDto(StockProduct stockProduct);
    StockProduct toEntity(StockProductDto stockProductDto);
    List<StockProductDto> toDto(List<StockProduct> stockProducts);
}
