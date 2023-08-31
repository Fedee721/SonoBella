package com.sonnobella.sonnobella.controladores;

import com.sonnobella.sonnobella.entidades.Oferta;
import com.sonnobella.sonnobella.entidades.Turno;
import com.sonnobella.sonnobella.excepciones.MiException;
import com.sonnobella.sonnobella.servicios.OfertaServicio;
import com.sonnobella.sonnobella.servicios.TurnoServicio;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/oferta")
public class OfertaControlador {
    
    @Autowired
    private OfertaServicio ofertaServicio;
    
    @Autowired
    private TurnoServicio turnoServicio;
    
    @GetMapping("/registrar")
    public String registrar() {
        return "crearOferta.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String titulo, @RequestParam String descripcion,
            @RequestParam Long precio, ModelMap modelo, MultipartFile archivo,
            @RequestParam(required = false) Boolean esCurso) {
        
        try {
            ofertaServicio.crearOferta(titulo, descripcion, precio, archivo, esCurso);
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
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id,ModelMap modelo) {  
        modelo.put("oferta", ofertaServicio.getOne(id));
        return "modificarOferta.html";
    }
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String titulo, String descripcion, Long precio,
            ModelMap modelo, MultipartFile archivo, @RequestParam(required = false) Boolean esCurso) {  
        try {
            ofertaServicio.modificarOferta(id, titulo, descripcion, precio, archivo, esCurso);
            return "redirect:../lista";
        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            return "modificarOferta.html";
        }
    }
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id, ModelMap modelo) throws MiException{
        try {
            ofertaServicio.eliminar(id);
            return "redirect:../lista";
        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            return "listaOfertas.html";
        }
    }
    @GetMapping("/reservar/{id}")
    public String reservar(@PathVariable String id,ModelMap modelo) {  
        modelo.put("oferta", ofertaServicio.getOne(id));
        List<Turno> turnos = turnoServicio.listarTurnos();
        modelo.put("turnos", turnos);
        return "reservarOferta.html";
    }
    @PostMapping("/reservar/{id}")
    public String reservar(@PathVariable String id, Integer dia, Integer mes,
            Integer ano, Integer hora, String idCliente,
            ModelMap modelo) throws MiException {  
        try {
            turnoServicio.crearTurno(dia, mes, ano, hora, idCliente, id);
            return "redirect:../lista";
        } catch (MiException e) {
            System.out.println("error");
            modelo.put("error", e.getMessage());
            return "reservarOferta.html";
        }
    }
}
