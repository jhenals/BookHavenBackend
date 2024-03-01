package com.progetto.BookHavenBackend.controllers;

import com.progetto.BookHavenBackend.entities.Book;
import com.progetto.BookHavenBackend.entities.Category;
import com.progetto.BookHavenBackend.entities.User;
import com.progetto.BookHavenBackend.repositories.InventoryRepository;
import com.progetto.BookHavenBackend.services.BookService;
import com.progetto.BookHavenBackend.services.CategoryService;
import com.progetto.BookHavenBackend.support.ResponseMessage;
import com.progetto.BookHavenBackend.support.common.ApiResponse;
import com.progetto.BookHavenBackend.support.exceptions.BookNotFoundException;
import com.progetto.BookHavenBackend.support.exceptions.CustomException;
import com.progetto.BookHavenBackend.support.exceptions.UpdateFailedException;
import com.progetto.BookHavenBackend.support.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BookController {

    @Autowired
    BookService bookService;
    @Autowired
    private InventoryRepository inventoryRepository;


    //CREATE
    @PostMapping("/books")
    public ResponseEntity<ApiResponse> addNewBook(@RequestBody  Book book) {
        bookService.addNewBook(book);
        return new ResponseEntity<>(new ApiResponse(true,  "New book has been added."), HttpStatus.CREATED);
    }

    //READ
    @GetMapping("/books")
    public List<Book> getAllBooks(){
        List<Book> books= bookService.getAllBooks();

        for( Book book: books){
            book.setDiscountedPrice(book.getDiscountedPrice());
        }
        return books;
    }

    //READ
    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable long id) throws BookNotFoundException {
        try {
            Book book = bookService.getBookById(id);
            return new ResponseEntity<>(book, HttpStatus.OK);
        } catch (BookNotFoundException e) {
            return new ResponseEntity(new ResponseMessage("Error: Book not found"), HttpStatus.BAD_REQUEST);
        }
    }

    //UPDATE
    @RequestMapping(value = "/books/{id}", method = RequestMethod.PUT)
    public void updateBook( @PathVariable long id, @RequestBody Book book) throws BookNotFoundException, UpdateFailedException{
        try{
            bookService.updateBook(id, book);
        }catch( BookNotFoundException e){
            throw new BookNotFoundException();
        }catch ( UpdateFailedException e){
            throw new UpdateFailedException( "Failed to update book");
        }
    }

    //DELETE
    @RequestMapping(value = "/delete-book/{id}", method = RequestMethod.DELETE)
    public void deleteBook(@PathVariable long id) throws BookNotFoundException{
        try{
            bookService.deleteBookById(id);
        }catch( BookNotFoundException e){
            throw new BookNotFoundException();
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

    //Update book quantity in inventory
    @RequestMapping(value="/book/{id}", method = RequestMethod.PUT)
    public void updateBookQuantityInInventory(@PathVariable("id") Long id, Long quantity){
        try{
            bookService.updateBookQuantityInInventory(id, quantity);
        }catch(CustomException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book quantity can not be updated");
        } catch (BookNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
