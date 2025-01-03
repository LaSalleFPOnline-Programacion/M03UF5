package org.example.m03uf6.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;
import org.example.m03uf6.controller.pantallaCarreraController;
/**
 * Clase principal para gestionar el juego de la carrera de caballos.
 */
public class Juego {
    private final int ACORTAR_TABLERO = 0;
    private final int MAX_FILAS = 11 - ACORTAR_TABLERO;
    private final int MAX_COLUMNAS = 5;
    private final int NUM_FILAS_PENALIZACIONES = 8 - ACORTAR_TABLERO;
    private final int NUM_COLUMNAS_PENALIZACIONES = 4;
    private final int PRIMERA_CARTA_ALEATORIA = 0;
    private int filaPenalizacion = MAX_FILAS - 2;
    private Card[][] tablero;
    public ArrayList<Jugador> jugadores = new ArrayList<>();
    final int MIN_JUGADORES = 1, MAX_JUGADORES = 4;
    final int MIN_APUESTA = 1, MAX_APUESTA = 1000;
    public double bote;
    public CardsDeck baraja = new CardsDeck();
    private CardsDeck descartes = new CardsDeck();
    Scanner input = new Scanner(System.in);

    /**
     * Constructor de la clase Juego. Inicializa el tablero, la baraja, y los jugadores.
     */
    public Juego() {
        tablero = new Card[MAX_FILAS][MAX_COLUMNAS];
        inicializaTablero();
        if(BaseDatos.seguirPartida) {
            // Cargar apuestas
            // Buscar posiciones
            // Pintar posiciones
        } else {
            BaseDatos.insertarCartasEnBaraja(BaseDatos.carrera, baraja);
            inicializaBaraja();
        }
    }

    /**
     * Inicializa el tablero con las posiciones iniciales de los caballos.
     */
    public void inicializaTablero() {

        tablero[MAX_FILAS-1][0] = new FacedCard(CardFace.KNIGHT, CardSuit.GOLD);
        tablero[MAX_FILAS-1][1] = new FacedCard(CardFace.KNIGHT, CardSuit.CUPS);
        tablero[MAX_FILAS-1][2] = new FacedCard(CardFace.KNIGHT, CardSuit.CLUBS);
        tablero[MAX_FILAS-1][3] = new FacedCard(CardFace.KNIGHT, CardSuit.SWORDS);

    }

    /**
     * Inicializa la baraja y coloca cartas aleatorias en las posiciones de penalización.
     */
    public void inicializaBaraja() {

        for (int i = 2; i < NUM_FILAS_PENALIZACIONES + 2; i++) {
            Card carta = this.baraja.getCardFromDeck();
            tablero[i][NUM_COLUMNAS_PENALIZACIONES] = carta;
            BaseDatos.eliminarCartaBaraja(BaseDatos.carrera, carta);
            BaseDatos.insertarCartaEnDescartes(BaseDatos.carrera, carta);
            this.baraja.removeCard(PRIMERA_CARTA_ALEATORIA);
        }
    }

    /**
     * Muestra el tablero de juego en su estado actual.
     */
    public void mostrarTablero() {
        System.out.println("==========================================================================");
        for(int i = 0; i < MAX_FILAS; i++){
                System.out.print("|");
                for(int j = 0; j < MAX_COLUMNAS; j++) {
                    if (tablero[i][j] != null) {
                        String caballo = tablero[i][j].getCardCode() + "            ";
                        System.out.print(caballo.substring(0, 12) + "|");
                    }
                    else
                        System.out.print("            |");
                }
                if (i == 0) System.out.print(" LLEGADA ");
                if (i == 10) System.out.print(" SALIDA");
                System.out.println();
            }
        System.out.println("==========================================================================");
    }

    /**
     * Comprueba si el palo indicado es válido y lo devuelve.
     *
     * @param palo El nombre del palo a comprobar.
     * @return El palo correspondiente si es válido, null si no lo es.
     */
    public CardSuit comprobarCaballo(String palo) {

        for (CardSuit type : CardSuit.values()) {
            if (type.name().equals(palo)) {
                return type;
            }
        }
        return null;
    }

    /**
     * Solicita el número de jugadores y lo valida.
     *
     * @return El número de jugadores en el rango permitido.
     */
    public int getNumeroJugadores() {

        int numeroJugadores;

        do { /* MIENTRAS EL NÚMERO DE JUGADORES NO SEA CORRECTO, NO SE PROSIGUE */
            System.out.println("Indique el número de jugadores (1 - 4):");
            if (input.hasNextInt()) {
                numeroJugadores = input.nextInt();
                if (numeroJugadores >= MIN_JUGADORES && numeroJugadores <= MAX_JUGADORES) {
                    break;
                }
            }
            input.nextLine();
        } while (true);
        return numeroJugadores;
    }

