package com.progetto.BookHavenBackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "payment_information")
public class PaymentInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "cardholder_name", nullable = false)
    private String cardholderName;

    @Column(name = "card_number", nullable = false)
    private String cardNumber;

    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    @Column(name = "cvv", nullable = false)
    private String cvv; //make this decrypted

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}