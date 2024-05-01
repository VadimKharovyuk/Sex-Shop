package com.example.spring_security.controller;

import com.example.spring_security.entity.UserEntity;
import com.example.spring_security.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profile") // Базовый путь для личного кабинета
@AllArgsConstructor
public class UserProfileController {

    private final UserService userService;


    // Метод для отображения страницы профиля пользователя
    // Получение информации о текущем пользователе
    @GetMapping
    public String getUserProfile(Model model) {
        // Получение текущего аутентифицированного пользователя
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new IllegalStateException("Пользователь не аутентифицирован");
        }

        String currentUsername = authentication.getName(); // Имя или email пользователя

        // Получение данных пользователя из базы данных
        UserEntity user = userService.getUserByEmail(currentUsername);

        if (user == null) {
            throw new IllegalArgumentException("Пользователь с email '" + currentUsername + "' не найден");
        }

        // Передача данных в модель
        model.addAttribute("user", user);

        return "user_profile"; // Имя шаблона
    }

    // Обновление данных пользователя
    @PostMapping("/update")
    public String updateUserProfile(@ModelAttribute UserEntity user) {
        userService.updateUser(user.getId(), user);
        return "redirect:/profile"; // Перенаправление в личный кабинет после обновления
    }
}
