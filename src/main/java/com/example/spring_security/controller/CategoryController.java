package com.example.spring_security.controller;

import com.example.spring_security.entity.Category;
import com.example.spring_security.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/categories") // Базовый путь для всех маршрутов контроллера
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final String picDirectory = "/static/pic/"; // Стандартный путь к картинкам


    // Получение всех категорий
    @GetMapping
    public String getAllCategories(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "category_list"; // Имя шаблона для отображения всех категорий
    }

    // Получение категории по ID
    @GetMapping("/{id}")
    public String getCategoryById(@PathVariable Long id, Model model) {
        Category category = categoryService.findById(id);
        model.addAttribute("category", category);
        return "category_details"; // Имя шаблона для отображения деталей категории
    }

    // Форма для создания новой категории
    @GetMapping("/create")
    public String createCategoryForm(Model model) {
        model.addAttribute("category", new Category()); // Новый объект Category для формы
        List<String> availableImages = getAvailableImages();
        model.addAttribute("availableImages", availableImages);
        return "category_form"; // Имя шаблона для создания категории
    }

    // Создание новой категории
//    @PostMapping("/save")
//    public String createCategory(@ModelAttribute Category category) {
//        categoryService.createCategory(category);
//        return "redirect:/categories"; // Перенаправление после успешного создания
//    }
    @PostMapping("/save")
    public String saveCategory(@ModelAttribute Category category, @RequestParam("image") String imageName) {
        if (!imageName.isEmpty()) {
            category.setImagePath(picDirectory + imageName); // Установить путь к изображению
        }

        categoryService.saveCategory(category);

        return "redirect:/categories";
    }

    private List<String> getAvailableImages() {
        try {
            Path path = Paths.get("src/main/resources/static/pic");
            return Files.list(path)
                    .filter(Files::isRegularFile)
                    .map(p -> p.getFileName().toString())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return List.of(); // Возвращаем пустой список в случае ошибки
        }
    }

    // Форма для обновления существующей категории
    @GetMapping("/edit/{id}")
    public String editCategoryForm(@PathVariable Long id, Model model) {
        Category category = categoryService.findById(id);
        model.addAttribute("category", category);
        return "category_form"; // Используем ту же форму, что и для создания
    }

    // Обновление существующей категории
    @PostMapping("/edit/{id}")
    public String updateCategory(@PathVariable Long id, @ModelAttribute Category category) {
        categoryService.updateCategory(id, category);
        return "redirect:/categories"; // Перенаправление после успешного обновления
    }

    // Удаление категории
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/categories"; // Перенаправление после успешного удаления
    }
}