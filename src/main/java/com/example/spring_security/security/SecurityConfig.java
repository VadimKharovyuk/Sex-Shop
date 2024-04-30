package com.example.spring_security.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
//                requestMatchers("/registration").permitAll() //доступ всем
//                .anyRequest().authenticated())) // авторизаваным
//                .formLogin((form->form.loginPage("/login").permitAll())).logout((log->log.permitAll()));
//        return httpSecurity.build();
//
//    }
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
            .authorizeHttpRequests((req -> req
                    // Разрешение доступа к определенным маршрутам без аутентификации
                    .requestMatchers("/categories/**", "/products/**", "/registration", "/login","/").permitAll()
                    // Требование аутентификации для остальных маршрутов
                    .requestMatchers("/profile/**", "/orders/**").authenticated()
                    .anyRequest().authenticated() // По умолчанию все остальные запросы требуют аутентификации
            ))
            .formLogin((form -> form
                    // Настройка страницы входа
                    .loginPage("/login") // Страница входа
                    .permitAll() // Разрешение доступа к странице входа без аутентификации
            ))
            .logout((log -> log
                    .permitAll() // Выход доступен всем
            ));

    return httpSecurity.build();
}
}
