package com.example.spring_security.repositoty;

import com.example.spring_security.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findAllByUser_Id(Long userId); // Найти все заказы по ID пользователя
    List<Order> findByUserId(Long userId); // Найти заказы по ID пользователя
    List<Order> findByStatus(String status); // Найти заказы по статусу
}
