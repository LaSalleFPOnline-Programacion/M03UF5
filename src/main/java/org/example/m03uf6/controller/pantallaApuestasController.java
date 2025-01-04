package org.example.m03uf6.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.m03uf6.carreraCaballos;
import org.example.m03uf6.model.*;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.util.List;

public class pantallaApuestasController {
    private Juego juego;
    @FXML
    private TextField nombreJugador;
    @FXML
    private ComboBox<String> elegirCaballo;
    @FXML
    private TextField importeApostado;
    @FXML
    private TextField importeBot1, caballoBot1;
    @FXML
    private TextField importeBot2, caballoBot2;
    @FXML
    private TextField importeBot3, caballoBot3;
    private Stage stage;
    @FXML
    private Label labelCarrera;
    @FXML
    private Button botonCarrera;
    @FXML
    private Button botonGenerarBots;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private pantallaCarreraController controller;

    @FXML
    public void initialize() {

        for (CardSuit suit : CardSuit.values()) {
            elegirCaballo.getItems().add(suit.toString());
        }
        juego = new Juego();
        juego.limpiarFicheroDescartes();
        labelCarrera.setText("Configuración de la carrera " + BaseDatos.carrera);
        botonCarrera.setText("Empezar carrera");
        if(BaseDatos.seguirPartida) {
            cargarApuestaCarreraAnterior();
        }

    }

    @FXML
    public void generarBots() {
        juego.jugadores.clear();
        Jugador jugador = new Jugador(Double.parseDouble(importeApostado.getText()), juego.comprobarCaballo(elegirCaballo.getValue()), nombreJugador.getText());
        juego.jugadores.add(jugador);
        BaseDatos.guardarApostante(BaseDatos.carrera, nombreJugador.getText(), 0, Float.parseFloat(importeApostado.getText()), juego.comprobarCaballo(elegirCaballo.getValue()).toString());
        for (int i = 1 ; i <= 3; i++) {
            juego.jugadores.add(juego.crearBot(i));
            juego.bote = juego.bote + juego.jugadores.get(i).getImporteApuesta();
            switch (i) {
                case 1:
                    importeBot1.clear();
                    importeBot1.setText(String.valueOf(juego.jugadores.get(1).getImporteApuesta()));
                    caballoBot1.setText(String.valueOf(juego.jugadores.get(1).getCaballoApuesta()));
                    BaseDatos.guardarApostante(BaseDatos.carrera, "Bot1", 1, Float.parseFloat(importeBot1.getText()), caballoBot1.getText());
                    break;
                case 2:
                    importeBot2.clear();
                    importeBot2.setText(String.valueOf(juego.jugadores.get(2).getImporteApuesta()));
                    caballoBot2.setText(String.valueOf(juego.jugadores.get(2).getCaballoApuesta()));
                    BaseDatos.guardarApostante(BaseDatos.carrera, "Bot2", 1, Float.parseFloat(importeBot2.getText()), caballoBot2.getText());
                    break;
                case 3:
                    importeBot3.clear();
                    importeBot3.setText(String.valueOf(juego.jugadores.get(3).getImporteApuesta()));
                    caballoBot3.setText(String.valueOf(juego.jugadores.get(3).getCaballoApuesta()));
                    BaseDatos.guardarApostante(BaseDatos.carrera, "Bot3", 1, Float.parseFloat(importeBot3.getText()), caballoBot3.getText());
                    break;
            }
        }
    }

    @FXML
    public void empezarCarrera() throws IOException {
        if(BaseDatos.seguirPartida) {
            cargarVistaCarrera();
            cargarVistaCarreraAnterior();
        } else {
            if (datosCorrectos()) {
                cargarVistaCarrera();
            }
        }
    }

