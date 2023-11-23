
package com.app.servicioSalud.entidades;


import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;


@Entity
@Data
public class HistoriaClinica {
    
    @Id
    private Usuario dni;
    private List<String> dx;
    private Profesional nombreProfesional;
    
    
}
