package com.app.servicioSalud.entidades;

import com.app.servicioSalud.enumeraciones.Horario;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String idTurno;

    private LocalDate fecha;

    private LocalTime horario;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profesional_id")
    private Profesional profesional;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;
    
    private Boolean reservado;
}
