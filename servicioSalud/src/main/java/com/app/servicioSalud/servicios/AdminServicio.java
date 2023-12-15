package com.app.servicioSalud.servicios;

import com.app.servicioSalud.entidades.Admin;
import com.app.servicioSalud.entidades.Imagen;
import com.app.servicioSalud.enumeraciones.RolEnum;
import com.app.servicioSalud.excepciones.MiException;
import com.app.servicioSalud.repositorios.AdminRepositorio;
import java.util.ArrayList;
import java.util.List;
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
public class AdminServicio implements UserDetailsService {

    @Autowired
    private AdminRepositorio adminRepositorio;
    @Autowired
    private ImagenServicio imagenServicio;

    @Transactional
    public void registrar(MultipartFile archivo, String email, String password, String password2) throws MiException {

        validar(email, password, password2);

        Admin admin = new Admin();

        admin.setEmail(email);
        admin.setPassword(new BCryptPasswordEncoder().encode(password));
        admin.setRol(RolEnum.ADMIN);
        Imagen imagen = imagenServicio.guardar(archivo);
        admin.setImagen(imagen);

        adminRepositorio.save(admin);

    }

    private void validar(String email, String password, String password2) throws MiException {

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

    public List<Admin> listarAdmin() {

        return adminRepositorio.findAll();

    }

    public Admin getOne(String id) {
        return adminRepositorio.getReferenceById(id);
    }

    public void eliminarAdmin(String id) throws MiException {
        adminRepositorio.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Admin admin = adminRepositorio.buscarPorEmail(email);

        if (admin != null) {

            List<GrantedAuthority> permisos = new ArrayList<>();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + admin.getRol().toString());

            permisos.add(p);
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("adminsession", admin);

            return new User(admin.getEmail(), admin.getPassword(), permisos);
        } else {
            return null;
        }

    }
}
