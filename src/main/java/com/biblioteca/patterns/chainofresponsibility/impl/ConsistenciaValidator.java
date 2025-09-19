package com.biblioteca.patterns.chainofresponsibility.impl;

import com.biblioteca.model.entities.Libro;
import com.biblioteca.patterns.chainofresponsibility.BaseLibroValidator;
import com.biblioteca.patterns.chainofresponsibility.ValidationResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Validador para los campos obligatorios y la consistencia general del libro
 * Implementa validaciones de reglas de negocio específicas de la biblioteca
 */
@Component
public class ConsistenciaValidator extends BaseLibroValidator {
    
    @Override
    protected ValidationResult validarEspecifico(Libro libro) {
        List<String> errores = new ArrayList<>();
        List<String> advertencias = new ArrayList<>();
        
        // Validación de campos obligatorios no nulos
        if (libro.getTipo() == null) {
            errores.add("El tipo de libro es obligatorio");
        }
        
        if (libro.getFormato() == null) {
            errores.add("El formato del libro es obligatorio");
        }
        
        if (libro.getEstado() == null) {
            errores.add("El estado del libro es obligatorio");
        }
        
        // Validaciones de consistencia de negocio
        if (libro.getTitulo() != null && libro.getAutor() != null) {
            // Verificar que el título y autor no sean idénticos
            if (libro.getTitulo().trim().equalsIgnoreCase(libro.getAutor().trim())) {
                advertencias.add("El título y el autor son idénticos. Verifique que sea correcto.");
            }
        }
        
        // Validación de combinaciones lógicas
        if (libro.getTipo() != null && libro.getFormato() != null) {
            // Aquí se pueden agregar validaciones específicas según reglas de negocio
            // Por ejemplo: ciertos tipos de libros solo en ciertos formatos
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
        return "Validador de Consistencia";
    }
}