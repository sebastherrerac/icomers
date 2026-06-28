package com.icomers.catalogo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.icomers.catalogo.model.Personaje;
import com.icomers.catalogo.repository.PersonajeRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PersonajeServiceTest {

    @Autowired
    private PersonajeService personajeService;

    @MockitoBean
    private PersonajeRepository personajeRepository;

    private Personaje createPersonaje() {
        Personaje personaje = new Personaje();
        personaje.setId(1L);
        personaje.setNombre("Goku");
        personaje.setFranquicia("Dragon Ball");
        return personaje;
    }

    @Test
    void debeListarTodos() {
        Personaje p1 = createPersonaje();
        when(personajeRepository.findAll()).thenReturn(Arrays.asList(p1));

        List<Personaje> resultados = personajeService.listarTodos();

        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        verify(personajeRepository, times(1)).findAll();
    }

    @Test
    void debeBuscarPorNombreConExito() {
        String nombreBuscado = "Goku";
        Personaje personajeMock = createPersonaje();
        when(personajeRepository.findByNombre(nombreBuscado)).thenReturn(Optional.of(personajeMock));

        Personaje resultado = personajeService.buscarPorNombre(nombreBuscado);

        assertNotNull(resultado);
        assertEquals("Goku", resultado.getNombre());
        verify(personajeRepository, times(1)).findByNombre(nombreBuscado);
    }

    @Test
    void debeLanzarExcepcionCuandoNombreNoExiste() {
        String nombreInexistente = "Desconocido";
        when(personajeRepository.findByNombre(nombreInexistente)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            personajeService.buscarPorNombre(nombreInexistente);
        });

        assertEquals("No se encontró el personaje: Desconocido", exception.getMessage());
    }

    @Test
    void debeGuardarPersonaje() {
        Personaje personajeMock = createPersonaje();
        when(personajeRepository.save(any(Personaje.class))).thenReturn(personajeMock);

        Personaje resultado = personajeService.guardar(personajeMock);

        assertNotNull(resultado);
        assertEquals("Goku", resultado.getNombre());
        verify(personajeRepository, times(1)).save(any(Personaje.class));
    }

    @Test
    void debeEliminarPersonaje() {
        Long idEliminar = 1L;
        doNothing().when(personajeRepository).deleteById(idEliminar);

        personajeService.eliminar(idEliminar);

        verify(personajeRepository, times(1)).deleteById(idEliminar);
    }

    @Test
    void debeActualizarPersonajeConExito() {
        Long idActualizar = 1L;
        Personaje personajeExistente = createPersonaje();
        
        Personaje nuevosDatos = new Personaje();
        nuevosDatos.setNombre("Goku Super Saiyan");

        when(personajeRepository.findById(idActualizar)).thenReturn(Optional.of(personajeExistente));
        when(personajeRepository.save(any(Personaje.class))).thenReturn(personajeExistente);

        Personaje resultado = personajeService.actualizar(idActualizar, nuevosDatos);

        assertNotNull(resultado);
        assertEquals("Goku Super Saiyan", resultado.getNombre());
        verify(personajeRepository, times(1)).findById(idActualizar);
        verify(personajeRepository, times(1)).save(personajeExistente);
    }

    @Test
    void debeLanzarExcepcionAlActualizarIdInexistente() {
        Long idInexistente = 99L;
        Personaje nuevosDatos = new Personaje();
        when(personajeRepository.findById(idInexistente)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            personajeService.actualizar(idInexistente, nuevosDatos);
        });

        assertEquals("Personaje no encontrado con ID: 99", exception.getMessage());
    }
}