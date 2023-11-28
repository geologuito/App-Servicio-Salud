package com.app.servicioSalud.repositorios;

import com.app.servicioSalud.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepositorio extends JpaRepository<Paciente,String> {
 
    @Query ("select p from Paciente p where p.dni = :dni")
    public Paciente buscarPorDNI(@Param("dni") String dni);
    
    @Query("select p from Paciente p where p.email = :email")
    public Paciente buscarPorEmail(@Param("email") String email);
}
