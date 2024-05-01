package com.example.spring_security.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    UserDetailsServiceImpl userDetailsService ;
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){ //отвечает за авторизацию с БД
        DaoAuthenticationProvider aut = new DaoAuthenticationProvider();
        aut.setUserDetailsService(userDetailsService);
        aut.setPasswordEncoder(encoder());
        return aut;
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception{
//        httpSecurity.authorizeHttpRequests((req->req.
//                requestMatchers("/registration","/categories/**","/products/**","/login","/").permitAll() //доступ всем
//                .anyRequest().authenticated())) // авторизаваным
//                .formLogin((form->form.loginPage("/login").permitAll())).logout((LogoutConfigurer::permitAll));
//        return httpSecurity.build();
//
//    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests((req -> req
                        .requestMatchers("/categories/**", "/products/**", "/registration", "/login", "/", "/static/pic/**", "/templates/pic/**").permitAll() // Разрешение на открытый доступ
                        .requestMatchers("/profile/**", "/orders/**").authenticated() // Требование аутентификации
                        .anyRequest().authenticated() // Любые другие запросы также требуют аутентификации
                ))
                .formLogin((form -> form
                        .loginPage("/login") // Настройка страницы входа
                        .permitAll() // Разрешить доступ к странице входа
                ))
                .logout((log -> log
                        .logoutUrl("/logout") // URL для выхода из системы
                        .logoutSuccessUrl("/login") // Перенаправление после успешного выхода
                        .permitAll() // Разрешение на выход без аутентификации
                )).csrf().disable(); // Отключение CSRF, если это необходимо

        return httpSecurity.build();
    }
}
