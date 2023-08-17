package com.sonnobella.sonnobella.controladores;

import com.sonnobella.sonnobella.entidades.Usuario;
import com.sonnobella.sonnobella.excepciones.MiException;
import com.sonnobella.sonnobella.servicios.UsuarioServicio;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class PortalControlador {
    
    @GetMapping("/")
    public String index() {
        
        return "index.html";
    }
    
    @Autowired
    UsuarioServicio usuarioServicio;
    
    @GetMapping("/registrar")
    public String registrar() {
        return "registrarUsuario.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String apellido, 
            @RequestParam String email, @RequestParam String pass,  @RequestParam String pass2,
            ModelMap modelo, MultipartFile archivo) 
    {
        try {
            usuarioServicio.registrar(archivo, nombre, apellido, email, pass, pass2);
            modelo.put("exito", "El usuario fue cargado con exito!");
            return "index.html";
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            return "registrarUsuario.html";
        }
        
    }
    
    @GetMapping("/login")
    public String iniciar(@RequestParam(required = false) String error,ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "Usuario o Contraseña invalidos!");
            return "redirect:/login";
        }
        return "iniciarSesion.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session) {
        
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");
        
        if (logueado.getRol().toString().equals("ADMIN")) {
            return "redirect:/admin/dashboard";
        }
        return "inicio.html";
    }
    
    
    
    
}
