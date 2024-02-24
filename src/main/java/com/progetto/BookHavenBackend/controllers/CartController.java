package com.progetto.BookHavenBackend.controllers;

import com.progetto.BookHavenBackend.entities.Book;
import com.progetto.BookHavenBackend.entities.Cart;
import com.progetto.BookHavenBackend.entities.CartItem;
import com.progetto.BookHavenBackend.services.BookService;
import com.progetto.BookHavenBackend.services.CartService;
import com.progetto.BookHavenBackend.support.common.ApiResponse;
import com.progetto.BookHavenBackend.support.exceptions.BookNotFoundException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v1/cart/")
public class CartController {

    @Autowired
    CartService cartService;

    @Autowired
    BookService bookService;

    //CREATE
    @PostMapping("/cartItem")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody @Valid Book book) throws BookNotFoundException { //, @RequestParam("token") String token
        BigDecimal finalPrice = null;
        if( !book.getDiscount().equals(0) ){
            finalPrice= book.getDiscountedPrice();
        }else{
            finalPrice = book.getPrice();
        }
        CartItem cartItem= new CartItem(
                new Date(), book, 1,finalPrice
        );
        try {
            //User user=

            cartService.addToCart(cartItem);
            return new ResponseEntity<>(new ApiResponse(true, "Added to Cart"), HttpStatus.OK);

        } catch (BookNotFoundException e) {
            throw new BookNotFoundException();
        }
    }
    //READ
    @GetMapping("/cart")
    public List<CartItem> getCart(){
       // List<CartItem> cartItems = cartService.getCart();
        return null;
    }

    @GetMapping("/cartIsWorking")
    public String isWorking(){
        return "Endpoint is working";
    }


    //DELETE - delete an item inside cart




    /*

    public CartDto listCartItems(User user) {
        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);

        List<CartItemDto> cartItems = new ArrayList<>();
        double totalCost = 0;
        for (Cart cart: cartList) {
            CartItemDto cartItemDto = new CartItemDto(cart);
            totalCost += cartItemDto.getQuantity() * cart.getProduct().getPrice();
            cartItems.add(cartItemDto);
        }

        CartDto cartDto = new CartDto();
        cartDto.setTotalCost(totalCost);
        cartDto.setCartItems(cartItems);
        return  cartDto;
    }

    public void deleteCartItem(Integer cartItemId, User user) {
        // the item id belongs to user

        Optional<Cart> optionalCart = cartRepository.findById(cartItemId);

        if (optionalCart.isEmpty()) {
            throw new CustomException("cart item id is invalid: " + cartItemId);
        }

        Cart cart = optionalCart.get();

        if (cart.getUser() != user) {
            throw  new CustomException("cart item does not belong to user: " +cartItemId);
        }

        cartRepository.delete(cart);


     */




}
