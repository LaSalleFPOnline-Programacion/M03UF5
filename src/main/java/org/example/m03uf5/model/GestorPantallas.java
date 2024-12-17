package org.example.m03uf5.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class GestorPantallas {

    public static Scene loadScene(String fxmlFileName) {
        try {
            FXMLLoader loader = new FXMLLoader(GestorPantallas.class.getResource(fxmlFileName));
            return new Scene(loader.load());
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar la pantalla: " + fxmlFileName, e);
        }
    }
}
