package com.biblioteca.patterns.strategy;

import com.biblioteca.model.entities.Libro;

import java.util.List;

/**
 * Interfaz para el patrón Strategy
 * Define diferentes estrategias de búsqueda de libros
 */
public interface SearchStrategy {
    
    /**
     * Busca libros según el criterio específico de la estrategia
     * @param libros lista de libros donde buscar
     * @param criterio criterio de búsqueda
     * @return lista de libros que coinciden con el criterio
     */
    List<Libro> buscar(List<Libro> libros, String criterio);
    
    /**
     * Obtiene el nombre de la estrategia
     * @return nombre descriptivo de la estrategia
     */
    String getNombreEstrategia();
}