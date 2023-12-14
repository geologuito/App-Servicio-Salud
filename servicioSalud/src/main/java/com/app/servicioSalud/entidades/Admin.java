package com.app.servicioSalud.entidades;

import com.app.servicioSalud.enumeraciones.RolEnum;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@Getter
@Setter
public class Admin {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private RolEnum rol;

    @OneToOne
    private Imagen imagen;

}
