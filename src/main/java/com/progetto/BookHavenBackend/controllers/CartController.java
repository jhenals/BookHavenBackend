package com.progetto.BookHavenBackend.controllers;

import com.progetto.BookHavenBackend.entities.Book;
import com.progetto.BookHavenBackend.entities.CartItem;
import com.progetto.BookHavenBackend.services.CartService;
import com.progetto.BookHavenBackend.support.common.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/{userId}/cart")
public class CartController {
    @Autowired
    CartService cartService;

    @PostMapping
    public ResponseEntity<ApiResponse> addToCart(@PathVariable String userId,  Book book){
        CartItem cartItem= new CartItem(book);
        cartService.addToCart(cartItem, userId);
        return new ResponseEntity<>(new ApiResponse(true,"Book added to cart successfully!"), HttpStatus.OK);
    }
}
