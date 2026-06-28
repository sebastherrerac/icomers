package com.icomers.catalogo.controller;

import com.icomers.catalogo.model.Marca;
import com.icomers.catalogo.service.MarcaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/marcas")
@Tag(name = "Marcas", description = "Gestión de marcas de figuras de colección")
public class MarcaController {

    @Autowired
    private MarcaService marcaService;

    @Operation(summary = "Listar todas las marcas")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping
    public ResponseEntity<List<Marca>> obtenerTodas() {
        List<Marca> marcas = marcaService.obtenerTodos();
        if (marcas.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(marcas);
    }

    @Operation(summary = "Crear marca")
    @ApiResponses({ @ApiResponse(responseCode = "201", description = "Creada"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos") })
    @PostMapping
    public ResponseEntity<Marca> agregarMarca(@RequestBody Marca marca) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(marcaService.guardarMarca(marca));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Actualizar marca")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Actualizada"),
                    @ApiResponse(responseCode = "400", description = "Error") })
    @PutMapping("/{id}")
    public ResponseEntity<Marca> actualizarMarca(@PathVariable Integer id, @RequestBody Marca marca) {
        try {
            return ResponseEntity.ok(marcaService.actualizarMarca(id, marca));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Eliminar marca")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Eliminada"),
                    @ApiResponse(responseCode = "404", description = "No encontrada") })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMarca(@PathVariable Integer id) {
        String resultado = marcaService.eliminarMarca(id);
        if (resultado.equals("Marca eliminada exitosamente")) return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }
}
