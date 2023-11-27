package com.app.servicioSalud.entidades;

import com.app.servicioSalud.enumeraciones.RolEnum;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Data
public class Usuario {

    @Id
    @Column(unique = true)
    private String dni;

    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String domicilio;

    private String telefono;
    
    //@OneToOne
    //private Paciente paciente;
    
    //@OneToOne
    //private Profesional profecional;
    
    @Enumerated(EnumType.STRING)
    private RolEnum rol;
    }



    /*@OneToMany(mappedBy = "usuario")
    private List<Turno> turnos;
    @OneToMany(mappedBy = "usuario")
    private List<Profesional> profesionales;
    @Enumerated(EnumType.STRING)
    private RolEnum rol;*/
