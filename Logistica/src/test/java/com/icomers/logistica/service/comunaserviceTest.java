package com.icomers.logistica.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.icomers.logistica.model.comuna;
import com.icomers.logistica.repository.comunarepository;

@ExtendWith(MockitoExtension.class)
public class comunaserviceTest {

    @Mock
    private comunarepository comunarepository;
    
    @InjectMocks
    private comunaservice comunaservice;

    @Test
    void listartodos_ListaDeComunas() {
        
        comuna c1 = new comuna(1, "Santiago Centro", true, 1);
        comuna c2 = new comuna(2, "Providencia", true, 1);
        Mockito.when(comunarepository.findAll()).thenReturn(Arrays.asList(c1, c2));

        List<comuna> resultado = comunaservice.listartodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        Mockito.verify(comunarepository, Mockito.times(1)).findAll();
    }

    @Test
    void buscarporid_Comuna_Existe() {
        Integer idBuscado = 1;
        comuna comunaSimulada = new comuna(idBuscado, "Maipu", true, 1);
        Mockito.when(comunarepository.findById(idBuscado)).thenReturn(Optional.of(comunaSimulada));

        comuna resultado = comunaservice.buscarporid(idBuscado);

        assertNotNull(resultado);
        assertEquals("Maipu", resultado.getIdcomuna());
    }

    @Test
    void buscarporid_Excepcion_NoExiste() {
        Integer idBuscado = 99;
        Mockito.when(comunarepository.findById(idBuscado)).thenReturn(Optional.empty());

        RuntimeException excepcion = assertThrows(RuntimeException.class, () -> {
            comunaservice.buscarporid(idBuscado);
        });
        
        assertTrue(excepcion.getMessage().contains("no existe"));
    }

    @Test
    void guardarcomuna_ComunaGuardada() {
        comuna nuevaComuna = new comuna(null, "Puente Alto", true, 1);
        comuna comunaGuardada = new comuna(3, "Puente Alto", true, 1);
        
        Mockito.when(comunarepository.save(Mockito.any(comuna.class))).thenReturn(comunaGuardada);

        comuna resultado = comunaservice.guardarcomuna(nuevaComuna);

        assertNotNull(resultado.getIdcomuna());
        assertEquals("Puente Alto", resultado.getIdcomuna());
        Mockito.verify(comunarepository, Mockito.times(1)).save(nuevaComuna);
    }

    @Test
    void actualizarcomuna_ActualizarComuna() {
        Integer id = 1;
        comuna datosNuevos = new comuna(null, "Comuna Actualizada", null, null);
        comuna comunaExistente = new comuna(id, "Maipu", true, 1);
        
        
        Mockito.when(comunarepository.findById(id)).thenReturn(Optional.of(comunaExistente));
        Mockito.when(comunarepository.save(Mockito.any(comuna.class))).thenReturn(comunaExistente);

        comuna resultado = comunaservice.actualizarcomuna(id, datosNuevos);

        assertEquals("Comuna Actualizada", resultado.getIdcomuna());
        Mockito.verify(comunarepository, Mockito.times(1)).save(comunaExistente);
    }

    @Test
    void eliminarcomuna_Eliminar_Existe() {

        Integer id = 1;
        comuna comunaExistente = new comuna(id, "La Florida", true, 1);
        Mockito.when(comunarepository.findById(id)).thenReturn(Optional.of(comunaExistente));

       
        comunaservice.eliminarcomuna(id);

       
        Mockito.verify(comunarepository, Mockito.times(1)).delete(comunaExistente);
    }
}