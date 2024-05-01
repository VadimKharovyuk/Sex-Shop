package com.example.spring_security.security;

import com.example.spring_security.entity.UserEntity;
import com.example.spring_security.repositoty.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
   private final UserRepo userRepo ;

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepo.findByEmail(username)
                .orElseThrow(()->new UsernameNotFoundException("Пользователя не  найден с  email " +username +" не найден")) ;
        return UserDetailsImpl.build(userEntity) ;
    }
    public UserEntity getUserByEmail(String email) {
        Optional<UserEntity> userOpt = userRepo.findByEmail(email);

        if (!userOpt.isPresent()) {
            throw new IllegalArgumentException("Пользователь с email '" + email + "' не найден");
        }

        return userOpt.get();
    }

}
