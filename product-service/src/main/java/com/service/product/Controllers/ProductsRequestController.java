package com.service.product.Controllers;


import com.service.product.Dtos.ProductsRequestDto;
import com.service.product.Service.ProductsRequestService.ProductsRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v3")
public class ProductsRequestController {

    private final ProductsRequestService service;

    @PostMapping("/add")
    public ResponseEntity<ProductsRequestDto> add(@RequestBody ProductsRequestDto productsRequest){
        return new ResponseEntity<>(service.add(productsRequest), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductsRequestDto>> all(){
        return new ResponseEntity<>(service.all(), HttpStatus.OK);
    }

    @GetMapping("/reject/")
    public ResponseEntity<String> reject(@RequestParam String id,@RequestParam String from){
        System.out.println("id "+id+" from "+from);
        return new ResponseEntity<>(service.rejectRequest(id,from),HttpStatus.OK);
    }
}
