package com.progetto.BookHavenBackend.services;

import com.progetto.BookHavenBackend.entities.Book;
import com.progetto.BookHavenBackend.entities.Order;
import com.progetto.BookHavenBackend.entities.OrderBook;
import com.progetto.BookHavenBackend.repositories.BookRepository;
import com.progetto.BookHavenBackend.repositories.OrderBookRepository;
import com.progetto.BookHavenBackend.repositories.OrderRepository;
import com.progetto.BookHavenBackend.support.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;


@Service
@Transactional
public class OrderBookService {

    @Autowired
    OrderBookRepository orderBookRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private OrderRepository orderRepository;

    public OrderBook addBookToCart(OrderBook orderBook){
        return this.orderBookRepository.save(orderBook);
    }

    public void addBookQuantityInCart(long bookId, long orderId) {
        Optional<OrderBook> orderBookOptional = Optional.ofNullable(orderBookRepository.findEntryByIds(bookId, orderId));
        if( orderBookOptional.isPresent()){
            OrderBook orderBook = orderBookOptional.get();
            int newQty = orderBook.getQuantity() + 1; //incremento
            orderBook.setQuantity(newQty);
            orderBookRepository.save(orderBook);
        }else{
            throw new CustomException("Entry not present in database");
        }
    }
    // OrderBook create(@NotNull(message = "The books for order cannot be null.") @Valid OrderBook orderBook);
}
