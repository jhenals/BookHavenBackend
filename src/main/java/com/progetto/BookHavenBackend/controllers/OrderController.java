package com.progetto.BookHavenBackend.controllers;

import com.progetto.BookHavenBackend.entities.Order;
import com.progetto.BookHavenBackend.entities.OrderBook;
import com.progetto.BookHavenBackend.entities.OrderStatus;
import com.progetto.BookHavenBackend.services.BookService;
import com.progetto.BookHavenBackend.services.OrderBookService;
import com.progetto.BookHavenBackend.services.OrderService;
import com.progetto.BookHavenBackend.support.exceptions.BookNotFoundException;
import com.progetto.BookHavenBackend.support.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    BookService bookService;

    @Autowired
    OrderService orderService;


   @Autowired
    OrderBookService orderBookService;

   @GetMapping
   @ResponseStatus(HttpStatus.OK)
   public @NotNull Iterable<Order> getAllOrders(){
       return this.orderService.getAllOrders();
   }

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody OrderForm form) throws BookNotFoundException {
        List<OrderBook> orderForm = form.getOrderedBooks();
        validateBookExistence(orderForm);

        Order order = new Order();
        order.setOrderStatus(OrderStatus.PAID);
        order = this.orderService.createOrder(order);

        List<OrderBook> orderBooks = new ArrayList<>();
        for( OrderBook ob : orderForm){
            orderBooks.add(orderBookService.addBookToCart(new OrderBook(order, bookService.getBookById(ob.getBook().getId()), ob.getQuantity())));
        }

        order.setOrderedBooks(orderBooks);
        this.orderService.update(order);

        String uri= ServletUriComponentsBuilder
                .fromCurrentServletMapping()
                .path("orders/{id}")
                .buildAndExpand(order.getId())
                .toString();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", uri);

        return new ResponseEntity<Order>(order, headers, HttpStatus.CREATED);
    }

    private void validateBookExistence(List<OrderBook> orderBooks) {
       List<OrderBook> list = orderBooks
               .stream()
               .filter(ob -> {
                   try {
                       return Objects.isNull(bookService.getBookById(ob.getBook().getId()));
                   } catch (BookNotFoundException e) {
                       throw new RuntimeException(e);
                   }
               }).toList(); // .collect(Collectors.toList());

        if(!CollectionUtils.isEmpty(list)){
            new ResourceNotFoundException("Book not found");
        }
    }
    public static class OrderForm{
       private List<OrderBook> orderedBooks;
       public List<OrderBook> getOrderedBooks(){
           return orderedBooks;
       }

       public void setOrderedBooks(List<OrderBook> orderedBooks){
           this.orderedBooks = orderedBooks;
        }
    }
}
