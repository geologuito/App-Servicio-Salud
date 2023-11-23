
package com.app.servicioSalud.entidades;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Data;


@Entity
@Data
public class Usuario {
    
    @Id
    private Integer dni;
    
    private String nombre;
    private String apellido;
    private String correo;
    private String password;    
    private String calle;
    private Integer numero;
    private String telefono;
     
    
     @OneToOne
     private HistoriaClinica historiaClinica;
     @OneToMany
     private Turno turno;
      
        
    
}
