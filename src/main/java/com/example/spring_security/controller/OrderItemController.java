package com.example.spring_security.controller;

import com.example.spring_security.entity.OrderItem;

import com.example.spring_security.service.OrderItemService;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order-items") // Базовый путь для всех маршрутов контроллера
@AllArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    // Получение всех элементов заказа
    @GetMapping
    public String getAllOrderItems(Model model) {
        model.addAttribute("orderItems", orderItemService.getAllOrderItems());
        return "order_items"; // Имя шаблона для отображения всех элементов заказа
    }

    // Получение элемента заказа по ID
    @GetMapping("/{id}")
    public String getOrderItemById(@PathVariable Long id, Model model) {
        OrderItem orderItem = orderItemService.getOrderItemById(id);
        model.addAttribute("orderItem", orderItem);
        return "order_item_details"; // Имя шаблона для отображения деталей элемента заказа
    }

    // Добавление нового элемента в заказ
    @PostMapping
    public String createOrderItem(@ModelAttribute OrderItem orderItem) {
        orderItemService.createOrderItem(orderItem);
        return "redirect:/order-items"; // Перенаправление на страницу со списком элементов заказа
    }

    // Обновление существующего элемента заказа
    @PutMapping("/{id}")
    public String updateOrderItem(@PathVariable Long id, @ModelAttribute OrderItem orderItem) {
        orderItemService.updateOrderItem(id, orderItem);
        return "redirect:/order-items"; // Перенаправление на страницу со списком элементов заказа
    }

    // Удаление элемента заказа
    @DeleteMapping("/{id}")
    public String deleteOrderItem(@PathVariable Long id) {
        orderItemService.deleteOrderItem(id);
        return "redirect:/order-items"; // Перенаправление на страницу со списком элементов заказа
    }
}
