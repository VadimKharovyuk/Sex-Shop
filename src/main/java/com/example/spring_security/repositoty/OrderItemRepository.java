package com.example.spring_security.repositoty;

import com.example.spring_security.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
    // Пример дополнительных методов, если необходимо
    List<OrderItem> findByOrderId(Long orderId); // Найти элементы заказа по идентификатору заказа

    List<OrderItem> findByProductId(Long productId); // Найти элементы заказа по идентификатору продукта
}
