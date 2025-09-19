package com.biblioteca.repository;

import com.biblioteca.model.entities.Libro;
import com.biblioteca.model.enums.EstadoLibro;
import com.biblioteca.model.enums.FormatoLibro;
import com.biblioteca.model.enums.TipoLibro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para la entidad Libro
 * Implementa el patrón Repository para acceso a datos
 */
@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    
    /**
     * Busca libros por título (case-insensitive y parcial)
     * @param titulo título a buscar
     * @return lista de libros que coinciden
     */
    List<Libro> findByTituloContainingIgnoreCase(String titulo);
    
    /**
     * Busca libros por autor (case-insensitive y parcial)
     * @param autor autor a buscar
     * @return lista de libros que coinciden
     */
    List<Libro> findByAutorContainingIgnoreCase(String autor);
    
    /**
     * Busca libros por tipo
     * @param tipo tipo de libro
     * @return lista de libros del tipo especificado
     */
    List<Libro> findByTipo(TipoLibro tipo);
    
    /**
     * Busca libros por formato
     * @param formato formato del libro
     * @return lista de libros del formato especificado
     */
    List<Libro> findByFormato(FormatoLibro formato);
    
    /**
     * Busca libros por estado
     * @param estado estado del libro
     * @return lista de libros en el estado especificado
     */
    List<Libro> findByEstado(EstadoLibro estado);
    
    /**
     * Busca libros disponibles
     * @return lista de libros disponibles
     */
    @Query("SELECT l FROM Libro l WHERE l.estado = com.biblioteca.model.enums.EstadoLibro.DISPONIBLE")
    List<Libro> findLibrosDisponibles();
    
    /**
     * Busca libros prestados
     * @return lista de libros prestados
     */
    @Query("SELECT l FROM Libro l WHERE l.estado = com.biblioteca.model.enums.EstadoLibro.PRESTADO")
    List<Libro> findLibrosPrestados();
    
    /**
     * Cuenta libros por estado
     * @param estado estado a contar
     * @return número de libros en ese estado
     */
    long countByEstado(EstadoLibro estado);
    
    /**
     * Búsqueda personalizada por título y autor
     * @param titulo título a buscar (puede ser null)
     * @param autor autor a buscar (puede ser null)
     * @return lista de libros que coinciden
     */
    @Query("SELECT l FROM Libro l WHERE " +
           "(:titulo IS NULL OR LOWER(l.titulo) LIKE LOWER(CONCAT('%', :titulo, '%'))) AND " +
           "(:autor IS NULL OR LOWER(l.autor) LIKE LOWER(CONCAT('%', :autor, '%')))")
    List<Libro> findByTituloAndAutor(@Param("titulo") String titulo, @Param("autor") String autor);
    
    /**
     * Búsqueda avanzada con múltiples criterios
     * @param titulo título a buscar (opcional)
     * @param autor autor a buscar (opcional)
     * @param tipo tipo de libro (opcional)
     * @param formato formato del libro (opcional)
     * @param estado estado del libro (opcional)
     * @return lista de libros que coinciden con los criterios
     */
    @Query("SELECT l FROM Libro l WHERE " +
           "(:titulo IS NULL OR LOWER(l.titulo) LIKE LOWER(CONCAT('%', :titulo, '%'))) AND " +
           "(:autor IS NULL OR LOWER(l.autor) LIKE LOWER(CONCAT('%', :autor, '%'))) AND " +
           "(:tipo IS NULL OR l.tipo = :tipo) AND " +
           "(:formato IS NULL OR l.formato = :formato) AND " +
           "(:estado IS NULL OR l.estado = :estado)")
    List<Libro> findByMultipleCriteria(
            @Param("titulo") String titulo,
            @Param("autor") String autor,
            @Param("tipo") TipoLibro tipo,
            @Param("formato") FormatoLibro formato,
            @Param("estado") EstadoLibro estado
    );
    
    /**
     * Verifica si existe un libro con el mismo título y autor
     * @param titulo título del libro
     * @param autor autor del libro
     * @return true si existe, false en caso contrario
     */
    boolean existsByTituloAndAutor(String titulo, String autor);
}