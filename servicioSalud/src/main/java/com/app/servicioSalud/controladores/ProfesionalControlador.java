package com.app.servicioSalud.controladores;

import com.app.servicioSalud.entidades.Profesional;
import com.app.servicioSalud.excepciones.MiException;
import com.app.servicioSalud.servicios.ProfesionalServicio;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/profesional")  //localhost:8080/profesional
public class ProfesionalControlador {

    @Autowired
    private ProfesionalServicio profesionalServicio;

    @GetMapping("/registrar")
    public String registrar(ModelMap modelo) {

        return "profesionalForm.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) String matricula, @RequestParam String dni, @RequestParam(required = false) String nombre,
            @RequestParam String apellido, @RequestParam String email, @RequestParam String password, @RequestParam String domicilio, @RequestParam String telefono, @RequestParam Boolean activo, @RequestParam String especialidad, @RequestParam Integer consulta, @RequestParam Date horario, ModelMap modelo) {

        try {

            profesionalServicio.registrar(matricula, dni, nombre, apellido, email, password, password, domicilio, telefono, activo, especialidad, consulta, horario); //si todo sale bien

            modelo.put("exito", "el profesional fue cargado con exito");
            return "profesionalForm.html";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());

            return "profesionalForm.html";  // cargamos de nuevo el formulario

        }

    }

    @GetMapping("/lista")
    public String listar(ModelMap modelo) {

        List<Profesional> profesionales = profesionalServicio.listaProfesional();
        modelo.addAttribute("profesionales", profesionales);
        return "profesionalList.html";

    }

    @GetMapping("/modificar/{matricula}")
    public String modificar(@PathVariable String matricula, ModelMap modelo) {

        modelo.put("profesional", profesionalServicio.getOne(matricula));
        return "profesionalModificar.html";
    }

    @PostMapping("/modificar/{matricula}")
    public String modificar(@PathVariable String matricula, String nombre, String apellido, String email, String password, String domicilio, String telefono, Boolean activo, String especialidad, Integer consulta, Date horario, ModelMap modelo) {
        try {

            profesionalServicio.modificarProfesional(matricula, email, nombre, apellido, email, password, password, domicilio, telefono, activo, especialidad, consulta, horario);
            return "redirect:../lista";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            return "libroModificar.html";
        }
    }

    @GetMapping("/eliminar/{matricula}")
    public String eliminarNoticia(@PathVariable String matricula, ModelMap modelo) throws MiException {

        profesionalServicio.eliminarProfesional(matricula);
        return "redirect:../lista";
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
