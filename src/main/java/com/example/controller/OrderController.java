package com.example.controller;

import com.example.model.Order;
import com.example.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;


@RestController
@RequestMapping("/order")
public class OrderController {
//The Dependency Injection Variables
//The Constructor with the requried variables mapping the Dependency Injection.

    private final OrderService orderService; // Dependency Injection

    // Constructor-based Dependency Injection
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Add a new order
    @PostMapping("/")
    public void addOrder(@RequestBody Order order) {
        orderService.addOrder(order);

    }

    // Get an order by ID
    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable UUID orderId) {
        return orderService.getOrderById(orderId);
    }

    // Get all orders
    @GetMapping("/")
    public ArrayList<Order> getOrders() {
        return orderService.getOrders();
    }

    // Delete an order by ID
    @DeleteMapping("/delete/{orderId}")
    public String deleteOrderById(@PathVariable UUID orderId) {
        try {
            orderService.deleteOrderById(orderId);
            return "Order deleted successfully";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }
}
