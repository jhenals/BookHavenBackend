package com.progetto.BookHavenBackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

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

    @Column(name = "book_cover_url")
    private String bookCoverUrl;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "data_pubblicazione")
    private LocalDate dataPubblicazione;

    @Column(name = "editor")
    private String editor;

    @Column(name = "discount", precision = 10, scale = 2)
    private BigDecimal discount;

    @Column(name = "date_book_added")
    private LocalDate dateBookAdded;

    @Column(name = "number_buyers")
    private Integer numberOfBuyers;

    @Transient
    private BigDecimal discountedPrice;

    @Column(name = "is_in_cart")
    private Boolean isInCart;

    @Column(name = "is_in_wishlist")
    private Boolean isInWishlist;

    @Column(name = "quantity")
    private Integer quantity;

//In this example, discountedPrice is marked as @Transient,
    // indicating that it won't be persisted in the database.
    // The getDiscountedPrice method calculates the discounted price based on the price and discount fields.

    // non deve essere memorizzato in modo persistente nel database,
    // puoi marcarlo con l'annotazione @Transient nella tua classe di entità.
    // Ciò indica a JPA (Java Persistence API) di ignorare quel campo durante le operazioni di persistenza.


    public BigDecimal getDiscountedPrice() {
        if (price != null && discount != null) {
            BigDecimal discountAmount = price.multiply(discount).divide(new BigDecimal(100));
            BigDecimal discountedPrice= price.subtract(discountAmount).setScale(2, RoundingMode.HALF_UP);
            return discountedPrice ;
        } else {
            return null; // Handle this appropriately based on your use case
        }
    }

    // @OneToMany(mappedBy = "book", orphanRemoval = true)
    //private Set<Category> categories = new LinkedHashSet<>();

}