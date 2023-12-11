package com.hadit1993.realestate.api.utils.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.hadit1993.realestate.api.utils.templetes.ResponseTemplate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        final ResponseTemplate<Void> responseTemplate = new ResponseTemplate<>(null,false, authException.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().print(new ObjectMapper().writeValueAsString(responseTemplate));
        response.setContentType("application/json");
    }
}
