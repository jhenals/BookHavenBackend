package com.progetto.BookHavenBackend.repositories;

import com.progetto.BookHavenBackend.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}