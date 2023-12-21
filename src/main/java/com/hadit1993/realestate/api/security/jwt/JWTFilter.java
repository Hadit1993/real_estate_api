package com.hadit1993.realestate.api.security.jwt;

import com.hadit1993.realestate.api.features.users.entities.User;
import com.hadit1993.realestate.api.features.users.repositories.UserRepository;
import com.hadit1993.realestate.api.utils.exceptions.UnAuthorizedException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private final JWTService jwtService;

    private final UserDetailsService userDetailsService;

    private final UserRepository userRepository;


    public JWTFilter(
            @Autowired
            JWTService jwtService,
            @Autowired
            UserDetailsService userDetailsService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(
            @NonNull
            HttpServletRequest request,
            @NonNull
            HttpServletResponse response,
            @NonNull
            FilterChain filterChain) throws ServletException, IOException {


        Cookie[] cookies = request.getCookies();

        if(cookies != null) {
            Optional<Cookie> accessTokenCookie = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals("accessToken")).findFirst();
            if(accessTokenCookie.isPresent()) {
                String accessToken = accessTokenCookie.get().getValue();
                final Claims claims = jwtService.verify(accessToken);
                final Long userId = Long.parseLong(claims.getSubject());
                final List<String> authorities = claims.get("authorities", List.class);
                final User user = userRepository.findById(userId).orElseThrow(() -> new UnAuthorizedException("Bad credentials"));
                final var authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), null,
                        authorities.stream().map(SimpleGrantedAuthority::new).toList());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}



