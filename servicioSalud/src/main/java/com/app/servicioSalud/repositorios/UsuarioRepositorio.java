package com.app.servicioSalud.repositorios;

import com.app.servicioSalud.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario,String> {
 
    @Query ("select u from Usuario u  where u.dni = :dni")
    public Usuario buscarPorDNI(@Param("dni") String dni);
}
