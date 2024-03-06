package com.progetto.BookHavenBackend.repositories;

import com.progetto.BookHavenBackend.entities.Order;
import com.progetto.BookHavenBackend.entities.OrderBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE o.user.id = :userId ")
    List<Order> findAllByUser(@Param("userId") String userId);

    @Query("SELECT o FROM Order o WHERE o.id= :orderId AND o.user.id= :userId")
    Order findByIdAndUserId(
            @Param("orderId") Long orderId,
            @Param("userId") String userId);
}