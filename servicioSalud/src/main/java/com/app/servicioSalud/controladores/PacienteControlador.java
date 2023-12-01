package com.app.servicioSalud.controladores;

import com.app.servicioSalud.entidades.Paciente;
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
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/paciente") // localhost:8080/paciente
public class PacienteControlador {

    @Autowired
    private PacienteServicio pacienteServicio;

    @GetMapping("/registrar") // localhost:8080/paciente/registrar
    public String registrar() {
        return "registroPaciente";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String dni, @RequestParam String nombre, @RequestParam String apellido,
            @RequestParam String email, @RequestParam String domicilio, @RequestParam String telefono,
            @RequestParam String password, String password2, ModelMap modelo) {

        try {
            pacienteServicio.registrar(dni, nombre, apellido, email, domicilio, telefono, password, password2);

            modelo.put("exito", "Usuario Registrado!");

        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("dni", dni);
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("email", email);
            modelo.put("domicilio", domicilio);
            modelo.put("telefono", telefono);

            return "registroPaciente.html";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {

        if (error != null) {
            modelo.put("error", "Usuario o ContraseÃ±a invalidos!");
        }

        return "loginPaciente.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_PACIENTE','ROLE_ADMIN')")
    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session) {

        Paciente logueado = (Paciente) session.getAttribute("pacientesession");

        if (logueado.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        }
        modelo.put("paciente", logueado);

        return "panelPaciente.html";
    }


    @GetMapping("/lista")
    public String listar(ModelMap modelo) { // lista de pacientes.
        List<Paciente> pacientes = pacienteServicio.listarPaciente();
        modelo.addAttribute("pacientes", pacientes);
        return "listarPacientes"; // para mapear con 
    }

    @GetMapping("/modificar/{dni}")
    public String modificar(@PathVariable String dni, ModelMap modelo) {

        modelo.put("paciente", pacienteServicio.getOne(dni));
        List<Paciente> pacientes = pacienteServicio.listarPaciente();
        modelo.addAttribute("pacientes", pacientes);

        return "pacienteModificar";// mapear con html
    }

    @PostMapping("/modificar/{dni}")
    public String modificar(@PathVariable String dni, String email, String domicilio, String telefono, String password, ModelMap modelo) throws MiException {
        try {

            pacienteServicio.modificarValidacion(domicilio, email, telefono, password, password);

            return "panelPaciente"; // si esta todo ok va a ir a panelPaciente

        } catch (MiException ex) {
            List<Paciente> pacientes = pacienteServicio.listarPaciente();
            modelo.addAttribute("pacientes", pacientes);
            modelo.put("error", ex.getMessage());
            return "pacienteModificar"; // mapear con html
        }
    }

    @GetMapping("/eliminar/{dni}")
    public String eliminarPaciente(@PathVariable String dni, ModelMap modelo) throws MiException {

        pacienteServicio.eliminarPaciente(dni);
        return "redirect:/index"; //Falta vista para saber a donde va cuando elimina prof
    }

    @DeleteMapping("/eliminar/{dni}")
    public ResponseEntity<String> eliminarProfesional(@PathVariable String dni) {
        try {
            pacienteServicio.eliminarPaciente(dni);
            return new ResponseEntity<>("Paciente eliminado con éxito", HttpStatus.OK);
        } catch (MiException ex) {
            return new ResponseEntity<>("Error al eliminar el Paciente: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
