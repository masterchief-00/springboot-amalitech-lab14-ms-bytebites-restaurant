package com.kwizera.orderservice.service;

import com.kwizera.orderservice.domain.dtos.CreateOrderDTO;
import com.kwizera.orderservice.domain.dtos.OrderDTO;
import com.kwizera.orderservice.domain.entities.Order;
import com.kwizera.orderservice.domain.enums.OrderStatus;

import java.util.List;

public interface OrderServices {
    OrderDTO createOrder(CreateOrderDTO orderDetails, Long clientId, String token);

    List<OrderDTO> getUserOrders(Long userId, String token);

    Order updateOrderStatus(Long orderId, OrderStatus orderStatus);
}
