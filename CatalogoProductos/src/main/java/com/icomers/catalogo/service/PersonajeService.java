package com.icomers.catalogo.service;

import com.icomers.catalogo.model.Personaje;
import com.icomers.catalogo.repository.PersonajeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PersonajeService {

    @Autowired
    private PersonajeRepository personajeRepository;

    public List<Personaje> listarTodos() {
        log.info("SERVICE: Listando todos los personajes");
        return personajeRepository.findAll();
    }

    public Personaje buscarPorNombre(String nombre) {
        log.info("SERVICE: Buscando personaje por nombre: {}", nombre);
        return personajeRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("No se encontró el personaje: " + nombre));
    }

    public Personaje guardar(Personaje personaje) {
        log.info("SERVICE: Guardando personaje: {}", personaje.getNombre());
        return personajeRepository.save(personaje);
    }

    public Personaje actualizar(Long id, Personaje datos) {
        log.info("SERVICE: Actualizando personaje ID: {}", id);
        Personaje existente = personajeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Personaje no encontrado con ID: " + id));
        existente.setNombre(datos.getNombre());
        if (datos.getFranquicia() != null) existente.setFranquicia(datos.getFranquicia());
        return personajeRepository.save(existente);
    }

    public void eliminar(Long id) {
        log.info("SERVICE: Eliminando personaje ID: {}", id);
        personajeRepository.deleteById(id);
    }
}
