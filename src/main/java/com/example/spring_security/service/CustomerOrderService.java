package com.example.spring_security.service;

import com.example.spring_security.entity.CustomerOrder;
import com.example.spring_security.repositoty.CustomerOrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerOrderService {

    private final CustomerOrderRepository orderRepository;

    // Создание нового заказа
    public CustomerOrder createOrder(CustomerOrder order) {
        return orderRepository.save(order);
    }

    // Получение всех заказов
    public List<CustomerOrder> getAllOrders() {
        return orderRepository.findAll();
    }

    // Получение заказа по ID
    public CustomerOrder getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }

    // Обновление существующего заказа
    public CustomerOrder updateOrder(Long id, CustomerOrder order) {
        if (orderRepository.existsById(id)) {
            CustomerOrder existingOrder = orderRepository.findById(id).get();
            existingOrder.setStatus(order.getStatus());
            existingOrder.setUser(order.getUser());
            existingOrder.setOrderItems(order.getOrderItems());
            return orderRepository.save(existingOrder);
        } else {
            throw new IllegalArgumentException("Order with ID " + id + " not found");
        }
    }

    // Удаление заказа
    public void deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Order with ID " + id + " not found");
        }
    }
}
