package com.biblioteca.model.enums;

/**
 * Enumeración que define los tipos de libros disponibles
 */
public enum TipoLibro {
    FICCION("Ficción"),
    NO_FICCION("No Ficción");
    
    private final String descripcion;
    
    TipoLibro(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
}