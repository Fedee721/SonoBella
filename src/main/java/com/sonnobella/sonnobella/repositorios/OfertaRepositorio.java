package com.sonnobella.sonnobella.repositorios;

import com.sonnobella.sonnobella.entidades.Oferta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OfertaRepositorio extends JpaRepository<Oferta, String> {
    
    @Query("SELECT o FROM Oferta o WHERE o.titulo = :titulo")
    public Oferta buscarPorTitulo(@Param("titulo") String titulo);
}
