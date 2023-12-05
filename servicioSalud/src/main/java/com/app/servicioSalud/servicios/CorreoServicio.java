/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.servicioSalud.servicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
/**
 *
 * @author EduRiu
 */
@Service
public class CorreoServicio {
    
    @Autowired
    private JavaMailSender javaMailSender;
    
    public void envioRegistro (String userEmail){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject("Registro exitoso");
        message.setText("Gracias por registrarte en nuestro sitio. Bienvenido!");

        javaMailSender.send(message);
    }
    
}