    /**
     * Crea un jugador solicitando y validando su apuesta y el palo del caballo.
     *
     * @param idJugador Número del jugador.
     * @return El jugador creado.
     */
    public Jugador crearJugador(int idJugador) {

        double importeApuesta;
        CardSuit caballoApuesta;
        String leerCaballoApuesta;

        do { /* MIENTRAS EL IMPORTE NO SEA CORRECTO, NO SE PROSIGUE */
            System.out.println("Jugador " + idJugador + ", indique el importe de la apuesta (1 - 1000):");
            if (input.hasNextDouble()) {
                importeApuesta = input.nextDouble();
                input.nextLine();
                if (importeApuesta >= MIN_APUESTA && importeApuesta <= MAX_APUESTA) {
                    break;
                }
            }
        } while (true);
        do { /* MIENTRAS EL PALO NO SEA CORRECTO, NO SE PROSIGUE */
            System.out.println("Jugador " + idJugador + ", indique el palo del caballo de la apuesta (GOLD, SWORDS, CUPS, CLUBS):");
            leerCaballoApuesta = input.nextLine().toUpperCase();
            caballoApuesta = comprobarCaballo(leerCaballoApuesta);
            if (caballoApuesta != null) {
                break;
            }
        } while (true);
        return new Jugador(importeApuesta, caballoApuesta, "Jugador " + idJugador);
    }

    /**
     * Crea un jugador bot y añadiendo su apuesta y el palo del caballo aleatoriamente.
     *
     * @param idBot Número del jugador bot.
     * @return El jugador bot creado.
     */
    public Jugador crearBot(int idBot) {

        Random random = new Random();

        double importeApuesta = MIN_APUESTA + random.nextInt(MAX_APUESTA - MIN_APUESTA + 1);
        CardSuit[] palos = CardSuit.values();
        CardSuit caballoApuesta = palos[random.nextInt(palos.length)];

        System.out.println("Bot " + idBot + " ha realizado una apuesta de " + importeApuesta + " en el palo " + caballoApuesta + ".");
        return new Jugador(importeApuesta, caballoApuesta, "Bot " + idBot);
    }

    /**
     * Saca una carta de la baraja y la añade a la pila de descartes.
     *
     * @return La carta extraída.
     */
    public Card levantarCarta() {

        Card carta = baraja.getCardFromDeck();
        descartes.addCard(carta);
        escribirDescarteEnFichero(carta);
        BaseDatos.eliminarCartaBaraja(BaseDatos.carrera, carta);
        BaseDatos.insertarCartaEnDescartes(BaseDatos.carrera, carta);
        baraja.removeCard(PRIMERA_CARTA_ALEATORIA);

        return carta;
    }

