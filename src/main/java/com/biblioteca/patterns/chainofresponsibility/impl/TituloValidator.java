package com.biblioteca.patterns.chainofresponsibility.impl;

import com.biblioteca.model.entities.Libro;
import com.biblioteca.patterns.chainofresponsibility.BaseLibroValidator;
import com.biblioteca.patterns.chainofresponsibility.ValidationResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Validador específico para el título del libro
 * Implementa reglas de negocio para títulos válidos
 */
@Component
public class TituloValidator extends BaseLibroValidator {
    
    private static final int TITULO_MIN_LENGTH = 1;
    private static final int TITULO_MAX_LENGTH = 200;
    private static final int TITULO_ADVERTENCIA_LENGTH = 100;
    
    @Override
    protected ValidationResult validarEspecifico(Libro libro) {
        List<String> errores = new ArrayList<>();
        List<String> advertencias = new ArrayList<>();
        
        String titulo = libro.getTitulo();
        
        // Validación de nulo o vacío
        if (titulo == null || titulo.trim().isEmpty()) {
            errores.add("El título no puede estar vacío");
            return ValidationResult.error(errores);
        }
        
        String tituloLimpio = titulo.trim();
        
        // Validación de longitud mínima
        if (tituloLimpio.length() < TITULO_MIN_LENGTH) {
            errores.add("El título debe tener al menos " + TITULO_MIN_LENGTH + " carácter");
        }
        
        // Validación de longitud máxima
        if (tituloLimpio.length() > TITULO_MAX_LENGTH) {
            errores.add("El título no puede exceder " + TITULO_MAX_LENGTH + " caracteres");
        }
        
        // Advertencia para títulos muy largos
        if (tituloLimpio.length() > TITULO_ADVERTENCIA_LENGTH) {
            advertencias.add("El título es muy largo (" + tituloLimpio.length() + " caracteres). Considere acortarlo.");
        }
        
        // Validación de caracteres especiales problemáticos
        if (tituloLimpio.matches(".*[<>\"'&].*")) {
            errores.add("El título contiene caracteres no permitidos (<, >, \", ', &)");
        }
        
        // Validación de solo números
        if (tituloLimpio.matches("^\\d+$")) {
            advertencias.add("El título contiene solo números. Considere agregar texto descriptivo.");
        }
        
        // Validación de mayúsculas excesivas
        if (tituloLimpio.equals(tituloLimpio.toUpperCase()) && tituloLimpio.length() > 10) {
            advertencias.add("El título está completamente en mayúsculas. Considere usar formato de título apropiado.");
        }
        
        if (!errores.isEmpty()) {
            return ValidationResult.error(errores);
        }
        
        if (!advertencias.isEmpty()) {
            return ValidationResult.exitoConAdvertencias(advertencias);
        }
        
        return ValidationResult.exito();
    }
    
    @Override
    public String getNombreValidador() {
        return "Validador de Título";
    }
}