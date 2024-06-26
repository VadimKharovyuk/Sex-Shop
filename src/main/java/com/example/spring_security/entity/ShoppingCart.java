package com.example.spring_security.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shopping_carts")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Уникальный идентификатор

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ShoppingCartItem> items = new ArrayList<>(); // Инициализируем пустым списком

    @OneToOne
    @JoinColumn(name = "user_id", nullable = true)
    private UserEntity user; // Ссылка на пользователя, если корзина принадлежит пользователю


    public void clearCart() {
        this.items.clear(); // Очищаем список товаров в корзине

    }
}
