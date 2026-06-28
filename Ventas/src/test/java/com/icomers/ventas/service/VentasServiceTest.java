package com.icomers.ventas.service; // <-- Paquete actualizado

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.icomers.ventas.model.Ventas;
import com.icomers.ventas.repository.VentasRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class VentasServiceTest {

    @Autowired
    private VentasServices ventasServices;

    @MockitoBean
    private VentasRepository ventasRepository;

    private Ventas createVentas() {
        Ventas venta = new Ventas();
        venta.setId(1L);
        venta.setTipoEnvio("Envío a Domicilio");
        venta.setTotal(25000.0);
        venta.setFechaVenta(LocalDateTime.now());
        return venta;
    }

    @Test
    void debeGuardarVenta() {
        Ventas ventaMock = createVentas();
        when(ventasRepository.save(any(Ventas.class))).thenReturn(ventaMock);

        Ventas resultado = ventasServices.guardarVenta(ventaMock);

        assertNotNull(resultado);
        assertEquals(25000.0, resultado.getTotal());
        // Verificamos que el repositorio se llamó 1 sola vez
        verify(ventasRepository, times(1)).save(any(Ventas.class)); 
    }

    @Test
    void debeListarVentas() {
        Ventas venta1 = createVentas();
        Ventas venta2 = createVentas();
        venta2.setId(2L);

        when(ventasRepository.findAll()).thenReturn(Arrays.asList(venta1, venta2));
        List<Ventas> resultados = ventasServices.listarVentas();
        assertNotNull(resultados);
        assertEquals(2, resultados.size());
        verify(ventasRepository, times(1)).findAll();
    }

    @Test
    void debeBuscarPorIdConExito() {
        Long idBuscado = 1L;
        Ventas ventaMock = createVentas();
        when(ventasRepository.findById(idBuscado)).thenReturn(Optional.of(ventaMock));
        Ventas resultado = ventasServices.buscarPorId(idBuscado);

        assertNotNull(resultado);
        assertEquals(idBuscado, resultado.getId());
    }

    @Test
    void debeLanzarExcepcionCuandoIdNoExiste() {
        Long idInexistente = 99L;
        when(ventasRepository.findById(idInexistente)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ventasServices.buscarPorId(idInexistente);
        });
        assertEquals("Venta no encontrada con ID: 99", exception.getMessage());
    }
}