package com.app.servicioSalud.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/") // localhost:8080
public class PortalControlador {

    @GetMapping("/") // localhost:8080/ este queda en portal
    public String index() {
<<<<<<< HEAD
        return "servicios";
=======
        return "index";

        // agregar login,perfil. agregar todo el controlador de admin.
>>>>>>> developer
    }
}
