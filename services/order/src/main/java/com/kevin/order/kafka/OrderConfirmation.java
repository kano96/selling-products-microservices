package com.kevin.order.kafka;

import com.kevin.order.customer.CustomerResponse;
import com.kevin.order.order.PaymentMethod;
import com.kevin.order.product.PurchaseResponse;
import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
    String orderReference,
    BigDecimal totalAmount,
    PaymentMethod paymentMethod,
    CustomerResponse customer,
    List<PurchaseResponse> products
) {

}
