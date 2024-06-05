package model;

import controller.GameState;
/**
 * The `OthelloGame` class represents the state and logic of an Othello game.
 * It manages the game board, players, and game state, and provides methods to interact with the game.
 */


public class OthelloGame {
    /** The current state of the Othello game (START, PLAYING, GAMEOVER). */
    private GameState state;
    /** The game board representing the state of the Othello board. */
    private Board board;
    /** The current player making a move. */
    private Player currentPlayer;
    /** The player controlling the black pieces. */
    private Player black;
    /** The player controlling the white pieces. */
    private Player white;
    /**
     * Constructs a new Othello game, initializes players, board, and starts a thread for player actions.
     */
    public OthelloGame() {
        this.state = GameState.START;
        this.black = new Player(PlayerType.BLACK);
        this.white = new Player(PlayerType.WHITE);
        this.currentPlayer = black;
        this.board = new Board();
        Threads pc = new Threads(this);
        pc.start();
    }
    /**
     * Gets the current state of the Othello game.
     *
     * @return The current state of the game (START, PLAYING, GAMEOVER).
     */

    public GameState getState() {
        return state;
    }
    /**
     * Sets the state of the Othello game.
     *
     * @param state The new state of the game (START, PLAYING, GAMEOVER).
     */

    public void setState(GameState state) {
        this.state = state;
    }
    /**
     * Gets the player who is currently making a move.
     *
     * @return The current player.
     */

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    /**
     * Gets the player controlling the black pieces.
     *
     * @return The player controlling the black pieces.
     */

    public Player getBlackPlayer() {
        return black;
    }
    /**
     * Gets the player controlling the white pieces.
     *
     * @return The player controlling the white pieces.
     */

    public Player getWhitePlayer() {
        return white;
    }
    /**
     * Switches the current player to the opponent player.
     */

    public void switchPlayer() {
        currentPlayer = currentPlayer.getType() == PlayerType.BLACK ? white : black;
    }
    /**
     * Sets the current player to the specified player.
     *
     * @param currentPlayer The new current player.
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
    /**
     * Gets the game board.
     *
     * @return The game board.
     */
    public Board getBoard() {
        return board;
    }
    /**
     * Checks if the white player has won the game.
     *
     * @return True if the white player has won, otherwise false.
     */

    public boolean playerWWon() {
        // char winner = checkWinner();
        if (white.getScore() > black.getScore()) {
           // System.out.println("Le joueur blanc a gagner !");
            return true;
        }
        return false;
    }
    /**
     * Checks if the black player has won the game.
     *
     * @return True if the black player has won, otherwise false.
     */

    public boolean playerBWon() {
        // char winner= checkWinner();
        if (black.getScore() > white.getScore()) {
            //System.out.println("le joueur noir a gagner");
            return true;
        }
        return false;
    }
    /**
     * Checks if the move at the specified position is valid for the given player.
     *
     * @param position The position of the move.
     * @param player   The player making the move.
     * @return True if the move is valid, otherwise false.
     */

    public boolean isValidMove(String position, Player player) {
        return this.board.isValidMove(position, player);
    }
    /**
     * Makes a move at the specified position for the given player.
     *
     * @param position The position of the move.
     * @param player   The player making the move.
     */
    public void makeMove(String position, Player player) {
        if (isValidMove(position, player)) {
            this.board.makeMove(position, player);
            // Update the scores after each move
            checkWinner();

            // ... (existing code)
        } else {
            //System.out.println("Invalid move!");
        }
    }
    /**
     * Checks if the Othello game is over.
     *
     * @return True if the game is over, otherwise false.
     */

    public boolean gameOver() {
        for (int x = 0; x < Board.BOARD_LENGTH; x++) {
            for (int y = 0; y < Board.BOARD_WIDTH; y++) {
                String position = x + "," + y;
                if (isValidMove(position, this.black) || isValidMove(position, this.white)) {
                    return false; // Il y a un coup valide, la partie continue
                }
            }
        }
        // Aucun coup valide pour aucun joueur, la partie est terminée
        this.checkWinner();
        this.setState(GameState.GAMEOVER);
        return true;
    }
    /**
     * Checks the winner of the game based on the scores.
     */

    public void checkWinner() {
        int blackScore = 0; int whiteScore = 0;

        // Réinitialiser les scores
        black.setScore(0); white.setScore(0);

        for (String key : board.board().keySet()) {
            char squareState = board.board().get(key);
            if (squareState == 'B') {
                blackScore++;
            } else if (squareState == 'W') {
                whiteScore++;
            }
        }

        // Update scores
        black.setScore(blackScore);
        white.setScore(whiteScore);

        // Optionally, you can print the scores here for debugging
        System.out.println("Black score: " + blackScore);
        System.out.println("White score: " + whiteScore);
    }

}
