package com.icomers.logistica.service;

import com.icomers.logistica.model.region;
import com.icomers.logistica.repository.regionrepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Transactional
public class regionservice {

    @Autowired
    private regionrepository regionrepository;

    public List<region> listartodos() {
        log.info("SERVICE: Listando todas las regiones");
        return regionrepository.findAll();
    }

    public region buscarporid(Integer id) {
        log.info("SERVICE: Buscando region ID: {}", id);
        return regionrepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La region con ID " + id + " no existe."));
    }

    public region guardarregion(region nueva) {
        log.info("SERVICE: Guardando region: {}", nueva.getNombreRegion());
        return regionrepository.save(nueva);
    }

    public region actualizarregion(Integer id, region datos) {
        log.info("SERVICE: Actualizando region ID: {}", id);
        region existente = regionrepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La region con ID " + id + " no existe."));
        if (datos.getNombreRegion() != null) existente.setNombreRegion(datos.getNombreRegion());
        if (datos.getActivo() != null) existente.setActivo(datos.getActivo());
        return regionrepository.save(existente);
    }

    public void eliminarregion(Integer id) {
        log.info("SERVICE: Eliminando region ID: {}", id);
        region existente = regionrepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La region con ID " + id + " no existe."));
        regionrepository.delete(existente);
    }
}
