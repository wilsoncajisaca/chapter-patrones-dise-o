package com.biblioteca.patterns.observer.impl;

import com.biblioteca.model.entities.Libro;
import com.biblioteca.patterns.observer.LibroObserver;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Implementación concreta del Observer para notificaciones de préstamos
 * Se encarga de notificar cuando se realizan préstamos de libros
 */
@Component
public class PrestamoObserver implements LibroObserver {
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    
    @Override
    public void onLibroEstadoCambiado(Libro libro, String estadoAnterior, String estadoNuevo) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        
        if ("PRESTADO".equals(estadoNuevo)) {
            System.out.println(String.format(
                "[%s] 📚 PRÉSTAMO REALIZADO: El libro '%s' de %s ha sido prestado.",
                timestamp, libro.getTitulo(), libro.getAutor()
            ));
        } else if ("DISPONIBLE".equals(estadoNuevo) && "PRESTADO".equals(estadoAnterior)) {
            System.out.println(String.format(
                "[%s] 📖 DEVOLUCIÓN COMPLETADA: El libro '%s' de %s ha sido devuelto y está disponible.",
                timestamp, libro.getTitulo(), libro.getAutor()
            ));
        }
    }
    
    @Override
    public void onLibroAgregado(Libro libro) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        System.out.println(String.format(
            "[%s] ➕ NUEVO LIBRO: Se agregó '%s' de %s (%s - %s) a la biblioteca.",
            timestamp, libro.getTitulo(), libro.getAutor(), 
            libro.getTipo().getDescripcion(), libro.getFormato().getDescripcion()
        ));
    }
    
    @Override
    public String getNombreObservador() {
        return "Observer de Préstamos";
    }
}