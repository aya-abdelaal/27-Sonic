package com.example.service;

import com.example.model.Cart;
import com.example.model.Order;
import com.example.model.Product;
import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@SuppressWarnings("rawtypes")
public class UserService extends MainService<User>{
    private final Cart cart;
    private final User user;
    //The Dependency Injection Variables
    UserRepository userRepository;
    CartService cartService;

    ProductService productService;


    //The Constructor with the requried variables mapping the Dependency Injection.
    @Autowired
    public  UserService (UserRepository userRepository, CartService cartService, ProductService productService, Cart cart, User user){
        this.userRepository = userRepository;
        this.cartService = cartService;
        this.productService = productService;
        this.cart = cart;
        this.user = user;
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
        //check order exists
        List<Order> orders = getOrdersByUserId(userId);
        for(Order o:orders){
            if(o.getId().equals(orderId)){
                userRepository.removeOrderFromUser(userId, orderId);
                return;
            }
        }

        throw new IllegalArgumentException("Order not found");

    }

    public void deleteUserById(UUID userId){


        if(userRepository.getUserById(userId) == null){

            throw new IllegalArgumentException("User not found");
        }
        userRepository.deleteUserById(userId);
    }


    public void addOrderToUser(UUID userId){
        //get the cart
        Cart userCart = cartService.getCartByUserId(userId);
        System.out.println(userCart);
        if(cart == null){
            throw new IllegalArgumentException("Cart not found");
        }

        if(userCart.getProducts().size() == 0){
            throw new IllegalArgumentException("Cart is empty");
        }

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

    public String addProductToCart(UUID userId, UUID pId){
        Cart userCart = cartService.getCartByUserId(userId);
        User user = userRepository.getUserById(userId);
        if(user ==null){
            return "User not found";
        }
        Product product = productService.getProductById(pId);
        if(product==null){
            return "Product not found";
        }
        if(userCart == null) {
            userCart = createCart(userId);
        }
        cartService.addProductToCart(userCart.getId(), product);
        return "Product added to cart";
    }

    public void deleteProductFromCart(UUID userId, UUID pId){
        Cart userCart = cartService.getCartByUserId(userId);
        if(userCart == null){
            throw new IllegalArgumentException("Cart is empty");
        }
        Product p =  productService.getProductById(pId);
        if(p == null){
            throw new IllegalArgumentException("Product not found");
        }
        cartService.deleteProductFromCart(userCart.getId(), p);
    }

    public Cart createCart(UUID userId){
        Cart userCart = new Cart(userId,new ArrayList<Product>());
        cartService.addCart(userCart);
        return userCart;
    }
}
