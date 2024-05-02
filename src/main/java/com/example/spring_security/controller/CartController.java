package com.example.spring_security.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.security.Principal;
import com.example.spring_security.entity.*;
import com.example.spring_security.service.*;

@Controller
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {

    private final CustomerOrderService customerOrderService;
    private final ProductService productService;




    // Добавление продукта в корзину
//    @GetMapping("/add/{productId}")
//    public String addProductToCart(@PathVariable Long productId, Principal principal) {
//        // Получение текущего пользователя
//        String username = principal.getName();
//
//        // Добавление продукта в корзину пользователя
//        customerOrderService.addProductToCart(productId, username);
//
//        return "redirect:/cart/view"; // Перенаправление на страницу просмотра корзины
//    }

    // Просмотр корзины
//    @GetMapping("/view")
//    public String viewCart(Principal principal, Model model) {
//        // Получение текущего пользователя
//        String username = principal.getName();
//
//        // Получение корзины пользователя
//        CustomerOrder cart = customerOrderService.getCartForUser(username);
//
//        model.addAttribute("cart", cart);
//        return "view_cart"; // Имя шаблона для отображения корзины
//    }
}
