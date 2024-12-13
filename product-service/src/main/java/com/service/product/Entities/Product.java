package com.service.product.Entities;

import com.service.product.Util.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Product extends AbstractEntity {

    private String name;
    private String category;
    private double price;

    @OneToMany(mappedBy = "product")
    private List<StockProduct> ProductStocks =new ArrayList<>();

}
