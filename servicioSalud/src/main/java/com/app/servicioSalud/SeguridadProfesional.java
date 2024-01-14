package com.app.servicioSalud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.app.servicioSalud.servicios.ProfesionalServicio;
import org.springframework.core.annotation.Order;

@Configuration
@Order(101)
public class SeguridadProfesional extends WebSecurityConfigurerAdapter {
    @Autowired
    private ProfesionalServicio profesionalServicio;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(profesionalServicio)
            .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(requests -> requests
                .antMatchers("/profesional/**").hasRole("PROFESIONAL")
                .antMatchers("/css/", "/js/", "/img/*", "/**").permitAll())
                .formLogin(login -> login
                        .loginPage("/profesional/login")
                        .loginProcessingUrl("/logincheck")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/profesional/perfil")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll())
                .csrf(csrf -> csrf.disable());
    }
}
