package com.service.user.Controller;

import com.service.user.Dto.*;
import com.service.user.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/addUser")
    public ResponseEntity<UserResponse> add(@Valid @RequestBody UserRequest userRequest){
        return new ResponseEntity<>(userService.add(userRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> all(){
        return new ResponseEntity<>(userService.all(), HttpStatus.OK);
    }

    @PutMapping("/updateUser")
    public ResponseEntity<UserResponse> update(@RequestBody UserRequest userRequest){
        return new ResponseEntity<>(userService.update(userRequest),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> userById(@PathVariable String id){
        return new ResponseEntity<>(userService.userById(id),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id){
        return new ResponseEntity<>(userService.delete(id),HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationResponse> login(@RequestBody SignInRequest signInRequest) throws Exception {
        return new ResponseEntity<>(userService.signIn(signInRequest),HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(){
        return new ResponseEntity<>(userService.getCurrentUser(),HttpStatus.OK);
    }
}
