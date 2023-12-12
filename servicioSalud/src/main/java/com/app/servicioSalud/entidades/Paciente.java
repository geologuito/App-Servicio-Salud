package com.app.servicioSalud.entidades;

import com.app.servicioSalud.enumeraciones.*;
import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
public class Paciente {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String dni;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String telefono;
    private String domicilio;
    private String edad;

    @OneToOne
    private Imagen imagen;

    @Enumerated(EnumType.STRING)
    private RolEnum rol;

    @Enumerated(EnumType.STRING)
    private ObraSocialEnum obraSocial;

    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL)
    private HistoriaClinica historiaClinica;

    @ManyToOne
    @JoinColumn(name = "profesional_matricula")
    private Profesional profesional;
}
