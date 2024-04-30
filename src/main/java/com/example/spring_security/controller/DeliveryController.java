package com.example.spring_security.controller;

import com.example.spring_security.entity.Delivery;
import com.example.spring_security.service.DeliveryService;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/deliveries") // Базовый путь для всех маршрутов в этом контроллере
@AllArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    // Получение всех доставок
    @GetMapping
    public String getAllDeliveries(Model model) {
        model.addAttribute("deliveries", deliveryService.getAllDeliveries());
        return "deliveries";
    }

    // Получение доставки по ID
    @GetMapping("/{id}")
    public String getDeliveryById(@PathVariable Long id, Model model) {
        Delivery delivery = deliveryService.getDeliveryById(id);
        model.addAttribute("delivery", delivery);
        return "delivery";
    }

    // Создание новой доставки
    @PostMapping
    public String createDelivery(@ModelAttribute Delivery delivery) {
        deliveryService.save(delivery);
        return "redirect:/deliveries"; // Перенаправление на страницу со списком доставок
    }
}
