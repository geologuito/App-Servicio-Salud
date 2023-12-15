package com.app.servicioSalud.controladores;

import com.app.servicioSalud.entidades.Admin;
import com.app.servicioSalud.entidades.Paciente;
import com.app.servicioSalud.entidades.Profesional;
import com.app.servicioSalud.enumeraciones.Especialidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.app.servicioSalud.excepciones.MiException;
import com.app.servicioSalud.servicios.PacienteServicio;
import com.app.servicioSalud.servicios.ProfesionalServicio;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/profesional") // localhost:8080/profesional
public class ProfesionalControlador {

    @Autowired
    private ProfesionalServicio profesionalServicio;
    @Autowired
    private PacienteServicio pacienteServicio;

    @GetMapping("/registrar") // localhost:8080/profesional/registrar
    public String registrar() {
        return "registroProfesional";

    }

    @PostMapping("/registro")
    public String registro(@RequestParam String matricula, @RequestParam String dni,
            @RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String email, @RequestParam String password, String password2,
            @RequestParam String domicilio, @RequestParam String telefono,
            Boolean activo, Integer consulta, Date horario, @RequestParam Especialidad especialidad,
            ModelMap modelo, MultipartFile archivo) {

        try {
            profesionalServicio.registrar(archivo, matricula, dni, nombre, apellido, email, password, password2, domicilio, telefono, activo, consulta, horario, especialidad);

            modelo.put("exito", "Profesional Registrado!");
            
            return "redirect:/";

        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("dni", dni);
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("email", email);
            modelo.put("domicilio", domicilio);
            modelo.put("telefono", telefono);

            return "registroProfesional.html";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_PROFESIONAL')")
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session) {

        Profesional profesional = (Profesional) session.getAttribute("profesionalsession");

        if (profesional.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        }

        // Obtener la lista de profesionales
        List<Paciente> pacientes = pacienteServicio.listarPaciente();

        // Agregar profesionales y el profesional actual al modelo
        modelo.addAttribute("pacientes", pacientes); // trae la lista de pacientes
        modelo.addAttribute("profesional", profesional); // muestra los datos del prof del perfil

        return "panelProfesional";
    }

    @ModelAttribute("especialidades")
    public Especialidad[] especialidades() {
        return Especialidad.values();
    }

    @GetMapping("/listaProfesionales")
    public String listarProfesional(ModelMap modelo) {

        List<Profesional> profesionales = profesionalServicio.listarProfesional();
        modelo.addAttribute("profesionales", profesionales);
        return "listarProfesional.html";

    }

    @PreAuthorize("hasAnyRole('ROLE_PROFESIONAL','ROLE_ADMIN')")
    @GetMapping("/modificar/{matricula}")
    public String modificarForm(@PathVariable String matricula, ModelMap modelo, HttpSession session) {

        // Obtener el usuario actual
        Object usuario = session.getAttribute("adminsession");
        if (usuario == null) {
            usuario = session.getAttribute("profesionalsession");
        }

        // Verificar el tipo de usuario y asignar el rol correspondiente
        String rol = (usuario instanceof Admin) ? "ADMIN" : "PROFESIONAL";

        // Agregar el usuario y su rol al modelo
        modelo.put("usuario", usuario);
        modelo.put("rol", rol);

        // Obtener y agregar la información del profesional
        modelo.put("profesional", profesionalServicio.getOne(matricula));

        return "modificarProfesional";
    }

    @PreAuthorize("hasAnyRole('ROLE_PROFESIONAL','ROLE_ADMIN')")
    @PostMapping("/modificar/{matricula}")
    public String modificar(@PathVariable String matricula, String email, String password, String domicilio,
            String telefono, ModelMap modelo, MultipartFile archivo, HttpSession session) {
        try {
            // Obtener el usuario actual
            Object usuario = session.getAttribute("adminsession");
            if (usuario == null) {
                usuario = session.getAttribute("profesionalsession");
            }

            // Verificar el tipo de usuario y asignar el rol correspondiente
            String rol = (usuario instanceof Admin) ? "ADMIN" : "PROFESIONAL";

            // Modificar la información según el tipo de usuario
            profesionalServicio.modificarProfesional(archivo, matricula, email, password, password, domicilio,
                    telefono);
            return (rol.equals("ADMIN")) ? "redirect:/admin/dashboard" : "redirect:/profesional/perfil";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            return "modificarProfesional";
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/eliminar/{matricula}")
    public String eliminarProfesional(@PathVariable String matricula, ModelMap modelo) throws MiException {

        profesionalServicio.eliminarProfesional(matricula);
        return "redirect:/admin/dashboard"; // Falta vista para saber a donde va cuando elimina prof
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping("/eliminar/{matricula}")
    public ResponseEntity<String> eliminarProfesional(@PathVariable String matricula) {
        try {
            profesionalServicio.eliminarProfesional(matricula);
            return new ResponseEntity<>("Profesional eliminado con éxito", HttpStatus.OK);
        } catch (MiException ex) {
            return new ResponseEntity<>("Error al eliminar el Profesional: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
