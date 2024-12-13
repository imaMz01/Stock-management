package com.service.product.Controllers;

import com.service.product.Dtos.ProductStocksDto;
import com.service.product.Dtos.StockDto;
import com.service.product.Dtos.StockProductDto;
import com.service.product.Service.StockService.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
public class StockController {

    private final StockService stockService;

    @GetMapping
    public ResponseEntity<List<StockDto>> all(){
        return new ResponseEntity<>(stockService.stocks(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StockDto> add(@RequestBody StockDto stockDto){
        return new ResponseEntity<>(stockService.add(stockDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockDto> getById(@PathVariable String id){
        return new ResponseEntity<>(stockService.stockById(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<StockDto> update(@RequestBody StockDto stockDto){
        return new ResponseEntity<>(stockService.update(stockDto), HttpStatus.OK);
    }

    @GetMapping("/productStocks/{sku}")
    public ResponseEntity<List<ProductStocksDto>> productStocks(@PathVariable String sku){
        return new ResponseEntity<>(stockService.productStocks(sku), HttpStatus.OK);
    }

    @PostMapping("/addProductToStock/{id}")
    public ResponseEntity<StockDto> addProductToStock(@PathVariable String id, @RequestBody StockProductDto stockProductDto){
        return new ResponseEntity<>(stockService.addProductToStock(id,stockProductDto),HttpStatus.OK);
    }

}
