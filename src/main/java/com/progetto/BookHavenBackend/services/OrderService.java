package com.progetto.BookHavenBackend.services;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.progetto.BookHavenBackend.entities.Cart;
import com.progetto.BookHavenBackend.entities.Order;
import com.progetto.BookHavenBackend.entities.OrderBook;
import com.progetto.BookHavenBackend.repositories.OrderBookRepository;
import com.progetto.BookHavenBackend.repositories.OrderRepository;
import com.progetto.BookHavenBackend.support.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public List<Order> getAllOrdersOfUser(String userId) {
        return orderRepository.findAllByUser(userId);
    }

    public List<OrderBook> getCartItemsOfOrder(String userId, Long orderId){
        Optional<Order> orderOptional= Optional.ofNullable(orderRepository.findByIdAndUserId(orderId, userId));
        if(orderOptional.isPresent()){
            Order order = orderOptional.get();
            return order.getCart().getOrderBooks();
        }
        else{
            throw new CustomException("Error in getting items");
        }
    }
}
