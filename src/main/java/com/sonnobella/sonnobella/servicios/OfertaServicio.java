package com.sonnobella.sonnobella.servicios;

import com.sonnobella.sonnobella.entidades.Oferta;
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
    public void crearOferta(Long id, String titulo, String descripcion, Long precio) {
        
        Oferta oferta = new Oferta();
        
        oferta.setId(id);
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
    
    public void modificarOferta(Long id, String titulo, String descripcion, Long precio) {
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
}
