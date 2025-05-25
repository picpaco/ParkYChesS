package com.marcopiccionitraining.parkychess;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.pieces.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.marcopiccionitraining.parkychess.model.FENPositions.FEN_INITIAL_POSITION;
import static com.marcopiccionitraining.parkychess.model.FENPositions.FEN_TEST_LOSING_CASTLE_RIGHTS_1_WHITE_MOVES_DEPTH_2_BxA8;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FENTests {
    Board chessboard;
    private final String FEN_VALIDATOR_REGEX = "((([pnbrqkPNBRQK1-8]{1,8})/?){8})\\s+([bw])\\s+(-|K?Q?k?q?)\\s+(-|([a-h][3-6])?)\\s+(\\d+)\\s+(\\d+)\\s*";

    @BeforeEach
        void setUp() {
            chessboard = new Board();
        }
    @Test
    void initialFENCorrect(){
        String expectedInitialFEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
        assertTrue(expectedInitialFEN.matches(FEN_VALIDATOR_REGEX), "The fen string failed to validate");
        assertEquals(expectedInitialFEN, FEN_INITIAL_POSITION, "Initial FEN string should be different");
    }
    @Test
    void FENEmptyRow(){
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new FENString("rnbqkbnr/pppppppp/8//8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"),
                "An empty row in the FEN should be a problem.");
    }
    @Test
    void FENNineEmptySquaresInARow(){
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new FENString("rnbqkbnr/pppppppp/8/9/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"),
                "More than 8 empty squares on a row in the FEN should be a problem.");
    }
    @Test
    void FENSixPawnsInARow(){
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new FENString("rnbqkbnr/pppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"),
                "Less than 8 squares on a row (empty or not) in the FEN should be a problem.");
    }
    @Test
    void FENSevenEmptySquaresInARow(){
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new FENString("rnbqkbnr/pppppppp/8/7/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"),
                "Less than 8 empty squares on a row in the FEN should be a problem.");
    }
    @Test
    void FENNinePawnsInARow(){
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new FENString("rnbqkbnr/ppppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"),
                "More than 8 squares on a row (empty or not) in the FEN should be a problem.");
    }
    @Test
    void FENEightPawnsAndOneEmptySquareInARow(){
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new FENString("rnbqkbnr/ppppppp1p/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"),
                "More than 8 squares on a row (empty or not) in the FEN should be a problem.");
    }
    @Test
    void FENNinePawnsAndOneEmptySquareInARow(){
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new FENString("rnbqkbnr/pppppppp1p/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"),
                "More than 8 squares on a row (empty or not) in the FEN should be a problem.");
    }
    @Test
    void FENWrongRowMissingSlash(){
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new FENString("rnbqkbnr/pppppppp/88/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"),
                "A missing slash in the FEN should be a problem.");
    }
    @Test
    void FENWrongRowMissingTwoSlashes(){
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new FENString("rnbqkbnr/pppppppp/88/8/8PPPPPPPP/RNBQKBNR w KQkq - 0 1"),
                "A missing slash in the FEN should be a problem.");
    }
    @Test
    void FENTwoBlackKings(){
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new FENString("rnbqkbnr/kppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"),
                "There are two black kings in the FEN.");
    }
    @Test
    void FENNoKings(){
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new FENString("rnbq1bnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQ1BNR w KQkq - 0 1"),
                "Having no kings in the FEN should be a problem..");
    }
    @Test
    void FENBlackPawnOnFirstRow(){
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new FENString("rnbqkbnp/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"),
                "A black pawn on the first row should be a problem.");
    }
    @Test
    void FENWhitePawnOnEighthRow(){
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new FENString("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RPBQKBNR w KQkq - 0 1"),
                "A white pawn on the eigth row should be a problem.");
    }
    @Test
    void fromInitialStringToFENString(){
        ChessGame gameState = new ChessGame(chessboard, FEN_INITIAL_POSITION);
        assertEquals(FEN_INITIAL_POSITION, new FENString(FEN_INITIAL_POSITION).toString(),
                "FEN_INITIAL_POSITION and of new FENString(FEN_INITIAL_POSITION).toString() should be the same.");
    }
    @Test
    void testCastleRightsFromFEN() {
        ChessGame gameState = new ChessGame(chessboard, FEN_TEST_LOSING_CASTLE_RIGHTS_1_WHITE_MOVES_DEPTH_2_BxA8);
        assertFalse(chessboard.isQueensideCastlingPossibleFromFEN(PlayerColor.BLACK), "Black should not be able to castle queenside.");
        assertFalse(chessboard.isQueensideCastlingPossibleFromFEN(PlayerColor.WHITE), "White should not be able to castle queenside.");
        assertTrue(chessboard.isKingsideCastlingPossibleFromFEN(PlayerColor.BLACK), "Black should be able to castle kings.");
        assertTrue(chessboard.isKingsideCastlingPossibleFromFEN(PlayerColor.WHITE), "White should be able to castle kings.");
    }

    @Test
    void fromEnPassantEnabledFENToChessboard(){
        String expectedComputedFEN = "7k/8/8/pP6/8/8/8/7K w - a6 0 0";
        ChessGame gameState = new ChessGame(chessboard, expectedComputedFEN);
        Board chessboard2 = new Board();
        chessboard2.setPiece(new King(PlayerColor.BLACK), 0, 7);
        chessboard2.setPiece(new King(PlayerColor.WHITE), 7, 7);
        chessboard2.setPiece(new Pawn(PlayerColor.BLACK), 3, 0);
        Pawn whitePawn = new Pawn(PlayerColor.WHITE);
        chessboard2.setEnPassantCapturePositionForColor(PlayerColor.BLACK, new Position(2,0));
        chessboard2.setPiece(whitePawn,3,1);
        assertEquals(chessboard, chessboard2, "The content and positions of all pieces should be the same.");
        assertEquals(new Position(2,0), chessboard.getEnPassantCapturePositionForColor(PlayerColor.BLACK),
                "Black must have an en passant capture positions set.");
    }
    @Test
    void fromInitialPositionToFEN(){
        String computedFENString = new FENString(FEN_INITIAL_POSITION).toString();
        assertEquals("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1", computedFENString,
                "The expected and computed FEN strings should be the same.");
    }
}
