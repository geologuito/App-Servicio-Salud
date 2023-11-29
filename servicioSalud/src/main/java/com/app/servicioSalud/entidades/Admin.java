
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

    @Id
    private String id;
     @Enumerated(EnumType.STRING)
    private RolEnum rol;
    
}
