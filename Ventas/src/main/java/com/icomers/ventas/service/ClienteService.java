package com.icomers.ventas.service;

import com.icomers.ventas.model.Cliente;
import com.icomers.ventas.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Transactional
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listarTodos() {
        log.info("SERVICE: Listando todos los clientes");
        return clienteRepository.findAll();
    }

    public Cliente guardar(Cliente cliente) {
        log.info("SERVICE: Guardando cliente: {}", cliente.getNombre());
        return clienteRepository.save(cliente);
    }

    public Cliente actualizar(Integer id, Cliente datos) {
        log.info("SERVICE: Actualizando cliente ID: {}", id);
        Cliente existente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));
        existente.setNombre(datos.getNombre());
        existente.setEmail(datos.getEmail());
        if (datos.getTelefono() != null) existente.setTelefono(datos.getTelefono());
        if (datos.getDireccion() != null) existente.setDireccion(datos.getDireccion());
        return clienteRepository.save(existente);
    }

    public void eliminar(Integer id) {
        log.info("SERVICE: Eliminando cliente ID: {}", id);
        clienteRepository.deleteById(id);
    }
}
