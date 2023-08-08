package com.sonnobella.sonnobella.repositorios;

import com.sonnobella.sonnobella.entidades.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ImagenRepositorio extends JpaRepository<Imagen,String> {
    
}
