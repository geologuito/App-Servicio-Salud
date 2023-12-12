package com.app.servicioSalud.controladores;

import com.app.servicioSalud.entidades.HistoriaClinica;
import com.app.servicioSalud.entidades.Paciente;
import com.app.servicioSalud.entidades.Profesional;
import com.app.servicioSalud.servicios.HistoriaClinicaServicio;
import com.app.servicioSalud.servicios.PacienteServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/hc")
public class HistoriaClinicaControlador {

    @Autowired
    private HistoriaClinicaServicio historiaClinicaServicio;
    @Autowired
    private PacienteServicio PacienteServicio;

    @GetMapping("/crear/{id}") // localhost:8080/paciente/registrar
    public String registrar(ModelMap modelo, HttpSession session, @PathVariable String id) {

        Profesional profesional = (Profesional) session.getAttribute("profesionalsession");

        Paciente paciente = PacienteServicio.getOne(id);

        modelo.addAttribute("profesional", profesional);
        modelo.addAttribute("paciente", paciente);

        return "historiaclinica";
    }

    @PostMapping("/creada")
    public String registro(@RequestParam Paciente paciente_dni,
            @RequestParam Profesional profesional_id, @RequestParam String dx, @RequestParam String titulo, @RequestParam String tratamiento) {

        System.out.println("antes del exito");

        historiaClinicaServicio.crearHC(profesional_id, paciente_dni, titulo, dx, tratamiento);

        System.out.println("creada con exito");

        return "redirect:/";
    }

    @GetMapping("/listar/{id}") // localhost:8080/paciente/registrar
    public String Listar(ModelMap modelo, HttpSession session, @PathVariable String id) {

        List<HistoriaClinica> hc = historiaClinicaServicio.listarPorDNI(id);

        modelo.addAttribute("hc", hc);

        return "listaHistoria";

    }
}
