package com.icomers.ventas.service;

import com.icomers.ventas.model.Ventas;
import com.icomers.ventas.repository.VentasRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class VentasServices {

    @Autowired
    private VentasRepository ventasRepository;

    public List<Ventas> listarVentas() {
        log.info("SERVICE: Listando todas las ventas");
        return ventasRepository.findAll();
    }

    public Ventas guardarVenta(Ventas venta) {
        log.info("SERVICE: Guardando venta con total: {}", venta.getTotal());
        if (venta.getFechaVenta() == null) venta.setFechaVenta(LocalDateTime.now());
        return ventasRepository.save(venta);
    }

    public Ventas buscarPorId(Long id) {
        log.info("SERVICE: Buscando venta por ID: {}", id);
        return ventasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));
    }

    public Ventas actualizar(Long id, Ventas datos) {
        log.info("SERVICE: Actualizando venta ID: {}", id);
        Ventas existente = ventasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));
        existente.setTipoEnvio(datos.getTipoEnvio());
        existente.setTotal(datos.getTotal());
        if (datos.getFechaVenta() != null) existente.setFechaVenta(datos.getFechaVenta());
        return ventasRepository.save(existente);
    }

    public void eliminar(Long id) {
        log.info("SERVICE: Eliminando venta ID: {}", id);
        ventasRepository.deleteById(id);
    }
}
