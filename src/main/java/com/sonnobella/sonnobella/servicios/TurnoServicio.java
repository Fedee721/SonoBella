package com.sonnobella.sonnobella.servicios;


import com.sonnobella.sonnobella.entidades.Oferta;
import com.sonnobella.sonnobella.entidades.Turno;
import com.sonnobella.sonnobella.entidades.Usuario;
import com.sonnobella.sonnobella.excepciones.MiException;
import com.sonnobella.sonnobella.repositorios.TurnoRepositorio;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TurnoServicio {
    
    @Autowired
    TurnoRepositorio turnoRepositorio;
    
    @Autowired
    UsuarioServicio usuarioServicio;
    
    @Autowired
    OfertaServicio ofertaServicio;
    
    //CREAR TURNO-------------------------------------
    @Transactional
    public void crearTurno(Integer dia, Integer mes, Integer ano, Integer hora, String idCliente,
            String idOferta) throws MiException {  
        
        validar(ano, mes, dia, hora, idCliente, idOferta);
        
        Turno turno = new Turno();
        
        turno.setAno(ano);
        turno.setMes(mes);
        turno.setDia(dia);
        turno.setHorario(hora);
        
        Usuario cliente = usuarioServicio.getOne(idCliente);
        turno.setCliente(cliente);
        Oferta oferta = ofertaServicio.getOne(idOferta);
        turno.setOferta(oferta);
        
        turnoRepositorio.save(turno);
    }
    
    //LISTAR-------------------------------------
    public List<Turno> listarTurnos() {
        List<Turno> turnos = new ArrayList();
        turnos = turnoRepositorio.findAll();
        return turnos;
    }
    
    //VALIDAR-------------------------------------
    private void validar(Integer ano, Integer mes, Integer dia, Integer horario, String idCliente, String idOferta) 
            throws MiException {

        if (ano == null || ano <=0) {
            throw new MiException("El año no puede ser nulo o menor a cero");
        }
        if (mes == null || mes <=0) {
            throw new MiException("El mes no puede ser nulo o menor a cero");
        }
        if (dia == null || dia <=0) {
            throw new MiException("El dia no puede ser nulo o menor a cero");
        }
        if (horario == null || horario<=0) {
            throw new MiException("El horario no puede ser nulo o menor a cero");
        }
        if (idCliente == null || idCliente.isEmpty()) {
            throw new MiException("El cliente no puede ser nulo");
        }
        if (idOferta == null || idOferta.isEmpty()) {
            throw new MiException("La oferta no puede ser nula");
        }
    }
}
