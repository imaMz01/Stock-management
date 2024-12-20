package com.service.product.Dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestDecisionDto {

    private String status;
    private String email;
}
