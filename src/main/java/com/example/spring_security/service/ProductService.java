package com.example.spring_security.service;

import com.example.spring_security.entity.Product;
import com.example.spring_security.repositoty.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    // Поиск товаров по имени
    public List<Product> findProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }



    // Получение всех товаров
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Получение товара по ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Создание нового товара
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // Обновление существующего товара
    public Product updateProduct(Long id, Product product) {
        if (productRepository.existsById(id)) {
            product.setId(id);
            return productRepository.save(product);
        } else {
            throw new IllegalArgumentException("Product with ID " + id + " not found");
        }
    }

    // Удаление товара
    public void deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Product with ID " + id + " not found");
        }
    }
}
