package com.app.servicioSalud.repositorios;

import com.app.servicioSalud.entidades.Profesional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfesionalRepositorio extends JpaRepository<Profesional, String> {

    @Query("SELECT p FROM Profesional p WHERE p.matricula = :matricula")
    public Profesional buscarPorMatricula(@Param("matricula") String matricula);

    @Query("SELECT p FROM Profesional p WHERE p.email = :email")
    public Profesional buscarPorEmail(@Param("email") String email);
    
     @Query("SELECT p FROM Profesional p WHERE p.especialidad = :especialidad")
    public Profesional buscarPorEspecialidad(@Param("especialidad") String especialidad);

    
}
