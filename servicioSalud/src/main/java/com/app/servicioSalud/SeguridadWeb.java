package com.app.servicioSalud;

import com.app.servicioSalud.servicios.AdminServicio;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SeguridadWeb extends WebSecurityConfigurerAdapter {

<<<<<<< HEAD
        @Autowired
        public PacienteServicio pacienteServicio;
=======
    @Autowired
    public AdminServicio adminServicio;

    @Autowired
    public PacienteServicio pacienteServicio;
>>>>>>> ema

        @Autowired
        public ProfesionalServicio profesionalServicio;

<<<<<<< HEAD
        /*
         * @Autowired
         * public void configuredGlobal(AuthenticationManagerBuilder auth) throws
         * Exception {
         * 
         * auth.userDetailsService(profesionalServicio)
         * .passwordEncoder(new BCryptPasswordEncoder());
         * auth.userDetailsService(pacienteServicio)
         * .passwordEncoder(new BCryptPasswordEncoder());
         * 
         * }
         */
        @Autowired
        public void configuredGlobal(AuthenticationManagerBuilder auth) throws Exception {
                CompositeUserDetailsService compositeUserDetailsService = new CompositeUserDetailsService(
                                profesionalServicio, pacienteServicio);
=======
    @Autowired
    public void configuredGlobal(AuthenticationManagerBuilder auth) throws Exception {
        CompositeUserDetailsService compositeUserDetailsService = new CompositeUserDetailsService(
                profesionalServicio, pacienteServicio, adminServicio);
>>>>>>> ema

                auth.userDetailsService(compositeUserDetailsService)
                                .passwordEncoder(new BCryptPasswordEncoder());
        }

        /*
         * @Override
         * protected void configure(HttpSecurity http) throws Exception{
         * http.authorizeRequests()
         * .antMatchers("/admin/").hasRole("ADMIN") // le da permiso solo a los admin
         * para el paneladministrador// le da permiso solo a los admin para el
         * paneladministrador
         * .antMatchers("/css/" , "/js/" ,"/img/*", "/**" )
         * .permitAll()
         * .and().formLogin()
         * .loginPage("/paciente/login")
         * .loginProcessingUrl("/logincheck")
         * .usernameParameter("email")
         * .passwordParameter("password")
         * .defaultSuccessUrl("/paciente/perfil")
         * .permitAll()
         * .and().logout()
         * .logoutUrl("/logout")
         * .logoutSuccessUrl("/login")
         * .permitAll()
         * .and().csrf()
         * .disable();
         * 
         * http.authorizeRequests()
         * .antMatchers("/admin/").hasRole("ADMIN") // le da permiso solo a los admin
         * para el paneladministrador// le da permiso solo a los admin para el
         * paneladministrador
         * .antMatchers("/css/" , "/js/" ,"/img/*", "/**" )
         * .permitAll()
         * .and().formLogin()
         * .loginPage("/profesional/login")
         * .loginProcessingUrl("/logincheck")
         * .usernameParameter("email")
         * .passwordParameter("password")
         * .defaultSuccessUrl("/profesional/perfil")
         * .permitAll()
         * .and().logout()
         * .logoutUrl("/logout")
         * .logoutSuccessUrl("/login")
         * .permitAll()
         * .and().csrf()
         * .disable();
         * }
         */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/css/", "/js/", "/img/*", "/**").permitAll()
                .antMatchers("/paciente/login").permitAll()
                .antMatchers("/profesional/login").permitAll()
                .antMatchers("/paciente/**").hasRole("PACIENTE")
                .antMatchers("/profesional/**").hasRole("PROFESIONAL")
                .and().formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/logincheck")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler(new CustomAuthenticationSuccessHandler())
                .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .and().csrf().disable();

    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

}
