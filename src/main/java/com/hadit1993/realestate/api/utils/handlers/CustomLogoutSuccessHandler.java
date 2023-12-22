package com.hadit1993.realestate.api.utils.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hadit1993.realestate.api.utils.templetes.ResponseTemplate;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {


        final ResponseTemplate<Void> responseTemplate =
                new ResponseTemplate<>(
                null,
                true,
                        "You successfully signed out");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print(new ObjectMapper().writeValueAsString(responseTemplate));
        response.setContentType("application/json");
    }
}
