package com.kevin.order.order;

import com.kevin.order.customer.CustomerClient;
import com.kevin.order.exception.BusinessException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;

    public Integer createOrder(@Valid OrderRequest request) {
        // Check the costumer --> Open Feign
        var customer = customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order. Customer not found with id: " + request.customerId()));

        // Purchase the products --> Products microservice


        // Persist Order

        // Persist Order Lines

        // Start Payment Process

        // Send the order confirmation  --> notification-ms (kafka)
    }
}
