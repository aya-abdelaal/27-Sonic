package com.example.controller;

import com.example.model.Cart;
import com.example.model.Product;
import com.example.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/cart")
public class CartController {
//The Dependency Injection Variables
    CartService cartService;
//The Constructor with the requried variables mapping the Dependency Injection.
    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    @PostMapping("/")
    public Cart addCart(@RequestBody Cart cart){
        return cartService.addCart(cart);
    }
    @GetMapping("/")
    public ArrayList<Cart> getCarts(){
        return cartService.getCarts();
    }
    @GetMapping("/{cartId}")
    public Cart getCartById(@PathVariable UUID cartId){

        try {
            return cartService.getCartById(cartId);
        }catch (IllegalArgumentException e) {
            return null;
        }
    }
    @PutMapping("/addProduct/{cartId}")
    public String addProductToCart(@PathVariable UUID cartId, @RequestBody Product product){
        cartService.addProductToCart(cartId, product);
        return "Product added successfully";
    }
    @DeleteMapping("/delete/{cartId}")
    public String deleteCartById(@PathVariable UUID cartId){
        try {
            cartService.deleteCartById(cartId);
            return "Cart deleted successfully";
        }catch (IllegalArgumentException e) {
            return e.getMessage();
        }

    }

}