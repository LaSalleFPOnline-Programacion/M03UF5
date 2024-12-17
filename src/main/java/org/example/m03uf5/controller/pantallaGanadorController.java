package org.example.m03uf5.controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;
public class pantallaGanadorController {
    private Stage stage;
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    @FXML
    public void salirJuego() {
        stage.close();
    }

}


