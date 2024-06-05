package model;

import java.util.*;
import java.util.concurrent.Semaphore;
/**
 * The `Board` class represents the game board for Othello.
 * It includes methods for initializing the board, validating moves, making moves, and accessing the board state.
 */
public class Board {
    /** The width of the Othello board. */
    public static final int BOARD_WIDTH = 8;
    /** The length of the Othello board. */
    public static final int BOARD_LENGTH = 8;
    /** The internal representation of the game board using a map with position keys and cell states. */
    private Map<String, Character> board;
    /** A semaphore to control access to the board for concurrent threads. */

    private Semaphore semaphore;
    /**
     * Constructs a new `Board` object, initializing the game board and semaphore.
     */

    public Board() {
        board = new HashMap<>();
        initializeBoard();
        //System.out.println("In hier");
        semaphore = new Semaphore(1);
    }

    /**
     * Initializes the Othello game board with starting positions.
     */

    public void initializeBoard() {
        for (int x = 0; x < BOARD_LENGTH; x++) {
            for (int y = 0; y < BOARD_WIDTH; y++) {
                String key = x + "," + y;
                board.put(key, ' ');
            }
        }
        board.put("3,3", 'W');
        board.put("3,4", 'B');
        board.put("4,3", 'B');
        board.put("4,4", 'W');
    }
    /**
     * Retrieves the internal representation of the game board.
     *
     * @return A map representing the state of the game board.
     */

    public Map<String, Character> board() {
        return this.board;
    }
    /**
     * Checks if a move at the specified position is valid for the given player.
     *
     * @param position      The position to check in the format "x,y".
     * @param currentPlayer The player making the move.
     * @return `true` if the move is valid, `false` otherwise.
     */


    public boolean isValidMove(String position, Player currentPlayer) {
        if (board.get(position) != ' ') {
            return false; // La case n'est pas vide
        }

        int x = Integer.parseInt(position.split(",")[0]);
        int y = Integer.parseInt(position.split(",")[1]);
        boolean validMove = false;

        for (MoveDirection dir : MoveDirection.values()) {
            int deriverx = 0, deriverY = 0;
            switch (dir) {
                case UP -> deriverY = -1; case DOWN -> deriverY = 1;
                case LEFT -> deriverx = -1; case RIGHT -> deriverx = 1;
                case UPLEFT -> { deriverx = -1; deriverY = -1; }
                case UPRIGHT -> { deriverx = 1; deriverY = -1; }
                case DOWNLEFT -> { deriverx = -1; deriverY = 1; }
                case DOWNRIGHT -> { deriverx = 1;  deriverY = 1; }
                default -> throw new IllegalStateException("Unexpected value: " + dir);
            }
            int currentX = x + deriverx;
            int currentY = y + deriverY;
            boolean foundOpponent = false;
            while (currentX >= 0 && currentX < BOARD_LENGTH && currentY >= 0 && currentY < BOARD_WIDTH) {
                String currentPos = currentX + "," + currentY;
                char currentSquare = board.get(currentPos);
                if (currentSquare == currentPlayer.getType().toString().charAt(0)) {
                    if (foundOpponent) {
                        validMove = true;
                        break;
                    } else {
                        break;
                    }
                } else if (currentSquare == ' ') {
                    break;
                } else { foundOpponent = true; }
                currentX += deriverx; currentY += deriverY; }
            if (validMove) {
                break;
            }
        }
        return validMove;
    }
    /**
     * Makes a move at the specified position for the given player, updating the board state.
     *
     * @param position      The position to make the move in the format "x,y".
     * @param currentPlayer The player making the move.
     */

    public void makeMove(String position, Player currentPlayer) {
        if (isValidMove(position, currentPlayer)) {
            board.put(position, currentPlayer.getType().toString().charAt(0));
            int x = Integer.parseInt(position.split(",")[0]);
            int y = Integer.parseInt(position.split(",")[1]);
            String[] directions = { "up", "down", "left", "right", "upleft", "upright", "downleft", "downright" };
            for (String dir : directions) {
                int deriverX1 = 0, deriverY1 = 0;
                switch (dir) {
                    case "up":
                        deriverY1 = -1;
                        break;
                    case "down":
                        deriverY1 = 1;
                        break;
                    case "left":
                        deriverX1 = -1;
                        break;
                    case "right":
                        deriverX1 = 1;
                        break;
                    case "upleft":
                        deriverX1 = -1; deriverY1 = -1;
                        break;
                    case "upright":
                        deriverX1 = 1; deriverY1 = -1;
                        break;
                    case "downleft":
                        deriverX1 = -1; deriverY1 = 1;
                        break;
                    case "downright":
                        deriverX1 = 1; deriverY1 = 1;
                        break;
                }
                int currentX = x + deriverX1;
                int currentY = y + deriverY1;
                boolean foundOpponent = false;
                ArrayList<String> piecesToFlip = new ArrayList<>();
                while (currentX >= 0 && currentX < BOARD_LENGTH && currentY >= 0 && currentY < BOARD_WIDTH) {
                    String currentPos = currentX + "," + currentY;
                    char currentSquare = board.get(currentPos);
                    if (currentSquare == currentPlayer.getType().toString().charAt(0)) {
                        if (foundOpponent) {
                            piecesToFlip.forEach(pos -> board.put(pos, currentPlayer.getType().toString().charAt(0)));
                            break;
                        } else {
                            break;
                        }
                    } else if (currentSquare == ' ') {
                        break;
                    } else {
                        foundOpponent = true;
                        piecesToFlip.add(currentPos);
                    }
                    currentX += deriverX1;
                    currentY += deriverY1;
                }
            }
        } else {
            //System.out.println("Mouvement invalide !");
        }
    }
    /**
     * Retrieves the semaphore used to control access to the board for concurrent threads.
     *
     * @return The semaphore instance.
     */
    public Semaphore getSemaphore() {
        return semaphore;
    }

    public Map<String, Character> getBoard() {
        return board;
    }

    /*@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(board);
        for (int x = 0; x < BOARD_LENGTH; x++) {
            sb.append('\n').append(x + 1);
            for (int y = 0; y < BOARD_WIDTH; y++) {
                String key = x + "," + y;
                sb.append(' ').append(board.get(key));
            }
        }
        sb.append('\n');
        return sb.toString();
    }*/
    /**
     * Retrieves the state of the cell at the specified coordinates.
     *
     * @param x The x-coordinate of the cell.
     * @param y The y-coordinate of the cell.
     * @return The state of the cell at the specified coordinates.
     */
    public char getCellState(int x, int y) {
        String key = x + "," + y;
        return board.get(key);
    }
}
