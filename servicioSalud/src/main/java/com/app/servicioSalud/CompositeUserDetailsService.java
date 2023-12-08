package com.app.servicioSalud;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CompositeUserDetailsService implements UserDetailsService {

    private final UserDetailsService profesionalServicio;
    private final UserDetailsService pacienteServicio;

    public CompositeUserDetailsService(UserDetailsService profesionalServicio, UserDetailsService pacienteServicio) {
        this.profesionalServicio = profesionalServicio;
        this.pacienteServicio = pacienteServicio;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Implementa la lógica para cargar el usuario según el servicio apropiado
        UserDetails userDetailsProfesional = profesionalServicio.loadUserByUsername(username);
        if (userDetailsProfesional != null) {
            return userDetailsProfesional;
        }

        UserDetails userDetailsPaciente = pacienteServicio.loadUserByUsername(username);
        if (userDetailsPaciente != null) {
            return userDetailsPaciente;
        }

        throw new UsernameNotFoundException("Usuario no encontrado");
    }
}
