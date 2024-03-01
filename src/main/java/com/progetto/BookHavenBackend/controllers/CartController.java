package com.progetto.BookHavenBackend.controllers;

import com.progetto.BookHavenBackend.entities.Book;
import com.progetto.BookHavenBackend.entities.Cart;
import com.progetto.BookHavenBackend.entities.CartItem;
import com.progetto.BookHavenBackend.services.CartService;
import com.progetto.BookHavenBackend.support.exceptions.BookNotFoundException;
import com.progetto.BookHavenBackend.support.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    @Autowired
    CartService cartService;

    @GetMapping("/{userId}")
    public Cart getCart( @PathVariable("userId") String userId){
        return cartService.getCart(userId);
    }

    @GetMapping("/{userId}/cart-items")
    public List<CartItem> getCartItems(@PathVariable("userId") String userId){
        return cartService.getCartItems(userId);
    }


    @PostMapping("/{userId}")
    public ResponseEntity addBookToCart(@RequestBody Book book, @Valid @PathVariable("userId") String userId ) {
        try{
            return new ResponseEntity<>( cartService.addBookToCart(book, userId), HttpStatus.OK);
        } catch (BookNotFoundException e) {
            throw new RuntimeException(e);
        } catch(CustomException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book can not be added to cart!", e);
        }
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity removeBookFromCart(@RequestBody Book book, @Valid @PathVariable("userId") String userId ) {
        try{
            return new ResponseEntity<>( cartService.removeBookFromCart(book, userId), HttpStatus.OK);
        } catch (BookNotFoundException e) {
            throw new RuntimeException(e);
        } catch(CustomException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book can not be removed from cart!", e);
        }
    }

    @RequestMapping(value = "/{userId}/increment-item-quantity", method = RequestMethod.PUT)
    public void incrementBookQtyInCart(@PathVariable("userId") String userId, @Valid @RequestBody Book book) throws BookNotFoundException {
        cartService.incrementBookQtyInCart(userId, book);
    }


    @RequestMapping(value = "/{userId}/decrement-item-quantity", method = RequestMethod.PUT)
    public void decrementBookQtyInCart(@PathVariable("userId") String userId, @Valid @RequestBody Book book) throws BookNotFoundException {
        cartService.decrementBookQtyInCart(userId, book);
    }

    @RequestMapping(value = "/{userId}/decrement-item-quantity", method = RequestMethod.PUT)
    public Cart checkout(@PathVariable("userId") String userId, @Valid @RequestBody Cart cart) {
        return cartService.checkout(userId, cart);
    }


}
