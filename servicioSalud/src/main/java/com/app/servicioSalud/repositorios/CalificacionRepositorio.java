package com.app.servicioSalud.repositorios;

import com.app.servicioSalud.entidades.Calificacion;

import java.util.List;
import javax.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CalificacionRepositorio extends JpaRepository<Calificacion, String> {

//    @Query("SELECT c FROM Calificacion c WHERE c.profesional.matricula = :profesionalId")
//    List<Calificacion> buscarPorMatricula(@Param("profesioanlId") String profesionalId);
    @Query("SELECT c FROM Calificacion c WHERE c.profesional.matricula = :profesionalId")
    List<Calificacion> buscarPorMatricula(@Param("profesionalId") String profesionalId);

    @Query("SELECT ROUND(AVG(c.puntualidad)), ROUND(AVG(c.mobiliario)),ROUND(AVG(c.atencion))"
            + "FROM Calificacion c "
            + "WHERE c.profesional.matricula = :profesionalId")
    Tuple calcularPromedio(@Param("profesionalId") String profesionalId);

}
