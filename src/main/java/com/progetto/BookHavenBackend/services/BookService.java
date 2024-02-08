package com.progetto.BookHavenBackend.services;

import com.progetto.BookHavenBackend.entities.Book;
import com.progetto.BookHavenBackend.repositories.BookRepository;
import com.progetto.BookHavenBackend.support.exceptions.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Book addNewBook(Book book) {
        return bookRepository.save(book);
    }

    public void updateBook(long id, Book book) throws BookNotFoundException{
        Optional<Book> bookOptional = Optional.ofNullable(bookRepository.findBookById(id));
        if(bookOptional.isPresent()) {
            Book newBook = bookOptional.get();
            newBook = book;
            bookRepository.save(newBook);
        }else{
            throw  new BookNotFoundException();
        }
    }

    public void deleteBookById(long id) throws BookNotFoundException {
        Optional<Book> bookOptional = Optional.ofNullable(bookRepository.findBookById(id));
        if( bookOptional.isPresent()){
            bookRepository.deleteById(id);
        }else{
            throw new BookNotFoundException();
        }
    }

    public List<Book> getWishlist() {
        List<Book> wishlist = bookRepository.findBooksInWishlist();
        return wishlist;
    }

    public Book findBookById(Long bookId) throws BookNotFoundException {
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()){
            throw new BookNotFoundException("Product id is not valid" + bookId);
        }
        return optionalBook.get();
    }
}
