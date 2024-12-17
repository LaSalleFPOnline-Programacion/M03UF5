package org.example.m03uf5.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class pantallaGanadorController {
    private Stage stage;
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private Label caballoGanador;
    @FXML
    public void salirJuego() {
        stage.close();
    }

    public void setCaballoGanador(String nombreCaballo) {
        caballoGanador.setText("¡El caballo ganador es: " + nombreCaballo + "!");
    }

}


