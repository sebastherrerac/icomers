package com.icomers.logistica.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comunas")
public class comuna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idcomuna;

    @NotBlank(message = "El nombre de la comuna no puede estar vacio")
    @Column(nullable = false, length = 50)
    private String nombreComuna;

    @Column(nullable = false)
    private Boolean activo;

    @NotNull(message = "Debe especificar la region")
    @Column(nullable = false)
    private Integer regionid;
}
