package com.example.MoneyMatrix.context;


import com.example.MoneyMatrix.security.CustomUserDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthContext {
public static Long getId(){
    Authentication auth=SecurityContextHolder.getContext().getAuthentication();
    if (auth==null || !auth.isAuthenticated()||auth.getPrincipal().equals("anonymousUser")){
        throw new RuntimeException("Unauthorized access");
    }
    return ((CustomUserDetails)auth.getPrincipal()).getId();
}
}
