package com.app.servicioSalud.repositorios;

import com.app.servicioSalud.entidades.Profesional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfesionalRepositorio extends JpaRepository<Profesional, String> {

    @Query("select p from Profesional p where p.email = :email")
    public Profesional buscarPorEmail(@Param("email") String email);
}
