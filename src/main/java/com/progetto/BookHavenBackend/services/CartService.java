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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    OrderBookRepository orderBookRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentInformationRepository paymentInformationRepository;

    @Transactional(readOnly = true)
    public Cart getPendingCart(String userId) throws UserNotFoundException {
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findById(userId));
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Cart pendingCart = cartRepository.findByUserIdAndCartStatus(userId, OrderStatus.PENDING);
            if (pendingCart == null) {
                pendingCart = new Cart();
                pendingCart.setUser(user);
                pendingCart.setTotalPrice(BigDecimal.ZERO);
                pendingCart.setCartStatus(OrderStatus.PENDING);
                pendingCart = cartRepository.save(pendingCart);
            }else{
                pendingCart.setTotalPrice(calculateTotalPrice(pendingCart.getOrderBooks()));
                pendingCart = cartRepository.save(pendingCart);
            }

            return pendingCart;
        } else {
            throw new UserNotFoundException();
        }
    }

    private BigDecimal calculateTotalPrice(List<OrderBook> cartItems){
        BigDecimal totalPrice = BigDecimal.ZERO;
        for(OrderBook cartItem : cartItems){
            totalPrice = totalPrice.add(cartItem.getBook().getDiscountedPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));
        }
            return totalPrice;
    }


    @Transactional(readOnly = false)
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
            pendingCart.setTotalPrice(calculateTotalPrice(pendingCart.getOrderBooks()));
            cartRepository.save(pendingCart);
            return pendingCart;
        }
    }

    @Transactional(readOnly = false)
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
                pendingCart.setTotalPrice(pendingCart.getTotalPrice().subtract(orderBook.getBook().getDiscountedPrice()));
            }else{
                pendingCart.getOrderBooks().remove(orderBook);
                orderBook.setQuantity(orderBook.getQuantity()-1);
                orderBook.setFinalPrice(orderBook.getBook().getDiscountedPrice().multiply(BigDecimal.valueOf(orderBook.getQuantity())));
                orderBook= orderBookRepository.save(orderBook);
                pendingCart.getOrderBooks().add(orderBook);
                pendingCart.setTotalPrice(pendingCart.getTotalPrice().subtract(orderBook.getBook().getDiscountedPrice()));
            }
            cartRepository.save(pendingCart);
            return pendingCart;
        }else{
            throw new CustomException("Book is not present in cart");
        }
    }

    @Transactional(readOnly = false)
    public OrderBook incrementBookQtyInCart(String userId, Book book) {
        Cart pendingCart = cartRepository.findByUserIdAndCartStatus(userId, OrderStatus.PENDING);
        OrderBook orderBook = orderBookRepository.findByBookIdAndCartId(book.getId(), pendingCart.getId());
        if (orderBook == null){
            throw new CustomException("Book is not present in cart");
        }else{
            pendingCart.getOrderBooks().remove(orderBook);
            orderBook.setQuantity(orderBook.getQuantity()+1);
            orderBook.setFinalPrice(orderBook.getBook().getDiscountedPrice().multiply(BigDecimal.valueOf(orderBook.getQuantity())));
            orderBook = orderBookRepository.save(orderBook);
            pendingCart.getOrderBooks().add(orderBook);
            pendingCart.setTotalPrice(calculateTotalPrice(pendingCart.getOrderBooks()));
            cartRepository.save(pendingCart);
            return orderBook;
        }
    }

    public void decrementBookQtyInCart(String userId, Book book) {
    }

    @Transactional(readOnly = false)
    public void resetCart(String userId, Long cartId) {
        Optional<Cart> pendingCartOptional = cartRepository.findById(cartId);
        if( pendingCartOptional.isPresent() ){
            for( OrderBook orderBook : orderBookRepository.findAllByCartId(cartId)){
                orderBookRepository.delete(orderBook);
            }
            Cart cart = pendingCartOptional.get();
            cart.setTotalPrice(BigDecimal.ZERO);
            cart.setOrderBooks(new ArrayList<>());
            cartRepository.save(cart);
        }else{
            throw new CustomException("Error in resetting cart");
        }
    }

    @Transactional(readOnly = false )
    public Order checkout(String userId, OrderForm orderForm) {
        Cart pendingCart = cartRepository.findByUserIdAndCartStatus(userId, OrderStatus.PENDING);
        Optional<PaymentInformation> paymentInformationOptional = paymentInformationRepository.findById(orderForm.getCardId());
        if(paymentInformationOptional.isPresent()){
            if( validPaymentMethod(paymentInformationOptional.get())){
                pendingCart.setCartStatus(OrderStatus.PAID);
                pendingCart= cartRepository.save(pendingCart);
            }else{
                throw new CustomException("Payment method not valid");
            }
        }else{
            throw new CustomException("Payment method is required");
        }

        if( orderForm.getRecipientName()!=null && orderForm.getShippingAddress() != null){
            Order newOrder = new Order();
            newOrder.setCart(pendingCart);
            newOrder.setDateTime(LocalDateTime.now());
            newOrder.setOrderStatus(OrderStatus.PROCESSING);
            newOrder.setRecipientName(orderForm.getRecipientName());
            newOrder.setShippingAddress(orderForm.getShippingAddress());
            newOrder.setPaymentInformation(newOrder.getPaymentInformation());
            orderRepository.save(newOrder);
            return newOrder;
        } else {
            throw new CustomException("Error in checking out.");
        }
    }

    private boolean validPaymentMethod(PaymentInformation paymentInformation) {
        return LocalDate.now().isBefore(paymentInformation.getExpirationDate());
    }

}//CartService
