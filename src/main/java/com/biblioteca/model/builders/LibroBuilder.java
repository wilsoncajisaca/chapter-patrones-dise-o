package com.biblioteca.model.builders;

import com.biblioteca.model.entities.Libro;
import com.biblioteca.model.enums.EstadoLibro;
import com.biblioteca.model.enums.FormatoLibro;
import com.biblioteca.model.enums.TipoLibro;

/**
 * Implementación del patrón Builder para la construcción flexible de objetos Libro
 * Permite crear libros de manera fluida y legible, aplicando Clean Code
 */
public class LibroBuilder {
    
    private String titulo;
    private String autor;
    private TipoLibro tipo;
    private FormatoLibro formato;
    private EstadoLibro estado = EstadoLibro.DISPONIBLE; // Valor por defecto
    
    /**
     * Constructor privado para forzar el uso del método estático
     */
    private LibroBuilder() {}
    
    /**
     * Método estático para iniciar la construcción de un libro
     * @return nueva instancia del builder
     */
    public static LibroBuilder nuevo() {
        return new LibroBuilder();
    }
    
    /**
     * Establece el título del libro
     * @param titulo el título del libro
     * @return el builder para permitir method chaining
     */
    public LibroBuilder conTitulo(String titulo) {
        this.titulo = titulo;
        return this;
    }
    
    /**
     * Establece el autor del libro
     * @param autor el autor del libro
     * @return el builder para permitir method chaining
     */
    public LibroBuilder conAutor(String autor) {
        this.autor = autor;
        return this;
    }
    
    /**
     * Establece el tipo del libro (Ficción/No Ficción)
     * @param tipo el tipo del libro
     * @return el builder para permitir method chaining
     */
    public LibroBuilder deTipo(TipoLibro tipo) {
        this.tipo = tipo;
        return this;
    }
    
    /**
     * Establece el formato del libro (Físico/Digital)
     * @param formato el formato del libro
     * @return el builder para permitir method chaining
     */
    public LibroBuilder enFormato(FormatoLibro formato) {
        this.formato = formato;
        return this;
    }
    
    /**
     * Establece el estado del libro (Disponible/Prestado)
     * @param estado el estado del libro
     * @return el builder para permitir method chaining
     */
    public LibroBuilder conEstado(EstadoLibro estado) {
        this.estado = estado;
        return this;
    }
    
    /**
     * Método de conveniencia para crear un libro de ficción
     * @return el builder configurado para ficción
     */
    public LibroBuilder ficcion() {
        this.tipo = TipoLibro.FICCION;
        return this;
    }
    
    /**
     * Método de conveniencia para crear un libro de no ficción
     * @return el builder configurado para no ficción
     */
    public LibroBuilder noFiccion() {
        this.tipo = TipoLibro.NO_FICCION;
        return this;
    }
    
    /**
     * Método de conveniencia para crear un libro físico
     * @return el builder configurado para formato físico
     */
    public LibroBuilder fisico() {
        this.formato = FormatoLibro.FISICO;
        return this;
    }
    
    /**
     * Método de conveniencia para crear un libro digital
     * @return el builder configurado para formato digital
     */
    public LibroBuilder digital() {
        this.formato = FormatoLibro.DIGITAL;
        return this;
    }
    
    /**
     * Valida que todos los campos obligatorios estén presentes
     * @throws IllegalStateException si faltan campos obligatorios
     */
    private void validar() {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalStateException("El título es obligatorio");
        }
        if (autor == null || autor.trim().isEmpty()) {
            throw new IllegalStateException("El autor es obligatorio");
        }
        if (tipo == null) {
            throw new IllegalStateException("El tipo de libro es obligatorio");
        }
        if (formato == null) {
            throw new IllegalStateException("El formato del libro es obligatorio");
        }
    }
    
    /**
     * Construye el objeto Libro con los parámetros establecidos
     * @return nueva instancia de Libro
     * @throws IllegalStateException si faltan campos obligatorios
     */
    public Libro construir() {
        validar();
        
        Libro libro = new Libro(titulo.trim(), autor.trim(), tipo, formato);
        if (estado != null) {
            libro.setEstado(estado);
        }
        
        return libro;
    }
    
    /**
     * Método de conveniencia que combina construir y retornar
     * Alias para construir() para mayor legibilidad
     * @return nueva instancia de Libro
     */
    public Libro build() {
        return construir();
    }
}