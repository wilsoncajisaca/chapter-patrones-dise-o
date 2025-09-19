package com.biblioteca.patterns.abstractfactory;

import com.biblioteca.model.entities.Libro;

/**
 * Interfaz para el patrón Abstract Factory
 * Define el contrato para crear familias de libros según su formato
 */
public interface AbstractLibroFactory {

    /**
     * Crea un libro de ficción en el formato específico
     *
     * @param titulo el título del libro
     * @param autor  el autor del libro
     * @return nueva instancia de Libro de ficción
     */
    Libro crearLibroFiccion(String titulo, String autor);

    /**
     * Crea un libro de no ficción en el formato específico
     *
     * @param titulo el título del libro
     * @param autor  el autor del libro
     * @return nueva instancia de Libro de no ficción
     */
    Libro crearLibroNoFiccion(String titulo, String autor);


}