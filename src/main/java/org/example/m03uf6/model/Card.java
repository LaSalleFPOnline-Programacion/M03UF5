package org.example.m03uf6.model;
public abstract class Card {
    protected CardSuit suit;
    protected float value;
    public float getValue() {
        return value;
    }

    protected String toString(String numberOrFace) {
        //return numberOrFace + " of " + suit + ", value " + value;
        return numberOrFace;
    }

    public abstract String getCardCode();

    public abstract String getDescription();

    public CardSuit getCardSuit() {

        return this.suit;
    }

}
