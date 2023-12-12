/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.servicioSalud;


/**
 *
 * @author EduRiu
 */
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

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
