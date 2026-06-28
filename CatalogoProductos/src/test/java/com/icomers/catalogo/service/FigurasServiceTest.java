package com.icomers.catalogo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.icomers.catalogo.DTO.FigurasDTO;
import com.icomers.catalogo.model.Figuras;
import com.icomers.catalogo.repository.FigurasRepository;

@SpringBootTest
public class FigurasServiceTest {

    @Autowired
    private FigurasService figurasService;

    @MockitoBean
    private FigurasRepository figurasRepository;

    private Figuras createFigura() {
        Figuras figura = new Figuras();
        figura.setIdFigura(1);
        figura.setNombre("Figura Test");
        figura.setPrecio(15000.0);
        figura.setStock(10);
        figura.setDescripcion("Descripción de prueba");
        return figura;
    }

    private FigurasDTO createFiguraDTO() {
        FigurasDTO dto = new FigurasDTO();
        dto.setIdFigura(1);
        dto.setNombre("Figura Actualizada");
        dto.setPrecio(20000.0);
        dto.setStock(5);
        return dto;
    }

    @Test
    void debeObtenerTodos() {
        Figuras figura = createFigura();
        when(figurasRepository.findAll()).thenReturn(Arrays.asList(figura));

        List<FigurasDTO> resultados = figurasService.obtenerTodos();

        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        assertEquals("Figura Test", resultados.get(0).getNombre());
        verify(figurasRepository, times(1)).findAll();
    }

    @Test
    void debeGuardarFiguras() {
        Figuras figuraMock = createFigura();
        when(figurasRepository.save(any(Figuras.class))).thenReturn(figuraMock);

        Figuras resultado = figurasService.guardarFiguras(figuraMock);

        assertNotNull(resultado);
        assertEquals("Figura Test", resultado.getNombre());
        verify(figurasRepository, times(1)).save(any(Figuras.class));
    }

    @Test
    void debeBuscarPorIdConExito() {
        Integer idBuscado = 1;
        Figuras figuraMock = createFigura();
        when(figurasRepository.findById(idBuscado)).thenReturn(Optional.of(figuraMock));

        FigurasDTO resultado = figurasService.buscarPorId(idBuscado);

        assertNotNull(resultado);
        assertEquals(idBuscado, resultado.getIdFigura());
    }

    @Test
    void debeLanzarExcepcionAlBuscarIdInexistente() {
        Integer idInexistente = 99;
        when(figurasRepository.findById(idInexistente)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            figurasService.buscarPorId(idInexistente);
        });

        assertEquals("Error, La figura con ID 99 no existe.", exception.getMessage());
    }

    @Test
    void debeBuscarPorCategoria() {
        Integer idCategoria = 1;
        Figuras figura = createFigura();
        when(figurasRepository.findByCategoria_IdCategoria(idCategoria)).thenReturn(Arrays.asList(figura));

        List<FigurasDTO> resultados = figurasService.buscarPorCategoria(idCategoria);

        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        verify(figurasRepository, times(1)).findByCategoria_IdCategoria(idCategoria);
    }

    @Test
    void debeActualizarFigurasConExito() {
        Integer idActualizar = 1;
        Figuras figuraExistente = createFigura();
        FigurasDTO nuevosDatos = createFiguraDTO();

        when(figurasRepository.findById(idActualizar)).thenReturn(Optional.of(figuraExistente));
        when(figurasRepository.save(any(Figuras.class))).thenReturn(figuraExistente);

        FigurasDTO resultado = figurasService.actualizarFiguras(idActualizar, nuevosDatos);

        assertNotNull(resultado);
        assertEquals("Figura Actualizada", resultado.getNombre());
        verify(figurasRepository, times(1)).findById(idActualizar);
        verify(figurasRepository, times(1)).save(figuraExistente);
    }

    @Test
    void debeEliminarFiguraConExito() {
        Integer idEliminar = 1;
        Figuras figuraExistente = createFigura();
        when(figurasRepository.findById(idEliminar)).thenReturn(Optional.of(figuraExistente));
        doNothing().when(figurasRepository).delete(figuraExistente);

        String mensaje = figurasService.eliminarFigura(idEliminar);

        assertEquals("La figura Figura Test ha sido eliminada exitosamente.", mensaje);
        verify(figurasRepository, times(1)).delete(figuraExistente);
    }

    @Test
    void debeRetornarMensajeDeErrorAlEliminarIdInexistente() {
        Integer idInexistente = 99;
        when(figurasRepository.findById(idInexistente)).thenReturn(Optional.empty());

        String mensaje = figurasService.eliminarFigura(idInexistente);

        assertEquals("La figura con ID 99 no existe.", mensaje);
        verify(figurasRepository, never()).delete(any());
    }
}