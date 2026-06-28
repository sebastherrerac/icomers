package com.icomers.ventas.service;

import com.icomers.ventas.model.tipopago;
import com.icomers.ventas.repository.tipopagorepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Transactional
public class tipopagoservice {

    @Autowired
    private tipopagorepository tipopagorepository;

    public List<tipopago> listartodos() {
        log.info("SERVICE: Listando todos los tipos de pago");
        return tipopagorepository.findAll();
    }

    public tipopago buscarporid(Integer id) {
        log.info("SERVICE: Buscando tipo de pago ID: {}", id);
        return tipopagorepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El tipo de pago con ID " + id + " no existe."));
    }

    public tipopago guardartipopago(tipopago nuevo) {
        log.info("SERVICE: Guardando tipo de pago: {}", nuevo.getNombreTipoPago());
        return tipopagorepository.save(nuevo);
    }

    public tipopago actualizartipopago(Integer id, tipopago datos) {
        log.info("SERVICE: Actualizando tipo de pago ID: {}", id);
        tipopago existente = tipopagorepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El tipo de pago con ID " + id + " no existe."));
        if (datos.getNombreTipoPago() != null) existente.setNombreTipoPago(datos.getNombreTipoPago());
        return tipopagorepository.save(existente);
    }

    public void eliminartipopago(Integer id) {
        log.info("SERVICE: Eliminando tipo de pago ID: {}", id);
        tipopago existente = tipopagorepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El tipo de pago con ID " + id + " no existe."));
        tipopagorepository.delete(existente);
    }
}
