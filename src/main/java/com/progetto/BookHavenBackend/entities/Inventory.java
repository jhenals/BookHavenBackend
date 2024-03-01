package com.progetto.BookHavenBackend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Inventory extends Book {
    @Column(name = "quantity")
    private Long quantity;


    @Column(name = "date_time_book_is_last_updated")
    private LocalDateTime dateTimeBookIsLastUpdated;

    @Column(name = "num_purchases")
    private Long numPurchases;

}