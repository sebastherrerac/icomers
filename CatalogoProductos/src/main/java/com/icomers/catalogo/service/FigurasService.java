package com.icomers.catalogo.service;

import com.icomers.catalogo.DTO.FigurasDTO;
import com.icomers.catalogo.model.Figuras;
import com.icomers.catalogo.repository.FigurasRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Transactional
public class FigurasService {

    @Autowired
    private FigurasRepository figurasRepository;

    public List<FigurasDTO> obtenerTodos() {
        log.info("SERVICE: Listando todas las figuras");
        return figurasRepository.findAll().stream().map(this::convertirADTO).toList();
    }

    public Figuras guardarFiguras(Figuras figura) {
        log.info("SERVICE: Guardando nueva figura: {}", figura.getNombre());
        return figurasRepository.save(figura);
    }

    public FigurasDTO buscarPorId(Integer id) {
        log.info("SERVICE: Buscando figura por ID: {}", id);
        Figuras figura = figurasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La figura con ID " + id + " no existe."));
        return convertirADTO(figura);
    }

    public List<FigurasDTO> buscarPorCategoria(Integer idCategoria) {
        log.info("SERVICE: Buscando figuras por categoria ID: {}", idCategoria);
        return figurasRepository.findByCategoria_IdCategoria(idCategoria).stream()
                .map(this::convertirADTO).toList();
    }

    public FigurasDTO actualizarFiguras(Integer id, FigurasDTO datos) {
        log.info("SERVICE: Actualizando figura ID: {}", id);
        Figuras existente = figurasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La figura con ID " + id + " no existe."));
        if (datos.getNombre() != null) existente.setNombre(datos.getNombre());
        if (datos.getPrecio() != null) existente.setPrecio(datos.getPrecio());
        if (datos.getStock() != null) existente.setStock(datos.getStock());
        if (datos.getCategoria() != null) existente.setCategoria(datos.getCategoria());
        if (datos.getDescripcion() != null) existente.setDescripcion(datos.getDescripcion());
        return convertirADTO(figurasRepository.save(existente));
    }

    public String eliminarFigura(Integer id) {
        log.info("SERVICE: Eliminando figura ID: {}", id);
        try {
            Figuras figura = figurasRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("La figura con ID " + id + " no existe."));
            figurasRepository.delete(figura);
            return "La figura " + figura.getNombre() + " ha sido eliminada exitosamente.";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    public FigurasDTO convertirADTO(Figuras figura) {
        FigurasDTO dto = new FigurasDTO();
        dto.setIdFigura(figura.getIdFigura());
        dto.setNombre(figura.getNombre());
        dto.setPrecio(figura.getPrecio());
        dto.setCategoria(figura.getCategoria());
        dto.setDescripcion(figura.getDescripcion());
        dto.setStock(figura.getStock());
        return dto;
    }
}
