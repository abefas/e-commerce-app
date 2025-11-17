package com.abefas;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public Order saveOrder(Order order) {
        if (order.getOrderItems() != null) {
            for (OrderItem item : order.getOrderItems()) {
                item.setOrder(order);
            }
        }
        // Verify or recalculate total price before saving
        order.setTotalPrice(calculateTotalPrice(order.getOrderItems()));
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    // Remove an OrderItem from an Order by OrderItem ID
    public void removeOrderItem(Order order, Long orderItemId) {
        order.getOrderItems().removeIf(item -> item.getId().equals(orderItemId));
        order.setTotalPrice(calculateTotalPrice(order.getOrderItems()));
        orderRepository.save(order);
    }

    // Update an existing OrderItem in an Order
    public void updateOrderItem(Order order, OrderItem updatedItem) {
        List<OrderItem> items = order.getOrderItems();
        items.replaceAll(item -> item.getId().equals(updatedItem.getId()) ? updatedItem : item);
        updatedItem.setOrder(order);
        order.setTotalPrice(calculateTotalPrice(items));
        orderRepository.save(order);
    }

    @Transactional
    public void addOrderItem(Long orderId, OrderItem orderItem) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Long productId = orderItem.getProduct().getId();
        Product managedProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        orderItem.setProduct(managedProduct);
        orderItem.setOrder(order);
        orderItem.setPriceAtPurchase(managedProduct.getPrice());

        if (order.getOrderItems() == null) {
            order.setOrderItems(new ArrayList<>());
        }

        order.getOrderItems().add(orderItem);

        // Optionally recalc total price here
        order.setTotalPrice(calculateTotalPrice(order.getOrderItems()));

        orderRepository.save(order);
    }


    // Calculate total price from list of OrderItems
    public double calculateTotalPrice(List<OrderItem> orderItems) {
        if (orderItems == null || orderItems.isEmpty()) {
            return 0.0;
        }
        return orderItems.stream()
                .mapToDouble(item -> item.getPriceAtPurchase() * item.getQuantity())
                .sum();
    }
}
