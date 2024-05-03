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

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products") // Базовый путь для всех маршрутов контроллера
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService ;



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
    @GetMapping("/add")
    public String getAddProductForm(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categories);
        return "add_product"; // Имя Thymeleaf-шаблона для добавления продукта
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute Product product, @RequestParam(required = false) Long categoryId) {
        if (categoryId == null) {
            // Если categoryId не передан, перенаправляем на страницу с ошибкой или показываем сообщение об ошибке
            return "redirect:/error?message=Category ID is required";
        }

        try {
            Category category = categoryService.findById(categoryId);
            product.setCategory(category); // Устанавливаем категорию
            productService.addProduct(product, categoryId); // Сохраняем продукт
            return "redirect:/products"; // Перенаправляем после успешного добавления
        } catch (EntityNotFoundException ex) {
            // Если категория с данным ID не найдена, перенаправляем на страницу с ошибкой
            return "redirect:/error?message=Category not found";
        } catch (Exception ex) {
            // Обработка любых других исключений, возможна логгирование или другое действие
            return "redirect:/error"; // Перенаправление на страницу с ошибкой
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

            return "redirect:/error"; // Перенаправление на страницу ошибки
        }
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return "redirect:/products"; // После удаления
        } catch (EntityNotFoundException ex) {
            return "redirect:/error"; // Если продукт не найден
        }
    }
    @PutMapping("/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product product) {
        try {
            Product updatedProduct = productService.updateProduct(id, product);
            return "redirect:/products/" + id; // Перенаправление на страницу обновленного продукта
        } catch (EntityNotFoundException ex) {
            return "redirect:/error"; // Если продукт не найден
        }
    }

}
