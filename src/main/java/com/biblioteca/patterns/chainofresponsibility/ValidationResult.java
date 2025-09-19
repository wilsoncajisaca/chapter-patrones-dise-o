package com.biblioteca.patterns.chainofresponsibility;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que encapsula el resultado de una validación
 * Implementa el principio de Clean Code para resultados claros
 */
public class ValidationResult {
    
    private final boolean valido;
    private final List<String> errores;
    private final List<String> advertencias;
    
    private ValidationResult(boolean valido, List<String> errores, List<String> advertencias) {
        this.valido = valido;
        this.errores = new ArrayList<>(errores);
        this.advertencias = new ArrayList<>(advertencias);
    }
    
    /**
     * Crea un resultado de validación exitosa
     * @return ValidationResult válido
     */
    public static ValidationResult exito() {
        return new ValidationResult(true, List.of(), List.of());
    }
    
    /**
     * Crea un resultado de validación exitosa con advertencias
     * @param advertencias lista de advertencias
     * @return ValidationResult válido con advertencias
     */
    public static ValidationResult exitoConAdvertencias(List<String> advertencias) {
        return new ValidationResult(true, List.of(), advertencias);
    }
    
    /**
     * Crea un resultado de validación fallida
     * @param errores lista de errores
     * @return ValidationResult inválido
     */
    public static ValidationResult error(List<String> errores) {
        return new ValidationResult(false, errores, List.of());
    }
    
    /**
     * Crea un resultado de validación fallida con un solo error
     * @param error mensaje de error
     * @return ValidationResult inválido
     */
    public static ValidationResult error(String error) {
        return new ValidationResult(false, List.of(error), List.of());
    }
    
    /**
     * Combina dos resultados de validación
     * @param otro otro resultado a combinar
     * @return nuevo resultado combinado
     */
    public ValidationResult combinar(ValidationResult otro) {
        List<String> todosErrores = new ArrayList<>(this.errores);
        todosErrores.addAll(otro.errores);
        
        List<String> todasAdvertencias = new ArrayList<>(this.advertencias);
        todasAdvertencias.addAll(otro.advertencias);
        
        boolean esValido = this.valido && otro.valido;
        
        return new ValidationResult(esValido, todosErrores, todasAdvertencias);
    }
    
    // Getters
    public boolean isValido() {
        return valido;
    }
    
    public List<String> getErrores() {
        return new ArrayList<>(errores);
    }
    
    public List<String> getAdvertencias() {
        return new ArrayList<>(advertencias);
    }
    
    public boolean tieneErrores() {
        return !errores.isEmpty();
    }
    
    public boolean tieneAdvertencias() {
        return !advertencias.isEmpty();
    }
    
    /**
     * Obtiene un resumen legible del resultado
     * @return String con el resumen de la validación
     */
    public String getResumen() {
        StringBuilder sb = new StringBuilder();
        
        if (valido) {
            sb.append("✓ Validación exitosa");
        } else {
            sb.append("✗ Validación fallida");
        }
        
        if (tieneErrores()) {
            sb.append("\nErrores:");
            errores.forEach(error -> sb.append("\n  - ").append(error));
        }
        
        if (tieneAdvertencias()) {
            sb.append("\nAdvertencias:");
            advertencias.forEach(advertencia -> sb.append("\n  - ").append(advertencia));
        }
        
        return sb.toString();
    }
    
    @Override
    public String toString() {
        return getResumen();
    }
}