package com.biblioteca.model.enums;

/**
 * Enumeración que define los formatos de libros disponibles
 */
public enum FormatoLibro {
    FISICO("Físico"),
    DIGITAL("Digital");
    
    private final String descripcion;
    
    FormatoLibro(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
}