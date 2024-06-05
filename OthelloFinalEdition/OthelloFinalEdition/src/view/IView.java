package view;
/**
 * The `IView` interface defines the methods that should be implemented by any class
 * representing the view in the Othello game.
 */

public interface IView {
    /**
     * Draws the start screen of the game.
     */

    void drawStart();
    /**
     * Draws the main game screen with the current state of the board.
     */
    void drawGame();
    /**
     * Draws the game over screen.
     */
    void drawGameOver();
    /**
     * Displays a message indicating that the black player has won the game.
     */

    void displayBWon();
    /**
     * Displays a message indicating that the white player has won the game.
     */

    void displayWWon();

}
