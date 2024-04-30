package com.example.spring_security.security;

import com.example.spring_security.entity.UserEntity;
import com.example.spring_security.repositoty.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    UserRepo userRepo ;

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepo.findByEmail(username)
                .orElseThrow(()->new UsernameNotFoundException("Пользователя не  найден с  email " +username +" не найден")) ;
        return UserDetailsImpl.build(userEntity) ;
    }
}
