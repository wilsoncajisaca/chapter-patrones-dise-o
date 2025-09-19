package com.biblioteca.service;

import com.biblioteca.exception.LibroNoEncontradoException;
import com.biblioteca.exception.OperacionInvalidaException;
import com.biblioteca.exception.ValidationException;
import com.biblioteca.model.entities.Libro;
import com.biblioteca.model.enums.EstadoLibro;
import com.biblioteca.patterns.chainofresponsibility.LibroValidator;
import com.biblioteca.patterns.chainofresponsibility.ValidationResult;
import com.biblioteca.patterns.chainofresponsibility.impl.AutorValidator;
import com.biblioteca.patterns.chainofresponsibility.impl.ConsistenciaValidator;
import com.biblioteca.patterns.chainofresponsibility.impl.TituloValidator;
import com.biblioteca.patterns.observer.LibroObserver;
import com.biblioteca.patterns.observer.LibroSubject;
import com.biblioteca.patterns.strategy.SearchStrategy;
import com.biblioteca.repository.LibroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio principal para la gestión de libros
 * Implementa los principios SOLID y Clean Code
 * Actúa como Subject en el patrón Observer
 */
@Service
@Transactional
public class BibliotecaService implements LibroSubject {
    
    private final LibroRepository libroRepository;
    private final List<LibroObserver> observadores;
    private final LibroValidator validadorChain;
    
