package com.app.servicioSalud.controladores;

import com.app.servicioSalud.entidades.Paciente;
import com.app.servicioSalud.entidades.Profesional;
import com.app.servicioSalud.excepciones.MiException;
import com.app.servicioSalud.servicios.AdminServicio;
import com.app.servicioSalud.servicios.PacienteServicio;
import com.app.servicioSalud.servicios.ProfesionalServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin")
public class AdminControlador {

    @Autowired
    private AdminServicio adminServicio;
    @Autowired
    private PacienteServicio pacienteServicio;
    @Autowired
    private ProfesionalServicio profesionalServicio;

    @GetMapping("/registrar") // localhost:8080/admin/registrar
    public String registrar() {
        return "registroAdmin";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String email, @RequestParam String password, String password2, ModelMap modelo, MultipartFile archivo) {

        try {

            adminServicio.registrar(archivo, email, password, password2);

            modelo.put("exito", "Admin Registrado!");

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            modelo.put("email", email);

            return "registroAdmin.html";
        }
        return "redirect:/";
    }

    @GetMapping("/dashboard")
    public String panelAdministrativo(ModelMap modelo) {

        List<Paciente> pacientes = pacienteServicio.listarPaciente();
        List<Profesional> profesionales = profesionalServicio.listarProfesional();
        modelo.addAttribute("pacientes", pacientes);
        modelo.addAttribute("profesionales", profesionales);

        return "dashbo";
    }

    @GetMapping("/pacientes")
    public String listarPacientes(ModelMap modelo) {
        List<Paciente> pacientes = pacienteServicio.listarPaciente();
        modelo.addAttribute("pacientes", pacientes);

        return "dashbo";
    }

    @GetMapping("/modificar/{dni}")
    public String modificar(@PathVariable String dni, MultipartFile archivo, String email, String domicilio,
            String telefono, String password, String password2) throws MiException {

        pacienteServicio.modificarPaciente(archivo, dni, email, domicilio, telefono, password, password2);

        return "redirect:/paciente/modificarPaciente.html";
    }

//    @GetMapping("/modificar1/{dni}")
//    public String modificarPorAdmin(@PathVariable String dni, ModelMap modelo) {
//        modelo.put("paciente", pacienteServicio.getOne(dni));
//        return "modificarPacienteAdmin.html";// mapear con html
//    }
    @PostMapping("/modificar/{dni}")
    public String modificar(@PathVariable String dni, String nombre, String apellido, String email, String domicilio, String telefono, String password,
            String edad, MultipartFile archivo,
            ModelMap modelo) {
        try {
            pacienteServicio.modificarPaciente(archivo, dni, email, domicilio, telefono, password, password);
            return "dashbo"; // si esta todo ok va a ir a panelPaciente

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            return "modificarPaciente.html"; // mapear con html

        }
    }

    @GetMapping("/profesional")
    public String listarProfesionales(ModelMap modelo) {
        List<Profesional> profesionales = profesionalServicio.listarProfesional();
        modelo.addAttribute("profesional", profesionales);

        return "listarProfesional";
    }

    @GetMapping("/modificarProfesional/{matricula}")
    public String modificarProfesional(@PathVariable String matricula, MultipartFile archivo, String email,
            String domicilio, String telefono, String password, String password2) throws MiException {

        profesionalServicio.modificarProfesional(archivo, matricula, email, password, password2, domicilio, telefono);

        return "redirect:/admin/profesional";
    }

    @GetMapping("/eliminar/{dni}")
    public String eliminarPaciente(@PathVariable String dni, ModelMap modelo) throws MiException {

        pacienteServicio.eliminarPaciente(dni);
        return "dashbo"; // Falta vista para saber a donde va cuando elimina paciente
    }

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
