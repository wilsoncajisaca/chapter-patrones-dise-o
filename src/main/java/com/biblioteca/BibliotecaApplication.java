package com.biblioteca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Spring Boot
 * Sistema de Gestión de Biblioteca con Patrones de Diseño
 */
@SpringBootApplication
public class BibliotecaApplication {
    
    public static void main(String[] args) {
        System.out.println("🏛️ Iniciando Sistema de Gestión de Biblioteca...");
        System.out.println("📚 Implementando patrones de diseño: Singleton, Factory, Builder, Strategy, Observer, Decorator, Chain of Responsibility y Adapter");
        
        SpringApplication.run(BibliotecaApplication.class, args);
        
        System.out.println("✅ Sistema iniciado correctamente!");
        System.out.println("🌐 Acceso a H2 Console: http://localhost:8080/h2-console");
        System.out.println("📡 API REST disponible en: http://localhost:8080/api/libros");
    }
}