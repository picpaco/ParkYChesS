package com.marcopiccionitraining.parkychess;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import com.marcopiccionitraining.parkychess.model.moves.StandardMove;
import com.marcopiccionitraining.parkychess.model.pieces.Knight;
import com.marcopiccionitraining.parkychess.model.pieces.PieceName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;

import static com.marcopiccionitraining.parkychess.model.FENPositions.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class KnightTests {
    private Board chessboard;
    @Autowired
    private ObjectFactory factory;

    @BeforeEach
    void setup(){
        chessboard = new Board();
    }

    @Test
    void contextLoaded(){
        assertNotNull(factory, "Object factory should not be null");
    }

    @Test
    void whiteKnightInACornerInitializedUsingCache(){
        Knight knight = new Knight(PlayerColor.WHITE);
        ArrayList<Position> destinations = new ArrayList<>();
        destinations.add(new Position(1, 2)  );
        destinations.add(new Position(2, 1)  );
        assertTrue(chessboard.getPotentialKnightDestinations(new Position(0,0)).containsAll(destinations) &&
                destinations.containsAll(chessboard.getPotentialKnightDestinations(new Position(0,0))),
                "The content of the potential destinations lists should be the same.");
    }
    @Test
    void blackKnightAtG8StartingPosition() {
        ChessGame gameState = new ChessGame(chessboard, new FENString (FEN_INITIAL_POSITION));
        gameState.setCurrentColor(PlayerColor.BLACK);
        assertEquals(2, gameState.getLegalMovesForPieceAtPosition(new Position(0, 6)).size());
        Collection<Move> knightLegalMoves = new ArrayList<>();
        knightLegalMoves.add(new StandardMove(new Position(0, 6), new Position(2, 5)));
        knightLegalMoves.add(new StandardMove(new Position(0, 6), new Position(2, 7)));
        Collection<Move> computedKnightMoves = gameState.getLegalMovesForPieceAtPosition(new Position(0, 6));
        assertTrue(knightLegalMoves.size() == computedKnightMoves.size() &&
                knightLegalMoves.containsAll(computedKnightMoves) &&
                computedKnightMoves.containsAll(knightLegalMoves), "The expected list of moves: " + knightLegalMoves +
                " is not the same as the produced one: " + computedKnightMoves);
    }
    @Test
    void blackKnightAtB8StartingPosition() {
        ChessGame gameState = new ChessGame(chessboard, new FENString (FEN_INITIAL_POSITION));
        gameState.setCurrentColor(PlayerColor.BLACK);
        assertEquals(2, gameState.getLegalMovesForPieceAtPosition(new Position(0, 1)).size());
        Collection<Move> knightLegalMoves = new ArrayList<>();
        knightLegalMoves.add(new StandardMove(new Position(0, 1), new Position(2, 0)));
        knightLegalMoves.add(new StandardMove(new Position(0, 1), new Position(2, 2)));
        Collection<Move> computedKnightMoves = gameState.getLegalMovesForPieceAtPosition(new Position(0, 1));
        assertTrue(knightLegalMoves.size() == computedKnightMoves.size() &&
                knightLegalMoves.containsAll(computedKnightMoves) &&
                computedKnightMoves.containsAll(knightLegalMoves), "The expected list of moves: " + knightLegalMoves +
                " is not the same as the produced one: " + computedKnightMoves);
    }
    @Test
    void whiteKnightAtB1StartingPosition() {
        ChessGame gameState = new ChessGame(chessboard, new FENString (FEN_INITIAL_POSITION));
        Collection<Move> knightLegalMoves = new ArrayList<>();
        knightLegalMoves.add(new StandardMove(new Position(7, 1), new Position(5, 0)));
        knightLegalMoves.add(new StandardMove(new Position(7, 1), new Position(5, 2)));
        Collection<Move> computedKnightMoves = gameState.getLegalMovesForPieceAtPosition(new Position(7, 1));
        assertTrue(knightLegalMoves.size() == computedKnightMoves.size() &&
                knightLegalMoves.containsAll(computedKnightMoves) &&
                computedKnightMoves.containsAll(knightLegalMoves), "The expected list of moves: " + knightLegalMoves +
                " is not the same as the produced one: " + computedKnightMoves);
    }
    @Test
    void whiteKnightAtG1StartingPosition() {
        ChessGame gameState = new ChessGame(chessboard, new FENString (FEN_INITIAL_POSITION));
        assertEquals(2, gameState.getLegalMovesForPieceAtPosition(new Position(7, 6)).size());
        Collection<Move> knightLegalMoves = new ArrayList<>();
        knightLegalMoves.add(new StandardMove(new Position(7, 6), new Position(5, 5)));
        knightLegalMoves.add(new StandardMove(new Position(7, 6), new Position(5, 7)));
        Collection<Move> computedKnightMoves = gameState.getLegalMovesForPieceAtPosition(new Position(7, 6));
        assertTrue(knightLegalMoves.size() == computedKnightMoves.size() &&
                knightLegalMoves.containsAll(computedKnightMoves) &&
                computedKnightMoves.containsAll(knightLegalMoves), "The expected list of moves: " + knightLegalMoves +
                " is not the same as the produced one: " + computedKnightMoves);
    }
    @Test
    void whiteKnightInitialPositionHasNotMovedYet(){
        ChessGame gameState = new ChessGame(chessboard, new FENString (FEN_INITIAL_POSITION));
        Knight whiteKnight = (Knight) chessboard.getPiece (7,6);
        assertFalse(whiteKnight.hasMoved(), "Knight should not have already moved at starting position.");
    }
    @Test
    void blackKnightInitialPositionHasNotMovedYet(){
        ChessGame gameState = new ChessGame(chessboard, new FENString (FEN_INITIAL_POSITION));
        Knight blackKnight = (Knight) chessboard.getPiece (0,6);
        assertFalse(blackKnight.hasMoved(), "Knight should not have already moved at starting position.");
    }
    @Test
    void whiteKnightNameCheck(){
        ChessGame gameState = new ChessGame(chessboard, new FENString (FEN_INITIAL_POSITION));
        Knight whiteKnight = (Knight) chessboard.getPiece (7,6);
        assertEquals(PlayerColor.WHITE, whiteKnight.getColor(), "Knight at square g1 should be white.");
        assertEquals(PieceName.KNIGHT, whiteKnight.getName(), "Knight's name should be 'KNIGHT'");
        assertEquals("KNIGHT", whiteKnight.toString(), "Knight's name as string should be 'KNIGHT'");
    }
    @Test
    void blackKnightNameCheck(){
        ChessGame gameState = new ChessGame(chessboard, new FENString (FEN_INITIAL_POSITION));
        Knight blackKnight = (Knight) chessboard.getPiece (0,6);
        assertEquals(PlayerColor.BLACK, blackKnight.getColor(), "Knight at square g1 should be black.");
        assertEquals(PieceName.KNIGHT, blackKnight.getName(), "Knight's name should be 'KNIGHT'");
        assertEquals("knight", blackKnight.toString(), "Knight's name as string should be 'knight'");
    }
    @Test
    void whiteKnightOnTheCenterThreePossibleCaptureJumps() {
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_BLACK_KNIGHT_D5_NO_CHECK_8_MIXED_JUMPS_POSITION));
        assertEquals(3, gameState.getLegalMovesForPieceAtPosition(new Position(3, 3)).size());
        Collection<Move> knightLegalMoves = new ArrayList<>();
        knightLegalMoves.add(new StandardMove(new Position(3, 3), new Position(2, 5)));
        knightLegalMoves.add(new StandardMove(new Position(3, 3), new Position(4, 5)));
        knightLegalMoves.add(new StandardMove(new Position(3, 3), new Position(5, 4)));
        Collection<Move> computedKnightMoves = gameState.getLegalMovesForPieceAtPosition(new Position(3, 3));
        assertTrue(knightLegalMoves.size() == computedKnightMoves.size() &&
                knightLegalMoves.containsAll(computedKnightMoves) &&
                computedKnightMoves.containsAll(knightLegalMoves), "The expected list of moves: " + knightLegalMoves +
                " is not the same as the produced one: " + computedKnightMoves);
    }
    @Test
    void whiteKnightOnTheCornerTwoPossibleJumps() {
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_WHITE_KNIGHT_CORNER_2_JUMPS_POSITION));
        assertEquals(2, gameState.getLegalMovesForPieceAtPosition(new Position(7, 0)).size());
        Collection<Move> knightLegalMoves = new ArrayList<>();
        knightLegalMoves.add(new StandardMove(new Position(7, 0), new Position(5, 1)));
        knightLegalMoves.add(new StandardMove(new Position(7, 0), new Position(6, 2)));
        Collection<Move> computedKnightMoves = gameState.getLegalMovesForPieceAtPosition(new Position(7, 0));
        assertTrue(knightLegalMoves.size() == computedKnightMoves.size() &&
                knightLegalMoves.containsAll(computedKnightMoves) &&
                computedKnightMoves.containsAll(knightLegalMoves), "The expected list of moves: " + knightLegalMoves +
                " is not the same as the produced one: " + computedKnightMoves);
    }
    @Test
    void whiteKnightOnTheRimOnePossibleJump() {
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_WHITE_KNIGHT_RIM_1_JUMP_POSITION));
        assertEquals(1, gameState.getLegalMovesForPieceAtPosition(new Position(3, 0)).size());
        Collection<Move> knightLegalMoves = new ArrayList<>();
        knightLegalMoves.add(new StandardMove(new Position(3, 0), new Position(1, 1)));
        Collection<Move> computedKnightMoves = gameState.getLegalMovesForPieceAtPosition(new Position(3, 0));
        assertTrue(knightLegalMoves.size() == computedKnightMoves.size() &&
                knightLegalMoves.containsAll(computedKnightMoves) &&
                computedKnightMoves.containsAll(knightLegalMoves), "The expected list of moves: " + knightLegalMoves +
                " is not the same as the produced one: " + computedKnightMoves);
    }
    @Test
    void whiteKnightOnTheCenterNoLegalJumps() {
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_WHITE_KNIGHT_E5_NO_CHECK_NO_LEGAL_JUMPS_POSITION));
        assertEquals(0, gameState.getLegalMovesForPieceAtPosition(new Position(4, 3)).size());
        Collection<Move> computedKnightMoves = gameState.getLegalMovesForPieceAtPosition(new Position(4, 3));
        assertEquals(0, computedKnightMoves.size(), "The computed list of moves should be empty.");
    }
}
