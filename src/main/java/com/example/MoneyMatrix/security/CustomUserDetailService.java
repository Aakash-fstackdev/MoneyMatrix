package com.example.MoneyMatrix.security;

import com.example.MoneyMatrix.entity.User;
import com.example.MoneyMatrix.exceptions.UserNameNotFoundException;
import com.example.MoneyMatrix.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user=userRepo.findByEmail(email).orElseThrow(()->{throw new UserNameNotFoundException("User With email not found :"+email);
        });
        return new CustomUserDetails(user);

    }
}
