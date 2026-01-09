package com.marcopiccionitraining.parkychess;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.pieces.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;

import static com.marcopiccionitraining.parkychess.model.FENPositions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest @Slf4j
public class FENTests {
    private final String FEN_VALIDATOR_REGEX = "((([pnbrqkPNBRQK1-8]{1,8})/?){8})\\s+([bw])\\s+(-|K?Q?k?q?)\\s+(-|([a-h][3-6])?)\\s+(\\d+)\\s+(\\d+)\\s*";

    @BeforeEach
        void setUp() {};

    @Test
    void initialFENCorrect(){
        String expectedInitialFEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        assertTrue(expectedInitialFEN.matches(FEN_VALIDATOR_REGEX), "The fen string failed to validate");
        assertEquals(expectedInitialFEN, FEN_INITIAL_POSITION, "Initial FEN string is wrong.");
    }
    @Test
    void FENTwoConsecutiveSlashes(){
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new ChessGame("rnbqkbnr/pppppppp/8//8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"),
                "There cannot be two consecutive slashes in a FEN string.");
    }
    @Test
    void FENNineEmptySquaresInARow(){
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new ChessGame("rnbqkbnr/pppppppp/8/9/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"),
                "There cannot be more than 8 empty squares in a FEN row.");
    }
    @Test
    void FENSixSquareInARow(){
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new ChessGame("rnbqkbnr/pppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"),
                "In a FEN row without empty squares there cannot be less than 8 chars.");
    }
    @Test
    void FENSevenEmptySquaresInARow(){
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new ChessGame("rnbqkbnr/pppppppp/8/7/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"),
                "There cannot be less than 8 empty squares in an empty FEN row.");
    }
    @Test
    void FENNinePawnsInARow(){
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new ChessGame("rnbqkbnr/ppppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"),
                "There cannot be more than 8 chars representing pieces on a FEN string row.");
    }
    @Test
    void FENEightPawnsAndOneEmptySquareInARow(){
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new ChessGame("rnbqkbnr/ppppppp1p/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"),
                "There cannot be more than 8 chars representing pieces and empty squares on a FEN string row.");
    }
    @Test
    void FENNinePawnsAndOneEmptySquareInARow(){
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new ChessGame("rnbqkbnr/pppppppp1p/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"),
                "There cannot be more than 8 chars representing pieces and empty squares on a FEN string row.");
    }
    @Test
    void FENWrongRowMissingSlash(){
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new ChessGame("rnbqkbnr/pppppppp/88/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"),
                "There is a missing slash in the FEN.");
    }
    @Test
    void FENWrongRowMissingTwoSlashes(){
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new ChessGame("rnbqkbnr/pppppppp/88/8/8PPPPPPPP/RNBQKBNR w KQkq - 0 1"),
                "There are more than a missing slash in the FEN.");
    }
    @Test
    void FENTwoBlackKings(){
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new ChessGame("rnbqkbnr/kppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"),
                "There are two black kings in the FEN.");
    }
    @Test
    void FENTwoWhiteKings(){
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new ChessGame("rnbqkbnr/pppppppp/8/8/8/8/KPPPPPPP/RNBQKBNR w KQkq - 0 1"),
                "There are two white kings in the FEN.");
    }
    @Test
    void FENNoKings(){
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new ChessGame("rnbq1bnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQ1BNR w KQkq - 0 1"),
                "There are no kings in the FEN.");
    }
    @Test
    void FENBlackPawnOnFirstRow(){
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new ChessGame("rnbqkbnp/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"),
                "There cannot be a black pawn on the first row.");
    }
    @Test
    void FENWhitePawnOnEighthRow(){
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new ChessGame("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RPBQKBNR w KQkq - 0 1"),
                "There cannot be a white pawn on the eighth row.");
    }

    @Test
    void fromEnPassantEnabledFENToChessboard(){
        String expectedComputedFEN = "7k/8/8/pP6/8/8/8/7K w - a6 0 0";
        ChessGame gameState = new ChessGame(expectedComputedFEN);
        log.debug("{}", gameState.getChessboard().toString());
        gameState.getChessboard().setPiece(new King(PlayerColor.BLACK), 0, 7);
        gameState.getChessboard().setPiece(new King(PlayerColor.WHITE), 7, 7);
        gameState.getChessboard().setPiece(new Pawn(PlayerColor.BLACK), 3, 0);
        Pawn whitePawn = new Pawn(PlayerColor.WHITE);
        gameState.getChessboard().setPiece(whitePawn,3,1);
        Collection<Position> piecePositions = new ArrayList<>();
        gameState.getChessboard().setPawnSkippedPosition(PlayerColor.getOpponentColor(
                gameState.getChessboard().getCurrentPlayerColor()), new Position(2,0));
        piecePositions.add(new Position(2,0));
        piecePositions.add(new Position(3,1));
        piecePositions.add(new Position(0,7));
        piecePositions.add(new Position(7,7));
        piecePositions.add(new Position(3,0));
        assertEquals(gameState.getChessboard().getPiecesPositions().size() + 1, piecePositions.size(),
                "The positions of all pieces should be the same.");
        assertEquals(new Position(2,0), gameState.getChessboard().getPawnSkippedPosition(
                PlayerColor.getOpponentColor(gameState.getChessboard().getCurrentPlayerColor())),
                "Black must have an en passant capture position set.");
    }
    @Test
    void noCastlingPossibleShouldBeEncodedWithADash (){
        ChessGame gameState = new ChessGame(FEN_KINGS_ONLY);
        FENStateString computedFENStateString = new FENStateString(gameState);
        assertEquals(FEN_KINGS_ONLY, computedFENStateString.toString(),
                "FEN_KINGS_ONLY and the computed FEN state string should be equal.");
        assertEquals(FEN_KINGS_ONLY.charAt(22), computedFENStateString.toString().charAt(22),
                "The 22nd char of both strings should be a dash.");
        King whiteKing = gameState.getChessboard().getKing(PlayerColor.WHITE);
        King blackKing = gameState.getChessboard().getKing(PlayerColor.BLACK);
        assertFalse(blackKing.isFlaggedAsHavingAlreadyMoved(), "The black King should not have already moved.");
        assertFalse(whiteKing.isFlaggedAsHavingAlreadyMoved(), "The white King should not have already moved.");
    }

    @Test
    void equalsTest(){
        ChessGame gameState = new ChessGame(FEN_KINGS_ONLY);
        FENStateString computedFENStateString1 = new FENStateString(gameState);
        FENStateString computedFENStateString2 = new FENStateString(gameState);
        assertEquals(computedFENStateString1, computedFENStateString2, "The two FENs should be equal.");
    }
    @Test
    void equalsTestWithDifferentTails(){
        ChessGame gameState = new ChessGame("4k3/8/8/8/8/8/8/4K3 w - - 50 31");
        ChessGame gameState2 = new ChessGame("4k3/8/8/8/8/8/8/4K3 w - - 50 31");
        FENStateString computedFENStateString1 = new FENStateString(gameState);
        FENStateString computedFENStateString2 = new FENStateString(gameState2);
        assertEquals(computedFENStateString1, computedFENStateString2, "The two FENs should be equal.");
        log.debug("computedFENStateString1: {}", computedFENStateString1);
        log.debug("computedFENStateString2: {}", computedFENStateString2);
    }
}
