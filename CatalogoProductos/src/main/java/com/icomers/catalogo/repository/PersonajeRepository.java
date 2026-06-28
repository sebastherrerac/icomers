package com.icomers.catalogo.repository;

import com.icomers.catalogo.model.Personaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonajeRepository extends JpaRepository<Personaje, Long> {
    Optional<Personaje> findByNombre(String nombre);
}
