package com.example.spring_security.service;

import com.example.spring_security.entity.Product;
import com.example.spring_security.entity.ShoppingCart;
import com.example.spring_security.entity.ShoppingCartItem;
import com.example.spring_security.repositoty.ShoppingCartItemRepository;
import com.example.spring_security.repositoty.ShoppingCartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartItemRepository shoppingCartItemRepository;

    public ShoppingCart createCart() {
        ShoppingCart cart = new ShoppingCart();
        return shoppingCartRepository.save(cart);
    }

    public ShoppingCart getCartById(Long id) {
        return shoppingCartRepository.findById(id).orElse(null);
    }

    public List<ShoppingCartItem> getItemsInCart(ShoppingCart cart) {
        return cart.getItems();
    }

    public ShoppingCartItem addItemToCart(ShoppingCart cart, Product product, int quantity) {
        ShoppingCartItem item = new ShoppingCartItem();
        item.setCart(cart); // Убедитесь, что ссылка на корзину установлена
        item.setProduct(product); // Убедитесь, что ссылка на продукт установлена
        item.setQuantity(quantity);

        cart.getItems().add(item); // Добавляем элемент в список элементов корзины

        shoppingCartItemRepository.save(item); // Сохраняем элемент в базе данных
        shoppingCartRepository.save(cart); // Обновляем корзину в базе данных, если нужно

        return item;
    }


    public void removeItemFromCart(ShoppingCart cart, Product product) {
        ShoppingCartItem item = cart.getItems().stream()
                .filter(cartItem -> cartItem.getProduct().equals(product))
                .findFirst().orElse(null);

        if (item != null) {
            shoppingCartItemRepository.delete(item);
        }
    }

    public void updateItemQuantity(ShoppingCart cart, Product product, int quantity) {
        ShoppingCartItem item = cart.getItems().stream()
                .filter(cartItem -> cartItem.getProduct().equals(product))
                .findFirst().orElse(null);

        if (item != null) {
            if (quantity > 0) {
                item.setQuantity(quantity);
                shoppingCartItemRepository.save(item);
            } else {
                removeItemFromCart(cart, product);
            }
        }
    }

    public double getTotalPrice(ShoppingCart cart) {
        return cart.getItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }

    public void clearCart(ShoppingCart cart) {
        shoppingCartItemRepository.deleteAll(cart.getItems());
        cart.getItems().clear();
    }

    public void deleteCart(ShoppingCart cart) {
        shoppingCartRepository.delete(cart);
    }
}
