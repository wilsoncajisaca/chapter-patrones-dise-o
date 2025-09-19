package com.biblioteca.patterns.observer;

import com.biblioteca.model.entities.Libro;

import java.util.List;

/**
 * Interfaz Subject para el patrón Observer
 * Define el contrato para sujetos observables
 */
public interface LibroSubject {
    
    /**
     * Agrega un observador a la lista de observadores
     * @param observer el observador a agregar
     */
    void agregarObservador(LibroObserver observer);
    
    /**
     * Elimina un observador de la lista de observadores
     * @param observer el observador a eliminar
     */
    void eliminarObservador(LibroObserver observer);
    
    /**
     * Notifica a todos los observadores sobre un cambio de estado
     * @param libro el libro que cambió
     * @param estadoAnterior el estado anterior
     * @param estadoNuevo el nuevo estado
     */
    void notificarCambioEstado(Libro libro, String estadoAnterior, String estadoNuevo);
    
    /**
     * Notifica a todos los observadores sobre un libro agregado
     * @param libro el libro que fue agregado
     */
    void notificarLibroAgregado(Libro libro);
    
    /**
     * Obtiene la lista de observadores
     * @return lista de observadores registrados
     */
    List<LibroObserver> getObservadores();
}