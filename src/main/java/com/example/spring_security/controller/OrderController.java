package com.example.spring_security.controller;

import com.example.spring_security.entity.CustomerOrder;

import com.example.spring_security.service.CustomerOrderService;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders") // Базовый путь для всех маршрутов, связанных с заказами
@AllArgsConstructor
public class OrderController {

    private final CustomerOrderService orderService;

    // Получение всех заказов
    @GetMapping
    public String getAllOrders(Model model) {
        List<CustomerOrder> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "orders"; // Имя шаблона для отображения всех заказов
    }

    // Получение заказа по ID
    @GetMapping("/{id}")
    public String getOrderById(@PathVariable Long id, Model model) {
        CustomerOrder order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        return "order_details"; // Имя шаблона для отображения деталей заказа
    }

    // Создание нового заказа
    @PostMapping
    public String createOrder(@ModelAttribute CustomerOrder order) {
        orderService.createOrder(order);
        return "redirect:/orders"; // Перенаправление на страницу со списком заказов
    }

    // Обновление существующего заказа
    @PutMapping("/{id}")
    public String updateOrder(@PathVariable Long id, @ModelAttribute CustomerOrder order) {
        orderService.updateOrder(id, order);
        return "redirect:/orders"; // Перенаправление на страницу со списком заказов
    }

    // Удаление заказа
    @DeleteMapping("/{id}")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/orders"; // Перенаправление на страницу со списком заказов
    }
}
