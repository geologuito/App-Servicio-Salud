
package com.app.servicioSalud.entidades;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Data
public class Turno {
    
    @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)    
    private String idTurno;   
    private Date fecha;
    private Date hora;
    
    @OneToOne
    private Calificacion calificacion;
    @OneToOne
    private Profesional profesional;
    @OneToOne
    private Usuario usuario;
    
    
}
