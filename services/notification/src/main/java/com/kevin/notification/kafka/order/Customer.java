package com.kevin.notification.kafka.order;

import org.springframework.validation.annotation.Validated;

public record Customer(
        String id,
        String firstname,
        String lastname,
        String email
) {
}
