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
import org.springframework.web.bind.annotation.PostMapping;
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
    public String panelAdministrativo(ModelMap modelo) {

        List<Paciente> pacientes = pacienteServicio.listarPaciente();
        List<Profesional> profesionales = profesionalServicio.listarProfesional();
        modelo.addAttribute("pacientes", pacientes);
        modelo.addAttribute("profesional", profesionales);

        return "dashboard";
    }

    @GetMapping("/pacientes")
    public String listarPacientes(ModelMap modelo) {
        List<Paciente> pacientes = pacienteServicio.listarPaciente();
        modelo.addAttribute("pacientes", pacientes);

        return "dashboard";
    }

    @GetMapping("/modificar/{dni}")
    public String modificar(@PathVariable String dni, MultipartFile archivo, String email, String domicilio,
            String telefono, String password, String password2) throws MiException {

        pacienteServicio.modificarPaciente(archivo, dni, email, domicilio, telefono, password, password2);

        return "redirect:/admin/pacientes";
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
            return "redirect:/admin/dashboard"; // si esta todo ok va a ir a panelPaciente

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

}
