package com.progetto.BookHavenBackend.services;

import com.progetto.BookHavenBackend.entities.*;
import com.progetto.BookHavenBackend.repositories.BookRepository;
import com.progetto.BookHavenBackend.repositories.CartItemRepository;
import com.progetto.BookHavenBackend.repositories.CartRepository;
import com.progetto.BookHavenBackend.repositories.UserRepository;
import com.progetto.BookHavenBackend.support.exceptions.BookNotFoundException;
import com.progetto.BookHavenBackend.support.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CartItemRepository cartItemRepository;


    @Transactional(readOnly = true)
    public Cart getCart(String userId){
        Cart cart = cartRepository.findByUserIdAndOrderStatus(userId, OrderStatus.PENDING);
        if (cart != null) {
            BigDecimal totalPrice = calculateTotalPrice(cart.getCartItems());
            cart.setTotalPrice(totalPrice);
            return cart;
        } else {
            throw new CustomException("Cart not found");
        }
    }//getCart

    private BigDecimal calculateTotalPrice(List<CartItem> cartItems) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for(CartItem cartItem : cartItems){
            totalPrice = totalPrice.add(cartItem.getFinalPrice().multiply(BigDecimal.valueOf(cartItem.getOrderQty())));
        }
        return totalPrice;
    }

    @Transactional(readOnly = true)
    public List<CartItem> getCartItems(String userId) {
        Cart cart = cartRepository.findByUserIdAndOrderStatus(userId, OrderStatus.PENDING);
        if (cart != null) {
            return cart.getCartItems();
        } else {
            throw new CustomException("Cart not found");
        }
    }  //getCartItems

    @Transactional(readOnly = false)
    public Cart addBookToCart(Book book, String userId) throws CustomException, BookNotFoundException {
        User user = userRepository.findById(userId);
        Cart returnCart= new Cart();
        if (book == null ) {
            throw new CustomException("Book is required.");
        }else{
            Cart pendingCart = cartRepository.findByUserIdAndOrderStatus(userId, OrderStatus.PENDING);
            if(pendingCart==null){
                pendingCart = new Cart();
                pendingCart.setUser(user);
            }

            Optional<CartItem> cartItemOptional = cartItemRepository.findByUserIdAndBookIdAndCartId(user.getId(), book.getId(), pendingCart.getId());
            if (cartItemOptional.isPresent()) {
                   throw new CustomException("Book is already in cart");
            } else {
                Optional<Book> bookOptional = bookRepository.findById(book.getId());
                if(bookOptional.isPresent()){
                    Book bookToAdd= bookOptional.get();
                    CartItem newCartItem = new CartItem();
                    newCartItem.setBook(bookToAdd);
                    newCartItem.setUser(user);
                    newCartItem.setCart(pendingCart);
                    newCartItem.setFinalPrice(newCartItem.getFinalPrice());
                    newCartItem.setOrderQty(1);
                    CartItem newItem= cartItemRepository.save(newCartItem);
                    pendingCart.getCartItems().add(newItem);
                    pendingCart.setTotalPrice(pendingCart.getTotalPrice().add(newCartItem.getFinalPrice()));
                    returnCart=  cartRepository.save(pendingCart);
                }else {
                    throw new BookNotFoundException();
                }
            }
        }
        return returnCart;
    }//addBookToCart

    @Transactional(readOnly = false)
    public Cart removeBookFromCart(Book book, String userId) throws CustomException, BookNotFoundException {
        Cart returnCart = new Cart();
        if (book == null ) {
            throw new CustomException("Book is required.");
        }else {
            User user = userRepository.findById(userId);

            Optional<Cart> cartOptional = Optional.ofNullable(cartRepository.findByUserIdAndOrderStatus(userId, OrderStatus.PENDING));
            if (!cartOptional.isPresent()) {
                throw new CustomException("Cart doesn't exist.");
            } else {
                Cart pendingCart = cartOptional.get();
                Optional<CartItem> cartItemOptional = cartItemRepository.findByUserIdAndBookIdAndCartId(user.getId(), book.getId(), pendingCart.getId());
                if (!cartItemOptional.isPresent()) {
                    throw new CustomException("Book is not found in cart");
                } else {
                    CartItem bookToRemove = cartItemOptional.get();
                    cartItemRepository.delete(bookToRemove);
                    pendingCart.getCartItems().remove(bookToRemove);
                    pendingCart.setTotalPrice(pendingCart.getTotalPrice().subtract(bookToRemove.getFinalPrice()));
                    cartRepository.save(pendingCart);
                    returnCart = pendingCart;
                }
            }
        }
        return returnCart;
    }//removeBookFromCart

    public void incrementBookQtyInCart(String userId, Book book) throws BookNotFoundException {
        Cart cart = cartRepository.findByUserIdAndOrderStatus(userId, OrderStatus.PENDING);
        Optional<CartItem> cartItemOptional = cartItemRepository.findByUserIdAndBookIdAndCartId(userId, book.getId(), cart.getId());

        if (cart != null){
            CartItem updatedItem = new CartItem();
            updatedItem.setId(cartItemOptional.get().getId());
            updatedItem.setBook(book);
            updatedItem.setCart(cart);
            updatedItem.setUser( userRepository.findById(userId));
            updatedItem.setFinalPrice(cartItemOptional.get().getFinalPrice());
            updatedItem.setOrderQty(cartItemOptional.get().getOrderQty()+1);
            cart.getCartItems().add(updatedItem);
            cart.setTotalPrice(cart.getTotalPrice().add(updatedItem.getFinalPrice()));
            cartItemRepository.save(updatedItem);
            cartRepository.save(cart);
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error in incrementing book quantity in cart");
        }
    }//incrementBookQtyInCart


    public void decrementBookQtyInCart(String userId, Book book) throws BookNotFoundException {
        Cart cart = cartRepository.findByUserIdAndOrderStatus(userId, OrderStatus.PENDING);
        Optional<CartItem> cartItemOptional = cartItemRepository.findByUserIdAndBookIdAndCartId(userId, book.getId(), cart.getId());

        if (cart != null){
            CartItem updatedItem = new CartItem();
            updatedItem.setId(cartItemOptional.get().getId());
            updatedItem.setBook(book);
            updatedItem.setUser( userRepository.findById(userId));
            updatedItem.setCart(cart);
            updatedItem.setFinalPrice(cartItemOptional.get().getFinalPrice());
            if( cartItemOptional.get().getOrderQty()-1 == 0 ){
                removeBookFromCart(book, userId);
            }else{
                updatedItem.setOrderQty(cartItemOptional.get().getOrderQty()-1);
                cart.getCartItems().add(updatedItem);
                cart.setTotalPrice(cart.getTotalPrice().subtract(updatedItem.getFinalPrice()));
                cartItemRepository.save(updatedItem);
                cartRepository.save(cart);
            }
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error in decrementing book quantity in cart");
        }
    }//decrementBookQtyInCart

    public Cart checkout(String userId, Cart cart) {
        Cart cartCheckout = cartRepository.findByIdAndUserIdAndOrderStatus(cart.getId(), userId, OrderStatus.PENDING);

        return null;
    }
}//CartService
