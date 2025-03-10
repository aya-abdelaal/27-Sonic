package com.example.service;

import com.example.model.Cart;
import com.example.model.Order;
import com.example.model.Product;
import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@SuppressWarnings("rawtypes")
public class UserService extends MainService<User>{
    //The Dependency Injection Variables
    UserRepository userRepository;
    CartService cartService;

    ProductService productService;


    //The Constructor with the requried variables mapping the Dependency Injection.
    @Autowired
    public  UserService (UserRepository userRepository, CartService cartService, ProductService productService){
        this.userRepository = userRepository;
        this.cartService = cartService;
        this.productService = productService;
    }

    public User addUser(User user){
        return userRepository.addUser(user);

    }

    public ArrayList<User> getUsers(){
        return  userRepository.getUsers();
    }

    public User getUserById(UUID userId){
        return  userRepository.getUserById(userId);
    }

    public List<Order> getOrdersByUserId(UUID userId){
        return userRepository.getOrdersByUserId(userId);
    }

    public void removeOrderFromUser(UUID userId, UUID orderId){
        userRepository.removeOrderFromUser(userId, orderId);
    }

    public void deleteUserById(UUID userId){
        userRepository.deleteUserById(userId);
    }


    public void addOrderToUser(UUID userId){
        //get the cart
        Cart userCart = cartService.getCartByUserId(userId);

        //create order
        double totalPrice = 0;
        List<Product> products = userCart.getProducts();
        for(Product p: products){
            totalPrice += p.getPrice();

        }

        Order newOrder = new Order(userId,totalPrice, products);

        //save order to user
        userRepository.addOrderToUser(userId, newOrder);

        //empty cart
        for(Product p: userCart.getProducts()){
            cartService.deleteProductFromCart(userCart.getId(), p);
        }



    }

    public void emptyCart(UUID userId){
        Cart userCart = cartService.getCartByUserId(userId);

        for(Product p: userCart.getProducts()){
            cartService.deleteProductFromCart(userCart.getId(), p);
        }
    }

    public void addProductToCart(UUID userId, UUID pId){
        Cart userCart = cartService.getCartByUserId(userId);
        cartService.addProductToCart(userCart.getId(), productService.getProductById(pId));
    }

    public void deleteProductFromCart(UUID userId, UUID pId){
        Cart userCart = cartService.getCartByUserId(userId);
        if(userCart == null){
            throw new IllegalArgumentException("Cart not found");
        }
        Product p =  productService.getProductById(pId);
        if(p == null){
            throw new IllegalArgumentException("Product not found");
        }
        cartService.deleteProductFromCart(userCart.getId(), p);
    }
}
