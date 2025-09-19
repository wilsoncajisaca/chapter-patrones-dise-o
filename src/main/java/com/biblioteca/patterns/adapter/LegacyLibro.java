package com.biblioteca.patterns.adapter;

/**
 * Clase que simula un sistema legacy de libros con interfaz incompatible
 * Representa un libro del sistema antiguo con métodos diferentes
 */
public class LegacyLibro {
    
    private String nombre;
    private String escritor;
    private String categoria;
    private String formato;
    private boolean prestado;
    
    public LegacyLibro(String nombre, String escritor, String categoria, String formato) {
        this.nombre = nombre;
        this.escritor = escritor;
        this.categoria = categoria;
        this.formato = formato;
        this.prestado = false;
    }
    
    // Métodos del sistema legacy con nombres diferentes
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getEscritor() {
        return escritor;
    }
    
    public void setEscritor(String escritor) {
        this.escritor = escritor;
    }
    
    public String getCategoria() {
        return categoria;
    }
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public String getFormato() {
        return formato;
    }
    
    public void setFormato(String formato) {
        this.formato = formato;
    }
    
    public boolean isPrestado() {
        return prestado;
    }
    
    public void marcarPrestado() {
        this.prestado = true;
    }
    
    public void marcarDisponible() {
        this.prestado = false;
    }
    
    /**
     * Método legacy para obtener información del libro
     * @return información en formato del sistema antiguo
     */
    public String obtenerDatos() {
        return String.format("Nombre: %s | Escritor: %s | Categoría: %s | Formato: %s | Estado: %s",
                nombre, escritor, categoria, formato, prestado ? "Prestado" : "Disponible");
    }
}