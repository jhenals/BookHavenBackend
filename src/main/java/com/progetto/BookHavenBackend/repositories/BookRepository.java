package com.progetto.BookHavenBackend.repositories;

import com.progetto.BookHavenBackend.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE b.id= :bookId")
    Book findBookById(@Param("bookId") Long bookId);


    @Query("SELECT b FROM Book b WHERE b.discount IS NOT null")
    List<Book> findBooksWithDiscount();
}