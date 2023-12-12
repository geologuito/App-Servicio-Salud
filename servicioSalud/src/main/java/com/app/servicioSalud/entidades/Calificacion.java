package com.app.servicioSalud.entidades;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
public class Calificacion {

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //private Integer id;
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profesional_id")
    private Profesional profesional;

    private Double puntualidad;
    private Double mobiliario;
    private Double atencion;
    private String comentario;

}
