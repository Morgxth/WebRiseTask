package com.example.restapi.service;

import com.example.restapi.dto.SubscriptionCreateDto;
import com.example.restapi.dto.SubscriptionDto;
import com.example.restapi.dto.TopSubscriptionDto;
import com.example.restapi.entity.Subscription;
import com.example.restapi.entity.User;
import com.example.restapi.exception.NotFoundException;
import com.example.restapi.repository.SubscriptionRepository;
import com.example.restapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    @Override
    public SubscriptionDto addSubscription(Long userId, SubscriptionCreateDto dto) {
        log.info("Adding subscription '{}' to user {}", dto.getServiceName(), userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Subscription subscription = Subscription.builder()
                .serviceName(dto.getServiceName())
                .user(user)
                .build();

        return toDto(subscriptionRepository.save(subscription));
    }

    @Override
    public List<SubscriptionDto> getUserSubscriptions(Long userId) {
        log.info("Fetching subscriptions for user {}", userId);
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User not found");
        }

        return subscriptionRepository.findByUserId(userId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteSubscription(Long userId, Long subscriptionId) {
        log.info("Deleting subscription '{}' to user {}", subscriptionId, userId);
        Subscription sub = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new NotFoundException("Subscription not found"));

        if (!Objects.equals(sub.getUser().getId(), userId)) {
            throw new NotFoundException("Subscription does not belong to this user");
        }

        subscriptionRepository.delete(sub);
    }

    public List<TopSubscriptionDto> getTopSubscriptions() {
        log.info("Fetching top subscriptions");
        return subscriptionRepository.findTopSubscriptions().stream()
                .map(proj -> new TopSubscriptionDto(proj.getServiceName(), proj.getCount()))
                .toList();
    }

    private SubscriptionDto toDto(Subscription s) {
        return SubscriptionDto.builder()
                .id(s.getId())
                .serviceName(s.getServiceName())
                .build();
    }
}