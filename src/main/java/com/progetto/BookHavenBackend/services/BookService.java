package com.progetto.BookHavenBackend.services;

import com.progetto.BookHavenBackend.entities.Book;
import com.progetto.BookHavenBackend.entities.Inventory;
import com.progetto.BookHavenBackend.repositories.BookRepository;
import com.progetto.BookHavenBackend.repositories.InventoryRepository;
import com.progetto.BookHavenBackend.support.exceptions.BookNotFoundException;
import com.progetto.BookHavenBackend.support.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service

public class BookService {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    private InventoryRepository inventoryRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

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
        return bestSellers.size() > 10 ? bestSellers.subList(0, 10) : bestSellers; // If there are more than 10 best sellers, return the first 10.
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
        if( bookOptional.isPresent() ){
            if (inventoryRepository.findById(id).get().getQuantity() == 0) {
                bookRepository.deleteById(id);
            } else {
                throw new CustomException("Book can not be deleted because it is not empty in inventory.");
            }
        }else{
            throw new BookNotFoundException();
        }
    }

    public void updateBookQuantityInInventory(Long id, Long quantity) throws BookNotFoundException {
        Optional<Book> bookOptional = Optional.ofNullable(bookRepository.findBookById(id));
        if (bookOptional.isPresent()){
            Inventory bookInventory = new Inventory();
            bookInventory.setId(id);
            bookInventory.setQuantity(quantity);
            bookInventory.setDateTimeBookIsLastUpdated(LocalDateTime.now());
            inventoryRepository.save(bookInventory);
        }else{
            throw new BookNotFoundException();
        }

    }
}
