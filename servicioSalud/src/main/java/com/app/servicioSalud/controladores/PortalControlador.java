package com.app.servicioSalud.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/") // localhost:8080
public class PortalControlador {

    @GetMapping("/") // localhost:8080/ este queda en portal
    public String index() {

        return "index";
    }
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {

        if (error != null) {
            modelo.put("error", "Usuario o Contraseña invalidos!");
        }

        return "login";
    }
    
    @GetMapping("/servicios")
    public String servicios(){
        
        return "servicios";
    }
    @GetMapping("/profesionales")
    public String profesionales() {

        return "profesionales";
    }
    @GetMapping("/us")
    public String us() {

        return "us";
    }
    
}
