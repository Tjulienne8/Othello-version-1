package view;

import controller.Controller;
import controller.IController;
import processing.core.PApplet;
import processing.core.PImage;
import processing.event.KeyEvent;
/**
 * The graphical view class for the Othello game.
 * Implements the {@link IView} interface and extends {@link PApplet} for graphical rendering.
 */
public class OthelloView extends PApplet implements IView {
    /** The size of the game board. */
    public static final int BOARD_SIZE = 400;
    /** The size of each tile on the game board. */
    public static final int TITLE_SIZE = BOARD_SIZE / 8;
    /** Image displayed at the start of the game. */
    /** Image displayed when the game is over. */


    private PImage imageStart, imageGameOver;
    /** The controller managing the game logic. */

    private IController controller;
    /**
     * Configures the size of the window.
     */

    public void settings() {
        size(BOARD_SIZE + 100, BOARD_SIZE + 50);
    }
    /**
     * Initializes the graphical view by creating a controller and loading images.
     */
    public void setup() {
        this.controller = new Controller(this);
        imageStart = loadImage("images/Othello_Start.jpg");
        imageGameOver = loadImage("images/GameOver_2.jpg");
    }
    /**
     * Called continuously to update the graphical display based on the game state.
     */

    @Override
    public void draw() {
        this.controller.nextFrame();
    }
    /**
     * Handles key presses and passes them to the controller.
     *
     * @param event The key event object containing information about the key press.
     */

    /* @Override
    public void keyPressed(KeyEvent event) {
        this.controller.userInput(event.getKey(), event.getKeyCode());
    } */

    public void keyPressed(KeyEvent event) {
        this.controller.userInput(event);
    }
    /**
     * Handles mouse clicks and passes the coordinates to the controller.
     */
    @Override
    public void mousePressed() {
        this.controller.userInput(mouseX, mouseY);
    }
    /**
     * Displays the start screen of the game.
     */
    @Override
    public void drawStart() {
        image(imageStart, 0, 0, width, height);
        fill(0);
        textSize(60);
        textAlign(CENTER);
        text("Press 'P' to play", (float) ((width / 2)), (float) ((height / 2) + 50));
    }
    /**
     * Renders the game board and pieces during gameplay.
     */
    @Override
    public void drawGame() {
        background(0, 155, 0);

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                int xPos = x * TITLE_SIZE;
                int yPos = y * TITLE_SIZE;
                char squareState = this.controller.getGame().getBoard().getCellState(x, y);

                // Dessiner les rectangles pour les cellules du plateau
                if (this.controller.getGame().isValidMove(x + "," + y, this.controller.getGame().getCurrentPlayer())) {
                    fill(100, 100, 255); // Couleur pour les coups valides
                } else {
                    if (squareState == 'B') {
                        fill(0); // Pièce du joueur noir
                    } else if (squareState == 'W') {
                        fill(255); // Pièce du joueur blanc
                    } else {
                        fill(0, 150, 0); // Cellule vide
                    }
                }

                rect(xPos, yPos, TITLE_SIZE, TITLE_SIZE);
            }
        }

        drawScore();
    }
    /**
     * Draws the scores of both players on the screen.
     */

    private void drawScore() {
        fill(0);
        textSize(16);
        textAlign(LEFT);
        text("White Player score: " + this.controller.getGame().getWhitePlayer().getScore(), 20, height - 20);
        textAlign(RIGHT);
        text("Black Player score: " + this.controller.getGame().getBlackPlayer().getScore(), width - 120, height - 20);
    }
    /**
     * Displays the game over screen.
     */

    @Override
    public void drawGameOver() {
        image(imageGameOver, 0, 0, width, height);

    }
    /**
     * Displays a message when the black player wins.
     */

    @Override
    public void displayBWon() {
        fill(255, 255, 0);
        textAlign(CENTER);
        textSize(40);
        text(" Black Player YOU WON!!!", width / 2, height - 100);
        fill(255, 0, 0);
        text("Press 'P' to replay", width / 2, height - 40);
        System.out.println(222);
    }

    @Override
    public void displayWWon() {
        fill(255, 255, 0);
        textAlign(CENTER);
        textSize(40);
        text(" white Player YOU WON!!!", width / 2, height - 100);
        fill(255, 0, 0);
        text("Press 'P' to replay", width / 2, height - 40);
    }

   

    


}
