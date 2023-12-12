package com.app.servicioSalud.repositorios;

import com.app.servicioSalud.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepositorio extends JpaRepository<Paciente, String> {

    @Query("SELECT p FROM Paciente p WHERE p.id = :id")
    public Paciente buscarPorID(@Param("id") String id);

    @Query("SELECT p FROM Paciente p WHERE p.email = :email")
    public Paciente buscarPorEmail(@Param("email") String email);
}
