package org.example.m03uf6;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.m03uf6.controller.*;

import java.io.IOException;

public class carreraCaballos extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(carreraCaballos.class.getResource("views/pantallaInicio.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Carrera de caballos");
        stage.setScene(scene);
        pantallaInicioController controller = fxmlLoader.getController();
        controller.setStage(stage);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}