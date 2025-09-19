package com.biblioteca.patterns.observer.impl;

import com.biblioteca.model.entities.Libro;
import com.biblioteca.patterns.observer.LibroObserver;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Implementación concreta del Observer para estadísticas
 * Se encarga de recopilar y mostrar estadísticas de la biblioteca
 */
@Component
public class EstadisticasObserver implements LibroObserver {
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private int totalLibrosAgregados = 0;
    private int totalPrestamosCuandoRealizados = 0;
    private int totalDevoluciones = 0;
    
    @Override
    public void onLibroEstadoCambiado(Libro libro, String estadoAnterior, String estadoNuevo) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        
        if ("PRESTADO".equals(estadoNuevo)) {
            totalPrestamosCuandoRealizados++;
            System.out.println(String.format(
                "[%s] 📊 ESTADÍSTICA: Total de préstamos realizados: %d",
                timestamp, totalPrestamosCuandoRealizados
            ));
        } else if ("DISPONIBLE".equals(estadoNuevo) && "PRESTADO".equals(estadoAnterior)) {
            totalDevoluciones++;
            System.out.println(String.format(
                "[%s] 📊 ESTADÍSTICA: Total de devoluciones: %d",
                timestamp, totalDevoluciones
            ));
        }
    }
    
    @Override
    public void onLibroAgregado(Libro libro) {
        totalLibrosAgregados++;
        String timestamp = LocalDateTime.now().format(FORMATTER);
        System.out.println(String.format(
            "[%s] 📊 ESTADÍSTICA: Total de libros en biblioteca: %d",
            timestamp, totalLibrosAgregados
        ));
    }
    
    @Override
    public String getNombreObservador() {
        return "Observer de Estadísticas";
    }
    
    // Métodos adicionales para obtener estadísticas
    public int getTotalLibrosAgregados() {
        return totalLibrosAgregados;
    }
    
    public int getTotalPrestamos() {
        return totalPrestamosCuandoRealizados;
    }
    
    public int getTotalDevoluciones() {
        return totalDevoluciones;
    }
    
    public void mostrarResumenEstadisticas() {
        System.out.println("\n=== RESUMEN DE ESTADÍSTICAS ===");
        System.out.println("📚 Total de libros agregados: " + totalLibrosAgregados);
        System.out.println("📤 Total de préstamos: " + totalPrestamosCuandoRealizados);
        System.out.println("📥 Total de devoluciones: " + totalDevoluciones);
        System.out.println("📊 Libros actualmente prestados: " + (totalPrestamosCuandoRealizados - totalDevoluciones));
        System.out.println("================================\n");
    }
}