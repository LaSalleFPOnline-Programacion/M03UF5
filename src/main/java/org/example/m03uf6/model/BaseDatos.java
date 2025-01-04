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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conexion;
    }

    public static void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
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

    public static void anularPartida(int carrera) {

        String query = "UPDATE carreras SET Finalizada = -1 WHERE IdCarrera = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, carrera);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int crearNuevaPartida() {

        String insertQuery = "INSERT INTO carreras (Finalizada, FilaPenalizacion) VALUES (0, 9)";
        String selectQuery = "SELECT LAST_INSERT_ID()";

        try (Statement stmt = conexion.createStatement()) {
            stmt.executeUpdate(insertQuery);
            ResultSet rs = stmt.executeQuery(selectQuery);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

    public static void guardarBaraja(int carrera, String nombreBaraja, CardsDeck cardsDeck) {

        String query = "INSERT INTO " + nombreBaraja + " (IdCarrera, carta) VALUES (?, ?)";

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

    public static void guardarCartaEnBaraja(int carrera, String nombreBaraja, Card carta) {

        String query = "INSERT INTO " + nombreBaraja + " (IdCarrera, carta) VALUES (?, ?)";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, carrera);
            stmt.setString(2, carta.getCardCode());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void eliminarCartaBaraja(int carrera, String nombreBaraja, Card carta) {

        String query = "DELETE FROM " + nombreBaraja + " WHERE IdCarrera = ? and Carta = ?";

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
                return rs.getInt("Posicion");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void actualizarEstadoCarrera(int carrera, int filaPenalizacion) {

        String query = "UPDATE carreras SET FilaPenalizacion = ? WHERE IdCarrera = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, filaPenalizacion);
            stmt.setInt(2, carrera);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static int obtenerFilaPenalizacionCarrera(int carrera) {

        String query = "SELECT FilaPenalizacion FROM carreras WHERE IdCarrera = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, carrera);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("FilaPenalizacion");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static List<Card> obtenerCartasPenalizadoras(int carrera) {
        List<Card> penalizaciones = new ArrayList<>();
        String query = "SELECT Carta FROM penalizaciones WHERE idCarrera = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setInt(1, carrera);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String cardCode = rs.getString("Carta");
                Card penalizacion = Card.crearCard(cardCode);
                if (penalizacion != null) {
                    penalizaciones.add(penalizacion);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return penalizaciones;
    }

}
