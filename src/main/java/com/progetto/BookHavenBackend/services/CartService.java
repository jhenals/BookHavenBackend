package com.progetto.BookHavenBackend.services;

import com.progetto.BookHavenBackend.entities.*;
import com.progetto.BookHavenBackend.repositories.BookRepository;
import com.progetto.BookHavenBackend.repositories.CartItemRepository;
import com.progetto.BookHavenBackend.repositories.CartRepository;
import com.progetto.BookHavenBackend.repositories.UserRepository;
import com.progetto.BookHavenBackend.support.ResponseMessage;
import com.progetto.BookHavenBackend.support.exceptions.BookNotFoundException;
import com.progetto.BookHavenBackend.support.exceptions.CustomException;
import com.progetto.BookHavenBackend.support.exceptions.UserNotFoundException;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    private EntityManager entityManager;


    @Transactional(readOnly = false)
    public Cart addBookToCart(Book book,String userId) throws CustomException {
        if (book == null || userId == null) {
            throw new CustomException("Book and userId are required.");
        }

        Cart pendingCart = cartRepository.findByUserIdAndOrderStatus(userId, OrderStatus.PENDING);
        if(pendingCart==null){
            pendingCart = new Cart();
            User user = userRepository.findById(userId);
            if( user==null){
                throw new CustomException("User not found in database.");
            }else{
                pendingCart.setUser(user);
            }
        }

        Optional<CartItem> cartItemOptional = cartItemRepository.findByUserIdAndBookIdAndCartId(userId, book.getId(), pendingCart.getId());
        if (cartItemOptional.isPresent()) {
               throw new CustomException("Book is already in cart");
        } else {
            Optional<Book> bookOptional = bookRepository.findById(book.getId());
            if(bookOptional.isPresent()){
                Book bookToAdd= bookOptional.get();
                CartItem newCartItem = new CartItem();
                newCartItem.setBook(bookToAdd);
                newCartItem.setUserId(userId);
                newCartItem.setCart(pendingCart);
                newCartItem.setFinalPrice(newCartItem.getFinalPrice());
                CartItem newItem= cartItemRepository.save(newCartItem);
                pendingCart.getCartItems().add(newItem);

            }else {
                throw new CustomException("Book is not in the database");
            }
        }
        return cartRepository.save(pendingCart);

    }

}
