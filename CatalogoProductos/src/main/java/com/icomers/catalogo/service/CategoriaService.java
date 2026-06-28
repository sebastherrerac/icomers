package com.icomers.catalogo.service;

import com.icomers.catalogo.model.Categoria;
import com.icomers.catalogo.repository.CategoriaRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Transactional
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> obtenerTodos() {
        log.info("SERVICE: Listando todas las categorias");
        return categoriaRepository.findAll();
    }

    public Categoria guardarCategoria(Categoria categoria) {
        log.info("SERVICE: Guardando categoria: {}", categoria.getNombreCategoria());
        return categoriaRepository.save(categoria);
    }

    public Categoria actualizarCategoria(Integer id, Categoria datos) {
        log.info("SERVICE: Actualizando categoria ID: {}", id);
        Categoria existente = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La categoria con ID " + id + " no existe."));
        if (datos.getNombreCategoria() != null) existente.setNombreCategoria(datos.getNombreCategoria());
        return categoriaRepository.save(existente);
    }

    public String eliminarPorId(Integer id) {
        try {
            Categoria categoria = categoriaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("La categoria con id " + id + " no existe."));
            categoriaRepository.delete(categoria);
            return "La categoria " + categoria.getNombreCategoria() + " ha sido eliminada exitosamente.";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
}
