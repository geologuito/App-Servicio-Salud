package com.app.servicioSalud.controladores;

import com.app.servicioSalud.entidades.Paciente;
import com.app.servicioSalud.entidades.Profesional;
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
            @RequestParam String domicilio, @RequestParam String telefono, @RequestParam String especialidad,
            Boolean activo, Integer consulta, Date horario,
            ModelMap modelo, MultipartFile archivo) {

        try {
            profesionalServicio.registrar(archivo, matricula, dni, nombre, apellido, email, password, password2, domicilio, telefono, activo, especialidad, consulta, horario);

            modelo.put("exito", "Usuario Registrado!");

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
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {

        if (error != null) {
            modelo.put("error", "Usuario o Contraseña invalidos!");
        }

        return "loginProfesional.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_PROFESIONAL')")
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session) {

        Profesional profesional = (Profesional) session.getAttribute("profesionalsession");

        // Obtener la lista de profesionales
        List<Paciente> pacientes = pacienteServicio.listarPacientes();

        // Agregar profesionales y el profesional actual al modelo
        modelo.addAttribute("pacientes", pacientes); // trae la lista de pacientes
        modelo.addAttribute("profesional", profesional); // muestra los datos del prof del perfil

        return "panelProfesional";
    }

    @PreAuthorize("hasAnyRole('ROLE_PROFESIONAL')")
    @PostMapping("/perfil/{matricula}")
    public String actualizar(MultipartFile archivo, @PathVariable String idProfesional, @RequestParam String matricula, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String dni, @RequestParam String domicilio, @RequestParam String email, @RequestParam String telefono, @RequestParam String password, @RequestParam String password2, @RequestParam String especialidad, @RequestParam Date horario, ModelMap modelo) {

        try {
            profesionalServicio.actualizar(archivo, idProfesional, matricula, dni, nombre, apellido, email, password, password2, domicilio, telefono, Boolean.TRUE, especialidad, Integer.MIN_VALUE, horario);

            modelo.put("exito", "Profesional registrado con Exito");
            return "panelProfesional.html";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("email", email);
            modelo.put("domicilio", domicilio);
            modelo.put("telefono", telefono);

            return "modificarProfesional.html";
        }
    }

    @GetMapping("/listaProfesionales")
    public String listarProfesional(ModelMap modelo) {

        List<Profesional> profesionales = profesionalServicio.listarProfesionales();
        modelo.addAttribute("profesionales", profesionales);
        return "listarProfesional.html";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo) {

        modelo.put("profesional", profesionalServicio.getOne(id));
        return "modificarProfesional.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(MultipartFile archivo, @PathVariable String idProfesional, @RequestParam String matricula, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String dni, @RequestParam String domicilio, @RequestParam String email, @RequestParam String telefono, @RequestParam String password, @RequestParam String password2, @RequestParam String especialidad, @RequestParam Date horario, ModelMap modelo) {
        try {

            profesionalServicio.actualizar(archivo, idProfesional, matricula, dni, nombre, apellido, email, password, password2, domicilio, telefono, Boolean.TRUE, domicilio, Integer.MIN_VALUE, horario);
            return "redirect:../"; //Decidir donde va cuando modifica prof

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            return "modificarProfesional.html";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProfesional(@PathVariable String id, ModelMap modelo) throws MiException {

        profesionalServicio.eliminarProfesional(id);
        return "redirect:/index"; //Falta vista para saber a donde va cuando elimina prof
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarProfesional(@PathVariable String id) {
        try {
            profesionalServicio.eliminarProfesional(id);
            return new ResponseEntity<>("Profesional eliminado con éxito", HttpStatus.OK);
        } catch (MiException ex) {
            return new ResponseEntity<>("Error al eliminar el Profesional: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
