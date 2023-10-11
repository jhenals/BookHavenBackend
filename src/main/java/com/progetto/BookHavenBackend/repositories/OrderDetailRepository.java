package com.progetto.BookHavenBackend.repositories;

import com.progetto.BookHavenBackend.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}