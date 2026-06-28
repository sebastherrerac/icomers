package com.icomers.catalogo.controller;

import com.icomers.catalogo.DTO.FigurasDTO;
import com.icomers.catalogo.model.Figuras;
import com.icomers.catalogo.service.FigurasService;
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
@RequestMapping("/api/v1/figuras")
@Tag(name = "Figuras", description = "Gestión de figuras de colección")
public class FigurasController {

    @Autowired
    private FigurasService figurasService;

    @Operation(summary = "Listar todas las figuras")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "204", description = "Sin registros") })
    @GetMapping
    public ResponseEntity<List<FigurasDTO>> obtenerTodos() {
        List<FigurasDTO> figuras = figurasService.obtenerTodos();
        if (figuras.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(figuras, HttpStatus.OK);
    }

    @Operation(summary = "Buscar figura por ID")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Encontrada"),
                    @ApiResponse(responseCode = "404", description = "No encontrada") })
    @GetMapping("/{id}")
    public ResponseEntity<FigurasDTO> buscarPorId(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(figurasService.buscarPorId(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Buscar figuras por categoría")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping("/categoria/{idCategoria}")
    public ResponseEntity<List<FigurasDTO>> buscarPorCategoria(@PathVariable Integer idCategoria) {
        List<FigurasDTO> figuras = figurasService.buscarPorCategoria(idCategoria);
        if (figuras.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(figuras, HttpStatus.OK);
    }

    @Operation(summary = "Crear figura")
    @ApiResponses({ @ApiResponse(responseCode = "201", description = "Creada"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos") })
    @PostMapping
    public ResponseEntity<Figuras> guardarFigura(@RequestBody Figuras figura) {
        try {
            return new ResponseEntity<>(figurasService.guardarFiguras(figura), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Actualizar figura")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Actualizada"),
                    @ApiResponse(responseCode = "404", description = "No encontrada") })
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<FigurasDTO> actualizarFigura(@PathVariable Integer id, @RequestBody FigurasDTO figura) {
        try {
            return new ResponseEntity<>(figurasService.actualizarFiguras(id, figura), HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminar figura")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Eliminada"),
                    @ApiResponse(responseCode = "404", description = "No encontrada") })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarFigura(@PathVariable Integer id) {
        String resultado = figurasService.eliminarFigura(id);
        if (resultado.contains("exitosamente")) return new ResponseEntity<>(resultado, HttpStatus.OK);
        return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
    }
}
