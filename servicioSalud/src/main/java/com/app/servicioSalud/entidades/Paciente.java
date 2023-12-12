package com.app.servicioSalud.entidades;

import com.app.servicioSalud.enumeraciones.*;
import java.util.List;
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
    private String edad;

    @OneToOne
    private Imagen imagen;

    @Enumerated(EnumType.STRING)
    private RolEnum rol;

    @Enumerated(EnumType.STRING)
    private ObraSocialEnum obraSocial;

    @ManyToMany(mappedBy = "paciente", cascade = CascadeType.ALL)
    private List<HistoriaClinica> historiaClinica;
}
