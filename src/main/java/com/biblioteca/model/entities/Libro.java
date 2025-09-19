package com.biblioteca.model.entities;

import com.biblioteca.model.enums.EstadoLibro;
import com.biblioteca.model.enums.FormatoLibro;
import com.biblioteca.model.enums.TipoLibro;
import com.biblioteca.model.interfaces.ILibro;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entidad JPA que representa un libro en el sistema de biblioteca
 * Implementa la interfaz ILibro y principios de Clean Code
 */
@Entity
@Table(name = "libros")
public class Libro implements ILibro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El título no puede estar vacío")
    @Column(nullable = false, length = 200)
    private String titulo;
    
    @NotBlank(message = "El autor no puede estar vacío")
    @Column(nullable = false, length = 100)
    private String autor;
    
    @NotNull(message = "El tipo de libro es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoLibro tipo;
    
    @NotNull(message = "El formato del libro es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FormatoLibro formato;
    
    @NotNull(message = "El estado del libro es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoLibro estado;
    
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    // Constructor por defecto requerido por JPA
    public Libro() {
        this.estado = EstadoLibro.DISPONIBLE;
        this.fechaCreacion = LocalDateTime.now();
    }
    
    // Constructor para el Builder pattern
    public Libro(String titulo, String autor, TipoLibro tipo, FormatoLibro formato) {
        this();
        this.titulo = titulo;
        this.autor = autor;
        this.tipo = tipo;
        this.formato = formato;
    }
    
    // Métodos de la interfaz ILibro
    @Override
    public Long getId() {
        return id;
    }
    
    @Override
    public String getTitulo() {
        return titulo;
    }
    
    @Override
    public String getAutor() {
        return autor;
    }
    
    @Override
    public TipoLibro getTipo() {
        return tipo;
    }
    
    @Override
    public FormatoLibro getFormato() {
        return formato;
    }
    
    @Override
    public EstadoLibro getEstado() {
        return estado;
    }
    
    @Override
    public void setEstado(EstadoLibro estado) {
        this.estado = estado;
        this.fechaActualizacion = LocalDateTime.now();
    }
    
    @Override
    public String getInformacion() {
        return String.format("Libro[ID=%d, Título='%s', Autor='%s', Tipo=%s, Formato=%s, Estado=%s]",
                id, titulo, autor, tipo.getDescripcion(), formato.getDescripcion(), estado.getDescripcion());
    }
    
    @Override
    public boolean estaDisponible() {
        return EstadoLibro.DISPONIBLE.equals(this.estado);
    }
    
    // Getters y Setters adicionales
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
        this.fechaActualizacion = LocalDateTime.now();
    }
    
    public void setAutor(String autor) {
        this.autor = autor;
        this.fechaActualizacion = LocalDateTime.now();
    }
    
    public void setTipo(TipoLibro tipo) {
        this.tipo = tipo;
        this.fechaActualizacion = LocalDateTime.now();
    }
    
    public void setFormato(FormatoLibro formato) {
        this.formato = formato;
        this.fechaActualizacion = LocalDateTime.now();
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }
    
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
    
    // Métodos JPA Lifecycle
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
    
    // Métodos equals, hashCode y toString para buenas prácticas
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Libro libro = (Libro) o;
        return Objects.equals(id, libro.id) &&
                Objects.equals(titulo, libro.titulo) &&
                Objects.equals(autor, libro.autor);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, titulo, autor);
    }
    
    @Override
    public String toString() {
        return getInformacion();
    }
}