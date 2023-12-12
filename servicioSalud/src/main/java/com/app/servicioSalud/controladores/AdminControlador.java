package com.app.servicioSalud.controladores;

import com.app.servicioSalud.entidades.Paciente;
import com.app.servicioSalud.entidades.Profesional;
import com.app.servicioSalud.excepciones.MiException;
import com.app.servicioSalud.servicios.PacienteServicio;
import com.app.servicioSalud.servicios.ProfesionalServicio;
import java.util.Date;
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
        List<Paciente> pacientes = pacienteServicio.listarPacientes();
        modelo.addAttribute("pacientes", pacientes);

        return "pacienteList";
    }

    @GetMapping("/modificarPaciente/{dni}")
    public String modificarPaciente(@PathVariable String idPaciente, String dni, String nombre, String apellido, MultipartFile archivo, String email, String domicilio, String telefono, String password, String password2, String edad) throws MiException {

        pacienteServicio.actualizar(archivo, idPaciente, nombre, apellido, dni, email, domicilio, telefono, password, password2, edad);

        return "redirect:/admin/pacientes";
    }

    @GetMapping("/profesional")
    public String listarProfesionales(ModelMap modelo) {
        List<Profesional> profesionales = profesionalServicio.listarProfesionales();
        modelo.addAttribute("profesional", profesionales);

        return "profesionalList";
    }

    @GetMapping("/modificarProfesional/{matricula}")
    public String modificarProfesional(@PathVariable String idProfesional, String matricula, String dni, String nombre, String apellido, MultipartFile archivo, String email, String domicilio, String telefono, String password, String password2, String especialidad, Date horario) throws MiException {

        profesionalServicio.actualizar(archivo, idProfesional, matricula, dni, nombre, apellido, email, password, password2, domicilio, telefono, Boolean.TRUE, especialidad, Integer.MIN_VALUE, horario);

        return "redirect:/admin/profesionales";
    }

}
