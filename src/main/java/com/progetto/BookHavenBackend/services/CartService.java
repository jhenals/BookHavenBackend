package com.progetto.BookHavenBackend.services;

import com.progetto.BookHavenBackend.entities.Cart;
import com.progetto.BookHavenBackend.entities.CartItem;
import com.progetto.BookHavenBackend.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;
    public void addToCart(CartItem cartItem, String userId) {
        Optional<Cart> cartOptional= Optional.ofNullable(cartRepository.findByUserId(userId));
        if(cartOptional.isPresent()){
            Cart cart= cartOptional.get();
            cart.getCartItems().add(cartItem);
            cartRepository.save(cart);
        }else{
            throw new RuntimeException("Error in adding book to cart");
        }


    }
}
