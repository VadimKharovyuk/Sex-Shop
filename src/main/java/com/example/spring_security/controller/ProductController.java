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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

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
//    @GetMapping("/add")
//    public String getAddProductForm(Model model) {
//        model.addAttribute("product", new Product());
//        model.addAttribute("categories", categoryService.getAllCategories());
//        return "add_product"; // Имя Thymeleaf-шаблона для добавления продукта
//    }
//
//    // Добавление нового продукта
//    @PostMapping("/add")
//    public String addProduct(@ModelAttribute Product product) {
//            productService.addProduct(product);
//            return "redirect:/products"; // Перенаправление после успешного добавления
//
//    }
    // Путь к каталогу изображений
    private static final String IMAGE_DIRECTORY = "src/main/resources/static/pic/";

    @GetMapping("/add")
    public String createProductForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());

        // Получение списка доступных изображений из каталога
        List<String> availableImages = getAvailableImages();
        model.addAttribute("availableImages", availableImages);

        return "add_product"; // Имя шаблона
    }

    @PostMapping("/add")
    public String addProduct(
            @ModelAttribute Product product,
            @RequestParam("image") String image, // Имя параметра должно соответствовать имени в форме
            RedirectAttributes redirectAttributes
    ) {
        if (image != null && !image.isEmpty()) {
            product.setImagePath("/static/pic/" + image); // Используем значение параметра
        }

        productService.saveProduct(product); // Сохранение продукта
        return "redirect:/products"; // Перенаправление после успешного добавления
    }


    private List<String> getAvailableImages() {
        try {
            Path path = Paths.get(IMAGE_DIRECTORY);
            return Files.list(path)
                    .filter(Files::isRegularFile)
                    .map(p -> p.getFileName().toString())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("Failed to list images", e);
            return List.of();
        }
    }


    @GetMapping
    public String getAllProducts(Model model) {
        List<Product> allProducts = productService.getAllProducts();
        model.addAttribute("products", allProducts);
        return "all_products"; //
    }



    @GetMapping("/{id}")
    public String getProductById(@PathVariable Long id, Model model) {
        try {
            Product product = productService.getProductById(id);
            model.addAttribute("product", product);
            return "product_details";
        } catch (EntityNotFoundException ex) {
            logger.error("Product not found for ID " + id, ex);
            return "redirect:/error?message=Product not found";
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


    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return "redirect:/products"; // Перенаправление после успешного удаления
        } catch (EntityNotFoundException ex) {
            logger.error("Product not found for ID " + id, ex);
            return "redirect:/error?message=Product not found"; // Если продукт не найден
        }
    }


    @GetMapping("/by-category/{categoryId}")
    public String getProductsByCategory(@PathVariable Long categoryId, Model model) {
        List<Product> products = productService.findProductsByCategory(categoryId); // Получаем продукты по `category_id`
        model.addAttribute("products", products); // Добавляем продукты в модель
        return "category_products"; // Имя шаблона для отображения продуктов
    }
}
