package com.icomers.logistica.service;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.icomers.logistica.model.tipoenvio;
import com.icomers.logistica.repository.tipoenviorepository;

@SpringBootTest
public class tipoenvioserviceTest {

    @Autowired
    private tipoenvioservice tipoenvioService;

    @MockitoBean
    private tipoenviorepository tipoenvioRepository;

    private tipoenvio createTipoEnvio() {
        tipoenvio envio = new tipoenvio();
        envio.setIdEnvio(1);
        envio.setNombreEnvio("Envio");
        envio.setCostoEnvio(3000.0);
        envio.setActivoEnvio(true);
        return envio;
    }

    @Test
    void debeListarTodosLosTiposEnvio() {
        tipoenvio envio1 = createTipoEnvio();
        when(tipoenvioRepository.findAll()).thenReturn(Arrays.asList(envio1));

        List<tipoenvio> resultado = tipoenvioService.listartodos();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(tipoenvioRepository, times(1)).findAll();
    }

    @Test
    void debeBuscarPorIdExitosamente() {
        Integer idBuscado = 1;
        tipoenvio envio = createTipoEnvio();
        when(tipoenvioRepository.findById(idBuscado)).thenReturn(Optional.of(envio));

        tipoenvio resultado = tipoenvioService.buscarporid(idBuscado);

        assertNotNull(resultado);
        assertEquals("Envio", resultado.getNombreEnvio());
        verify(tipoenvioRepository, times(1)).findById(idBuscado);
    }

    @Test
    void debeLanzarExcepcionCuandoIdNoExiste() {
        Integer idInexistente = 67;
        when(tipoenvioRepository.findById(idInexistente)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            tipoenvioService.buscarporid(idInexistente);
        });

        assertEquals("Error, El envio con ID 67 no existe" , exception.getMessage());
        verify(tipoenvioRepository, times(1)).findById(idInexistente);
    }

    @Test
    void debeGuardarTipoEnvioExitosamente() {
        tipoenvio envio = createTipoEnvio();
        when(tipoenvioRepository.save(envio)).thenReturn(envio);

        tipoenvio resultado = tipoenvioService.guardartipoenvio(envio);

        assertNotNull(resultado);
        assertEquals("Envio", resultado.getNombreEnvio());
        assertEquals(3000.0, resultado.getCostoEnvio());
        verify(tipoenvioRepository, times(1)).save(envio);
    }

    @Test
    void debeLanzarExcepcionAlGuardarTipoEnvio() {
        tipoenvio envio = createTipoEnvio();
        when(tipoenvioRepository.save(envio)).thenThrow(new RuntimeException("No se pudo guardar el tipo de envio"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            tipoenvioService.guardartipoenvio(envio);
        });

        assertEquals("No se pudo guardar el tipo de envio", exception.getMessage());
        verify(tipoenvioRepository, times(1)).save(envio);
    }

    @Test
    void debeActualizarTipoenvioExitosamente(){
        Integer idActualizar = 1;
        tipoenvio envioExistente = createTipoEnvio();
        
        tipoenvio envioActualizado = new tipoenvio();
        envioActualizado.setNombreEnvio("Envio modificado");
        envioActualizado.setCostoEnvio(3500.0);
        envioActualizado.setActivoEnvio(true);
        
        when(tipoenvioRepository.findById(idActualizar)).thenReturn(Optional.of(envioExistente));
        when(tipoenvioRepository.save(any(tipoenvio.class))).thenReturn(envioExistente);

        tipoenvio resultado = tipoenvioService.actualizartipoenvio(idActualizar, envioActualizado);

        assertNotNull(resultado);
        assertEquals("Envio modificado", resultado.getNombreEnvio());
        assertEquals(3500.0, resultado.getCostoEnvio());
        verify(tipoenvioRepository, times(3)).findById(idActualizar);
        verify(tipoenvioRepository, times(1)).save(envioExistente);

    }

    @Test
    void debeEliminarTipoenvioExitosamente() {
        Integer idEliminar = 1;
        tipoenvio envioExistente = createTipoEnvio();
        when(tipoenvioRepository.findById(idEliminar)).thenReturn(Optional.of(envioExistente));
        doNothing().when(tipoenvioRepository).delete(envioExistente);

        tipoenvioService.eliminartipoenvio(idEliminar);

        verify(tipoenvioRepository, times(1)).findById(idEliminar);
        verify(tipoenvioRepository, times(1)).delete(envioExistente);
    }

}
