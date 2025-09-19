package com.biblioteca.exception;

/**
 * Excepción lanzada cuando se intenta una operación inválida con un libro
 */
public class OperacionInvalidaException extends RuntimeException {
    
    public OperacionInvalidaException(String mensaje) {
        super(mensaje);
    }
    
    public OperacionInvalidaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}