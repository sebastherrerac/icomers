package com.icomers.catalogo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.icomers.catalogo.model.Categoria;
import com.icomers.catalogo.repository.CategoriaRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class CategoriaServiceTest {
    
    @Autowired
    private CategoriaService categoriaService;

    @MockitoBean
    private CategoriaRepository categoriaRepository;

    private Categoria createCategoria() {
        return new Categoria(1, "Categoria de prueba", new ArrayList<>());
    }

    @Test
    public void testFindAll() {
        when(categoriaRepository.findAll()).thenReturn(new ArrayList<>());
        List<Categoria> categorias = categoriaService.obtenerTodos();
        assertNotNull(categorias);
        assertEquals(0, categorias.size());
    }

    @Test
    public void testGuardarCategoria() {
        Categoria categoria = createCategoria();
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);
        Categoria savedCategoria = categoriaService.guardarCategoria(categoria);
        assertNotNull(savedCategoria);
        assertEquals("Categoria de prueba", savedCategoria.getNombreCategoria());
    }

    @Test
    public void testActualizarCategoria() {
        Categoria existingCategoria = createCategoria();
        Categoria pathData = new Categoria();

        when(categoriaRepository.findById(1)).thenReturn(java.util.Optional.of(existingCategoria));
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(existingCategoria);

        Categoria updatedCategoria = categoriaService.actualizarCategoria(1, pathData);
        assertNotNull(updatedCategoria);
        assertEquals("Categoria de prueba", updatedCategoria.getNombreCategoria());

    }

    @Test
    public void testEliminarPorId() {
        doNothing().when(categoriaRepository).deleteById(1);
        categoriaService.eliminarPorId(1);
        verify(categoriaRepository, times(1)).deleteById(1);
    }








}
