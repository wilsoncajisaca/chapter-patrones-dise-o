# Sistema de Gestión de Biblioteca - Patrones de Diseño

## 📋 Descripción

Este proyecto implementa un sistema completo de gestión de biblioteca utilizando **Java 21**, **Spring Boot** y **H2 Database** como base de datos en memoria. El sistema demuestra la implementación de múltiples patrones de diseño trabajando de manera integrada.

## 🎯 Patrones de Diseño Implementados

### 1. **Singleton** 📍
- **Ubicación**: `com.biblioteca.config.DatabaseConnection`
- **Propósito**: Garantiza una única instancia de conexión a la base de datos
- **Implementación**: Thread-safe con lazy initialization

### 2. **Builder** 🏗️
- **Ubicación**: `com.biblioteca.model.builders.LibroBuilder`
- **Propósito**: Construcción flexible y legible de objetos Libro
- **Características**: Method chaining, validaciones, métodos de conveniencia

### 3. **Factory Method** 🏭
- **Ubicación**: `com.biblioteca.patterns.factory`
- **Implementaciones**: 
  - `FiccionFactory` - Crea libros de ficción
  - `NoFiccionFactory` - Crea libros de no ficción
- **Propósito**: Encapsula la creación de libros según su tipo

### 4. **Abstract Factory** 🏭🏭
- **Ubicación**: `com.biblioteca.patterns.abstractfactory`
- **Implementaciones**:
  - `LibroFisicoFactory` - Crea familias de libros físicos
  - `LibroDigitalFactory` - Crea familias de libros digitales
- **Propósito**: Crea familias relacionadas de objetos (libros por formato)

### 5. **Strategy** 🎯
- **Ubicación**: `com.biblioteca.patterns.strategy`
- **Implementaciones**:
  - `SearchByTitleStrategy` - Búsqueda por título
  - `SearchByAuthorStrategy` - Búsqueda por autor
  - `SearchByTypeStrategy` - Búsqueda por tipo
- **Propósito**: Algoritmos intercambiables de búsqueda

### 6. **Observer** 👁️
- **Ubicación**: `com.biblioteca.patterns.observer`
- **Implementaciones**:
  - `PrestamoObserver` - Notifica préstamos y devoluciones
  - `EstadisticasObserver` - Recopila estadísticas
- **Propósito**: Notificaciones automáticas de cambios de estado

### 7. **Decorator** 🎨
- **Ubicación**: `com.biblioteca.patterns.decorator`
- **Implementación**: `PrestamoDecorator`
- **Propósito**: Añade funcionalidad de préstamo sin modificar la clase Libro

### 8. **Chain of Responsibility** ⛓️
- **Ubicación**: `com.biblioteca.patterns.chainofresponsibility`
- **Implementaciones**:
  - `TituloValidator` - Valida títulos
  - `AutorValidator` - Valida autores
  - `ConsistenciaValidator` - Valida consistencia general
- **Propósito**: Cadena de validaciones secuenciales

### 9. **Adapter** 🔌
- **Ubicación**: `com.biblioteca.patterns.adapter`
- **Implementación**: `LegacyLibroAdapter`
- **Propósito**: Integra sistemas legacy con interfaz incompatible

## 🏗️ Arquitectura del Sistema

```
src/
├── main/java/com/biblioteca/
│   ├── BibliotecaApplication.java          # Aplicación principal
│   ├── DemostracionPatrones.java           # Demostración de patrones
│   ├── config/
│   │   └── DatabaseConnection.java         # Singleton
│   ├── controller/
│   │   └── LibroController.java            # REST API
│   ├── service/
│   │   └── BibliotecaService.java          # Lógica de negocio
│   ├── repository/
│   │   └── LibroRepository.java            # Acceso a datos
│   ├── model/
│   │   ├── entities/Libro.java             # Entidad JPA
│   │   ├── enums/                          # Enumeraciones
│   │   ├── interfaces/ILibro.java          # Contrato de libro
│   │   └── builders/LibroBuilder.java      # Builder pattern
│   ├── patterns/
│   │   ├── factory/                        # Factory Method
│   │   ├── abstractfactory/                # Abstract Factory
│   │   ├── strategy/                       # Strategy
│   │   ├── observer/                       # Observer
│   │   ├── decorator/                      # Decorator
│   │   ├── chainofresponsibility/          # Chain of Responsibility
│   │   └── adapter/                        # Adapter
│   └── exception/                          # Excepciones personalizadas
└── resources/
    └── application.properties              # Configuración
```

## 🚀 Cómo Ejecutar

