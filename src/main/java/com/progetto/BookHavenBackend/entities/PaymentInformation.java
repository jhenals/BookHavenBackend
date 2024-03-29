package com.progetto.BookHavenBackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Column(name = "card_type")
    private String cardType;

    @Column(name = "cvv", nullable = false)
    private String cvv; //make this decrypted

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "is_default")
    private Boolean isDefault;

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        if(cardNumber.startsWith("4")){
            this.cardType = "Visa";
        }else if( cardNumber.matches("^5[1-5].*")) {
            this.cardType = "MasterCard";
        }else{
            this.cardType = "Unknown";
        }
    }
}