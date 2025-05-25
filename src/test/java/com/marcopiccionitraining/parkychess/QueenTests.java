package com.marcopiccionitraining.parkychess;
import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import com.marcopiccionitraining.parkychess.model.moves.StandardMove;
import com.marcopiccionitraining.parkychess.model.pieces.PieceNames;
import com.marcopiccionitraining.parkychess.model.pieces.Queen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;

import static com.marcopiccionitraining.parkychess.model.FENPositions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class QueenTests {

    Board chessboard;

    @BeforeEach
    void setUp() {
        chessboard = new Board();
    }
    @Test
    void whiteQueenInitialPositionHasNotMovedYet(){
        ChessGame gameState = new ChessGame(chessboard,FEN_INITIAL_POSITION );
        Queen whiteQueen = (Queen) chessboard.getPiece (7,3);
        assertFalse(whiteQueen.hasMoved(), "Queen should not have already moved at starting position.");
    }
    @Test
    void blackQueenInitialPositionHasNotMovedYet(){
        ChessGame gameState = new ChessGame(chessboard,FEN_INITIAL_POSITION );
        Queen blackQueen = (Queen) chessboard.getPiece (0,3);
        assertFalse(blackQueen.hasMoved(), "Queen should not have already moved at starting position.");
    }
    @Test
    void whiteQueenNameCheck(){
        ChessGame gameState = new ChessGame(chessboard,FEN_INITIAL_POSITION );
        Queen whiteQueen = (Queen) chessboard.getPiece (7,3);
        assertEquals(PlayerColor.WHITE, whiteQueen.getColor(), "Queen at square d1 should be white.");
        assertEquals(PieceNames.QUEEN, whiteQueen.getName(), "Queen's name should be 'QUEEN'");
        assertEquals("QUEEN", whiteQueen.toString(), "Queen's name as string should be 'QUEEN'");
    }
    @Test
    void blackQueenNameCheck(){
        ChessGame gameState = new ChessGame(chessboard,FEN_INITIAL_POSITION );
        Queen blackQueen = (Queen) chessboard.getPiece (0,3);
        assertEquals(PlayerColor.BLACK, blackQueen.getColor(), "Queen at square d8 should be black.");
        assertEquals(PieceNames.QUEEN, blackQueen.getName(), "Queen's name should be 'QUEEN'");
        assertEquals("queen", blackQueen.toString(), "Queen's name as string should be 'queen'");
    }
    @Test
    void blackQueenAtD8CannotMoveAtStartingPosition() {
        ChessGame gameState = new ChessGame(chessboard,FEN_INITIAL_POSITION );
        gameState.setCurrentColor(PlayerColor.BLACK);
        assertTrue(gameState.getLegalMovesForPieceAtPosition(new Position(0, 3)).isEmpty());
    }

    @Test
    void whiteQueenAtD1CannotMoveAtStartingPosition() {
        ChessGame gameState = new ChessGame(chessboard,FEN_INITIAL_POSITION );
        assertTrue(gameState.getLegalMovesForPieceAtPosition(new Position(7, 3)).isEmpty());
    }
    @Test
    void blackQueenAtD8LegalMovesNoCheck() {
        ChessGame gameState = new ChessGame(chessboard, FEN_BLACK_QUEEN_D8_NO_CHECK_POSITION);
        Collection<Move> queenLegalMoves = new ArrayList<>();
        queenLegalMoves.add(new StandardMove(new Position(0, 3), new Position(0, 0)));
        queenLegalMoves.add(new StandardMove(new Position(0, 3), new Position(0, 1)));
        queenLegalMoves.add(new StandardMove(new Position(0, 3), new Position(0, 2)));
        queenLegalMoves.add(new StandardMove(new Position(0, 3), new Position(1, 3)));
        queenLegalMoves.add(new StandardMove(new Position(0, 3), new Position(2, 3)));
        queenLegalMoves.add(new StandardMove(new Position(0, 3), new Position(3, 3)));
        queenLegalMoves.add(new StandardMove(new Position(0, 3), new Position(4, 3)));
        queenLegalMoves.add(new StandardMove(new Position(0, 3), new Position(5, 3)));
        queenLegalMoves.add(new StandardMove(new Position(0, 3), new Position(6, 3)));
        queenLegalMoves.add(new StandardMove(new Position(0, 3), new Position(7, 3)));
        queenLegalMoves.add(new StandardMove(new Position(0, 3), new Position(1, 2)));
        queenLegalMoves.add(new StandardMove(new Position(0, 3), new Position(2, 1)));
        queenLegalMoves.add(new StandardMove(new Position(0, 3), new Position(3, 0)));
        queenLegalMoves.add(new StandardMove(new Position(0, 3), new Position(1, 4)));
        queenLegalMoves.add(new StandardMove(new Position(0, 3), new Position(2, 5)));
        queenLegalMoves.add(new StandardMove(new Position(0, 3), new Position(3, 6)));
        queenLegalMoves.add(new StandardMove(new Position(0, 3), new Position(4, 7)));
        Collection<Move> computedQueenMoves = gameState.getLegalMovesForPieceAtPosition(new Position(0, 3));
        assertTrue(queenLegalMoves.size() == computedQueenMoves.size() &&
                queenLegalMoves.containsAll(computedQueenMoves) &&
                computedQueenMoves.containsAll(queenLegalMoves), "The expected list of moves: " + queenLegalMoves +
                " is not the same as the produced one: " + computedQueenMoves);
    }
    @Test
    void whiteQueenAtD1LegalMovesNoCheck() {
        ChessGame gameState = new ChessGame(chessboard, FEN_WHITE_QUEEN_D1_NO_CHECK_POSITION);
        Collection<Move> queenLegalMoves = new ArrayList<>();
        queenLegalMoves.add(new StandardMove(new Position(7, 3), new Position(7, 0)));
        queenLegalMoves.add(new StandardMove(new Position(7, 3), new Position(7, 1)));
        queenLegalMoves.add(new StandardMove(new Position(7, 3), new Position(7, 2)));
        queenLegalMoves.add(new StandardMove(new Position(7, 3), new Position(6, 3)));
        queenLegalMoves.add(new StandardMove(new Position(7, 3), new Position(5, 3)));
        queenLegalMoves.add(new StandardMove(new Position(7, 3), new Position(4, 3)));
        queenLegalMoves.add(new StandardMove(new Position(7, 3), new Position(3, 3)));
        queenLegalMoves.add(new StandardMove(new Position(7, 3), new Position(2, 3)));
        queenLegalMoves.add(new StandardMove(new Position(7, 3), new Position(1, 3)));
        queenLegalMoves.add(new StandardMove(new Position(7, 3), new Position(0, 3)));
        queenLegalMoves.add(new StandardMove(new Position(7, 3), new Position(6, 2)));
        queenLegalMoves.add(new StandardMove(new Position(7, 3), new Position(5, 1)));
        queenLegalMoves.add(new StandardMove(new Position(7, 3), new Position(4, 0)));
        queenLegalMoves.add(new StandardMove(new Position(7, 3), new Position(6, 4)));
        queenLegalMoves.add(new StandardMove(new Position(7, 3), new Position(5, 5)));
        queenLegalMoves.add(new StandardMove(new Position(7, 3), new Position(4, 6)));
        queenLegalMoves.add(new StandardMove(new Position(7, 3), new Position(3, 7)));
        Collection<Move> computedQueenMoves = gameState.getLegalMovesForPieceAtPosition(new Position(7, 3));
        assertTrue(queenLegalMoves.size() == computedQueenMoves.size() &&
                queenLegalMoves.containsAll(computedQueenMoves) &&
                computedQueenMoves.containsAll(queenLegalMoves), "The expected list of moves: " + queenLegalMoves +
                " is not the same as the produced one: " + computedQueenMoves);
    }
}