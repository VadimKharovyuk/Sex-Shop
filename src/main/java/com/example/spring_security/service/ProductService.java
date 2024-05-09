
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

    private final ShoppingCartItemRepository shoppingCartItemRepository;


    @Transactional
    public void deleteProduct(Long productId) {
        try {
            // Удаление всех элементов корзины, связанных с этим продуктом
            shoppingCartItemRepository.deleteByProductId(productId);

            // Удаление самого продукта из репозитория
            productRepository.deleteById(productId);
        } catch (DataIntegrityViolationException ex) {
            // Обработка исключения при нарушении целостности данных
            throw new IllegalStateException("Product cannot be deleted because it has associated cart items.", ex);
        } catch (EntityNotFoundException ex) {
            throw new IllegalStateException("Product with ID " + productId + " not found", ex);
        }
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


    // Поиск продуктов по имени
    public List<Product> findProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name); // case-insensitive search
    }


    public void saveProduct(Product product) {
        productRepository.save(product);
    }
}

