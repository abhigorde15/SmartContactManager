package com.smart_contact_manager.smart_contact_manager.config;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class AuthFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        HttpSession session = request.getSession();
        
        if (exception instanceof org.springframework.security.authentication.DisabledException) {
            session.setAttribute("error", "User is disabled,Email is send to you For verification");
        } else {
            session.setAttribute("error", "Invalid Username or Password");
        }
      System.out.println("error"+session.getAttribute("error"));
        response.sendRedirect("/login");  // Redirecting to login page
    }
}
