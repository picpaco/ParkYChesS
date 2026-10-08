package com.marcopiccionitraining.parkychess;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import com.marcopiccionitraining.parkychess.model.moves.StandardMove;
import com.marcopiccionitraining.parkychess.model.pieces.Bishop;
import com.marcopiccionitraining.parkychess.model.pieces.PieceNames;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.marcopiccionitraining.parkychess.model.FENPositions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j @SpringBootTest
public class BishopTests {

    @Test
    void whiteBishopNameCheck() {
        ChessGame gameState = new ChessGame(FEN_INITIAL_POSITION);
        Bishop whiteBishop = (Bishop) gameState.getChessboard().getPiece(7, 2);
        assertEquals(PlayerColor.WHITE, whiteBishop.getColor(), "Bishop at square c1 should be white.");
        assertEquals(PieceNames.BISHOP, whiteBishop.getName(), "Bishop at square c1's name should be 'BISHOP'");
        assertEquals("B", whiteBishop.toString(), "Bishop at square c1's representation as string should be 'B'");
    }

    @Test
    void blackBishopNameCheck() {
        ChessGame gameState = new ChessGame(FEN_INITIAL_POSITION);
        Bishop blackBishop = (Bishop) gameState.getChessboard().getPiece(0, 2);
        assertEquals(PlayerColor.BLACK, blackBishop.getColor(), "Bishop at square c8 should be black.");
        assertEquals(PieceNames.BISHOP, blackBishop.getName(), "Bishop at square c8's name should be 'BISHOP'");
        assertEquals("b", blackBishop.toString(), "Bishop at square c8's string representation should be 'b");
    }

    @Test
    void blackBishopAtC8CannotMoveAtStartingPosition() {
        ChessGame gameState = new ChessGame(FEN_INITIAL_POSITION);
        gameState.getChessboard().setCurrentPlayerColor(PlayerColor.BLACK);
        assertTrue(gameState.getLegalMovesForPieceAtPosition(new Position(0, 2)).isEmpty(),
                "The bishop should not have legal moves in its position.");
    }

    @Test
    void blackBishopAtF8CannotMoveAtStartingPosition() {
        ChessGame gameState = new ChessGame(FEN_INITIAL_POSITION);
        gameState.getChessboard().setCurrentPlayerColor(PlayerColor.BLACK);
        assertTrue(gameState.getLegalMovesForPieceAtPosition(new Position(0, 5)).isEmpty(),
                "The bishop should not have legal moves in its position.");
    }

    @Test
    void whiteBishopAtC1CannotMoveAtStartingPosition() {
        ChessGame gameState = new ChessGame(FEN_INITIAL_POSITION);
        assertTrue(gameState.getLegalMovesForPieceAtPosition(new Position(7, 2)).isEmpty(),
                "The bishop should not have legal moves in its position.");
    }

    @Test
    void whiteBishopAtF1CannotMoveAtStartingPosition() {
        ChessGame gameState = new ChessGame(FEN_INITIAL_POSITION);
        assertEquals(0, gameState.getLegalMovesForPieceAtPosition(new Position(7, 5)).size(),
                "The bishop should not have legal moves in its position.");
    }

    @Test
    void blackBishopPairLegalMovesNoCheck() {
        ChessGame gameState = new ChessGame(FEN_BLACK_BISHOP_PAIR_NO_CHECK_POSITION);
        List<Move> bishopLegalMoves = new ArrayList<>();
        bishopLegalMoves.add(new StandardMove(new Position(0, 2), new Position(1, 1)));
        bishopLegalMoves.add(new StandardMove(new Position(0, 2), new Position(2, 0)));
        bishopLegalMoves.add(new StandardMove(new Position(0, 2), new Position(1, 3)));
        bishopLegalMoves.add(new StandardMove(new Position(0, 2), new Position(2, 4)));
        bishopLegalMoves.add(new StandardMove(new Position(0, 2), new Position(3, 5)));
        bishopLegalMoves.add(new StandardMove(new Position(0, 2), new Position(4, 6)));
        bishopLegalMoves.add(new StandardMove(new Position(0, 2), new Position(5, 7)));
        List<Move> computedBishopMoves = gameState.getLegalMovesForPieceAtPosition(new Position(0, 2));
        assertEquals(bishopLegalMoves.size(), computedBishopMoves.size(),
                "The expected list of moves does not have the same length as the constructed one.");
        assertTrue(bishopLegalMoves.containsAll(computedBishopMoves) && computedBishopMoves.containsAll(bishopLegalMoves),
                "The expected list of moves does not contain the same elements as the produced one.");
    }

    @Test
    void whiteBishopPairLegalMovesNoCheck() {
        ChessGame gameState = new ChessGame(FEN_WHITE_BISHOP_PAIR_NO_CHECK_POSITION);
        List<Move> bishopC1LegalMoves = new ArrayList<>();
        Position bishopC1Position = new Position(7, 2);
        bishopC1LegalMoves.add(new StandardMove(bishopC1Position, new Position(6, 1)));
        bishopC1LegalMoves.add(new StandardMove(bishopC1Position, new Position(5, 0)));
        bishopC1LegalMoves.add(new StandardMove(bishopC1Position, new Position(6, 3)));
        bishopC1LegalMoves.add(new StandardMove(bishopC1Position, new Position(5, 4)));
        bishopC1LegalMoves.add(new StandardMove(bishopC1Position, new Position(4, 5)));
        bishopC1LegalMoves.add(new StandardMove(bishopC1Position, new Position(3, 6)));
        bishopC1LegalMoves.add(new StandardMove(bishopC1Position, new Position(2, 7)));
        List<Move> computedBishopC1Moves = gameState.getLegalMovesForPieceAtPosition(bishopC1Position);
        assertEquals(bishopC1LegalMoves.size(), computedBishopC1Moves.size(),
                "The expected number of moves is not equal to the produced one.");
        assertTrue(bishopC1LegalMoves.containsAll(computedBishopC1Moves) && computedBishopC1Moves.containsAll(bishopC1LegalMoves),
                "The expected list of moves is not equal to the produced one.");
    }

    @Test
    void whiteBishop4Diags2FreeOneWithFriendlyPieceOneNot() {
            ChessGame gameState = new ChessGame(FEN_WHITE_BISHOP_4_DIAGS_2_FREE_ONE_WITH_FRIENDLY_PIECE_ONE_NOT);
            List<Move> bishopLegalMoves = new ArrayList<>();
            bishopLegalMoves.add(new StandardMove(new Position(6, 5), new Position(3, 2)));
            bishopLegalMoves.add(new StandardMove(new Position(6, 5), new Position(4, 3)));
            bishopLegalMoves.add(new StandardMove(new Position(6, 5), new Position(5, 4)));
            bishopLegalMoves.add(new StandardMove(new Position(6, 5), new Position(5, 6)));
            bishopLegalMoves.add(new StandardMove(new Position(6, 5), new Position(4, 7)));
            bishopLegalMoves.add(new StandardMove(new Position(6, 5), new Position(7, 6)));
            List<Move> computedBishopMoves = gameState.getLegalMovesForPieceAtPosition(new Position(6, 5));
            assertTrue(bishopLegalMoves.size() == computedBishopMoves.size() &&
                            bishopLegalMoves.containsAll(computedBishopMoves) &&
                            computedBishopMoves.containsAll(bishopLegalMoves),
                    "The expected list of moves is not equal to the produced one.");
    }
}