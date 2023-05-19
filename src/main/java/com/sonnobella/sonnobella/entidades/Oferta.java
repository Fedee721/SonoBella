package com.sonnobella.sonnobella.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import lombok.Data;

@Data
@Entity
public class Oferta {
    
    @Id
    private Long id;
    private String titulo;
    private String descripcion;
    private Long precio;
}
