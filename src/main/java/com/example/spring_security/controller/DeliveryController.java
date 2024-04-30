package com.example.spring_security.controller;

import com.example.spring_security.entity.Delivery;
import com.example.spring_security.service.DeliveryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@AllArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;



    @GetMapping("/delivery")
    public String getAllDeliveries(Model model) {
        model.addAttribute("deliveries", deliveryService.getAllDeliveries());
        return "deliveries";
    }

    @GetMapping("/delivery/{id}")
    public String getDeliveryById(@PathVariable Long id, Model model) {
        Delivery delivery = deliveryService.getDeliveryById(id);
        model.addAttribute("delivery", delivery);
        return "delivery";
    }

    @PostMapping("/delivery")
    public String createDelivery(@ModelAttribute Delivery delivery) {
        deliveryService.save(delivery);
        return "redirect:/delivery";
    }
}
