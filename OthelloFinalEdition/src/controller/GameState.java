package controller;
/**
 * The `GameState` enum represents the possible states of an Othello game.
 * It includes states for the start of the game, during gameplay, and when the game is over.
 */
public enum GameState {
    /**
     * The game is in the initial state, waiting to start.
     */
    START,

    /**
     * The game is currently in progress, with players making moves.
     */
    PLAYING,
    /**
     * The game is over, and no more moves can be made.
     */
    GAMEOVER
}
