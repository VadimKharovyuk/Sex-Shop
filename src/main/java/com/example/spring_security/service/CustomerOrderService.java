package com.example.spring_security.service;

import com.example.spring_security.entity.CustomerOrder;
import com.example.spring_security.entity.OrderItem;
import com.example.spring_security.entity.Product;
import com.example.spring_security.entity.UserEntity;
import com.example.spring_security.repositoty.CustomerOrderRepository;
import com.example.spring_security.repositoty.UserRepo;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerOrderService {
    private final CustomerOrderRepository orderRepository;
    private final UserService userService;
    private final ProductService productService;
    private final UserRepo userRepo;

    private static final Logger logger = LoggerFactory.getLogger(CustomerOrderService.class);





    public Optional<UserEntity> getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }


    // Получение или создание корзины для пользователя
    public CustomerOrder getOrCreateCartForUser(String username) {
        UserEntity user = userService.getUserByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

        // Поиск существующей корзины пользователя
        Optional<CustomerOrder> existingCart = orderRepository.findCartByUserId(user.getId());

        return existingCart.orElseGet(() -> {
            // Если корзина не найдена, создаем новую
            CustomerOrder newCart = new CustomerOrder();
            newCart.setUser(user);
            newCart.setStatus("Cart");
            return orderRepository.save(newCart);
        });
    }

    public void addProductToCart(Long productId, String username) {
        try {
            Product product = productService.getProductById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Продукт не найден"));

            UserEntity user = userService.getUserByEmail(username)
                    .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

            CustomerOrder cart = getOrCreateCartForUser(username);

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(1); // Устанавливаем количество по умолчанию
            orderItem.setOrder(cart);

            cart.getOrderItems().add(orderItem);

            orderRepository.save(cart);

        } catch (IllegalArgumentException e) {
            logger.error("Ошибка при добавлении продукта в корзину: ", e);
            throw e;
        }
    }

    public CustomerOrder getCartForUser(String username) {
        UserEntity user = userService.getUserByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с именем " + username + " не найден"));

        // Поиск существующей корзины пользователя
        Optional<CustomerOrder> cartOptional = orderRepository.findCartByUserId(user.getId());

        return cartOptional.orElseGet(() -> {
            // Если корзина не найдена, создаем новую
            return createNewCartForUser(user);
        });
    }

    private CustomerOrder createNewCartForUser(UserEntity user) {
        CustomerOrder newCart = new CustomerOrder();
        newCart.setUser(user);
        newCart.setStatus("Cart");
        return orderRepository.save(newCart);
    }

    public List<CustomerOrder> getAllOrders() {
        // Используем метод findAll() для получения всех заказов
        return orderRepository.findAll();
    }

    public CustomerOrder getOrderById(Long id) {
        // Используем метод findById() из JpaRepository, который возвращает Optional<CustomerOrder>
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Заказ с ID " + id + " не найден")); // Если не найден, выбрасываем исключение
    }


    public CustomerOrder createOrder(CustomerOrder order) {
        // Проверка, что объект CustomerOrder не null
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        // Сохранение заказа в репозитории
        return orderRepository.save(order);
    }

    public CustomerOrder updateOrder(Long id, CustomerOrder updatedOrder) {
        // Найти существующий заказ по его ID
        CustomerOrder existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Заказ с ID " + id + " не найден"));

        // Обновить поля существующего заказа данными из updatedOrder
        existingOrder.setStatus(updatedOrder.getStatus());
        existingOrder.setUser(updatedOrder.getUser());
        existingOrder.setOrderItems(updatedOrder.getOrderItems());

        // Сохранить изменения
        return orderRepository.save(existingOrder); // Сохранение обновленного заказа
    }

    public void deleteOrder(Long id) {
        // Проверяем, существует ли заказ с данным ID
        if (!orderRepository.existsById(id)) {
            throw new IllegalArgumentException("Заказ с ID " + id + " не найден");
        }

        // Если заказ найден, удаляем его
        orderRepository.deleteById(id);
    }
}
