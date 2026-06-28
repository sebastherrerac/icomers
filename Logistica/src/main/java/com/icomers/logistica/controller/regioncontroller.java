package com.icomers.logistica.controller;

import com.icomers.logistica.model.region;
import com.icomers.logistica.service.regionservice;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/region")
@Tag(name = "Regiones", description = "Gestión de regiones del país")
public class regioncontroller {

    @Autowired
    private regionservice regionservice;

    @Operation(summary = "Listar todas las regiones")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "204", description = "Sin registros") })
    @GetMapping
    public ResponseEntity<?> listartodos() {
        List<region> lista = regionservice.listartodos();
        if (!lista.isEmpty()) return new ResponseEntity<>(lista, HttpStatus.OK);
        return new ResponseEntity<>("sin registros", HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Buscar región por ID")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Encontrada"),
                    @ApiResponse(responseCode = "404", description = "No encontrada") })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarporid(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(regionservice.buscarporid(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Crear región")
    @ApiResponses({ @ApiResponse(responseCode = "201", description = "Creada"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos") })
    @PostMapping
    public ResponseEntity<?> guardarregion(@Valid @RequestBody region objeto) {
        try {
            return new ResponseEntity<>(regionservice.guardarregion(objeto), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("error en los datos", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Actualizar región")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Actualizada"),
                    @ApiResponse(responseCode = "404", description = "No encontrada") })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarregion(@PathVariable Integer id, @Valid @RequestBody region objeto) {
        try {
            return new ResponseEntity<>(regionservice.actualizarregion(id, objeto), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("id no existe", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Eliminar región")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Eliminada"),
                    @ApiResponse(responseCode = "404", description = "No encontrada") })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarregion(@PathVariable Integer id) {
        try {
            regionservice.eliminarregion(id);
            return new ResponseEntity<>("region eliminada", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("id no existe", HttpStatus.NOT_FOUND);
        }
    }
}
