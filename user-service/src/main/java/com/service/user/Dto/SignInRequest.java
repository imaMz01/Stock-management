package com.service.user.Dto;

import com.service.user.Annotations.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest {

    @Email(message = "The email is not valid.")
    private String email;

    @NotBlank(message = "The password is required.")
    @Password
    private String password;
}