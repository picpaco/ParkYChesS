package com.marcopiccionitraining.parkychess;

import com.marcopiccionitraining.parkychess.model.Board;
import com.marcopiccionitraining.parkychess.model.ChessGame;
import com.marcopiccionitraining.parkychess.model.PlayerColor;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import com.marcopiccionitraining.parkychess.model.pieces.King;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

import static com.marcopiccionitraining.parkychess.model.FENPositions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BulkPerftTests {
    private Board chessboard;
    private final Logger LOGGER = LoggerFactory.getLogger(BulkPerftTests.class);
    private ChessGame gameState;

    @BeforeEach
    void setup() {
        chessboard = new Board();
    }

    @Test
    void numberOfMovesAtSimplestPlayablePositionDepth0() {
        gameState = new ChessGame(chessboard, FEN_SIMPLEST_PLAYABLE);
        assertThrowsExactly(AssertionError.class, () ->{
            bulkPerft(0);
            }, "Passing depth 0mshould cause an AssertionError.");
    }

    @Test
    void bulkPerftNumberOfPositionsAtInitialPositionDepth1() {
        gameState = new ChessGame(chessboard, FEN_INITIAL_POSITION);
        long result = bulkPerft(1);
        assertEquals(20L, result, "From the initial position there are 20 possible legal moves at depth 1");
    }

    @Test
    void numberOfMovesAtSimplestPlayablePositionDepth1() {
        gameState = new ChessGame(chessboard, FEN_SIMPLEST_PLAYABLE);
        long result = bulkPerft(1);
        assertEquals(4L, result, "There are 4 possible positions at depth 1");
        King blackKing = gameState.getChessboard().getKing(PlayerColor.BLACK);
        King whiteKing = gameState.getChessboard().getKing(PlayerColor.WHITE);
        assertFalse(blackKing.hasMoved(), "Black king should not have moved.");
        assertFalse(whiteKing.hasMoved(), "White king should not have moved.");
    }

    @Test
    void numberOfMovesAtSimplestPlayablePositionDepth2() {
        gameState = new ChessGame(chessboard, FEN_SIMPLEST_PLAYABLE);
        long result = bulkPerft(2);
        assertEquals(12L, result, "There are 12 possible positions at depth 2");
    }

    @Test
    void oneBlackOneWhitePawnAtHomeDepth1() {
        gameState = new ChessGame(chessboard, FEN_ONE_BLACK_ONE_WHITE_PAWN_AT_STARTING_SQUARE);
        long result = bulkPerft(1);
        assertEquals(5L, result, "There are 5 possible positions at depth 1");
    }

    @Test
    void oneBlackOneWhitePawnAtHomeDepth2() {
        gameState = new ChessGame(chessboard, FEN_ONE_BLACK_ONE_WHITE_PAWN_AT_STARTING_SQUARE);
        long result = bulkPerft(2);
        assertEquals(25L, result, "There are 25 possible positions at depth 2");
    }

    @Test
    void oneBlackOneWhitePawnAtHomeOnAdjacentColumnsDepth1() {
        gameState = new ChessGame(chessboard, FEN_ONE_BLACK_ONE_WHITE_PAWN_AT_STARTING_SQUARE_ON_ADJACENT_COLUMNS);
        long result = bulkPerft(1);
        assertEquals(5L, result, "There are 5 possible positions at depth 1");
    }

    @Test
    void oneBlackOneWhitePawnAtHomeOnAdjacentColumnsDepth2() {
        gameState = new ChessGame(chessboard, FEN_ONE_BLACK_ONE_WHITE_PAWN_AT_STARTING_SQUARE_ON_ADJACENT_COLUMNS);
        long result = bulkPerft(2);
        assertEquals(25L, result, "There are 25 possible positions at depth 2");
    }

    @Test
    void twoBlackTwoWhitePawnsAllAtHomeDepth1() {
        gameState = new ChessGame(chessboard, FEN_TWO_BLACK_TWO_WHITE_PAWNS_AT_STARTING_SQUARE);
        long result = bulkPerft(1);
        assertEquals(7L, result, "There are 7 possible positions at depth 1");
    }

    @Test
    void twoBlackTwoWhitePawnsAllAtHomeDepth2() {
        gameState = new ChessGame(chessboard, FEN_TWO_BLACK_TWO_WHITE_PAWNS_AT_STARTING_SQUARE);
        long result = bulkPerft(2);
        assertEquals(49L, result, "There are 49 possible positions at depth 2");
    }

    @Test
    void perftNumberOfPositionsAtInitialPositionDepth1() {
        gameState = new ChessGame(chessboard, FEN_INITIAL_POSITION);
        long result = bulkPerft(1);
        assertEquals(20L, result, "From the initial position there are 20 possible legal moves at depth 1");
    }

    @Test
    void bulkPerftNumberOfPositionsAtInitialPositionDepth2() {
        gameState = new ChessGame(chessboard, FEN_INITIAL_POSITION);
        long result = bulkPerft(2);
        assertEquals(400L, result, "From the initial position there are 400 possible legal moves at depth 2");
    }

    @Test
    void bulkPerftNumberOfPositionsAtInitialPositionDepth3() {
        gameState = new ChessGame(chessboard, FEN_INITIAL_POSITION);
        long result = bulkPerft(3);
        assertEquals(8902L, result, "From the initial position there are 8902 possible legal moves at depth 3");
    }

    @Test
    void bulkPerftNumberOfPositionsAtInitialPositionDepth4() {
        gameState = new ChessGame(chessboard, FEN_INITIAL_POSITION);
        long result = bulkPerft(4);
        assertEquals(197_281L, result, "From the initial position there are 197_281 possible legal moves at depth 3");
        //takes <1s
    }

    @Test
    void bulkPerftNumberOfPositionsAtInitialPositionDepth5() {
        gameState = new ChessGame(chessboard, FEN_INITIAL_POSITION);
        long result = bulkPerft(5);
        assertEquals(4_865_609L, result, "From the initial position there are 4_865_609 possible legal moves at depth 5");
        //takes 11s
    }
    @Test
    void bulkPerftNumberOfPositionsAtInitialPositionDepth6() {
        gameState = new ChessGame(chessboard, FEN_INITIAL_POSITION);
        long result = bulkPerft(6);
        assertEquals(119_060_324L, result, "From the initial position there are 119_060_324 possible legal moves at depth 6");
        //takes 4m 49s
    }

    //@Test
    void bulkPerftNumberOfPositionsAtInitialPositionDepth7() {
        gameState = new ChessGame(chessboard, FEN_INITIAL_POSITION);
        long result = bulkPerft(7);
        assertEquals(3_195_901_860L, result, "From the initial position there are 3_195_901_860 possible legal moves at depth 3");
        //takes too long > 12h
    }

    @Test
    void bulkPerftNumberOfPositionsAfterNa3Depth4(){
        gameState = new ChessGame(chessboard, FEN_INITIAL_POSITION_NA3);
        long result = bulkPerft(4);
        assertEquals(198_572L, result, "From the initial position there are 198_572 possible positions at depth 4");
    }

    @Test
    void perftNumberOfPositionsAfterE3() {
        gameState = new ChessGame(chessboard, FEN_POSITION_AFTER_E3);
        long result = bulkPerft(4);
        assertEquals(402_988L, result, "From the initial position there are 402_988 possible positions at depth 4");
    }

    @Test
    void perftNumberOfPositionsAfterE3_D6() {
        gameState = new ChessGame(chessboard, FEN_POSITION_AFTER_E3_D6);
        long result = bulkPerft(3);
        assertEquals(23_960L, result, "From the initial position there are 23_960L possible positions at depth 3");
    }

    @Test
    void perftNumberOfPositionsAfterE3_D6_QG4() {
        gameState = new ChessGame(chessboard, FEN_POSITION_AFTER_E3_D6_QG4);
        long result = bulkPerft(2);
        assertEquals(1_111L, result, "From the initial position there are 1_111L possible positions at depth 2");
    }

    @Test
    void perftNumberOfPositionsAfterE3_D6_Bb5() {
        gameState = new ChessGame(chessboard, FEN_POSITION_AFTER_E3_D6_BB5);
        long result = bulkPerft(2);
        assertEquals(168L, result, "From the initial position there are 168 possible positions at depth 2");
    }

    @Test
    void perftNumberOfPositionsAfterE3_D6_Bb5_Bd7() {
        gameState = new ChessGame(chessboard, FEN_POSITION_AFTER_E3_D6_BB5_BD7);
        long result = bulkPerft(1);
        assertEquals(34L, result, "From the initial position there are 34 possible positions at depth 1");
    }

    @Test
    void perftNumberOfPositionsAfter_a4() {
        gameState = new ChessGame(chessboard, FEN_POSITION_AFTER_A4);
        long result = bulkPerft(5);
        assertEquals(5_363_555L, result, "From the initial position there are 5_363_555 possible positions at depth 5");
    }

    @Test
    void perftNumberOfPositionsAfter_a4_c6() {
        gameState = new ChessGame(chessboard, FEN_POSITION_AFTER_A4_C6);
        long result = bulkPerft(4);
        assertEquals(244_282L, result, "From the initial position there are 244_282 possible positions at depth 4");
    }

    @Test
    void perftNumberOfPositionsAfter_a4_c6_a5() {
        gameState = new ChessGame(chessboard, FEN_POSITION_AFTER_A4_C6_A5);
        long result = bulkPerft(3);
        assertEquals(10_292L, result, "From the initial position there are 10_292 possible positions at depth 3");
    }

    @Test
    void perftNumberOfPositionsAfter_a4_e5() {
        gameState = new ChessGame(chessboard, FEN_POSITION_AFTER_A4_E5);
        long result = bulkPerft(4);
        assertEquals(447_849L, result, "From the initial position there are 447_849 possible positions at depth 4");
    }

    @Test
    void perftNumberOfPositionsAfter_a4_e5_d4() {
        gameState = new ChessGame(chessboard, FEN_POSITION_AFTER_A4_E5_D4);
        long result = bulkPerft(3);
        assertEquals(28_212L, result, "From the initial position there are 28_212 possible positions at depth 3");
    }

    @Test
    void perftNumberOfPositionsAfter_a4_Nc6() {
        gameState = new ChessGame(chessboard, FEN_POSITION_AFTER_A4_NC6);
        long result = bulkPerft(4);
        assertEquals(257_893L, result, "From the initial position there are 257_893 possible positions at depth 4");
    }

    @Test
    void perftNumberOfPositionsAfter_a4_Nc6_a5() {
        gameState = new ChessGame(chessboard, FEN_POSITION_AFTER_A4_NC6_A5);
        long result = bulkPerft(3);
        assertEquals(10_729L, result, "From the initial position there are 10_729 possible positions at depth 3");
    }

    @Test
    void perftNumberOfPositionsAfter_a4_a5() {
        gameState = new ChessGame(chessboard, FEN_POSITION_AFTER_A4_A5);
        long result = bulkPerft(4);
        assertEquals(204_508L, result, "From the initial position there are 204_508 possible positions at depth 4");
    }

    @Test
    void perftNumberOfPositionsAfter_a4_a5_b4() {
        gameState = new ChessGame(chessboard, FEN_POSITION_AFTER_A4_A5_B4);
        long result = bulkPerft(3);
        assertEquals(10_798L, result, "From the initial position there are 10_798 possible positions at depth 3");
    }

    @Test
    void perftNumberOfPositionsAfter_a4_a5_b4_ra6() {
        gameState = new ChessGame(chessboard, FEN_POSITION_AFTER_A4_A5_B4_RA6);
        long result = bulkPerft(2);
        assertEquals(592L, result, "From the initial position there are 592 possible positions at depth 2");
    }

    @Test
    void perftNumberOfPositionsAfter_a4_a5_b4_ra6_b4a5() {
        gameState = new ChessGame(chessboard, FEN_POSITION_AFTER_A4_A5_B4_RA6_PA4B5);
        long result = bulkPerft(1);
        assertEquals(27L, result, "From the initial position there are 27 possible positions at depth 1");
    }

    @Test
    void bulkPerftKiwiPeteNumberOfPositionsDepth1() {
        gameState = new ChessGame(chessboard, FEN_KIWIPETE_POSITION);
        long result = bulkPerft(1);
        assertEquals(48L, result, "There should be 48 legal moves (leaf nodes).");
    }

    @Test
    void bulkPerftKiwiPeteNumberOfPositionsDepth2() {
        gameState = new ChessGame(chessboard, FEN_KIWIPETE_POSITION);
        long result = bulkPerft(2);
        assertEquals(2039L, result, "There should be 2039 legal moves (leaf nodes).");
    }

    @Test
    void bulkPerftKiwiPeteNumberOfPositionsDepth3() {
        gameState = new ChessGame(chessboard, FEN_KIWIPETE_POSITION);
        long result = bulkPerft(3);
        assertEquals(97_862L, result, "There should be 97862 legal moves (leaf nodes).");
    }

    @Test
    void bulkPerftAvoidIllegalEnPassantCaptureWhiteMovesDepth6() {
        gameState = new ChessGame(chessboard, FEN_TEST_AVOID_ILLEGAL_EP_CAPTURE_WHITE_MOVES);
        long result = bulkPerft(6);
        assertEquals(824_064L, result, "There should be 824_064 legal moves (leaf nodes).");
    }

    @Test
    void bulkPerftAvoidIllegalEnPassantCaptureBlackMovesDepth6() {
        gameState = new ChessGame(chessboard, FEN_TEST_AVOID_ILLEGAL_EP_CAPTURE_BLACK_MOVES);
        long result = bulkPerft(6);
        assertEquals(824_064L, result, "There should be 824_064 legal moves (leaf nodes).");
    }

    @Test
    void bulkPerftEnPassantCaptureCheckOpponentWhiteMovesDepth6() {
        gameState = new ChessGame(chessboard, FEN_TEST_EP_CAPTURE_CHECK_OPPONENT_WHITE_MOVES);
        long result = bulkPerft(6);
        assertEquals(1_440_467L, result, "There should be 1_440_467 legal moves (leaf nodes).");
    }

    @Test
    void bulkPerftEnPassantCaptureCheckOpponentBlackMovesDepth6() {
        gameState = new ChessGame(chessboard, FEN_TEST_EP_CAPTURE_CHECK_OPPONENT_BLACK_MOVES);
        long result = bulkPerft(6);
        assertEquals(1_440_467L, result, "There should be 1_440_467 legal moves (leaf nodes).");
    }

    @Test
    void bulkPerftShortCastleWithCheckWhiteMovesDepth6() {
        gameState = new ChessGame(chessboard, FEN_TEST_SHORT_CASTLE_WITH_CHECK_WHITE_MOVES);
        long result = bulkPerft(6);
        assertEquals(661_072L, result, "There should be 661_072 legal moves (leaf nodes).");
    }

    @Test
    void bulkPerftShortCastleWithCheckBlackMovesDepth6() {
        gameState = new ChessGame(chessboard, FEN_TEST_SHORT_CASTLE_WITH_CHECK_BLACK_MOVES);
        long result = bulkPerft(6);
        assertEquals(661_072L, result, "There should be 661_072 legal moves (leaf nodes).");
    }

    @Test
    void bulkPerftLongCastleWithCheckWhiteMovesDepth6() {
        gameState = new ChessGame(chessboard, FEN_TEST_LONG_CASTLE_WITH_CHECK_WHITE_MOVES);
        long result = bulkPerft(6);
        assertEquals(803_711L, result, "There should be 803_711 legal moves (leaf nodes).");
    }

    @Test
    void bulkPerftLongCastleWithCheckBlackMovesDepth6() {
        gameState = new ChessGame(chessboard, FEN_TEST_LONG_CASTLE_WITH_CHECK_BLACK_MOVES);
        long result = bulkPerft(6);
        assertEquals(803_711L, result, "There should be 803_711 legal moves (leaf nodes).");
    }

    @Test
    void bulkPerftLosingCastleRights_0_Rb1_BlackMovesDepth1() {
        gameState = new ChessGame(chessboard, FEN_KING_ROOK_NO_CHECK_LOSING_CASTLE_RIGHTS_BOTH_COLORS_RB1_D1);
        long result = bulkPerft(1);
        assertEquals(15L, result, "There should be 15 legal moves (leaf nodes).");
    }

    @Test
    void bulkPerftLosingCastleRights_0a_BlackMovesDepth1() {
        gameState = new ChessGame(chessboard, FEN_NO_CHECK_LOST_CASTLE_RIGHTS_0A);
        long result = bulkPerft(1);
        assertEquals(10L, result, "There should be 10 legal moves (leaf nodes).");
    }

    @Test
    void bulkPerftLosingCastleRights_0a_BlackMovesDepth2() {
        gameState = new ChessGame(chessboard, FEN_NO_CHECK_LOST_CASTLE_RIGHTS_0A);
        long result = bulkPerft(2);
        assertEquals(90L, result, "There should be 90 legal moves (leaf nodes).");
    }

    @Test
    void bulkPerftLosingCastleRights_0a_BlackMovesDepth3() {
        gameState = new ChessGame(chessboard, FEN_NO_CHECK_LOST_CASTLE_RIGHTS_0A);
        long result = bulkPerft(3);
        assertEquals(1134L, result, "There should be 1134 legal moves (leaf nodes).");
    }

    @Test
    void bulkPerftLosingCastleRights_0a_BlackMovesDepth4() {
        gameState = new ChessGame(chessboard, FEN_NO_CHECK_LOST_CASTLE_RIGHTS_0A);
        long result = bulkPerft(4);
        assertEquals(15607L, result, "There should be 15607 legal moves (leaf nodes).");
    }

    @Test
    void bulkPerftLosingCastleRights_0a_BlackMovesDepth5() {
        gameState = new ChessGame(chessboard, FEN_NO_CHECK_LOST_CASTLE_RIGHTS_0A);
        long result = bulkPerft(5);
        assertEquals(236169L, result, "There should be 236169 legal moves (leaf nodes).");
    }

    @Test
    void bulkPerftLosingCastleRights_0a_BlackMovesDepth6() {
        gameState = new ChessGame(chessboard, FEN_NO_CHECK_LOST_CASTLE_RIGHTS_0A);
        long result = bulkPerft(6);
        assertEquals(3_687_809L, result, "There should be 3_687_809 legal moves (leaf nodes).");
    }

    @Test
    void perftLosingCastleRights_0_WhiteMovesDepth2() {
        gameState = new ChessGame(chessboard, FEN_KING_ROOK_NO_CHECK_LOSING_CASTLE_RIGHTS_BOTH_COLORS);
        long result = bulkPerft(2);
        assertEquals(221L, result, "There should be 221 legal moves (leaf nodes).");
    }

    @Test
    void perftLosingCastleRights_0_BlackMovesDepth3() {
        gameState = new ChessGame(chessboard, FEN_KING_ROOK_CHECK_LOSING_CASTLE_RIGHTS);
        long result = bulkPerft(3);
        assertEquals(951L, result, "There should be 951 legal moves (leaf nodes).");
    }

    @Test
    void bulkPerftLosingCastleRights_0_WhiteMovesDepth4() {
        gameState = new ChessGame(chessboard, FEN_KING_ROOK_NO_CHECK_LOSING_CASTLE_RIGHTS_BOTH_COLORS);
        long result = bulkPerft(4);
        assertEquals(57_515L, result, "There should be 57515 legal moves (leaf nodes).");
    }

    @Test
    void bulkPerftLosingCastleRights_0_WhiteCastledBlackMovesDepth5() {
        gameState = new ChessGame(chessboard, FEN_KING_ROOK_NO_CHECK_WHITE_LOST_CASTLE_RIGHTS);
        long result = bulkPerft(5);
        assertEquals(656_771L, result, "There should be 656_771 legal moves (leaf nodes).");
    }

    @Test
    void bulkPerftLosingCastleRights_0_WhiteMovesDepth6() {
        gameState = new ChessGame(chessboard, FEN_KING_ROOK_NO_CHECK_LOSING_CASTLE_RIGHTS_BOTH_COLORS);
        long result = bulkPerft(6);
        assertEquals(15_874_227L, result, "There should be 15_874_227 legal moves (leaf nodes).");
    }

    @Test
    void bulkPerftLosingCastleRights_0_Kd1_BlackMovesDepth1() {
        gameState = new ChessGame(chessboard, FEN_KING_ROOK_NO_CHECK_LOSING_CASTLE_RIGHTS_BOTH_COLORS_KD1_D1);
        long result = bulkPerft(1);
        assertEquals(15L, result, "There should be 15 legal moves (leaf nodes).");
    }

    @Test
    void bulkPerftLosingCastleRights_0_BlackMovesRc1Depth1() {
        gameState = new ChessGame(chessboard, FEN_KING_ROOK_NO_CHECK_LOSING_CASTLE_RIGHTS_BLACK_MOVES_D1);
        long result = bulkPerft(1);
        assertEquals(15, result, "There should be 15 legal moves (leaf nodes).");
    }

    @Test
    void bulkPerftLostCastleRights_0_BothColorsCastledWhiteMovesDepth4() {
        gameState = new ChessGame(chessboard, FEN_KING_ROOK_NO_CHECK_LOST_CASTLE_RIGHTS_BOTH_COLORS);
        long result = bulkPerft(4);
        assertEquals(51_509L, result, "There should be 51509 legal moves (leaf nodes).");
    }

    @Test
    void perftLostCastleRights_0_WhiteCastledBlackMovesDepth5() {
        gameState = new ChessGame(chessboard, FEN_KING_ROOK_NO_CHECK_WHITE_LOST_CASTLE_RIGHTS);
        long result = bulkPerft(5);
        assertEquals(656_771L, result, "There should be 656_771 legal moves (leaf nodes).");
    }

    @Test
    void bulkPerftBothColorsLostCastleRights_0_BlackMovesDepth1() {
        gameState = new ChessGame(chessboard, FEN_KING_ROOK_NO_CHECK_BOTH_COLORS_LOST_CASTLE_RIGHTS_D1);
        long result = bulkPerft(1);
        assertEquals(12L, result, "There should be 12 legal moves (leaf nodes).");
        /* 13; {e8g8=1} castling should not be possible at this point for black*/
    }

    @Test
    void bulkPerftBothColorsLostCastleRights_0_WhiteMovesDepth2() {
        gameState = new ChessGame(chessboard, FEN_KING_ROOK_NO_CHECK_BOTH_COLORS_LOST_CASTLE_RIGHTS_D2);
        long result = bulkPerft(2);
        assertEquals(179L, result, "There should be 179 legal moves (leaf nodes).");
    }

    @Test
    void bulkPerftLostCastleRights_0_WhiteCastledBlackMovesDepth3() {
        gameState = new ChessGame(chessboard, FEN_KING_ROOK_NO_CHECK_BOTH_COLORS_LOST_CASTLE_RIGHTS_D3);
        long result = bulkPerft(3);
        assertEquals(2087L, result, "There should be 2087 legal moves (leaf nodes).");
    }

    @Test
    void bulkPerftLostCastleRights_0_WhiteCastledWhiteMovesDepth4() {
        gameState = new ChessGame(chessboard, FEN_KING_ROOK_NO_CHECK_BOTH_COLORS_LOST_CASTLE_RIGHTS);
        long result = bulkPerft(4);
        assertEquals(18_700L, result, "There should be 18700 legal moves (leaf nodes).");
    }

    @Test
    void bulkPerftLosingCastleRights_1_WhiteMovesDepth4() {
        gameState = new ChessGame(chessboard, FEN_TEST_LOSING_CASTLE_RIGHTS_1_WHITE_MOVES_DEPTH_4);
        long result = bulkPerft(4);
        assertEquals(1_274_206L, result, "There should be 1_274_206L legal moves (leaf nodes).");
    }

    @Test
    void bulkPerftLosingCastleRights_1_Rxa8_BlackMovesDepth3() {
        gameState = new ChessGame(chessboard, FEN_TEST_LOSING_CASTLE_RIGHTS_1_BLACK_MOVES_DEPTH_3_RxA8);
   //     System.out.println(chessboard);
        assertFalse(chessboard.isQueensideCastlingPossibleFromFEN(PlayerColor.BLACK), "Black should not be able to castle queenside.");
        assertFalse(chessboard.isQueensideCastlingPossibleFromFEN(PlayerColor.WHITE), "White should not be able to castle queenside.");
        assertTrue(chessboard.isKingsideCastlingPossibleFromFEN(PlayerColor.BLACK), "Black should be able to castle kingside.");
        assertTrue(chessboard.isKingsideCastlingPossibleFromFEN(PlayerColor.WHITE), "White should be able to castle kingside.");
        long result = bulkPerft(3);
        assertEquals(4427L, result, "There should be 4427 legal moves (leaf nodes).");
    }

    @Test
    void bulkPerftLosingCastleRights_1_Rxa8_Ke7_WhiteMovesDepth2() {
        gameState = new ChessGame(chessboard, FEN_TEST_LOSING_CASTLE_RIGHTS_1_WHITE_MOVES_DEPTH_2_KE7);
        assertFalse(chessboard.isQueensideCastlingPossibleFromFEN(PlayerColor.BLACK), "Black should not be able to castle queenside.");
        assertFalse(chessboard.isQueensideCastlingPossibleFromFEN(PlayerColor.WHITE), "White should not be able to castle queenside.");
        assertFalse(chessboard.isKingsideCastlingPossibleFromFEN(PlayerColor.BLACK), "Black should not be able to castle kingside.");
        assertTrue(chessboard.isKingsideCastlingPossibleFromFEN(PlayerColor.WHITE), "White should be able to castle kingside.");
        long result = bulkPerft(2);
        assertEquals(1077L, result, "There should be 1077L legal moves (leaf nodes).");
    }

    @Test
    void bulkPerftLosingCastleRights_1_Rxa8_Kd7_WhiteMovesDepth2() {
        gameState = new ChessGame(chessboard, FEN_TEST_LOSING_CASTLE_RIGHTS_1_WHITE_MOVES_DEPTH_2_KD7);
        assertFalse(chessboard.isQueensideCastlingPossibleFromFEN(PlayerColor.BLACK), "Black should not be able to castle queenside.");
        assertFalse(chessboard.isQueensideCastlingPossibleFromFEN(PlayerColor.WHITE), "White should not be able to castle queenside.");
        assertFalse(chessboard.isKingsideCastlingPossibleFromFEN(PlayerColor.BLACK), "Black should not be able to castle kingside.");
        assertTrue(chessboard.isKingsideCastlingPossibleFromFEN(PlayerColor.WHITE), "White should be able to castle kingside.");
        long result = bulkPerft(2);
        assertEquals(1091L, result, "There should be 1091L legal moves (leaf nodes).");
    }

    @Test
    void bulkPerftLosingCastleRights_1_Rxa8_Kf7_WhiteMovesDepth2() {
        gameState = new ChessGame(chessboard, FEN_TEST_LOSING_CASTLE_RIGHTS_1_WHITE_MOVES_DEPTH_2_KF7);
        assertFalse(chessboard.isQueensideCastlingPossibleFromFEN(PlayerColor.BLACK), "Black should not be able to castle queenside.");
        assertFalse(chessboard.isQueensideCastlingPossibleFromFEN(PlayerColor.WHITE), "White should not be able to castle queenside.");
        assertFalse(chessboard.isKingsideCastlingPossibleFromFEN(PlayerColor.BLACK), "Black should not be able to castle kingside.");
        assertTrue(chessboard.isKingsideCastlingPossibleFromFEN(PlayerColor.WHITE), "White should be able to castle kingside.");
        long result = bulkPerft(2);
        assertEquals(1048L, result, "There should be 1048L legal moves (leaf nodes).");
    }

    @Test
    void bulkPerftLosingCastleRights_1_Rxa8_bc8_WhiteMovesDepth2() {
        gameState = new ChessGame(chessboard, FEN_TEST_LOSING_CASTLE_RIGHTS_1_WHITE_MOVES_DEPTH_2_BC8);
        assertFalse(chessboard.isQueensideCastlingPossibleFromFEN(PlayerColor.BLACK), "Black should not be able to castle queenside.");
        assertFalse(chessboard.isQueensideCastlingPossibleFromFEN(PlayerColor.WHITE), "White should not be able to castle queenside.");
        assertTrue(chessboard.isKingsideCastlingPossibleFromFEN(PlayerColor.BLACK), "Black should be able to castle kingside.");
        assertTrue(chessboard.isKingsideCastlingPossibleFromFEN(PlayerColor.WHITE), "White should be able to castle kingside.");
        long result = bulkPerft(2);
        assertEquals(693L, result, "There should be 693L legal moves (leaf nodes).");
    }

    @Test
    void bulkPerftLosingCastleRights_1_Rxa8_bxa8_WhiteMovesDepth2() {
        gameState = new ChessGame(chessboard, FEN_TEST_LOSING_CASTLE_RIGHTS_1_WHITE_MOVES_DEPTH_2_BxA8);
        assertFalse(chessboard.isQueensideCastlingPossibleFromFEN(PlayerColor.BLACK), "Black should not be able to castle queenside.");
        assertFalse(chessboard.isQueensideCastlingPossibleFromFEN(PlayerColor.WHITE), "White should not be able to castle queenside.");
        assertTrue(chessboard.isKingsideCastlingPossibleFromFEN(PlayerColor.BLACK), "Black should be able to castle kingside.");
        assertTrue(chessboard.isKingsideCastlingPossibleFromFEN(PlayerColor.WHITE), "White should be able to castle kingside.");
        long result = bulkPerft(2);
        assertEquals(518L, result, "There should be 518L legal moves (leaf nodes).");
    }

    @Test
    void bulkPerftLosingCastleRights_1_Rxa8_bxa8_Kf1_BlackMovesDepth1() {
        gameState = new ChessGame(chessboard, FEN_TEST_LOSING_CASTLE_RIGHTS_1_BLACK_MOVES_DEPTH_1_KF1);
        assertFalse(chessboard.isQueensideCastlingPossibleFromFEN(PlayerColor.BLACK), "Black should not be able to castle queenside.");
        assertFalse(chessboard.isQueensideCastlingPossibleFromFEN(PlayerColor.WHITE), "White should not be able to castle queenside.");
        assertTrue(chessboard.isKingsideCastlingPossibleFromFEN(PlayerColor.BLACK), "Black should be able to castle kingside.");
        assertFalse(chessboard.isKingsideCastlingPossibleFromFEN(PlayerColor.WHITE), "White should be able to castle kingside.");
        long result = bulkPerft(1);
        assertEquals(35L, result, "There should be 35 legal moves (leaf nodes).");
        /* Stockfish () vs ParkYChesS () "b3k2r/6bq/8/8/8/8/7B/5K1R b k - 0 1"*/
    }

    @Test
    void bulkPerftLosingCastleRights_1_Rxa8_bxa8_Rf1_BlackMovesDepth1() {
        gameState = new ChessGame(chessboard, FEN_TEST_LOSING_CASTLE_RIGHTS_1_BLACK_MOVES_DEPTH_1_RF1);
        assertFalse(chessboard.isQueensideCastlingPossibleFromFEN(PlayerColor.BLACK), "Black should not be able to castle queenside.");
        assertFalse(chessboard.isQueensideCastlingPossibleFromFEN(PlayerColor.WHITE), "White should not be able to castle queenside.");
        assertTrue(chessboard.isKingsideCastlingPossibleFromFEN(PlayerColor.BLACK), "Black should be able to castle kingside.");
        assertFalse(chessboard.isKingsideCastlingPossibleFromFEN(PlayerColor.WHITE), "White should be able to castle kingside.");
        long result = bulkPerft(1);
        assertEquals(32L, result, "There should be 32 legal moves (leaf nodes).");
        /* Stockfish () vs ParkYChesS () "b3k2r/6bq/8/8/8/8/7B/5K1R b k - 0 1"*/
    }

    @Test
    void bulkPerftLosingCastleRights_1_Rxa8_bxa8_Bb8_BlackMovesDepth1() {
        gameState = new ChessGame(chessboard, FEN_TEST_LOSING_CASTLE_RIGHTS_1_BLACK_MOVES_DEPTH_1_BB8);
        assertFalse(chessboard.isQueensideCastlingPossibleFromFEN(PlayerColor.BLACK), "Black should not be able to castle queenside.");
        assertFalse(chessboard.isQueensideCastlingPossibleFromFEN(PlayerColor.WHITE), "White should not be able to castle queenside.");
        assertTrue(chessboard.isKingsideCastlingPossibleFromFEN(PlayerColor.BLACK), "Black should be able to castle kingside.");
        assertTrue(chessboard.isKingsideCastlingPossibleFromFEN(PlayerColor.WHITE), "White should be able to castle kingside.");
        long result = bulkPerft(1);
        assertEquals(36L, result, "There should be 36 legal moves (leaf nodes).");
        /* Stockfish vs ParkYChesS*/
    }

    @Test
    void bulkPerftLosingCastleRights_1_Rxa8_bxa8_Be5_BlackMovesDepth1() {
        gameState = new ChessGame(chessboard, FEN_TEST_LOSING_CASTLE_RIGHTS_1_BLACK_MOVES_DEPTH_1_BE5);
        assertFalse(chessboard.isQueensideCastlingPossibleFromFEN(PlayerColor.BLACK), "Black should not be able to castle queenside.");
        assertFalse(chessboard.isQueensideCastlingPossibleFromFEN(PlayerColor.WHITE), "White should not be able to castle queenside.");
        assertTrue(chessboard.isKingsideCastlingPossibleFromFEN(PlayerColor.BLACK), "Black should be able to castle kingside.");
        assertTrue(chessboard.isKingsideCastlingPossibleFromFEN(PlayerColor.WHITE), "White should be able to castle kingside.");
        long result = bulkPerft(1);
        assertEquals(32L, result, "There should be 32 legal moves (leaf nodes).");
        /* Stockfish vs ParkYChesS */
    }

    @Test
    void bulkPerftLosingCastleRights_1a_BlackMovesDepth4() {
        gameState = new ChessGame(chessboard, FEN_TEST_LOSING_CASTLE_RIGHTS_1A_BLACK_MOVES_DEPTH_4);
        long result = bulkPerft(4);
        assertEquals(1_274_206L, result, "There should be 1_274_206L legal moves (leaf nodes).");
    }

    @Test
    void bulkPerftLosingCastleRights_2_WhiteMovesDepth4() {
        gameState = new ChessGame(chessboard, FEN_TEST_LOSING_CASTLE_RIGHTS_2_WHITE_MOVES);
        long result = bulkPerft(4);
        assertEquals(1_720_476L, result, "There should be 1_720_476L legal moves (leaf nodes).");
    }

    @Test
    void bulkPerftLosingCastleRights_2_BlackMovesDepth4() {
        gameState = new ChessGame(chessboard, FEN_TEST_LOSING_CASTLE_RIGHTS_2_BLACK_MOVES);
        long result = bulkPerft(4);
        assertEquals(1_720_476L, result, "There should be 1_720_476L legal moves (leaf nodes).");
    }

        /**
         * Compare the result of this function applied to your moves generator to the
         * established result (e.g. computed by Stockfish) at the given depth.
         * @param depth initial depth of moves tree search.
         * @return number of leaf nodes (legal moves) in a chess position.
         * @see "https://www.chessprogramming.org/Perft_Results"
         */
    private long bulkPerft (int depth) {
        assert depth > 0 : "Depth should be greater than 0";
        long totalNumberOfPNodes = 0;
        Collection<Move> movesList = gameState.getLegalMovesForColor(gameState.getCurrentColor());
        System.out.println("Legal moves for " + gameState.getCurrentColor() + " at depth " + depth + ": " + movesList);
        long numberOfNodesFromPosition = movesList.size();
        if (depth == 1) {
            gameState.switchCurrentPlayerColor();
            return numberOfNodesFromPosition;
        }
       // LOGGER.info("Depth: {}, {}: {}", depth, gameState.getCurrentColor().toString(), movesList);
        for (Move move : movesList) {
            System.out.println("Move " + move);
            gameState.makeMove(move, movesList);
            gameState.switchCurrentPlayerColor();
            numberOfNodesFromPosition = bulkPerft(depth - 1);
            totalNumberOfPNodes = totalNumberOfPNodes + numberOfNodesFromPosition;
            gameState.undo(move);
        }
        gameState.switchCurrentPlayerColor();
        return totalNumberOfPNodes;
    }
}