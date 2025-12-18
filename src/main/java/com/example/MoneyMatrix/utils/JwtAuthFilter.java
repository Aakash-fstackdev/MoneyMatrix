package com.example.MoneyMatrix.utils;

import com.example.MoneyMatrix.exceptions.ExpiredJwtException;
import com.example.MoneyMatrix.security.CustomUserDetailService;
import com.example.MoneyMatrix.security.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final CustomUserDetailService userDetailService;
    private final JwtProvider jwtProvider;

    public JwtAuthFilter(CustomUserDetailService userDetailService, JwtProvider jwtProvider) {
        this.userDetailService = userDetailService;
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader=request.getHeader("Authorization");
        if (authHeader==null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        String path=request.getServletPath();
        if (path.startsWith("/v3/api-docs") || path.startsWith("/swagger-ui")){
            filterChain.doFilter(request,response);
            return;
        }
        String token=authHeader.substring(7);
        String email=null;
        Long id=null;
        try {
            email= jwtProvider.extractEmail(token);
            id= jwtProvider.extractId(token);
        }catch (Exception e){
            filterChain.doFilter(request,response);
        }

        if (email!=null && SecurityContextHolder.getContext().getAuthentication()==null){

            if (jwtProvider.isTokenValid(token)) {
                CustomUserDetails userDetails = (CustomUserDetails) userDetailService.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            }
        filterChain.doFilter(request,response);
    }
}
