package com.example.restapi.repository;

import com.example.restapi.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findByUserId(Long userId);

    @Query("""
    SELECT s.serviceName AS serviceName, COUNT(s.id) AS count
    FROM Subscription s
    GROUP BY s.serviceName
    ORDER BY count DESC
    LIMIT 3
    """)
    List<TopSubscriptionProjection> findTopSubscriptions();
}
