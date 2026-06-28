package com.icomers.catalogo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.List;
import java.util.Arrays;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.icomers.catalogo.model.Marca;
import com.icomers.catalogo.repository.MarcaRepository;


@SpringBootTest
public class MarcaServiceTest {

    @Autowired
    private MarcaService marcaService;

    @MockitoBean
    private MarcaRepository marcaRepository;

    private Marca createMarca() {
        Marca marca = new Marca();
        marca.setIdMarca(1);
        marca.setNombreMarca("Marca Test");
        return marca;
    }

    @Test
    void debeObtenerTodasLasMarcas() {
        Marca marca1 = createMarca();
        when(marcaRepository.findAll()).thenReturn(Arrays.asList(marca1));

        List<Marca> resultado = marcaService.obtenerTodos();

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(marcaRepository, times(1)).findAll();
    }

    @Test
    void debeGuardarMarcaExitosamente() {
        Marca marca = createMarca();
        when(marcaRepository.save(marca)).thenReturn(marca);

        Marca resultado = marcaService.guardarMarca(marca);

        assertNotNull(resultado);
        assertEquals("Marca Test", resultado.getNombreMarca());
        verify(marcaRepository, times(1)).save(marca);
    }

    @Test
    void debeActualizarMarcaExitosamente(){
        Integer idActualizar = 1;
        Marca marcaExistente = createMarca();

        Marca nuevaMarca = new Marca();
        nuevaMarca.setNombreMarca("Marca Actualizada");
        
        when(marcaRepository.findById(idActualizar)).thenReturn(Optional.of(marcaExistente));
        when(marcaRepository.save(any(Marca.class))).thenReturn(marcaExistente);
        
        Marca resultado = marcaService.actualizarMarca(idActualizar, nuevaMarca);

        assertNotNull(resultado);
        assertEquals("Marca Actualizada", marcaExistente.getNombreMarca());
        verify(marcaRepository, times(1)).findById(idActualizar);
        verify(marcaRepository, times(1)).save(marcaExistente);
    }

    @Test
    void debeLanzarExcepcionAlActualizarMarcaInexistente() {
        Integer idInexistente = 99;
        Marca nuevaMarca = new Marca();
        nuevaMarca.setNombreMarca("Marca Inexistente");

        when(marcaRepository.findById(idInexistente)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            marcaService.actualizarMarca(idInexistente, nuevaMarca);
        });

        assertEquals("No se encontro la marca con ID: " + idInexistente, exception.getMessage());
        verify(marcaRepository, times(1)).findById(idInexistente);
    }

    @Test
    void debeEliminarMarcaExitosamente() {
        Integer idEliminar = 1;
        Marca marcaExistente = createMarca();
        when(marcaRepository.findById(idEliminar)).thenReturn(Optional.of(marcaExistente));
        doNothing().when(marcaRepository).delete(marcaExistente);

        marcaService.eliminarMarca(idEliminar);


        verify(marcaRepository, times(1)).findById(idEliminar);
        verify(marcaRepository, times(1)).delete(marcaExistente);
    }

    @Test
    void debeRetornarExcepcionAlEliminarMarcaInexistente() {
        Integer idInexistente = 99;
        when(marcaRepository.findById(idInexistente)).thenReturn(Optional.empty());

        String resultado = marcaService.eliminarMarca(idInexistente);

        assertEquals("No se encontro la marca con ID: " + idInexistente, resultado);
        verify(marcaRepository, times(1)).findById(idInexistente);
    }

}
