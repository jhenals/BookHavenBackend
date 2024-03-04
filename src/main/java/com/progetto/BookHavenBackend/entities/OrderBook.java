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

    @Column(name = "final_price", precision = 19, scale = 2)
    private BigDecimal finalPrice;

    public OrderBook(Cart cart, Book book, Integer qty){
        pk = new OrderBookPK();
        pk.setCart(cart);
        pk.setBook(book);
        this.quantity = qty;
        this.finalPrice = getBookFinalPrice();
    }

    public OrderBook() {

    }

    @Transient
    public Book getBook(){
        return this.pk.getBook();
    }

    @Transient
    public BigDecimal getBookFinalPrice(){
        return getBook().getDiscountedPrice().multiply(BigDecimal.valueOf(getQuantity()));
    }


}