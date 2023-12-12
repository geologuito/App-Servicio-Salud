package com.app.servicioSalud.controladores;

import com.app.servicioSalud.entidades.HistoriaClinica;
import com.app.servicioSalud.entidades.Paciente;
import com.app.servicioSalud.entidades.Profesional;
import com.app.servicioSalud.servicios.CalificacionServicio;
import com.app.servicioSalud.servicios.HistoriaClinicaServicio;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/calificacion")
public class CalificacionControlador {

    @Autowired
    private CalificacionServicio calificacionServicio;

    @Autowired
    private HistoriaClinicaServicio historiaClinicaServicio;
    
    @PreAuthorize("hasAnyRole('ROLE_PACIENTE')")
    @GetMapping("/crear/{id}") // localhost:8080/paciente/registrar
    public String registrar(ModelMap modelo, HttpSession session) {
        
        //  @PathVariable String id - aca vamos a usar el login
        Paciente user =(Paciente) session.getAttribute("pacientesession");
        
        String id;
        id = user.getDni();
        
        
        List<HistoriaClinica> paciente = historiaClinicaServicio.listarPorDNI(id);

        modelo.addAttribute("paciente", paciente);

        return "listaCalificacion";
    }

    @GetMapping("/valoracion/{id}")
    public String valoracion(@PathVariable String id, ModelMap modelo) {

        HistoriaClinica puntual = historiaClinicaServicio.getOne(id);

        modelo.addAttribute("puntual", puntual);

        System.out.println("creada con exito");

        return "calificacionConsulta";
    }

    @PostMapping("/creada")
    public String creada(@RequestParam String id, @RequestParam Profesional profesional_id, @RequestParam Double puntualidad,
            @RequestParam Double mobiliario, @RequestParam Double atencion, @RequestParam String comentario) {

        calificacionServicio.crearCalificacion(profesional_id, mobiliario, puntualidad, atencion, comentario);
        historiaClinicaServicio.modificacionEstado(id);
        System.out.println("creada con exito");

        return "redirect:/";
    }

}
