package com.icomers.catalogo.DTO;

import com.icomers.catalogo.model.Categoria;
import lombok.Data;

@Data
public class FigurasDTO {
    private Integer idFigura;
    private String nombre;
    private Double precio;
    private Categoria categoria;
    private String descripcion;
    private Integer stock;
}
