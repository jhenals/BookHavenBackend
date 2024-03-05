package com.progetto.BookHavenBackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Column(name = "date_time")
    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

    @Column(name = "recipient_name")
    private String recipientName;

    @Column(name = "address")
    private String shippingAddress;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "payment_information_id")
    private PaymentInformation paymentInformation;

}