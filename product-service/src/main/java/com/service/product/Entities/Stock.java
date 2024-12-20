package com.service.product.Entities;

import com.service.product.Util.AbstractEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Stock extends AbstractEntity {


    private String storeName;

    @OneToMany(mappedBy = "stock",cascade = CascadeType.MERGE)
    private List<StockProduct> stockProducts =new ArrayList<>();
    private String idManager;
    private String emailManager;
}
