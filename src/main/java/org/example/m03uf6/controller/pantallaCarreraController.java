package org.example.m03uf6.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import org.example.m03uf6.carreraCaballos;
import org.example.m03uf6.model.BaseDatos;
import org.example.m03uf6.model.Juego;
import org.example.m03uf6.model.Card;
import org.example.m03uf6.model.CardSuit;
import java.io.IOException;
import java.util.Objects;

public class pantallaCarreraController {
    private Juego juego;
    private boolean esFinPartida = false;
    private Stage stage;
    @FXML
    private Label textoCaballoAvanza;
    @FXML
    private Line linea1;
    @FXML
    private Line linea2;
    @FXML
    private Line linea3;
    @FXML
    private Line linea4;
    @FXML
    private Line linea5;
    @FXML
    private ImageView cartaLevantada;

    @FXML
    private void initialize() {
        // Configuración de la línea
        linea1.setStartX(-470);
        linea1.setStartY(0);
        linea1.setEndX(100);
        linea1.setEndY(0);
        linea1.setStroke(Color.RED);
        linea1.setStrokeWidth(5);
        linea1.getStrokeDashArray().addAll(10.0, 10.0);

        linea2.setStartX(-470);
        linea2.setStartY(0);
        linea2.setEndX(100);
        linea2.setEndY(0);
        linea2.setStroke(Color.WHITE);
        linea2.setStrokeWidth(5);
        linea2.getStrokeDashArray().addAll(10.0, 10.0);

        linea3.setStartX(-470);
        linea3.setStartY(0);
        linea3.setEndX(100);
        linea3.setEndY(0);
        linea3.setStroke(Color.WHITE);
        linea3.setStrokeWidth(5);
        linea3.getStrokeDashArray().addAll(10.0, 10.0);

        linea4.setStartX(-470);
        linea4.setStartY(0);
        linea4.setEndX(100);
        linea4.setEndY(0);
        linea4.setStroke(Color.WHITE);
        linea4.setStrokeWidth(5);
        linea4.getStrokeDashArray().addAll(10.0, 10.0);

        linea5.setStartX(-470);
        linea5.setStartY(0);
        linea5.setEndX(100);
        linea5.setEndY(0);
        linea5.setStroke(Color.RED);
        linea5.setStrokeWidth(5);
        linea5.getStrokeDashArray().addAll(10.0, 10.0);
    }
    public void setStage(Stage stage) { this.stage = stage; }
    public void setJuego(Juego juego) {
        this.juego = juego;
    }
    @FXML
    public void levantarCarta() throws IOException {

        if (!esFinPartida) {
            Card carta = juego.levantarCarta();
            String cartaImagenURL = obtenerURLImagen(carta);
            Image cartaImagen = new Image(Objects.requireNonNull(getClass().getResource(cartaImagenURL)).toExternalForm());
            cartaLevantada.setImage(cartaImagen);

            int posicionActual = juego.moverCaballoAdelante(carta);
            BaseDatos.registrarMovimiento(1, carta, carta.getCardSuit().toString() + (posicionActual + 1) );
            actualizarCaballoEnVista(carta.getCardSuit(), posicionActual, posicionActual + 1);
            textoCaballoAvanza.setText("El caballo que avanza es: " + carta.getCardSuit().toString());
            int posicionPenalizacion = juego.chequearPenalizacion();
            if (posicionPenalizacion != 0) {
                CardSuit paloPenalizacion = juego.getCartaTablero(juego.getFilaPenalizacion()+1,4).getCardSuit();
                actualizarCaballoEnVista(paloPenalizacion, posicionPenalizacion, posicionPenalizacion - 1);
                BaseDatos.registrarMovimiento(1, carta, paloPenalizacion.toString() + (posicionPenalizacion - 1) );
                textoCaballoAvanza.setText("El caballo penalizado es: " + carta.getCardSuit().toString());
            }
            esFinPartida = juego.finPartida();
        } else {
            cargarVistaGanador();
        }
        juego.chequearGanador();

    }

    public void actualizarCaballoEnVista(CardSuit suit, int posicionActual, int posicionSiguiente) {
        String idActual = String.format("#%s%d", suit, posicionActual);
        String idSiguiente = String.format("#%s%d", suit, posicionSiguiente);
        ImageView caballoActual = (ImageView) stage.getScene().lookup(idActual);
        ImageView caballoSiguiente = (ImageView) stage.getScene().lookup(idSiguiente);
        if (caballoActual != null) {
            caballoActual.setOpacity(0.1);
        }
        if (caballoSiguiente != null) {
            caballoSiguiente.setOpacity(1.0);
        }
    }

    private String obtenerURLImagen(Card carta) {
        String cardName = carta.toString();
        String cardSuit = carta.getCardSuit().toString();
        return "/org/example/m03uf6/images/" + cardName + "_" + cardSuit + ".png";
    }

    public void cargarVistaGanador() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(carreraCaballos.class.getResource("views/pantallaGanador.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        pantallaGanadorController controller = fxmlLoader.getController();
        controller.setStage(stage);

        if (!juego.quedanCartas()) {
            controller.setCaballoGanador("Fin. No hay ganador");
        } else {
            controller.setCaballoGanador("¡El caballo ganador es " + juego.obtenerCaballoGanador().toString() + "!");
            String url = Objects.requireNonNull(getClass().getResource("/org/example/m03uf6/images/KNIGHT_" + juego.obtenerCaballoGanador().toString() + ".png")).toExternalForm();
            controller.setImagenCaballoGanador(url);
            controller.setNombreGanadores(juego.obtenerGanador(juego.obtenerCaballoGanador()));

        }
        stage.setScene(scene);
    }

}
