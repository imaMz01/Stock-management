package com.service.user.Service;

import com.service.user.Dto.*;

import java.util.List;

public interface UserService {

    UserResponse add(UserRequest userRequest);
    List<UserResponse> all();
    UserResponse update(UserRequest userRequest);
    UserResponse userById(String id);
    String delete(String id);
    JwtAuthenticationResponse signIn(SignInRequest request) throws Exception;
    UserResponse getCurrentUser();
}
