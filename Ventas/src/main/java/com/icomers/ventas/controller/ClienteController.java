package com.icomers.ventas.controller;

import com.icomers.ventas.model.Cliente;
import com.icomers.ventas.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/clientes")
@Tag(name = "Clientes", description = "Gestion de clientes del e-commerce")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Listar todos los clientes")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping
    public List<Cliente> obtenerTodos() {
        return clienteService.listarTodos();
    }

    @Operation(summary = "Crear cliente")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Creado"),
                    @ApiResponse(responseCode = "400", description = "Datos invalidos") })
    @PostMapping
    public Cliente guardar(@RequestBody Cliente cliente) {
        return clienteService.guardar(cliente);
    }

    @Operation(summary = "Actualizar cliente")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Actualizado"),
                    @ApiResponse(responseCode = "404", description = "No encontrado") })
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizar(@PathVariable Integer id, @RequestBody Cliente datos) {
        return ResponseEntity.ok(clienteService.actualizar(id, datos));
    }

    @Operation(summary = "Eliminar cliente")
    @ApiResponse(responseCode = "204", description = "Eliminado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