    /**
     * Guarda la información de la carta descartada en un fichero de texto.
     * @param carta
     */
    private void escribirDescarteEnFichero(Card carta) {
        String rutaFichero = "descartes.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaFichero, true))) {
            writer.write("Carta descartada: " + carta.getDescription());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo de descartes: " + e.getMessage());
        }
    }

    /**
     * Vacía el contenido del fichero de texto.
     */
    public void limpiarFicheroDescartes() {
        String rutaFichero = "descartes.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaFichero))) {
            writer.write("");
        } catch (IOException e) {
            System.err.println("Error al limpiar el archivo de descartes: " + e.getMessage());
        }
    }

    /**
     * Mueve un caballo hacia adelante si su palo coincide con el de la carta proporcionada.
     *
     * @param carta La carta que determina el caballo a mover.
     */

    private pantallaCarreraController carreraController; // Referencia al controlador

    public void setCarreraController(pantallaCarreraController carreraController) {
        this.carreraController = carreraController;
    }
    public int moverCaballoAdelante(Card carta) {

        for(int i = MAX_FILAS - 1; i > 0; i--) {
            for(int j = 0; j < MAX_COLUMNAS - 1; j++) {
                if(tablero[i][j] != null) {
                    if(tablero[i][j].suit == carta.suit) {
                        tablero[i - 1][j] = tablero[i][j];
                        tablero[i][j] = null;
                        System.out.println();
                        System.out.println("CARRERA DE CABALLOS --- PREMIO: " + bote);
                        System.out.println(" Carta descubierta: " + carta.getDescription());
                        mostrarTablero();
                        return (MAX_FILAS - i - 1);
                    }
                }
            }
        }
        return 0;
    }

    /**
     * Mueve un caballo hacia atrás si su palo coincide con el palo penalizado.
     *
     * @param paloPenalizado El palo del caballo a penalizar.
     */
    public int moverCaballoAtras(CardSuit paloPenalizado) {
        for (int i = MAX_FILAS - 1; i > 0; i--) {
            for (int j = 0; j < MAX_COLUMNAS - 1; j++) {
                if (tablero[i][j] != null && tablero[i][j].suit == paloPenalizado) {
                    tablero[i + 1][j] = tablero[i][j];
                    tablero[i][j] = null;
                    return (MAX_FILAS - i - 1);
                }
            }
        }
        return 0;
    }

    /**
     * Verifica si se aplica una penalización en la fila de penalización y mueve el caballo afectado.
     */
    public int chequearPenalizacion() {

        if (tablero[filaPenalizacion][0] == null && tablero[filaPenalizacion][1] == null &&
            tablero[filaPenalizacion][2] == null && tablero[filaPenalizacion][3] == null &&
            tablero[filaPenalizacion+1][0] == null && tablero[filaPenalizacion+1][1] == null &&
            tablero[filaPenalizacion+1][2] == null && tablero[filaPenalizacion+1][3] == null) {
            Card carta = tablero[filaPenalizacion][4];
            if (carta != null) {
                System.out.println();
                System.out.println("CARRERA DE CABALLOS --- PREMIO: " + bote);
                System.out.println("Penalización con carta: " + carta.getCardCode());
                int posicionPenalizacion = moverCaballoAtras(carta.suit);
                filaPenalizacion--;
                mostrarTablero();
                return posicionPenalizacion; /*MAX_FILAS - filaPenalizacion - 1;*/
            }
        }
        return 0;
        /*System.out.println("Pulse una tecla para continuar...");
        input.nextLine();*/

    }

    /**
     * Verifica si hay un ganador comprobando la primera fila del tablero.
     *
     * @return True si hay un caballo en la primera fila, false en caso contrario.
     */
    public boolean hayGanador() {
        if (tablero[0][0] == null && tablero[0][1] == null && tablero[0][2] == null && tablero[0][3] == null)
            return false;
        else
            return true;
    }

    /**
     * Verifica si la partida ha terminado, es decir, si un caballo ha alcanzado la meta.
     *
     * @return True si la partida ha terminado, false si aún continúa.
     */
    public boolean finPartida(){
        return (baraja.totalCartas() == 0 || hayGanador());
    }

     /**
      * Verifica el ganador de la partida evaluando el caballo ganador en el tablero.
      *
      *  Si no quedan cartas en la baraja, es el  fin de la partida sin ganador.
      *  En caso contrario, determina el caballo ganador basado en el primer caballo en la
      *  fila superior del tablero y verifica los jugadores que han apostado por él.
      *
      *  Imprime el caballo ganador.
      *  Imprime los nombres de los jugadores que han ganado.
      *  Calcula y reparte el bote entre los jugadores ganadores.
      *  Si ningún jugador ha apostado por el caballo ganador, lo indica.
      */
    public void chequearGanador() {

        if (!quedanCartas()) {
            System.out.println("Fin. No hay ganador.");
        } else {
            CardSuit caballoGanador = obtenerCaballoGanador();
            System.out.println("¡El caballo ganador es: " + caballoGanador + "!");
            boolean hayGanador = false;
            int totalGanadores = 0;
            for (Jugador jugador : jugadores) {
                if (jugador.getCaballoApuesta() == caballoGanador) {
                    System.out.println(jugador.getNombreJugador() + " ha ganado");
                    totalGanadores++;
                    hayGanador = true;
                }
            }
            if (!hayGanador) {
                System.out.println("Ningún jugador apostó por el caballo ganador.");
            } else {
                System.out.println("Cada ganador se lleva " + bote / totalGanadores);
            }
        }

    }

    public boolean quedanCartas() {
        return baraja.totalCartas() > 0;
    }

    public CardSuit obtenerCaballoGanador() {
        for (int j = 0; j < MAX_COLUMNAS - 1; j++) {
            if (tablero[0][j] != null) {
                return tablero[0][j].suit;
            }
        }
        return null;
    }

        public String obtenerGanador(CardSuit caballoGanador) {
        boolean hayGanador = false;
        int totalGanadores = 0;
        StringBuilder ganadores = new StringBuilder(); // StringBuilder para construir la cadena

        for (Jugador jugador : jugadores) {
            if (jugador.getCaballoApuesta() == caballoGanador) {
                ganadores.append(jugador.getNombreJugador()).append(" ha ganado.\n"); // Concatenar nombre
                totalGanadores++;
                hayGanador = true;
            }
        }

        if (!hayGanador) {
            return "Ningún jugador apostó por el caballo ganador.";
        } else {
            // Añadir el bote al mensaje final
            ganadores.append("Cada ganador se lleva ").append(bote / totalGanadores).append(" puntos.");
            return ganadores.toString(); // Convertir StringBuilder a String
        }
    }

    public Card getCartaTablero(int i, int j) {
        return this.tablero[i][j];
    }

    public int getFilaPenalizacion() {
        return this.filaPenalizacion;
    }
}