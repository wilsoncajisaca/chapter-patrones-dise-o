package com.biblioteca.patterns.abstractfactory.impl;

import com.biblioteca.model.builders.LibroBuilder;
import com.biblioteca.model.entities.Libro;
import com.biblioteca.model.enums.FormatoLibro;
import com.biblioteca.patterns.abstractfactory.AbstractLibroFactory;
import org.springframework.stereotype.Component;

/**
 * Implementación del Abstract Factory para crear libros físicos
 * Crea familias de libros en formato físico
 */
@Component
public class LibroFisicoFactory implements AbstractLibroFactory {
    
    @Override
    public Libro crearLibroFiccion(String titulo, String autor) {
        return LibroBuilder.nuevo()
                .conTitulo(titulo)
                .conAutor(autor)
                .ficcion()
                .fisico()
                .construir();
    }
    
    @Override
    public Libro crearLibroNoFiccion(String titulo, String autor) {
        return LibroBuilder.nuevo()
                .conTitulo(titulo)
                .conAutor(autor)
                .noFiccion()
                .fisico()
                .construir();
    }


}