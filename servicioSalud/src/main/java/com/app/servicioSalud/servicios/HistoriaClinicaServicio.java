/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.servicioSalud.servicios;

import com.app.servicioSalud.entidades.HistoriaClinica;
import com.app.servicioSalud.entidades.Paciente;
import com.app.servicioSalud.entidades.Profesional;
import com.app.servicioSalud.excepciones.MiException;
import com.app.servicioSalud.repositorios.HistoriaClinicaRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author EduRiu
 */
@Service
public class HistoriaClinicaServicio {

    @Autowired
    private HistoriaClinicaRepositorio historiaClinicaRepositorio;

    @Transactional
    public void crearHC(Profesional profesional_id, Paciente paciente_id, String titulo, String dx, String tratamiento) {

        HistoriaClinica historiaClinica = new HistoriaClinica();
     
        historiaClinica.setProfesional(profesional_id);
        historiaClinica.setPaciente(paciente_id);
        historiaClinica.setTitulo(titulo);
        historiaClinica.setDx(dx);
        historiaClinica.setTratamiento(tratamiento);
        historiaClinica.setAlta(new Date());

        historiaClinicaRepositorio.save(historiaClinica);

    }

    public void modificacionHC(String id, Profesional profesional_id, Paciente paciente_id,String titulo, String dx, String tratamiento) {

        Optional<HistoriaClinica> respuesta = historiaClinicaRepositorio.findById(id);

        if (respuesta.isPresent()) {
            HistoriaClinica historiaClinica = respuesta.get();

            historiaClinica.setTitulo(titulo);
            historiaClinica.setDx(dx);
            historiaClinica.setTratamiento(tratamiento);

            historiaClinicaRepositorio.save(historiaClinica);

        }

    }

    public List<HistoriaClinica> listarHC() {

        return historiaClinicaRepositorio.findAll();

    }
    
    public List<HistoriaClinica> listarPorDNI(String paciente_id){
        
        List<HistoriaClinica> hc = historiaClinicaRepositorio.buscarPorDNI(paciente_id);
        
        return hc;
    }

    public HistoriaClinica getOne(String id) {
        return historiaClinicaRepositorio.getReferenceById(id);
    }

}
