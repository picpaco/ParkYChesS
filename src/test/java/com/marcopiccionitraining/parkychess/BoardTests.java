package com.marcopiccionitraining.parkychess;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.*;
import com.marcopiccionitraining.parkychess.model.pieces.Piece;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.marcopiccionitraining.parkychess.model.FENPositions.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
public class BoardTests {
    //TODO: Make sure there are tests for public methods of class Board, and in particular for:
    // getLegalMovesForColor (PlayerColor playerColor),
    // getPiece(piecePositionForColor),
    // List<Move> getPseudoLegalMovesForColor,
    // and for Piece.getPseudoLegalMoves (piecePositionForColor, chessboard);
    @BeforeEach
    void setup() {

    }

    @Test
    void getPiecesPositionsForBothColors() {
        ChessGame gameState = new ChessGame(FEN_BISHOP_PINNED_BY_OPPOSITE_COLOR_BISHOP);
        List<Position> allPiecesPositions = gameState.getChessboard().getPiecesPositions();
        List<Position> blackPiecesPositions = gameState.getChessboard().getPiecesPositionsForColor(PlayerColor.BLACK);
        List<Position> whitePiecesPositions = gameState.getChessboard().getPiecesPositionsForColor(PlayerColor.WHITE);
        for (Position position : allPiecesPositions) {
            Piece piece = gameState.getChessboard().getPiece(position);
            log.error("Analyzing all pieces positions: found piece {} which has color {}", position, piece.getColor());
            assertTrue(allPiecesPositions.contains(position));
        }
    }

    @Test
    void getPiecesFromPositionsForBothColors() {
        ChessGame gameState = new ChessGame(FEN_BISHOP_PINNED_BY_OPPOSITE_COLOR_BISHOP);
        List<Position> allPiecesPositions = gameState.getChessboard().getPiecesPositions();
        List<Position> blackPiecesPositions = gameState.getChessboard().getPiecesPositionsForColor(PlayerColor.BLACK);
        List<Position> whitePiecesPositions = gameState.getChessboard().getPiecesPositionsForColor(PlayerColor.WHITE);
        blackPiecesPositions.addAll(whitePiecesPositions);
        assertEquals(blackPiecesPositions.size(), allPiecesPositions.size(),
                "All pieces positions list size should be equivalent to white and black positions size.");
        assertEquals(blackPiecesPositions, allPiecesPositions,
                "All pieces positions are not equivalent to white and black positions.");
    }

}
