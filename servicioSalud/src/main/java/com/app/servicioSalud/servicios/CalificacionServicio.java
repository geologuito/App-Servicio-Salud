/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.servicioSalud.servicios;

import com.app.servicioSalud.entidades.Calificacion;
import com.app.servicioSalud.entidades.Profesional;
import com.app.servicioSalud.repositorios.CalificacionRepositorio;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author EduRiu
 */
@Service
public class CalificacionServicio {

    @Autowired
    private CalificacionRepositorio calificacionRepositorio;

    @Transactional
    public void crearCalificacion(Profesional Profesion_id, Double mobiliario, Double puntualidad, Double atencion,  String comentario) {

        Calificacion calificacion = new Calificacion();

        calificacion.setProfesional(Profesion_id);
        calificacion.setPuntualidad(puntualidad);
        calificacion.setMobiliario(mobiliario);
        calificacion.setAtencion(atencion);
        calificacion.setComentario(comentario);

        calificacionRepositorio.save(calificacion);

    }

    // no se pueden modificar las calificacion, ni borrar
    public List<Calificacion> ListarTodas() {

        return calificacionRepositorio.findAll();

    }

    public List<Calificacion> listarCalificacion(String id) {
        
        List<Calificacion> calificacion = calificacionRepositorio.buscarPorMatricula(id);

        return calificacion;
    }
    
    public  List<Calificacion[]> calcularPromedio(String id){
        
      List<Calificacion[]> calificacion = calificacionRepositorio.calcularPromedio(id);
        
        return calificacion;
    }

}
