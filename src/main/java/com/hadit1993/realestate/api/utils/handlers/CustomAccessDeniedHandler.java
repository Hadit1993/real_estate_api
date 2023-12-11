package com.hadit1993.realestate.api.utils.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.hadit1993.realestate.api.utils.templetes.ResponseTemplate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException {
        final ResponseTemplate<Void> responseTemplate = new ResponseTemplate<>(null,false, accessDeniedException.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().print(new ObjectMapper().writeValueAsString(responseTemplate));
        response.setContentType("application/json");
    }
}
