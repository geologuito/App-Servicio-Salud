/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.servicioSalud.entidades;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Admin {
    
    @Id
    private String idAdmin;
    private String usuario;
    private String password;
    
    
}
