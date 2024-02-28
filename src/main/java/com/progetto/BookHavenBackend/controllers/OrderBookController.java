package com.progetto.BookHavenBackend.controllers;

import com.progetto.BookHavenBackend.entities.Book;
import com.progetto.BookHavenBackend.entities.Order;
import com.progetto.BookHavenBackend.entities.OrderBook;
import com.progetto.BookHavenBackend.services.OrderBookService;
import com.progetto.BookHavenBackend.support.common.ApiResponse;
import com.progetto.BookHavenBackend.support.exceptions.BookNotFoundException;
import com.progetto.BookHavenBackend.support.exceptions.CustomException;
import com.progetto.BookHavenBackend.support.exceptions.UpdateFailedException;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/{userId}/orders")
public class OrderBookController {

    @Autowired
    OrderBookService orderBookService;


    @PostMapping("/books/{bookId}")
    public ResponseEntity<ApiResponse> addBookToCart(@RequestBody  Book book){
        OrderBook cartItem = new OrderBook(book, 1);
        orderBookService.addBookToCart(cartItem);
        return new ResponseEntity<>(new ApiResponse(true, "New book is added to cart"), HttpStatus.CREATED);
    }

    @GetMapping("/")

    @RequestMapping(value="/{orderId}/books/{bookId}", method= RequestMethod.PUT)
    public void addBookQuantity(@PathVariable("orderId") long bookId , @PathVariable("bookId") long orderId) throws BookNotFoundException, CustomException {
        try{
            orderBookService.addBookQuantityInCart(bookId, orderId); // book quantity +1
        } catch (CustomException e){
            throw new CustomException("Book quantity in cart not updated");
        }
    }
}
