package com.icomers.logistica.controller;

import com.icomers.logistica.model.tipoenvio;
import com.icomers.logistica.service.tipoenvioservice;
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
@RequestMapping("/api/v1/tipoenvio")
@Tag(name = "Tipos de Envio", description = "Gestion de modalidades de envio")
public class tipoenviocontroller {

    @Autowired
    private tipoenvioservice tipoenvioservice;

    @Operation(summary = "Listar tipos de envío")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "204", description = "Sin registros") })
    @GetMapping
    public ResponseEntity<?> listartodos() {
        List<tipoenvio> lista = tipoenvioservice.listartodos();
        if (!lista.isEmpty()) return new ResponseEntity<>(lista, HttpStatus.OK);
        return new ResponseEntity<>("sin registros", HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Buscar tipo de envío por ID")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Encontrado"),
                    @ApiResponse(responseCode = "404", description = "No encontrado") })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarporid(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(tipoenvioservice.buscarporid(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Crear tipo de envío")
    @ApiResponses({ @ApiResponse(responseCode = "201", description = "Creado"),
                    @ApiResponse(responseCode = "400", description = "Datos invalidos") })
    @PostMapping
    public ResponseEntity<?> guardartipoenvio(@Valid @RequestBody tipoenvio objeto) {
        try {
            return new ResponseEntity<>(tipoenvioservice.guardartipoenvio(objeto), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("error en los datos", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Actualizar tipo de envío")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Actualizado"),
                    @ApiResponse(responseCode = "404", description = "No encontrado") })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizartipoenvio(@PathVariable Integer id, @Valid @RequestBody tipoenvio objeto) {
        try {
            return new ResponseEntity<>(tipoenvioservice.actualizartipoenvio(id, objeto), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("id no existe", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Eliminar tipo de envío")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Eliminado"),
                    @ApiResponse(responseCode = "404", description = "No encontrado") })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminartipoenvio(@PathVariable Integer id) {
        try {
            tipoenvioservice.eliminartipoenvio(id);
            return new ResponseEntity<>("envio eliminado", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("id no existe", HttpStatus.NOT_FOUND);
        }
    }
}
