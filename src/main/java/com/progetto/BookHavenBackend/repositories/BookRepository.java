package com.progetto.BookHavenBackend.repositories;

import com.progetto.BookHavenBackend.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE b.id= :bookId")
    Book findBookById(@Param("bookId") Long bookId);


    @Query("SELECT b FROM Book b WHERE b.discount > 0")
    List<Book> findBooksWithDiscount();

    @Query("SELECT b FROM Book b WHERE b.dateBookAdded >= :oneWeekAgo")
    List<Book> findRecentBooks(@Param("oneWeekAgo") LocalDate oneWeekAgo);

    @Query("SELECT b FROM Inventory b ORDER BY b.numPurchases DESC")
    List<Book> sortByNumberOfBuyers();
}