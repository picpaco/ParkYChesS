package com.marcopiccionitraining.parkychess;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.pieces.King;
import com.marcopiccionitraining.parkychess.model.pieces.Rook;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.marcopiccionitraining.parkychess.model.FENPositions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class PerftTests {
    private MoveGeneratorStatistics stats;
    private ChessGame gameState;
    private PerftUtils ptu;

    @BeforeEach
    void setup() {
        stats = new MoveGeneratorStatistics();
    }

    @Test
    void perft_id_001_NumberOfPositionsSimplestPlayablePositionDepth0() {
        gameState = new ChessGame(FEN_SIMPLEST_PLAYABLE);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(0, 0, stats);
        assertEquals(1L, result, "Perft (0) must evaluate to 1.");
        stats.setNumberOfLeaves(result);
        log.info(stats.toString());
    }
    @Test
    void perft_id_002_NumberOfPositionsSimplestPlayablePositionDepth1() {
        gameState = new ChessGame(FEN_SIMPLEST_PLAYABLE);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(1, 1, stats);
        assertEquals(4L, result, "There are 4 possible positions at depth 1");
        stats.setNumberOfLeaves(result);
        log.info(stats.toString());
    }
    @Test
    void perft_id_003_NumberOfPositionsSimplestPlayablePositionDepth2() {
        gameState = new ChessGame (FEN_SIMPLEST_PLAYABLE);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(2, 2, stats);
        assertEquals(12L, result, "There are 12 possible positions at depth 2");
        stats.setNumberOfLeaves(result);
        log.info(stats.toString());
    }
    @Test
    void perft_id_004_oneBlackOneWhitePawnAtHomeDepth1() {
        gameState = new ChessGame(FEN_ONE_BLACK_ONE_WHITE_PAWN_AT_STARTING_SQUARE);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(1, 1, stats);
        assertEquals(5L, result, "There are 5 possible positions at depth 1");
        stats.setNumberOfLeaves(result);
        log.info(stats.toString());
    }
    @Test
    void perft_id_005_oneBlackOneWhitePawnAtHomeDepth2() {
        gameState = new ChessGame(FEN_ONE_BLACK_ONE_WHITE_PAWN_AT_STARTING_SQUARE);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(2, 2, stats);
        assertEquals(25L, result, "There are 25 possible positions at depth 2");
        stats.setNumberOfLeaves(result);
        log.info(stats.toString());
    }
    @Test
    void perft_id_006_oneBlackOneWhitePawnAtHomeOnAdjacentColumnsDepth1() {
        gameState = new ChessGame(FEN_ONE_BLACK_ONE_WHITE_PAWN_AT_STARTING_SQUARE_ON_ADJACENT_COLUMNS);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(1, 1, stats);
        assertEquals(5L, result, "There are 5 possible positions at depth 1");
        stats.setNumberOfLeaves(result);
        log.info(stats.toString());
    }
    @Test
    void perft_id_007_oneBlackOneWhitePawnAtHomeOnAdjacentColumnsDepth2() {
        gameState = new ChessGame(FEN_ONE_BLACK_ONE_WHITE_PAWN_AT_STARTING_SQUARE_ON_ADJACENT_COLUMNS);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(2, 2, stats);
        assertEquals(25L, result, "There are 25 possible positions at depth 2");
        stats.setNumberOfLeaves(result);
        log.info(stats.toString());
    }
    @Test
    void perft_id_008_twoBlackTwoWhitePawnsAllAtHomeDepth1() {
        gameState = new ChessGame(FEN_TWO_BLACK_TWO_WHITE_PAWNS_AT_STARTING_SQUARE);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(1, 1, stats);
        assertEquals(7L, result, "There are 7 possible positions at depth 1");
        stats.setNumberOfLeaves(result);
        log.info(stats.toString());
    }
    @Test
    void perft_id_009_twoBlackTwoWhitePawnsAllAtHomeDepth2() {
        gameState = new ChessGame(FEN_TWO_BLACK_TWO_WHITE_PAWNS_AT_STARTING_SQUARE);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(2, 2, stats);
        assertEquals(49L, result, "There are 49 possible positions at depth 2");
        stats.setNumberOfLeaves(result);
        log.info(stats.toString());
    }
    @Test
    void perft_id_010_NumberOfPositionsAtInitialPositionDepth1() {
        gameState = new ChessGame(FEN_INITIAL_POSITION);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(1, 1, stats);
        assertEquals(20L, result, "From the initial position there are 20 possible legal moves at depth 1");
        stats.setNumberOfLeaves(result);
        log.info(stats.toString());
    }
    @Test
    void perft_id_011_NumberOfPositionsAtInitialPositionDepth2() {
        gameState = new ChessGame(FEN_INITIAL_POSITION);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(2, 2, stats);
        assertEquals(400L, result, "From the initial position there are 400 possible positions at depth 2");
        stats.setNumberOfLeaves(result);
        log.info(stats.toString());
    }
    @Test
    void perft_id_012_NumberOfPositionsAtInitialPositionDepth3() {
        gameState = new ChessGame(FEN_INITIAL_POSITION);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(3, 3, stats);
        assertEquals(8902L, result, "From the initial position there are 8902 possible positions at depth 3");
        stats.setNumberOfLeaves(result);
        log.info(stats.toString());
    }
    @Test
    void perft_id_013_NumberOfPositionsAtInitialPositionDepth4() {
        gameState = new ChessGame(FEN_INITIAL_POSITION);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(4, 4, stats);
        assertEquals(197_281L, result, "From the initial position there are 197_281 possible positions at depth 4");
        stats.setNumberOfLeaves(result);
        log.info(stats.toString());
        //takes 1s
    }
    @Test
    void perft_id_014_NumberOfPositionsAtInitialPositionDepth5() {
        gameState = new ChessGame(FEN_INITIAL_POSITION);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(5, 5, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(4_865_609L, result, "From the initial position there are 4_865_609 possible positions at depth 5");
        //   log.info(stats.toString());
        //takes 35s
    }
    //@Test
    void perft_id_015_NumberOfPositionsAtInitialPositionDepth6() {
        gameState = new ChessGame(FEN_INITIAL_POSITION);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(6, 6, stats);
        assertEquals(119_060_324L, result, "From the initial position there are 119_060_324 possible legal moves at depth 6");
        //takes 4m 49s
    }
    //@Test
    void perft_id_016_NumberOfPositionsAtInitialPositionDepth7() {
        gameState = new ChessGame(FEN_INITIAL_POSITION);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(7, 7, stats);
        assertEquals(3_195_901_860L, result, "From the initial position there are 3_195_901_860 possible legal moves at depth 3");
        //takes too long > 12h
    }
    @Test
    void perft_id_017_NumberOfPositionsAfterNa3Depth4(){
        gameState = new ChessGame(FEN_INITIAL_POSITION_NA3);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(4, 4, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(198_572L, result, "From the initial position there are 198_572 possible positions at depth 4");
    }
    @Test
    void perft_id_018_NumberOfPositionsAfterE3Depth4() {
        gameState = new ChessGame(FEN_POSITION_AFTER_E3);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(4, 4, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(402_988L, result, "From the initial position there are 402_988 possible positions at depth 4");
    }
    @Test
    void perft_id_019_NumberOfPositionsAfterE3_D6Depth3() {
        gameState = new ChessGame(FEN_POSITION_AFTER_E3_D6);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(3, 3, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(23_960L, result, "From the initial position there are 23_960L possible positions at depth 3");
    }
    @Test
    void perft_id_020_NumberOfPositionsAfterE3_D6_QG4Depth2() {
        gameState = new ChessGame(FEN_POSITION_AFTER_E3_D6_QG4);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(2, 2, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(1_111L, result, "From the initial position there are 1_111L possible positions at depth 2");
    }
    @Test
    void perft_id_021_NumberOfPositionsAfterE3_D6_Bb5Depth2() {
        gameState = new ChessGame(FEN_POSITION_AFTER_E3_D6_BB5);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(2, 2, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(168L, result, "From the initial position there are 168 possible positions at depth 2");
    }
    @Test
    void perft_id_022_NumberOfPositionsAfterE3_D6_Bb5_Bd7Depth2() {
        gameState = new ChessGame(FEN_POSITION_AFTER_E3_D6_BB5_BD7);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(1, 1, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(34L, result, "From the initial position there are 34 possible positions at depth 1");
    }
    @Test
    void perft_id_023_NumberOfPositionsAfter_a4Depth5() {
        gameState = new ChessGame(FEN_POSITION_AFTER_A4);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(5, 5, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(5_363_555L, result, "From the initial position there are 5_363_555 possible positions at depth 5");
    }
    @Test
    void perft_id_024_NumberOfPositionsAfter_a4_c6Depth4() {
        gameState = new ChessGame(FEN_POSITION_AFTER_A4_C6);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(4, 4, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(244_282L, result, "From the initial position there are 244_282 possible positions at depth 4");
    }
    @Test
    void perft_id_025_NumberOfPositionsAfter_a4_c6_a5Depth3() {
        gameState = new ChessGame(FEN_POSITION_AFTER_A4_C6_A5);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(3, 3, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(10_292L, result, "From the initial position there are 10_292 possible positions at depth 3");
    }
    @Test
    void perft_id_026_NumberOfPositionsAfter_a4_e5Depth4() {
        gameState = new ChessGame(FEN_POSITION_AFTER_A4_E5);
        ptu = new PerftUtils(gameState);
        log.debug("Current situation: {}", gameState.getChessboard());
        long result = ptu.perft(4, 4, stats);
        stats.setNumberOfLeaves(result);
        log.debug(stats.getMovesMap().toString());
        assertEquals(447_849L, result, "From the initial position there are 447_849 possible positions at depth 4");
    /*
    * Stockfish vs ParkyChesS:
    *b2b3: 20191 vs b2b3=20135
    c2c3: 19389 vs c2c3=19281
    d2d3: 25019 vs d2d3=24906
    e2e3: 28231 vs e2e3=28111
    f2f3: 17704 vs f2f3=17596
    g2g3: 20316 vs g2g3=20234
    h2h3: 18365 vs h2h3=18285
    a4a5: 18978 vs a4a5=18871
    b2b4: 19090 vs b2b4=18984
    c2c4: 20169 vs c2c4=20036
    d2d4: 28212 vs d2d4=26444
    e2e4: 25725 vs e2e4=25545
    f2f4: 20448 vs f2f4=19507
    g2g4: 20035 vs g2g4=19925
    h2h4: 20289 vs h2h4=20207
    b1a3: 20160 vs b1a3=20077
    b1c3: 21106 vs b1c3=21022
    g1f3: 21154 vs g1f3=21042
    g1h3: 19294 vs g1h3=19212
    a1a2: 19314 vs a1a2=19233
    a1a3: 24660 vs a1a3=24572
    Nodes searched: 447849 vs 443225
    * */
    }
    @Test
    void perft_id_027_NumberOfPositionsAfter_a4_e5_d4Depth3() {
        gameState = new ChessGame(FEN_POSITION_AFTER_A4_E5_D4);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(3, 3, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(28_212L, result, "From the initial position there are 28_212 possible positions at depth 3");
    }
    @Test
    void perft_id_028_NumberOfPositionsAfter_a4_Nc6Depth4() {
        gameState = new ChessGame(FEN_POSITION_AFTER_A4_NC6);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(4, 4, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(257_893L, result, "From the initial position there are 257_893 possible positions at depth 4");
    }
    @Test
    void perft_id_029_NumberOfPositionsAfter_a4_Nc6_a5Depth3() {
        gameState = new ChessGame(FEN_POSITION_AFTER_A4_NC6_A5);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(3, 3, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(10_729L, result, "From the initial position there are 10_729 possible positions at depth 3");
    }
    @Test
    void perft_id_030_NumberOfPositionsAfter_a4_a5Depth4() {
        gameState = new ChessGame(FEN_POSITION_AFTER_A4_A5);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(4, 4, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(204_508L, result, "From the initial position there are 204_508 possible positions at depth 4");
    }
    @Test
    void perft_id_031_NumberOfPositionsAfter_a4_a5_b4Depth3() {
        gameState = new ChessGame(FEN_POSITION_AFTER_A4_A5_B4);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(3, 3, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(10_798L, result, "From the initial position there are 10_798 possible positions at depth 3");
    /*
    ParkyChesS: moves map for BLACK at depth 3: {a8a6=592, a8a7=460, b8c6=505, b7b6=483, b7b5=486, b8a6=481, c7c6=462,
    d7d5=636, a5b4=487, c7c5=523, d7d6=613, e7e5=661, h7h5=482, e7e6=660, h7h6=438, g8f6=504, f7f6=438, f7f5=461,
    g8h6=460, g7g6=482, g7g5=483}
     Stockfish: "rnbqkbnr/1ppppppp/8/p7/PP6/8/2PPPPPP/RNBQKBNR b KQkq - 0 1"
b7b6: 483 OK
c7c6: 462 OK
d7d6: 613 OK
e7e6: 660 OK
f7f6: 438 OK
g7g6: 482 OK
h7h6: 438 OK
b7b5: 486 OK
c7c5: 523 OK
d7d5: 636 OK
e7e5: 661 OK
f7f5: 461 OK
g7g5: 483 OK
h7h5: 482 OK
a5b4: 488 <--------- NOK: it is 487 instead
b8a6: 481 OK
b8c6: 505 OK
g8f6: 504 OK
g8h6: 460 OK
a8a6: 592 OK
a8a7: 460 OK
Nodes searched: 10798
     */
    }
    @Test
    void perft_id_032_NumberOfPositionsAfter_a4_a5_b4_a5xb4Depth2() {
        gameState = new ChessGame(FEN_POSITION_AFTER_A4_A5_B4_A5XB4);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(2, 2, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(488, result, "From the initial position there are 488 possible positions at depth 2");
    /* ParkyChesS moves map for WHITE at depth 2: {h2h3=23, h2h4=23, a4a5=22, b1a3=24, c2c4=23, a1a2=23, c1a3=24, a1a3=24,
    d2d4=23, c2c3=24, e2e4=23, b1c3=24, d2d3=23, c1b2=23, e2e3=23, f2f4=23, f2f3=23, g2g4=23, g2g3=23, g1f3=23, g1h3=23}
c2c3: 24 OK
d2d3: 23 OK
e2e3: 23 OK
f2f3: 23 OK
g2g3: 23 OK
h2h3: 23 OK
a4a5: 22 OK
c2c4: 24 <----- NOK! ParkyChesS outputs 23 instead. In fact it does not take into account the ep capture b4xc3 after 1. c4.
d2d4: 23 OK
e2e4: 23 OK
f2f4: 23 OK
g2g4: 23 OK
h2h4: 23 OK
b1a3: 24 OK
b1c3: 24 OK
g1f3: 23 OK
g1h3: 23 OK
c1b2: 23 OK
c1a3: 24 OK
a1a2: 23 OK
a1a3: 24 OK
Nodes searched: 488
 */
    }

    @Test
    void perft_id_033_NumberOfPositionsAfter_a4_a5_b4_a5xb4_c4Depth1() {
        gameState = new ChessGame(FEN_POSITION_AFTER_A4_A5_B4_A5XB4_C4);
        log.debug("Position: {}", gameState.getChessboard());
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(1, 1, stats);
        stats.setNumberOfLeaves(result);
        log.debug("Moves map: {}", stats.getMovesMap().toString());
        assertEquals(24, result, "From the initial position there are 24 possible positions at depth 1");
    }
    @Test
    void perft_id_034_NumberOfPositionsAfter_a4_a5_b4_ra6Depth2() {
        gameState = new ChessGame(FEN_POSITION_AFTER_A4_A5_B4_RA6);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(2, 2, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(592L, result, "From the initial position there are 592 possible positions at depth 2");
    }
    @Test
    void perft_id_035_NumberOfPositionsAfter_a4_a5_b4_ra6_b4a5Depth1() {
        gameState = new ChessGame(FEN_POSITION_AFTER_A4_A5_B4_RA6_PA4B5);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(1, 1, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(27L, result, "From the initial position there are 27 possible positions at depth 1");
    }
    @Test
    void perft_id_036_KiwiPeteNumberOfPositionsDepth1() {
        gameState = new ChessGame(FEN_KIWIPETE_POSITION);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(1,1,stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(48L, result, "There should be 48 legal moves (leaf nodes).");
    }
    @Test
    void perftK_id_037_KiwiPeteNumberOfPositionsDepth2() {
        gameState = new ChessGame(FEN_KIWIPETE_POSITION);
        ptu = new PerftUtils(gameState);
        log.debug("Initial board: {}", gameState.getChessboard());
        long result = ptu.perft(2,2, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(2039L, result, "There should be 2039 legal moves (leaf nodes).");
        /*
        Stockfish vs. ParkyChesS
        a2a3: 44 vs a2a3=42
        b2b3: 42 vs b2b3=40
        g2g3: 42 vs g2g3=40
        d5d6: 41 vs d5d6=41 OK!
        a2a4: 44 vs a2a4=41
        g2g4: 42 vs g2g4=40
        g2h3: 43 vs g2h3=41
        d5e6: 46 vs d5e6=44
        c3b1: 42 vs c3b1=40
        c3d1: 42 vs c3d1=40
        c3a4: 42 vs c3a4=40
        c3b5: 39 vs c3b5=37
        e5d3: 43 vs e5d3=41
        e5c4: 42 vs e5c4=40
        e5g4: 44 vs e5g4=42
        e5c6: 41 vs e5c6=40
        e5g6: 42 vs e5g6=41
        e5d7: 45 vs e5d7=44
        e5f7: 44 vs e5f7=43
        d2c1: 43 vs d2c1=41
        d2e3: 43 vs d2e3=41
        d2f4: 43 vs d2f4=41
        d2g5: 42 vs d2g5=40
        d2h6: 41 vs d2h6=39
        e2d1: 44 vs e2d1=42
        e2f1: 44 vs e2f1=42
        e2d3: 42 vs e2d3=40
        e2c4: 41 vs e2c4=39
        e2b5: 39 vs e2b5=37
        e2a6: 36 vs e2a6=35
        a1b1: 43 vs a1b1=41
        a1c1: 43 vs a1c1=41
        a1d1: 43 vs a1d1=41
        h1f1: 43 vs h1f1=41
        h1g1: 43 vs h1g1=41
        f3d3: 42 vs f3d3=40
        f3e3: 43 vs f3e3=41
        f3g3: 43 vs f3g3=41
        f3h3: 43 vs f3h3=41
        f3f4: 43 vs f3f4=41
        f3g4: 43 vs f3g4=41
        f3f5: 45 vs f3f5=43
        f3h5: 43 vs f3h5=41
        f3f6: 39 vs f3f6=37
        e1d1: 43 vs e1d1=41
        e1f1: 43 vs e1f1=41
        e1g1: 43 vs e1g1=41
        e1c1: 43 vs e1c1=41
         48 positions*/
        /* Black's 44 reactions to d5e6 according to ParkyChesS:
        [a8b8, a8c8, a8d8, e8d8, e8f8, h8h7, h8h6, h8h5, h8h4, h8g8,
        h8f8, c7c6, c7c5, d7d6, d7d5, d7e6, e7d8, e7f8, e7d6, e7c5,
        e7e6, f7e6, g7f8, g7h6, a6b7, a6c8, a6b5, a6c4, a6d3, a6e2,
        b6c8, b6d5, b6c4, b6a4, f6g8, f6h7, f6d5, f6h5, f6e4, f6g4,
        g6g5, b4b3, b4c3, h3g2]
        Black's 46 reactions to d5e6 according to Stockfish:
        Same as above + e8c8, e8g8
         */
    }
    @Test
    void perft_id_038_KiwiPeteNumberOfPositionsDepth3() {
        gameState = new ChessGame(FEN_KIWIPETE_POSITION);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(3, 3, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(97_862L, result, "There should be 97862 legal moves (leaf nodes).");
        /* StockFish vs ParkYChesS 97778 Note that the corresponding bulkPerft test succeeds.*/
    }
    @Test
    void perft_id_039_AvoidIllegalEnPassantCaptureWhiteMovesDepth6() {
        gameState = new ChessGame(FEN_TEST_AVOID_ILLEGAL_EP_CAPTURE_WHITE_MOVES);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(6, 6, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(824_064L, result, "There should be 824_064 legal moves (leaf nodes).");
    }
    @Test
    void perft_id_040_AvoidIllegalEnPassantCaptureBlackMovesDepth6() {
        gameState = new ChessGame(FEN_TEST_AVOID_ILLEGAL_EP_CAPTURE_BLACK_MOVES);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(6, 6, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(824_064L, result, "There should be 824_064 legal moves (leaf nodes).");
    }
    @Test
    void perft_id_041_EnPassantCaptureCheckOpponentWhiteMovesDepth6() {
        gameState = new ChessGame(FEN_TEST_EP_CAPTURE_CHECK_OPPONENT_WHITE_MOVES);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(6, 6, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(1_440_467L, result, "There should be 1_440_467 legal moves (leaf nodes).");
    }
    @Test
    void perf_id_042_tEnPassantCaptureCheckOpponentBlackMovesDepth6() {
        gameState = new ChessGame(FEN_TEST_EP_CAPTURE_CHECK_OPPONENT_BLACK_MOVES);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(6,6,stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(1_440_467L, result, "There should be 1_440_467 legal moves (leaf nodes).");
    }
    @Test
    void perft_id_043_ShortCastleWithCheckWhiteMovesDepth6() {
        gameState = new ChessGame(FEN_TEST_SHORT_CASTLE_WITH_CHECK_WHITE_MOVES);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(6, 6, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(661_072L, result, "There should be 661_072 legal moves (leaf nodes).");
    }
    @Test
    void perft_id_044_ShortCastleWithCheckBlackMovesDepth6() {
        gameState = new ChessGame(FEN_TEST_SHORT_CASTLE_WITH_CHECK_BLACK_MOVES);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(6,6,stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(661_072L, result, "There should be 661_072 legal moves (leaf nodes).");
    }
    @Test
    void perft_id_045_LongCastleWithCheckWhiteMovesDepth6() {
        gameState = new ChessGame(FEN_TEST_LONG_CASTLE_WITH_CHECK_WHITE_MOVES);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(6,6,stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(803_711L, result, "There should be 803_711 legal moves (leaf nodes).");
    }
    @Test
    void perft_id_046_LongCastleWithCheckBlackMovesDepth6() {
        gameState = new ChessGame(FEN_TEST_LONG_CASTLE_WITH_CHECK_BLACK_MOVES);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(6,6,stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(803_711L, result, "There should be 803_711 legal moves (leaf nodes).");
    }
    @Test
    void perft_id_047_LosingCastleRights_0_Rb1_BlackMovesDepth1() {
        gameState = new ChessGame(FEN_KING_ROOK_NO_CHECK_LOSING_CASTLE_RIGHTS_BOTH_COLORS_RB1_D1);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(1,1,stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(15L, result, "There should be 15 legal moves (leaf nodes).");
    }
    @Test
    void perft_id_048_LosingCastleRights_0_Kd1_BlackMovesDepth1() {
        gameState = new ChessGame(FEN_KING_ROOK_NO_CHECK_LOSING_CASTLE_RIGHTS_BOTH_COLORS_KD1_D1);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(1, 1, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(15L, result, "There should be 15 legal moves (leaf nodes).");
    }
    @Test
    void perft_id_049_LosingCastleRights_0_WhiteMovesDepth2() {
        gameState = new ChessGame(FEN_KING_ROOK_NO_CHECK_LOSING_CASTLE_RIGHTS_BOTH_COLORS_W_MOVES);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(2,2,stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(221L, result, "There should be 221 legal moves (leaf nodes).");
        /* Issue: after 1.Ra1c1 Black moves should include ke8g8 (KINGSIDE_CASTLE)
         WHITE Moves map for Stockfish (221) and ParkYChesS (207) at depth 2:
        a1b1: 15 a1b1=15 OK
        a1c1: 15 a1c1=14
        a1d1: 13 a1d1=12
        a1a2: 15 a1a2=14
        a1a3: 15 a1a3=14
        a1a4: 15 a1a4=14
        a1a5: 15 a1a5=14
        a1a6: 15 a1a6=14
        a1a7: 12 a1a7=11
        a1a8: 3 a1a8=3 OK
        e1d1: 15 e1d1=14
        e1f1: 15 e1f1=14
        e1d2: 15 e1d2=14
        e1e2: 15 e1e2=14
        e1f2: 15 e1f2=14
        e1c1: 13 e1c1=12
         */
    }
    @Test
    void perft_id_050_LosingCastleRights_0_BlackMovesDepth3() {
        gameState = new ChessGame(FEN_KING_ROOK_CHECK_LOSING_CASTLE_RIGHTS);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(3, 3, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(951L, result, "There should be 951 legal moves (leaf nodes).");
    }
    @Test
    void perft_id_051_LosingCastleRights_0_WhiteMovesDepth4() {
        gameState = new ChessGame(FEN_KING_ROOK_NO_CHECK_LOSING_CASTLE_RIGHTS_BOTH_COLORS_W_MOVES);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(4, 4, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(57_515L, result, "There should be 57515 legal moves (leaf nodes).");
    }
    @Test
    void perft_id_052_LosingCastleRights_0_BlackMovesDepth5() {
        gameState = new ChessGame(FEN_KING_ROOK_NO_CHECK_LOSING_CASTLE_RIGHTS_BOTH_COLORS_B_MOVES);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(5, 5, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(656_771L, result, "There should be 656_771 legal moves (leaf nodes).");
    }
    @Test
    void perft_id_053_LosingCastleRights_0_WhiteCastledBlackMovesDepth5() {
        gameState = new ChessGame(FEN_KING_ROOK_NO_CHECK_WHITE_LOST_CASTLE_RIGHTS);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(5,5,stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(656_771L, result, "There should be 656_771 legal moves (leaf nodes).");
    }
    @Test
    void perft_id_054_LosingCastleRights_0_WhiteMovesDepth6() {
        gameState = new ChessGame(FEN_KING_ROOK_NO_CHECK_LOSING_CASTLE_RIGHTS_BOTH_COLORS_W_MOVES);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(6, 6, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(15_874_227L, result, "There should be 15_874_227 legal moves (leaf nodes).");
    }
    @Test
    void perft_id_055_LosingCastleRights_0a_BlackMovesDepth1() {
        gameState = new ChessGame(FEN_NO_CHECK_LOST_CASTLE_RIGHTS_0A);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(1,1,stats);
        assertEquals(10L, result, "There should be 10 legal moves (leaf nodes).");
    }
    @Test
    void perft_id_056_LosingCastleRights_0a_BlackMovesDepth2() {
        gameState = new ChessGame(FEN_NO_CHECK_LOST_CASTLE_RIGHTS_0A);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(2, 2, stats);
        assertEquals(90L, result, "There should be 90 legal moves (leaf nodes).");
    }
    @Test
    void perft_id_057_LosingCastleRights_0a_BlackMovesDepth3() {
        gameState = new ChessGame(FEN_NO_CHECK_LOST_CASTLE_RIGHTS_0A);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(3,3,stats);
        assertEquals(1134L, result, "There should be 1134 legal moves (leaf nodes).");
        /*
BLACK will now execute move h7h6 at depth 3
WHITE generated 9 moves at depth 2: [d2d3, d2d4, c1c2, c1b1, c1b2, d1e1, d1f1, d1g1, d1h1] OK
WHITE will now execute move d2d3 at depth 2 OK
BLACK generated 9 moves at depth 1: [e8d8, e8e7, e8f8, e8f7, e8d7, h8g8, h8f8, h8h7, h6h5] NOK
It fails to generate e8g8 (kingside castle) because black king appears as having already moved
Stockfish vs. ParkYChesS
h7h6: 80 h7h6=74
h7h5: 88 h7h5=82
h8f8: 122 h8f8=122 OK
h8g8: 130 h8g8=130 OK
e8d7: 150 e8d7=150 OK
e8e7: 139 e8e7=139 OK
e8f7: 136 e8f7=136 OK
e8d8: 88 e8d8=88 OK
e8f8: 64 e8f8=64 OK
e8g8: 137 e8g8=137 OK
 "4k2r/7p/8/8/8/8/3P4/2KR4 b k - 0 0"
         */
    }
    @Test
    void perft_id_058_LosingCastleRights_0a_BlackMovesDepth4() {
        gameState = new ChessGame(FEN_NO_CHECK_LOST_CASTLE_RIGHTS_0A);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(4, 4, stats);
        assertEquals(15607L, result, "There should be 15607 legal moves (leaf nodes).");
    }
    @Test
    void perft_id_059_LosingCastleRights_0a_BlackMovesDepth5() {
        gameState = new ChessGame(FEN_NO_CHECK_LOST_CASTLE_RIGHTS_0A);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(5,5,stats);
        assertEquals(236169L, result, "There should be 236169 legal moves (leaf nodes).");
    }
    @Test
    void perft_id_060_LosingCastleRights_0a_BlackMovesDepth6() {
        gameState = new ChessGame(FEN_NO_CHECK_LOST_CASTLE_RIGHTS_0A);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(6, 6, stats);
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
    void perft_id_061_LosingCastleRights_0_BlackMovesRc1Depth1() {
        gameState = new ChessGame(FEN_KING_ROOK_NO_CHECK_LOSING_CASTLE_RIGHTS_BLACK_MOVES_D1);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(1, 1, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(15, result, "There should be 15 legal moves (leaf nodes).");
    }
    @Test
    void perft_id_062_LostCastleRights_0_BothColorsCastledWhiteMovesDepth4() {
        gameState = new ChessGame(FEN_KING_ROOK_NO_CHECK_LOST_CASTLE_RIGHTS_BOTH_COLORS);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(4, 4, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(51_509L, result, "There should be 51509 legal moves (leaf nodes).");
    }
    @Test
    void perft_id_063_LostCastleRights_0_WhiteCastledBlackMovesDepth5() {
        gameState = new ChessGame(FEN_KING_ROOK_NO_CHECK_WHITE_LOST_CASTLE_RIGHTS);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(5, 5, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(656_771L, result, "There should be 656_771 legal moves (leaf nodes).");
        /*
        ParkyChess: {h8h1=18763, e8f7=77984, e8g8=51509, h8h2=37996, e8e7=68999, e8f8=43190, h8h3=55554, h8h4=57151, h8h5=57402, h8h6=57669, h8h7=55001, h8g8=40564, h8f8=36592}
        Stockfish: {h8h1: 18700,e8f7: 77892,e8g8: 51509,h8h2:37878,e8e7: 68908, e8f8: 43099,h8h3: 55390,h8h4: 56987,h8h5: 57238,h8h6: 57505,h8h7: 54837, h8g8: 40400, h8f8: 36428}
*/
    }
    @Test
    void perft_id_064_BothColorsLostCastleRights_0_BlackMovesDepth1() {
        gameState = new ChessGame(FEN_KING_ROOK_NO_CHECK_BOTH_COLORS_LOST_CASTLE_RIGHTS_D1);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(1, 1, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(12L, result, "There should be 12 legal moves (leaf nodes).");
        /* 13; {e8g8=1} castling should not be possible at this point for black*/
    }
    @Test
    void perft_id_065_BothColorsLostCastleRights_0_WhiteMovesDepth2() {
        gameState = new ChessGame(FEN_KING_ROOK_NO_CHECK_BOTH_COLORS_LOST_CASTLE_RIGHTS_D2);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(2, 2, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(179L, result, "There should be 179 legal moves (leaf nodes).");
    }
    @Test
    void perft_id_066_LostCastleRights_0_WhiteCastledBlackMovesDepth3() {
        gameState = new ChessGame(FEN_KING_ROOK_NO_CHECK_BOTH_COLORS_LOST_CASTLE_RIGHTS_D3);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(3, 3, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(2087L, result, "There should be 2087 legal moves (leaf nodes).");
    }
    @Test
    void perft_id_067_LostCastleRights_0_WhiteCastledWhiteMovesDepth4() {
        gameState = new ChessGame(FEN_KING_ROOK_NO_CHECK_BOTH_COLORS_LOST_CASTLE_RIGHTS);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(4, 4, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(18_700L, result, "There should be 18700 legal moves (leaf nodes).");
    }
    @Test
    void perft_id_068_LosingCastleRights_0a_Rg8_WhiteMovesDepth5() {
        gameState = new ChessGame(FEN_NO_CHECK_LOST_CASTLE_RIGHTS_0A_RG8);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(5,5,stats);
        assertEquals(461_098L, result, "There should be 461_098 legal moves (leaf nodes).");
    }
    @Test
    void perft_id_069_LosingCastleRights_0a_Rf8_WhiteMovesDepth5() {
        gameState = new ChessGame(FEN_NO_CHECK_LOST_CASTLE_RIGHTS_0A_RF8);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(5,5,stats);
        assertEquals(424_079L, result, "There should be 424079 legal moves (leaf nodes).");
    }
    @Test
    void perft_id_070_LosingCastleRights_0a_Ke7_WhiteMovesDepth5() {
        gameState = new ChessGame(FEN_NO_CHECK_LOST_CASTLE_RIGHTS_0A_KE7);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(5, 5, stats);
        assertEquals(465_039L, result, "There should be 465_039 legal moves (leaf nodes).");
    }
    @Test
    void perft_id_071_LosingCastleRights_0a_Kd7_WhiteMovesDepth5() {
        gameState = new ChessGame(FEN_NO_CHECK_LOST_CASTLE_RIGHTS_0A_KD7);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(5, 5, stats);
        assertEquals(551_144L, result, "There should be 551_144 legal moves (leaf nodes).");
    }
    @Test
    void perft_id_072_LosingCastleRights_0a_Kf7_WhiteMovesDepth5() {
        gameState = new ChessGame(FEN_NO_CHECK_LOST_CASTLE_RIGHTS_0A_KF7);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(5, 5, stats);
        assertEquals(426_733L, result, "There should be 426733 legal moves (leaf nodes).");
    }
    @Test
    void perft_id_073_LosingCastleRights_0a_Kd8_WhiteMovesDepth5() {
        gameState = new ChessGame(FEN_NO_CHECK_LOST_CASTLE_RIGHTS_0A_KD8);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(5, 5, stats);
        assertEquals(282_602L, result, "There should be 282_602 legal moves (leaf nodes).");
    }
    @Test
    void perft_id_074_LosingCastleRights_0a_Kf8_WhiteMovesDepth5() {
        gameState = new ChessGame(FEN_NO_CHECK_LOST_CASTLE_RIGHTS_0A_KF8);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(5, 5, stats);
        assertEquals(152_519L, result, "There should be 152_519 legal moves (leaf nodes).");
    }
    @Test
    void perft_id_075_LosingCastleRights_0b_BlackMovesDepth3() {
        gameState = new ChessGame (FEN_NO_CHECK_LOST_CASTLE_RIGHTS_0B);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(3, 3, stats);
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
    void perft_id_076_LosingCastleRights_1_WhiteMovesDepth4() {
        gameState = new ChessGame(FEN_TEST_LOSING_CASTLE_RIGHTS_1_WHITE_MOVES_DEPTH_4);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(4, 4, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(1_274_206L, result, "There should be 1_274_206L legal moves (leaf nodes).");
    }
    @Test
    void perft_id_077_LosingCastleRights_1_Rxa8_BlackMovesDepth3() {
        gameState = new ChessGame(FEN_TEST_LOSING_CASTLE_RIGHTS_1_BLACK_MOVES_DEPTH_3_RxA8);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(3, 3, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(4427L, result, "There should be 4427 legal moves (leaf nodes).");
    }
    @Test
    void perft_id_078_LosingCastleRights_1_Rxa8_bxa8_WhiteMovesDepth2() {
        gameState = new ChessGame(FEN_TEST_LOSING_CASTLE_RIGHTS_1_WHITE_MOVES_DEPTH_2_BxA8);
        log.debug("Chessboard: {}", gameState.getChessboard());
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(2, 2, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(518L, result, "There should be 518 legal moves (leaf nodes).");
    /* Stockfish  "b3k2r/6bq/8/8/8/8/7B/4K2R w Kk - 0 1"
    * Note: After every bishop move black does not castle, while it should be so only afterh2d6.
    * Same for every white king move and for every rook move. The only rook move preventing castle is h1f1.
    * So there must be something preveting black from castling after some white's moves
    * Stockfish vs ParkYChesS:
h2g1: 36 / OK
h2g3: 36 / 35
h2f4: 36 / 35
h2e5: 32 / 31
h2d6: 33 / OK
h2c7: 35 / 34
h2b8: 36 / 35
h1f1: 32 / OK
h1g1: 35 / 34
e1d1: 35 / 34
e1f1: 35 / 34
e1d2: 35 / 34
e1e2: 35 / 34
e1f2: 35 / 34
e1g1: 32 / OK
    * */
    }
    @Test
    void perft_id_079_LosingCastleRights_1_Rxa8_Ke7_WhiteMovesDepth2() {
        gameState = new ChessGame(FEN_TEST_LOSING_CASTLE_RIGHTS_1_WHITE_MOVES_DEPTH_2_KE7);
        ptu = new PerftUtils(gameState);
        log.debug("Initial chessboard position: {}", gameState.getChessboard());
        long result = ptu.perft(2,2, stats);
        assertEquals(1077L, result, "There should be 1077L legal moves (leaf nodes).");
        King blackKing = gameState.getChessboard().getKing(PlayerColor.BLACK);
        assertTrue(blackKing.isFlaggedAsHavingAlreadyMoved(), "Black king should be flagged as already moved.");
    }
    @Test
    void perft_id_080_LosingCastleRights_1_Rxa8_Kd7_WhiteMovesDepth2() {
        gameState = new ChessGame(FEN_TEST_LOSING_CASTLE_RIGHTS_1_WHITE_MOVES_DEPTH_2_KD7);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(2,2, stats);
        King blackKing = gameState.getChessboard().getKing(PlayerColor.BLACK);
        assertEquals(1091L, result, "There should be 1091L legal moves (leaf nodes).");
        assertTrue(blackKing.isFlaggedAsHavingAlreadyMoved(), "Black king should be flagged as already moved.");
    }
    @Test
    void perft_id_081_LosingCastleRights_1_Rxa8_Kf7_WhiteMovesDepth2() {
        gameState = new ChessGame(FEN_TEST_LOSING_CASTLE_RIGHTS_1_WHITE_MOVES_DEPTH_2_KF7);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(2,2, stats);
        King blackKing = gameState.getChessboard().getKing(PlayerColor.BLACK);
        assertEquals(1048L, result, "There should be 1048L legal moves (leaf nodes).");
        assertTrue(blackKing.isFlaggedAsHavingAlreadyMoved(), "Black king should be flagged as already moved.");
    }
    @Test
    void perft_id_082_LosingCastleRights_1_Rxa8_bc8_WhiteMovesDepth2() {
        gameState = new ChessGame(FEN_TEST_LOSING_CASTLE_RIGHTS_1_WHITE_MOVES_DEPTH_2_BC8);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(2,2, stats);
        assertEquals(693L, result, "There should be 693L legal moves (leaf nodes).");
    }
    @Test
    void perft_id_083_LosingCastleRights_1_Rxa8_bxa8_Kf1_BlackMovesDepth1() {
        gameState = new ChessGame(FEN_TEST_LOSING_CASTLE_RIGHTS_1_BLACK_MOVES_DEPTH_1_KF1);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(1, 1, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(35L, result, "There should be 35 legal moves (leaf nodes).");
        /* Stockfish () vs ParkYChesS () "b3k2r/6bq/8/8/8/8/7B/5K1R b k - 0 1"*/
    }
    @Test
    void perft_id_084_LosingCastleRights_1_Rxa8_bxa8_Rf1_BlackMovesDepth1() {
        gameState = new ChessGame(FEN_TEST_LOSING_CASTLE_RIGHTS_1_BLACK_MOVES_DEPTH_1_RF1);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(1, 1, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(32L, result, "There should be 32 legal moves (leaf nodes).");
        /* Stockfish () vs ParkYChesS () "b3k2r/6bq/8/8/8/8/7B/5K1R b k - 0 1"*/
    }
    @Test
    void perf_id_085_tLosingCastleRights_1_Rxa8_bxa8_Bb8_BlackMovesDepth1() {
        gameState = new ChessGame(FEN_TEST_LOSING_CASTLE_RIGHTS_1_BLACK_MOVES_DEPTH_1_BB8);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(1, 1, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(36L, result, "There should be 36 legal moves (leaf nodes).");
    }
    @Test
    void perft_id_086_LosingCastleRights_1_Rxa8_bxa8_Be5_BlackMovesDepth1() {
        gameState = new ChessGame(FEN_TEST_LOSING_CASTLE_RIGHTS_1_BLACK_MOVES_DEPTH_1_BE5);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(1, 1, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(32L, result, "There should be 32 legal moves (leaf nodes).");
    /* Stockfish vs ParkYChesS */
    }
    @Test
    void perft_id_087_LosingCastleRights_1a_BlackMovesDepth4() {
        gameState = new ChessGame(FEN_TEST_LOSING_CASTLE_RIGHTS_1A_BLACK_MOVES_DEPTH_4);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(4,4, stats);
        assertEquals(1_274_206L, result, "There should be 1_274_206L legal moves (leaf nodes).");
    }
    @Test
    void perft_id_088_LosingCastleRights_2_WhiteMovesDepth4() {
        gameState = new ChessGame(FEN_TEST_LOSING_CASTLE_RIGHTS_2_WHITE_MOVES);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(4,4, stats);
        assertEquals(1_720_476L, result, "There should be 1_720_476L legal moves (leaf nodes).");
    }
    @Test
    void perft_id_089_LosingCastleRights_2_BlackMovesDepth4() {
        gameState = new ChessGame(FEN_TEST_LOSING_CASTLE_RIGHTS_2_BLACK_MOVES);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(4,4, stats);
        assertEquals(1_720_476L, result, "There should be 1_720_476L legal moves (leaf nodes).");
    }
    @Test
    void perft_id_090_BlackRookHasMovedForGoodDepth3(){
        gameState = new ChessGame(FEN_BLACK_ROOK_H8_HAS_NOT_MOVED_FOR_GOOD);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(3,3, stats);
        King whiteKing = gameState.getChessboard().getKing(PlayerColor.WHITE);
        assertFalse(whiteKing.isFlaggedAsHavingAlreadyMoved(),"The white king should not be flagged as having already moved.");
        King blackKing = gameState.getChessboard().getKing(PlayerColor.BLACK);
        assertFalse(blackKing.isFlaggedAsHavingAlreadyMoved(),"The black king should not be flagged as having already moved.");
        Rook blackRook = (Rook)gameState.getChessboard().getPiece(0, 7);
        assertFalse(whiteKing.isFlaggedAsHavingAlreadyMoved(),"The white king should not be flagged as having already moved.");
        assertFalse(blackRook.isFlaggedAsHavingAlreadyMoved(),"The black rook should not be flagged as having already moved.");
        assertEquals(1197L, result, "There should be 1197 legal moves (leaf nodes).");
    }
    @Test
    void perft_id_091_whiteKingShoulBeMarkedAsAlreadyMovedInTheSimplestPlayablePositionDepth3() {
        gameState = new ChessGame(FEN_SIMPLEST_PLAYABLE);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(3,3, stats);
        assertEquals(69, result, "There should be 69 legal moves (leaf nodes).");
        King king = gameState.getChessboard().getKing(PlayerColor.WHITE);
        assertTrue(king.isFlaggedAsHavingAlreadyMoved(),"King should be flagged as having already moved.");
    }
    @Test
    void perft_id_092_whiteKingShouldNotBeMarkedAsAlreadyMovedInTheSimplestPlayablePosition_h1g1_Depth2() {
        gameState = new ChessGame(FEN_SIMPLEST_PLAYABLE_2);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(2, 2, stats);
        assertEquals(18, result, "There should be 18 legal moves (leaf nodes).");
        King whiteKing = gameState.getChessboard().getKing(PlayerColor.WHITE);
        assertFalse(whiteKing.isFlaggedAsHavingAlreadyMoved(), "White king should not be flagged as having already moved.");
    }
    @Test
    void perft_id_093_whiteKingShoulBeMarkedAsAlreadyMovedInTheSimplestPlayablePosition_h1g1_h8h7_Depth1() {
        gameState = new ChessGame(FEN_SIMPLEST_PLAYABLE_3);
        log.debug("Position at start: {}", gameState.getChessboard());
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(1,1, stats);
        assertEquals(6, result, "There should be 6 legal moves (leaf nodes).");
        King whiteKing = gameState.getChessboard().getKing(PlayerColor.WHITE);
        assertTrue(whiteKing.isFlaggedAsHavingAlreadyMoved(),"White king should be flagged as having already moved.");
    }
    @Test
    void perft_id_094_whiteKingShoulBeMarkedAsAlreadyMovedInTheSimplestPlayablePositionDepth7() {
        gameState = new ChessGame(FEN_SIMPLEST_PLAYABLE);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(7,7, stats);
        assertEquals(116242, result, "There should be 116242 legal moves (leaf nodes).");
        King king = gameState.getChessboard().getKing(PlayerColor.WHITE);
        assertTrue(king.isFlaggedAsHavingAlreadyMoved(),"King should be flagged as having already moved.");
    }
    //@Test
    void perft_id_095_whiteKingShoulBeMarkedAsAlreadyMovedInTheSimplestPlayablePositionDepth9() {
        gameState = new ChessGame(FEN_SIMPLEST_PLAYABLE);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(9,9, stats);
        assertEquals(5_424_200L, result, "There should be 5_424_200L legal moves (leaf nodes).");
        King king = gameState.getChessboard().getKing(PlayerColor.WHITE);
        assertFalse(king.isFlaggedAsHavingAlreadyMoved(),"King should be flagged as having already moved.");
    }
 //   @Test
    void perft_id_096_whiteKingShoulBeMarkedAsAlreadyMovedInTheSimplestPlayablePositionDepth11() {
        gameState = new ChessGame(FEN_SIMPLEST_PLAYABLE);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(11,11, stats);
        assertEquals(258_215_746L, result, "There should be 258_215_746L legal moves (leaf nodes).");
        King king = gameState.getChessboard().getKing(PlayerColor.WHITE);
        assertTrue(king.isFlaggedAsHavingAlreadyMoved(),"King should be flagged as having already moved.");
    }
    @Test
    void perft_id_097_whiteKingShoulBeMarkedAsAlreadyMovedInTheSimplestCastlingPositionDepth3() {
        gameState = new ChessGame(FEN_TEST_LOSING_CASTLE_RIGHTS_2_SIMPLEST);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(3,3, stats);
        assertEquals(1197, result, "There should be 1197 legal moves (leaf nodes).");
        King king = gameState.getChessboard().getKing(PlayerColor.WHITE);
        assertFalse(king.isFlaggedAsHavingAlreadyMoved(),"King should be flagged as having already moved.");
    }

    @Test
    void perft_id_098_EnPassant_Minimal_Depth2() {
        gameState = new ChessGame(FEN_TWO_PAWNS_EN_PASSANT_POSITION_BASIC_DEPTH2);
        log.debug("Initial position:{}", gameState.getChessboard());
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(2, 2, stats);
        stats.setNumberOfLeaves(result);
        log.debug(stats.getMovesMap().toString());
        assertEquals(22, result, "From the initial position there are 22 possible positions at depth 2.");
    }


}