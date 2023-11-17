package com.progetto.BookHavenBackend.services;

import com.progetto.BookHavenBackend.entities.Book;
import com.progetto.BookHavenBackend.repositories.BookRepository;
import com.progetto.BookHavenBackend.support.exceptions.BookNotFoundException;
import jakarta.persistence.Transient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service

public class BookService {
    @Autowired
    BookRepository bookRepository;
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Book getBookById(long bookId) throws BookNotFoundException {
        Optional<Book> bookOptional = Optional.ofNullable(bookRepository.findBookById(bookId));
        if( bookOptional.isPresent()){
            return bookOptional.get();
        }else{
            return null;
        }
    }

    public List<Book> getAllBooksWithDiscount() {
        return bookRepository.findBooksWithDiscount();
    }

    public List<Book> getRecentBooks() {
        LocalDate oneWeekAgo = LocalDate.now().minusDays(7);
        return bookRepository.findRecentBooks(oneWeekAgo);
    }


    public List<Book> getBestSellingBooks() {
        List<Book> bestSellers = bookRepository.sortByNumberOfBuyers();
        // If there are more than 10 best sellers, return the first 10.
        return bestSellers.size() > 10 ? bestSellers.subList(0, 10) : bestSellers;
    }

}
