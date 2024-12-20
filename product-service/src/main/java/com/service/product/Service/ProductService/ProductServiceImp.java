package com.service.product.Service.ProductService;

import com.service.product.Dtos.ProductDto;
import com.service.product.Entities.Product;
import com.service.product.Mappers.ProductMapper;
import com.service.product.Repositories.ProductRepository;
import com.service.product.exceptions.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService{

    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    @Override
    public ProductDto add(ProductDto productDto) {
        Product product = mapper.toEntity(productDto);
        product.setId(UUID.randomUUID().toString());
        return mapper.toDto(productRepository.save(product));
    }

    @Override
    public List<ProductDto> all() {
        return mapper.toDto(productRepository.findAll());
    }

    public Product getById(String id){
        return productRepository.findById(id).orElseThrow(()->
                new ProductNotFoundException("sku code "+id));
    }

    @Override
    public Product getByName(String name) {
        return productRepository.findByName(name).orElseThrow(()-> new ProductNotFoundException("with name "+name));
    }

    @Override
    public boolean checkProduct(String id) {
        return productRepository.findById(id).isPresent();
    }

    @Override
    public ProductDto update(ProductDto productDto) {
        Product product = getById(productDto.getId());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setCategory(productDto.getCategory());
        return mapper.toDto(productRepository.save(product));
    }

    @Override
    public String delete(String id) {
        productRepository.delete(getById(id));
        return "Product was deleted successfully";
    }

    @Override
    public ProductDto productById(String id) {
        return mapper.toDto(getById(id));
    }
}
