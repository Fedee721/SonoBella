package com.sonnobella.sonnobella.servicios;

import com.sonnobella.sonnobella.entidades.Imagen;
import com.sonnobella.sonnobella.entidades.Usuario;
import com.sonnobella.sonnobella.excepciones.MiException;
import com.sonnobella.sonnobella.repositorios.UsuarioRepositorio;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class UsuarioServicio {
    
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    private ImagenServicio imagenServicio;
    
    //REGISTRAR-------------------------------------
    @Transactional
    public void registrar(MultipartFile archivo, String nombre, String email, String password, String password2) throws MiException {

        validar(nombre, email, password, password2);

        Usuario usuario = new Usuario();

        usuario.setNombre(nombre);
        usuario.setEmail(email);

        //usuario.setPass(new BCryptPasswordEncoder().encode(password));
        usuario.setPass(password);
        //usuario.setRol(Rol.USER);
        
        Imagen imagen = imagenServicio.guardar(archivo);

        usuario.setImagen(imagen);
        
        usuarioRepositorio.save(usuario);
    }
    
    //ACTUALIZAR-------------------------------------
    @Transactional
    public void actualizar(MultipartFile archivo, String idUsuario, String nombre, String email, String password, String password2) throws MiException {

        validar(nombre, email, password, password2);

        Optional<Usuario> respuesta = usuarioRepositorio.findById(idUsuario);
        if (respuesta.isPresent()) {

            Usuario usuario = respuesta.get();
            usuario.setNombre(nombre);
            usuario.setEmail(email);

            //usuario.setPass(new BCryptPasswordEncoder().encode(password));
            usuario.setPass(password);
            //usuario.setRol(Rol.USER);
            
            String idImagen = null;
            
            if (usuario.getImagen() != null) {
                idImagen = usuario.getImagen().getId();
            }
            
            Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
            
            usuario.setImagen(imagen);
            
            usuarioRepositorio.save(usuario);
        }

    }
    
    //BUSCAR UN USUARIO-----------------------------------
    public Usuario getOne(String id){
        return usuarioRepositorio.getOne(id);
    }
    
    //LISTAR USUARIOS-------------------------------------
    public List<Usuario> listarUsuarios() {

        List<Usuario> usuarios = new ArrayList();

        usuarios = usuarioRepositorio.findAll();

        return usuarios;
    }
    
    //VALIDAR-------------------------------------
    private void validar(String nombre, String email, String password, String password2) throws MiException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("el nombre no puede ser nulo o estar vacío");
        }
        if (email.isEmpty() || email == null) {
            throw new MiException("el email no puede ser nulo o estar vacio");
        }
        if (password.isEmpty() || password == null || password.length() <= 5) {
            throw new MiException("La contraseña no puede estar vacía, y debe tener más de 5 dígitos");
        }

        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }

    }

}
