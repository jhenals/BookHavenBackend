package com.progetto.BookHavenBackend.services;

import com.progetto.BookHavenBackend.entities.Order;
import com.progetto.BookHavenBackend.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;
    public Iterable<Order> getAllOrders() {
        return this.orderRepository.findAll();
    }

    public Order createOrder(Order order){
        order.setDateTime(LocalDateTime.now());
        return this.orderRepository.save(order);
    }

    public void update(Order order){
        this.orderRepository.save(order);
    }


}
