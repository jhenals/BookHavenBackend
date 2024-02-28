package com.progetto.BookHavenBackend.controllers;

import com.progetto.BookHavenBackend.entities.Book;
import com.progetto.BookHavenBackend.entities.CartItem;
import com.progetto.BookHavenBackend.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;


    //@PostMapping("/cart")
   // public ResponseEntity<?> addProductToCart(@RequestBody CartItem cartItem){
   //     return customerService.addBookToCart(cartItem);
   // }

}
