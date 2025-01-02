package org.example.m03uf6.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.m03uf6.model.BaseDatos;

public class pantallaGanadorController {
    private Stage stage;
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private Label caballoGanador;
    @FXML
    private ImageView imagenCaballoGanador;
    @FXML
    private Label nombreGanadores;
    @FXML
    public void salirJuego() {
        BaseDatos.cerrarConexion();
        stage.close();
    }

    public void setCaballoGanador(String texto) {
        caballoGanador.setText(texto);
    }

    public void setImagenCaballoGanador(String urlImagen) {
        try {
            Image nuevaImagen = new Image(urlImagen);
            imagenCaballoGanador.setImage(nuevaImagen);
        } catch (IllegalArgumentException e) {
            System.err.println("Error al cargar la imagen: " + e.getMessage());
        }
    }

    public void setNombreGanadores(String texto) {
        nombreGanadores.setText(texto);
    }
}


