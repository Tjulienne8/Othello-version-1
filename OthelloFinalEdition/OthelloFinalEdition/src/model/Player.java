package model;
/**
 * The `Player` class represents a player in the Othello game.
 * Each player has a type (black or white) and a score that represents their current game score.
 */

public class Player  {
    /** The type of the player (BLACK or WHITE). */
    private final PlayerType type;
    /** The score of the player in the current game. */
    private int score;
    /**
     * Constructs a new player with the specified type and initializes the score to 2.
     *
     * @param type The type of the player (BLACK or WHITE).
     */
    public Player(PlayerType type) {
        this.type = type;
        this.score = 2;
    }
    /**
     * Gets the type of the player.
     *
     * @return The type of the player (BLACK or WHITE).
     */

    public PlayerType getType() {
        return type;
    }
    /**
     * Gets the current score of the player.
     *
     * @return The current score of the player.
     */
    public int getScore() {
        return score;
    }
    /**
     * Increments the player's score by 1.
     */
    public void incrementScore() {
        this.score += 1;
    }
    /**
     * Sets the score of the player to the specified value.
     *
     * @param score The new score of the player.
     */

    public void setScore(int score) {
        this.score = score;
    }
    /**
     * Gets the opponent's player type.
     *
     * @return The player type of the opponent.
     */

    public PlayerType opponent() {
        return this.type == PlayerType.BLACK ? PlayerType.WHITE : PlayerType.BLACK;
    }

   
}
