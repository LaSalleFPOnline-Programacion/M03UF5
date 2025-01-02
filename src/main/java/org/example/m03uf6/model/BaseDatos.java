package org.example.m03uf6.model;

import java.sql.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class BaseDatos {

    private static Connection conexion;
    public static Connection conectar() {
        if (conexion == null) {
            String url = "jdbc:mysql://localhost:3306/m03uf6";
            String user = "root";
            String password = "";

            try {
                conexion = DriverManager.getConnection(url, user, password);
                System.out.println("Conexión correcta.");
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            }
        }
        return conexion;
    }

    public static void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    public static void insertarCartasEnBaraja(int baraja, CardsDeck cardsDeck) {

        String query = "INSERT INTO Baraja (IdBaraja, carta) VALUES (?, ?)";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            for (Card card : cardsDeck.getCards()) {
                stmt.setInt(1, baraja);
                stmt.setString(2, card.getCardCode());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertarCartaEnDescartes(int baraja, Card carta) {

        String query = "INSERT INTO Descartes (IdBaraja, carta) VALUES (?, ?)";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, baraja);
            stmt.setString(2, carta.getCardCode());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void eliminarCartaBaraja(int baraja, Card carta) {

        String query = "DELETE FROM Baraja WHERE IdBaraja = ? and Carta = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, baraja);
            stmt.setString(2, carta.getCardCode());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void registrarMovimiento(int baraja, Card carta, String posicion) {

        String query = "INSERT INTO Movimientos (IdBaraja, carta, posicion) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, baraja);
            stmt.setString(2, carta.getCardCode());
            stmt.setString(3, posicion);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
