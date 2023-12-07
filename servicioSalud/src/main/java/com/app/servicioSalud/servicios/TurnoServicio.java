package com.app.servicioSalud.servicios;

import com.app.servicioSalud.entidades.Profesional;
import com.app.servicioSalud.entidades.Turno;
import com.app.servicioSalud.repositorios.TurnoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class TurnoServicio {

    @Autowired
    private TurnoRepositorio turnoRepositorio;
    @Autowired
    private ProfesionalServicio profesionalServicio;

    // Método para crear un turno
    @Transactional
    public Turno crearTurno(LocalTime hora, LocalDate fecha, String profesional_id) {

        Turno turno = new Turno();

        turno.setFecha(fecha);
        turno.setHora(hora);

        Profesional profesional = profesionalServicio.getOne(profesional_id);

        turno.setProfesional(profesional);

        return turnoRepositorio.save(turno);

    }

    // ----Listado de todos los turnos---//
    @Transactional
    public List<Turno> listarTurnos() {

        List<Turno> turnos = new ArrayList<>();

        return turnos;

    }

    // ---Busqueda por paciente---//
    @Transactional
    public List<Turno> listarTurnoPorPaciente(String id) {

        return turnoRepositorio.listarTurnosPorPaciente(id);

    }

    // ---Busqueda por fecha---//
    @Transactional
    public List<Turno> listarTurnoPorFecha(LocalDate fecha) {

        return turnoRepositorio.filtrarPorFecha(fecha);

    }
    @Transactional
    public void modificarTurno(Long id, LocalDate fecha, LocalTime hora) {
        Optional<Turno> respuesta = turnoRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Turno turno = respuesta.get();
            turno.setFecha(fecha);
            turno.setHora(hora);

            turnoRepositorio.save(turno);
        }

    }
    // ---Eliminar turno---//
    @Transactional
    public void eliminarTurno(Long id) {

        Optional<Turno> respuesta = turnoRepositorio.findById(id);
        Turno turno = new Turno();

        if (respuesta.isPresent()) {
            turno = respuesta.get();
            turnoRepositorio.delete(turno);
        }
    }

}
