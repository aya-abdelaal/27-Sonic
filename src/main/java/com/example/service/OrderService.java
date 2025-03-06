package com.example.service;

import com.example.model.Order;
import com.example.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
@SuppressWarnings("rawtypes")
public class OrderService extends MainService<Order> {
//The Dependency Injection Variables

//The Constructor with the requried variables mapping the Dependency Injection.

    private final OrderRepository orderRepository; // Dependency Injection

    // Constructor-based Dependency Injection
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // Add an order
    public void addOrder(Order order) {
        orderRepository.addOrder(order);
    }

    // Get all orders
    public ArrayList<Order> getOrders() {
        return orderRepository.getOrders();
    }

    // Get an order by ID
    public Order getOrderById(UUID orderId) {
        Order order = orderRepository.getOrderById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found with ID: " + orderId);
        }
        return order;
    }

    // Delete an order by ID
    public void deleteOrderById(UUID orderId) {
        Order order = orderRepository.getOrderById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found with ID: " + orderId);
        }
        orderRepository.deleteOrderById(orderId);
    }
}