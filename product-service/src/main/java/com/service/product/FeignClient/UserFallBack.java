package com.service.product.FeignClient;

import com.service.product.Dtos.UserResponse;
import com.service.product.exception.FailedToFindService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeoutException;

@Component
@Slf4j
public class UserFallBack implements FallbackFactory<UserFeign> {
    @Override
    public UserFeign create(Throwable cause) {
        return new UserFeign() {
            @Override
            public ResponseEntity<UserResponse> getCurrentUser() {
                if(cause instanceof FeignException.ServiceUnavailable ||
                        cause instanceof TimeoutException)
                    throw new FailedToFindService();
                throw new RuntimeException(cause);
            }

            @Override
            public ResponseEntity<UserResponse> userById(String id) {
                if(cause instanceof FeignException.ServiceUnavailable ||
                        cause instanceof TimeoutException)
                    throw new FailedToFindService();
                throw new RuntimeException(cause);
            }
        };
    }
}
