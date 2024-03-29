package com.progetto.BookHavenBackend.repositories;

import com.progetto.BookHavenBackend.entities.Cart;
import com.progetto.BookHavenBackend.entities.OrderStatus;
import com.progetto.BookHavenBackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId")
    Cart findByUserId(@Param("userId") String userId);

    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId AND c.cartStatus = :orderStatus")
    Cart findByUserIdAndCartStatus(
            @Param("userId") String userId,
            @Param("orderStatus") OrderStatus orderStatus);

}