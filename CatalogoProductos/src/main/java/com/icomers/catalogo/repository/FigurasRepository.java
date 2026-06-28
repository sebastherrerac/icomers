package com.icomers.catalogo.repository;

import com.icomers.catalogo.model.Figuras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FigurasRepository extends JpaRepository<Figuras, Integer> {
    List<Figuras> findByCategoria_IdCategoria(Integer idCategoria);
}
