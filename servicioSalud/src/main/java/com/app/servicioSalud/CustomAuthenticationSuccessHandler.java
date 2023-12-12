package com.app.servicioSalud;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if (authorities.stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_PACIENTE"))) {
            response.sendRedirect("/paciente/perfil");
        } else if (authorities.stream().anyMatch(authority -> authority.getAuthority().equals("ROLE_PROFESIONAL"))) {
            response.sendRedirect("/profesional/perfil");
        } else {
            response.sendRedirect("/index");
        }
    }
}
