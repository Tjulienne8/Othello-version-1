package model;

import java.util.ArrayList;
import java.util.List;
/**
 * This class represents a custom thread for managing the flow of an Othello game.
 * It extends the {@link Thread} class and is designed to control the game logic in a separate thread.
 */
public class Threads extends Thread {
    private OthelloGame game;
    /**
     * Constructs a new Threads instance associated with a specific Othello game.
     *
     * @param game The Othello game to be managed by this thread.
     */

    public Threads(OthelloGame game) {
        this.game = game;
    }
    /**
     * The main execution method of the thread. It controls the game flow until the game is over.
     * If it is the turn of the WHITE player, it waits for a semaphore and then makes a random move.
     * The thread continues until the game is over.
     */

    @Override
    public void run() {
        //System.out.println("start");
        while (!game.gameOver()) {
            if (game.getCurrentPlayer().getType() != PlayerType.WHITE) {
                game.getBoard().getSemaphore().release();
            } else {
                //System.out.println("start");
                try {
                    //System.out.println("acquiring");
                    this.game.getBoard().getSemaphore().acquire();
                   // System.out.println("acquired");

                    if (this.game.getCurrentPlayer().getType() == PlayerType.WHITE) {
                        //System.out.println("Its whites turn");
                        Thread.sleep(2000);
                        playRandomMove();
                    }
                    // this.game.getBlackPlayer().incrementScore();
                    // this.game.getWhitePlayer().incrementScore();

                    game.switchPlayer();
                    //System.out.println("Not game over");
                } catch (InterruptedException e) {throw new RuntimeException(e);}
                finally {
                }
            }
        }
        //System.out.println("Its game over");

    }
    /**
     * Makes a random move for the WHITE player if available; otherwise, switches the player's turn.
     *
     * @throws InterruptedException If the thread is interrupted while waiting for a move.
     */

    public void playRandomMove() throws InterruptedException {
        List<String> availableMoves = getAvailableMoves();
        if (!availableMoves.isEmpty()) {

            int randomIndex = (int) (Math.random() * availableMoves.size());
            String chosenMove = availableMoves.get(randomIndex);

            // board.semaphore.acquire();
            this.game.makeMove(chosenMove, this.game.getWhitePlayer());
            //System.out.println("PC playing " + chosenMove);
            // board.semaphore.release();
        } else {
            this.game.switchPlayer();
           // System.out.println("No moves available for White player");
        }
    }
    /**
     * Gets a list of available moves for the WHITE player on the current game board.
     *
     * @return A list of strings representing available moves in the format "x,y".
     */

    public List<String> getAvailableMoves() {
        List<String> availableMoves = new ArrayList<>();
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                String move = x + "," + y;
                if (this.game.isValidMove(move, game.getWhitePlayer())) {
                    availableMoves.add(move);
                }
            }
        }
        return availableMoves;
    }
}
