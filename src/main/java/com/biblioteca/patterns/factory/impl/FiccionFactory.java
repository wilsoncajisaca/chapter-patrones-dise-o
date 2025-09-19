package com.biblioteca.patterns.factory.impl;

import com.biblioteca.model.builders.LibroBuilder;
import com.biblioteca.model.entities.Libro;
import com.biblioteca.model.enums.FormatoLibro;
import com.biblioteca.model.enums.TipoLibro;
import com.biblioteca.patterns.factory.LibroFactory;
import org.springframework.stereotype.Component;

/**
 * Implementación del Factory Method para crear libros de ficción
 * Encapsula la lógica de creación de libros de ficción
 */
@Component
public class FiccionFactory implements LibroFactory {
    
    @Override
    public Libro crearLibro(String titulo, String autor) {
        return LibroBuilder.nuevo()
                .conTitulo(titulo)
                .conAutor(autor)
                .ficcion()
                .fisico() // Por defecto, los libros de ficción son físicos
                .construir();
    }
    
    /**
     * Crea un libro de ficción con formato específico
     * @param titulo el título del libro
     * @param autor el autor del libro
     * @param formato el formato del libro (físico/digital)
     * @return nueva instancia de Libro de ficción
     */
    public Libro crearLibroConFormato(String titulo, String autor, FormatoLibro formato) {
        return LibroBuilder.nuevo()
                .conTitulo(titulo)
                .conAutor(autor)
                .ficcion()
                .enFormato(formato)
                .construir();
    }
    
    @Override
    public TipoLibro getTipoLibro() {
        return TipoLibro.FICCION;
    }
}