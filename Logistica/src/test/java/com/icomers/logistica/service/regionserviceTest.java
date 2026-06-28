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

import com.icomers.logistica.model.region;
import com.icomers.logistica.repository.regionrepository;

@ExtendWith(MockitoExtension.class)
public class regionserviceTest {

    @Mock
    private regionrepository regionrepository;

    @InjectMocks
    private regionservice regionservice;

    @Test
    void listartodos_DeberiaRetornarListaDeRegiones() {
        region r1 = new region(1, "Metropolitana", true);
        region r2 = new region(2, "Valparaiso", true);
        Mockito.when(regionrepository.findAll()).thenReturn(Arrays.asList(r1, r2));

        List<region> resultado = regionservice.listartodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        Mockito.verify(regionrepository, Mockito.times(1)).findAll();
    }

    @Test
    void buscarporid_DeberiaRetornarRegion_CuandoExiste() {
        Integer idBuscado = 1;
        region regionSimulada = new region(idBuscado, "Metropolitana", true);
        Mockito.when(regionrepository.findById(idBuscado)).thenReturn(Optional.of(regionSimulada));

        region resultado = regionservice.buscarporid(idBuscado);

        assertNotNull(resultado);
        assertEquals("Metropolitana", resultado.getNombreRegion());
    }

    @Test
    void buscarporid_DeberiaLanzarExcepcion_CuandoNoExiste() {
        Integer idBuscado = 99;
        Mockito.when(regionrepository.findById(idBuscado)).thenReturn(Optional.empty());

        RuntimeException excepcion = assertThrows(RuntimeException.class, () -> {
            regionservice.buscarporid(idBuscado);
        });
        
        assertTrue(excepcion.getMessage().contains("no existe"));
    }

    @Test
    void guardarregion_DeberiaRetornarRegionGuardada() {
        region nuevaRegion = new region(null, "Araucania", true);
        region regionGuardada = new region(3, "Araucania", true);
        
        Mockito.when(regionrepository.save(Mockito.any(region.class))).thenReturn(regionGuardada);

        region resultado = regionservice.guardarregion(nuevaRegion);
        assertNotNull(resultado.getIdRegion());
        assertEquals("Araucania", resultado.getNombreRegion());
        Mockito.verify(regionrepository, Mockito.times(1)).save(nuevaRegion);
    }

    @Test
    void actualizarregion_DeberiaActualizarYRetornarRegion() {
        Integer id = 1;
        region datosNuevos = new region(null, "Region Actualizada", null);
        region regionExistente = new region(id, "Metropolitana", true);
        
       
        Mockito.when(regionrepository.findById(id)).thenReturn(Optional.of(regionExistente));
        Mockito.when(regionrepository.save(Mockito.any(region.class))).thenReturn(regionExistente);

    
        region resultado = regionservice.actualizarregion(id, datosNuevos);

        
        assertEquals("Region Actualizada", resultado.getNombreRegion());
        Mockito.verify(regionrepository, Mockito.times(1)).save(regionExistente);
    }

    @Test
    void eliminarregion_DeberiaEliminar_CuandoExiste() {
       
        Integer id = 1;
        region regionExistente = new region(id, "Metropolitana", true);
        Mockito.when(regionrepository.findById(id)).thenReturn(Optional.of(regionExistente));

        
        regionservice.eliminarregion(id);

     
        Mockito.verify(regionrepository, Mockito.times(1)).delete(regionExistente);
    }
}