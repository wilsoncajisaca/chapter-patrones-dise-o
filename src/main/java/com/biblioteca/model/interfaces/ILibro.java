package com.biblioteca.model.interfaces;

import com.biblioteca.model.enums.EstadoLibro;
import com.biblioteca.model.enums.FormatoLibro;
import com.biblioteca.model.enums.TipoLibro;

/**
 * Interfaz que define el contrato para todos los libros del sistema
 * Implementa principios SOLID - Interface Segregation Principle
 */
public interface ILibro {
    
    Long getId();
    String getTitulo();
    String getAutor();
    TipoLibro getTipo();
    FormatoLibro getFormato();
    EstadoLibro getEstado();
    
    void setEstado(EstadoLibro estado);
    
    /**
     * Método para obtener información resumida del libro
     * @return String con información del libro
     */
    String getInformacion();
    
    /**
     * Método para determinar si el libro está disponible para préstamo
     * @return true si está disponible, false en caso contrario
     */
    boolean estaDisponible();
}