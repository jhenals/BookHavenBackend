package com.progetto.BookHavenBackend.controllers;

import com.progetto.BookHavenBackend.entities.Book;
import com.progetto.BookHavenBackend.entities.Category;
import com.progetto.BookHavenBackend.entities.User;
import com.progetto.BookHavenBackend.services.BookService;
import com.progetto.BookHavenBackend.services.CategoryService;
import com.progetto.BookHavenBackend.support.ResponseMessage;
import com.progetto.BookHavenBackend.support.exceptions.BookNotFoundException;
import com.progetto.BookHavenBackend.support.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    BookService bookService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("books")
    public List<Book> getAllBooks(){
        List<Book> books= bookService.getAllBooks();

        //popolare discountedPrice per ogni libro  prima di inviare su frontend
        for( Book book: books){
            book.setDiscountedPrice(book.getDiscountedPrice());
        }
        return books;
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable long id) throws BookNotFoundException {
        try {
            Book book = bookService.getBookById(id);
            return new ResponseEntity<>(book, HttpStatus.OK);
        } catch (BookNotFoundException e) {
            return new ResponseEntity(new ResponseMessage("Error: Book not found"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/books/special-offers")
    public List<Book> getAllBooksWithDiscount(){
        return bookService.getAllBooksWithDiscount();
    }

    @GetMapping("/books/recent")
    public List<Book> getAllRecentlyAddedBooks(){
        return bookService.getRecentBooks();
    }

    @GetMapping("/books/best-sellers")
    public List<Book> getBestSellingBooks(){
        return bookService.getBestSellingBooks();
    }

}