    public BibliotecaService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
        this.observadores = new ArrayList<>();
        this.validadorChain = configurarCadenaValidacion();
    }
    
    /**
     * Configura la cadena de validadores usando Chain of Responsibility
     * @return primer validador de la cadena
     */
    private LibroValidator configurarCadenaValidacion() {
        TituloValidator tituloValidator = new TituloValidator();
        AutorValidator autorValidator = new AutorValidator();
        ConsistenciaValidator consistenciaValidator = new ConsistenciaValidator();
        
        // Configurar la cadena: Título -> Autor -> Consistencia
        tituloValidator.setNext(autorValidator);
        autorValidator.setNext(consistenciaValidator);
        
        return tituloValidator;
    }
    
    /**
     * Agrega un nuevo libro al sistema con validaciones
     * @param libro el libro a agregar
     * @return el libro guardado con ID asignado
     * @throws ValidationException si la validación falla
     */
    public Libro agregarLibro(Libro libro) {
        // Ejecutar validaciones usando Chain of Responsibility
        ValidationResult resultado = validadorChain.validar(libro);
        
        if (!resultado.isValido()) {
            throw new ValidationException(resultado);
        }
        
        // Verificar duplicados
        if (libroRepository.existsByTituloAndAutor(libro.getTitulo(), libro.getAutor())) {
            throw new OperacionInvalidaException(
                "Ya existe un libro con el mismo título y autor: " + libro.getTitulo() + " - " + libro.getAutor()
            );
        }
        
        // Guardar el libro
        Libro libroGuardado = libroRepository.save(libro);
        
        // Notificar a observadores
        notificarLibroAgregado(libroGuardado);
        
        // Mostrar advertencias si las hay
        if (resultado.tieneAdvertencias()) {
            System.out.println("⚠️ Advertencias en la validación:");
            resultado.getAdvertencias().forEach(adv -> System.out.println("  - " + adv));
        }
        
        return libroGuardado;
    }
    
    /**
     * Busca libros usando una estrategia de búsqueda específica
     * @param estrategia la estrategia de búsqueda a usar
     * @param criterio el criterio de búsqueda
     * @return lista de libros encontrados
     */
    @Transactional(readOnly = true)
    public List<Libro> buscarLibros(SearchStrategy estrategia, String criterio) {
        List<Libro> todosLosLibros = libroRepository.findAll();
        return estrategia.buscar(todosLosLibros, criterio);
    }
    
    /**
     * Lista todos los libros del sistema
     * @return lista de todos los libros
     */
    @Transactional(readOnly = true)
    public List<Libro> listarTodosLosLibros() {
        return libroRepository.findAll();
    }
    
    /**
     * Lista solo los libros disponibles
     * @return lista de libros disponibles
     */
    @Transactional(readOnly = true)
    public List<Libro> listarLibrosDisponibles() {
        return libroRepository.findByEstado(EstadoLibro.DISPONIBLE);
    }
    
    /**
     * Lista solo los libros prestados
     * @return lista de libros prestados
     */
    @Transactional(readOnly = true)
    public List<Libro> listarLibrosPrestados() {
        return libroRepository.findByEstado(EstadoLibro.PRESTADO);
    }
    
    /**
     * Busca un libro por ID
     * @param id el ID del libro
     * @return el libro encontrado
     * @throws LibroNoEncontradoException si no se encuentra el libro
     */
    @Transactional(readOnly = true)
    public Libro buscarPorId(Long id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new LibroNoEncontradoException(id));
    }
    
    /**
     * Presta un libro cambiando su estado
     * @param id el ID del libro a prestar
     * @return el libro prestado
     * @throws LibroNoEncontradoException si no se encuentra el libro
     * @throws OperacionInvalidaException si el libro ya está prestado
     */
    public Libro prestarLibro(Long id) {
        Libro libro = buscarPorId(id);
        
        if (!libro.estaDisponible()) {
            throw new OperacionInvalidaException(
                "El libro '" + libro.getTitulo() + "' no está disponible para préstamo"
            );
        }
        
        EstadoLibro estadoAnterior = libro.getEstado();
        libro.setEstado(EstadoLibro.PRESTADO);
        
        Libro libroActualizado = libroRepository.save(libro);
        
        // Notificar cambio de estado
        notificarCambioEstado(libroActualizado, 
                            estadoAnterior.name(), 
                            EstadoLibro.PRESTADO.name());
        
        return libroActualizado;
    }
    
    /**
     * Devuelve un libro cambiando su estado a disponible
     * @param id el ID del libro a devolver
     * @return el libro devuelto
     * @throws LibroNoEncontradoException si no se encuentra el libro
     * @throws OperacionInvalidaException si el libro no está prestado
     */
    public Libro devolverLibro(Long id) {
        Libro libro = buscarPorId(id);
        
        if (libro.getEstado() != EstadoLibro.PRESTADO) {
            throw new OperacionInvalidaException(
                "El libro '" + libro.getTitulo() + "' no está prestado"
            );
        }
        
        EstadoLibro estadoAnterior = libro.getEstado();
        libro.setEstado(EstadoLibro.DISPONIBLE);
        
        Libro libroActualizado = libroRepository.save(libro);
        
        // Notificar cambio de estado
        notificarCambioEstado(libroActualizado, 
                            estadoAnterior.name(), 
                            EstadoLibro.DISPONIBLE.name());
        
        return libroActualizado;
    }
    
    /**
     * Elimina un libro del sistema
     * @param id el ID del libro a eliminar
     * @throws LibroNoEncontradoException si no se encuentra el libro
     * @throws OperacionInvalidaException si el libro está prestado
     */
    public void eliminarLibro(Long id) {
        Libro libro = buscarPorId(id);
        
        if (libro.getEstado() == EstadoLibro.PRESTADO) {
            throw new OperacionInvalidaException(
                "No se puede eliminar el libro '" + libro.getTitulo() + "' porque está prestado"
            );
        }
        
        libroRepository.delete(libro);
    }
    
    /**
     * Obtiene estadísticas básicas de la biblioteca
     * @return objeto con estadísticas
     */
    @Transactional(readOnly = true)
    public EstadisticasBiblioteca obtenerEstadisticas() {
        long totalLibros = libroRepository.count();
        long librosDisponibles = libroRepository.countByEstado(EstadoLibro.DISPONIBLE);
        long librosPrestados = libroRepository.countByEstado(EstadoLibro.PRESTADO);
        
        return new EstadisticasBiblioteca(totalLibros, librosDisponibles, librosPrestados);
    }
    
    // Implementación del patrón Observer
    @Override
    public void agregarObservador(LibroObserver observer) {
        observadores.add(observer);
    }
    
    @Override
    public void eliminarObservador(LibroObserver observer) {
        observadores.remove(observer);
    }
    
    @Override
    public void notificarCambioEstado(Libro libro, String estadoAnterior, String estadoNuevo) {
        observadores.forEach(observer -> 
            observer.onLibroEstadoCambiado(libro, estadoAnterior, estadoNuevo));
    }
    
    @Override
    public void notificarLibroAgregado(Libro libro) {
        observadores.forEach(observer -> observer.onLibroAgregado(libro));
    }
    
    @Override
    public List<LibroObserver> getObservadores() {
        return new ArrayList<>(observadores);
    }
    
    /**
     * Clase interna para encapsular estadísticas de la biblioteca
     */
    public static class EstadisticasBiblioteca {
        private final long totalLibros;
        private final long librosDisponibles;
        private final long librosPrestados;
        
        public EstadisticasBiblioteca(long totalLibros, long librosDisponibles, long librosPrestados) {
            this.totalLibros = totalLibros;
            this.librosDisponibles = librosDisponibles;
            this.librosPrestados = librosPrestados;
        }
        
        public long getTotalLibros() { return totalLibros; }
        public long getLibrosDisponibles() { return librosDisponibles; }
        public long getLibrosPrestados() { return librosPrestados; }
        
        @Override
        public String toString() {
            return String.format("Estadísticas: Total=%d, Disponibles=%d, Prestados=%d", 
                    totalLibros, librosDisponibles, librosPrestados);
        }
    }
}