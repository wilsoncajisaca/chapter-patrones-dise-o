package com.biblioteca.patterns.decorator.impl;

import com.biblioteca.model.enums.EstadoLibro;
import com.biblioteca.model.interfaces.ILibro;
import com.biblioteca.patterns.decorator.LibroDecorator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Decorator concreto que añade funcionalidad de préstamo a los libros
 * Extiende la funcionalidad básica sin modificar la clase original
 * 
 * Note: No es un componente Spring, se usa manualmente cuando sea necesario
 */
public class PrestamoDecorator extends LibroDecorator {
    
    private LocalDateTime fechaPrestamo;
    private LocalDateTime fechaDevolucionEsperada;
    private String prestatario;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    public PrestamoDecorator(ILibro libroBase) {
        super(libroBase);
    }
    
    /**
     * Realiza el préstamo del libro
     * @param prestatario nombre de la persona que toma prestado el libro
     * @param diasPrestamo número de días del préstamo
     * @return true si el préstamo fue exitoso, false si el libro no está disponible
     */
    public boolean prestar(String prestatario, int diasPrestamo) {
        if (!libroBase.estaDisponible()) {
            return false;
        }
        
        this.prestatario = prestatario;
        this.fechaPrestamo = LocalDateTime.now();
        this.fechaDevolucionEsperada = fechaPrestamo.plusDays(diasPrestamo);
        
        libroBase.setEstado(EstadoLibro.PRESTADO);
        return true;
    }
    
    /**
     * Realiza la devolución del libro
     * @return true si la devolución fue exitosa
     */
    public boolean devolver() {
        if (libroBase.getEstado() != EstadoLibro.PRESTADO) {
            return false;
        }
        
        libroBase.setEstado(EstadoLibro.DISPONIBLE);
        this.prestatario = null;
        this.fechaPrestamo = null;
        this.fechaDevolucionEsperada = null;
        
        return true;
    }
    
    /**
     * Verifica si el libro está atrasado en su devolución
     * @return true si el libro está atrasado
     */
    public boolean estaAtrasado() {
        return fechaDevolucionEsperada != null && 
               LocalDateTime.now().isAfter(fechaDevolucionEsperada) &&
               libroBase.getEstado() == EstadoLibro.PRESTADO;
    }
    
    /**
     * Calcula los días de atraso
     * @return número de días de atraso, 0 si no está atrasado
     */
    public long getDiasAtraso() {
        if (!estaAtrasado()) {
            return 0;
        }
        return java.time.Duration.between(fechaDevolucionEsperada, LocalDateTime.now()).toDays();
    }
    
    @Override
    public String getInformacion() {
        String infoBase = libroBase.getInformacion();
        
        if (libroBase.getEstado() == EstadoLibro.PRESTADO && fechaPrestamo != null) {
            String estadoPrestamo = estaAtrasado() ? 
                String.format(" [ATRASADO %d días]", getDiasAtraso()) : " [EN PRÉSTAMO]";
            
            return infoBase + String.format(
                " | Prestado a: %s | Fecha préstamo: %s | Devolución esperada: %s%s",
                prestatario,
                fechaPrestamo.format(FORMATTER),
                fechaDevolucionEsperada.format(FORMATTER),
                estadoPrestamo
            );
        }
        
        return infoBase;
    }
    
    // Getters para la información del préstamo
    public LocalDateTime getFechaPrestamo() {
        return fechaPrestamo;
    }
    
    public LocalDateTime getFechaDevolucionEsperada() {
        return fechaDevolucionEsperada;
    }
    
    public String getPrestatario() {
        return prestatario;
    }
    
    /**
     * Método de conveniencia para crear un PrestamoDecorator
     * @param libro el libro a decorar
     * @return nuevo PrestamoDecorator
     */
    public static PrestamoDecorator decorar(ILibro libro) {
        return new PrestamoDecorator(libro);
    }
}