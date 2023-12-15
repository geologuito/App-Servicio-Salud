// package com.app.servicioSalud.servicios;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.mail.SimpleMailMessage;
// import org.springframework.mail.javamail.JavaMailSender;
// import org.springframework.stereotype.Service;

// @Service
// public class CorreoServicio {

//     @Autowired
//     private JavaMailSender javaMailSender;

//     public void envioRegistro(String userEmail, String userName) {

//         SimpleMailMessage message = new SimpleMailMessage();
//         message.setTo(userEmail);
//         message.setSubject("Registro exitoso ");
//         message.setText("Hola " + userName
//                 + " \n Gracias por registrarte en nuestro sitio. \n Apartir de ahora podras gestionar turnos y actualizar tus datos");

//         javaMailSender.send(message);
//     }

//     public void registroProfesional(String correoProfesional, String nombreProfesional) {

//         SimpleMailMessage message = new SimpleMailMessage();
//         message.setTo(correoProfesional);
//         message.setSubject("Registro exitoso ");
//         message.setText("Hola " + nombreProfesional + "\n Gracias por registrarte en nuestro sitio. "
//                 + " El administrador tiene que validar tu inscripcion, te avisaremos cuando estes habilitado\n La administracion ");

//         javaMailSender.send(message);
//     }

//     public void altaProfesional(String matricula) {

//         SimpleMailMessage message = new SimpleMailMessage();
//         message.setTo("ahealtservice@gmail");
//         message.setSubject("Nuevo profesional registrado");
//         message.setText("Atencion! tenemos una nueva inscpricion. \n Revisa matricula " + matricula);

//         javaMailSender.send(message);
//     }

//     public void calificacionProfesional(String correoPaciente, String matricula, String nombrePaciente, String id) {

//         // String urlBase = obtenerURL();
//         String urlBase = "http://localhost:8080";

//         SimpleMailMessage message = new SimpleMailMessage();
//         message.setTo(correoPaciente);
//         message.setSubject("Consulta Finalizada");
//         message.setText(nombrePaciente + "\n\n gracias por elegir nuestros servicios. \n"
//                 + "Ahora te pedimos que nos des tu opinio y califiques al profesional \n"
//                 + "ingresando a: " + urlBase + "/calificacion/crear/" + id + ".\n"
//                 + "Este feedback es voluntario\n\n Muchas gracias por colaborar. \n\n"
//                 + "Atentamente la Administracion");

        // javaMailSender.send(message);
    

