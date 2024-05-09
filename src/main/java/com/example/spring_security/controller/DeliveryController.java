package com.example.spring_security.controller;

import com.example.spring_security.entity.Delivery;
import com.example.spring_security.entity.ShoppingCart;
import com.example.spring_security.service.DeliveryService;
import com.example.spring_security.service.ShoppingCartService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/deliveries")
@AllArgsConstructor
public class DeliveryController {
    private final ShoppingCartService shoppingCartService;
    private final DeliveryService deliveryService;

    @GetMapping("/create")
    public String createDeliveryForm(HttpSession session, Model model) {
        ShoppingCart cart = shoppingCartService.getCartFromSession(session);

        model.addAttribute("delivery", new Delivery()); // Объект для новой доставки
        model.addAttribute("cart", cart); // Передаем корзину в шаблон

        return "create_delivery"; // Шаблон для создания новой доставки
    }

//    @PostMapping("/createFromCart")
//    public String createDeliveryFromCart(HttpSession session, @RequestParam("cartItems") List<Long> productIds) {
//        ShoppingCart cart = shoppingCartService.getCartFromSession(session);
//
//        if (cart != null) {
//            deliveryService.createDeliveryFromCart(cart, productIds); // Передача списка напрямую
//            shoppingCartService.clearCart(cart); // Очистка корзины после оформления доставки
//        }
//
//        return "redirect:/deliveries"; // Перенаправление к списку доставок
//    }


//    @PostMapping("/createFromCart")
//    public String createDeliveryFromCart(HttpSession session, Model model, @RequestParam("productIds") List<Long> productIds) {
//        ShoppingCart cart = shoppingCartService.getCartFromSession(session); // Получаем корзину из сессии
//        if (cart == null || cart.getItems().isEmpty()) {
//            return "redirect:/cart"; // Если корзина пуста, перенаправляем обратно
//        }
//
//        // Создаем новую доставку, используя корзину и переданные идентификаторы продуктов
//        Delivery newDelivery = deliveryService.createDeliveryFromCart(cart, productIds);
//        model.addAttribute("delivery", newDelivery); // Передаем созданную доставку в модель
//
//        // Очищаем корзину после создания доставки
//        shoppingCartService.clearCart(cart);
//
//        return "redirect:/deliveries"; // Перенаправляем к списку доставок
//    }
@PostMapping("/createFromCart")
public String createDeliveryFromCart(HttpSession session, Model model, @RequestParam("productIds") List<Long> productIds) {
    if (productIds == null || productIds.isEmpty()) {
        return "redirect:/cart"; // Если параметр отсутствует, перенаправляем обратно
    }

    // Получаем корзину из сессии и создаем доставку
    ShoppingCart cart = shoppingCartService.getCartFromSession(session);
    Delivery newDelivery = deliveryService.createDeliveryFromCart(cart, productIds);

    shoppingCartService.clearCart(cart); // Очищаем корзину после доставки

    return "redirect:/deliveries"; // Перенаправление после создания доставки
}



}
