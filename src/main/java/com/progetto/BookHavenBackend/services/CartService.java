package com.progetto.BookHavenBackend.services;

import com.progetto.BookHavenBackend.entities.*;
import com.progetto.BookHavenBackend.repositories.*;
import com.progetto.BookHavenBackend.support.exceptions.BookNotFoundException;
import com.progetto.BookHavenBackend.support.exceptions.CustomException;
import com.progetto.BookHavenBackend.support.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    OrderBookRepository orderBookRepository;

    public Cart getPendingCart(String userId) throws UserNotFoundException {
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findById(userId));
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Cart pendingCart = cartRepository.findByUserIdAndCartStatus(userId, OrderStatus.PENDING);
            if (pendingCart == null) {
                pendingCart = new Cart();
                pendingCart.setUser(user); // Set the user for the new cart
                pendingCart.setTotalPrice(BigDecimal.ZERO);
                pendingCart.setCartStatus(OrderStatus.PENDING);
                pendingCart = cartRepository.save(pendingCart);
            }
            return pendingCart;
        } else {
            throw new UserNotFoundException();
        }
    }

    public Cart addBookToCart(Book book, String userId) throws UserNotFoundException, BookNotFoundException {
        User user = userRepository.findById(userId);
        Cart pendingCart =  getPendingCart(userId);
        if(book == null ){
            throw new BookNotFoundException("Book is required");
        }
        Optional<OrderBook> orderBookOptional = Optional.ofNullable(orderBookRepository.findByBookIdAndCartId(book.getId(), pendingCart.getId()));
        if( orderBookOptional.isPresent() ){
            throw new CustomException("Book is already present in cart");
        }else{
            OrderBook orderBook = new OrderBook(pendingCart, book, 1);
            orderBookRepository.save(orderBook);
            pendingCart.getOrderBooks().add(orderBook);
            pendingCart.setTotalPrice(pendingCart.getTotalPrice().add(orderBook.getBookFinalPrice()));
            cartRepository.save(pendingCart);
            return pendingCart;
        }
    }

    public Cart removeBookFromCart(Book book, String userId) throws BookNotFoundException, UserNotFoundException {
        User user = userRepository.findById(userId);
        Cart pendingCart =  getPendingCart(userId);
        if(book == null ){
            throw new BookNotFoundException("Book is required");
        }
        Optional<OrderBook> orderBookOptional = Optional.ofNullable(orderBookRepository.findByBookIdAndCartId(book.getId(), pendingCart.getId()));
        if( orderBookOptional.isPresent() ){
            OrderBook orderBook = orderBookOptional.get();
            if (orderBook.getQuantity() == 1) {
                orderBookRepository.delete(orderBook);
                pendingCart.getOrderBooks().remove(orderBook);
                pendingCart.setTotalPrice(pendingCart.getTotalPrice().subtract(orderBook.getBookFinalPrice()));
            }else{
                pendingCart.getOrderBooks().remove(orderBook);
                orderBook.setQuantity(orderBook.getQuantity()-1);
                orderBook.setFinalPrice(orderBook.getFinalPrice().multiply(BigDecimal.valueOf(orderBook.getQuantity())));
                orderBookRepository.save(orderBook);
                pendingCart.getOrderBooks().add(orderBook);
            }
            cartRepository.save(pendingCart);
            return pendingCart;
        }else{
            throw new CustomException("Book is not present in cart");
        }
    }



    /*

    public List<OrderBook> getPendingCartItems(String userId){
        User user = userRepository.findById(userId);
        Cart pendingCart = cartRepository.findByUserIdAndCartStatus(userId, OrderStatus.PENDING);
        if( pendingCart == null ){
            Cart newCart = new Cart();
            newCart.setId(newCart.getId());
            newCart.setUser(user);
            newCart.setTotalPrice(BigDecimal.ZERO);
            newCart.setCartStatus(OrderStatus.PENDING);
            newCart.setOrderBooks(new ArrayList<>());
            pendingCart =  cartRepository.save(newCart);
        }
        List<OrderBook> items= orderBookRepository.findAllByCartId(pendingCart.getId());
        if( items.isEmpty() || items== null){
            throw new CustomException("Cart is empty");
        }
        return items;
    }



    public OrderBook addToCart(String userId, Book book) throws BookNotFoundException {
        Cart pendingCart = cartRepository.findByUserIdAndCartStatus(userId, OrderStatus.PENDING);
        if (book == null){
            throw new BookNotFoundException("Book is required");
        }else{
            OrderBook orderBook = orderBookRepository.findByBookIdAndCartId(book.getId(), pendingCart.getId());
            if(orderBook != null){
                OrderBook newOB = new OrderBook(pendingCart, book, 1);
                orderBookRepository.save(newOB);
                pendingCart.getOrderBooks().add(newOB);
                cartRepository.save(pendingCart);
                return newOB;
            }else{
                throw new CustomException("Book already exist in pending cart");
            }
        }
    }

     */




    /*

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

     */



    /*
    public Cart checkout(String userId, Cart cart) {
        Cart cartCheckout = cartRepository.findByIdAndUserIdAndOrderStatus(cart.getId(), userId, OrderStatus.PENDING);

        return null;
    }

     */
}//CartService
