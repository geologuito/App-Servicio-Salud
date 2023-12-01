
package com.app.servicioSalud.controladores;

import com.app.servicioSalud.entidades.Profesional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.app.servicioSalud.excepciones.MiException;
import com.app.servicioSalud.servicios.ProfesionalServicio;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {

        if (error != null) {
            modelo.put("error", "Usuario o Contraseña invalidos!");
        }

        return "loginProfesional.html";
    }

    @GetMapping("/listaProfesionales")
    public String listarProfesional(ModelMap modelo) {

        List<Profesional> profesionales = profesionalServicio.listaProfesional();
        modelo.addAttribute("profesional", profesionales);
        return "profesionalList.html";

    }

    @GetMapping("/modificar/{matricula}")
    public String modificarProfesional(@PathVariable String matricula, ModelMap modelo) {

        modelo.put("profesional", profesionalServicio.getOne(matricula));
        return "profesionalModificar.html";
    }

    @PostMapping("/modificar/{matricula}")
    public String modificar(@PathVariable String matricula, String email, String password, String domicilio, String telefono, ModelMap modelo) {
        try {

            profesionalServicio.modificarProfesional(matricula, email, password, password, domicilio, telefono);
            return "redirect:/panelProfesional"; //Decidir donde va cuando modifica prof

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            return "profesionalModificar.html";
        }
    }

    @GetMapping("/eliminar/{matricula}")
    public String eliminarProfecional(@PathVariable String matricula, ModelMap modelo) throws MiException {

        profesionalServicio.eliminarProfesional(matricula);
        return "redirect:/index"; //Falta vista para saber a donde va cuando elimina prof
    }

    @DeleteMapping("/eliminar/{matricula}")
    public ResponseEntity<String> eliminarProfesional(@PathVariable String matricula) {
        try {
            profesionalServicio.eliminarProfesional(matricula);
            return new ResponseEntity<>("Profesional eliminado con éxito", HttpStatus.OK);
        } catch (MiException ex) {
            return new ResponseEntity<>("Error al eliminar el Profesional: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
