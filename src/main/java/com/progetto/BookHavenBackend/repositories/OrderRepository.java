package com.progetto.BookHavenBackend.repositories;

import com.progetto.BookHavenBackend.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface OrderRepository extends JpaRepository<Order, Long> {


}