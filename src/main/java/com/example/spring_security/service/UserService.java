package com.example.spring_security.service;

import com.example.spring_security.entity.UserEntity;
import com.example.spring_security.repositoty.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    UserRepo userRepo ;
    private BCryptPasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder();
    }
    public void save(UserEntity userEntity) {
        userEntity.setPassword(passwordEncoder().encode(userEntity.getPassword()));
        userRepo.save(userEntity);

    }
    public List<UserEntity> getAll(){
        return userRepo.findAll();
    }
}
