package com.example.restapi.service;

import com.example.restapi.dto.SubscriptionCreateDto;
import com.example.restapi.dto.SubscriptionDto;
import com.example.restapi.dto.TopSubscriptionDto;

import java.util.List;

public interface SubscriptionService {

    SubscriptionDto addSubscription(Long userId, SubscriptionCreateDto dto);
    List<SubscriptionDto> getUserSubscriptions(Long userId);
    void deleteSubscription(Long userId, Long subscriptionId);
    List<TopSubscriptionDto> getTopSubscriptions();
}
