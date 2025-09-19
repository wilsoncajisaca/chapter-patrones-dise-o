package com.biblioteca.model.enums;

/**
 * Enumeración que define los estados posibles de un libro
 */
public enum EstadoLibro {
    DISPONIBLE("Disponible"),
    PRESTADO("Prestado");
    
    private final String descripcion;
    
    EstadoLibro(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
}