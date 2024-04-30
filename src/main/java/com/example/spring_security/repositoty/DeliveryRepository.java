package com.example.spring_security.repositoty;

import com.example.spring_security.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository  extends JpaRepository<Delivery,Long> {
}
