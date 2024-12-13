package com.service.product.Mappers;

import com.service.product.Dtos.StockDto;
import com.service.product.Entities.Stock;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",uses = StockProductMapper.class)
public interface StockMapper {

    StockDto toDto(Stock stock);
    Stock toEntity(StockDto stockDto);
    List<StockDto> toDto(List<Stock> stocks);
}
