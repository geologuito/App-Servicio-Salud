package com.app.servicioSalud.servicios;

import com.app.servicioSalud.entidades.Imagen;
import com.app.servicioSalud.entidades.Profesional;
import com.app.servicioSalud.enumeraciones.Especialidad;
import com.app.servicioSalud.enumeraciones.RolEnum;
import com.app.servicioSalud.excepciones.MiException;
import com.app.servicioSalud.repositorios.ProfesionalRepositorio;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProfesionalServicio implements UserDetailsService {

    @Autowired
    private ProfesionalRepositorio profesionalRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    // @Autowired
    // private CorreoServicio correoServicio;
    @Transactional
    public void registrar(MultipartFile archivo, String matricula, String dni, String nombre, String apellido, String email, String password, String password2, String domicilio, String telefono, Boolean activo, Integer consulta, Date horario, Especialidad especialidad) throws MiException {

        validar(matricula, dni, nombre, apellido, email, password, password2, domicilio, telefono);

        Profesional profesional = new Profesional();

        profesional.setMatricula(matricula);
        profesional.setDni(dni);
        profesional.setNombre(nombre);
        profesional.setApellido(apellido);
        profesional.setEmail(email);
        profesional.setPassword(new BCryptPasswordEncoder().encode(password));
        profesional.setDomicilio(domicilio);
        profesional.setTelefono(telefono);
        profesional.setActivo(activo);
        profesional.setConsulta(consulta);
        profesional.setHorario(horario);
        profesional.setRol(RolEnum.PROFESIONAL);
        profesional.setEspecialidad(especialidad);
        Imagen imagen = imagenServicio.guardar(archivo);
        profesional.setImagen(imagen);

        profesionalRepositorio.save(profesional);
        // correoServicio.registroProfesional(profesional.getEmail(), profesional.getNombre());
        // correoServicio.altaProfesional(matricula);
    }

    public List<Profesional> listarProfesional() {

        return profesionalRepositorio.findAll();

    }

    public void modificarProfesional(MultipartFile archivo,@NonNull String matricula, String email, String password, String password2, String domicilio, String telefono) throws MiException {

        validarModificar(password, password2, domicilio, telefono);

        Optional<Profesional> respuesta = profesionalRepositorio.findById(matricula);
        if (respuesta.isPresent()) {

            Profesional profesional = respuesta.get();

            profesional.setPassword(new BCryptPasswordEncoder().encode(password));
            profesional.setDomicilio(domicilio);
            profesional.setTelefono(telefono);

            String idImagen = null;
            if (profesional.getImagen() != null) {
                idImagen = profesional.getImagen().getId();
            }
            Imagen imagen = imagenServicio.modificar(archivo, idImagen);
            profesional.setImagen(imagen);
            profesionalRepositorio.save(profesional);

        }

    }

    private void validar(String matricula, String dni, String nombre, String apellido, String email, String password, String password2, String domicilio, String telefono) throws MiException {
        if (matricula == null || matricula.isEmpty()) {
            throw new MiException("La matricula no puede ser nula o estar vacia");
        }
        if (dni == null || dni.isEmpty() || dni.length() <= 6) {
            throw new MiException("Se requiere DNI valido o mayor a 6 digitos");
        }
        if (nombre == null || nombre.isEmpty()) {
            throw new MiException("El nombre no puede ser nulo ni estar vacio");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new MiException("El apellido no puede ser nulo ni estar vacio");
        }
        if (email == null || email.isEmpty()) {
            throw new MiException("El email no puede ser nulo ni estar vacio");
        }
        if (password == null || password.isEmpty() || password.length() <= 5) {
            throw new MiException("la contraseña no puede estar vacia y debe tener más de 5 digitos");
        }
        if (!password.equals(password2)) {
            throw new MiException("las contraseñas no coinciden, verifica que sean iguales");
        }
        if (domicilio == null || domicilio.isEmpty()) {
            throw new MiException("el domicilio no puede ser nulo ni estar vacio");
        }

        if (telefono == null || telefono.isEmpty() || telefono.length() <= 6) {
            throw new MiException("el telefono no puede ser nulo ni estar vacio");
        }

    }

    private void validarModificar(String password, String password2, String domicilio, String telefono) throws MiException {

        if (password == null || password.isEmpty() || password.length() <= 5) {
            throw new MiException("La contraseña no puede estar vacia y debe tener más de 5 caracteres");
        }
        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas no coinciden, verifica que sean iguales");
        }
        if (domicilio == null || domicilio.isEmpty()) {
            throw new MiException("El domicilio no puede ser nulo ni estar vacio");
        }
        if (telefono == null || telefono.isEmpty() || telefono.length() <= 6) {
            throw new MiException("El telefono no puede ser nulo, estar vacio o tener menos de 6 digitos");
        }
    }

    public Profesional getOne(@NonNull String id) {
        return profesionalRepositorio.getReferenceById(id);
    }

    public void eliminarProfesional(@NonNull String id) throws MiException {
        profesionalRepositorio.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Profesional profesional = profesionalRepositorio.buscarPorEmail(email);

        if (profesional != null) {

            List<GrantedAuthority> permisos = new ArrayList<>();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + profesional.getRol().toString());

            permisos.add(p);
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("profesionalsession", profesional);

            return new User(profesional.getEmail(), profesional.getPassword(), permisos);
        } else {
            return null;
        }
    }
}
