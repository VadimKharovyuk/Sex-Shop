package com.example.spring_security.controller;

import com.example.spring_security.entity.Product;
import com.example.spring_security.entity.ShoppingCart;
import com.example.spring_security.service.ProductService;
import com.example.spring_security.service.ShoppingCartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
@AllArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final ProductService productService;

    @GetMapping
    public String viewCart(Model model) {
        ShoppingCart cart = getOrCreateCart(); // Получаем или создаем новую корзину
        model.addAttribute("cart", cart);
        return "shopping_cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId, @RequestParam int quantity) {
        ShoppingCart cart = getOrCreateCart(); // Получаем или создаем новую корзину
        Product product = productService.getProductById(productId);

        if (product != null) {
            shoppingCartService.addItemToCart(cart, product, quantity);
        }

        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long productId) {
        ShoppingCart cart = getOrCreateCart();
        Product product = productService.getProductById(productId);

        if (product != null) {
            shoppingCartService.removeItemFromCart(cart, product);
        }

        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clearCart() {
        ShoppingCart cart = getOrCreateCart();
        shoppingCartService.clearCart(cart);
        return "redirect:/cart";
    }

    private ShoppingCart getOrCreateCart() {
        // Метод для получения или создания новой корзины
        // В этом примере мы создаем новую корзину
        // Если ваша система позволяет сохранять корзину для пользователя,
        // вы можете извлечь корзину по идентификатору пользователя или по сессии
        return shoppingCartService.createCart();
    }
}
