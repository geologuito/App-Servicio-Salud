package com.app.servicioSalud.repositorios;

import com.app.servicioSalud.entidades.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepositorio extends JpaRepository<Admin, String> {

    @Query("SELECT a FROM Admin a WHERE a.email = :email")
    public Admin buscarPorEmail(@Param("email") String email);

}
