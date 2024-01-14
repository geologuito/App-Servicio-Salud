package com.app.servicioSalud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.app.servicioSalud.servicios.PacienteServicio;
import org.springframework.core.annotation.Order;

@Configuration
@Order(102)
public class SeguridadPaciente extends WebSecurityConfigurerAdapter {
        @Autowired
        private PacienteServicio pacienteServicio;

        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
                auth.userDetailsService(pacienteServicio)
                                .passwordEncoder(new BCryptPasswordEncoder());
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
                http
                                .authorizeRequests(requests -> requests
                                                .antMatchers("/paciente/**").hasRole("PACIENTE")
                                                .antMatchers("/css/", "/js/", "/img/*", "/**").permitAll())
                                .formLogin(login -> login
                                                .loginPage("/paciente/login")
                                                .loginProcessingUrl("/logincheck")
                                                .usernameParameter("email")
                                                .passwordParameter("password")
                                                .defaultSuccessUrl("/paciente/perfil")
                                                .permitAll())
                                .logout(logout -> logout
                                                .logoutUrl("/logout")
                                                .logoutSuccessUrl("/")
                                                .permitAll())
                                .csrf(csrf -> csrf
                                                .disable());
        }
}
