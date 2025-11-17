package com.abefas;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Create or update an order with order items (POST /orders)
    @PostMapping
    public Order createOrUpdateOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    // Get all orders (GET /orders)
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    // Get a single order by ID (GET /orders/{id})
    @GetMapping("/{id}")
    public Optional<Order> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    // Delete an order by ID (DELETE /orders/{id})
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

    // Add a new OrderItem to existing order (POST /orders/{orderId}/items)
    @PostMapping("/{orderId}/items")
    public void addOrderItem(@PathVariable Long orderId, @RequestBody OrderItem orderItem) {
        Optional<Order> optionalOrder = orderService.getOrderById(orderId);
        if (optionalOrder.isPresent()) {
            orderService.addOrderItem(optionalOrder.get().getId(), orderItem);
        } else {
            throw new RuntimeException("Order not found");
        }
    }

    // Update an existing OrderItem (PUT /orders/{orderId}/items/{itemId})
    @PutMapping("/{orderId}/items/{itemId}")
    public void updateOrderItem(@PathVariable Long orderId, @PathVariable Long itemId, @RequestBody OrderItem updatedItem) {
        Optional<Order> optionalOrder = orderService.getOrderById(orderId);
        if (optionalOrder.isPresent()) {
            // Ensure correct item ID in updated object
            updatedItem.setId(itemId);
            orderService.updateOrderItem(optionalOrder.get(), updatedItem);
        } else {
            throw new RuntimeException("Order not found");
        }
    }

    // Remove an OrderItem from an order (DELETE /orders/{orderId}/items/{itemId})
    @DeleteMapping("/{orderId}/items/{itemId}")
    public void removeOrderItem(@PathVariable Long orderId, @PathVariable Long itemId) {
        Optional<Order> optionalOrder = orderService.getOrderById(orderId);
        if (optionalOrder.isPresent()) {
            orderService.removeOrderItem(optionalOrder.get(), itemId);
        } else {
            throw new RuntimeException("Order not found");
        }
    }
}
