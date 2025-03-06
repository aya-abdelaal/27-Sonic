package com.example.repository;

import com.example.model.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
@SuppressWarnings("rawtypes")
public class OrderRepository extends MainRepository<Order> {
    public OrderRepository() {
    }

    @Override
    protected String getDataPath() {
        return "src/main/java/com/example/data/orders.json"; // Path to the JSON file
    }

    @Override
    protected Class<Order[]> getArrayType() {
        return Order[].class; // JSON deserialization type
    }

    // Add an Order to the JSON file
    public void addOrder(Order order) {
        save(order);
    }

    // Get all Orders from the JSON file
    public ArrayList<Order> getOrders() {
        return findAll();
    }

    // Get a specific Order by ID
    public Order getOrderById(UUID orderId) {
        return findAll().stream()
                .filter(order -> order.getId().equals(orderId))
                .findFirst()
                .orElse(null);
    }

    // Delete a specific Order by ID
    public void deleteOrderById(UUID orderId) {
        ArrayList<Order> orders = findAll();
        orders.removeIf(order -> order.getId().equals(orderId)); // Remove matching order
        overrideData(orders); // Save the updated list back to JSON
    }

}
