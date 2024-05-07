package com.example.spring_security.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    private LocalDate shipmentDate; // Дата отправки
    private LocalDate receiptDate; // Дата получения

    @Enumerated(EnumType.STRING) // Хранение enum в виде строки
    private DeliveryType type; // Тип доставки

    @ElementCollection // Указываем, что items — это коллекция простых элементов или встраиваемых объектов
    private List<ShoppingCartItem> items = new ArrayList<>(); // Инициализация списка по умолчанию

    public void setItems(List<ShoppingCartItem> items) {
        // Убеждаемся, что список инициализирован
        if (this.items == null) {
            this.items = new ArrayList<>(); // Инициализируем, если нужно
        }

        this.items.clear(); // Очищаем список перед установкой новых элементов
        this.items.addAll(items); // Добавляем все новые элементы
    }

    public List<ShoppingCartItem> getItems() {
        return new ArrayList<>(this.items); // Возвращаем копию списка для безопасности
    }
}
