package com.example.spring_security.controller;

import com.example.spring_security.entity.Category;
import com.example.spring_security.entity.Product;
import com.example.spring_security.service.CategoryService;
import com.example.spring_security.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/products") // Базовый путь для всех маршрутов контроллера
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    // Поиск товаров по имени
    @GetMapping("/search")
    public String searchProducts(@RequestParam("query") String query, Model model) {
        List<Product> products = productService.findProductsByName(query);

        if (products.isEmpty()) {
            model.addAttribute("message", "No products found for the query: " + query);
        } else {
            model.addAttribute("products", products);
        }

        return "search_results"; // Имя шаблона для отображения результатов поиска
    }

    // Форма для добавления продукта
    @GetMapping("/add")
    public String getAddProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "add_product"; // Имя Thymeleaf-шаблона для добавления продукта
    }

    // Добавление нового продукта
    @PostMapping("/add")
    public String addProduct(@ModelAttribute Product product, @RequestParam Long categoryId) {
        try {
            Product newProduct = productService.addProduct(product, categoryId);
            return "redirect:/products"; // Перенаправление на список продуктов после успешного добавления
        } catch (EntityNotFoundException ex) {
            return "redirect:/error"; // В случае, если категория не найдена
        } catch (Exception ex) {
            return "redirect:/error"; // Другие ошибки
        }
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
        try {
            Product product = productService.getProductById(id);
            model.addAttribute("product", product);
            return "product_details"; // Имя шаблона для отображения деталей товара
        } catch (EntityNotFoundException ex) {
            logger.error("Product not found for ID " + id, ex);
            return "redirect:/error?message=Product not found"; // Перенаправление на страницу ошибки
        }
    }

    // Обновление продукта
    @PutMapping("/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product) {
        try {
            Product updatedProduct = productService.updateProduct(id, product);
            return "redirect:/products/" + id; // Перенаправление на страницу обновленного продукта
        } catch (EntityNotFoundException ex) {
            logger.error("Product not found for ID " + id, ex);
            return "redirect:/error?message=Product not found"; // Если продукт не найден
        }
    }

    // Удаление продукта
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return "redirect:/products"; // Перенаправление после успешного удаления
        } catch (EntityNotFoundException ex) {
            logger.error("Product not found for ID " + id, ex);
            return "redirect:/error?message=Product not found"; // Если продукт не найден
        }
    }
}
