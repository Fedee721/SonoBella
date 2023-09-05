package com.sonnobella.sonnobella.controladores;

import com.sonnobella.sonnobella.entidades.Turno;
import com.sonnobella.sonnobella.entidades.Usuario;
import com.sonnobella.sonnobella.servicios.TurnoServicio;
import com.sonnobella.sonnobella.servicios.UsuarioServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminControlador {
    
   @Autowired
   private UsuarioServicio usuarioServicio;
   
   @Autowired
    private TurnoServicio turnoServicio;
    
   @GetMapping("/dashboard")
   public String panelAdministrativo(){
       return "panel.html";
   }
   
   @GetMapping("/usuarios")
    public String listar(ModelMap modelo) {
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);

        return "usuario_list";
    }
    
    @GetMapping("/modificarRol/{id}")
    public String cambiarRol(@PathVariable String id){
        usuarioServicio.cambiarRol(id);
        
       return "redirect:/admin/usuarios";
    }
    
    @GetMapping("/agenda")
    public String agenda(ModelMap modelo) {
        List<Turno> turnos = turnoServicio.listarTurnos();
        modelo.put("turnos", turnos);
        return "agenda.html";
    }
}
