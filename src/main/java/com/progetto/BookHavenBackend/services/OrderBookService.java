package com.progetto.BookHavenBackend.services;

import com.progetto.BookHavenBackend.entities.OrderBook;
import com.progetto.BookHavenBackend.repositories.OrderBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderBookService {
    @Autowired
    OrderBookRepository orderBookRepository;

    public List<OrderBook> getItemsInUserPendingCart(Long cartId) {
        return orderBookRepository.findAllByCartId(cartId);
    }
}
