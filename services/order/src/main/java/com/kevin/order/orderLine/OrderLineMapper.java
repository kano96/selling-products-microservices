package com.kevin.order.orderLine;

import com.kevin.order.order.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderLineMapper {

  public OrderLine toOrderLine(OrderLineRequest orderLineRequest) {
    return OrderLine.builder()
        .id(orderLineRequest.id())
        .quantity(orderLineRequest.quantity())
        .order(
            Order.builder()
                .id(orderLineRequest.orderId())
                .build()
        )
        .productId(orderLineRequest.productId())
        .build();
  }

    public OrderLineResponse toOrderLineResponse(OrderLine orderLine) {
        return new OrderLineResponse(orderLine.getId(),  orderLine.getQuantity());
  }
}
