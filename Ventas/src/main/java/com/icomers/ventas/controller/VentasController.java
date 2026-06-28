package com.icomers.ventas.controller;

import com.icomers.ventas.model.Ventas;
import com.icomers.ventas.service.VentasServices;
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
@RequestMapping("/api/v1/ventas")
@CrossOrigin(origins = "*")
@Tag(name = "Ventas", description = "Gestión de ventas del e-commerce")
public class VentasController {

    @Autowired
    private VentasServices ventasService;

    @Operation(summary = "Listar todas las ventas")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping
    public List<Ventas> obtenerTodas() {
        return ventasService.listarVentas();
    }

    @Operation(summary = "Registrar venta")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Registrada"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos") })
    @PostMapping
    public Ventas guardar(@RequestBody Ventas venta) {
        return ventasService.guardarVenta(venta);
    }

    @Operation(summary = "Buscar venta por ID")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Encontrada"),
                    @ApiResponse(responseCode = "404", description = "No encontrada") })
    @GetMapping("/{id}")
    public ResponseEntity<Ventas> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ventasService.buscarPorId(id));
    }

    @Operation(summary = "Actualizar venta")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Actualizada"),
                    @ApiResponse(responseCode = "404", description = "No encontrada") })
    @PutMapping("/{id}")
    public ResponseEntity<Ventas> actualizar(@PathVariable Long id, @RequestBody Ventas datos) {
        return ResponseEntity.ok(ventasService.actualizar(id, datos));
    }

    @Operation(summary = "Eliminar venta")
    @ApiResponse(responseCode = "204", description = "Eliminada")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        ventasService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
