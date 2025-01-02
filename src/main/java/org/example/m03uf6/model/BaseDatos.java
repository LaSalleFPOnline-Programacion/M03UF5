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

    public static void insertarBaraja(int baraja, CardsDeck cardsDeck) {

        String insertQuery = "INSERT INTO Baraja (IdBaraja, carta) VALUES (?, ?)";

        try (PreparedStatement stmt = conexion.prepareStatement(insertQuery)) {
            for (Card card : cardsDeck.getCards()) {
                stmt.setInt(1, baraja);
                stmt.setString(2, card.getCardCode());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertarDescarte(int baraja, Card carta) {

        String insertQuery = "INSERT INTO Descartes (IdBaraja, carta) VALUES (?, ?)";

        try (PreparedStatement stmt = conexion.prepareStatement(insertQuery)) {
            stmt.setInt(1, baraja);
            stmt.setString(2, carta.getCardCode());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void eliminarCarta(int baraja, Card carta) {

        String deleteQuery = "DELETE FROM Baraja WHERE IdBaraja = ? and Carta = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(deleteQuery)) {
            stmt.setInt(1, baraja);
            stmt.setString(2, carta.getCardCode());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
