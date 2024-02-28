package com.progetto.BookHavenBackend.controllers;

import com.progetto.BookHavenBackend.entities.Book;
import com.progetto.BookHavenBackend.entities.Cart;
import com.progetto.BookHavenBackend.entities.CartItem;
import com.progetto.BookHavenBackend.services.CartService;
import com.progetto.BookHavenBackend.support.exceptions.BookNotFoundException;
import com.progetto.BookHavenBackend.support.exceptions.CustomException;
import com.progetto.BookHavenBackend.support.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    @Autowired
    CartService cartService;


    @PostMapping
    @ResponseStatus(code= HttpStatus.OK)
    public ResponseEntity addBookToCart(@RequestBody Book book, @RequestBody  String userId) {
        try{
            return new ResponseEntity<>( cartService.addBookToCart(book, userId), HttpStatus.OK);
        }catch(CustomException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book can not be added to cart!", e);
        }
    }

    /*
    @GetMapping("/cart-items")
    public List<CartItem> getCartItems(@RequestBody  String userId){
        List<CartItem> cartitems = new ArrayList<>();
        Cart cart = cartService.getCartOfUser(userId);
        if(cart != null ){
            cartitems = cart.getCartItems();
        }
        return cartitems;
    }

    */

    public void incrementBookQtyInCart(){

    }

    public void decrementBookQtyInCart(){

    }


}
