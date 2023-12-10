/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.servicioSalud.repositorios;

import com.app.servicioSalud.entidades.HistoriaClinica;
import com.app.servicioSalud.entidades.Paciente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author EduRiu
 */
@Repository
public interface HistoriaClinicaRepositorio extends JpaRepository<HistoriaClinica, String> {

    @Query("SELECT h FROM HistoriaClinica h WHERE h.paciente.id = :pacienteId")
    List<HistoriaClinica> buscarPorDNI(@Param("pacienteId") String pacienteId);
}
