package com.example.spring_security.service;

import com.example.spring_security.entity.Category;
import com.example.spring_security.entity.Product;

import com.example.spring_security.repositoty.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public void addProduct(Product product, Long categoryId) {
        // Используем аргумент categoryId напрямую, проверка на null не требуется
        Category category = categoryService.findById(categoryId);
        product.setCategory(category); // Устанавливаем категорию продукта
        productRepository.save(product); // Сохраняем продукт
    }

    // Поиск товаров по имени
    public List<Product> findProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    // Получение всех товаров
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Получение товара по ID
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Product with ID " + id + " not found")
        );
    }


    // Создание нового товара
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // Обновление существующего товара
    public Product updateProduct(Long id, Product product) {
        if (productRepository.existsById(id)) {
            product.setId(id); // Устанавливаем ID для сохранения
            return productRepository.save(product);
        } else {
            throw new EntityNotFoundException("Product with ID " + id + " not found");
        }
    }

    // Удаление товара
    public void deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Product with ID " + id + " not found");
        }
    }

    // Метод для поиска продуктов по идентификатору категории
    public List<Product> findProductsByCategory(Long categoryId) {
        return productRepository.findByCategory_Id(categoryId);
    }
}
