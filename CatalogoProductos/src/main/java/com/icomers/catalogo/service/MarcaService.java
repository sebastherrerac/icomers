package com.icomers.catalogo.service;

import com.icomers.catalogo.model.Marca;
import com.icomers.catalogo.repository.MarcaRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Transactional
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    public List<Marca> obtenerTodos() {
        log.info("SERVICE: Listando todas las marcas");
        return marcaRepository.findAll();
    }

    public Marca guardarMarca(Marca marca) {
        log.info("SERVICE: Guardando marca: {}", marca.getNombreMarca());
        return marcaRepository.save(marca);
    }

    public Marca actualizarMarca(Integer id, Marca datos) {
        log.info("SERVICE: Actualizando marca ID: {}", id);
        Marca existente = marcaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La marca con ID " + id + " no existe."));
        if (datos.getNombreMarca() != null) existente.setNombreMarca(datos.getNombreMarca());
        return marcaRepository.save(existente);
    }

    public String eliminarMarca(Integer id) {
        try {
            Marca marca = marcaRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("La marca con id " + id + " no existe."));
            marcaRepository.delete(marca);
            return "Marca eliminada exitosamente";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
}
