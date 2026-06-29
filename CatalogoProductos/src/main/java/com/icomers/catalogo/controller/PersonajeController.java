package com.icomers.catalogo.controller;

import com.icomers.catalogo.model.Personaje;
import com.icomers.catalogo.service.PersonajeService;
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
@RequestMapping("/api/v1/personajes")
@Tag(name = "Personajes", description = "Gestion de personajes de figuras de coleccion")
public class PersonajeController {

    @Autowired
    private PersonajeService personajeService;

    @Operation(summary = "Listar todos los personajes")
    @ApiResponse(responseCode = "200", description = "OK")
    @GetMapping
    public List<Personaje> obtenerTodos() {
        return personajeService.listarTodos();
    }

    @Operation(summary = "Buscar personaje por nombre")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Encontrado"),
                    @ApiResponse(responseCode = "404", description = "No encontrado") })
    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<Personaje> buscarPorNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(personajeService.buscarPorNombre(nombre));
    }

    @Operation(summary = "Crear personaje")
    @ApiResponse(responseCode = "200", description = "Creado")
    @PostMapping
    public Personaje guardar(@RequestBody Personaje personaje) {
        return personajeService.guardar(personaje);
    }

    @Operation(summary = "Actualizar personaje")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Actualizado"),
                    @ApiResponse(responseCode = "404", description = "No encontrado") })
    @PutMapping("/{id}")
    public ResponseEntity<Personaje> actualizar(@PathVariable Long id, @RequestBody Personaje datos) {
        return ResponseEntity.ok(personajeService.actualizar(id, datos));
    }

    @Operation(summary = "Eliminar personaje")
    @ApiResponse(responseCode = "204", description = "Eliminado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        personajeService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
