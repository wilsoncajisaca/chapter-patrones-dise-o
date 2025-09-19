package com.biblioteca.patterns.observer;

import com.biblioteca.model.entities.Libro;

/**
 * Interfaz Observer para el patrón Observer
 * Define el contrato para observadores que reciben notificaciones
 */
public interface LibroObserver {
    
    /**
     * Método llamado cuando un libro cambia de estado
     * @param libro el libro que cambió
     * @param estadoAnterior el estado anterior del libro
     * @param estadoNuevo el nuevo estado del libro
     */
    void onLibroEstadoCambiado(Libro libro, String estadoAnterior, String estadoNuevo);
    
    /**
     * Método llamado cuando se agrega un nuevo libro
     * @param libro el libro que fue agregado
     */
    void onLibroAgregado(Libro libro);
    
    /**
     * Obtiene el nombre del observador
     * @return nombre descriptivo del observador
     */
    String getNombreObservador();
}