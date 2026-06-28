package com.icomers.ventas.controller;

import com.icomers.ventas.model.tipopago;
import com.icomers.ventas.service.tipopagoservice;
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
@RequestMapping("/api/v1/tipopago")
@Tag(name = "Tipos de Pago", description = "Gestion de métodos de pago")
public class tipopagocontroller {

    @Autowired
    private tipopagoservice tipopagoservice;

    @Operation(summary = "Listar tipos de pago")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "204", description = "Sin registros") })
    @GetMapping
    public ResponseEntity<?> listartodos() {
        List<tipopago> lista = tipopagoservice.listartodos();
        if (!lista.isEmpty()) return new ResponseEntity<>(lista, HttpStatus.OK);
        return new ResponseEntity<>("sin registros", HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Buscar tipo de pago por ID")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Encontrado"),
                    @ApiResponse(responseCode = "404", description = "No encontrado") })
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarporid(@PathVariable Integer id) {
        try {
            return new ResponseEntity<>(tipopagoservice.buscarporid(id), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Crear tipo de pago")
    @ApiResponses({ @ApiResponse(responseCode = "201", description = "Creado"),
                    @ApiResponse(responseCode = "400", description = "Datos invalidos") })
    @PostMapping
    public ResponseEntity<?> guardartipopago(@Valid @RequestBody tipopago objeto) {
        try {
            return new ResponseEntity<>(tipopagoservice.guardartipopago(objeto), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("error en los datos", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Actualizar tipo de pago")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Actualizado"),
                    @ApiResponse(responseCode = "404", description = "No encontrado") })
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizartipopago(@PathVariable Integer id, @Valid @RequestBody tipopago objeto) {
        try {
            return new ResponseEntity<>(tipopagoservice.actualizartipopago(id, objeto), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("id no existe", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Eliminar tipo de pago")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Eliminado"),
                    @ApiResponse(responseCode = "404", description = "No encontrado") })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminartipopago(@PathVariable Integer id) {
        try {
            tipopagoservice.eliminartipopago(id);
            return new ResponseEntity<>("pago eliminado", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("id no existe", HttpStatus.NOT_FOUND);
        }
    }
}
