package com.marcopiccionitraining.parkychess;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.pieces.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;

import static com.marcopiccionitraining.parkychess.model.FENPositions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest @Slf4j
public class FENTests {
    private final String FEN_VALIDATOR_REGEX = "((([pnbrqkPNBRQK1-8]{1,8})/?){8})\\s+([bw])\\s+(-|K?Q?k?q?)\\s+(-|([a-h][3-6])?)\\s+(\\d+)\\s+(\\d+)\\s*";

    @BeforeEach
    void setUp() {
    }

    ;

    @Test
    void initialFENCorrect() {
        String expectedInitialFEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        assertTrue(expectedInitialFEN.matches(FEN_VALIDATOR_REGEX), "The fen string failed to validate");
        assertEquals(expectedInitialFEN, FEN_INITIAL_POSITION, "Initial FEN string is wrong.");
    }

    @Test
    void FENTwoConsecutiveSlashes() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new ChessGame("rnbqkbnr/pppppppp/8//8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"),
                "There cannot be two consecutive slashes in a FEN string.");
    }

    @Test
    void FENNineEmptySquaresInARow() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new ChessGame("rnbqkbnr/pppppppp/8/9/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"),
                "There cannot be more than 8 empty squares in a FEN row.");
    }

    @Test
    void FENSixSquareInARow() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new ChessGame("rnbqkbnr/pppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"),
                "In a FEN row without empty squares there cannot be less than 8 chars.");
    }

    @Test
    void FENSevenEmptySquaresInARow() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new ChessGame("rnbqkbnr/pppppppp/8/7/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"),
                "There cannot be less than 8 empty squares in an empty FEN row.");
    }

    @Test
    void FENNinePawnsInARow() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new ChessGame("rnbqkbnr/ppppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"),
                "There cannot be more than 8 chars representing pieces on a FEN string row.");
    }

    @Test
    void FENEightPawnsAndOneEmptySquareInARow() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new ChessGame("rnbqkbnr/ppppppp1p/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"),
                "There cannot be more than 8 chars representing pieces and empty squares on a FEN string row.");
    }

    @Test
    void FENNinePawnsAndOneEmptySquareInARow() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new ChessGame("rnbqkbnr/pppppppp1p/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"),
                "There cannot be more than 8 chars representing pieces and empty squares on a FEN string row.");
    }

    @Test
    void FENWrongRowMissingSlash() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new ChessGame("rnbqkbnr/pppppppp/88/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"),
                "There is a missing slash in the FEN.");
    }

    @Test
    void FENWrongRowMissingTwoSlashes() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new ChessGame("rnbqkbnr/pppppppp/88/8/8PPPPPPPP/RNBQKBNR w KQkq - 0 1"),
                "There are more than a missing slash in the FEN.");
    }

    @Test
    void FENTwoBlackKings() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new ChessGame("rnbqkbnr/kppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"),
                "There are two black kings in the FEN.");
    }

    @Test
    void FENTwoWhiteKings() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new ChessGame("rnbqkbnr/pppppppp/8/8/8/8/KPPPPPPP/RNBQKBNR w KQkq - 0 1"),
                "There are two white kings in the FEN.");
    }

    @Test
    void FENNoKings() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new ChessGame("rnbq1bnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQ1BNR w KQkq - 0 1"),
                "There are no kings in the FEN.");
    }

    @Test
    void FENBlackPawnOnFirstRow() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new ChessGame("rnbqkbnp/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"),
                "There cannot be a black pawn on the first row.");
    }

    @Test
    void FENWhitePawnOnEighthRow() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new ChessGame("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RPBQKBNR w KQkq - 0 1"),
                "There cannot be a white pawn on the eighth row.");
    }

    @Test
    void enPassantEnabledFENvsDirectPiecePlacement() {
        ChessGame gameState = new ChessGame(FEN_TWO_PAWNS_EN_PASSANT_POSITION_STANDARD);
        log.debug("{}", gameState.getChessboard());
        Board chessboard2 = new Board(PlayerColor.WHITE);
        chessboard2.setPiece(new King(PlayerColor.BLACK), 0, 7);
        chessboard2.setPiece(new King(PlayerColor.WHITE), 7, 7);
        chessboard2.setPiece(new Pawn(PlayerColor.BLACK), 3, 0);
        chessboard2.setPiece(new Pawn(PlayerColor.WHITE), 3, 1);
        Collection<Position> piecePositions = new ArrayList<>();
        chessboard2.setPawnSkippedPosition(PlayerColor.BLACK, new Position(2, 0));
        piecePositions.add(new Position(3, 0));
        piecePositions.add(new Position(3, 1));
        piecePositions.add(new Position(0, 7));
        piecePositions.add(new Position(7, 7));
        assertEquals(gameState.getChessboard().getPiecesPositions().size(), piecePositions.size(),
                "The positions of pieces on both chessboards should be the same.");
        assertEquals(chessboard2.getPawnSkippedPosition(PlayerColor.getOpponentColor(gameState.getChessboard().getCurrentPlayerColor()
        )), gameState.getChessboard().getPawnSkippedPosition(
                PlayerColor.BLACK), "Black must have an en passant capture position set.");
    }
}
 /*   @Test
    void noCastlingPossibleShouldBeEncodedWithDash (){
        ChessGame gameState = new ChessGame(FEN_KINGS_ONLY);
        Board chessboard = gameState.getChessboard();
        FENStateString computedFENStateString = new FENStateString(chessboard.getCurrentPlayerColor(), chessboard);
        assertEquals(FEN_KINGS_ONLY, computedFENStateString.toString() + " 0 1",
                "FEN_KINGS_ONLY and the computed FEN state string should be equal.");
        assertEquals(FEN_KINGS_ONLY.charAt(22), computedFENStateString.toString().charAt(22),
                "The 22nd char of both strings should be a dash.");
    }

    @Test
    void equalsTest(){
        ChessGame gameState = new ChessGame(FEN_KINGS_ONLY);
        Board chessboard = gameState.getChessboard();
        FENStateString computedFENStateString1 = new FENStateString(chessboard.getCurrentPlayerColor(), chessboard);
        FENStateString computedFENStateString2 = new FENStateString(chessboard.getCurrentPlayerColor(), chessboard);
        assertEquals(computedFENStateString1, computedFENStateString2, "The two FENs should be equal.");
    }

    @Test
    void equalsTestWithDifferentTails(){
        ChessGame gameState = new ChessGame("4k3/8/8/8/8/8/8/4K3 w - - 50 31");
        ChessGame gameState2 = new ChessGame("4k3/8/8/8/8/8/8/4K3 w - - 50 31");
        Board chessboard = gameState.getChessboard();
        FENStateString computedFENStateString1 = new FENStateString(chessboard.getCurrentPlayerColor(), chessboard);
        FENStateString computedFENStateString2 = new FENStateString(chessboard.getCurrentPlayerColor(), chessboard);
        assertEquals(computedFENStateString1, computedFENStateString2, "The two FENs should be equal.");
        log.debug("computedFENStateString1: {}", computedFENStateString1);
        log.debug("computedFENStateString2: {}", computedFENStateString2);
    }
}
*/