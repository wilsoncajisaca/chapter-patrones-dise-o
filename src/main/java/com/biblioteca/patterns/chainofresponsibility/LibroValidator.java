package com.biblioteca.patterns.chainofresponsibility;

import com.biblioteca.model.entities.Libro;

/**
 * Interfaz para el patrón Chain of Responsibility
 * Define el contrato para validadores de libros
 */
public interface LibroValidator {
    
    /**
     * Establece el siguiente validador en la cadena
     * @param nextValidator el siguiente validador
     */
    void setNext(LibroValidator nextValidator);
    
    /**
     * Valida el libro según las reglas del validador
     * @param libro el libro a validar
     * @return resultado de la validación
     */
    ValidationResult validar(Libro libro);
}