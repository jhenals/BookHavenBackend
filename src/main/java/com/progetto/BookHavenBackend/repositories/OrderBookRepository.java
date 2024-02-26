package com.progetto.BookHavenBackend.repositories;

import com.progetto.BookHavenBackend.entities.OrderBook;
import com.progetto.BookHavenBackend.entities.OrderBookPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderBookRepository extends JpaRepository<OrderBook, OrderBookPK> {
}