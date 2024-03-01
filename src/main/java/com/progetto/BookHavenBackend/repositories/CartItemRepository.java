package com.progetto.BookHavenBackend.repositories;

import com.progetto.BookHavenBackend.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query("SELECT ci FROM CartItem ci WHERE ci.user.id = :userId AND ci.book.id = :bookId AND ci.cart.id = :cartId")
    Optional<CartItem> findByUserIdAndBookIdAndCartId(
            @Param("userId") String userId,
            @Param("bookId") Long bookId,
            @Param("cartId") Long cartId);

}