    private void cargarVistaCarrera() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(carreraCaballos.class.getResource("views/pantallaCarrera.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 640);
        controller = fxmlLoader.getController();
        controller.setJuego(juego);
        controller.setStage(stage);
        stage.setTitle("Carrera de caballos");
        stage.setScene(scene);
    }

    public boolean datosCorrectos() {
        if (nombreJugador.getText().isEmpty() || elegirCaballo.getValue() == null || importeApostado.getText().isEmpty() || importeBot1.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Todos los campos son obligatorios de rellenar.");
            alert.showAndWait();
            return false;
        } else {
            return true;
        }
    }

    public void cargarApuestaCarreraAnterior() {

        botonCarrera.setText("Continuar carrera");
        nombreJugador.setDisable(true);
        elegirCaballo.setDisable(true);
        importeApostado.setDisable(true);
        importeBot1.setDisable(true);
        caballoBot1.setDisable(true);
        importeBot2.setDisable(true);
        caballoBot2.setDisable(true);
        importeBot3.setDisable(true);
        caballoBot3.setDisable(true);
        botonGenerarBots.setDisable(true);

        List<Apuesta> apuestas = BaseDatos.obtenerApuestasPorCarrera(BaseDatos.carrera);

        nombreJugador.setText(String.valueOf(apuestas.get(0).getNombre()));
        elegirCaballo.setValue(String.valueOf(apuestas.get(0).getCaballo()));
        importeApostado.setText(String.valueOf(apuestas.get(0).getImporte()));

        importeBot1.setText(String.valueOf(apuestas.get(1).getImporte()));
        caballoBot1.setText(apuestas.get(1).getCaballo());

        importeBot2.setText(String.valueOf(apuestas.get(2).getImporte()));
        caballoBot2.setText(apuestas.get(2).getCaballo());

        importeBot3.setText(String.valueOf(apuestas.get(3).getImporte()));
        caballoBot3.setText(apuestas.get(3).getCaballo());
    }

    private void cargarVistaCarreraAnterior() throws IOException {
        int posicionGold = BaseDatos.obtenerUltimoMovimientoCaballo(BaseDatos.carrera, "GOLD");
        int posicionCups = BaseDatos.obtenerUltimoMovimientoCaballo(BaseDatos.carrera, "CUPS");
        int posicionClubs = BaseDatos.obtenerUltimoMovimientoCaballo(BaseDatos.carrera, "CLUBS");
        int posicionSwords = BaseDatos.obtenerUltimoMovimientoCaballo(BaseDatos.carrera, "SWORDS");

        controller.actualizarCaballoEnVista(CardSuit.GOLD,0, posicionGold);
        controller.actualizarCaballoEnVista(CardSuit.CUPS,0, posicionCups);
        controller.actualizarCaballoEnVista(CardSuit.CLUBS,0, posicionClubs);
        controller.actualizarCaballoEnVista(CardSuit.SWORDS,0, posicionSwords);

        juego.filaPenalizacion = BaseDatos.obtenerFilaPenalizacionCarrera(BaseDatos.carrera);
        juego.tablero[juego.MAX_FILAS - 1][0] = null;
        juego.tablero[juego.MAX_FILAS - 1][1] = null;
        juego.tablero[juego.MAX_FILAS - 1][2] = null;
        juego.tablero[juego.MAX_FILAS - 1][3] = null;
        juego.tablero[juego.MAX_FILAS - posicionGold - 1][0] = new FacedCard(CardFace.KNIGHT, CardSuit.GOLD);
        juego.tablero[juego.MAX_FILAS - posicionCups - 1][1] = new FacedCard(CardFace.KNIGHT, CardSuit.CUPS);
        juego.tablero[juego.MAX_FILAS - posicionClubs - 1][2] = new FacedCard(CardFace.KNIGHT, CardSuit.CLUBS);
        juego.tablero[juego.MAX_FILAS - posicionSwords - 1][3] = new FacedCard(CardFace.KNIGHT, CardSuit.SWORDS);

        List<Card> penalizaciones = BaseDatos.obtenerCartasPenalizadoras(BaseDatos.carrera);
        for (int i = 0; i < penalizaciones.size(); i++) {
            if (i < juego.NUM_FILAS_PENALIZACIONES) {
                juego.tablero[i + 2][juego.NUM_COLUMNAS_PENALIZACIONES] = penalizaciones.get(i);
            }
        }
    }

}
