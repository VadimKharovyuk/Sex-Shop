//package com.example.spring_security.controller;
//
//import com.example.spring_security.entity.UserEntity;
//import com.example.spring_security.security.UserDetailsServiceImpl;
//import com.example.spring_security.service.UserService;
//import lombok.AllArgsConstructor;
//import org.springframework.ui.Model;
//import org.springframework.stereotype.Controller;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Optional;
//
//@Controller
//@RequestMapping("/profile") // Базовый путь для личного кабинета
//@AllArgsConstructor
//public class UserProfileController {
//
//    private final UserService userService;
//
//    @GetMapping
//    public String getUserProfile(Model model) {
//        // Получение аутентифицированного пользователя
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//
//        UserEntity user = userService.getUserByEmail(username)
//                .orElseThrow(() -> new IllegalArgumentException("Пользователь с именем " + username + " не найден"));
//
//        model.addAttribute("user", user); // Добавление объекта `user` в модель
//
//        return "user_profile"; // Имя шаблона
//    }
//
//    // Обновление данных пользователя
//    @PostMapping("/update")
//    public String updateUserProfile(@ModelAttribute UserEntity user) {
//        userService.updateUser(user.getId(), user);
//        return "redirect:/profile"; // Перенаправление в личный кабинет после обновления
//    }
//}
