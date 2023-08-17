package com.sonnobella.sonnobella.controladores;

import com.sonnobella.sonnobella.entidades.Oferta;
import com.sonnobella.sonnobella.entidades.Usuario;
import com.sonnobella.sonnobella.servicios.ImagenServicio;
import com.sonnobella.sonnobella.servicios.OfertaServicio;
import com.sonnobella.sonnobella.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/imagen")
public class ImagenControlador {
    
    @Autowired
    ImagenServicio imagenServicio;
    
    @Autowired
    UsuarioServicio usuarioServicio;
    
    @Autowired
    OfertaServicio ofertaServicio;
    
    @GetMapping("/perfil/{id}")
    public ResponseEntity<byte[]> imagenUsuario (@PathVariable String id){
       Usuario usuario = usuarioServicio.getOne(id);
        
       byte[] imagen= usuario.getImagen().getContenido();
       
       HttpHeaders headers = new HttpHeaders();
       
       headers.setContentType(MediaType.IMAGE_JPEG);
   
       return new ResponseEntity<>(imagen,headers, HttpStatus.OK); 
    }
    
    @GetMapping("/oferta/{id}")
    public ResponseEntity<byte[]> imagenOferta (@PathVariable String id){
       Oferta oferta = ofertaServicio.getOne(id);
        
       byte[] imagen= oferta.getImagen().getContenido();
       
       HttpHeaders headers = new HttpHeaders();
       
       headers.setContentType(MediaType.IMAGE_JPEG);

       return new ResponseEntity<>(imagen,headers, HttpStatus.OK); 
    }
}
