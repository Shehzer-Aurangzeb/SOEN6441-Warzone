package models.Enums;

/**
 * Enum representing different type of cards
 **/
public enum CardType {
    BOMB("Bomb"),
    BLOCKADE("Blockade"),
    AIRLIFT("Airlift"),
    DIPLOMACY("Diplomacy");

    private final String name;

    CardType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
