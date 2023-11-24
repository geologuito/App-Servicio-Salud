package com.app.servicioSalud.entidades;

import com.app.servicioSalud.enumeraciones.Rol;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Data
public class Profesional {

    @Id
    private String Matricula;

    private String nombre;
    private String apellido;
    private String correo;
    private String password;
    private Boolean activo;
    private String especialidad;
    private Integer consulta;
    @Temporal(TemporalType.DATE)
    private Date horario;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    @OneToMany(mappedBy = "profesional")
    private List<Turno> turno;
    
    @Enumerated(EnumType.STRING)
    private Rol rol;
}
