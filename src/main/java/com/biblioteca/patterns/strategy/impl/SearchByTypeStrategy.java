package com.biblioteca.patterns.strategy.impl;

import com.biblioteca.model.entities.Libro;
import com.biblioteca.model.enums.TipoLibro;
import com.biblioteca.patterns.strategy.SearchStrategy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación de Strategy para búsqueda por tipo de libro
 * Permite filtrar por Ficción o No Ficción
 */
@Component
public class SearchByTypeStrategy implements SearchStrategy {
    
    @Override
    public List<Libro> buscar(List<Libro> libros, String criterio) {
        if (criterio == null || criterio.trim().isEmpty()) {
            return List.of();
        }
        
        String criterioBusqueda = criterio.trim().toLowerCase();
        TipoLibro tipoABuscar = null;
        
        // Determinar el tipo según el criterio
        if (criterioBusqueda.contains("ficcion") || criterioBusqueda.contains("ficción")) {
            tipoABuscar = TipoLibro.FICCION;
        } else if (criterioBusqueda.contains("no ficcion") || criterioBusqueda.contains("no ficción")) {
            tipoABuscar = TipoLibro.NO_FICCION;
        }
        
        if (tipoABuscar == null) {
            return List.of();
        }
        
        final TipoLibro tipoFinal = tipoABuscar;
        return libros.stream()
                .filter(libro -> libro.getTipo() == tipoFinal)
                .collect(Collectors.toList());
    }
    
    @Override
    public String getNombreEstrategia() {
        return "Búsqueda por Tipo";
    }
}