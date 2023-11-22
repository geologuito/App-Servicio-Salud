
package com.app.servicioSalud.entidades;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Data
public class Profesional {
    
     @Id
    private String Matricula;
    
     private String nombre;
     private String apellido;
     private String correo;
     private String password;
     private Boolean activo;
     private String especialidad;
     private Integer consulta;
     private Date horario;
  
     @OneToMany
     private Usuario usuario;
     @OneToMany
     private Turno turno;
     @OneToMany
     private HistoriaClinica historiaClinica;
     
     
    
     
     
     
     
     
    
}
