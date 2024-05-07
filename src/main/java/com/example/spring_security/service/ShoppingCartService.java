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
        item.setCart(cart);
        item.setProduct(product);
        item.setQuantity(quantity);
        return shoppingCartItemRepository.save(item);
    }

    public void removeItemFromCart(ShoppingCart cart, Product product) {
        ShoppingCartItem item = cart.getItems().stream()
                .filter(cartItem -> cartItem.getProduct().equals(product))
                .findFirst().orElse(null);

        if (item != null) {
            shoppingCartItemRepository.delete(item);
        }
    }

    public void clearCart(ShoppingCart cart) {
        shoppingCartItemRepository.deleteAll(cart.getItems());
    }
}
