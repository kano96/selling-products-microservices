package com.kevin.order.product;

import java.math.BigDecimal;

public record PurchaseResponse(
        Long productId,
        String name,
        String description,
        BigDecimal price,
        double quantity
) {
}
