package com.app.servicioSalud.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/") // localhost:8080
public class PortalControlador {

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
    
}