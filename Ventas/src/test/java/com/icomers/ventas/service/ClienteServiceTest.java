package com.icomers.ventas.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.icomers.ventas.model.Cliente;
import com.icomers.ventas.repository.ClienteRepository;

@SpringBootTest
public class ClienteServiceTest {

    @Autowired
    private ClienteService clienteService;

    @MockitoBean
    private ClienteRepository clienteRepository;
    
    private Cliente createCliente() {
        return new Cliente(1, "pedro", "pedro@example.com", "123456789", "Calle Falsa 123");

    }

    @Test
    public void testFindAll() {
        
        when(clienteRepository.findAll()).thenReturn(List.of(createCliente()));
        List<Cliente> clientes = clienteService.listarTodos();
        assertNotNull(clientes);
        assertEquals(1, clientes.size());



    }

    @Test
    public void testGuardarCliente() {
        Cliente cliente = createCliente();
        when(clienteRepository.save(cliente)).thenReturn(cliente);
        Cliente savedCliente = clienteService.guardar(cliente);
        assertNotNull(savedCliente);
        assertEquals("pedro", savedCliente.getNombre());
    }

    @Test
    public void testActualizarCliente() {
        Cliente existingCliente = createCliente();
        Cliente pathData = new Cliente();

        when(clienteRepository.findById(1)).thenReturn(java.util.Optional.of(existingCliente));
        when(clienteRepository.save(existingCliente)).thenReturn(existingCliente);

        Cliente updatedCliente = clienteService.actualizar(1, pathData);
        assertNotNull(updatedCliente);
        assertEquals("pedro", updatedCliente.getNombre());

    }



}
