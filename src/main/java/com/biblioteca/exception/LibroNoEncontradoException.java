package com.biblioteca.exception;

/**
 * Excepción lanzada cuando no se encuentra un libro
 */
public class LibroNoEncontradoException extends RuntimeException {
    
    public LibroNoEncontradoException(String mensaje) {
        super(mensaje);
    }
    
    public LibroNoEncontradoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
    
    public LibroNoEncontradoException(Long id) {
        super("No se encontró el libro con ID: " + id);
    }
}