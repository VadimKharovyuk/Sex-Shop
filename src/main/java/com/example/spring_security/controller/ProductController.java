package com.example.spring_security.controller;

import com.example.spring_security.entity.Product;
import com.example.spring_security.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products") // Базовый путь для всех маршрутов контроллера
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

    // Получение всех товаров
    @GetMapping
    public String getAllProducts(Model model) {
        List<Product> allProducts = productService.getAllProducts();
        model.addAttribute("products", allProducts);
        return "all_products"; // Имя шаблона для отображения всех товаров
    }


    // Получение товара по ID
    @GetMapping("/{id}")
    public String getProductById(@PathVariable Long id, Model model) {
        Optional<Product> product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "product_details"; // Имя шаблона для отображения деталей товара
    }

    // Остальные методы контроллера...
}
