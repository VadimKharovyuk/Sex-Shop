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
    public UserEntity getUserById(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public UserEntity getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
    public void deleteUser(Long id) {
        if (userRepo.existsById(id)) {
            userRepo.deleteById(id);
        } else {
            throw new IllegalArgumentException("User with ID " + id + " not found");
        }
    }
    public UserEntity updateUser(Long id, UserEntity user) {
        if (userRepo.existsById(id)) {
            UserEntity existingUser = userRepo.findById(id).get();
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            if (user.getPassword() != null) {
                existingUser.setPassword(passwordEncoder().encode(user.getPassword()));
            }
            return userRepo.save(existingUser);
        } else {
            throw new IllegalArgumentException("User with ID " + id +"not found");
        }
    }

}
