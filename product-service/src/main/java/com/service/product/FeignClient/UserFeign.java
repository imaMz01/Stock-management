package com.service.product.FeignClient;

import com.service.product.Dtos.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", fallbackFactory = UserFallBack.class)
public interface UserFeign {

    @GetMapping("/users/me")
    public ResponseEntity<UserResponse> getCurrentUser();

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> userById(@PathVariable String id);
}
