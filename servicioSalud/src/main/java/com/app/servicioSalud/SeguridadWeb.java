package com.app.servicioSalud;

import com.app.servicioSalud.servicios.PacienteServicio;
import com.app.servicioSalud.servicios.ProfesionalServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SeguridadWeb extends WebSecurityConfigurerAdapter {

    
    @Autowired
    public PacienteServicio pacienteServicio;
    @Autowired
    public ProfesionalServicio profesionalServicio;
    
    @Autowired
    public void configuredGlobal(AuthenticationManagerBuilder auth)throws Exception{
        
        auth.userDetailsService(pacienteServicio)
           .passwordEncoder(new BCryptPasswordEncoder());
        
        auth.userDetailsService(profesionalServicio)
           .passwordEncoder(new BCryptPasswordEncoder());
    }
    
    
    
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                .antMatchers("/admin/").hasRole("ADMIN") // le da permiso solo a los admin para el paneladministrador
                .antMatchers("/css/" , "/js/" ,"/img/*", "/**" )
                .permitAll()
                .and().formLogin()
                        .loginPage("/login")
                        .loginProcessingUrl("/logincheck")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/inicio")
                        .permitAll()
                .and().logout()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .permitAll()
                .and().csrf()
                        .disable();
    }
}


