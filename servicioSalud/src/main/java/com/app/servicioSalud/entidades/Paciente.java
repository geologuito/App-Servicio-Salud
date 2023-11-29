package com.app.servicioSalud.entidades;

import com.app.servicioSalud.enumeraciones.*;
import javax.persistence.*;
import lombok.Data;

@Entity
@Data
public class Paciente {

    @Id
    private String dni;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String telefono;
    private String domicilio;

    @Enumerated(EnumType.STRING)
    private RolEnum rol;

    @Enumerated(EnumType.STRING)
    private ObraSocialEnum obraSocial;

    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL)
    private HistoriaClinica historiaClinica;
}
