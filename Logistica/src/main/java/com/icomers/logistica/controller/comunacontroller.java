package com.icomers.logistica.controller;

import com.icomers.logistica.model.comuna;
import com.icomers.logistica.service.comunaservice;
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
@RequestMapping("/api/v1/comuna")
@Tag(name = "Comunas", description = "Gestión de comunas del país")
public class comunacontroller {

    @Autowired
    private comunaservice comunaservice;

    @Operation(summary = "Listar todas las comunas")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "204", description = "Sin registros") })
    @GetMapping
    public ResponseEntity<?> listartodos() {
        List<comuna> lista = comunaservice.listartodos();
        if (!lista.isEmpty()) return new ResponseEntity<>(lista, HttpStatus.OK);
        return new ResponseEntity<>("sin registros", HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Buscar comuna por ID")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Encontrada"),
                    @ApiResponse(responseCode = "404", description = "No encontrada") })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarporid(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(comunaservice.buscarporid(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Crear comuna")
    @ApiResponses({ @ApiResponse(responseCode = "201", description = "Creada"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos") })
    @PostMapping
    public ResponseEntity<?> guardarcomuna(@Valid @RequestBody comuna objeto) {
        try {
            return new ResponseEntity<>(comunaservice.guardarcomuna(objeto), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("error en los datos", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Actualizar comuna")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Actualizada"),
                    @ApiResponse(responseCode = "404", description = "No encontrada") })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarcomuna(@PathVariable Integer id, @Valid @RequestBody comuna objeto) {
        try {
            return new ResponseEntity<>(comunaservice.actualizarcomuna(id, objeto), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("id no existe", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Eliminar comuna")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Eliminada"),
                    @ApiResponse(responseCode = "404", description = "No encontrada") })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarcomuna(@PathVariable Integer id) {
        try {
            comunaservice.eliminarcomuna(id);
            return new ResponseEntity<>("comuna eliminada", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("id no existe", HttpStatus.NOT_FOUND);
        }
    }
}
