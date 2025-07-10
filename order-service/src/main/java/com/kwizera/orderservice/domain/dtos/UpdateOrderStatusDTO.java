package com.kwizera.orderservice.domain.dtos;

import com.kwizera.orderservice.domain.enums.OrderStatus;

public record UpdateOrderStatusDTO(
        OrderStatus newStatus
) {
}
