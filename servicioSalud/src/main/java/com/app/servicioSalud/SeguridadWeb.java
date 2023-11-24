<<<<<<< HEAD
=======
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
>>>>>>> developer
package com.app.servicioSalud;

import com.app.servicioSalud.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
<<<<<<< HEAD
=======
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
>>>>>>> developer
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
<<<<<<< HEAD
=======
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Chicho
 */
>>>>>>> developer

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SeguridadWeb extends WebSecurityConfigurerAdapter {
<<<<<<< HEAD

    @Autowired
    public UsuarioServicio usuarioServicio;


=======
    
    @Autowired
    public UsuarioServicio usuarioServicio;
    
   /* @Autowired
    public void configuredGlobal(AuthenticationManagerBuilder auth)throws Exception{
        
        auth.userDetailsService(usuarioServicio)
           .passwordEncoder(new BCryptPasswordEncoder());
    }*/
    
    
>>>>>>> developer
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
<<<<<<< HEAD
                .antMatchers("/admin/").hasRole("ADMIN") // le da permiso solo a los admin para el paneladministrador
                .antMatchers("/css/" , "/js/" ,"/img/*", "/**" )
=======
                .antMatchers("/admin/*").hasRole("ADMIN") // le da permiso solo a los admin para el paneladministrador
                .antMatchers("/css/*" , "/js/*" ,"/img/*", "/**" )
>>>>>>> developer
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
<<<<<<< HEAD
    }
}
=======
    
    }
    
    
    
}

>>>>>>> developer
