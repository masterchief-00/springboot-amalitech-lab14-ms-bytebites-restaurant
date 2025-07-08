package com.kwizera.orderservice.repositories;

import com.kwizera.orderservice.domain.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
}
