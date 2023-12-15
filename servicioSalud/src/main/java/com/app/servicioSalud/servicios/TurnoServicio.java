package com.app.servicioSalud.servicios;

import com.app.servicioSalud.entidades.Paciente;
import com.app.servicioSalud.entidades.Profesional;
import com.app.servicioSalud.entidades.Turno;
import com.app.servicioSalud.repositorios.TurnoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class TurnoServicio {

    @Autowired
    private TurnoRepositorio turnoRepositorio;

    // Método para crear un turno
    @Transactional
    public void generarTurnos(LocalDate fecha, LocalTime horaInicio, LocalTime horaFin,
            Profesional profesional_id, Paciente paciente_id, Boolean reservado) {

        while (horaInicio.isBefore(horaFin)) {
            Turno turno = new Turno();
            turno.setFecha(fecha);
            turno.setHorario(horaInicio);
            turno.setProfesional(profesional_id);
            turno.setPaciente(paciente_id);
            turno.setReservado(false);

            turnoRepositorio.save(turno);

            horaInicio = horaInicio.plusMinutes(30);
        }
    }

    public void asignarPaciente(String id, Paciente paciente_id) {

        Optional<Turno> respuesta = turnoRepositorio.findById(id);

        if (respuesta.isPresent()) {
            Turno turno = respuesta.get();

            turno.setPaciente(paciente_id);
            turno.setReservado(Boolean.TRUE);

            turnoRepositorio.save(turno);

        }

    }

    public List<Turno> listarPorDia(String fecha) {

        String fechaComoString = fecha;
        LocalDate fechaComoLocalDate = LocalDate.parse(fechaComoString);

        List<Turno> turno = turnoRepositorio.filtrarPorFecha(fechaComoLocalDate);

        return turno;
    }

    public List<Turno> listarPorMatricula(String matricula) {

        List<Turno> turno = turnoRepositorio.filtrarPorMatricula(matricula);

        return turno;
    }

    public List<Turno> listarTodos() {
        return turnoRepositorio.findAll();
    }
}
