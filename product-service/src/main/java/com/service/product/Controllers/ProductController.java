package com.service.product.Controllers;

import com.service.product.Dtos.ProductDto;
import com.service.product.Service.ProductService.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<ProductDto> add(@RequestBody ProductDto productDto){
        return new ResponseEntity<>(productService.add(productDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> all(){
        return new ResponseEntity<>(productService.all(),HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ProductDto> update(@RequestBody ProductDto productDto){
        return new ResponseEntity<>(productService.update(productDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable String id){
        return new ResponseEntity<>(productService.productById(id),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id){
        return new ResponseEntity<>(productService.delete(id),HttpStatus.OK);
    }
}
