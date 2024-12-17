package org.example.m03uf5.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.m03uf5.carreraCaballos;

import java.io.IOException;

public class pantallaInicioController {
    private Stage stage;
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @FXML
    public void empezarJuego() throws IOException {

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


