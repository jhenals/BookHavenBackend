package com.progetto.BookHavenBackend.services;

import com.progetto.BookHavenBackend.entities.*;
import com.progetto.BookHavenBackend.repositories.BookRepository;
import com.progetto.BookHavenBackend.repositories.CartItemRepository;
import com.progetto.BookHavenBackend.repositories.CartRepository;
import com.progetto.BookHavenBackend.repositories.UserRepository;
import com.progetto.BookHavenBackend.support.exceptions.UserNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    UserRepository userRepository;

    /*
    public ResponseEntity<?> addBookToCart(CartItem cartItem){
        Cart pendingCart = cartRepository.findByUserIdAndOrderStatus(cartItem.getUser().getId(), OrderStatus.PENDING);
        Optional<CartItem> cartItemOptional = cartItemRepository.findByUserIdAndBookIdAndCartId(
               // cartItem.getUser().getId(),
              //  cartItem.getBook().getId(),
                cartItem.getCart().getId());
        if (cartItemOptional.isPresent()){
            CartItem itemAlreadyExistingInCart = new CartItem();
            itemAlreadyExistingInCart.setBook(null);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(itemAlreadyExistingInCart);
        }else{
            Optional<Book> bookOptional = bookRepository.findById(cartItem.getBook().getId());
            Optional<User> userOptional = Optional.ofNullable(userRepository.findById(cartItem.getUser().getId()));
            if (cartItem.getUser() == null) {
                // Handle the case where the user is null, perhaps return an appropriate error response
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User is required.");
            }

            if(bookOptional.isPresent() && userOptional.isPresent()){
                Book book = bookOptional.get();
                CartItem newCartItem = new CartItem();
                newCartItem.setBook(book);
                //newCartItem.setUser(userOptional.get());
                newCartItem.setOrderQty(1);
                newCartItem.setCart(pendingCart);
                newCartItem.setFinalPrice(cartItem.getFinalPrice());
                CartItem updatedCartItem = cartItemRepository.save(newCartItem);
                pendingCart.setTotalPrice(pendingCart.getTotalPrice().add(updatedCartItem.getFinalPrice()));
                pendingCart.getCartItems().add(updatedCartItem);
                cartRepository.save(pendingCart);
                return ResponseEntity.status(HttpStatus.CREATED).body(newCartItem);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or book not found");
            }
        }
    }
    */

}
