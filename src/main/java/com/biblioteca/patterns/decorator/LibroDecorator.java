package com.biblioteca.patterns.decorator;

import com.biblioteca.model.enums.EstadoLibro;
import com.biblioteca.model.enums.FormatoLibro;
import com.biblioteca.model.enums.TipoLibro;
import com.biblioteca.model.interfaces.ILibro;

/**
 * Clase base abstracta para el patrón Decorator
 * Implementa la interfaz ILibro y contiene una referencia al libro base
 */
public abstract class LibroDecorator implements ILibro {
    
    protected ILibro libroBase;
    
    public LibroDecorator(ILibro libroBase) {
        this.libroBase = libroBase;
    }
    
    // Delegación de métodos básicos al libro base
    @Override
    public Long getId() {
        return libroBase.getId();
    }
    
    @Override
    public String getTitulo() {
        return libroBase.getTitulo();
    }
    
    @Override
    public String getAutor() {
        return libroBase.getAutor();
    }
    
    @Override
    public TipoLibro getTipo() {
        return libroBase.getTipo();
    }
    
    @Override
    public FormatoLibro getFormato() {
        return libroBase.getFormato();
    }
    
    @Override
    public EstadoLibro getEstado() {
        return libroBase.getEstado();
    }
    
    @Override
    public void setEstado(EstadoLibro estado) {
        libroBase.setEstado(estado);
    }
    
    @Override
    public String getInformacion() {
        return libroBase.getInformacion();
    }
    
    @Override
    public boolean estaDisponible() {
        return libroBase.estaDisponible();
    }
}