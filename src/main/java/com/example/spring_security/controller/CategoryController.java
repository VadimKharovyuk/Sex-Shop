package com.example.spring_security.controller;

import com.example.spring_security.entity.Category;
import com.example.spring_security.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/categories") // Базовый путь для всех маршрутов в контроллере
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // Получение всех категорий
    @GetMapping
    public String getAllCategories(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "categories"; // Имя шаблона для отображения всех категорий
    }

    // Получение категории по ID
    @GetMapping("/{id}")
    public String getCategoryById(@PathVariable Long id, Model model) {
        Optional<Category> category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        return "category_details"; // Имя шаблона для отображения деталей категории
    }

    // Создание новой категории
    @PostMapping
    public String createCategory(@ModelAttribute Category category) {
        categoryService.createCategory(category);
        return "redirect:/categories"; // Перенаправление на страницу со списком категорий
    }

    // Обновление существующей категории
    @PutMapping("/{id}")
    public String updateCategory(@PathVariable Long id, @ModelAttribute Category category) {
        categoryService.updateCategory(id, category);
        return "redirect:/categories"; // Перенаправление на страницу со списком категорий
    }

    // Удаление категории
    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/categories"; // Перенаправление на страницу со списком категорий
    }
}
