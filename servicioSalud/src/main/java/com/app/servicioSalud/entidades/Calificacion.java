package com.app.servicioSalud.entidades;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
<<<<<<< HEAD
import org.hibernate.annotations.GenericGenerator;
=======
>>>>>>> ema

@Data
@Getter
@Setter
@Entity
@Getter
@Setter
public class Calificacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name = "profesional_id")
    private Profesional profesional;
    
    private Double puntualidad;
    private Double mobiliario;
    private Double atencion;
    private String comentario; 
          
    
    
     
    
}
