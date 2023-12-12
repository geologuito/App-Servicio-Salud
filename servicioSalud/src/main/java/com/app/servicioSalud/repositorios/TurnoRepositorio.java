package com.app.servicioSalud.repositorios;

import com.app.servicioSalud.entidades.Paciente;
import com.app.servicioSalud.entidades.Turno;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnoRepositorio extends JpaRepository<Turno, String> {

    @Query("SELECT t FROM Turno t WHERE t.paciente.dni = :dni")
    List<Paciente> listarTurnosPorPaciente(@Param("dni") String dni);

    @Query("SELECT t FROM Turno t WHERE t.fecha = :fecha")
    List<Turno> filtrarPorFecha(@Param("fecha") LocalDate fecha);
}
