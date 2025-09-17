package com.kevin.order.order;

import com.kevin.order.orderLine.OrderLine;
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
}
