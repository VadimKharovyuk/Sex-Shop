package com.example.spring_security.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "product_id") // Ссылка на продукт
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id") // Ссылка на заказ
    private CustomerOrder order;
}
