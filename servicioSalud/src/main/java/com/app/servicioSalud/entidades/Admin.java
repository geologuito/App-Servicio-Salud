
package com.app.servicioSalud.entidades;

import com.app.servicioSalud.enumeraciones.RolEnum;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
<<<<<<< HEAD
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
=======
>>>>>>> developer
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Luciano Otegui
 */
@Entity
@Data
public class Admin{
<<<<<<< HEAD
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
=======
    
>>>>>>> developer
    
     @Enumerated(EnumType.STRING)
    private RolEnum rol;
    
}
