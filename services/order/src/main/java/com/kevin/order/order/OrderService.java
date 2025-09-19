package com.kevin.order.order;

import com.kevin.order.customer.CustomerClient;
import com.kevin.order.exception.BusinessException;
import com.kevin.order.kafka.OrderConfirmation;
import com.kevin.order.kafka.OrderProducer;
import com.kevin.order.orderLine.OrderLineRequest;
import com.kevin.order.orderLine.OrderLineService;
import com.kevin.order.product.ProductClient;
import com.kevin.order.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;

    public Integer createOrder(@Valid OrderRequest request) {
        // Check the costumer --> Open Feign
        var customer = customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order. Customer not found with id: " + request.customerId()));

        // Purchase the products --> Products-ms (RestTemplate)
        var purchasedProducts = productClient.purchaseProducts(request.products());

        // Persist Order
        var order = repository.save(mapper.toOrder(request));

        // Persist Order Lines
        for(PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                new OrderLineRequest(
                    null,
                    order.getId(),
                    purchaseRequest.productId(),
                    purchaseRequest.quantity()
                )
            );
        }

        // TODO: Start Payment Process

        // Send the order confirmation  --> notification-ms (kafka)
        orderProducer.sendOrderConfirmation(
            new OrderConfirmation(
                request.reference(),
                request.amount(),
                request.paymentMethod(),
                customer,
                purchasedProducts
            )
        );
        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return repository.findAll()
            .stream()
            .map(mapper::fromOrder)
            .toList();
    }

    public OrderResponse findById(Integer id) {
        return repository.findById(id)
            .map(mapper::fromOrder)
            .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));
    }
}
