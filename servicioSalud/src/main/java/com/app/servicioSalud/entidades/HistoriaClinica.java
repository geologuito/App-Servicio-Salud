package com.app.servicioSalud.entidades;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter
//Sin esto cuando se genera la tabla da error "Falta index". Con esto se genera un indice aparte del id llamado idx_id
@Table(name = "historia_clinica", indexes = @Index(name = "idx_id", columnList = "id"))
public class HistoriaClinica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profesional_id")
    private Profesional profesional;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    private String titulo;
    private String dx;
    private String tratamiento;
    @Temporal(TemporalType.DATE)
    private Date alta;

    private Boolean respuesta = false;

}