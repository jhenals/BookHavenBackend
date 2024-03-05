package com.progetto.BookHavenBackend.controllers;

import com.progetto.BookHavenBackend.entities.Book;
import com.progetto.BookHavenBackend.entities.Cart;
import com.progetto.BookHavenBackend.entities.OrderBook;
import com.progetto.BookHavenBackend.entities.OrderStatus;
import com.progetto.BookHavenBackend.repositories.CartRepository;
import com.progetto.BookHavenBackend.services.CartService;
import com.progetto.BookHavenBackend.services.OrderBookService;
import com.progetto.BookHavenBackend.support.exceptions.BookNotFoundException;
import com.progetto.BookHavenBackend.support.exceptions.CustomException;
import com.progetto.BookHavenBackend.support.exceptions.UserNotFoundException;
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

    @Autowired
    OrderBookService orderBookService;

    @GetMapping("{userId}")
    public Cart getPendingCart( @PathVariable("userId") String userId) throws UserNotFoundException {
        return cartService.getPendingCart(userId);
    }

    @GetMapping("{userId}/cart-items")
    public List<OrderBook> getItemsInPendingCart( @PathVariable("userId") String userId) throws UserNotFoundException {
        Cart pendingCart = getPendingCart(userId);
        return orderBookService.getItemsInUserPendingCart(pendingCart.getId());
    }

    @RequestMapping(value = "/{userId}/reset", method = RequestMethod.PUT)
    public void resetCart(@PathVariable("userId") String userId) throws UserNotFoundException {
        Cart pendingCart = getPendingCart(userId);
        cartService.resetCart(userId, pendingCart.getId());

    }

    @PostMapping("/{userId}")
    public ResponseEntity addBookToCart(@RequestBody Book book, @Valid @PathVariable("userId") String userId ) {
        try{
            return new ResponseEntity<>( cartService.addBookToCart(book, userId), HttpStatus.OK);
        } catch (BookNotFoundException e) {
            throw new RuntimeException(e);
        } catch(CustomException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book can not be added to cart!", e);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity removeBookFromCart(@RequestBody Book book, @Valid @PathVariable("userId") String userId ) {
        try{
            return new ResponseEntity<>( cartService.removeBookFromCart(book, userId), HttpStatus.OK);
        } catch (BookNotFoundException | UserNotFoundException e) {
            throw new RuntimeException(e);
        } catch(CustomException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book can not be removed from cart!", e);
        }
    }

    @RequestMapping(value = "/{userId}/increment-item-quantity", method = RequestMethod.PUT)
    public ResponseEntity incrementBookQtyInCart(@PathVariable("userId") String userId, @Valid @RequestBody Book book) throws BookNotFoundException {
        try{
            return new ResponseEntity<>( cartService.incrementBookQtyInCart(userId, book), HttpStatus.OK);
        }catch(CustomException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book quantity can not be incremented!", e);
        }

    }


    @RequestMapping(value = "/{userId}/decrement-item-quantity", method = RequestMethod.PUT)
    public void decrementBookQtyInCart(@PathVariable("userId") String userId, @Valid @RequestBody Book book) throws BookNotFoundException {
        removeBookFromCart(book, userId);
    }

    /*
    @RequestMapping(value = "/{userId}/decrement-item-quantity", method = RequestMethod.PUT)
    public Cart checkout(@PathVariable("userId") String userId, @Valid @RequestBody Cart cart) {
        return cartService.checkout(userId, cart);
    }
     */

}
