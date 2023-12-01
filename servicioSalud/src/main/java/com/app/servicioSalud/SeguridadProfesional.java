
package com.app.servicioSalud;

import com.app.servicioSalud.servicios.ProfesionalServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Luciano Otegui
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SeguridadProfesional extends WebSecurityConfigurerAdapter {

    @Autowired
    private ProfesionalServicio profesionalServicio;

    @Autowired
    public void configuredGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(profesionalServicio)
            .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/profesional/**").hasRole("PROFESIONAL")
                .antMatchers("/css/", "/js/", "/img/*", "/**").permitAll()
                .and()
            .formLogin()
                .loginPage("/profesional/login")
                .loginProcessingUrl("/logincheck")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/profesional/perfil")
                .permitAll()
                .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .permitAll()
                .and()
            .csrf().disable();
    }
}

