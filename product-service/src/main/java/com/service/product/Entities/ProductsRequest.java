package com.service.product.Entities;

import com.service.product.Enums.Status;
import com.service.product.Util.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProductsRequest extends AbstractEntity {

    private String idProduct;
    private long quantity;
    private String idManager;
    private String idStock;
    @Enumerated(EnumType.STRING)
    private Status status;
}
