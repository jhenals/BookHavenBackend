package com.progetto.BookHavenBackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "amazon_reviews", precision = 10, scale = 1)
    private BigDecimal amazonReviews;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "`isbn-10`")
    private Long isbn10;

    @Column(name = "data_pubblicazione")
    private LocalDate dataPubblicazione;

    @Column(name = "editor")
    private String editor;

    @Column(name = "discount", precision = 10, scale = 2)
    private BigDecimal discount;

    @Column(name = "date_book_added", nullable = false)
    private LocalDate dateBookAdded;

    @Column(name = "number_buyers")
    private Integer numberOfBuyers;


    // @OneToMany(mappedBy = "book", orphanRemoval = true)
    //private Set<Category> categories = new LinkedHashSet<>();

}