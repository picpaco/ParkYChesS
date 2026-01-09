package com.marcopiccionitraining.parkychess;

import com.marcopiccionitraining.parkychess.model.Board;
import com.marcopiccionitraining.parkychess.model.ChessGame;
import com.marcopiccionitraining.parkychess.model.PerftUtils;
import com.marcopiccionitraining.parkychess.model.PlayerColor;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import com.marcopiccionitraining.parkychess.model.pieces.King;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

import static com.marcopiccionitraining.parkychess.model.FENPositions.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest @Slf4j
public class BulkPerftTests {
    private ChessGame gameState;
    private PerftUtils ptu;

    @BeforeEach
    void setup() {
    }

    @Test
    void bulkPerft_id_001_NumberOfPositionsSimplestPlayablePositionDepth0() {
        gameState = new ChessGame(FEN_SIMPLEST_PLAYABLE);
        ptu = new PerftUtils(gameState);
        assertThrowsExactly(AssertionError.class, () ->{
            ptu.bulkPerft(0);
            }, "Passing depth 0mshould cause an AssertionError.");
    }
    @Test
    void bulkPerf_id_002_tNumberOfPositionsSimplestPlayablePositionDepth1() {
        gameState = new ChessGame(FEN_SIMPLEST_PLAYABLE);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(1);
        assertEquals(4L, result, "There are 4 possible positions at depth 1");
    }
    @Test
    void bulkPerft_id_003_NumberOfPositionsSimplestPlayablePositionDepth2() {
        gameState = new ChessGame (FEN_SIMPLEST_PLAYABLE);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(2);
        assertEquals(12L, result, "There are 12 possible positions at depth 2");
    }
    @Test
    void bulkPerft_id_004_oneBlackOneWhitePawnAtHomeDepth1() {
        gameState = new ChessGame(FEN_ONE_BLACK_ONE_WHITE_PAWN_AT_STARTING_SQUARE);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(1);
        assertEquals(5L, result, "There are 5 possible positions at depth 1");
    }
    @Test
    void bulkPerft_id_005_oneBlackOneWhitePawnAtHomeDepth2() {
        gameState = new ChessGame(FEN_ONE_BLACK_ONE_WHITE_PAWN_AT_STARTING_SQUARE);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(2);
        assertEquals(25L, result, "There are 25 possible positions at depth 2");
    }
    @Test
    void bulkPerft_id_006_oneBlackOneWhitePawnAtHomeOnAdjacentColumnsDepth1() {
        gameState = new ChessGame(FEN_ONE_BLACK_ONE_WHITE_PAWN_AT_STARTING_SQUARE_ON_ADJACENT_COLUMNS);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(1);
        assertEquals(5L, result, "There are 5 possible positions at depth 1");
    }
    @Test
    void bulkPerft_id_007_oneBlackOneWhitePawnAtHomeOnAdjacentColumnsDepth2() {
        gameState = new ChessGame(FEN_ONE_BLACK_ONE_WHITE_PAWN_AT_STARTING_SQUARE_ON_ADJACENT_COLUMNS);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(2);
        assertEquals(25L, result, "There are 25 possible positions at depth 2");
    }
    @Test
    void bulkPerft_id_008_twoBlackTwoWhitePawnsAllAtHomeDepth1() {
        gameState = new ChessGame(FEN_TWO_BLACK_TWO_WHITE_PAWNS_AT_STARTING_SQUARE);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(1);
        assertEquals(7L, result, "There are 7 possible positions at depth 1");
    }
    @Test
    void bulkPerft_id_009_twoBlackTwoWhitePawnsAllAtHomeDepth2() {
        gameState = new ChessGame(FEN_TWO_BLACK_TWO_WHITE_PAWNS_AT_STARTING_SQUARE);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(2);
        assertEquals(49L, result, "There are 49 possible positions at depth 2");
    }
    @Test
    void bulkPerft_id_010_NumberOfPositionsAtInitialPositionDepth1() {
        gameState = new ChessGame(FEN_INITIAL_POSITION);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(1);
        assertEquals(20L, result, "From the initial position there are 20 possible legal moves at depth 1");
    }
    @Test
    void bulkPerft_id_011_NumberOfPositionsAtInitialPositionDepth2() {
        gameState = new ChessGame(FEN_INITIAL_POSITION);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(2);
        assertEquals(400L, result, "From the initial position there are 400 possible legal moves at depth 2");
    }
    @Test
    void bulkPerft_id_012_NumberOfPositionsAtInitialPositionDepth3() {
        gameState = new ChessGame(FEN_INITIAL_POSITION);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(3);
        assertEquals(8902L, result, "From the initial position there are 8902 possible legal moves at depth 3");
    }
    @Test
    void bulkPerft_id_013_NumberOfPositionsAtInitialPositionDepth4() {
        gameState = new ChessGame(FEN_INITIAL_POSITION);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(4);
        assertEquals(197_281L, result, "From the initial position there are 197_281 possible legal moves at depth 3");
        //takes <1s
    }
    @Test
    void bulkPerft_id_014_NumberOfPositionsAtInitialPositionDepth5() {
        gameState = new ChessGame(FEN_INITIAL_POSITION);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(5);
        assertEquals(4_865_609L, result, "From the initial position there are 4_865_609 possible legal moves at depth 5");
        //takes 11s
    }
    //@Test
    void bulkPerft_id_015_NumberOfPositionsAtInitialPositionDepth6() {
        gameState = new ChessGame(FEN_INITIAL_POSITION);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(6);
        assertEquals(119_060_324L, result, "From the initial position there are 119_060_324 possible legal moves at depth 6");
        //takes 4m 49s
    }
    //@Test
    void bulkPerft_id_016_NumberOfPositionsAtInitialPositionDepth7() {
        gameState = new ChessGame(FEN_INITIAL_POSITION);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(7);
        assertEquals(3_195_901_860L, result, "From the initial position there are 3_195_901_860 possible legal moves at depth 3");
        //takes too long > 12h
    }
    @Test
    void bulkPerft_id_017_NumberOfPositionsAfterNa3Depth4(){
        gameState = new ChessGame(FEN_INITIAL_POSITION_NA3);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(4);
        assertEquals(198_572L, result, "From the initial position there are 198_572 possible positions at depth 4");
    }
    @Test
    void bulkPerft_id_018_perftNumberOfPositionsAfterE3Depth4() {
        gameState = new ChessGame(FEN_POSITION_AFTER_E3);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(4);
        assertEquals(402_988L, result, "From the initial position there are 402_988 possible positions at depth 4");
    }
    @Test
    void bulkPerft_id_019_NumberOfPositionsAfterE3_D6Depth4() {
        gameState = new ChessGame(FEN_POSITION_AFTER_E3_D6);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(3);
        assertEquals(23_960L, result, "From the initial position there are 23_960L possible positions at depth 3");
    }
    @Test
    void bulkPerft_id_020_NumberOfPositionsAfterE3_D6_QG4Depth2() {
        gameState = new ChessGame(FEN_POSITION_AFTER_E3_D6_QG4);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(2);
        assertEquals(1_111L, result, "From the initial position there are 1_111L possible positions at depth 2");
    }
    @Test
    void bulkPerft_id_021_NumberOfPositionsAfterE3_D6_Bb5Depth2() {
        gameState = new ChessGame(FEN_POSITION_AFTER_E3_D6_BB5);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(2);
        assertEquals(168L, result, "From the initial position there are 168 possible positions at depth 2");
    }
    @Test
    void bulkPerft_id_022_NumberOfPositionsAfterE3_D6_Bb5_Bd7Depth2() {
        gameState = new ChessGame(FEN_POSITION_AFTER_E3_D6_BB5_BD7);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(1);
        assertEquals(34L, result, "From the initial position there are 34 possible positions at depth 1");
    }
    @Test
    void bulkPerft_id_023_NumberOfPositionsAfter_a4Depth5() {
        gameState = new ChessGame(FEN_POSITION_AFTER_A4);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(5);
        assertEquals(5_363_555L, result, "From the initial position there are 5_363_555 possible positions at depth 5");
    }
    @Test
    void bulkPerft_id_024_NumberOfPositionsAfter_a4_Depth4() {
        gameState = new ChessGame(FEN_POSITION_AFTER_A4_C6);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(4);
        assertEquals(244_282L, result, "From the initial position there are 244_282 possible positions at depth 4");
    }
    @Test
    void bulkPerft_id_025_NumberOfPositionsAfter_a4_c6_a5Depth3() {
        gameState = new ChessGame(FEN_POSITION_AFTER_A4_C6_A5);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(3);
        assertEquals(10_292L, result, "From the initial position there are 10_292 possible positions at depth 3");
    }
    @Test
    void bulkPerft_id_026_NumberOfPositionsAfter_a4_e5Depth4() {
        gameState = new ChessGame(FEN_POSITION_AFTER_A4_E5);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(4);
        assertEquals(447_849L, result, "From the initial position there are 447_849 possible positions at depth 4");
    }
    @Test
    void bulkPerft_id_027_NumberOfPositionsAfter_a4_e5_d4Depth3() {
        gameState = new ChessGame(FEN_POSITION_AFTER_A4_E5_D4);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(3);
        assertEquals(28_212L, result, "From the initial position there are 28_212 possible positions at depth 3");
    }
    @Test
    void bulkPerft_id_028_NumberOfPositionsAfter_a4_Nc6Depth4() {
        gameState = new ChessGame(FEN_POSITION_AFTER_A4_NC6);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(4);
        assertEquals(257_893L, result, "From the initial position there are 257_893 possible positions at depth 4");
    }
    @Test
    void bulkPerft_id_029_NumberOfPositionsAfter_a4_Nc6_a5Depth3() {
        gameState = new ChessGame(FEN_POSITION_AFTER_A4_NC6_A5);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(3);
        assertEquals(10_729L, result, "From the initial position there are 10_729 possible positions at depth 3");
    }
    @Test
    void bulkPerft_id_030_umberOfPositionsAfter_a4_a5Depth4() {
        gameState = new ChessGame(FEN_POSITION_AFTER_A4_A5);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(4);
        assertEquals(204_508L, result, "From the initial position there are 204_508 possible positions at depth 4");
    }
    @Test
    void bulkPerft_id_031_NumberOfPositionsAfter_a4_a5_b4Depth3() {
        gameState = new ChessGame(FEN_POSITION_AFTER_A4_A5_B4);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(3);
        assertEquals(10_798L, result, "From the initial position there are 10_798 possible positions at depth 3");
    }
    @Test
    void bulkPerft_id_032_NumberOfPositionsAfter_a4_a5_b4_a5xb4Depth2() {
        gameState = new ChessGame(FEN_POSITION_AFTER_A4_A5_B4_A5XB4);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(2);
        assertEquals(488, result, "From the initial position there are 488 possible positions at depth 2");
    }
    @Test
    void bulkPerft_id_033_NumberOfPositionsAfter_a4_a5_b4_a5xb4_c4Depth1() {
        gameState = new ChessGame(FEN_POSITION_AFTER_A4_A5_B4_A5XB4_C4);
        log.debug("Position: {}", gameState.getChessboard());
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(1);
        assertEquals(24, result, "From the initial position there are 24 possible positions at depth 1");
    }
    @Test
    void bulkPerft_id_034_NumberOfPositionsAfter_a4_a5_b4_ra6Depth2() {
        gameState = new ChessGame(FEN_POSITION_AFTER_A4_A5_B4_RA6);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(2);
        assertEquals(592L, result, "From the initial position there are 592 possible positions at depth 2");
    }
    @Test
    void bulkPerft_id_035_NumberOfPositionsAfter_a4_a5_b4_ra6_b4a5Depth1() {
        gameState = new ChessGame(FEN_POSITION_AFTER_A4_A5_B4_RA6_PA4B5);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(1);
        assertEquals(27L, result, "From the initial position there are 27 possible positions at depth 1");
    }
    @Test
    void bulkPerft_id_036_KiwiPeteNumberOfPositionsDepth1() {
        gameState = new ChessGame(FEN_KIWIPETE_POSITION);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(1);
        assertEquals(48L, result, "There should be 48 legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_037_KiwiPeteNumberOfPositionsDepth2() {
        gameState = new ChessGame(FEN_KIWIPETE_POSITION);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(2);
        assertEquals(2039L, result, "There should be 2039 legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_038_KiwiPeteNumberOfPositionsDepth3() {
        gameState = new ChessGame(FEN_KIWIPETE_POSITION);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(3);
        assertEquals(97_862L, result, "There should be 97862 legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_039_AvoidIllegalEnPassantCaptureWhiteMovesDepth6() {
        gameState = new ChessGame(FEN_TEST_AVOID_ILLEGAL_EP_CAPTURE_WHITE_MOVES);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(6);
        assertEquals(824_064L, result, "There should be 824_064 legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_040_AvoidIllegalEnPassantCaptureBlackMovesDepth6() {
        gameState = new ChessGame(FEN_TEST_AVOID_ILLEGAL_EP_CAPTURE_BLACK_MOVES);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(6);
        assertEquals(824_064L, result, "There should be 824_064 legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_041_EnPassantCaptureCheckOpponentWhiteMovesDepth6() {
        gameState = new ChessGame(FEN_TEST_EP_CAPTURE_CHECK_OPPONENT_WHITE_MOVES);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(6);
        assertEquals(1_440_467L, result, "There should be 1_440_467 legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_042_EnPassantCaptureCheckOpponentBlackMovesDepth6() {
        gameState = new ChessGame(FEN_TEST_EP_CAPTURE_CHECK_OPPONENT_BLACK_MOVES);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(6);
        assertEquals(1_440_467L, result, "There should be 1_440_467 legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_043_ShortCastleWithCheckWhiteMovesDepth6() {
        gameState = new ChessGame(FEN_TEST_SHORT_CASTLE_WITH_CHECK_WHITE_MOVES);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(6);
        assertEquals(661_072L, result, "There should be 661_072 legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_044_ShortCastleWithCheckBlackMovesDepth6() {
        gameState = new ChessGame(FEN_TEST_SHORT_CASTLE_WITH_CHECK_BLACK_MOVES);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(6);
        assertEquals(661_072L, result, "There should be 661_072 legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_045_LongCastleWithCheckWhiteMovesDepth6() {
        gameState = new ChessGame(FEN_TEST_LONG_CASTLE_WITH_CHECK_WHITE_MOVES);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(6);
        assertEquals(803_711L, result, "There should be 803_711 legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_046_LongCastleWithCheckBlackMovesDepth6() {
        gameState = new ChessGame(FEN_TEST_LONG_CASTLE_WITH_CHECK_BLACK_MOVES);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(6);
        assertEquals(803_711L, result, "There should be 803_711 legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_047_LosingCastleRights_0_Rb1_BlackMovesDepth1() {
        gameState = new ChessGame(FEN_KING_ROOK_NO_CHECK_LOSING_CASTLE_RIGHTS_BOTH_COLORS_RB1_D1);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(1);
        assertEquals(15L, result, "There should be 15 legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_048_LosingCastleRights_0_Kd1_BlackMovesDepth1() {
        gameState = new ChessGame(FEN_KING_ROOK_NO_CHECK_LOSING_CASTLE_RIGHTS_BOTH_COLORS_KD1_D1);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(1);
        assertEquals(15L, result, "There should be 15 legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_049_LosingCastleRights_0_WhiteMovesDepth2() {
        gameState = new ChessGame(FEN_KING_ROOK_NO_CHECK_LOSING_CASTLE_RIGHTS_BOTH_COLORS_W_MOVES);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(2);
        assertEquals(221L, result, "There should be 221 legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_050_LosingCastleRights_0_BlackMovesDepth3() {
        gameState = new ChessGame(FEN_KING_ROOK_CHECK_LOSING_CASTLE_RIGHTS);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(3);
        assertEquals(951L, result, "There should be 951 legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_051_LosingCastleRights_0_WhiteMovesDepth4() {
        gameState = new ChessGame(FEN_KING_ROOK_NO_CHECK_LOSING_CASTLE_RIGHTS_BOTH_COLORS_W_MOVES);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(4);
        assertEquals(57_515L, result, "There should be 57515 legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_052_LosingCastleRights_0_BlackMovesDepth5() {
        gameState = new ChessGame(FEN_KING_ROOK_NO_CHECK_LOSING_CASTLE_RIGHTS_BOTH_COLORS_B_MOVES);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(5);
        assertEquals(656_771L, result, "There should be 656_771 legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_053_LosingCastleRights_0_WhiteCastledBlackMovesDepth5() {
        gameState = new ChessGame(FEN_KING_ROOK_NO_CHECK_WHITE_LOST_CASTLE_RIGHTS);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(5);
        assertEquals(656_771L, result, "There should be 656_771 legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_054_LosingCastleRights_0_WhiteMovesDepth6() {
        gameState = new ChessGame(FEN_KING_ROOK_NO_CHECK_LOSING_CASTLE_RIGHTS_BOTH_COLORS_W_MOVES);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(6);
        assertEquals(15_874_227L, result, "There should be 15_874_227 legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_055_LosingCastleRights_0a_BlackMovesDepth1() {
        gameState = new ChessGame(FEN_NO_CHECK_LOST_CASTLE_RIGHTS_0A);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(1);
        assertEquals(10L, result, "There should be 10 legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_056_LosingCastleRights_0a_BlackMovesDepth2() {
        gameState = new ChessGame(FEN_NO_CHECK_LOST_CASTLE_RIGHTS_0A);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(2);
        assertEquals(90L, result, "There should be 90 legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_057_LosingCastleRights_0a_BlackMovesDepth3() {
        gameState = new ChessGame(FEN_NO_CHECK_LOST_CASTLE_RIGHTS_0A);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(3);
        assertEquals(1134L, result, "There should be 1134 legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_058_LosingCastleRights_0a_BlackMovesDepth4() {
        gameState = new ChessGame(FEN_NO_CHECK_LOST_CASTLE_RIGHTS_0A);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(4);
        assertEquals(15607L, result, "There should be 15607 legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_059_LosingCastleRights_0a_BlackMovesDepth5() {
        gameState = new ChessGame(FEN_NO_CHECK_LOST_CASTLE_RIGHTS_0A);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(5);
        assertEquals(236169L, result, "There should be 236169 legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_060_LosingCastleRights_0a_BlackMovesDepth6() {
        gameState = new ChessGame(FEN_NO_CHECK_LOST_CASTLE_RIGHTS_0A);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(6);
        assertEquals(3_687_809L, result, "There should be 3_687_809 legal moves (leaf nodes).");
     /*
h7h6: 227471
h7h5: 259814
h8f8: 424079
h8g8: 461098
e8d7: 551144
e8e7: 465039
e8f7: 426733
e8d8: 282602
e8f8: 152519
e8g8: 437310

Nodes searched: 3687809
    */
    }
    @Test
    void bulkPerft_id_061_LosingCastleRights_0_BlackMovesRc1Depth1() {
        gameState = new ChessGame(FEN_KING_ROOK_NO_CHECK_LOSING_CASTLE_RIGHTS_BLACK_MOVES_D1);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(1);
        assertEquals(15, result, "There should be 15 legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_062_LostCastleRights_0_BothColorsCastledWhiteMovesDepth4() {
        gameState = new ChessGame(FEN_KING_ROOK_NO_CHECK_LOST_CASTLE_RIGHTS_BOTH_COLORS);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(4);
        assertEquals(51_509L, result, "There should be 51509 legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_063_LostCastleRights_0_WhiteCastledBlackMovesDepth5() {
        gameState = new ChessGame(FEN_KING_ROOK_NO_CHECK_WHITE_LOST_CASTLE_RIGHTS);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(5);
        assertEquals(656_771L, result, "There should be 656_771 legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_064_BothColorsLostCastleRights_0_BlackMovesDepth1() {
        gameState = new ChessGame(FEN_KING_ROOK_NO_CHECK_BOTH_COLORS_LOST_CASTLE_RIGHTS_D1);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(1);
        assertEquals(12L, result, "There should be 12 legal moves (leaf nodes).");
        /* 13; {e8g8=1} castling should not be possible at this point for black*/
    }
    @Test
    void bulkPerft_id_065_BothColorsLostCastleRights_0_WhiteMovesDepth2() {
        gameState = new ChessGame(FEN_KING_ROOK_NO_CHECK_BOTH_COLORS_LOST_CASTLE_RIGHTS_D2);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(2);
        assertEquals(179L, result, "There should be 179 legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_066_LostCastleRights_0_WhiteCastledBlackMovesDepth3() {
        gameState = new ChessGame(FEN_KING_ROOK_NO_CHECK_BOTH_COLORS_LOST_CASTLE_RIGHTS_D3);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(3);
        assertEquals(2087L, result, "There should be 2087 legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_067_LostCastleRights_0_WhiteCastledWhiteMovesDepth4() {
        gameState = new ChessGame(FEN_KING_ROOK_NO_CHECK_BOTH_COLORS_LOST_CASTLE_RIGHTS);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(4);
        assertEquals(18_700L, result, "There should be 18700 legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_068_LosingCastleRights_0a_Rg8_WhiteMovesDepth5() {
        gameState = new ChessGame(FEN_NO_CHECK_LOST_CASTLE_RIGHTS_0A_RG8);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(5);
        assertEquals(461_098L, result, "There should be 461_098 legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_069_LosingCastleRights_0a_Rf8_WhiteMovesDepth5() {
        gameState = new ChessGame(FEN_NO_CHECK_LOST_CASTLE_RIGHTS_0A_RF8);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(5);
        assertEquals(424_079L, result, "There should be 424079 legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_070_LosingCastleRights_0a_Ke7_WhiteMovesDepth5() {
        gameState = new ChessGame(FEN_NO_CHECK_LOST_CASTLE_RIGHTS_0A_KE7);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(5);
        assertEquals(465_039L, result, "There should be 465_039 legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_071_LosingCastleRights_0a_Kd7_WhiteMovesDepth5() {
        gameState = new ChessGame(FEN_NO_CHECK_LOST_CASTLE_RIGHTS_0A_KD7);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(5);
        assertEquals(551_144L, result, "There should be 551_144 legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_072_LosingCastleRights_0a_Kf7_WhiteMovesDepth5() {
        gameState = new ChessGame(FEN_NO_CHECK_LOST_CASTLE_RIGHTS_0A_KF7);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(5);
        assertEquals(426_733L, result, "There should be 426733 legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_073_LosingCastleRights_0a_Kd8_WhiteMovesDepth5() {
        gameState = new ChessGame(FEN_NO_CHECK_LOST_CASTLE_RIGHTS_0A_KD8);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(5);
        assertEquals(282_602L, result, "There should be 282_602 legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_074_LosingCastleRights_0a_Kf8_WhiteMovesDepth5() {
        gameState = new ChessGame(FEN_NO_CHECK_LOST_CASTLE_RIGHTS_0A_KF8);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(5);
        assertEquals(152_519L, result, "There should be 152_519 legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_075_LosingCastleRights_0b_BlackMovesDepth3() {
        gameState = new ChessGame (FEN_NO_CHECK_LOST_CASTLE_RIGHTS_0B);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(3);
        assertEquals(828L, result, "There should be 828 legal moves (leaf nodes).");
        /*
Stockfish vd ParkYChess
h7h6: 60 h7h6=54 <-- issue
h7h5: 66 h7h5=60 <-- issue
h8f8: 90 h8f8=90 OK
h8g8: 96 h8g8=96 OK
e8d7: 102 e8d7=102 OK
e8e7: 102 e8e7=102 OK
e8f7: 102 e8f7=102 OK
e8d8: 60 e8d8=60 OK
e8f8: 48 e8f8=48 OK
e8g8: 102 e8g8=102 OK
         */
    }
    @Test
    void bulkPerft_id_076_LosingCastleRights_1_WhiteMovesDepth4() {
        gameState = new ChessGame(FEN_TEST_LOSING_CASTLE_RIGHTS_1_WHITE_MOVES_DEPTH_4);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(4);
        assertEquals(1_274_206L, result, "There should be 1_274_206L legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_077_LosingCastleRights_1_Rxa8_BlackMovesDepth3() {
        gameState = new ChessGame(FEN_TEST_LOSING_CASTLE_RIGHTS_1_BLACK_MOVES_DEPTH_3_RxA8);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(3);
        assertEquals(4427L, result, "There should be 4427 legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_078_LosingCastleRights_1_Rxa8_bxa8_WhiteMovesDepth2() {
        gameState = new ChessGame(FEN_TEST_LOSING_CASTLE_RIGHTS_1_WHITE_MOVES_DEPTH_2_BxA8);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(2);
        assertEquals(518L, result, "There should be 518L legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_079_LosingCastleRights_1_Rxa8_Ke7_WhiteMovesDepth2() {
        gameState = new ChessGame(FEN_TEST_LOSING_CASTLE_RIGHTS_1_WHITE_MOVES_DEPTH_2_KE7);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(2);
        assertEquals(1077L, result, "There should be 1077L legal moves (leaf nodes).");
        King blackKing = gameState.getChessboard().getKing(PlayerColor.BLACK);
        assertTrue(blackKing.isFlaggedAsHavingAlreadyMoved(), "Black king should be flagged as already moved.");
    }
    @Test
    void bulkPerft_id_080_LosingCastleRights_1_Rxa8_Kd7_WhiteMovesDepth2() {
        gameState = new ChessGame(FEN_TEST_LOSING_CASTLE_RIGHTS_1_WHITE_MOVES_DEPTH_2_KD7);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(2);
        King blackKing = gameState.getChessboard().getKing(PlayerColor.BLACK);
        assertTrue(blackKing.isFlaggedAsHavingAlreadyMoved(), "Black king should be flagged as already moved.");
        assertEquals(1091L, result, "There should be 1091L legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_081_LosingCastleRights_1_Rxa8_Kf7_WhiteMovesDepth2() {
        gameState = new ChessGame(FEN_TEST_LOSING_CASTLE_RIGHTS_1_WHITE_MOVES_DEPTH_2_KF7);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(2);
        King blackKing = gameState.getChessboard().getKing(PlayerColor.BLACK);
        assertEquals(1048L, result, "There should be 1048L legal moves (leaf nodes).");
        assertTrue(blackKing.isFlaggedAsHavingAlreadyMoved(), "Black king should be flagged as already moved.");
    }
    @Test
    void bulkPerft_id_082_LosingCastleRights_1_Rxa8_bc8_WhiteMovesDepth2() {
        gameState = new ChessGame(FEN_TEST_LOSING_CASTLE_RIGHTS_1_WHITE_MOVES_DEPTH_2_BC8);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(2);
        assertEquals(693L, result, "There should be 693L legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_083_LosingCastleRights_1_Rxa8_bxa8_Kf1_BlackMovesDepth1() {
        gameState = new ChessGame(FEN_TEST_LOSING_CASTLE_RIGHTS_1_BLACK_MOVES_DEPTH_1_KF1);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(1);
        assertEquals(35L, result, "There should be 35 legal moves (leaf nodes).");
        /* Stockfish () vs ParkYChesS () "b3k2r/6bq/8/8/8/8/7B/5K1R b k - 0 1"*/
    }
    @Test
    void bulkPerft_id_084_LosingCastleRights_1_Rxa8_bxa8_Rf1_BlackMovesDepth1() {
        gameState = new ChessGame(FEN_TEST_LOSING_CASTLE_RIGHTS_1_BLACK_MOVES_DEPTH_1_RF1);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(1);
        assertEquals(32L, result, "There should be 32 legal moves (leaf nodes).");
        /* Stockfish () vs ParkYChesS () "b3k2r/6bq/8/8/8/8/7B/5K1R b k - 0 1"*/
    }
    @Test
    void bulkPerft_id_085_LosingCastleRights_1_Rxa8_bxa8_Bb8_BlackMovesDepth1() {
        gameState = new ChessGame(FEN_TEST_LOSING_CASTLE_RIGHTS_1_BLACK_MOVES_DEPTH_1_BB8);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(1);
        assertEquals(36L, result, "There should be 36 legal moves (leaf nodes).");
        /* Stockfish vs ParkYChesS*/
    }
    @Test
    void bulkPerft_id_086_LosingCastleRights_1_Rxa8_bxa8_Be5_BlackMovesDepth1() {
        gameState = new ChessGame(FEN_TEST_LOSING_CASTLE_RIGHTS_1_BLACK_MOVES_DEPTH_1_BE5);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(1);
        assertEquals(32L, result, "There should be 32 legal moves (leaf nodes).");
        /* Stockfish vs ParkYChesS */
    }
    @Test
    void bulkPerft_id_087_LosingCastleRights_1a_BlackMovesDepth4() {
        gameState = new ChessGame(FEN_TEST_LOSING_CASTLE_RIGHTS_1A_BLACK_MOVES_DEPTH_4);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(4);
        assertEquals(1_274_206L, result, "There should be 1_274_206L legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_088_LosingCastleRights_2_WhiteMovesDepth4() {
        gameState = new ChessGame(FEN_TEST_LOSING_CASTLE_RIGHTS_2_WHITE_MOVES);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(4);
        assertEquals(1_720_476L, result, "There should be 1_720_476L legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_089_LosingCastleRights_2_BlackMovesDepth4() {
        gameState = new ChessGame(FEN_TEST_LOSING_CASTLE_RIGHTS_2_BLACK_MOVES);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(4);
        assertEquals(1_720_476L, result, "There should be 1_720_476L legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_090_BlackRookHasMovedForGoodDepth3(){
        gameState = new ChessGame(FEN_BLACK_ROOK_H8_HAS_NOT_MOVED_FOR_GOOD);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(3);
        assertEquals(1197L, result, "There should be 1197 legal moves (leaf nodes).");
    }
    @Test
    void bulkPerft_id_091_whiteKingShoulBeMarkedAsAlreadyMovedInTheSimplestPlayablePositionDepth3() {
        gameState = new ChessGame(FEN_SIMPLEST_PLAYABLE);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(3);
        assertEquals(69, result, "There should be 69 legal moves (leaf nodes).");
        King king = gameState.getChessboard().getKing(PlayerColor.WHITE);
        assertTrue(king.isFlaggedAsHavingAlreadyMoved(),"King should be flagged as having already moved.");
    }
    @Test
    void bulkPerft_id_092_whiteKingShouldNotBeMarkedAsAlreadyMovedInTheSimplestPlayablePosition_h1g1_Depth2() {
        gameState = new ChessGame(FEN_SIMPLEST_PLAYABLE_2);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(2);
        assertEquals(18, result, "There should be 18 legal moves (leaf nodes).");
        King whiteKing = gameState.getChessboard().getKing(PlayerColor.WHITE);
        assertFalse(whiteKing.isFlaggedAsHavingAlreadyMoved(),"White king should not be flagged as having already moved.");
    }
    @Test
    void bulkPerft_id_093_whiteKingShoulBeMarkedAsAlreadyMovedInTheSimplestPlayablePosition_h1g1_h8h7_Depth1() {
        gameState = new ChessGame(FEN_SIMPLEST_PLAYABLE_3);
        ptu = new PerftUtils(gameState);
        log.debug("Position at start: {}", gameState.getChessboard());
        long result = ptu.bulkPerft(1);
        assertEquals(6, result, "There should be 6 legal moves (leaf nodes).");
        King whiteKing = gameState.getChessboard().getKing(PlayerColor.WHITE);
        assertTrue(whiteKing.isFlaggedAsHavingAlreadyMoved(),"White king should be flagged as having already moved.");
    }
    @Test
    void bulkPerft_id_094_whiteKingShoulBeMarkedAsAlreadyMovedInTheSimplestPlayablePositionDepth7() {
        gameState = new ChessGame(FEN_SIMPLEST_PLAYABLE);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(7);
        assertEquals(116242, result, "There should be 116242 legal moves (leaf nodes).");
        King king = gameState.getChessboard().getKing(PlayerColor.WHITE);
        assertTrue(king.isFlaggedAsHavingAlreadyMoved(),"King should be flagged as having already moved.");
    }
    //@Test
    void bulkPerft_id_095_whiteKingShoulBeMarkedAsAlreadyMovedInTheSimplestPlayablePositionDepth9() {
        gameState = new ChessGame(FEN_SIMPLEST_PLAYABLE);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(9);
        assertEquals(5_424_200L, result, "There should be 5_424_200L legal moves (leaf nodes).");
        King king = gameState.getChessboard().getKing(PlayerColor.WHITE);
        assertFalse(king.isFlaggedAsHavingAlreadyMoved(),"King should be flagged as having already moved.");
    }
    //@Test
    void bulkPerft_id_096_whiteKingShoulBeMarkedAsAlreadyMovedInTheSimplestPlayablePositionDepth11() {
        gameState = new ChessGame(FEN_SIMPLEST_PLAYABLE);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(11);
        assertEquals(258_215_746L, result, "There should be 258_215_746L legal moves (leaf nodes).");
        King king = gameState.getChessboard().getKing(PlayerColor.WHITE);
        assertTrue(king.isFlaggedAsHavingAlreadyMoved(),"King should be flagged as having already moved.");
    }
    @Test
    void bulkPerft_id_097_whiteKingShoulBeMarkedAsAlreadyMovedInTheSimplestCastlingPositionDepth3() {
        gameState = new ChessGame(FEN_TEST_LOSING_CASTLE_RIGHTS_2_SIMPLEST);
        ptu = new PerftUtils(gameState);
        long result = ptu.bulkPerft(3);
        assertEquals(1197, result, "There should be 1197 legal moves (leaf nodes).");
        King king = gameState.getChessboard().getKing(PlayerColor.WHITE);
    }
    @Test
    void bulkPerft_id_098_EnPassant_Minimal_Depth2() {
        gameState = new ChessGame(FEN_TWO_PAWNS_EN_PASSANT_POSITION_BASIC_DEPTH2);
        ptu = new PerftUtils(gameState);
        log.debug("Initial position:{}", gameState.getChessboard());
        long result = ptu.bulkPerft(2);
        assertEquals(22, result, "From the initial position there are 22 possible positions at depth 2.");
    /*
    Stockfish vs ParkyChesS
    a7a6: 5 vs 4 b5a6 is missing
    a7a5: 5 vs 4 b5a6 ep is missing
    h8g7: 4 OK
    h8h7: 4 OK
    h8g8: 4 OK
     */
    }

}