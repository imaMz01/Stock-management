package com.service.notification.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RequestDto {

    private String idProductsRequest;
    private String to;
    private String from;
    private String productName;
    private long quantity;
}
