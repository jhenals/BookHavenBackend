package com.progetto.BookHavenBackend.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "total_price", precision = 19, scale = 2)
    private BigDecimal totalPrice;

    @OneToMany(mappedBy = "pk.cart")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private List<OrderBook> orderBooks = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "cart_status")
    private OrderStatus cartStatus;

    public Cart(){
        this.cartStatus = OrderStatus.PENDING;
        this.totalPrice = BigDecimal.ZERO;
        this.orderBooks = new ArrayList<>();
    }


}