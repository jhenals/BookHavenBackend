package com.progetto.BookHavenBackend.repositories;

import com.progetto.BookHavenBackend.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

}
