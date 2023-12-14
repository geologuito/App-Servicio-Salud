package com.app.servicioSalud.controladores;

import com.app.servicioSalud.entidades.Paciente;
import com.app.servicioSalud.entidades.Profesional;
import com.app.servicioSalud.entidades.Turno;
import com.app.servicioSalud.repositorios.PacienteRepositorio;
import com.app.servicioSalud.repositorios.ProfesionalRepositorio;
import com.app.servicioSalud.repositorios.TurnoRepositorio;
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
    private TurnoRepositorio turnoRepositorio;

    @Autowired
    ProfesionalServicio profesionalServicio;

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

        List<Profesional> profesionales = profesionalServicio.listarProfesional();
        modelo.addAttribute("profesionales", profesionales);

        String fechaComoString = fecha;
        LocalDate fechaComoLocalDate = LocalDate.parse(fechaComoString);

        DateTimeFormatter formato2 = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime horaInicial = LocalTime.parse(horaInicio, formato2);

        DateTimeFormatter formato1 = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime horaFinal = LocalTime.parse(horaFin, formato1);

        turnoServicio.generarTurnos(fechaComoLocalDate, horaInicial, horaFinal, profesional_id, null, Boolean.FALSE);

        return "redirect:/";
    }

    @GetMapping("/buscarFecha")
    public String listarPorDia(ModelMap modelo, HttpSession session) {

        Paciente paciente = (Paciente) session.getAttribute("pacientesession");

        modelo.addAttribute("paciente", paciente);

        return "buscarTurno";

    }

    @PostMapping("/buscarTurno")
    public String reservarTurno(@RequestParam String fecha, ModelMap modelo, HttpSession session) {

        Paciente paciente = (Paciente) session.getAttribute("pacientesession");
        modelo.addAttribute("paciente", paciente);

        String fechaComoString = fecha;
        LocalDate fechaComoLocalDate = LocalDate.parse(fechaComoString);

        List<Turno> turnoDia = turnoRepositorio.filtrarPorFecha(fechaComoLocalDate);

        modelo.addAttribute("turno", turnoDia);

        return "listaTurnoFecha";
    }

    @GetMapping("/reservado/{id}")
    public String turnoOK(@PathVariable String id, HttpSession session) {

        Paciente paciente = (Paciente) session.getAttribute("pacientesession");
        System.out.println(id.toString());

        turnoServicio.asignarPaciente(id, paciente);

        return "redirect:/";
    }

    @GetMapping("/buscarDia")
    public String citas() {

        return "buscarDia";
    }

    @PostMapping("/citas")
    public String citaDelDia(@RequestParam String fecha, ModelMap modelo) {

        String fechaComoString = fecha;
        LocalDate fechaComoLocalDate = LocalDate.parse(fechaComoString);

        List<Turno> turnoDia = turnoRepositorio.turnosDelDia(fechaComoLocalDate);

        modelo.addAttribute("turno", turnoDia);

        return "citas";

    }
}