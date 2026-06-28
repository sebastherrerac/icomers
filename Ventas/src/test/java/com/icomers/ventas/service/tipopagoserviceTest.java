package com.icomers.ventas.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import com.icomers.ventas.model.tipopago;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.icomers.ventas.repository.tipopagorepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;




@SpringBootTest
public class tipopagoserviceTest {

    @Autowired
    private tipopagoservice tipopagoService;

    @MockitoBean
    private tipopagorepository tipopagoRepository;

    private tipoPago createTipoPago() {
        tipoPago pago = new tipoPago();
        pago.setIdTipoPago(1);
        pago.setNombreTipoPago("Tarjeta de Crédito");
        return pago;
    }

    @Test
    private void debeGuardarTipoPagoExitosamente() {
        tipoPago pago = createTipoPago();
        when(tipoPagoRepository.save(pago)).thenReturn(pago);

        tipoPago resultado = tipoPagoService.guardartipopago(pago);

        assertNotNull(resultado);
        assertEquals("Tarjeta de Crédito", resultado.getNombreTipoPago());
        verify(tipoPagoRepository, times(1)).save(pago);
    }

    @Test
    private void debeLanzarExcepcionAlGuardarTipoPago() {
        tipopago pago = createTipoPago();

        when(tipopagorepository.save(pago)).thenThrow(new RuntimeException("No se pudo guardar el tipo de pago"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            tipopagoservice.guardartipopago(pago);
        });

        assertEquals("No se pudo guardar el tipo de pago", exception.getMessage());
        verify(tipopagorepository, times(1)).save(pago);
    }

    @Test
    private void debeListarTipoPagos() {
        tipoPago pago1 = createTipoPago();
        tipoPago pago2 = new tipopago();
        pago2.setIdTipoPago(2);
        pago2.setNombreTipoPago("Efectivo");

        when(tipoPagoRepository.findAll()).thenReturn(Arrays.asList(pago1, pago2));
        List<tipoPago> resultados = tipoPagoService.listartodos();
        assertNotNull(resultados);
        assertEquals(2, resultados.size());
        verify(tipoPagoRepository, times(1)).findAll();
    }

    @Test
    private void debeBuscarPorIdExitosamente() {
        tipoPago pago = createTipoPago();
        when(tipoPagoRepository.findById(1)).thenReturn(Optional.of(pago));

        tipopago resultado = tipoPagoService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals("Tarjeta de Crédito", resultado.getNombretipoPago());
        verify(tipoPagoRepository, times(1)).findById(1);
    }

    @Test
    private void debeLanzarExcepcionCuandoIdNoExiste() {
        Integer idInexistente = 99;
        when(tipoPagoRepository.findById(idInexistente)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            tipoPagoService.buscarPorId(idInexistente);
        });
        assertEquals("Tipo de pago no encontrado con ID: 99", exception.getMessage());
        verify(tipoPagoRepository, times(1)).findById(idInexistente);
    }

    @Test
    private void debeActualizarTipoPagoConExito() {
        Integer idActualizar = 1;
        tipoPago pagoExistente = createTipoPago();
        tipopago nuevosDatos = new tipopago();
        nuevosDatos.setIdTipoPago(idActualizar);
        nuevosDatos.setNombreTipoPago("Transferencia Bancaria");

        when(tipoPagoRepository.findById(idActualizar)).thenReturn(Optional.of(pagoExistente));
        when(tipoPagoRepository.save(any(tipoPago.class))).thenReturn(pagoExistente);

        tipoPago resultado = tipoPagoService.actualizarTipoPago(idActualizar, nuevosDatos);

        assertNotNull(resultado);
        assertEquals("Transferencia Bancaria", resultado.getNombre());
        verify(tipoPagoRepository, times(1)).findById(idActualizar);
        verify(tipoPagoRepository, times(1)).save(pagoExistente);
    }

    @Test
    private void debeEliminarTipoPagoConExito() {
        Integer idEliminar = 1;
        tipoPago pagoExistente = createTipoPago();
        when(tipoPagoRepository.findById(idEliminar)).thenReturn(Optional.of(pagoExistente));
        doNothing().when(tipoPagoRepository).delete(pagoExistente);

        tipoPagoService.eliminarTipoPago(idEliminar);

        verify(tipoPagoRepository, times(1)).findById(idEliminar);
        verify(tipoPagoRepository, times(1)).delete(pagoExistente);
    }
}