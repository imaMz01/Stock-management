package com.service.product.Entities;

import com.service.product.Util.StockProductId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class StockProduct {

    @EmbeddedId
    private StockProductId id;
    private long quantity;

    @ManyToOne
    @MapsId("idStock")
    private Stock stock;

    @ManyToOne
    @MapsId("idProduct")
    private Product product;
}
