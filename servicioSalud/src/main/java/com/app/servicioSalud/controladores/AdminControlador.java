package com.app.servicioSalud.controladores;

import com.app.servicioSalud.entidades.HistoriaClinica;
import com.app.servicioSalud.entidades.Paciente;
import com.app.servicioSalud.entidades.Profesional;
import com.app.servicioSalud.excepciones.MiException;
import com.app.servicioSalud.servicios.HistoriaClinicaServicio;
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
    @Autowired
    private HistoriaClinicaServicio HistoriaClinicaServicio;

    @GetMapping("/dashboard")
    public String panelAdministrativo(ModelMap modelo) {

        List<Paciente> pacientes = pacienteServicio.listarPaciente();
        List<Profesional> profesionales = profesionalServicio.listarProfesional();
        List<HistoriaClinica> hc = HistoriaClinicaServicio.listarHC();

        modelo.addAttribute("pacientes", pacientes);
        modelo.addAttribute("profesionales", profesionales);
        modelo.addAttribute("hc",hc);
        
        return "dashboard";
    }

    @GetMapping("/pacientes")
    public String listarPacientes(ModelMap modelo) {
        List<Paciente> pacientes = pacienteServicio.listarPaciente();
        modelo.addAttribute("pacientes", pacientes);

        return "dashboard";
    }

    @GetMapping("/modificarPaciente/{dni}")
    public String modificarPaciente(@PathVariable String dni, MultipartFile archivo, String email, String domicilio,
            String telefono, String password, String password2) throws MiException {

        pacienteServicio.modificarPaciente(archivo, dni, email, domicilio, telefono, password, password2);

        return "modificarPaciente";
    }
    @PostMapping("/modificar/{dni}")
    public String modificar(@PathVariable String dni, String nombre, String apellido, String email, String domicilio, String telefono, String password,
            String edad, MultipartFile archivo,
            ModelMap modelo) {
        try {
            pacienteServicio.modificarPaciente(archivo, dni, email, domicilio, telefono, password, password);
            return "dashboard"; // si esta todo ok va a ir a Dasboard- Esta regregando al login

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            return "modificarPaciente"; // mapear con html

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
