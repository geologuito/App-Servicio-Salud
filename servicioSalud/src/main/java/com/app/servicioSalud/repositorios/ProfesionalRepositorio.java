package com.app.servicioSalud.repositorios;

import com.app.servicioSalud.entidades.Profesional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfesionalRepositorio extends JpaRepository<Profesional,String> {
    
}
