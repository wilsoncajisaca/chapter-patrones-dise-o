package com.biblioteca.controller;

import com.biblioteca.exception.LibroNoEncontradoException;
import com.biblioteca.exception.OperacionInvalidaException;
import com.biblioteca.exception.ValidationException;
import com.biblioteca.model.builders.LibroBuilder;
import com.biblioteca.model.entities.Libro;
import com.biblioteca.model.enums.FormatoLibro;
import com.biblioteca.model.enums.TipoLibro;
import com.biblioteca.patterns.strategy.impl.SearchByAuthorStrategy;
import com.biblioteca.patterns.strategy.impl.SearchByTitleStrategy;
import com.biblioteca.patterns.strategy.impl.SearchByTypeStrategy;
import com.biblioteca.service.BibliotecaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST para la gestión de libros
 * Expone endpoints para todas las operaciones de la biblioteca
 */
@RestController
@RequestMapping("/api/libros")
@CrossOrigin(origins = "*")
public class LibroController {
    
    private final BibliotecaService bibliotecaService;
    private final SearchByTitleStrategy searchByTitleStrategy;
    private final SearchByAuthorStrategy searchByAuthorStrategy;
    private final SearchByTypeStrategy searchByTypeStrategy;
    
    public LibroController(BibliotecaService bibliotecaService,
                          SearchByTitleStrategy searchByTitleStrategy,
                          SearchByAuthorStrategy searchByAuthorStrategy,
                          SearchByTypeStrategy searchByTypeStrategy) {
        this.bibliotecaService = bibliotecaService;
        this.searchByTitleStrategy = searchByTitleStrategy;
        this.searchByAuthorStrategy = searchByAuthorStrategy;
        this.searchByTypeStrategy = searchByTypeStrategy;
    }
    
    /**
     * Lista todos los libros
     * GET /api/libros
     */
    @GetMapping
    public ResponseEntity<List<Libro>> listarTodosLosLibros() {
        List<Libro> libros = bibliotecaService.listarTodosLosLibros();
        return ResponseEntity.ok(libros);
    }
    
    /**
     * Lista solo libros disponibles
     * GET /api/libros/disponibles
     */
    @GetMapping("/disponibles")
    public ResponseEntity<List<Libro>> listarLibrosDisponibles() {
        List<Libro> libros = bibliotecaService.listarLibrosDisponibles();
        return ResponseEntity.ok(libros);
    }
    
    /**
     * Lista solo libros prestados
     * GET /api/libros/prestados
     */
    @GetMapping("/prestados")
    public ResponseEntity<List<Libro>> listarLibrosPrestados() {
        List<Libro> libros = bibliotecaService.listarLibrosPrestados();
        return ResponseEntity.ok(libros);
    }
    
