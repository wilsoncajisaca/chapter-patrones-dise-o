package com.biblioteca.patterns.chainofresponsibility;

import com.biblioteca.model.entities.Libro;

/**
 * Clase base abstracta para el patrón Chain of Responsibility
 * Implementa la lógica común para el encadenamiento de validadores
 */
public abstract class BaseLibroValidator implements LibroValidator {
    
    protected LibroValidator nextValidator;
    
    @Override
    public void setNext(LibroValidator nextValidator) {
        this.nextValidator = nextValidator;
    }
    
    @Override
    public ValidationResult validar(Libro libro) {
        // Ejecuta la validación específica de esta clase
        ValidationResult resultado = validarEspecifico(libro);
        
        // Si hay un siguiente validador y la validación actual es exitosa o permite continuar
        if (nextValidator != null && deberiacontinuar(resultado)) {
            ValidationResult siguienteResultado = nextValidator.validar(libro);
            resultado = resultado.combinar(siguienteResultado);
        }
        
        return resultado;
    }
    
    /**
     * Validación específica que debe implementar cada validador
     * @param libro el libro a validar
     * @return resultado de la validación específica
     */
    protected abstract ValidationResult validarEspecifico(Libro libro);
    
    /**
     * Determina si se debe continuar con el siguiente validador
     * Por defecto continúa siempre, pero puede ser sobrescrito
     * @param resultado resultado de la validación actual
     * @return true si debe continuar, false en caso contrario
     */
    protected boolean deberiacontinuar(ValidationResult resultado) {
        return true; // Por defecto continúa siempre para recopilar todos los errores
    }
    
    /**
     * Obtiene el nombre del validador para logging y debugging
     * @return nombre descriptivo del validador
     */
    public abstract String getNombreValidador();
}