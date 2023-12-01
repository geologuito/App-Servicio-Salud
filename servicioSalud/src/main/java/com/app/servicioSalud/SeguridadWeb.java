package com.app.servicioSalud;

import com.app.servicioSalud.servicios.PacienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/*@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SeguridadWeb extends WebSecurityConfigurerAdapter {


    @Autowired
    public PacienteServicio pacienteServicio;

    @Autowired
    public void configuredGlobal(AuthenticationManagerBuilder auth)throws Exception{

        auth.userDetailsService(pacienteServicio)
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
                        .loginPage("/paciente/login")
                        .loginProcessingUrl("/logincheck")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/paciente/inicio")
                        .permitAll()
                .and().logout()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .permitAll()
                .and().csrf()
                        .disable();
    }*/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SeguridadWeb extends WebSecurityConfigurerAdapter {

    @Autowired
    public PacienteServicio pacienteServicio;

    @Autowired
    public void configurarAutenticacion(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(pacienteServicio)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN") // Endpoints para el administrador
                .antMatchers("/profesional/**").hasRole("PROFESIONAL") // Endpoints para el profesional
                .antMatchers("/paciente/**").hasRole("PACIENTE") // Endpoints para el paciente
                .antMatchers("/css/", "/js/", "/img/**", "/**").permitAll()
                .and().formLogin()
                    .loginPage("/paciente/login")
                    .loginProcessingUrl("/paciente/logincheck") // Utilizar una URL específica para el procesamiento del inicio de sesión del paciente
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/") // Especificar la URL de éxito para el paciente
                    .permitAll()
                .and().formLogin()
                    .loginPage("/profesional/login")
                    .loginProcessingUrl("/profesional/logincheck") // Utilizar una URL específica para el procesamiento del inicio de sesión del profesional
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/") // Especificar la URL de éxito para el profesional
                    .permitAll()
                .and().logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
                    .permitAll()
                .and().csrf().disable();
    }
}

