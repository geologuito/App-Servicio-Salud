package com.app.servicioSalud.controladores;

import com.app.servicioSalud.entidades.Paciente;
import com.app.servicioSalud.entidades.Profesional;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
        return "registroPaciente";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String dni, @RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String email, @RequestParam String domicilio, @RequestParam String telefono,
            @RequestParam String password, String password2, String edad, ModelMap modelo, MultipartFile archivo) {

        try {

            pacienteServicio.registrar(archivo, nombre, apellido, dni, email, domicilio, telefono, password, password2, edad);

            modelo.put("exito", "¡Paciente Registrado!");

            return "index.html";

        } catch (MiException ex) {

            //   List<Profesional> profesionales = profesionalServicio.listarProfesionales();
            //   modelo.addAttribute("profesionales", profesionales);
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
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {

        if (error != null) {
            modelo.put("error", "Usuario o Contraseña invalidos!");
        }

        return "loginPaciente.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_PACIENTE', 'ROLE_ADMIN')")
    @GetMapping("/index")
    public String inicio(HttpSession session) {

        Paciente logueado = (Paciente) session.getAttribute("pacientesession");

        if (logueado.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        }

        return "index.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_PACIENTE', 'ROLE_ADMIN')")
    @GetMapping("/paciente")
    public String perfil(ModelMap modelo, HttpSession session) {

        Paciente paciente = (Paciente) session.getAttribute("pacientesession");

        List<Profesional> profesionales = profesionalServicio.listarProfesionales();

        modelo.addAttribute("profesionales", profesionales);
        //modelo.addAttribute("paciente", paciente);

        modelo.put("paciente", paciente); //

        return "panelPaciente.html";
    }

    //  @GetMapping("/modificar/{dni}")
    //public String modificar(@PathVariable String dni, ModelMap modelo) {
    //  modelo.put("paciente", pacienteServicio.getOne(dni));
    //return "pacienteModificar";// mapear con html
    //}
    @PreAuthorize("hasAnyRole('ROLE_PACIENTE', 'ROLE_ADMIN')")
    @PostMapping("/paciente/{id}")
    public String actualizar(MultipartFile archivo, @PathVariable String id, @RequestParam String dni, @RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String email, @RequestParam String domicilio, @RequestParam String telefono,
            @RequestParam String password, String password2, String edad, ModelMap modelo) throws MiException {
        try {

            pacienteServicio.actualizar(archivo, id, nombre, apellido, dni, email, domicilio, telefono, password, password2, edad);

            modelo.put("exito", "Paciente registrado con Exito");

            return "panelPaciente.html"; // si esta todo ok va a ir a panelPaciente

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("dni", dni);
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("email", email);
            modelo.put("domicilio", domicilio);
            modelo.put("telefono", telefono);
            modelo.put("edad", edad);

            return "pacienteModificar.html"; // mapear con html
        }
    }

    @GetMapping("/listaPacientes")
    public String listarPaciente(ModelMap modelo) { // lista de pacientes.
        return "listarPaciente"; // para mapear con
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPaciente(@PathVariable String id, ModelMap modelo) throws MiException {

        pacienteServicio.eliminarPaciente(id);
        return "redirect:/index"; // Falta vista para saber a donde va cuando elimina paciente
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable String id) {
        try {
            pacienteServicio.eliminarPaciente(id);
            return new ResponseEntity<>("Paciente eliminado con éxito", HttpStatus.OK);
        } catch (MiException ex) {
            return new ResponseEntity<>("Error al eliminar el Paciente: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/puntuacion/{id}")
    public String mostrarPuntuacion(ModelMap modelo, @PathVariable String id) {

        // List<Calificacion> calificacion = calificacionServicio.listarCalificacion(id);
        Tuple promedio = calificacionServicio.calcularPromedio(id);
        // Calificacion promedio = calificacionServicio.calcularPromedio(id);
        //modelo.addAttribute("tuplas", promedio);

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
