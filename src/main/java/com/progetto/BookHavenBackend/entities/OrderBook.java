package com.progetto.BookHavenBackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "order_book")
public class OrderBook {
    @EmbeddedId
    @JsonIgnore
    private OrderBookPK pk;

    @Column(nullable = false)
    private Integer quantity;

    public OrderBook (Order order, Book book , Integer quantity){
        pk = new OrderBookPK();
        pk.setOrder(order);
        pk.setBook(book);
        this.quantity = quantity;
    }

    @Transient
    public Book getBook(){
        return this.pk.getBook();
    }

    @Transient
    public BigDecimal getTotalPrice(){
        return getBook().getPrice().multiply(BigDecimal.valueOf(getQuantity()));
    }

    // standard getters and setters

    // hashcode() and equals() methods

}