package com.sonnobella.sonnobella.repositorios;

import com.sonnobella.sonnobella.entidades.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnoRepositorio extends JpaRepository<Turno, String> {

}
