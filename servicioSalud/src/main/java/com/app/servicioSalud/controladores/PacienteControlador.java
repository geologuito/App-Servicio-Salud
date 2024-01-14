package com.app.servicioSalud.controladores;

import com.app.servicioSalud.entidades.Admin;
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
import com.app.servicioSalud.servicios.PacienteServicio;
import com.app.servicioSalud.servicios.ProfesionalServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

            modelo.put("exito", "Paciente Registrado!");
            return "redirect:/";

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
        // return "redirect:/";
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

    @PreAuthorize("hasAnyRole('ROLE_PACIENTE','ROLE_ADMIN')")
    @GetMapping("/modificar/{dni}")
    public String modificarForm(@PathVariable String dni, ModelMap modelo, HttpSession session) {
        // Obtener el usuario actual
        Object usuario = session.getAttribute("adminsession");
        if (usuario == null) {
            usuario = session.getAttribute("pacientesession");
        }

        // Verificar el tipo de usuario y asignar el rol correspondiente
        String rol = (usuario instanceof Admin) ? "ADMIN" : "PACIENTE";

        // Agregar el usuario y su rol al modelo
        modelo.put("usuario", usuario);
        modelo.put("rol", rol);

        // Obtener y agregar la información del paciente
        modelo.put("paciente", pacienteServicio.getOne(dni));

        System.out.println("modificar");

        return "modificarPaciente"; // Mapear con el HTML
    }

    @PreAuthorize("hasAnyRole('ROLE_PACIENTE','ROLE_ADMIN')")
    @PostMapping("/modificar/{dni}")
    public String modificar(@PathVariable String dni, String nombre, String apellido, String email,
            String domicilio, String telefono, String password, String edad,
            MultipartFile archivo, ModelMap modelo, HttpSession session) {
        try {
            // Obtener el usuario actual
            Object usuario = session.getAttribute("adminsession");
            if (usuario == null) {
                usuario = session.getAttribute("pacientesession");
            }

            // Verificar el tipo de usuario y asignar el rol correspondiente
            String rol = (usuario instanceof Admin) ? "ADMIN" : "PACIENTE";

            // Modificar la información según el tipo de usuario
            pacienteServicio.modificarPaciente(archivo, dni, email, domicilio, telefono, password, password);

            // Redirigir según el tipo de usuario
            return (rol.equals("ADMIN")) ? "redirect:/admin/dashboard" : "redirect:/paciente/perfil";

        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "modificarPaciente.html"; // Mapear con el HTML
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/eliminar/{dni}")
    public String eliminarPaciente(@PathVariable String dni, ModelMap modelo) throws MiException {

        pacienteServicio.eliminarPaciente(dni);
        return "redirect:/admin/dashboard";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("/eliminar/{dni}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable String dni) {
        try {
            pacienteServicio.eliminarPaciente(dni);
            return new ResponseEntity<>("Paciente eliminado con éxito", HttpStatus.OK);

        } catch (MiException ex) {
            return new ResponseEntity<>("Error al eliminar el Paciente: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
