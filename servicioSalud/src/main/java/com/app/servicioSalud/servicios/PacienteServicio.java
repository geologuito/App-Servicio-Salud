package com.app.servicioSalud.servicios;

import com.app.servicioSalud.entidades.Imagen;
import com.app.servicioSalud.entidades.Paciente;
import com.app.servicioSalud.enumeraciones.RolEnum;
import com.app.servicioSalud.excepciones.MiException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.servicioSalud.repositorios.PacienteRepositorio;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PacienteServicio implements UserDetailsService {

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Autowired
    private CorreoServicio correoServicio;
    
    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void registrar(MultipartFile archivo, String dni, String nombre, String apellido, String email, String domicilio, String telefono, String password, String password2, String edad) throws MiException {

        validar(dni, nombre, apellido, domicilio, telefono, email, password, password2, edad);

        Paciente paciente = new Paciente();

        paciente.setDni(dni);
        paciente.setNombre(nombre);
        paciente.setApellido(apellido);
        paciente.setEmail(email);
        paciente.setDomicilio(domicilio);
        paciente.setTelefono(telefono);
        paciente.setPassword(new BCryptPasswordEncoder().encode(password));
        paciente.setEdad(edad);
        paciente.setRol(RolEnum.PACIENTE);
        Imagen imagen = imagenServicio.guardar(archivo);
        paciente.setImagen(imagen);

        pacienteRepositorio.save(paciente);

        correoServicio.envioRegistro(paciente.getEmail(), paciente.getNombre());

    }

    public List<Paciente> listarPaciente() {

        return pacienteRepositorio.findAll();

    }

    public void modificarPaciente(MultipartFile archivo, String dni, String email, String domicilio, String telefono, String password, String password2) throws MiException {

        modificarValidacion(domicilio, email, telefono, password, password2);

        Optional<Paciente> respuesta = pacienteRepositorio.findById(dni);

        if (respuesta.isPresent()) {
            Paciente paciente = respuesta.get();

            paciente.setEmail(email);
            paciente.setDomicilio(domicilio);
            paciente.setTelefono(telefono);
            paciente.setPassword(new BCryptPasswordEncoder().encode(password));

            String idImagen = null;
            if (paciente.getImagen() != null) {
                idImagen = paciente.getImagen().getId();
            }
            Imagen imagen = imagenServicio.modificar(archivo, idImagen);
            paciente.setImagen(imagen);

            pacienteRepositorio.save(paciente);
        }

    }

    private void validar(String dni, String nombre, String apellido, String domicilio, String telefono, String email, String password, String password2, String edad) throws MiException {


        if (nombre == null || nombre.isEmpty()) {
            throw new MiException("el nombre no puede ser nulo ni estar vacio");
        }

        if (apellido == null || apellido.isEmpty()) {
            throw new MiException("el apellido no puede ser nulo ni estar vacio");
        }

        if (dni == null || dni.isEmpty() || dni.length() <= 6) {
            throw new MiException("se requiere DNI valido");
        }

        if (edad == null || edad.isEmpty()) {
            throw new MiException("La Edad no puede ser nula ni estar vacia");
        }

        modificarValidacion(domicilio, email, telefono, password, password2);
    }

    public void modificarValidacion(String domicilio, String email, String telefono, String password, String password2) throws MiException {
        Paciente correoBD = pacienteRepositorio.buscarPorEmail(email);
        if (domicilio == null || domicilio.isEmpty()) {
            throw new MiException("el domicilio no puede ser nulo ni estar vacio");
        }
        if (email == null || email.isEmpty() || correoBD != null) {
            throw new MiException("el email no puede ser nulo ni estar vacio o esta repetido");
        }
        if (telefono == null || telefono.isEmpty() || telefono.length() <= 6) {
            throw new MiException("el telefono no puede ser nulo ni estar vacio");
        }
        if (password == null || password.isEmpty() || password.length() <= 5) {
            throw new MiException("la contraseña no puede estar vacia y debe tener más de 5 digitos");
        }
        if (!password.equals(password2)) {
            throw new MiException("las contraseñas no coinciden, verifica que sean iguales");
        }
    }

    public Paciente getOne(String id) {
        return pacienteRepositorio.getReferenceById(id);
    }

    public void eliminarPaciente(String id) throws MiException {
        pacienteRepositorio.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Paciente paciente = pacienteRepositorio.buscarPorEmail(email);

        if (paciente != null) {

            List<GrantedAuthority> permisos = new ArrayList<>();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_PACIENTE" + paciente.getRol().toString());

            permisos.add(p);
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("pacientesession", paciente);

            return new User(paciente.getEmail(), paciente.getPassword(), permisos);
        } else {
            return null;
        }

    }
}
