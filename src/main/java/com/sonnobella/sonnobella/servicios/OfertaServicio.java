package com.sonnobella.sonnobella.servicios;

import com.sonnobella.sonnobella.entidades.Imagen;
import com.sonnobella.sonnobella.entidades.Oferta;
import com.sonnobella.sonnobella.excepciones.MiException;
import com.sonnobella.sonnobella.repositorios.OfertaRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class OfertaServicio {
    
    @Autowired
    private OfertaRepositorio ofertaRepositorio;
    
    @Autowired
    private ImagenServicio imagenServicio;
    
    @Transactional
    public void crearOferta( String titulo, String descripcion, Long precio, MultipartFile archivo)
            throws MiException {
        
        validar(titulo,descripcion,precio);
        
        Oferta oferta = new Oferta();
        
        oferta.setTitulo(titulo);
        oferta.setDescripcion(descripcion);
        oferta.setPrecio(precio);
        
        Imagen imagen = imagenServicio.guardar(archivo);
        
        oferta.setImagen(imagen);
        
        ofertaRepositorio.save(oferta);
    }
    
    public List<Oferta> listarOfertas() {
        List<Oferta> ofertas = new ArrayList();
        
        ofertas = ofertaRepositorio.findAll();
        
        return ofertas;
    }
    
    @Transactional
    public void modificarOferta(String id, String titulo, String descripcion, Long precio, MultipartFile archivo)
            throws MiException {
        
        validar(titulo,descripcion,precio);
        
        Optional<Oferta> respuesta = ofertaRepositorio.findById(id);
        
        if(respuesta.isPresent()) {
            Oferta oferta = respuesta.get();
            
            oferta.setId(id);
            oferta.setTitulo(titulo);
            oferta.setDescripcion(descripcion);
            oferta.setPrecio(precio);
            
            String idImagen = null;
            
            if (oferta.getImagen() != null) {
                idImagen = oferta.getImagen().getId();
            }
            
            Imagen imagen = imagenServicio.actualizar(archivo, idImagen);
            
            oferta.setImagen(imagen);
            
            ofertaRepositorio.save(oferta);
        }
    }
    
    public Oferta getOne(String id) {
        return ofertaRepositorio.getOne(id);
    }
    
    @Transactional
    public void eliminar(String id) throws MiException{
        
        Oferta oferta = ofertaRepositorio.getById(id);
        
        ofertaRepositorio.delete(oferta);

    }
    
    private void validar( String titulo, String descripcion, Long precio) throws MiException {
        if(titulo==null || titulo.isEmpty()) {
            throw new MiException("El titulo no puede ser nulo o estar vacio.");
        }
        if(descripcion==null || descripcion.isEmpty()) {
            throw new MiException("La descripcion no puede ser nulo o estar vacio.");
        }
        if(precio==null || precio<=0) {
            throw new MiException("El precio no puede ser nulo o negativo.");
        }
    }
}
