package com.service.user.Service;

import com.service.user.Dto.*;
import com.service.user.Entity.User;
import com.service.user.Mapper.UserMapper;
import com.service.user.Repository.UserRepository;
import com.service.user.Security.JwtTokenProvider;
import com.service.user.exception.InvalidEmailOrPasswordException;
import com.service.user.exception.UserAlreadyExistException;
import com.service.user.exception.UserNotFundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse add(UserRequest userRequest) {
        userRepository.findByEmailIgnoreCase(userRequest.getEmail()).ifPresent(
                user->{
                    throw new UserAlreadyExistException(user.getEmail());
                });
        User user = userMapper.toEntity(userRequest);
        user.setId(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public List<UserResponse> all() {
        return userMapper.toDto(userRepository.findAll());
    }

    @Override
    public UserResponse update(UserRequest userRequest) {
        User user = getUserByEmail(userRequest.getEmail());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserResponse userById(String id) {
        return userMapper.toDto(getUserByd(id));
    }

    @Override
    public String delete(String id) {
        userRepository.delete(getUserByd(id));
        return "user deleted successfully";
    }

    @Override
    public JwtAuthenticationResponse signIn(SignInRequest request) {

        // Find user by email
        User user = userRepository.findByEmailIgnoreCase(request.getEmail())
                .orElseThrow(InvalidEmailOrPasswordException::new);
        // Authenticate using email and password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        log.info("User authenticated");

        log.info("User found: {}", user.getEmail());
        String jwt = jwtTokenProvider.generateAccessToken(user);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user);

        log.info("Access token generated: {}", jwt);
        log.info("Refresh token generated: {}",refreshToken);

        log.info("User return object: {}",JwtAuthenticationResponse.builder()
                .accessToken(jwt)
                .refreshToken(refreshToken)
                .userResponse(userMapper.toDto(user))
                .build());
        return JwtAuthenticationResponse.builder()
                .accessToken(jwt)
                .refreshToken(refreshToken)
                .userResponse(userMapper.toDto(user))
                .build();
    }

    @Override
    public UserResponse getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            Object principal = authentication.getPrincipal();
            if(principal instanceof User){
                return userMapper.toDto((User)principal);
            }
        }
        return null;
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new UserNotFundException("email "+email));
    }

    private User getUserByd(String id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFundException("id "+id));
    }
}
