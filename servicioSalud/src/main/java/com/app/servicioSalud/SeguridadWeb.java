    package com.app.servicioSalud;


import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(100)
public class SeguridadWeb extends WebSecurityConfigurerAdapter {



    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/admin/").hasRole("ADMIN") // le da permiso solo a los admin para el paneladministrador// le da permiso solo a los admin para el paneladministrador
                .antMatchers("/css/" , "/js/" ,"/img/*", "/**" )
                .permitAll()
                .and().logout()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .permitAll()
                .and().csrf()
                        .disable();
    }
}
