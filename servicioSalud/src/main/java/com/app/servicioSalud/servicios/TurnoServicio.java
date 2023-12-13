package com.app.servicioSalud.servicios;

import com.app.servicioSalud.entidades.Paciente;
import com.app.servicioSalud.entidades.Profesional;
import com.app.servicioSalud.entidades.Turno;
import com.app.servicioSalud.repositorios.TurnoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

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
}

//    // ----Listado de todos los turnos---//
//    @Transactional
//    public List<Turno> listarTurnos() {
//
//        return turnoRepositorio.findAll();
//
//    }
//
//    // ---Busqueda por paciente---//
//    @Transactional
//    public List<Paciente> listarTurnoPorPaciente(String id) {
//
//        return turnoRepositorio.listarTurnosPorPaciente(id);
//
//    }
//
//    // ---Busqueda por fecha---//
//    @Transactional
//    public List<Turno> listarTurnoPorFecha(LocalDate fecha) {
//
//        return turnoRepositorio.filtrarPorFecha(fecha);
//
//    }
//
//    @Transactional
//    public void modificarTurno(String id, LocalDate horario, LocalDate fecha, Profesional profesional_id, Paciente Paciente_id) {
//
//        Optional<Turno> respuesta = turnoRepositorio.findById(id);
//
//        if (respuesta.isPresent()) {
//
//            Turno turno = respuesta.get();
//
//            turno.setProfesional(profesional_id);
//            turno.setFecha(fecha);
//            turno.setHorario(horario);
//
//            turnoRepositorio.save(turno);
//        }
//
//    }
//
//    // ---Eliminar turno---//
//    @Transactional
//    public void eliminarTurno(String id) {
//
//        Optional<Turno> respuesta = turnoRepositorio.findById(id);
//        Turno turno = new Turno();
//
//        if (respuesta.isPresent()) {
//            turno = respuesta.get();
//            turnoRepositorio.delete(turno);
//        }
//    }
