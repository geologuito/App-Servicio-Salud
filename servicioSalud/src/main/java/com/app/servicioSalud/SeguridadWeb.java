    package com.app.servicioSalud;

import com.app.servicioSalud.servicios.PacienteServicio;
import com.app.servicioSalud.servicios.ProfesionalServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/*@Configuration
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
<<<<<<< HEAD
                .antMatchers("/admin/").hasRole("ADMIN")
                .antMatchers("/profesional/").hasRole("PROFESIONAL")
                .antMatchers("/paciente/").hasRole("PACIENTE")// le da permiso solo a los admin para el paneladministrador
=======
                .antMatchers("/admin/").hasRole("ADMIN") // le da permiso solo a los admin para el paneladministrador
                .antMatchers("/paciente/").hasRole("PACIENTE") // le da permiso solo a los admin para el paneladministrador
                .antMatchers("/profesional/").hasRole("PROFESIONAL") // le da permiso solo a los admin para el paneladministrador
>>>>>>> cb83b199e8fd7597d02e9cf562f24352a93c401c
                .antMatchers("/css/" , "/js/" ,"/img/*", "/**" )
                .permitAll()
                .and().formLogin()
                        .loginPage("/paciente/login")
                        .loginProcessingUrl("/logincheck")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/paciente/perfil")
<<<<<<< HEAD
                        .permitAll()
                 .and().formLogin()
                        .loginPage("/profesional/login")
                        .loginProcessingUrl("/logincheck")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/profesional/perfil")
=======
>>>>>>> cb83b199e8fd7597d02e9cf562f24352a93c401c
                        .permitAll()
                .and().logout()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .permitAll()
                .and().csrf()
                        .disable();
    }
}*/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SeguridadWeb extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("adminPassword"))
                .roles("ADMIN")
                .build();

        UserDetails paciente = User.builder()
                .username("paciente")
                .password(passwordEncoder().encode("pacientePassword"))
                .roles("PACIENTE")
                .build();

        UserDetails profesional = User.builder()
                .username("profesional")
                .password(passwordEncoder().encode("profesionalPassword"))
                .roles("PROFESIONAL")
                .build();

        return new InMemoryUserDetailsManager(admin, paciente, profesional);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/paciente/**").hasRole("PACIENTE")
                .antMatchers("/profesional/**").hasRole("PROFESIONAL")
                .antMatchers("/public/**").permitAll()
                .and()
            .formLogin()
                 .and().formLogin()
                        .loginPage("/paciente/login")
                        .loginProcessingUrl("/logincheck")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/paciente/perfil")
                        .permitAll()                               
                .and().logout()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .permitAll()
                .and().csrf()
                        .disable();
            
             
    }
}