package com.progetto.BookHavenBackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "created_date_time")
    private LocalTime createdDateTime;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

 //   @ManyToOne
 //   @JoinColumn(name = "user_id")
 //   private User user;

    @Column(name = "quantity")
    private Integer quantity;

}