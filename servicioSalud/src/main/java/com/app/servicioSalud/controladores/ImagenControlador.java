package com.app.servicioSalud.controladores;

import com.app.servicioSalud.entidades.Paciente;
import com.app.servicioSalud.entidades.Profesional;
import com.app.servicioSalud.servicios.PacienteServicio;
import com.app.servicioSalud.servicios.ProfesionalServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/imagen")
public class ImagenControlador {

    @Autowired
    ProfesionalServicio profesionalServicio;

    @Autowired
    PacienteServicio pacienteServicio;

    @GetMapping("/profesional/{id}")
    public ResponseEntity<byte[]> imagenProfesional(@PathVariable String id) {

        Profesional profesional = profesionalServicio.getOne(id);

        byte[] imagen = profesional.getImagen().getContenido();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }

    @GetMapping("/paciente/{id}")
    public ResponseEntity<byte[]> imagenPaciente(@PathVariable String id) {

        Paciente paciente = pacienteServicio.getOne(id);

        byte[] imagen = paciente.getImagen().getContenido();

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
    }

}
