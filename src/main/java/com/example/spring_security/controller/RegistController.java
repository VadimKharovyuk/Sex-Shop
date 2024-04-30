package com.example.spring_security.controller;

import com.example.spring_security.entity.UserEntity;
import com.example.spring_security.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class RegistController {
    private final UserService userService ;
    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute("user", new UserEntity());
        return "registration" ;
    }
   @PostMapping("/registration")
    public String addUser(@ModelAttribute ("user")  UserEntity user ,Model model){
        userService.save(user);
        return "redirect:/login" ;
    }
}
