
package com.app.servicioSalud.servicios;

import com.app.servicioSalud.entidades.Usuario;
import com.app.servicioSalud.enumeraciones.Rol;
import com.app.servicioSalud.excepciones.MiException;
import com.app.servicioSalud.repositorios.UsuarioRepositorio;
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
    public void registrar(Integer dni, String nombre, String apellido, String email, String domicilio, String telefono, String password, String password2) throws MiException {
        validar(nombre, email, password, password2);

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

    /*
    Falta validar los campos dni, apellido, domicilio y telefono
     */
    private void validar(String nombre, String email, String password, String password2) throws MiException {
        if (nombre == null || nombre.isEmpty()) {
            throw new MiException("el nombre no puede ser nulo ni estar vacio");
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
