package com.biblioteca.patterns.adapter.impl;

import com.biblioteca.model.enums.EstadoLibro;
import com.biblioteca.model.enums.FormatoLibro;
import com.biblioteca.model.enums.TipoLibro;
import com.biblioteca.model.interfaces.ILibro;
import com.biblioteca.patterns.adapter.LegacyLibro;

/**
 * Adapter que permite integrar libros del sistema legacy con la interfaz actual
 * Implementa el patrón Adapter para compatibilidad con sistemas antiguos
 * 
 * Note: No es un componente Spring, se usa manualmente cuando sea necesario
 */
public class LegacyLibroAdapter implements ILibro {
    
    private final LegacyLibro legacyLibro;
    private Long id;
    
    public LegacyLibroAdapter(LegacyLibro legacyLibro) {
        this.legacyLibro = legacyLibro;
    }
    
    @Override
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    @Override
    public String getTitulo() {
        return legacyLibro.getNombre();
    }
    
    @Override
    public String getAutor() {
        return legacyLibro.getEscritor();
    }
    
    @Override
    public TipoLibro getTipo() {
        // Mapeo de categoría legacy a TipoLibro
        String categoria = legacyLibro.getCategoria();
        if (categoria == null) {
            return TipoLibro.FICCION; // Valor por defecto
        }
        
        return switch (categoria.toLowerCase()) {
            case "ficcion", "novela", "cuento", "fantasia", "ciencia ficcion" -> TipoLibro.FICCION;
            case "no ficcion", "ensayo", "biografia", "historia", "ciencia", "tecnico" -> TipoLibro.NO_FICCION;
            default -> TipoLibro.FICCION; // Valor por defecto
        };
    }
    
    @Override
    public FormatoLibro getFormato() {
        // Mapeo de formato legacy a FormatoLibro
        String formato = legacyLibro.getFormato();
        if (formato == null) {
            return FormatoLibro.FISICO; // Valor por defecto
        }
        
        return switch (formato.toLowerCase()) {
            case "digital", "ebook", "pdf", "epub", "electronico" -> FormatoLibro.DIGITAL;
            case "fisico", "papel", "impreso", "tapa dura", "tapa blanda" -> FormatoLibro.FISICO;
            default -> FormatoLibro.FISICO; // Valor por defecto
        };
    }
    
    @Override
    public EstadoLibro getEstado() {
        return legacyLibro.isPrestado() ? EstadoLibro.PRESTADO : EstadoLibro.DISPONIBLE;
    }
    
    @Override
    public void setEstado(EstadoLibro estado) {
        if (EstadoLibro.PRESTADO.equals(estado)) {
            legacyLibro.marcarPrestado();
        } else {
            legacyLibro.marcarDisponible();
        }
    }
    
    @Override
    public String getInformacion() {
        return String.format("Libro Legacy[ID=%d, Título='%s', Autor='%s', Tipo=%s, Formato=%s, Estado=%s]",
                id, getTitulo(), getAutor(), getTipo().getDescripcion(), 
                getFormato().getDescripcion(), getEstado().getDescripcion());
    }
    
    @Override
    public boolean estaDisponible() {
        return !legacyLibro.isPrestado();
    }
    
    /**
     * Obtiene el libro legacy subyacente
     * @return instancia del libro legacy
     */
    public LegacyLibro getLegacyLibro() {
        return legacyLibro;
    }
    
    /**
     * Método de conveniencia para crear un adapter desde un libro legacy
     * @param legacyLibro el libro legacy a adaptar
     * @return nuevo adapter
     */
    public static LegacyLibroAdapter adaptar(LegacyLibro legacyLibro) {
        return new LegacyLibroAdapter(legacyLibro);
    }
    
    /**
     * Método de conveniencia para crear un adapter con ID
     * @param legacyLibro el libro legacy a adaptar
     * @param id el ID a asignar
     * @return nuevo adapter con ID
     */
    public static LegacyLibroAdapter adaptarConId(LegacyLibro legacyLibro, Long id) {
        LegacyLibroAdapter adapter = new LegacyLibroAdapter(legacyLibro);
        adapter.setId(id);
        return adapter;
    }
}