//package com.example.spring_security.service;
//
//import com.example.spring_security.entity.Category;
//import com.example.spring_security.entity.Product;
//
//import com.example.spring_security.repositoty.ProductRepository;
//import jakarta.persistence.EntityNotFoundException;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@AllArgsConstructor
//public class ProductService {
//
//    private final ProductRepository productRepository;
//    private final CategoryService categoryService;
//
//
//
//    // Поиск товаров по имени
//    public List<Product> findProductsByName(String name) {
//        return productRepository.findByNameContainingIgnoreCase(name);
//    }
//
//    // Получение всех товаров
//    public List<Product> getAllProducts() {
//        return productRepository.findAll();
//    }
//
//    // Получение товара по ID
//    public Product getProductById(Long id) {
//        return productRepository.findById(id).orElseThrow(() ->
//                new EntityNotFoundException("Product with ID " + id + " not found")
//        );
//    }
//
//
//    // Создание нового товара
//    public Product createProduct(Product product) {
//        return productRepository.save(product);
//    }
//
//    // Обновление существующего товара
//    public Product updateProduct(Long id, Product product) {
//        if (productRepository.existsById(id)) {
//            product.setId(id); // Устанавливаем ID для сохранения
//            return productRepository.save(product);
//        } else {
//            throw new EntityNotFoundException("Product with ID " + id + " not found");
//        }
//    }
//
//    // Удаление товара
//    public void deleteProduct(Long id) {
//        if (productRepository.existsById(id)) {
//            productRepository.deleteById(id);
//        } else {
//            throw new EntityNotFoundException("Product with ID " + id + " not found");
//        }
//    }
//
//    // Метод для поиска продуктов по идентификатору категории
//    public List<Product> findProductsByCategory(Long categoryId) {
//        return productRepository.findByCategory_Id(categoryId);
//    }
//
//    public void addProduct(Product product, Long categoryId) {
//        if (categoryId == null) {
//            throw new IllegalArgumentException("Category ID must not be null");
//        }
//
//        // Поиск категории по переданному ID
//        Category category = categoryService.findById(categoryId);
//
//        if (category == null) {
//            throw new IllegalArgumentException("Category with ID " + categoryId + " does not exist");
//        }
//
//        // Устанавливаем категорию продукта
//        product.setCategory(category);
//
//        // Сохраняем продукт в базе данных
//        productRepository.save(product);
//    }
//
//}
package com.example.spring_security.service;

import com.example.spring_security.entity.Category;
import com.example.spring_security.entity.Product;
import com.example.spring_security.repositoty.ProductRepository;
import com.example.spring_security.repositoty.ShoppingCartItemRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    private  final  ShoppingCartItemRepository shoppingCartItemRepository;

    public void deleteProduct(Long productId) {
        try {
            deleteProduct(productId); // Удаляем продукт
        } catch (DataIntegrityViolationException ex) {
            // Обработка исключения при нарушении целостности данных
            throw new IllegalStateException("Product cannot be deleted because it has associated cart items.", ex);
        }

        // Удаление всех элементов корзины, связанных с продуктом
        shoppingCartItemRepository.deleteByProductId(productId);
        productRepository.deleteById(productId); // Удаление самого продукта
    }


    public List<Product> findProductsByCategory(Long categoryId) {
        return productRepository.findByCategory_Id(categoryId); // Используем метод репозитория
    }



    // Получение всех продуктов
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Получение продукта по ID
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with ID " + id + " not found"));
    }

    // Обновление продукта
    public Product updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = getProductById(id); // Проверяем, что продукт существует
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setImage(updatedProduct.getImage());
        existingProduct.setCategory(updatedProduct.getCategory());
        return productRepository.save(existingProduct);
    }

//    // Удаление продукта
//    public void deleteProduct(Long id) {
//        if (productRepository.existsById(id)) {
//            productRepository.deleteById(id);
//        } else {
//            throw new EntityNotFoundException("Product with ID " + id + " not found");
//        }
//    }

    // Поиск продуктов по имени
    public List<Product> findProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name); // case-insensitive search
    }


    public void addProduct(Product product) {
        productRepository.save(product);
    }
}

