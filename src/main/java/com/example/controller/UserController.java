package com.example.controller;

import com.example.model.Order;
import com.example.model.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
//The Dependency Injection Variables
    UserService userService;
//The Constructor with the requried variables mapping the Dependency Injection.
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public User addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @GetMapping("/")
    public ArrayList<User> getUsers()
    {
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable UUID userId){
        return userService.getUserById(userId);
    }

    @GetMapping("/{userId}/orders")
    public List<Order> getOrdersByUserId(@PathVariable UUID userId){
        return userService.getOrdersByUserId(userId);
    }

    @PostMapping("/{userId}/checkout")
    public String addOrderToUser(@PathVariable UUID userId){
        return  null;
    }

    @PostMapping("/{userId}/removeOrder")
    public String removeOrderFromUser(@PathVariable UUID userId, @RequestParam UUID orderId){
         userService.removeOrderFromUser(userId, orderId);
         return "Success";
    }

    @DeleteMapping("/delete/{userId}")
    public String deleteUserById(@PathVariable UUID userId){
         userService.deleteUserById(userId);
         return "Success";
    }





}