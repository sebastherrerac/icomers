package com.icomers.logistica.service;

import com.icomers.logistica.model.comuna;
import com.icomers.logistica.repository.comunarepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Transactional
public class comunaservice {

    @Autowired
    private comunarepository comunarepository;

    public List<comuna> listartodos() {
        log.info("SERVICE: Listando todas las comunas");
        return comunarepository.findAll();
    }

    public comuna buscarporid(Integer id) {
        log.info("SERVICE: Buscando comuna ID: {}", id);
        return comunarepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La comuna con ID " + id + " no existe."));
    }

    public comuna guardarcomuna(comuna nueva) {
        log.info("SERVICE: Guardando comuna: {}", nueva.getNombreComuna());
        return comunarepository.save(nueva);
    }

    public comuna actualizarcomuna(Integer id, comuna datos) {
        log.info("SERVICE: Actualizando comuna ID: {}", id);
        comuna existente = comunarepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La comuna con ID " + id + " no existe."));
        if (datos.getNombreComuna() != null) existente.setNombreComuna(datos.getNombreComuna());
        if (datos.getActivo() != null) existente.setActivo(datos.getActivo());
        if (datos.getRegionid() != null) existente.setRegionid(datos.getRegionid());
        return comunarepository.save(existente);
    }

    public void eliminarcomuna(Integer id) {
        log.info("SERVICE: Eliminando comuna ID: {}", id);
        comuna existente = comunarepository.findById(id)
                .orElseThrow(() -> new RuntimeException("La comuna con ID " + id + " no existe."));
        comunarepository.delete(existente);
    }
}
