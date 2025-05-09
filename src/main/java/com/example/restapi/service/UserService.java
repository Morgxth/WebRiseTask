package com.example.restapi.service;

import com.example.restapi.dto.UserCreateDto;
import com.example.restapi.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(UserCreateDto dto);
    UserDto getUser(Long id);
    UserDto updateUser(Long id, UserCreateDto dto);
    void deleteUser(Long id);
}
