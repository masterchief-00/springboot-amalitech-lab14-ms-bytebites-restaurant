package com.kwizera.orderservice.controllers;

import com.kwizera.orderservice.domain.dtos.CreateOrderDTO;
import com.kwizera.orderservice.domain.dtos.OrderDTO;
import com.kwizera.orderservice.domain.entities.Order;
import com.kwizera.orderservice.service.OrderServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {
    private final OrderServices orderServices;

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getOrders(
            @RequestHeader("X-User-Id") String userId,
            @RequestHeader("Authorization") String authHeader
    ) {
        List<OrderDTO> orders = orderServices.getUserOrders(Long.parseLong(userId), authHeader);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(
            @RequestBody CreateOrderDTO orderDetails,
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
