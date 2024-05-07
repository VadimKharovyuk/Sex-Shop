package com.example.spring_security.service;

import com.example.spring_security.entity.Delivery;
import com.example.spring_security.entity.ShoppingCart;
import com.example.spring_security.repositoty.DeliveryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class DeliveryService {

private final DeliveryRepository deliveryRepository;
    private final ShoppingCartService shoppingCartService;

    public Delivery createDeliveryFromCart(ShoppingCart cart, List<Long> productIds) {
        Delivery delivery = new Delivery();

        LocalDate shipmentDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        delivery.setShipmentDate(shipmentDate); // Устанавливаем дату отправки

        // Устанавливаем список товаров из корзины в доставку
        delivery.setItems(cart.getItems());

        // Сохранение новой доставки
        deliveryRepository.save(delivery);

        return delivery;
    }


    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    public Delivery getDeliveryById(Long id) {
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Delivery not found with ID " + id));
    }

    public void deleteDelivery(Long id) {
        if (deliveryRepository.existsById(id)) {
            deliveryRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Delivery with ID " + id + " not found");
        }
    }
}
