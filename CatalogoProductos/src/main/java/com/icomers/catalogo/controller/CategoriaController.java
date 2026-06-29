package com.icomers.catalogo.controller;

import com.icomers.catalogo.model.Categoria;
import com.icomers.catalogo.service.CategoriaService;
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
@RequestMapping("/api/v1/categorias")
@Tag(name = "Categorias", description = "Gestion de categorias de productos")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Operation(summary = "Listar todas las categorias")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "204", description = "Sin registros") })
    @GetMapping
    public ResponseEntity<List<Categoria>> obtenerTodos() {
        List<Categoria> categorias = categoriaService.obtenerTodos();
        if (categorias.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }

    @Operation(summary = "Crear categoria")
    @ApiResponses({ @ApiResponse(responseCode = "201", description = "Creada"),
                    @ApiResponse(responseCode = "400", description = "Datos invalidos") })
    @PostMapping
    public ResponseEntity<Categoria> agregarCategoria(@RequestBody Categoria categoria) {
        try {
            return new ResponseEntity<>(categoriaService.guardarCategoria(categoria), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Actualizar categoria")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Actualizada"),
                    @ApiResponse(responseCode = "404", description = "No encontrada") })
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizarCategoria(@PathVariable Integer id, @RequestBody Categoria categoria) {
        try {
            return new ResponseEntity<>(categoriaService.actualizarCategoria(id, categoria), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Eliminar categoria")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Eliminada"),
                    @ApiResponse(responseCode = "404", description = "No encontrada") })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Integer id) {
        String resultado = categoriaService.eliminarPorId(id);
        if (resultado.contains("exitosamente")) return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
