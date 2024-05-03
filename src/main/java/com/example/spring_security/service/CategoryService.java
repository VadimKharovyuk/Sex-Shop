package com.example.spring_security.service;

import com.example.spring_security.entity.Category;
import com.example.spring_security.repositoty.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // Получение всех категорий
    public List<Category> getAllCategories() {
        return categoryRepository.findAll(); // Проверка, что список не пустой
    }

    // Получение категории по ID
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    // Создание новой категории
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    // Обновление существующей категории
    public Category updateCategory(Long id, Category category) {
        if (categoryRepository.existsById(id)) {
            category.setId(id);
            return categoryRepository.save(category);
        } else {
            throw new IllegalArgumentException("Category with ID " + id + " not found");
        }
    }

    // Удаление категории
    public void deleteCategory(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Category with ID " + id + " not found");
        }
    }

    public Category findById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found for id: " + categoryId));
    }

    public boolean categoryExists(Long categoryId) {
        return categoryRepository.existsById(categoryId);
    }

}
