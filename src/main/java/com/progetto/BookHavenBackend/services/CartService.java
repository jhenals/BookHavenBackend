package com.progetto.BookHavenBackend.services;
import com.progetto.BookHavenBackend.entities.Book;
import com.progetto.BookHavenBackend.entities.Cart;
import com.progetto.BookHavenBackend.entities.CartItem;
import com.progetto.BookHavenBackend.repositories.CartRepository;
import com.progetto.BookHavenBackend.support.exceptions.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class CartService {
    @Autowired
    BookService bookService;

    @Autowired
    CartRepository cartRepository;

    public void addToCart(CartItem cartItem) throws BookNotFoundException {
        //validate if the item is valid
        Book bookItem= bookService.findBookById(cartItem.getBook().getId());
        Cart cart = new Cart();
        cart.setBook(bookItem);
        cart.setQuantity(cartItem.getQuantity());
        cart.setCreatedDateTime(LocalTime.from(LocalDateTime.now()));
        //cart.setUser(user);

        //save the cart
        cartRepository.save(cart);

    }
}
