package com.biblioteca.exception;

import com.biblioteca.patterns.chainofresponsibility.ValidationResult;

/**
 * Excepción lanzada cuando falla la validación de un libro
 */
public class ValidationException extends RuntimeException {
    
    private final ValidationResult validationResult;
    
    public ValidationException(String mensaje, ValidationResult validationResult) {
        super(mensaje);
        this.validationResult = validationResult;
    }
    
    public ValidationException(ValidationResult validationResult) {
        super("Error de validación: " + validationResult.getResumen());
        this.validationResult = validationResult;
    }
    
    public ValidationResult getValidationResult() {
        return validationResult;
    }
}