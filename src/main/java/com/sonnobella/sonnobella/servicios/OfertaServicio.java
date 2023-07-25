package com.sonnobella.sonnobella.servicios;

import com.sonnobella.sonnobella.entidades.Oferta;
import com.sonnobella.sonnobella.excepciones.MiException;
import com.sonnobella.sonnobella.repositorios.OfertaRepositorio;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfertaServicio {
    
    @Autowired
    private OfertaRepositorio ofertaRepositorio;
    
    @Transactional
    public void crearOferta( String titulo, String descripcion, Long precio) throws MiException {
        
        validar(titulo,descripcion,precio);
        
        Oferta oferta = new Oferta();
        
        oferta.setTitulo(titulo);
        oferta.setDescripcion(descripcion);
        oferta.setPrecio(precio);
        
        ofertaRepositorio.save(oferta);
    }
    
    public List<Oferta> listarOfertas() {
        List<Oferta> ofertas = new ArrayList();
        
        ofertas = ofertaRepositorio.findAll();
        
        return ofertas;
    }
    
    public void modificarOferta(String id, String titulo, String descripcion, Long precio) throws MiException {
        
        validar(titulo,descripcion,precio);
        
        Optional<Oferta> respuesta = ofertaRepositorio.findById(id);
        
        if(respuesta.isPresent()) {
            Oferta oferta = respuesta.get();
            
            oferta.setId(id);
            oferta.setTitulo(titulo);
            oferta.setDescripcion(descripcion);
            oferta.setPrecio(precio);
            
            ofertaRepositorio.save(oferta);
        }
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
