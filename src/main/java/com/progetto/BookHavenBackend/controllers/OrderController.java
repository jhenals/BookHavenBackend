package com.progetto.BookHavenBackend.controllers;

import com.progetto.BookHavenBackend.entities.Cart;
import com.progetto.BookHavenBackend.entities.Order;
import com.progetto.BookHavenBackend.entities.OrderBook;
import com.progetto.BookHavenBackend.services.OrderService;
import com.progetto.BookHavenBackend.support.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

     @GetMapping("/{userId}")
     public List<Order> getAllOrdersOfUser(@PathVariable("userId") String userId) {
         return orderService.getAllOrdersOfUser(userId);
     }

     @GetMapping("/{userId}/{orderId}")
    public List<OrderBook> getItemsOfOrder(@PathVariable("userId") String userId, @PathVariable("orderId") Long orderId){
         return orderService.getCartItemsOfOrder(userId, orderId);
     }



}
