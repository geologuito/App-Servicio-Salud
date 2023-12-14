package com.app.servicioSalud.controladores;

import com.app.servicioSalud.entidades.Paciente;
import com.app.servicioSalud.entidades.Profesional;
import com.app.servicioSalud.enumeraciones.ObraSocial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.app.servicioSalud.excepciones.MiException;
import com.app.servicioSalud.servicios.CalificacionServicio;
import com.app.servicioSalud.servicios.PacienteServicio;
import com.app.servicioSalud.servicios.ProfesionalServicio;
import java.util.List;
import javax.persistence.Tuple;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/paciente") // localhost:8080/paciente
public class PacienteControlador {

    @Autowired
    private PacienteServicio pacienteServicio;
    @Autowired
    private ProfesionalServicio profesionalServicio;
    @Autowired
    private CalificacionServicio calificacionServicio;

    @GetMapping("/registrar") // localhost:8080/paciente/registrar
    public String registrar() {
        return "registroPaciente.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String dni, @RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String email, @RequestParam String domicilio, @RequestParam String telefono,
            @RequestParam String password, String password2, String edad, @RequestParam ObraSocial obraSocial,
            ModelMap modelo, MultipartFile archivo) {

        try {

            pacienteServicio.registrar(archivo, dni, nombre, apellido, email, domicilio, telefono, password, password2,
                    edad, obraSocial);

            modelo.put("exito", "Usuario Registrado!");

        } catch (MiException ex) {

            List<Profesional> profesionales = profesionalServicio.listarProfesional();
            modelo.addAttribute("profesionales", profesionales);

            modelo.put("error", ex.getMessage());
            modelo.put("dni", dni);
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("email", email);
            modelo.put("domicilio", domicilio);
            modelo.put("telefono", telefono);
            modelo.put("edad", edad);

            return "registroPaciente.html";
        }
        return "redirect:/";
    }

    @PreAuthorize("hasAnyRole('ROLE_PACIENTE')")
    @GetMapping("/perfil")
    public String perfil(HttpSession session, ModelMap modelo) {

        Paciente paciente = (Paciente) session.getAttribute("pacientesession");
        
        List<Profesional> profesionales = profesionalServicio.listarProfesional();

        modelo.addAttribute("profesionales", profesionales);
        modelo.addAttribute("paciente", paciente);

        return "panelPaciente";

    }

    @ModelAttribute("obrasSociales")
    public ObraSocial[] obrasSociales() {
        return ObraSocial.values();
    }

    @GetMapping("/listaPacientes")
    public String listarPaciente(ModelMap modelo) { // lista de pacientes.
        List<Paciente> pacientes = pacienteServicio.listarPaciente();
        modelo.addAttribute("pacientes", pacientes);
        return "listarPaciente"; // para mapear con
    }

    @GetMapping("/modificar/{dni}")
    public String modificar(@PathVariable String dni, ModelMap modelo) {

        modelo.put("paciente", pacienteServicio.getOne(dni));

        System.out.println("modificar");

        return "modificarPaciente.html";// mapear con html
    }

    @PostMapping("/modificar/{dni}")
    public String modificar(@PathVariable String dni, String nombre, String apellido, String email, String domicilio, String telefono, String password,
            String edad, MultipartFile archivo,
            ModelMap modelo) {
        try {
            pacienteServicio.modificarPaciente(archivo, dni, email, domicilio, telefono, password, password);
            return "redirect:../perfil"; // si esta todo ok va a ir a panelPaciente

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            return "modificarPaciente.html"; // mapear con html
        }
    }

    @GetMapping("/puntuacion/{id}")
    public String mostrarPuntuacion(ModelMap modelo, @PathVariable String id) {

        // List<Calificacion> calificacion =
        // calificacionServicio.listarCalificacion(id);
        Tuple promedio = calificacionServicio.calcularPromedio(id);
        // Calificacion promedio = calificacionServicio.calcularPromedio(id);
        // modelo.addAttribute("tuplas", promedio);

        // Acceder a los valores de la tupla
        Double valorColumna1 = promedio.get(0, Double.class);
        Double valorColumna2 = promedio.get(1, Double.class);
        Double valorColumna3 = promedio.get(2, Double.class);

        modelo.put("valor1", valorColumna1);
        modelo.put("valor2", valorColumna2);
        modelo.put("valor3", valorColumna3);

        return "reputacion";
    }
}
