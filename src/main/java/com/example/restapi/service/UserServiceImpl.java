package com.example.restapi.service;

import com.example.restapi.dto.*;
import com.example.restapi.entity.User;
import com.example.restapi.exception.NotFoundException;
import com.example.restapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto createUser(UserCreateDto dto) {
        log.info("Creating user with email: {}", dto.getEmail());
        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .build();
        return toDto(userRepository.save(user));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getUser(Long id) {
        log.info("Retrieving user with id: {}", id);
        return toDto(userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found")));
    }

    @Override
    public UserDto updateUser(Long id, UserCreateDto dto) {
        log.info("Updating user with id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        return toDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        log.warn("Deleting user with id: {}", id);
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }

    private UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .subscriptions(user.getSubscriptions() != null
                        ? user.getSubscriptions().stream()
                        .map(s -> SubscriptionDto.builder()
                                .id(s.getId())
                                .serviceName(s.getServiceName())
                                .build())
                        .collect(Collectors.toList())
                        : null)
                .build();
    }
}