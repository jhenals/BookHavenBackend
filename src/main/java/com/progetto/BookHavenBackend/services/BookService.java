package com.progetto.BookHavenBackend.services;

import com.progetto.BookHavenBackend.entities.Book;
import com.progetto.BookHavenBackend.repositories.BookRepository;
import com.progetto.BookHavenBackend.support.exceptions.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
