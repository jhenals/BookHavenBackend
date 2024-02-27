package com.progetto.BookHavenBackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class CartItem {

    @Id
    @ManyToOne
    @JoinColumn(name="book_id")
    private Book book;


    private int orderQty;
    private BigDecimal finalPrice;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public CartItem(Book book){
        this.book= book;
        orderQty=1;
        finalPrice = book.getDiscount()!=null? book.getDiscountedPrice() : book.getPrice();
    }

    public CartItem() {
    }
}
