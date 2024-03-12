package models.Card;

import models.Enums.CardType;

/**
 * Represents a card in the game, which can be of various types as defined in the {@link CardType} enum.
 * Each card is associated with a specific type that determines its abilities or effects when used.
 */
public class Card {
    /**
     * The type of the card, which determines its functionality within the game.
     */
    private final CardType type;

    /**
     * Constructs a new card with the specified type.
     *
     * @param type The type of the card, as defined in the {@link CardType} enum.
     */
    public Card(CardType type) {
        this.type = type;
    }

    /**
     * Retrieves the type of this card.
     *
     * @return The {@link CardType} of this card, indicating its specific category or ability.
     */
    public CardType getType() {
        return type;
    }
}
