package com.example.spring_security.service;

import com.example.spring_security.entity.OrderItem;

import com.example.spring_security.repositoty.OrderItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public OrderItem getOrderItemById(Long id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order item not found"));
    }

    public OrderItem updateOrderItem(Long id, OrderItem orderItem) {
        if (orderItemRepository.existsById(id)) {
            OrderItem existingOrderItem = orderItemRepository.findById(id).get();
            existingOrderItem.setQuantity(orderItem.getQuantity());
            existingOrderItem.setProduct(orderItem.getProduct());
            existingOrderItem.setOrder(orderItem.getOrder());
            return orderItemRepository.save(existingOrderItem);
        } else {
            throw new IllegalArgumentException("Order item with ID " + id + " not found");
        }
    }


    public void deleteOrderItem(Long id) {
        if (orderItemRepository.existsById(id)) {
            orderItemRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Order item with ID " + id + " not found");
        }
    }
}
