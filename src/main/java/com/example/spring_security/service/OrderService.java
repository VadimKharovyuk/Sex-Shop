package com.example.spring_security.service;

import com.example.spring_security.entity.Order;
import com.example.spring_security.repositoty.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;


    // Создание нового заказа
    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    // Обновление заказа
    public Order updateOrder(Long id, Order order) {
        if (orderRepository.existsById(id)) {
            order.setId(id);
            return orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Order with ID " + id + " not found");
        }
    }

    // Получение заказа по ID
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    // Получение заказов пользователя
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findAllByUser_Id(userId);
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
