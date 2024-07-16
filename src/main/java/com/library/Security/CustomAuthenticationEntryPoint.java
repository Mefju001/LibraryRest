package com.library.Security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        int statusCode = response.getStatus();
        if (statusCode == HttpServletResponse.SC_UNAUTHORIZED) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Custom message");
        } else if (statusCode == HttpServletResponse.SC_FORBIDDEN) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden: Custom message");
            response.getWriter().write("Forbidden: Custom message"); // Zwrócenie niestandardowej wiadomości

        } else {
            // Obsługa innych kodów odpowiedzi
            response.sendError(statusCode, "Error: Custom message");
        }
    }


}
