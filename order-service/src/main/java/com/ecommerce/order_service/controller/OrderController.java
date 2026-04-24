package com.ecommerce.order_service.controller;

import com.ecommerce.order_service.model.Order;
import com.ecommerce.order_service.model.dtos.OrderStatusRequest;
import com.ecommerce.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @PostMapping("/checkout/{userId}")
    public Order checkout(@PathVariable String userId) {
        return service.checkout(userId);
    }

    // Get orders by user
    @GetMapping("/user/{userId}")
    public List<Order> getOrders(@PathVariable String userId) {
        return service.getOrders(userId);
    }

    // Get order by ID
    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable String orderId) {
        return service.getOrderById(orderId);
    }

    // Update order status (ADMIN)
    @PutMapping("/{orderId}/status")
    public Order updateOrderStatus(@PathVariable String orderId,
                                   @RequestBody OrderStatusRequest request) {
        return service.updateStatus(orderId, request.getStatus());
    }

    // Cancel order
    @DeleteMapping("/{orderId}")
    public String cancelOrder(@PathVariable String orderId) {
        service.cancelOrder(orderId);
        return "Order cancelled successfully";
    }
}