package com.app.servicioSalud.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.servicioSalud.entidades.Profesional;
import com.app.servicioSalud.servicios.ProfesionalServicio;


@Controller
@RequestMapping("/") // localhost:8080
public class PortalControlador {

    @Autowired
    private ProfesionalServicio profesionalServicio;

    @GetMapping("/") // localhost:8080/ este queda en portal
    public String index() {

        return "index";
    }
    @GetMapping("/servicios")
    public String servicios(){
        
        return "servicios";
    }
    @GetMapping("/profesionales")
    public String profesionales() {

        return "profesionales";
    }

    @GetMapping("/especialidades")
    public String especialidades(ModelMap modelo) {

        List<Profesional> profesionales = profesionalServicio.listarProfesional();
            modelo.addAttribute("profesionales", profesionales);

        return "especialidades";
    }
    
}


