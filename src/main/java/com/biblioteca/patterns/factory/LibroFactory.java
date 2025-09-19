package com.biblioteca.patterns.factory;

import com.biblioteca.model.entities.Libro;
import com.biblioteca.model.enums.TipoLibro;

/**
 * Interfaz para el patrón Factory Method
 * Define el contrato para crear libros según su tipo
 */
public interface LibroFactory {
    
    /**
     * Método factory para crear libros
     * @param titulo el título del libro
     * @param autor el autor del libro
     * @return nueva instancia de Libro
     */
    Libro crearLibro(String titulo, String autor);
    
    /**
     * Obtiene el tipo de libro que produce esta factory
     * @return el tipo de libro
     */
    TipoLibro getTipoLibro();
}