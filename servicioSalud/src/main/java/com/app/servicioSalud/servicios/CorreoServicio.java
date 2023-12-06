package com.app.servicioSalud.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class CorreoServicio {

    @Autowired
    private JavaMailSender javaMailSender;

    public void envioRegistro(String userEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject("Registro exitoso");
        message.setText("Gracias por registrarte en nuestro sitio. Bienvenido!");

        javaMailSender.send(message);
    }

}