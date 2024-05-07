package com.example.spring_security.controller;

import com.example.spring_security.entity.Product;
import com.example.spring_security.entity.ShoppingCart;
import com.example.spring_security.service.ProductService;
import com.example.spring_security.service.ShoppingCartService;
import jakarta.servlet.http.HttpSession;
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
    public String viewCart(HttpSession session, Model model) {
        ShoppingCart cart = shoppingCartService.getCartFromSession(session); // Получаем корзину из сессии
        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", shoppingCartService.getTotalPrice(cart)); // Общая стоимость корзины
        return "shopping_cart"; // Возвращаем имя шаблона для корзины
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId, @RequestParam int quantity, HttpSession session) {
        ShoppingCart cart = shoppingCartService.getCartFromSession(session); // Получаем корзину из сессии
        Product product = productService.getProductById(productId);

        if (product != null) {
            shoppingCartService.addItemToCart(cart, product, quantity);
        }

        return "redirect:/cart"; // Перенаправляем к корзине после добавления товара
    }

//    @GetMapping
//    public String viewCart(Model model, HttpSession session) {
//        ShoppingCart cart = getOrCreateCart(session); // Получаем корзину из сессии или создаем новую
//        model.addAttribute("cart", cart);
//        model.addAttribute("totalPrice", shoppingCartService.getTotalPrice(cart)); // Общая стоимость корзины
//        return "shopping_cart"; // Имя шаблона для отображения корзины
//    }

//    @PostMapping("/add")
//    public String addToCart(@RequestParam Long productId, @RequestParam int quantity, HttpSession session) {
//        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
//
//        if (cart == null) {
//            cart = shoppingCartService.createCart(); // Если корзина не была создана
//            session.setAttribute("cart", cart); // Сохраняем в сессии
//        }
//
//        Product product = productService.getProductById(productId);
//
//        if (product != null) {
//            shoppingCartService.addItemToCart(cart, product, quantity); // Добавляем товар в корзину
//        }
//
//        return "redirect:/cart"; // Перенаправляем на страницу корзины
//    }


    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long productId, HttpSession session) {
        ShoppingCart cart = getOrCreateCart(session);
        Product product = productService.getProductById(productId);

        if (product != null) {
            shoppingCartService.removeItemFromCart(cart, product);
        }

        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateCartItem(@RequestParam Long productId, @RequestParam int quantity, HttpSession session) {
        ShoppingCart cart = getOrCreateCart(session);
        Product product = productService.getProductById(productId);

        if (product != null) {
            shoppingCartService.updateItemQuantity(cart, product, quantity);
        }

        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clearCart(HttpSession session) {
        ShoppingCart cart = getOrCreateCart(session);
        shoppingCartService.clearCart(cart);
        return "redirect:/cart";
    }

    private ShoppingCart getOrCreateCart(HttpSession session) {
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");

        if (cart == null) {
            cart = shoppingCartService.createCart();
            session.setAttribute("cart", cart);
        }

        return cart;
    }
}
