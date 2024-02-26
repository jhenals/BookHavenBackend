package com.progetto.BookHavenBackend.services;

import com.progetto.BookHavenBackend.entities.OrderBook;
import com.progetto.BookHavenBackend.repositories.OrderBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;



@Service
@Transactional
public class OrderBookService {

    @Autowired
    OrderBookRepository orderBookRepository;

    public OrderBook create(OrderBook orderBook){
        return this.orderBookRepository.save(orderBook);
    }
   // OrderBook create(@NotNull(message = "The books for order cannot be null.") @Valid OrderBook orderBook);
}