    /**
     * Busca un libro por ID
     * GET /api/libros/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Libro> buscarPorId(@PathVariable Long id) {
        try {
            Libro libro = bibliotecaService.buscarPorId(id);
            return ResponseEntity.ok(libro);
        } catch (LibroNoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Agrega un nuevo libro usando el Builder pattern
     * POST /api/libros
     */
    @PostMapping
    public ResponseEntity<?> agregarLibro(@Valid @RequestBody LibroRequest request) {
        try {
            Libro libro = LibroBuilder.nuevo()
                    .conTitulo(request.getTitulo())
                    .conAutor(request.getAutor())
                    .deTipo(request.getTipo())
                    .enFormato(request.getFormato())
                    .construir();
            
            Libro libroGuardado = bibliotecaService.agregarLibro(libro);
            return ResponseEntity.status(HttpStatus.CREATED).body(libroGuardado);
            
        } catch (ValidationException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Error de validación", 
                               "detalles", e.getValidationResult().getErrores()));
        } catch (OperacionInvalidaException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * Busca libros por título
     * GET /api/libros/buscar/titulo?q={criterio}
     */
    @GetMapping("/buscar/titulo")
    public ResponseEntity<List<Libro>> buscarPorTitulo(@RequestParam String q) {
        List<Libro> libros = bibliotecaService.buscarLibros(searchByTitleStrategy, q);
        return ResponseEntity.ok(libros);
    }
    
    /**
     * Busca libros por autor
     * GET /api/libros/buscar/autor?q={criterio}
     */
    @GetMapping("/buscar/autor")
    public ResponseEntity<List<Libro>> buscarPorAutor(@RequestParam String q) {
        List<Libro> libros = bibliotecaService.buscarLibros(searchByAuthorStrategy, q);
        return ResponseEntity.ok(libros);
    }
    
    /**
     * Busca libros por tipo
     * GET /api/libros/buscar/tipo?q={criterio}
     */
    @GetMapping("/buscar/tipo")
    public ResponseEntity<List<Libro>> buscarPorTipo(@RequestParam String q) {
        List<Libro> libros = bibliotecaService.buscarLibros(searchByTypeStrategy, q);
        return ResponseEntity.ok(libros);
    }
    
    /**
     * Realiza un préstamo de libro
     * PUT /api/libros/{id}/prestar
     */
    @PutMapping("/{id}/prestar")
    public ResponseEntity<?> prestarLibro(@PathVariable Long id) {
        try {
            Libro libro = bibliotecaService.prestarLibro(id);
            return ResponseEntity.ok(libro);
        } catch (LibroNoEncontradoException e) {
            return ResponseEntity.notFound().build();
        } catch (OperacionInvalidaException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * Realiza la devolución de un libro
     * PUT /api/libros/{id}/devolver
     */
    @PutMapping("/{id}/devolver")
    public ResponseEntity<?> devolverLibro(@PathVariable Long id) {
        try {
            Libro libro = bibliotecaService.devolverLibro(id);
            return ResponseEntity.ok(libro);
        } catch (LibroNoEncontradoException e) {
            return ResponseEntity.notFound().build();
        } catch (OperacionInvalidaException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * Elimina un libro
     * DELETE /api/libros/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarLibro(@PathVariable Long id) {
        try {
            bibliotecaService.eliminarLibro(id);
            return ResponseEntity.noContent().build();
        } catch (LibroNoEncontradoException e) {
            return ResponseEntity.notFound().build();
        } catch (OperacionInvalidaException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * Obtiene estadísticas de la biblioteca
     * GET /api/libros/estadisticas
     */
    @GetMapping("/estadisticas")
    public ResponseEntity<BibliotecaService.EstadisticasBiblioteca> obtenerEstadisticas() {
        BibliotecaService.EstadisticasBiblioteca estadisticas = bibliotecaService.obtenerEstadisticas();
        return ResponseEntity.ok(estadisticas);
    }
    
    /**
     * Clase interna para encapsular requests de creación de libros
     */
    public static class LibroRequest {
        @jakarta.validation.constraints.NotBlank(message = "El título es obligatorio")
        private String titulo;
        
        @jakarta.validation.constraints.NotBlank(message = "El autor es obligatorio")
        private String autor;
        
        @jakarta.validation.constraints.NotNull(message = "El tipo es obligatorio")
        private TipoLibro tipo;
        
        @jakarta.validation.constraints.NotNull(message = "El formato es obligatorio")
        private FormatoLibro formato;
        
        // Constructores
        public LibroRequest() {}
        
        public LibroRequest(String titulo, String autor, TipoLibro tipo, FormatoLibro formato) {
            this.titulo = titulo;
            this.autor = autor;
            this.tipo = tipo;
            this.formato = formato;
        }
        
        // Getters y Setters
        public String getTitulo() { return titulo; }
        public void setTitulo(String titulo) { this.titulo = titulo; }
        
        public String getAutor() { return autor; }
        public void setAutor(String autor) { this.autor = autor; }
        
        public TipoLibro getTipo() { return tipo; }
        public void setTipo(TipoLibro tipo) { this.tipo = tipo; }
        
        public FormatoLibro getFormato() { return formato; }
        public void setFormato(FormatoLibro formato) { this.formato = formato; }
    }
}