package com.app.servicioSalud.entidades;

import com.app.servicioSalud.enumeraciones.Calificacion;
import com.app.servicioSalud.enumeraciones.Especialidad;
import com.app.servicioSalud.enumeraciones.RolEnum;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Data
@Getter
public class Profesional {

    @Id
    @Column(unique = true)
    private String matricula;
    private String dni;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String domicilio; // decidir si se deja o lo sacamos
    private String telefono;
    private Boolean activo = false;
    private Integer consulta;
    @Temporal(TemporalType.DATE)
    private Date horario;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;
    @OneToMany(mappedBy = "profesional")
    private List<Turno> turno;

    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;

    @Enumerated(EnumType.STRING)
    private RolEnum rol;

    @Enumerated(EnumType.STRING)
    private Calificacion calificacion;

    @OneToOne
    private Imagen imagen;
}
