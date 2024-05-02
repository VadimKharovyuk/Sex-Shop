package com.example.spring_security.repositoty;

import com.example.spring_security.entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder,Long> {
    Optional<CustomerOrder> findCartByUserId(Long id);

}
