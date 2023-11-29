
package com.app.servicioSalud.entidades;

import com.app.servicioSalud.enumeraciones.RolEnum;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
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
    private String id;
    
=======

    @Id
    private String id;
>>>>>>> developer
     @Enumerated(EnumType.STRING)
    private RolEnum rol;
    
}
