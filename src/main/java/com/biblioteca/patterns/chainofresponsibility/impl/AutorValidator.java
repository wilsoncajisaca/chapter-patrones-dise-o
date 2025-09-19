package com.biblioteca.patterns.chainofresponsibility.impl;

import com.biblioteca.model.entities.Libro;
import com.biblioteca.patterns.chainofresponsibility.BaseLibroValidator;
import com.biblioteca.patterns.chainofresponsibility.ValidationResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Validador específico para el autor del libro
 * Implementa reglas de negocio para autores válidos
 */
@Component
public class AutorValidator extends BaseLibroValidator {
    
    private static final int AUTOR_MIN_LENGTH = 2;
    private static final int AUTOR_MAX_LENGTH = 100;
    private static final Pattern PATRON_NOMBRE_VALIDO = Pattern.compile("^[a-zA-ZÀ-ÿ\\s\\.\\-']+$");
    
    @Override
    protected ValidationResult validarEspecifico(Libro libro) {
        List<String> errores = new ArrayList<>();
        List<String> advertencias = new ArrayList<>();
        
        String autor = libro.getAutor();
        
        // Validación de nulo o vacío
        if (autor == null || autor.trim().isEmpty()) {
            errores.add("El autor no puede estar vacío");
            return ValidationResult.error(errores);
        }
        
        String autorLimpio = autor.trim();
        
        // Validación de longitud mínima
        if (autorLimpio.length() < AUTOR_MIN_LENGTH) {
            errores.add("El nombre del autor debe tener al menos " + AUTOR_MIN_LENGTH + " caracteres");
        }
        
        // Validación de longitud máxima
        if (autorLimpio.length() > AUTOR_MAX_LENGTH) {
            errores.add("El nombre del autor no puede exceder " + AUTOR_MAX_LENGTH + " caracteres");
        }
        
        // Validación de caracteres válidos (letras, espacios, puntos, guiones, apostrofes)
        if (!PATRON_NOMBRE_VALIDO.matcher(autorLimpio).matches()) {
            errores.add("El nombre del autor contiene caracteres no válidos. Use solo letras, espacios, puntos, guiones y apostrofes.");
        }
        
        // Validación de espacios múltiples
        if (autorLimpio.contains("  ")) {
            advertencias.add("El nombre del autor contiene espacios múltiples. Se recomienda usar espacios simples.");
        }
        
        // Validación de solo números
        if (autorLimpio.matches("^\\d+$")) {
            errores.add("El nombre del autor no puede ser solo números");
        }
        
        // Validación de formato básico (debe tener al menos una letra)
        if (!autorLimpio.matches(".*[a-zA-ZÀ-ÿ].*")) {
            errores.add("El nombre del autor debe contener al menos una letra");
        }
        
        // Advertencia para autores con una sola palabra
        if (!autorLimpio.contains(" ") && autorLimpio.length() > 2) {
            advertencias.add("El autor tiene una sola palabra. Considere agregar nombre y apellido.");
        }
        
        // Validación de mayúsculas/minúsculas apropiadas
        if (autorLimpio.equals(autorLimpio.toLowerCase())) {
            advertencias.add("El nombre del autor está en minúsculas. Considere usar formato de nombre propio.");
        } else if (autorLimpio.equals(autorLimpio.toUpperCase())) {
            advertencias.add("El nombre del autor está en mayúsculas. Considere usar formato de nombre propio.");
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
        return "Validador de Autor";
    }
}