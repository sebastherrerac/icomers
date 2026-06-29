package com.icomers.ventas.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.icomers.ventas.model.tipopago;
import com.icomers.ventas.repository.tipopagorepository;




@SpringBootTest
public class tipopagoserviceTest {

    @Autowired
    private tipopagoservice tipopagoService;

    @MockitoBean
    private tipopagorepository tipopagoRepository;

    tipopago createTipoPago() {
        tipopago pago = new tipopago();
        pago.setIdTipoPago(1);
        pago.setNombreTipoPago("Tarjeta de Crédito");
        return pago;
    }

    @Test
    void debeGuardarTipoPagoExitosamente() {
        tipopago pago = createTipoPago();
        when(tipopagoRepository.save(pago)).thenReturn(pago);

        tipopago resultado = tipopagoService.guardartipopago(pago);

        assertNotNull(resultado);
        assertEquals("Tarjeta de Crédito", resultado.getNombreTipoPago());
        verify(tipopagoRepository, times(1)).save(pago);
    }

    @Test
    void debeLanzarExcepcionAlGuardarTipoPago() {
        tipopago pago = createTipoPago();

        when(tipopagoRepository.save(pago)).thenThrow(new RuntimeException("No se pudo guardar el tipo de pago"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            tipopagoService.guardartipopago(pago);
        });

        assertEquals("No se pudo guardar el tipo de pago", exception.getMessage());
        verify(tipopagoRepository, times(1)).save(pago);
    }

    @Test
    void debeListarTipoPagos() {
        tipopago pago1 = createTipoPago();
        tipopago pago2 = new tipopago();
        pago2.setIdTipoPago(2);
        pago2.setNombreTipoPago("Efectivo");

        when(tipopagoRepository.findAll()).thenReturn(Arrays.asList(pago1, pago2));
        List<tipopago> resultados = tipopagoService.listartodos();
        assertNotNull(resultados);
        assertEquals(2, resultados.size());
        verify(tipopagoRepository, times(1)).findAll();
    }

    @Test
    void debeBuscarPorIdExitosamente() {
        tipopago pago = createTipoPago();
        when(tipopagoRepository.findById(1)).thenReturn(Optional.of(pago));

        tipopago resultado = tipopagoService.buscarporid(1);

        assertNotNull(resultado);
        assertEquals("Tarjeta de Crédito", resultado.getNombreTipoPago());
        verify(tipopagoRepository, times(1)).findById(1);
    }

    @Test
    void debeLanzarExcepcionCuandoIdNoExiste() {
        Integer idInexistente = 99;
        when(tipopagoRepository.findById(idInexistente)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            tipopagoService.buscarporid(idInexistente);
        });
        assertEquals("Tipo de pago no encontrado con ID: 99", exception.getMessage());
        verify(tipopagoRepository, times(1)).findById(idInexistente);
    }

    @Test
    void debeActualizarTipoPagoConExito() {
        Integer idActualizar = 1;
        tipopago pagoExistente = createTipoPago();
        tipopago nuevosDatos = new tipopago();
        nuevosDatos.setIdTipoPago(idActualizar);
        nuevosDatos.setNombreTipoPago("Transferencia Bancaria");

        when(tipopagoRepository.findById(idActualizar)).thenReturn(Optional.of(pagoExistente));
        when(tipopagoRepository.save(any(tipopago.class))).thenReturn(pagoExistente);

        tipopago resultado = tipopagoService.actualizartipopago(idActualizar, nuevosDatos);

        assertNotNull(resultado);
        assertEquals("Transferencia Bancaria", resultado.getNombreTipoPago());
        verify(tipopagoRepository, times(1)).findById(idActualizar);
        verify(tipopagoRepository, times(1)).save(pagoExistente);
    }

    @Test
    void debeEliminarTipoPagoConExito() {
        Integer idEliminar = 1;
        tipopago pagoExistente = createTipoPago();
        when(tipopagoRepository.findById(idEliminar)).thenReturn(Optional.of(pagoExistente));
        doNothing().when(tipopagoRepository).delete(pagoExistente);

        tipopagoService.eliminartipopago(idEliminar);

        verify(tipopagoRepository, times(1)).findById(idEliminar);
        verify(tipopagoRepository, times(1)).delete(pagoExistente);
    }
}