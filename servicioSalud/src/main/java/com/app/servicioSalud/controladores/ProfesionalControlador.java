
package com.app.servicioSalud.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.servicioSalud.excepciones.MiException;
import com.app.servicioSalud.servicios.ProfesionalServicio;

@Controller
@RequestMapping("/profesional") // localhost:8080/profesional
public class ProfesionalControlador {

    @Autowired
    private ProfesionalServicio profesionalServicio;

    @GetMapping("/registrar") // localhost:8080/profesional/registrar
    public String registrar() {
        return "registroProfesional";

    }

    @PostMapping("/registro")
    public String registro(@RequestParam String dni, @RequestParam String nombre, @RequestParam String apellido,

            @RequestParam String email, @RequestParam String domicilio, @RequestParam String telefono,
            @RequestParam String password, String password2, ModelMap modelo) {

        try {
            profesionalServicio.registrar(password2, dni, nombre, apellido, email, password, password2, domicilio,
                    telefono, null, password2, null, null);

            modelo.put("exito", "Usuario Registrado!");

        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("dni", dni);
            modelo.put("nombre", nombre);
            modelo.put("apellido", apellido);
            modelo.put("email", email);
            modelo.put("domicilio", domicilio);
            modelo.put("telefono", telefono);

            return "registroProfesional.html";
        }
        return "index.html";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {

        if (error != null) {
            modelo.put("error", "Usuario o Contraseña invalidos!");
        }

        return "login.html";
    }
}
