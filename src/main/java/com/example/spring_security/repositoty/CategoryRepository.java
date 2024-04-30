package com.example.spring_security.repositoty;

import com.example.spring_security.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.Caret;
@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
}
