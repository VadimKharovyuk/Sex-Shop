package com.example.spring_security.controller;
import com.example.spring_security.entity.Delivery;
import com.example.spring_security.entity.ShoppingCart;
import com.example.spring_security.service.DeliveryService;
import com.example.spring_security.service.ShoppingCartService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/deliveries")
@AllArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;
    private final ShoppingCartService shoppingCartService;


    @GetMapping
    public String getAllDeliveries(Model model) {
        List<Delivery> deliveries = deliveryService.getAllDeliveries();
        model.addAttribute("deliveries", deliveries);
        return "delivery_list"; // Возвращаем имя HTML шаблона
    }

    @GetMapping("/{id}")
    public String getDeliveryById(@PathVariable Long id, Model model) {
        Delivery delivery = deliveryService.getDeliveryById(id);
        model.addAttribute("delivery", delivery);
        return "delivery_details"; // Возвращаем имя HTML шаблона
    }

    @GetMapping("/create")
    public String createDeliveryForm(HttpSession session, Model model) {
        ShoppingCart cart = shoppingCartService.getCartFromSession(session);

        model.addAttribute("delivery", new Delivery()); // Объект для новой доставки
        model.addAttribute("cart", cart); // Передаем корзину в шаблон

        return "create_delivery"; // Шаблон для создания новой доставки
    }
    @PostMapping("/create")
    public String createDelivery(@ModelAttribute("delivery") Delivery delivery, Model model) {
        Delivery newDelivery = deliveryService.createDelivery(delivery);
        model.addAttribute("delivery", newDelivery);
        return "redirect:/deliveries/" + newDelivery.getId(); // Перенаправляем на страницу с подробностями о новой доставке
    }


    @PostMapping("/delete/{id}")
    public String deleteDeliveryById(@PathVariable Long id) {
        deliveryService.deleteDelivery(id);
        return "redirect:/deliveries"; // Перенаправляем пользователя на список доставок
    }


}


