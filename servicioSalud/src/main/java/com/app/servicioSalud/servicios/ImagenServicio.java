package com.app.servicioSalud.servicios;

import com.app.servicioSalud.entidades.Imagen;
import com.app.servicioSalud.excepciones.MiException;
import com.app.servicioSalud.repositorios.ImagenRepositorio;
import java.util.Optional;
import javax.mail.Multipart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ImagenServicio {

    @Autowired
    private ImagenRepositorio imagenRepositorio;

    public Imagen guardar(Multipart archivo) throws MiException {

        if (archivo != null) {

            try {
                Imagen imagen = new Imagen();
                imagen.setMime(archivo.getContentType());

                imagen.setNombre(archivo.getName()); /* me tira error get name */

                imagen.setContenido(archivo.getBytes()); /* me tira error get Bytes */

                return imagenRepositorio.save(imagen);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

    public Imagen modificar(Multipart archivo, String idImagen) throws MiException {
        if (archivo != null) {

            try {
                Imagen imagen = new Imagen();

                if (idImagen != null) {
                    Optional<Imagen> respuesta = imagenRepositorio.findById(idImagen);

                    if (respuesta.isPresent()) {
                        imagen = respuesta.get();
                    }
                }

                imagen.setMime(archivo.getContentType());

                imagen.setNombre(archivo.getName());

                imagen.setContenido(archivo.getBytes());

                return imagenRepositorio.save(imagen);

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        return null;

    }
}
