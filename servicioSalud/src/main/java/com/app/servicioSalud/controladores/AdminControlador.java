package com.app.servicioSalud.controladores;

import com.app.servicioSalud.entidades.Paciente;
import com.app.servicioSalud.entidades.Profesional;
import com.app.servicioSalud.excepciones.MiException;
import com.app.servicioSalud.servicios.AdminServicio;
import com.app.servicioSalud.servicios.PacienteServicio;
import com.app.servicioSalud.servicios.ProfesionalServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
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

            return "registroAdmin";
        }
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/dashboard")
    public String panelAdministrativo(ModelMap modelo) {

        List<Paciente> pacientes = pacienteServicio.listarPaciente();
        List<Profesional> profesionales = profesionalServicio.listarProfesional();
        modelo.addAttribute("pacientes", pacientes);
        modelo.addAttribute("profesionales", profesionales);

        return "dashboard";
    }

    @GetMapping("/pacientes")
    public String listarPacientes(ModelMap modelo) {
        List<Paciente> pacientes = pacienteServicio.listarPaciente();
        modelo.addAttribute("pacientes", pacientes);

        return "dashboard";
    }

    @GetMapping("/profesional")
    public String listarProfesionales(ModelMap modelo) {
        List<Profesional> profesionales = profesionalServicio.listarProfesional();
        modelo.addAttribute("profesional", profesionales);

        return "listarProfesional";
    }
}
