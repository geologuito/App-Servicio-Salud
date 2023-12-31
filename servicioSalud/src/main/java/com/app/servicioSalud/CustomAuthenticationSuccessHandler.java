package com.app.servicioSalud;

import com.app.servicioSalud.entidades.Paciente;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    Paciente paciente = new Paciente();

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            response.sendRedirect("/admin/dashboard");
        } else if (authorities.stream().anyMatch(pr -> pr.getAuthority().equals("ROLE_PROFESIONAL"))) {
            response.sendRedirect("/profesional/perfil");
        } else if (authorities.stream().anyMatch(pa -> pa.getAuthority().equals("ROLE_PACIENTE"))) {
            response.sendRedirect("/paciente/perfil");
        } else {
            response.sendRedirect("/");
        }
    }
}
