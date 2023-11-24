package com.app.servicioSalud.entidades;

import com.app.servicioSalud.enumeraciones.Rol;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Data
public class Usuario {

    @Id
    private String dni;

    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String domicilio;
   
    private String telefono;

   
    @OneToMany(mappedBy = "usuario")
    private List<Turno> turnos;
    @OneToMany(mappedBy = "usuario")
    private List<Profesional> profesionales;
    @Enumerated(EnumType.STRING)
    private Rol rol;
}
