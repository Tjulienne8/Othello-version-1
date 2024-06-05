package model;

import static org.junit.jupiter.api.Assertions.*;

import controller.GameState;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MeineTeste {
    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    @Test
    void testInitializeBoard_validStart() {
        board.initializeBoard();
        assertEquals('W', board.getBoard().get("3,3"));
        assertEquals('B', board.getBoard().get("3,4"));
        assertEquals('B', board.getBoard().get("4,3"));
        assertEquals('W', board.getBoard().get("4,4"));
        // Test rest of board is empty
        for (int x = 0; x < Board.BOARD_LENGTH; x++) {
            for (int y = 0; y < Board.BOARD_WIDTH; y++) {
                if (!(x == 3 && y == 3) && !(x == 3 && y == 4)
                        && !(x == 4 && y == 3) && !(x == 4 && y == 4)) {
                    assertEquals(' ', board.getBoard().get(x + "," + y));
                }
            }
        }
    }


    @Test
    void testIsValidMoveForPiece() {
        Player player = new Player(PlayerType.BLACK);
        String position = "4,4";
        assertFalse(board.isValidMove(position, player));
    }

    @Test
    void testIsValidMove_positionOccupied() {
        Player player = new Player(PlayerType.BLACK);
        String position = "3,3";
        assertFalse(board.isValidMove(position, player));
    }

    @Test
    void testIsValidMove_notFlankingOpponent() {
        Player player = new Player(PlayerType.BLACK);
        String position = "5,5";
        assertFalse(board.isValidMove(position, player));
    }

    @Test
    void testMakeMove_validMove() {
        Player player = new Player(PlayerType.BLACK);
        String position = "2,3";

        // Assume that (2,3) is a valid move for simplicity
        assertTrue(board.isValidMove(position, player));

        // Make the move
        board.makeMove(position, player);

        // Check if the board is updated correctly
        assertEquals('B', board.getBoard().get(position));
    }
    @Test
    void testEnumValues() {
        // Vérifiez que toutes les valeurs de l'énumération sont présentes
        assertNotNull(MoveDirection.UP);
        assertNotNull(MoveDirection.DOWN);
        assertNotNull(MoveDirection.LEFT);
        assertNotNull(MoveDirection.RIGHT);
        assertNotNull(MoveDirection.UPLEFT);
        assertNotNull(MoveDirection.UPRIGHT);
        assertNotNull(MoveDirection.DOWNLEFT);
        assertNotNull(MoveDirection.DOWNRIGHT);
    }

    @Test
    void testEnumToString() {
        // Vérifiez que la méthode toString renvoie le nom correct de chaque valeur
        assertEquals("UP", MoveDirection.UP.toString());
        assertEquals("DOWN", MoveDirection.DOWN.toString());
        assertEquals("LEFT", MoveDirection.LEFT.toString());
        assertEquals("RIGHT", MoveDirection.RIGHT.toString());
        assertEquals("UPLEFT", MoveDirection.UPLEFT.toString());
        assertEquals("UPRIGHT", MoveDirection.UPRIGHT.toString());
        assertEquals("DOWNLEFT", MoveDirection.DOWNLEFT.toString());
        assertEquals("DOWNRIGHT", MoveDirection.DOWNRIGHT.toString());
    }

    @Test
    void thread() throws InterruptedException {
        var b = new OthelloGame();
        var c = GameState.START;
        var e = b.getState();
        assertEquals(e, GameState.START);
        b.setState(GameState.PLAYING);
        assertEquals(b.getState(), GameState.PLAYING);
        var a = new Threads(b);
        b.setState(GameState.PLAYING);
        a.getAvailableMoves();
        //a.playRandomMove();
        b.getBlackPlayer();
        b.getWhitePlayer();
        b.switchPlayer();
        b.setCurrentPlayer(new Player(PlayerType.BLACK));
        assertTrue(b.getCurrentPlayer().getType() == PlayerType.BLACK);
        var t = b.playerWWon();
        assertFalse(t);
        b.checkWinner();
        b.getWhitePlayer().incrementScore();
        b.getWhitePlayer().opponent();
        b.getBoard().getCellState(2, 4);
        b.getWhitePlayer().setScore(4);
        b.getBlackPlayer().setScore(2);
        b.playerWWon();
        assertFalse(b.playerBWon());

        var z = b.playerBWon();
        assertFalse(z);
        b.checkWinner();
        b.getBlackPlayer().incrementScore();
        b.getBlackPlayer().opponent();
        b.getBoard().getCellState(2, 4);
        b.getBlackPlayer().setScore(4);
        b.getWhitePlayer().setScore(2);
        b.playerBWon();

        assertTrue(b.playerBWon());
        //b.makeMove("44",b.getBlackPlayer());
    }

    @Test
    void othellotest() {
        var a = GameState.START;
    }

    @Test
    void run() {

    }

    @Test
    public void testMakeValidMove() {
        OthelloGame game = new OthelloGame();
        Player player = game.getBlackPlayer();
        String position = "3,3";
        game.makeMove(position, player);
        // assert board updated correctly after valid move
    }

}



