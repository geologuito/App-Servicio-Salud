
package com.app.servicioSalud.repositorios;

import com.app.servicioSalud.entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ImagenRepositorio extends JpaRepository<Imagen, String> {
    
}
