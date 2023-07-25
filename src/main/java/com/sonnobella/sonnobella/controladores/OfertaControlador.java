package com.sonnobella.sonnobella.controladores;

import com.sonnobella.sonnobella.entidades.Oferta;
import com.sonnobella.sonnobella.excepciones.MiException;
import com.sonnobella.sonnobella.servicios.OfertaServicio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/oferta")
public class OfertaControlador {
    
    @Autowired
    private OfertaServicio ofertaServicio;
    
    @GetMapping("/registrar")
    public String registrar() {
        return "crearOferta.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String titulo, @RequestParam String descripcion, @RequestParam Long precio, ModelMap modelo) {
        
        try {
            ofertaServicio.crearOferta(titulo, descripcion, precio);
            modelo.put("exito", "La oferta fue cargada con exito!");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "crearOferta.html";
        }
        
        return "index.html";
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        
        List<Oferta> ofertas = ofertaServicio.listarOfertas();
        modelo.addAttribute("ofertas", ofertas);
        return "listaOfertas.html";
    }
}
