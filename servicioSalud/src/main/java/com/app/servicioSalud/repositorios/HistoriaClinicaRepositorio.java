package com.app.servicioSalud.repositorios;

import com.app.servicioSalud.entidades.HistoriaClinica;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoriaClinicaRepositorio extends JpaRepository<HistoriaClinica, String> {

    @Query("SELECT h FROM HistoriaClinica h WHERE h.paciente.id = :pacienteId")
    List<HistoriaClinica> buscarPorDNI(@Param("pacienteId") String pacienteId);
}
