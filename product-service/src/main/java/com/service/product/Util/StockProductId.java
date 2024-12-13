package com.service.product.Util;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StockProductId {

    @Column(name = "idProduct")
    private String idProduct;
    @Column(name = "idStock")
    private String idStock;
}
