package com.app.servicioSalud.entidades;

import com.app.servicioSalud.enumeraciones.RolEnum;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
//@Table(name = "profesional")
public class Profesional{

    
    @Id
    @Column(unique = true)
    private String Matricula;
    private String dni;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String domicilio;
    private String telefono;
    private Boolean activo;
    private String especialidad;
    private Integer consulta;
    @Temporal(TemporalType.DATE)
    private Date horario;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;
    @OneToMany(mappedBy = "profesional")
    private List<Turno> turno;
    
    @Enumerated(EnumType.STRING)
    private RolEnum rol;
}
