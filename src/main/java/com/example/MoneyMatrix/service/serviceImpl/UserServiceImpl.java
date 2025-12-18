package com.example.MoneyMatrix.service.serviceImpl;

import com.example.MoneyMatrix.dto.LoginRequest;
import com.example.MoneyMatrix.dto.LoginResponse;
import com.example.MoneyMatrix.dto.RegisterRequest;
import com.example.MoneyMatrix.dto.RegisterResponse;
import com.example.MoneyMatrix.entity.User;
import com.example.MoneyMatrix.exceptions.UserNameAlreadyExistsException;
import com.example.MoneyMatrix.mapper.Mappers;
import com.example.MoneyMatrix.repository.UserRepo;
import com.example.MoneyMatrix.security.CustomUserDetailService;
import com.example.MoneyMatrix.security.CustomUserDetails;
import com.example.MoneyMatrix.service.UserService;
import com.example.MoneyMatrix.utils.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;
    private final Mappers mappers;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailService userDetailService;
    private final JwtProvider jwtProvider;



    @Override
    public RegisterResponse createUser(RegisterRequest request) {
        if (userRepo.findByEmail(request.getEmail()).isPresent()){
            throw new UserNameAlreadyExistsException("Email Already registred :"+ request.getEmail());

        }
        User user=new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
       if (request.getRole()==null){
           user.setRole(User.Role.USER);
       }else {
           user.setRole(request.getRole());
       }
        User saveduser=userRepo.save(user);
        return mappers.toResponse(saveduser);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
       Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
       CustomUserDetails userDetails= (CustomUserDetails) userDetailService.loadUserByUsername(authentication.getName());
       String token= jwtProvider.generateToken(userDetails);
       return new LoginResponse( token);
    }
}
