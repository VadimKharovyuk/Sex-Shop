package com.example.spring_security.entity;

import com.example.spring_security.entity.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Уникальный идентификатор

    private String name; // Название товара
    private String description; // Описание товара
    private double price; // Цена товара
    private String image; // Путь к изображению товара

    @ManyToOne(fetch = FetchType.LAZY) // Обеспечиваем ленивую загрузку
    @JoinColumn(name = "category_id") // Связь с категорией
    private Category category; // Связь с категорией

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ShoppingCartItem> cartItems; // Список связанных элементов корзины



}





