package com.app.servicioSalud.servicios;

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

@Service
public class PacienteServicio implements UserDetailsService {

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Transactional
    public void registrar(String dni, String nombre, String apellido, String email, String domicilio, String telefono,
            String password, String password2) throws MiException {
        validar(dni, nombre, apellido, domicilio, telefono, email, password, password2);

        Paciente paciente = new Paciente();

        paciente.setDni(dni);
        paciente.setNombre(nombre);
        paciente.setApellido(apellido);
        paciente.setEmail(email);
        paciente.setDomicilio(domicilio);
        paciente.setTelefono(telefono);
        paciente.setPassword(new BCryptPasswordEncoder().encode(password));
        paciente.setRol(RolEnum.PACIENTE);

        pacienteRepositorio.save(paciente);
    }

    public List<Paciente> listarPaciente() {

        List<Paciente> pacientes = new ArrayList<>();

        pacientes = pacienteRepositorio.findAll();

        return pacientes;

    }

    public void modificarPaciente(String dni, String email, String domicilio, String telefono, String password,
            String password2) throws MiException {

        modificarValidacion(domicilio, email, telefono, password, password2);

        Optional<Paciente> respuesta = pacienteRepositorio.findById(dni);

        if (respuesta.isPresent()) {
            Paciente paciente = respuesta.get();

            paciente.setEmail(email);
            paciente.setDomicilio(domicilio);
            paciente.setTelefono(telefono);
            paciente.setPassword(new BCryptPasswordEncoder().encode(password));
            pacienteRepositorio.save(paciente);
        }

    }

    private void validar(String dni, String nombre, String apellido, String domicilio, String telefono, String email,
            String password, String password2) throws MiException {

        if (nombre == null || nombre.isEmpty()) {
            throw new MiException("el nombre no puede ser nulo ni estar vacio");
        }

        if (apellido == null || apellido.isEmpty()) {
            throw new MiException("el apellido no puede ser nulo ni estar vacio");
        }

        if (dni == null || dni.isEmpty() || dni.length() <= 6) {
            throw new MiException("se requiere DNI valido");
        }

        if (domicilio == null || domicilio.isEmpty()) {
            throw new MiException("el domicilio no puede ser nulo ni estar vacio");
        }

        if (email == null || email.isEmpty()) {
            throw new MiException("el email no puede ser nulo ni estar vacio");
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

    public void modificarValidacion(String domicilio, String email, String telefono, String password, String password2)
            throws MiException {
        if (domicilio == null || domicilio.isEmpty()) {
            throw new MiException("el domicilio no puede ser nulo ni estar vacio");
        }

        if (email == null || email.isEmpty()) {
            throw new MiException("el email no puede ser nulo ni estar vacio");
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

            List<GrantedAuthority> permisos = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + paciente.getRol().toString());

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
