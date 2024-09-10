package com.marcopiccionitraining.parkychess;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import com.marcopiccionitraining.parkychess.model.moves.StandardMove;
import com.marcopiccionitraining.parkychess.model.pieces.Bishop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;

import static com.marcopiccionitraining.parkychess.model.FENPositions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BishopTests {

    Board chessboard;

    @BeforeEach
    void setUp() {
        chessboard = new Board();
    }
    @Test
    void whiteBishopInitialPositionHasNotMovedYet(){
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_INITIAL_POSITION));
        Bishop whiteBishop = (Bishop) chessboard.getPiece (7,5);
        assertFalse(whiteBishop.hasMoved(), "Bishop should not have already moved at starting position.");
    }
    @Test
    void blackBishopInitialPositionHasNotMovedYet(){
        ChessGame gameState = new ChessGame(chessboard, new FENString (FEN_INITIAL_POSITION));
        Bishop blackBishop = (Bishop) chessboard.getPiece (0,5);
        assertFalse(blackBishop.hasMoved(), "Bishop should not have already moved at starting position.");
    }
    @Test
    void whiteBishopNameCheck(){
        ChessGame gameState = new ChessGame(chessboard, new FENString (FEN_INITIAL_POSITION));
        Bishop whiteBishop = (Bishop) chessboard.getPiece (7,2);
        assertEquals(PlayerColor.WHITE, whiteBishop.getColor(), "Bishop at square h1 should be white.");
        assertEquals(PieceName.BISHOP, whiteBishop.getName(), "Bishop's name should be 'BISHOP'");
        assertEquals("BISHOP", whiteBishop.toString(), "Bishop's name as string should be 'BISHOP'");
    }
    @Test
    void blackBishopNameCheck(){
        ChessGame gameState = new ChessGame(chessboard, new FENString (FEN_INITIAL_POSITION));
        Bishop whiteBishop = (Bishop) chessboard.getPiece (0,2);
        assertEquals(PlayerColor.BLACK, whiteBishop.getColor(), "Bishop at square h1 should be black.");
        assertEquals(PieceName.BISHOP, whiteBishop.getName(), "Bishop's name should be 'BISHOP'");
        assertEquals("bishop", whiteBishop.toString(), "Bishop's name as string should be 'bishop'");
    }
    @Test
    void blackBishopAtC8CannotMoveAtStartingPosition() {
        ChessGame gameState = new ChessGame(chessboard, new FENString (FEN_INITIAL_POSITION));
        gameState.setCurrentColor(PlayerColor.BLACK);
        assertTrue(gameState.getLegalMovesForPieceAtPosition(new Position(0, 2)).isEmpty());
    }
    @Test
    void blackBishopAtF8CannotMoveAtStartingPosition() {
        ChessGame gameState = new ChessGame(chessboard, new FENString (FEN_INITIAL_POSITION));
        gameState.setCurrentColor(PlayerColor.BLACK);
        assertTrue(gameState.getLegalMovesForPieceAtPosition(new Position(0, 5)).isEmpty());
    }
    @Test
    void whiteBishopAtC1CannotMoveAtStartingPosition() {
        ChessGame gameState = new ChessGame(chessboard, new FENString (FEN_INITIAL_POSITION));
        assertTrue(gameState.getLegalMovesForPieceAtPosition(new Position(7, 2)).isEmpty());
    }
    @Test
    void whiteBishopAtF1CannotMoveAtStartingPosition() {
        ChessGame gameState = new ChessGame(chessboard, new FENString (FEN_INITIAL_POSITION));
        assertTrue(gameState.getLegalMovesForPieceAtPosition(new Position(7, 5)).isEmpty());
    }
    @Test
    void blackBishopAtC8LegalMovesNoCheck() {
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_BLACK_BISHOP_C8_NO_CHECK_POSITION));
        Collection<Move> bishopLegalMoves = new ArrayList<>();
        bishopLegalMoves.add(new StandardMove(new Position(0,2), new Position(1,1)));
        bishopLegalMoves.add(new StandardMove(new Position(0,2), new Position(2,0)));
        bishopLegalMoves.add(new StandardMove(new Position(0,2), new Position(1,3)));
        bishopLegalMoves.add(new StandardMove(new Position(0,2), new Position(2,4)));
        bishopLegalMoves.add(new StandardMove(new Position(0,2), new Position(3,5)));
        bishopLegalMoves.add(new StandardMove(new Position(0,2), new Position(4,6)));
        bishopLegalMoves.add(new StandardMove(new Position(0,2), new Position(5,7)));
        Collection<Move> computedBishopMoves = gameState.getLegalMovesForPieceAtPosition(new Position(0, 2));
        assertTrue(bishopLegalMoves.size() == computedBishopMoves.size() &&
                bishopLegalMoves.containsAll(computedBishopMoves) &&
                computedBishopMoves.containsAll(bishopLegalMoves), "The expected list of moves: " + bishopLegalMoves +
                " is not the same as the produced one: " + computedBishopMoves);
    }
    @Test
    void blackBishopAtF8LegalMovesNoCheck() {
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_BLACK_BISHOP_F8_NO_CHECK_POSITION));
        Collection<Move> bishopLegalMoves = new ArrayList<>();
        bishopLegalMoves.add(new StandardMove(new Position(0,5), new Position(1,6)));
        bishopLegalMoves.add(new StandardMove(new Position(0,5), new Position(2,7)));
        bishopLegalMoves.add(new StandardMove(new Position(0,5), new Position(1,4)));
        bishopLegalMoves.add(new StandardMove(new Position(0,5), new Position(2,3)));
        bishopLegalMoves.add(new StandardMove(new Position(0,5), new Position(3,2)));
        bishopLegalMoves.add(new StandardMove(new Position(0,5), new Position(4,1)));
        bishopLegalMoves.add(new StandardMove(new Position(0,5), new Position(5,0)));
        Collection<Move> computedBishopMoves = gameState.getLegalMovesForPieceAtPosition(new Position(0, 5));
        assertTrue(bishopLegalMoves.size() == computedBishopMoves.size() &&
                bishopLegalMoves.containsAll(computedBishopMoves) &&
                computedBishopMoves.containsAll(bishopLegalMoves), "The expected list of moves: " + bishopLegalMoves +
                " is not the same as the produced one: " + computedBishopMoves);
    }
    @Test
    void whiteBishopAtC1LegalMovesNoCheck() {
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_WHITE_BISHOP_C1_NO_CHECK_POSITION));
        Collection<Move> bishopLegalMoves = new ArrayList<>();
        bishopLegalMoves.add(new StandardMove(new Position(7,2), new Position(6,1)));
        bishopLegalMoves.add(new StandardMove(new Position(7,2), new Position(5,0)));
        bishopLegalMoves.add(new StandardMove(new Position(7,2), new Position(6,3)));
        bishopLegalMoves.add(new StandardMove(new Position(7,2), new Position(5,4)));
        bishopLegalMoves.add(new StandardMove(new Position(7,2), new Position(4,5)));
        bishopLegalMoves.add(new StandardMove(new Position(7,2), new Position(3,6)));
        bishopLegalMoves.add(new StandardMove(new Position(7,2), new Position(2,7)));
        Collection<Move> computedBishopMoves = gameState.getLegalMovesForPieceAtPosition(new Position(7, 2));
        assertTrue(bishopLegalMoves.size() == computedBishopMoves.size() &&
                bishopLegalMoves.containsAll(computedBishopMoves) &&
                computedBishopMoves.containsAll(bishopLegalMoves), "The expected list of moves: " + bishopLegalMoves +
                " is not the same as the produced one: " + computedBishopMoves);
    }
    @Test
    void whiteBishopAtF1LegalMovesNoCheck() {
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_WHITE_BISHOP_F1_NO_CHECK_POSITION));
        Collection<Move> bishopLegalMoves = new ArrayList<>();
        bishopLegalMoves.add(new StandardMove(new Position(7,5), new Position(6,6)));
        bishopLegalMoves.add(new StandardMove(new Position(7,5), new Position(5,7)));
        bishopLegalMoves.add(new StandardMove(new Position(7,5), new Position(6,4)));
        bishopLegalMoves.add(new StandardMove(new Position(7,5), new Position(5,3)));
        bishopLegalMoves.add(new StandardMove(new Position(7,5), new Position(4,2)));
        bishopLegalMoves.add(new StandardMove(new Position(7,5), new Position(3,1)));
        bishopLegalMoves.add(new StandardMove(new Position(7,5), new Position(2,0)));
        Collection<Move> computedBishopMoves = gameState.getLegalMovesForPieceAtPosition(new Position(7, 5));
        assertTrue(bishopLegalMoves.size() == computedBishopMoves.size() &&
                bishopLegalMoves.containsAll(computedBishopMoves) &&
                computedBishopMoves.containsAll(bishopLegalMoves), "The expected list of moves: " + bishopLegalMoves +
                " is not the same as the produced one: " + computedBishopMoves);
    }
}
