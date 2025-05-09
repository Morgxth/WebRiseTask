package com.example.restapi.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionCreateDto {
    private String serviceName;
}