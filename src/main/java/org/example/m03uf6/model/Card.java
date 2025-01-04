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

    /**
     * Crea una instancia de {@code Card} a partir de una cadena en formato específico.
     * <p>
     * El formato esperado de la cadena es {@code "valor_palo"}, donde:
     * <ul>
     *   <li>{@code valor} puede ser un número para una {@code NumeredCard} o un valor de {@code CardFace} para una {@code FacedCard}.</li>
     *   <li>{@code palo} debe coincidir con un valor de {@code CardSuit}.</li>
     * </ul>
     * </p>
     *
     * @param carta Una cadena que representa la carta en el formato {@code "valor_palo"}.
     * @return Una instancia de {@code NumeredCard} si el valor es un número,
     *         o una instancia de {@code FacedCard} si el valor es un nombre válido de {@code CardFace}.
     *         Devuelve {@code null} si la cadena no es válida o no puede ser interpretada correctamente.
     * @throws IllegalArgumentException Si el formato de la cadena es inválido.
     * @throws NullPointerException Si la cadena proporcionada es nula.
     */
    public static Card crearCard(String carta) {
        try {
            String[] parts = carta.split("_");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Formato inválido: " + carta);
            }

            String firstPart = parts[0]; // Valor (número o face)
            CardSuit suit = CardSuit.valueOf(parts[1]); // Suit (palo)

            try {
                int num = Integer.parseInt(firstPart);
                return new NumeredCard(num, suit);
            } catch (NumberFormatException e) {
                CardFace face = CardFace.valueOf(firstPart);
                return new FacedCard(face, suit);
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            System.err.println("Error al crear Card: " + e.getMessage());
            return null;
        }
    }

}
