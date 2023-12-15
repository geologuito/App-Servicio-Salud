// package com.app.servicioSalud.configuracion;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.mail.javamail.JavaMailSender;
// import org.springframework.mail.javamail.JavaMailSenderImpl;
// import java.util.Properties;

// @Configuration
// public class ConfiguracionCorreo {

//     @Bean
//     public JavaMailSender javaMailSender() {
//         JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//         mailSender.setHost("smtp.gmail.com");
//         mailSender.setPort(587);
//         mailSender.setUsername("ahealtservice@gmail.com");
//         mailSender.setPassword("pept dvdh rnuf zxyp");

//         Properties props = mailSender.getJavaMailProperties();
//         props.put("mail.transport.protocol", "smtp");
//         props.put("mail.smtp.auth", "true");
//         props.put("mail.smtp.starttls.enable", "true");

//         return mailSender;
//     }
// }
