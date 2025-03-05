package com.example.service;

import com.example.model.Order;
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


    //The Constructor with the requried variables mapping the Dependency Injection.
    @Autowired
    public  UserService (UserRepository userRepository){
        this.userRepository = userRepository;
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

}
