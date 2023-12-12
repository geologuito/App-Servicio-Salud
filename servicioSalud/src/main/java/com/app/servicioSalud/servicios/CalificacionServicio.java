package com.app.servicioSalud.servicios;

import com.app.servicioSalud.entidades.Calificacion;
import com.app.servicioSalud.entidades.Profesional;
import com.app.servicioSalud.repositorios.CalificacionRepositorio;
import java.util.List;
import javax.persistence.Tuple;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    
    public Tuple calcularPromedio(String id){
        
      Tuple calificacion = calificacionRepositorio.calcularPromedio(id);
        
        return calificacion;
    }

}
