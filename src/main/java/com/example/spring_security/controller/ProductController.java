package com.example.spring_security.controller;

import com.example.spring_security.entity.Product;
import com.example.spring_security.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    // Поиск товаров по имени
    @GetMapping("/search")
    public String searchProducts(@RequestParam("query") String query, Model model) {
        List<Product> products = productService.findProductsByName(query);
        model.addAttribute("products", products);
        return "search_results"; // Имя шаблона для отображения результатов поиска
    }

    // Остальные методы контроллера...
}
