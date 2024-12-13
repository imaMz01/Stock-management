package com.service.user.Dto;

import com.service.user.Annotations.Password;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserRequest {

    @NotNull(message = "Name should be not null")
    @NotEmpty(message = "Name should be not empty")
    private String firstName;
    @NotNull(message = "Name should be not null")
    @NotEmpty(message = "Name should be not empty")
    private String lastName;
    @NotNull(message = "Name should be not null")
    @NotEmpty(message = "Name should be not empty")
    private String email;
    @Password
    private String password;
}
