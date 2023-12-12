package com.app.servicioSalud.controladores;

import com.app.servicioSalud.entidades.Paciente;
import com.app.servicioSalud.entidades.Profesional;
import com.app.servicioSalud.entidades.Turno;
import com.app.servicioSalud.servicios.ProfesionalServicio;
import com.app.servicioSalud.servicios.TurnoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/turno")
public class TurnoControlador {

    @Autowired
    private TurnoServicio turnoServicio;

    @Autowired
    private ProfesionalServicio profesionalServicio;

    @GetMapping("/calendario")
    public String reservar(ModelMap modelo, HttpSession session) {

        
        return "turnos";
    }

    @PostMapping("/creado")
    public String crearTurno(ModelMap modelo,
            @RequestParam String fecha,
            @RequestParam String horaInicio,
            @RequestParam String horaFin,
            @RequestParam Profesional profesional_id) {

        String fechaComoString = fecha;
        LocalDate fechaComoLocalDate = LocalDate.parse(fechaComoString);

        DateTimeFormatter formato2 = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime horaInicial = LocalTime.parse(horaInicio, formato2);
       
        DateTimeFormatter formato1 = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime horaFinal = LocalTime.parse(horaFin, formato1);

        turnoServicio.generarTurnos(fechaComoLocalDate, horaInicial, horaFinal, profesional_id, null, Boolean.FALSE);
        

        return "redirect:/";
    }

//    @GetMapping("/listar")
//    public String listarTurnos(ModelMap modelo) {
//        List<Turno> turnos = turnoServicio.listarTurnos();
//        modelo.addAttribute("turnos", turnos);
//        return "lista_turnos";
//    }
//
////    @GetMapping("/buscarPorPaciente")
////    public String buscarTurnosPorPaciente(ModelMap modelo, @RequestParam String id) {
////        List<Turno> turnos = turnoServicio.listarTurnoPorPaciente(id);
////        modelo.addAttribute("turnos", turnos);
////        return "lista_turnos";
////    }
//
//    @GetMapping("/buscarPorFecha")
//    public String buscarTurnosPorFecha(ModelMap modelo, @RequestParam LocalDate fecha) {
//        
//        List<Turno> turnos = turnoServicio.listarTurnoPorFecha(fecha);
//        modelo.addAttribute("turnos", turnos);
//        return "lista_turnos";
//    }
//    @PostMapping("/modificar/{id}")
//    public String modificarTurno(ModelMap modelo, @PathVariable Long id, @RequestParam String fecha, @RequestParam String hora) {
//        try {
//            // Convertir String a LocalDate y LocalTime
//            LocalDate fechaLocalDate = LocalDate.parse(fecha);
//            LocalTime horaLocalTime = LocalTime.parse(hora);
//
//            turnoServicio.modificarTurno(id, fechaLocalDate, horaLocalTime);
//
//            modelo.put("exito", "¡El turno fue modificado!");
//        } catch (Exception ex) {
//            modelo.put("error", ex.getMessage());
//        }
//        return "redirect:/";
//    }
//    @PostMapping("/eliminar/{id}")
//    public String eliminarTurno(ModelMap modelo, @PathVariable Long id) {
//        try {
//            turnoServicio.eliminarTurno(id);
//            modelo.put("exito", "¡El turno fue eliminado!");
//        } catch (Exception ex) {
//            modelo.put("error", ex.getMessage());
//        }
//        return "redirect:/";
//    }
}
