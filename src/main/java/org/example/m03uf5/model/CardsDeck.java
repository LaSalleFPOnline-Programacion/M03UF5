package org.example.m03uf5.model;
import java.util.ArrayList;
import java.util.Collections;

public class CardsDeck {
    private ArrayList<Card> cardsDeck = new ArrayList<>();
    private int [] num = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private CardSuit[] cardSuits = {CardSuit.GOLD, CardSuit.CLUBS, CardSuit.CUPS, CardSuit.SWORDS};

    /*private CardFace[] cardFaces = {CardFace.JACK, CardFace.KNIGHT, CardFace.KING};*/
    private CardFace[] cardFaces = {CardFace.JACK, CardFace.KING};
    private Card card;
    private ArrayList<Integer> totalCartas ;

    /**
     * Method to create the cards deck every time you want to play
     * Create Cards from two vectors (number and suit) and add them to the deck ArrayList
     */
    public CardsDeck () {
        for (int i = 0; i < num.length; i++) {
            for (int j = 0; j < cardSuits.length; j++) {
                card = new NumeredCard(num[i], cardSuits[j]);
                cardsDeck.add(card);
            }
        }
        for (CardFace face : cardFaces){
            for (int j = 0; j < cardSuits.length; j++) {
                card = new FacedCard(face, cardSuits[j]);
                cardsDeck.add(card);
            }
        }
        totalCartas = new ArrayList<>();

        Collections.shuffle(cardsDeck); /* UNA VEZ CREAMOS LA BARAJA, LA MEZCLAMOS */
    }

    /**
     * Metode per repartir un nova carta aleatoria a la ma
     * @return --> Carta donada
     */
    public Card getCardFromDeck(){
        Card carta;
        int numcarta = comprobarTotalCartas();

        numcarta = 0; // SI LA BARAJA YA ESTÁ BARAJADA, NO TIENE SENTIDO NO COGER EL PRIMER ELEMENTO.
        carta = cardsDeck.get(numcarta) ;
        return carta;
    }

    /**
     * Helper method that returns a random card from Deck that has not been given before
     * @return --> Card not given before
     */
    private int comprobarTotalCartas (){
        boolean existe ;
        int numcarta;
        do{
            existe = false;
            numcarta = (int) (Math.random() * 40 + 1);
            if (totalCartas.isEmpty()){
                existe = false;
            }else {
                for (Integer x : totalCartas) {
                    if (numcarta == x) {
                        //System.out.println("carta"+x+" cartaRandom"+numcarta);
                        existe = true;
                    }
                }
            }
            //System.out.println(numcarta);
        } while(existe);
        totalCartas.add(numcarta - 1);
        return numcarta - 1;
    }

    public void removeCard(int index) {

        cardsDeck.remove(index);

    }

    public void addCard(Card carta) {

        cardsDeck.add(carta);

    }

    public int totalCartas() {

        return cardsDeck.size();

    }

}
