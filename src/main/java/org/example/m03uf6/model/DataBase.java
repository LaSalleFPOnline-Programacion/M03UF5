package org.example.m03uf6.model;

import java.sql.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    // Establecer conexión con la base de datos
    public static Connection conectar() {
        String url = "jdbc:mysql://localhost:3306/m03uf6"; // Cambia este nombre
        String user = "root"; // Cambia si usas un usuario distinto
        String password = ""; // Cambia si tienes una contraseña

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.err.println("Error al conectar: " + e.getMessage());
            return null;
        }
    }

    public static insertar(String nombreTabla, int IdCarrera String valor) {

    }

    public static void obtenerDatos() {
        Connection conexion = this.conectar();
        if (conexion != null) {
            String query = "SELECT * FROM prueba"; // Cambia por tu consulta
            try (Statement statement = conexion.createStatement();
                 ResultSet rs = statement.executeQuery(query)) {
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id") + ", description: " + rs.getString("description"));
                }
            } catch (SQLException e) {
                System.err.println("Error al ejecutar la consulta: " + e.getMessage());
            } finally {
                try {
                    conexion.close();
                } catch (SQLException e) {
                    System.err.println("Error al cerrar la conexión: " + e.getMessage());
                }
            }
        }
    }
}
