package com.kwizera.orderservice.controllers;

import com.kwizera.orderservice.domain.dtos.CreateOrderDTO;
import com.kwizera.orderservice.domain.dtos.OrderDTO;
import com.kwizera.orderservice.domain.entities.Order;
import com.kwizera.orderservice.service.OrderServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderServices orderServices;

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(
            @RequestBody CreateOrderDTO orderDetails,
            @RequestHeader("X-User-Role") String role,
            @RequestHeader("X-User-Id") String userId,
            @RequestHeader("Authorization") String authHeader
    ) {
        Long clientId = Long.parseLong(userId);
        OrderDTO order = orderServices.createOrder(orderDetails, clientId, authHeader);
        return new ResponseEntity<>(
                order,
                HttpStatus.CREATED
        );
    }
}