### Prerrequisitos
- Java 21
- Maven 3.6+

### Ejecución
```bash
# Clonar el repositorio
git clone [url-repositorio]

# Navegar al directorio
cd proyecto-patrones-diseño

# Compilar y ejecutar
mvn spring-boot:run
```

### Acceso al Sistema
- **API REST**: http://localhost:8080/api/libros
- **H2 Console**: http://localhost:8080/h2-console
  - URL JDBC: `jdbc:h2:mem:biblioteca`
  - Usuario: `sa`
  - Contraseña: `password`

## 📡 API REST Endpoints

### Libros
- `GET /api/libros` - Lista todos los libros
- `GET /api/libros/disponibles` - Lista libros disponibles
- `GET /api/libros/prestados` - Lista libros prestados
- `GET /api/libros/{id}` - Busca libro por ID
- `POST /api/libros` - Agrega nuevo libro
- `PUT /api/libros/{id}/prestar` - Presta un libro
- `PUT /api/libros/{id}/devolver` - Devuelve un libro
- `DELETE /api/libros/{id}` - Elimina un libro

### Búsquedas
- `GET /api/libros/buscar/titulo?q={criterio}` - Busca por título
- `GET /api/libros/buscar/autor?q={criterio}` - Busca por autor
- `GET /api/libros/buscar/tipo?q={criterio}` - Busca por tipo

### Estadísticas
- `GET /api/libros/estadisticas` - Obtiene estadísticas

## 📝 Ejemplo de Uso

### Crear un Libro (POST /api/libros)
```json
{
    "titulo": "Cien años de soledad",
    "autor": "Gabriel García Márquez",
    "tipo": "FICCION",
    "formato": "FISICO"
}
```

### Respuesta
```json
{
    "id": 1,
    "titulo": "Cien años de soledad",
    "autor": "Gabriel García Márquez",
    "tipo": "FICCION",
    "formato": "FISICO",
    "estado": "DISPONIBLE",
    "fechaCreacion": "2024-01-15T10:30:00",
    "fechaActualizacion": null
}
```

## 🔧 Principios SOLID Aplicados

- **S** - Single Responsibility: Cada clase tiene una responsabilidad específica
- **O** - Open/Closed: Extensible para nuevos tipos sin modificar código existente
- **L** - Liskov Substitution: Las implementaciones son intercambiables
- **I** - Interface Segregation: Interfaces específicas y cohesivas
- **D** - Dependency Inversion: Dependencias hacia abstracciones

## 🧪 Demostración Automática

Al iniciar la aplicación, se ejecuta automáticamente `DemostracionPatrones.java` que:

1. ✅ Configura observadores
2. 🏗️ Demuestra Builder pattern
3. 🏭 Demuestra Factory Method
4. 🏭🏭 Demuestra Abstract Factory
5. 🔌 Demuestra Adapter pattern
6. 🎯 Demuestra Strategy pattern
7. 🎨 Demuestra Decorator pattern
8. 📊 Muestra estadísticas finales

## 📋 Características Implementadas

### Validaciones
- Título no vacío (mínimo 1, máximo 200 caracteres)
- Autor válido (formato de nombre, mínimo 2, máximo 100 caracteres)
- Tipos y formatos obligatorios
- Verificación de duplicados

### Funcionalidades
- ✅ Agregar libros con validaciones
- 🔍 Búsqueda por múltiples criterios
- 📚 Listar todos los libros
- 📤 Sistema de préstamos
- 📥 Sistema de devoluciones
- 📊 Estadísticas en tiempo real
- 🔔 Notificaciones automáticas

### Clean Code
- Nombres descriptivos y significativos
- Métodos pequeños y enfocados
- Comentarios JavaDoc completos
- Manejo de excepciones robusto
- Principios DRY y KISS

## 🏆 Patrones Adicionales

### Repository Pattern
- Encapsula acceso a datos
- Queries personalizadas con JPA

### MVC Pattern
- Separación clara de responsabilidades
- Controller -> Service -> Repository

### DTO Pattern
- `LibroRequest` para transferencia de datos
- Validaciones con Bean Validation

## 📈 Mejoras Futuras

- [ ] Implementar autenticación y autorización
- [ ] Agregar más estrategias de búsqueda
- [ ] Sistema de reservas
- [ ] Interfaz web con Thymeleaf
- [ ] Notificaciones por email
- [ ] Cache con Redis
- [ ] Métricas con Micrometer

---

**Desarrollado con ❤️ usando Java 21, Spring Boot y buenas prácticas de programación**