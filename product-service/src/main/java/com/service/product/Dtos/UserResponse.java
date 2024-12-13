package com.service.product.Dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String email;

}
