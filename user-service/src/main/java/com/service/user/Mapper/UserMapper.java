package com.service.user.Mapper;

import com.service.user.Dto.ManagerDto;
import com.service.user.Dto.UserRequest;
import com.service.user.Dto.UserResponse;
import com.service.user.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper mapper = Mappers.getMapper(UserMapper.class);
    UserResponse toDto( User user);
    User toEntity(UserRequest userRequest);
    List<UserResponse> toDto(List<User> userList);
    ManagerDto toManagerDto(User user);

}
