package com.progetto.BookHavenBackend.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "date_time")
    @CreationTimestamp
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDateTime dateTime; //make today the default date

    @Column(name = "total_amount", precision = 19, scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;


    @OneToMany(mappedBy = "pk.order")
    private List<OrderBook> orderedBooks = new ArrayList<>();

    @Transient
    public BigDecimal getTotalOrderPrice(){
        BigDecimal sum= BigDecimal.valueOf(0);
        List<OrderBook> orderedBooks = getOrderedBooks();
        for( OrderBook book: orderedBooks){
           // sum = sum.add(book.getTotalPrice());
        }
        return sum;
    }

    @Column(name = "recipient_name")
    private String recipientName;

    //Address

    //paymentMethod

    @Transient
    public int getNUmberOfItems(){
        return this.orderedBooks.size();
    }


    @Column(name = "is_archived")
    private Boolean isArchived;

    // standard getters and setters

}