package com.app.servicioSalud.entidades;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "turno", indexes = @Index(name = "idx_id", columnList = "id"))
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTurno;

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
