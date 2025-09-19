package com.biblioteca.patterns.strategy.impl;

import com.biblioteca.model.entities.Libro;
import com.biblioteca.patterns.strategy.SearchStrategy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación de Strategy para búsqueda por título
 * Utiliza streams de Java para filtrado eficiente
 */
@Component
public class SearchByTitleStrategy implements SearchStrategy {
    
    @Override
    public List<Libro> buscar(List<Libro> libros, String criterio) {
        if (criterio == null || criterio.trim().isEmpty()) {
            return List.of();
        }
        
        String criterioBusqueda = criterio.trim().toLowerCase();
        
        return libros.stream()
                .filter(libro -> libro.getTitulo() != null)
                .filter(libro -> libro.getTitulo().toLowerCase().contains(criterioBusqueda))
                .collect(Collectors.toList());
    }
    
    @Override
    public String getNombreEstrategia() {
        return "Búsqueda por Título";
    }
}