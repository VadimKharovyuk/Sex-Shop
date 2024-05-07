package com.example.spring_security.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "delivery") // Имя таблицы в базе данных
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Уникальный идентификатор

    private String name; // Имя получателя
    private String phone; // Номер телефона
    private String email; // Email
    private String address; // Адрес доставки

//    @ManyToOne
//    @JoinColumn(name = "order_id") // Связь с заказом
//    private CustomerOrder order; // Заказ, связанный с этой доставкой

    private LocalDate shipmentDate; // Дата отправки
    private LocalDate receiptDate; // Дата получения

    @Enumerated(EnumType.STRING) // Хранение enum в виде строки
    private DeliveryType type; // Тип доставки
}
