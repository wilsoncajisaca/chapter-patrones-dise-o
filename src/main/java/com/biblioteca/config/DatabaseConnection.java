package com.biblioteca.config;

import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Implementación del patrón Singleton para gestionar la conexión a la base de datos.
 * Asegura que solo exista una instancia de la conexión en toda la aplicación.
 */
@Component
public class DatabaseConnection {
    
    private static DatabaseConnection instance;
    private final DataSource dataSource;
    private Connection connection;
    
    private DatabaseConnection(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    /**
     * Método estático que retorna la única instancia de DatabaseConnection
     * Implementa lazy initialization con thread safety
     * 
     * @param dataSource el DataSource configurado por Spring
     * @return la única instancia de DatabaseConnection
     */
    public static synchronized DatabaseConnection getInstance(DataSource dataSource) {
        if (instance == null) {
            instance = new DatabaseConnection(dataSource);
        }
        return instance;
    }
    
    /**
     * Obtiene una conexión a la base de datos
     * Implementa el patrón de reutilización de conexiones
     * 
     * @return una conexión activa a la base de datos
     * @throws SQLException si ocurre un error al obtener la conexión
     */
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = dataSource.getConnection();
        }
        return connection;
    }
    
    /**
     * Cierra la conexión a la base de datos de forma segura
     * Implementa manejo de recursos con try-catch
     */
    public void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                // Log del error - en un entorno real usaríamos un logger
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
    
    /**
     * Verifica si la conexión está activa
     * 
     * @return true si la conexión está activa, false en caso contrario
     */
    public boolean isConnectionActive() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}