package org.example.m03uf6.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.example.m03uf6.carreraCaballos;
import org.example.m03uf6.model.BaseDatos;

import java.io.IOException;
import java.util.Optional;

public class pantallaInicioController {
    private Stage stage;
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @FXML
    public void empezarJuego() throws IOException {

        int partidaPendienteId = BaseDatos.partidaPendiente();

        if (partidaPendienteId > 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Partida pendiente");
            alert.setHeaderText("Se ha detectado una partida pendiente.");
            alert.setContentText("¿Desea continuar con la partida pendiente o anularla?");

            ButtonType continuarButton = new ButtonType("Continuar");
            ButtonType anularButton = new ButtonType("Anular");
            ButtonType cancelarButton = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(continuarButton, anularButton, cancelarButton);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent()) {
                if (result.get() == continuarButton) {
                    BaseDatos.carrera = partidaPendienteId;
                    BaseDatos.seguirPartida = true;
                } else if (result.get() == anularButton) {
                    BaseDatos.anularPartida(partidaPendienteId);
                    BaseDatos.carrera = BaseDatos.crearNuevaPartida();
                } else {
                    return;
                }
            } else {
                return;
            }
        } else {
            BaseDatos.carrera = BaseDatos.crearNuevaPartida();
        }

        FXMLLoader fxmlLoader = new FXMLLoader(carreraCaballos.class.getResource("views/pantallaApuestas.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        pantallaApuestasController controller = fxmlLoader.getController();
        controller.setStage(stage);

        stage.setTitle("Apuestas");
        stage.setScene(scene);
    }

    @FXML
    public void salirJuego() {
        stage.close();
    }

}


