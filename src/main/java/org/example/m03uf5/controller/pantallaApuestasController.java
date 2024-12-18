package org.example.m03uf5.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.m03uf5.carreraCaballos;
import org.example.m03uf5.model.Juego;
import org.example.m03uf5.model.Jugador;
import org.example.m03uf5.model.CardSuit;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;

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

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {

        for (CardSuit suit : CardSuit.values()) {
            elegirCaballo.getItems().add(suit.toString());
        }
        juego = new Juego();
        juego.limpiarFicheroDescartes();
    }

    @FXML
    public void generarBots() {
        juego.jugadores.clear();
        Jugador jugador = new Jugador(Double.parseDouble(importeApostado.getText()), juego.comprobarCaballo(elegirCaballo.getValue()), " Jugador 1");
        juego.jugadores.add(jugador);
        for (int i = 1 ; i <= 3; i++) {
            juego.jugadores.add(juego.crearBot(i));
            juego.bote = juego.bote + juego.jugadores.get(i).getImporteApuesta();
            switch (i) {
                case 1:
                    importeBot1.clear();
                    importeBot1.setText(String.valueOf(juego.jugadores.get(i).getImporteApuesta()));
                    caballoBot1.setText(String.valueOf(juego.jugadores.get(i).getCaballoApuesta()));
                    break;
                case 2:
                    importeBot2.setText(String.valueOf(juego.jugadores.get(i).getImporteApuesta()));
                    caballoBot2.setText(String.valueOf(juego.jugadores.get(i).getCaballoApuesta()));
                    break;
                case 3:
                    importeBot3.setText(String.valueOf(juego.jugadores.get(i).getImporteApuesta()));
                    caballoBot3.setText(String.valueOf(juego.jugadores.get(i).getCaballoApuesta()));
                    break;
            }
        }
    }

    @FXML
    public void empezarCarrera() throws IOException {
        if(datosCorrectos()) {
            FXMLLoader fxmlLoader = new FXMLLoader(carreraCaballos.class.getResource("views/pantallaCarrera.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 640);
            pantallaCarreraController controller = fxmlLoader.getController();
            controller.setJuego(juego);
            controller.setStage(stage);
            stage.setTitle("Carrera de caballos");
            stage.setScene(scene);
        }
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

}
