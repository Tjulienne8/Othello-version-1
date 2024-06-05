package controller;

import model.OthelloGame;
import processing.event.KeyEvent;
import view.IView;
import view.OthelloView;

/**
 * The Controller class manages the interaction between the OthelloGame model
 * and the user interface (view).
 * It handles the game state transitions, user input processing, and updates the
 * view accordingly.
 *
 * <p>
 * The controller implements the {@link IController} interface, providing
 * methods to control the flow of the game.
 * </p>
 *
 * <p>
 * Example Usage:
 * </p>
 * 
 * <pre>{@code
 * // Create a new Controller with a specified view
 * IView view = // ... initialize the view
 * Controller controller = new Controller(view);
 *
 * // Advance to the next frame of the game
 * controller.nextFrame();
 *
 * // Process user input (e.g., key presses or mouse clicks)
 * controller.userInput('a', KeyEvent.VK_A);
 * }</pre>
 *
 * @author Tchenou
 */

public class Controller implements IController {
    private OthelloGame game;
    private IView view;

    /**
     * Constructs a Controller with the specified view.
     *
     * @param view The view to be associated with the controller.
     */
    public Controller(IView view) {
        this.game = new OthelloGame();
        this.view = view;
    }

    /**
     * Advances the game to the next frame based on its current state.
     * Draws the appropriate view for the current game state.
     */
    @Override
    public void nextFrame() {
        switch (this.game.getState()) {
            case START -> {
                this.view.drawStart();
            }
            case PLAYING -> {
                this.view.drawGame();
            }
            case GAMEOVER -> {
                this.view.drawGameOver();
                if (this.game.playerBWon()) {
                    this.view.displayBWon();
                } else if (this.game.playerWWon()) {
                    this.view.displayWWon();
                } else {
                    // Laissez vide si nécessaire
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + this.game.getState());
        }
    }

    /**
     * Processes user input for key events.
     * Initiates the game if the state is START and the Enter key is pressed.
     *
     * @param event The code representing the key.
     */

    @Override
    public void userInput(KeyEvent event) {
        switch (this.game.getState()) {
            case START:
                if (event.getKey() == 'p' || event.getKey() == 'P') {
                    this.game.setState(GameState.PLAYING);
                }
                break;
            case PLAYING:
                if (event.getKey() == 'g' || event.getKey() == 'G') {
                    this.game.setState(GameState.GAMEOVER);
                }
                break;
            case GAMEOVER:
                if (event.getKey() == 'p' || event.getKey() == 'P') {
                    this.game = new OthelloGame();
                    this.view.drawStart();
                    this.game.setState(GameState.PLAYING);
                }
                break;
        }
    }

    /**
     * Processes user input for mouse events.
     * Handles player moves during the PLAYING state and checks for game over
     * conditions.
     *
     * @param x The x-coordinate of the mouse click.
     * @param y The y-coordinate of the mouse click.
     */

    @Override
    public void userInput(int x, int y) {
        if (this.game.getState() == GameState.PLAYING && !this.game.gameOver()) {
            int newX = x / OthelloView.TITLE_SIZE;
            int newY = y / OthelloView.TITLE_SIZE;
            String position = newX + "," + newY;

            if (this.game.isValidMove(position, this.game.getCurrentPlayer())) {
                this.game.makeMove(position, this.game.getCurrentPlayer());
                this.game.switchPlayer();

                // Vérifie si la partie est terminée après chaque mouvement
                if (this.game.gameOver()) {
                    this.game.checkWinner();
                    this.game.setState(GameState.GAMEOVER);
                }
            }
        }
    }

    @Override
    public void newGame() {

    }

    /**
     * Gets the current OthelloGame instance associated with the controller.
     *
     * @return The OthelloGame instance.
     */

    @Override
    public OthelloGame getGame() {
        return this.game;
    }
}
