package com.app.servicioSalud.controladores;

import com.app.servicioSalud.entidades.Profesional;
import com.app.servicioSalud.excepciones.MiException;
import com.app.servicioSalud.servicios.ProfesionalServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
            @RequestParam String apellido, @RequestParam String email, @RequestParam String password, @RequestParam String domicilio, @RequestParam String telefono, @RequestParam Boolean activo, @RequestParam String especialidad, @RequestParam Integer consulta, ModelMap modelo) {

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
    public String modificar(@PathVariable String matricula, String titulo, String idAutor, String idEditorial, Integer ejemplares, ModelMap modelo) {

        try {

            libroServicio.modificarLibro(isbn, titulo, idAutor, idEditorial, ejemplares);
            return "redirect:../lista";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            return "libroModificar.html";
        }
    }

    @GetMapping("/eliminar/{isbn}")
    public String eliminarNoticia(@PathVariable Long isbn, ModelMap modelo) throws MiException {

        profesionalServicio.eliminarProfesional(id);
        return "redirect:../lista";
        /*    try {

            noticiaServicio.eliminarNoticias(id);
            return "redirect:../lista";

        } catch (MiException ex) {

            modelo.put("error", ex.getMessage());
            return "noticiaEliminar.html";
        } */
    }

    @DeleteMapping("/eliminar/{isbn}")
    public ResponseEntity<String> eliminarLibro(@PathVariable Long isbn) {
        try {
            libroServicio.eliminarLibro(isbn);
            return new ResponseEntity<>("Libro eliminado con éxito", HttpStatus.OK);
        } catch (MiException ex) {
            return new ResponseEntity<>("Error al eliminar el Libro: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
