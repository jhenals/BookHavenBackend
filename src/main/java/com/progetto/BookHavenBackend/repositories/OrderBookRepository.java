package com.progetto.BookHavenBackend.repositories;

import com.progetto.BookHavenBackend.entities.OrderBook;
import com.progetto.BookHavenBackend.entities.OrderBookPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderBookRepository extends JpaRepository<OrderBook, OrderBookPK> {


    @Query("SELECT ob FROM OrderBook  ob WHERE ob.pk.book= :bookId AND ob.pk.order= :orderId")
    OrderBook findEntryByIds(long bookId, long orderId);

   // void updateBookQuantityInOrder(long bookId, long orderId);
}