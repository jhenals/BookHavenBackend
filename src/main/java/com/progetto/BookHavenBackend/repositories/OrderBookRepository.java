package com.progetto.BookHavenBackend.repositories;

import com.progetto.BookHavenBackend.entities.OrderBook;
import com.progetto.BookHavenBackend.entities.OrderBookPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderBookRepository extends JpaRepository<OrderBook, OrderBookPK> {

    @Query("SELECT ob FROM OrderBook ob WHERE ob.pk.cart.id= :cartId")
    List<OrderBook> findAllByCartId(
            @Param("cartId") Long cartId);

    @Query("SELECT ob FROM OrderBook  ob WHERE ob.pk.book.id = :bookId AND ob.pk.cart.id = :cartId")
    OrderBook findByBookIdAndCartId(
            @Param("bookId")Long bookId,
            @Param("cartId")Long cartId);
}