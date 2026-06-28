package com.icomers.logistica.service;

import com.icomers.logistica.model.tipoenvio;
import com.icomers.logistica.repository.tipoenviorepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Transactional
public class tipoenvioservice {

    @Autowired
    private tipoenviorepository tipoenviorepository;

    public List<tipoenvio> listartodos() {
        log.info("SERVICE: Listando todos los tipos de envio");
        return tipoenviorepository.findAll();
    }

    public tipoenvio buscarporid(Integer id) {
        log.info("SERVICE: Buscando tipo de envio ID: {}", id);
        return tipoenviorepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El envio con ID " + id + " no existe."));
    }

    public tipoenvio guardartipoenvio(tipoenvio nuevo) {
        log.info("SERVICE: Guardando tipo de envio: {}", nuevo.getNombreEnvio());
        return tipoenviorepository.save(nuevo);
    }

    public tipoenvio actualizartipoenvio(Integer id, tipoenvio datos) {
        log.info("SERVICE: Actualizando tipo de envio ID: {}", id);
        tipoenvio existente = tipoenviorepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El envio con ID " + id + " no existe."));
        if (datos.getNombreEnvio() != null) existente.setNombreEnvio(datos.getNombreEnvio());
        if (datos.getCostoEnvio() != null) existente.setCostoEnvio(datos.getCostoEnvio());
        if (datos.getActivoEnvio() != null) existente.setActivoEnvio(datos.getActivoEnvio());
        return tipoenviorepository.save(existente);
    }

    public void eliminartipoenvio(Integer id) {
        log.info("SERVICE: Eliminando tipo de envio ID: {}", id);
        tipoenvio existente = tipoenviorepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El envio con ID " + id + " no existe."));
        tipoenviorepository.delete(existente);
    }
}
