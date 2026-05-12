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
        gameState = new ChessGame(FEN_SIMPLEST_PLAYABLE);
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
    void perft_id_017_NumberOfPositionsAfterNa3Depth4() {
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
    /*
     --Stockfish-vs. ParkyChesS----------------------------------------------------------------
    a7a6: 198827 OK
    b7b6: 238563 OK
    c7c6: 244282 OK
    d7d6: 363006 OK
    e7e6: 445322 OK
    f7f6: 196936 OK
    g7g6: 239145 OK
    h7h6: 199242 OK
    a7a5: 204508 OK
    b7b5: 273984 OK
    c7c5: 263428 nok: 263431
    d7d5: 399718 nok 399721
    e7e5: 447849 nok 447852
    f7f5: 218422 nok 218425
    g7g5: 235656 nok 235674
    h7h5: 240940 OK
    b8a6: 220131 OK
    b8c6: 257893 OK
    g8f6: 257155 OK
    g8h6: 218548 OK

    Nodes searched: 5363555
     */
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
        assertEquals(10_292L, result, "From the initial position there should be 10_292 possible positions at depth 3");
    }/* Stockfish vs. ParkyChesS:
c6c5: 463 vs 463 OK
a7a6: 399 vs 399 OK
b7b6: 459 vs 459 OK
d7d6: 593 vs 593 OK
e7e6: 659 vs 659 OK
f7f6: 418 vs 418 OK
g7g6: 462 vs 462 OK
h7h6: 418 vs 418 OK
b7b5: 483 vs 462 ??????????
d7d5: 616 vs 616 OK
e7e5: 660 vs 660 OK
f7f5: 441 vs 441 OK
g7g5: 463 vs 463 OK
h7h5: 462 vs 462 OK
b8a6: 483 vs 483 OK
g8f6: 484 vs 484 OK
g8h6: 440 vs 440 OK
d8a5: 690 vs 690 OK
d8b6: 630 vs 630 OK
d8c7: 569 vs 569 OK

Nodes searched: 10292 vs 10271
*/

    @Test
    void perft_id_025_bis_NumberOfPositionsAfter_a4_c6_a5_b5Depth2() {
        gameState = new ChessGame(FEN_POSITION_AFTER_A4_C6_A5_B5);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(2, 2, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(483, result, "From the initial position there should be 483 possible positions at depth 3");
    } /* Stockfish vs. ParkyChess:
{a5a6=20, h2h3=21, h2h4=21, b2b3=21, b1a3=21, c2c4=22, a1a2=21, a1a3=21, b2b4=20, a1a4=22, d2d4=21, c2c3=21,
e2e4=21, b1c3=21, d2d3=21, e2e3=21, f2f4=21, f2f3=21, g2g4=21, g2g3=21, g1f3=21, g1h3=21}

    b2b3: 21 OK
    c2c3: 21 OK
    d2d3: 21 OK
    e2e3: 21 OK
    f2f3: 21 OK
    g2g3: 21 OK
    h2h3: 21 OK
    a5a6: 20 OK
    b2b4: 20 OK
    c2c4: 22 OK
    d2d4: 21 OK
    e2e4: 21 OK
    f2f4: 21 OK
    g2g4: 21 OK
    h2h4: 21 OK
    a5b6: 21 MISSING
    b1a3: 21 OK
    b1c3: 21 OK
    g1f3: 21 OK
    g1h3: 21 OK
    a1a2: 21 OK
    a1a3: 21 OK
    a1a4: 22 OK

Nodes searched: 483 vs 462
    */

    @Test
    void perft_id_026_NumberOfPositionsAfter_a4_e5Depth4() {
        gameState = new ChessGame(FEN_POSITION_AFTER_A4_E5);
        ptu = new PerftUtils(gameState);
        log.debug("Current situation: {}", gameState.getChessboard());
        long result = ptu.perft(4, 4, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(447_849L, result, "From the initial position there are 447_849 possible positions at depth 4");
    /* Stockfish vs ParkyChesS:
    +++++++++++++++++++++++++
    {h2h3=18365, h2h4=20289, a4a5=18978,
    b2b3=20191, b1a3=20160, c2c4=20169,
    a1a2=19314, a1a3=24660, b2b4=19090,
    d2d4=28213, c2c3=19389, e2e4=25725,
    b1c3=21106, d2d3=25019, e2e3=28231,
    f2f4=20450, f2f3=17704, g2g4=20035,
    g2g3=20316, g1f3=21154, g1h3=19294}
    ++++++++++++++++++++++++++
b2b3: 20191
c2c3: 19389
d2d3: 25019
e2e3: 28231
f2f3: 17704
g2g3: 20316
h2h3: 18365
a4a5: 18978
b2b4: 19090
c2c4: 20169
d2d4: 28212 nok 28213
e2e4: 25725
f2f4: 20448 nok 20450
g2g4: 20035
h2h4: 20289
b1a3: 20160
b1c3: 21106
g1f3: 21154
g1h3: 19294
a1a2: 19314
a1a3: 24660

Nodes searched: 447849
    ++++++++++++++++++++++++++
    b2b3: 20191 OK
    c2c3: 19389 OK
    d2d3: 25019 OK
    e2e3: 28231 OK
    f2f3: 17704 OK
    g2g3: 20316 OK
    h2h3: 18365 OK
    a4a5: 18978 OK
    b2b4: 19090 OK
    c2c4: 20169 OK
    d2d4: 28212 NOK d2d4=28213,
    e2e4: 25725 OK
    f2f4: 20448 NOK f2f4=20450
    g2g4: 20035 OK
    h2h4: 20289 OK
    b1a3: 20160 OK
    b1c3: 21106 OK
    g1f3: 21154 OK
    g1h3: 19294 OK
    a1a2: 19314 OK
    a1a3: 24660 OK
    Nodes searched: 447849
    */
    }

    @Test
    void perft_id_026Bis_NumberOfPositionsAfter_a4_e5_withKingsDepth4() {
        gameState = new ChessGame(FEN_POSITION_AFTER_A4_E5_WITH_K);
        ptu = new PerftUtils(gameState);
        log.debug("Current chessboard: {}", gameState.getChessboard());
        long result = ptu.perft(4, 4, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(3386, result, "The expected number of leaf nodes does not match the computed one.");
    }
    /*
    a4a5: 407
    d4d5: 283
    d4e5: 210
    e1d1: 407
    e1f1: 407
    e1d2: 555
    e1e2: 555
    e1f2: 562
Nodes searched: 3386
     */
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
    void perft_id_027Quater_NumberOfPositionsAfter_a4_e5_d4Depth3() {
        gameState = new ChessGame(FEN_POSITION_AFTER_A4_E5_D4);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(3, 3, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(28_212L, result, "From the initial position there are 28_212 possible positions at depth 3");
    /* Stockfish vs ParkyChesS (Nodes searched: 28212):
    e5e4: 835 OK
    a7a6: 891 OK
    b7b6: 952 OK
    c7c6: 952 OK
    d7d6: 1012 OK
    f7f6: 805 OK
    g7g6: 950 OK
    h7h6: 895 OK
    a7a5: 892 OK
    b7b5: 1011 OK
    c7c5: 949 OK
    d7d5: 1097 OK
    f7f5: 921 OK
    g7g5: 866 OK
    h7h5: 953 OK
    e5d4: 868 OK
    b8a6: 922 OK
    b8c6: 982 OK
    g8f6: 863 OK
    g8h6: 890 OK
    g8e7: 714 OK
    f8a3: 995 OK
    f8b4: 173 OK
    f8c5: 1010 OK
    f8d6: 893 OK
    f8e7: 862 OK
    d8h4: 1118 OK
    d8g5: 1139 OK
    d8f6: 1187 OK
    d8e7: 893 OK
    e8e7: 722 OK
     */
    }

    @Test
    void perft_id_027bis_NumberOfPositionsAfter_a4_e5_f4Depth3() {
        gameState = new ChessGame(FEN_POSITION_AFTER_A4_E5_F4);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(3, 3, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(20448, result, "From the initial position there are 20448 possible positions at depth 3");
    /* Stockfish vs ParkyChesS (Nodes searched: 20448 vs. 20448 ok):
e5e4: 599
a7a6: 654
b7b6: 699
c7c6: 698
d7d6: 741
f7f6: 589
g7g6: 699
h7h6: 654
a7a5: 647
b7b5: 750
c7c5: 656
d7d5: 850
f7f5: 629
g7g5: 703
h7h5: 698
e5f4: 601
b8a6: 677
b8c6: 721
g8f6: 633
g8h6: 654
g8e7: 523
f8a3: 734
f8b4: 657
f8c5: 745
f8d6: 655
f8e7: 632
d8h4: 39
d8g5: 880
d8f6: 834
d8e7: 655
e8e7: 542
Nodes searched: 20448
     */
    }
    @Test
    void perft_id_027Ter_NumberOfPositionsAfter_a4_e5_d4_ke7Depth2() {
        gameState = new ChessGame(FEN_POSITION_AFTER_A4_E5_D4_KE7);
        log.info("Initial position: {}", gameState.getChessboard().toString());
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(2, 2, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(722, result, "From the initial position there are 722 possible positions at depth 3");
    /* Stockfish vs ParkyChesS (Nodes searched: 722):
    b2b3: 25 OK
    c2c3: 25 OK
    e2e3: 25 OK
    f2f3: 25 OK
    g2g3: 25 OK
    h2h3: 25 OK
    a4a5: 24 OK
    d4d5: 22 OK
    b2b4: 25 OK
    c2c4: 25 OK
    e2e4: 24 OK
    f2f4: 26 OK
    g2g4: 25 OK
    h2h4: 25 OK
    d4e5: 21 OK
    b1d2: 25 OK
    b1a3: 25 OK
    b1c3: 25 OK
    g1f3: 25 OK
    g1h3: 25 OK
    c1d2: 25 OK
    c1e3: 25 OK
    c1f4: 26 OK
    c1g5: 5 OK
    c1h6: 24 OK
    a1a2: 25 OK
    a1a3: 25 OK
    d1d2: 25 OK
    d1d3: 25 OK
    e1d2: 25 OK
     */
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
        assertEquals(5, result, "From the initial position there are 5 possible positions at depth 1");
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
        /*Stockfish vs. ParkyChesS:
            a2a3: 1 a2a3=2
            b2b3: 1 b2b3=2
            g2g3: 1 g2g3=2
            d5d6: 1 d5d6=2
            a2a4: 1 a2a4=2
            g2g4: 1 g2g4=2
            g2h3: 1 g2h3=2
            d5e6: 1 d5e6=2
            c3b1: 1 c3b1=2
            c3d1: 1 c3d1=2
            c3a4: 1 c3a4=2
            c3b5: 1 c3b5=2
            e5d3: 1 e5d3=2
            e5c4: 1 e5c4=2
            e5g4: 1 e5g4=2
            e5c6: 1 e5c6=2
            e5g6: 1 e5g6=2
            e5d7: 1 e5d7=2
            e5f7: 1 e5f7=2
            d2c1: 1 d2c1=2
            d2e3: 1 d2e3=2
            d2f4: 1 d2f4=2
            d2g5: 1 d2g5=2
            d2h6: 1 d2h6=2
            e2d1: 1 e2d1=2
            e2f1: 1 e2f1=2
            e2d3: 1 e2d3=2
            e2c4: 1 e2c4=2
            e2b5: 1 e2b5=2
            e2a6: 1 e2a6=2
            a1b1: 1 a1b1=2
            a1c1: 1 a1c1=2
            a1d1: 1 a1d1=2
            h1f1: 1 h1f1=2
            h1g1: 1 h1g1=2
            f3d3: 1 f3d3=2
            f3e3: 1 f3e3=2
            f3g3: 1 f3g3=2
            f3h3: 1 f3h3=2
            f3f4: 1 f3f4=2
            f3g4: 1 f3g4=2
            f3f5: 1 f3f5=2
            f3h5: 1 f3h5=2
            f3f6: 1 f3f6=2
            e1d1: 1 e1d1=2
            e1f1: 1 e1f1=2
            e1g1: 1 e1g1=2
            e1c1: 1 not computed by ParkyChesS
            Nodes searched: 48
           */
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
        a2a3: 44 vs a2a3=44 ------ OK
        b2b3: 42 vs b2b3=42 ------ OK
        g2g3: 42 vs g2g3=41 ------
        d5d6: 41 vs d5d6=41 ------ OK
        a2a4: 44 vs a2a4=43 ------ OK
        g2g4: 42 vs g2g4=41 ------
        g2h3: 43 vs g2h3=42 ------
        d5e6: 46 vs d5e6=46 ------ OK
        c3b1: 42 vs c3b1=42 ------ OK
        c3d1: 42 vs c3d1=42 ------ OK
        c3a4: 42 vs c3a4=42 ------ OK
        c3b5: 39 vs c3b5=39 ------ OK
        e5d3: 43 vs e5d3=43 ------ OK
        e5c4: 42 vs e5c4=42 ------ OK
        e5g4: 44 vs e5g4=44 ------ OK
        e5c6: 41 vs e5c6=41 ------ OK
        e5g6: 42 vs e5g6=42 ------ OK
        e5d7: 45 vs e5d7=45 ------ OK
        e5f7: 44 vs e5f7=44 ------ OK
        d2c1: 43 vs d2c1=42 ------
        d2e3: 43 vs d2e3=42 ------
        d2f4: 43 vs d2f4=42 ------
        d2g5: 42 vs d2g5=41 ------
        d2h6: 41 vs d2h6=40 ------
        e2d1: 44 vs e2d1=43 ------
        e2f1: 44 vs e2f1=43 ------
        e2d3: 42 vs e2d3=41 ------
        e2c4: 41 vs e2c4=40 ------
        e2b5: 39 vs e2b5=38 ------
        e2a6: 36 vs e2a6=35 ------
        a1b1: 43 vs a1b1=42 ------
        a1c1: 43 vs a1c1=42 ------
        a1d1: 43 vs a1d1=42 ------
        h1f1: 43 vs h1f1=42 ------
        h1g1: 43 vs h1g1=42 ------
        f3d3: 42 vs f3d3=42 ------ OK
        f3e3: 43 vs f3e3=43 ------ OK
        f3g3: 43 vs f3g3=43 ------ OK
        f3h3: 43 vs f3h3=43 ------ OK
        f3f4: 43 vs f3f4=43 ------ OK
        f3g4: 43 vs f3g4=43 ------ OK
        f3f5: 45 vs f3f5=45 ------ OK
        f3h5: 43 vs f3h5=43 ------ OK
        f3f6: 39 vs f3f6=39 ------ OK
        e1d1: 43 vs e1d1=42 ------
        e1f1: 43 vs e1f1=42 ------
        e1g1: 43 vs e1g1=42 ------
        e1c1: 43 vs e1c1=42 ------
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
    void perft_id_040Bis_EnPassantCaptureThenPromotionDepth6() {
        gameState = new ChessGame(FEN_TEST_EP_CAPTURE_PROMOTION_BLACK_MOVES);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(6, 6, stats);
        stats.setNumberOfLeaves(result);
        log.error("Map of moves at depth 6: {}", stats.getMovesMap().toString());
        assertEquals(31_704L, result, "The expected moves do not coincide with the computed ones.");
    }

    @Test
    void perft_id_040Ter_EnPassantCaptureThenPromotionCaptureDepth5() {
        gameState = new ChessGame(FEN_TEST_EP_CAPTURE_PROMOTION_CAPTURE_BLACK_MOVES);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(5, 5, stats);
        stats.setNumberOfLeaves(result);
        log.error("Moves map depth 5: {}", stats.getMovesMap().toString());
        assertEquals(44964L, result, "The expected moves do not coincide with the computed ones.");
    }

    @Test
    void perft_id_040Ter_EnPassantCaptureThenPromotionCaptureDepth6() {
        gameState = new ChessGame(FEN_TEST_EP_CAPTURE_PROMOTION_CAPTURE_BLACK_MOVES);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(6, 6, stats);
        stats.setNumberOfLeaves(result);
        log.error("Moves map depth 6: {}", stats.getMovesMap().toString());
        assertEquals(759_841L, result, "The expected moves do not coincide with the computed ones.");
    }

    @Test
    void perft_id_040_AvoidIllegalEnPassantCaptureBecausePawnPinnedBlackMovesDepth6() {
        gameState = new ChessGame(FEN_TEST_AVOID_ILLEGAL_EP_CAPTURE_BLACK_MOVES);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(5, 5, stats);
        stats.setNumberOfLeaves(result);
        log.info("Moves map at depth 6: {}", stats.getMovesMap().toString());
        assertEquals(148_335L, result, "The expected moves do not coincide with the computed ones.");
    }

    @Test
    void perft_id_041_EnPassantCaptureCheckOpponentWhiteMovesDepth6() {
        gameState = new ChessGame(FEN_TEST_EP_CAPTURE_CHECK_OPPONENT_WHITE_MOVES);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(6, 6, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(1_440_467L, result,
                "The number of expected legal moves is not equal to the number of the computed ones.");
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
    @Test //FIXME now
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
        a1c1: 15 a1c1=15 OK
        a1d1: 13 a1d1=13 OK
        a1a2: 15 a1a2=15 OK
        a1a3: 15 a1a3=15 OK
        a1a4: 15 a1a4=15 OK
        a1a5: 15 a1a5=15 OK
        a1a6: 15 a1a6=15 OK
        a1a7: 12 a1a7=12 OK
        a1a8: 3 a1a8=3 OK
        e1d1: 15 e1d1=15 OK
        e1f1: 15 e1f1=15 OK
        e1d2: 15 e1d2=15 OK
        e1e2: 15 e1e2=15 OK
        e1f2: 15 e1f2=15 OK
        e1c1: 13 e1c1=0
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
        assertEquals(961_399, result, "There should be 961_399 legal moves (leaf nodes).");
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
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(236169L, result, "There should be 236169 legal moves (leaf nodes).");
    }/*
    h7h6: 14355 OK
    h7h5: 16530 OK
    h8f8: 27624 vs. 27685
    h8g8: 29935 vs. 29996
    e8d7: 34593 vs. 34654
    e8e7: 29621 vs. 29682
    e8f7: 27467 vs. 27528
    e8d8: 17201 vs. 17262
    e8f8: 9495 vs. 9556
    e8g8: 29348 OK
    Nodes searched: 236169
    */

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
        gameState = new ChessGame(FEN_NO_CHECK_LOST_CASTLE_RIGHTS_0B);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(3, 3, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
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
        void perft_id_075_LosingCastleRights_0b_BlackMovesDepth5() {
            gameState = new ChessGame(FEN_NO_CHECK_LOST_CASTLE_RIGHTS_0B);
            ptu = new PerftUtils(gameState);
            long result = ptu.perft(5, 5, stats);
            stats.setNumberOfLeaves(result);
            log.info(stats.getMovesMap().toString());
            assertEquals(92_688L, result, "The expected number of legal moves " +
                    "should be the same as the computed number.");
    /* culprit: after 1)...;e8f8 2)d2d3; f8e8 3) d3d4;... e8g8 should not be a valid move,
    because the black king has moved and then moved back to e8.
    h7h6: 5878 vs. 5878 OK
    h7h5: 6802 vs. 6802 OK
    h8f8: 10777 vs. 10813
    h8g8: 11785 vs. 11821
    e8d7: 12255 vs. 12291
    e8e7: 12013 vs. 12049
    e8f7: 11579 vs. 11615
    e8d8: 5880 vs. 5916
    e8f8: 3947 vs. 3983
    e8g8: 11772 vs. 11772 OK

Nodes searched: 92688
     */
        }

    @Test
    void perft_id_075Bis_LosingCastleRights_0b_WhiteMovesDepth4() {
        gameState = new ChessGame(FEN_NO_CHECK_LOST_CASTLE_RIGHTS_0C);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(4, 4, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(3947, result, "The expected number of legal moves should be the same" +
                " as the computed number.");
    /*
    c2c3: 564
    c2c4: 563
    c1b1: 564
    c1d1: 564
    c1b2: 846
    c1d2: 846
Nodes searched: 3947
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
        assertTrue(blackKing.isFlaggedAsHavingAlreadyMoved(), "Black king should not be flagged as already moved.");
    }
    @Test
    void perft_id_080_LosingCastleRights_1_Rxa8_Kd7_WhiteMovesDepth2() {
        gameState = new ChessGame(FEN_TEST_LOSING_CASTLE_RIGHTS_1_WHITE_MOVES_DEPTH_2_KD7);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(2,2, stats);
        King blackKing = gameState.getChessboard().getKing(PlayerColor.BLACK);
        assertEquals(1091L, result, "There should be 1091L legal moves (leaf nodes).");
        assertTrue(blackKing.isFlaggedAsHavingAlreadyMoved(), "Black king should not be flagged as already moved.");
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
        assertEquals(32, result, "The number of computed positions does not match the expected one.");
    /* Stockfish vs ParkYChesS */
    }
    @Test
    void perft_id_087_LosingCastleRights_1a_BlackMovesDepth4() {
        gameState = new ChessGame(FEN_TEST_LOSING_CASTLE_RIGHTS_1A_BLACK_MOVES_DEPTH_4);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(4,4, stats);
        assertEquals(1_274_206L, result, "There should be 1_274_206L legal moves (leaf nodes).");
        log.info("Moves map: {}", stats.getMovesMap());
    }
    @Test
    void perft_id_088_LosingCastleRights_2_WhiteMovesDepth4() {
        gameState = new ChessGame(FEN_TEST_LOSING_CASTLE_RIGHTS_2_WHITE_MOVES);
        log.debug("Chessboard situation: {}", gameState.getChessboard());
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(4,4, stats);
        assertEquals(1_720_476L, result, "There should be 1_720_476L legal moves (leaf nodes).");
        log.debug("Moves so far: {}", stats.getMovesMap());
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
    /// ///
    @Test
    void perft_id_091_whiteKingShouldBeMarkedAsAlreadyMovedInTheSimplestPlayablePositionDepth3() {
        gameState = new ChessGame(FEN_SIMPLEST_PLAYABLE);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(3,3, stats);
        assertEquals(69, result, "There should be 69 legal moves (leaf nodes).");
        King king = gameState.getChessboard().getKing(PlayerColor.WHITE);
        assertTrue(king.isFlaggedAsHavingAlreadyMoved(),"King should be flagged as having already moved.");
    }
    @Test
    void perft_id_092_whiteKingShouldBeMarkedAsAlreadyMovedInTheSimplestPlayablePositionDepth2() {
        gameState = new ChessGame(FEN_SIMPLEST_PLAYABLE_2);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(2, 2, stats);
        assertEquals(18, result, "There should be 18 legal moves (leaf nodes).");
        King whiteKing = gameState.getChessboard().getKing(PlayerColor.WHITE);
        King blackKing = gameState.getChessboard().getKing(PlayerColor.BLACK);
        assertTrue(whiteKing.isFlaggedAsHavingAlreadyMoved(), "White king should be flagged as having already moved.");
        assertTrue(blackKing.isFlaggedAsHavingAlreadyMoved(), "Black king should be flagged as having already moved.");
    }

    @Test
    void perft_id_093_whiteKingShouldBeMarkedAsAlreadyMovedInTheSimplestPlayablePosition_Depth1() {
        gameState = new ChessGame(FEN_SIMPLEST_PLAYABLE_3);
        gameState.getChessboard().setCurrentPlayerColor(PlayerColor.WHITE);
        King whiteKing = gameState.getChessboard().getKing(PlayerColor.WHITE);
        King blackKing = gameState.getChessboard().getKing(PlayerColor.BLACK);
        assertTrue(whiteKing.isFlaggedAsHavingAlreadyMoved(),"White king should be flagged as having already moved.");
        assertTrue(blackKing.isFlaggedAsHavingAlreadyMoved(),"Black king should be flagged as having already moved.");
        log.debug("Position at game start: {}", gameState.getChessboard());
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(1,1, stats);
        assertEquals(6, result, "There should be 6 legal moves (leaf nodes).");
        assertTrue(whiteKing.isFlaggedAsHavingAlreadyMoved(),"White king should be flagged as having already moved.");
        assertTrue(blackKing.isFlaggedAsHavingAlreadyMoved(),"Black king should be flagged as having already moved.");
    }
    @Test
    void perft_id_094_whiteKingShoulBeMarkedAsAlreadyMovedInTheSimplestPlayablePositionDepth7() {
        gameState = new ChessGame(FEN_SIMPLEST_PLAYABLE);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(7,7, stats);
        assertEquals(116242, result, "There should be 116242 legal moves (leaf nodes).");
        King king = gameState.getChessboard().getKing(PlayerColor.WHITE);
        assertTrue(king.isFlaggedAsHavingAlreadyMoved(),"King should not be flagged as having already moved.");
    }
    @Test
    void perft_id_095_whiteKingShoulBeMarkedAsAlreadyMovedInTheSimplestPlayablePositionDepth9() {
        gameState = new ChessGame(FEN_SIMPLEST_PLAYABLE);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(9,9, stats);
        assertEquals(5_424_200L, result, "There should be 5_424_200L legal moves (leaf nodes).");
        King king = gameState.getChessboard().getKing(PlayerColor.WHITE);
        assertTrue(king.isFlaggedAsHavingAlreadyMoved(),"King should be flagged as having already moved.");
    }
 /*   @Test
    void perft_id_096_whiteKingShoulBeMarkedAsAlreadyMovedInTheSimplestPlayablePositionDepth11() {
        gameState = new ChessGame(FEN_SIMPLEST_PLAYABLE);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(11,11, stats);
        assertEquals(258_215_746L, result, "There should be 258_215_746L legal moves (leaf nodes).");
        King king = gameState.getChessboard().getKing(PlayerColor.WHITE);
        assertTrue(king.isFlaggedAsHavingAlreadyMoved(),"King should be flagged as having already moved.");
    }*/

    @Test
    void perft_id_096bis_whiteKingShouldBeMarkedAsAlreadyMovedInTheSimplestPlayablePositionDepth3() {
        gameState = new ChessGame(FEN_SIMPLEST_PLAYABLE_KING_AT_HOME_WHITE);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(3,3, stats);
        assertEquals(280, result, "There should be 280 legal moves (leaf nodes).");
        King king = gameState.getChessboard().getKing(PlayerColor.WHITE);
        assertFalse(king.isFlaggedAsHavingAlreadyMoved(),"King should not be flagged as having already moved.");
    }
    @Test
    void perft_id_092_whiteKingShouldNotBeMarkedAsAlreadyMovedInTheSimplestPlayablePositionDepth2() {
        gameState = new ChessGame(FEN_SIMPLEST_PLAYABLE_KING_AT_HOME_WHITE);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(2, 2, stats);
        assertEquals(35, result, "There should be 18 legal moves (leaf nodes).");
        King whiteKing = gameState.getChessboard().getKing(PlayerColor.WHITE);
        King blackKing = gameState.getChessboard().getKing(PlayerColor.BLACK);
        assertFalse(whiteKing.isFlaggedAsHavingAlreadyMoved(), "White king should not be flagged as having already moved.");
        assertFalse(blackKing.isFlaggedAsHavingAlreadyMoved(), "Black king should not be flagged as having already moved.");
    }
    @Test
    void perft_id_093_whiteKingShouldNotBeMarkedAsAlreadyMovedInTheSimplestPlayablePosition_Depth1() {
        gameState = new ChessGame(FEN_SIMPLEST_PLAYABLE_KING_AT_HOME_BLACK);
        gameState.getChessboard().setCurrentPlayerColor(PlayerColor.BLACK);
        King whiteKing = gameState.getChessboard().getKing(PlayerColor.WHITE);
        King blackKing = gameState.getChessboard().getKing(PlayerColor.BLACK);
        assertFalse(whiteKing.isFlaggedAsHavingAlreadyMoved(),"White king should be flagged as having already moved.");
        assertFalse(blackKing.isFlaggedAsHavingAlreadyMoved(),"Black king should be flagged as having already moved.");
        log.debug("Position at start: {}", gameState.getChessboard());
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(1,1, stats);
        assertEquals(5, result, "There should be 5 legal moves (leaf nodes).");
        assertFalse(whiteKing.isFlaggedAsHavingAlreadyMoved(),"White king should be flagged as having already moved.");
        assertFalse(blackKing.isFlaggedAsHavingAlreadyMoved(),"Black king should be flagged as having already moved.");
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
    @Test
    void perft_id_099_LosingCastleRights_3_WhiteMovesQueensideDepth1() {
        gameState = new ChessGame(FEN_TEST_LOSING_CASTLE_RIGHTS_0_W_QUEENSIDE_WHITE_D1);
        log.info("Chessboard: {}", gameState.getChessboard());
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(1,1, stats);
        assertEquals(37, result, "There should be 37 legal moves (leaf nodes).");
        log.info("Final moves map: {}", stats.getMovesMap());
    }
    @Test
    void perft_id_100_LosingCastleRights_3_WhiteMovesQueensideDepth2() {
        gameState = new ChessGame(FEN_TEST_LOSING_CASTLE_RIGHTS_0_W_QUEENSIDE_WHITE_D2);
        log.info("Chessboard: {}", gameState.getChessboard());
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(2,2, stats);
        assertEquals(92, result, "There should be 92 legal moves (leaf nodes).");
        log.info("At the end, moves map: {}", stats.getMovesMap());
    }

    @Test
    void perft_id_101_LosingCastleRights_3_WhiteMovesQueensideDepth3() {
        gameState = new ChessGame(FEN_TEST_LOSING_CASTLE_RIGHTS_0_W_QUEENSIDE_WHITE_D3);
        log.info("Chessboard snapshot: {}", gameState.getChessboard());
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(3,3, stats);
        assertEquals(3826, result, "Number of positions does not match the expected value.");
        log.info("Detailed moves map: {}", stats.getMovesMap());
    }

    @Test
    void perft_id_102_LosingCastleRights_3_WhiteMovesQueensideDepth4() {
        gameState = new ChessGame(FEN_TEST_LOSING_CASTLE_RIGHTS_0_W_QUEENSIDE_WHITE_D4);
        log.info("Chessboard visualization: {}", gameState.getChessboard());
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(4,4, stats);
        assertEquals(15481, result, "Number of positions does not match the expected value.");
        log.info("Complete moves map: {}", stats.getMovesMap());
    }

    @Test
    void perft_id_105depth4_white_NumOfPos_PawnVsPawnAdjacentColumns(){
        gameState = new ChessGame(FEN_POSITION_PAWN_VS_PAWN_ADJACENT_CENTRAL_COLUMNS_W);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(4, 4 , stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(2084, result, "Number of positions does not match the expected value.");
    /* Stockfish vs. ParkYChesS
    Stockfish vs. ParkyChesS:
    d2d3: 285 nook 286
    d2d4: 351 nook 356
    e1d1: 277 ok, e1f1: 322 ok, e1e2: 399 ok, e1f2: 450 ok
        Nodes searched: 2084
    * */
    }
    @Test
    void perft_id_105depth3_black_NumOfPos_PawnVsPawnAdjacentCentralColumns() {
        gameState = new ChessGame(FEN_POSITION_PAWN_VS_PAWN_ADJACENT_CENTRAL_COLUMNS_B);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(3, 3, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(351, result, "Number of positions does not match the expected value.");
    }

    @Test
    void perft_id_106depth3_black_NumOfPos_PawnVsPawnAdjacentCentralColumns() {
        gameState = new ChessGame(FEN_POSITION_PAWN_VS_PAWN_ADJACENT_CENTRAL_COLUMNS_B_2);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(3, 3, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(285, result, "Number of positions does not match the expected value.");
    }
    @Test
    void perft_id_106depth3_white_NumOfPos_PawnVsPawnAdjacentCentralColumns() {
        gameState = new ChessGame(FEN_POSITION_PAWN_VS_PAWN_ADJACENT_CENTRAL_COLUMNS_B_2);
        gameState.getChessboard().setCurrentPlayerColor(PlayerColor.WHITE);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(3, 3, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(271, result, "Number of positions does not match the expected value.");
    }
    @Test
    void perft_id_106depth4_white_NumOfPos_PawnVsPawnAdjacentCentralColumns() {
        gameState = new ChessGame(FEN_POSITION_PAWN_VS_PAWN_ADJACENT_CENTRAL_COLUMNS_B_2);
        gameState.getChessboard().setCurrentPlayerColor(PlayerColor.WHITE);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(4, 4, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(2081, result, "Number of positions does not match the expected value.");
    }

    @Test
    void perft_id_107depth4_white_NumOfPos_KingPawnVsPawnCentralColumns() {
        gameState = new ChessGame(FEN_POSITION_K_PAWN_VS_K_CENTRAL_COLUMN_B);
        gameState.getChessboard().setCurrentPlayerColor(PlayerColor.WHITE);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(4, 4, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(1492, result, "Number of positions does not match the expected value.");
    }

    @Test
    void perft_id_108depth4_white_NumOfPos_KingPawnVsPawnCentralColumns() {
        gameState = new ChessGame(FEN_POSITION_PAWN_VS_PAWN_ADJACENT_CENTRAL_COLUMNS_W_2);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(4, 4, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(862, result, "Number of positions does not match the expected value.");
    } /* Stockfish vs. ParkyChesS {d2d4=141, d2d3=110, a1b1=180, a1b2=255, a1a2=180}
    d2d3: 109 NOK 110
    d2d4: 138 NOK 141
    a1b1: 180 OK
    a1a2: 180 OK
    a1b2: 255 OK
    Nodes searched: 862
*/
    @Test
    void perft_id_109_NumOfPos_KingPawnVsPawnAdjacentCentralColumns_black_depth3() {
        gameState = new ChessGame(FEN_POSITION_KING_PAWN_VS_PAWN_ADJACENT_CENTRAL_COLUMNS_B3);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(3, 3, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(109, result, "Number of positions does not match the expected value.");
    }/*
    e5e4: 22
    h8g7: 37
    h8h7: 25
    h8g8: 25
    Nodes searched: 109
    */
    @Test
    void perft_id_110_NumOfPos_KingPawnVsPawnAdjacentCentralColumns_white_depth3() {
        gameState = new ChessGame(FEN_POSITION_KING_PAWN_VS_PAWN_ADJACENT_CENTRAL_COLUMNS_W3);
        ptu = new PerftUtils(gameState);
        long result = ptu.perft(4, 4, stats);
        stats.setNumberOfLeaves(result);
        log.info(stats.getMovesMap().toString());
        assertEquals(699, result, "Number of positions does not match the expected value.");
    }

}