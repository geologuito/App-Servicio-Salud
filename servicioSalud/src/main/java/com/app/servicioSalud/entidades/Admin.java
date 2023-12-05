package com.app.servicioSalud.entidades;

import com.app.servicioSalud.enumeraciones.RolEnum;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Admin {

    @Id
    private String id;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private RolEnum rol;

}
