package org.example.m03uf5.model;

/**
 * La clase Jugador representa a un jugador que realiza una apuesta en una carrera de caballos.
 * Cada jugador tiene un importe de apuesta, el caballo por el que apuesta, y su nombre.
 */
public class Jugador {

    private double importeApuesta;
    private CardSuit caballoApuesta;
    private String nombreJugador;

    /**
     * Crea una nueva instancia de {@code Jugador} con el importe de apuesta, el caballo por el que apuesta y el nombre del jugador.
     *
     * @param importeApuesta el importe de la apuesta realizada por el jugador
     * @param caballoApuesta el caballo por el que apuesta el jugador
     * @param nombreJugador el nombre del jugador
     */
    public Jugador(double importeApuesta, CardSuit caballoApuesta, String nombreJugador) {
        this.importeApuesta = importeApuesta;
        this.caballoApuesta = caballoApuesta;
        this.nombreJugador = nombreJugador;
    }

    /**
     * Obtiene el importe de la apuesta realizada por el jugador.
     *
     * @return el importe de la apuesta
     */
    public double getImporteApuesta() {
        return importeApuesta;
    }

    /**
     * Obtiene el caballo por el que el jugador ha apostado.
     *
     * @return el caballo de la apuesta
     */
    public CardSuit getCaballoApuesta() {
        return caballoApuesta;
    }

    /**
     * Obtiene el nombre del jugador.
     *
     * @return el nombre del jugador
     */
    public String getNombreJugador() {
        return nombreJugador;
    }

}