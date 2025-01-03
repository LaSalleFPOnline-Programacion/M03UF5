package org.example.m03uf6.model;

import java.sql.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BaseDatos {

    public static int carrera;
    public static boolean seguirPartida = false;
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

    public static int partidaPendiente() {

        String query = "SELECT IdCarrera FROM carreras WHERE Finalizada = 0 ORDER BY IdCarrera DESC";

        try (Statement stmt = conexion.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return rs.getInt("IdCarrera");
            } else {
                return 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void anularPartida(int idCarrera) {
        String query = "UPDATE carreras SET Finalizada = -1 WHERE IdCarrera = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, idCarrera);
            stmt.executeUpdate();
            System.out.println("Partida anulada con éxito.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al anular la partida: " + e.getMessage());
        }
    }

    public static int crearNuevaPartida() {
        String insertQuery = "INSERT INTO carreras (Finalizada) VALUES (0)";
        String selectQuery = "SELECT LAST_INSERT_ID()";

        try (Statement stmt = conexion.createStatement()) {
            stmt.executeUpdate(insertQuery);
            ResultSet rs = stmt.executeQuery(selectQuery);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error al crear una nueva partida: " + e.getMessage());
        }
        return 0;
    }

    public static void guardarApostante(int carrera, String nombreJugador, int esBot, double importeApostado, String caballo) {

        String query = "INSERT INTO apuestas (IdCarrera, NombreJugador, EsBot, ImporteApostado, CaballoApostado) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, carrera);
            stmt.setString(2, nombreJugador);
            stmt.setInt(3, esBot);
            stmt.setDouble(4, importeApostado);
            stmt.setString(5, caballo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static List<Apuesta> obtenerApuestasPorCarrera(int carrera) {
        List<Apuesta> apuestas = new ArrayList<>();
        String query = "SELECT * FROM apuestas WHERE idCarrera = ? ORDER BY esBot";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, carrera);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Apuesta apuesta = new Apuesta();
                apuesta.setNombre(rs.getString("NombreJugador"));
                apuesta.setCaballo(rs.getString("CaballoApostado"));
                apuesta.setImporte(rs.getDouble("ImporteApostado"));
                apuestas.add(apuesta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return apuestas;
    }

    public static void insertarCartasEnBaraja(int carrera, CardsDeck cardsDeck) {

        String query = "INSERT INTO baraja (IdCarrera, carta) VALUES (?, ?)";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            for (Card card : cardsDeck.getCards()) {
                stmt.setInt(1, carrera);
                stmt.setString(2, card.getCardCode());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertarCartaEnDescartes(int carrera, Card carta) {

        String query = "INSERT INTO descartes (IdCarrera, carta) VALUES (?, ?)";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, carrera);
            stmt.setString(2, carta.getCardCode());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void eliminarCartaBaraja(int carrera, Card carta) {

        String query = "DELETE FROM baraja WHERE IdCarrera = ? and Carta = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, carrera);
            stmt.setString(2, carta.getCardCode());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void registrarMovimiento(int carrera, Card carta, String caballo, int posicion) {

        String query = "INSERT INTO movimientos (IdCarrera, carta, caballo, posicion) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, carrera);
            stmt.setString(2, carta.getCardCode());
            stmt.setString(3, caballo);
            stmt.setInt(4, posicion);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int obtenerUltimoMovimientoCaballo(int carrera, String caballo) {

        String query = "SELECT Posicion FROM movimientos WHERE IdCarrera = ? AND Id = (SELECT MAX(Id) FROM movimientos WHERE Caballo = ?)";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, carrera);
            stmt.setString(2, caballo);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int posicion = rs.getInt("Posicion");
                return posicion;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
