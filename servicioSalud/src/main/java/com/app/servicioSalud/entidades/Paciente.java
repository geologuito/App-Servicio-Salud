package com.app.servicioSalud.entidades;

import com.app.servicioSalud.enumeraciones.ObraSocialEnum;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Luciano Otegui
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
//@DiscriminatorValue("paciente")
@Entity
public class Paciente extends Usuario {
    @Id
    @Column(unique = true)
    private String dni;
    
    @Enumerated(EnumType.STRING)
    private ObraSocialEnum obraSocial;
    
    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL)
    private HistoriaClinica historiaClinica;
}
