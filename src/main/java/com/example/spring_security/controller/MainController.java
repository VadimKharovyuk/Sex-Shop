package com.example.spring_security.controller;

import com.example.spring_security.repositoty.UserRepo;
import com.example.spring_security.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class MainController {
    UserService userService ;
    @GetMapping("/")
    public String main(Model model){
        model.addAttribute("user",userService.getAll());
        return "main";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
