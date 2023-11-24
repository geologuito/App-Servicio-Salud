
package com.app.servicioSalud.servicios;

import com.app.servicioSalud.entidades.Usuario;
import com.app.servicioSalud.enumeraciones.Rol;
import com.app.servicioSalud.excepciones.MiException;
import com.app.servicioSalud.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author gonza
 */

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;


    @Transactional

    public void registrar(String dni, String nombre, String apellido, String email, String domicilio, String telefono, String password, String password2) throws MiException {
        validar(dni, nombre, apellido, domicilio, telefono, email, password, password2);

        Usuario usuario = new Usuario();

        usuario.setDni(dni);
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setEmail(email);
        usuario.setDomicilio(domicilio);
        usuario.setTelefono(telefono);
        usuario.setPassword(password);
        usuario.setRol(Rol.USER);

        usuarioRepositorio.save(usuario);
    }

    
    
    public List<Usuario> listarUsuario() {

        List<Usuario> usuarios = new ArrayList<>();

        usuarios = usuarioRepositorio.findAll();

        return usuarios;

    }
    
   public void modificarUsuario(String dni, String nombre, String apellido, String email, String domicilio, String telefono, String password, String password2) throws MiException{
       
       validar(dni, nombre, apellido, domicilio, telefono, email, password, password2);
       
        Optional<Usuario> respuesta = usuarioRepositorio.findById(dni);
       
        if (respuesta.isPresent()) {
           Usuario usuario = respuesta.get();
           
           usuario.setNombre(nombre);
           usuario.setApellido(apellido);
           usuario.setEmail(email);
           usuario.setDomicilio(domicilio);
           usuario.setTelefono(telefono);
           usuario.setPassword(password);
           
           usuarioRepositorio.save(usuario);
       }
             
   }
    
    
      private void validar(String dni,String nombre, String apellido, String domicilio, String telefono, String email, String password, String password2) throws MiException {
       
        if (dni == null || dni.isEmpty() || dni.length() <= 6) {
            throw new MiException("se requiere DNI valido");
        }
        
        if (nombre == null || nombre.isEmpty()) {
            throw new MiException("el nombre no puede ser nulo ni estar vacio");
        }
        
        if (apellido == null || apellido.isEmpty()) {
            throw new MiException("el apellido no puede ser nulo ni estar vacio");
        }
        
         if (domicilio == null || domicilio.isEmpty()) {
            throw new MiException("el domicilio no puede ser nulo ni estar vacio");
        }
         
          if (telefono == null || telefono.isEmpty() || telefono.length() <=6) {
            throw new MiException("el telefono no puede ser nulo ni estar vacio");
        }


        if (email == null || email.isEmpty()) {
            throw new MiException("el email no puede ser nulo ni estar vacio");
        }

        if (password == null || password.isEmpty() || password.length() <= 5) {
            throw new MiException("la contraseña no puede estar vacia y debe tener más de 5 digitos");
        }

        if (!password.equals(password2)) {
            throw new MiException("las contraseñas no coinciden, verifica que sean iguales");
        }
    }

}
