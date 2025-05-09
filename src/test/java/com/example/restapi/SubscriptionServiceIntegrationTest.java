package com.example.restapi;

import com.example.restapi.dto.*;
import com.example.restapi.service.SubscriptionService;
import com.example.restapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest
public class SubscriptionServiceIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private UserService userService;

    @Autowired
    private SubscriptionService subscriptionService;

    @Test
    void addSubscription_createsSubscriptionForUser() {
        UserDto user = userService.createUser(new UserCreateDto("sub@example.com", "Sub"));
        SubscriptionDto subscription = subscriptionService.addSubscription(
                user.getId(),
                new SubscriptionCreateDto("Netflix")
        );

        assertNotNull(subscription.getId());
        assertEquals("Netflix", subscription.getServiceName());
    }

    @Test
    void getTopSubscriptions_returnsMostPopular() {
        UserDto user1 = userService.createUser(new UserCreateDto("u1@example.com", "U1"));
        UserDto user2 = userService.createUser(new UserCreateDto("u2@example.com", "U2"));

        subscriptionService.addSubscription(user1.getId(), new SubscriptionCreateDto("YouTube"));
        subscriptionService.addSubscription(user2.getId(), new SubscriptionCreateDto("YouTube"));
        subscriptionService.addSubscription(user2.getId(), new SubscriptionCreateDto("Netflix"));

        List<TopSubscriptionDto> top = subscriptionService.getTopSubscriptions();

        assertFalse(top.isEmpty());
        assertEquals("YouTube", top.get(0).serviceName());
        assertEquals(2L, top.get(0).count());
    }
}
