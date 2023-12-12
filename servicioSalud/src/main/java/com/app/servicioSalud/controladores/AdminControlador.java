package com.app.servicioSalud.controladores;

import com.app.servicioSalud.entidades.Paciente;
import com.app.servicioSalud.entidades.Profesional;
import com.app.servicioSalud.excepciones.MiException;
import com.app.servicioSalud.servicios.PacienteServicio;
import com.app.servicioSalud.servicios.ProfesionalServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin")
public class AdminControlador {

    @Autowired
    private PacienteServicio pacienteServicio;
    @Autowired
    private ProfesionalServicio profesionalServicio;

    @GetMapping("/dashboard")
    public String panelAdministrativo() {
        return "panelAdmin.html";
    }

    @GetMapping("/pacientes")
    public String listarPacientes(ModelMap modelo) {
        List<Paciente> pacientes = pacienteServicio.listarPaciente();
        modelo.addAttribute("pacientes", pacientes);

        return "pacienteList";
    }

    @GetMapping("/modificarPaciente/{dni}")
    public String modificarPaciente(@PathVariable String dni, MultipartFile archivo, String email, String domicilio, String telefono, String password, String password2) throws MiException {

        pacienteServicio.modificarPaciente(archivo, dni, email, domicilio, telefono, password, password2);

        return "redirect:/admin/pacientes";
    }

    @GetMapping("/profesional")
    public String listarProfesionales(ModelMap modelo) {
        List<Profesional> profesionales = profesionalServicio.listarProfesional();
        modelo.addAttribute("profesional", profesionales);

        return "profesionalList";
    }

    @GetMapping("/modificarProfesional/{matricula}")
    public String modificarProfesional(@PathVariable String matricula, MultipartFile archivo, String email, String domicilio, String telefono, String password, String password2) throws MiException {

        profesionalServicio.modificarProfesional(archivo, matricula, email, password, password2, domicilio, telefono);

        return "redirect:/admin/profesionales";
    }

}
