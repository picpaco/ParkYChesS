package com.marcopiccionitraining.parkychess;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.marcopiccionitraining.parkychess.model.FENPositions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PerftTests {
    private Board chessboard;
    private MoveGeneratorStatistics stats;
    private final Logger LOGGER = LoggerFactory.getLogger(PerftTests.class);
    private ChessGame gameState;

    @BeforeEach
    void setup() {
        chessboard = new Board();
        stats = new MoveGeneratorStatistics();
    }

    @Test
    void numberOfMovesAtSimplestPlayablePositionDepth0() {
        gameState = new ChessGame(chessboard, new FENString(FEN_SIMPLEST_PLAYABLE));
        long result = perft(0, 0, stats);
        assertEquals(1, result, "Perft (0) must evaluate to 1.");
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.toString());
    }

    @Test
    void numberOfMovesAtSimplestPlayablePositionDepth1() {
        gameState = new ChessGame(chessboard, new FENString(FEN_SIMPLEST_PLAYABLE));
        long result = perft(1, 1, stats);
        assertEquals(4, result, "There are 4 possible positions at depth 1");
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.toString());
    }

    @Test
    void numberOfMovesAtSimplestPlayablePositionDepth2() {
        gameState = new ChessGame(chessboard, new FENString(FEN_SIMPLEST_PLAYABLE));
        long result = perft(2, 2, stats);
        assertEquals(12, result, "There are 12 possible positions at depth 2");
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.toString());
    }

    @Test
    void oneBlackOneWhitePawnAtHomeDepth1() {
        gameState = new ChessGame(chessboard, new FENString(FEN_ONE_BLACK_ONE_WHITE_PAWN_AT_STARTING_SQUARE));
        long result = perft(1, 1, stats);
        assertEquals(5, result, "There are 5 possible positions at depth 1");
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.toString());
    }

    @Test
    void oneBlackOneWhitePawnAtHomeDepth2() {
        gameState = new ChessGame(chessboard, new FENString(FEN_ONE_BLACK_ONE_WHITE_PAWN_AT_STARTING_SQUARE));
        long result = perft(2, 2, stats);
        assertEquals(25, result, "There are 25 possible positions at depth 2");
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.toString());
    }

    @Test
    void oneBlackOneWhitePawnAtHomeOnAdjacentColumnsDepth1() {
        gameState = new ChessGame(chessboard, new FENString(FEN_ONE_BLACK_ONE_WHITE_PAWN_AT_STARTING_SQUARE_ON_ADJACENT_COLUMNS));
        long result = perft(1, 1, stats);
        assertEquals(5, result, "There are 5 possible positions at depth 1");
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.toString());
    }

    @Test
    void oneBlackOneWhitePawnAtHomeOnAdjacentColumnsDepth2() {
        gameState = new ChessGame(chessboard, new FENString(FEN_ONE_BLACK_ONE_WHITE_PAWN_AT_STARTING_SQUARE_ON_ADJACENT_COLUMNS));
        long result = perft(2, 2, stats);
        assertEquals(25, result, "There are 25 possible positions at depth 2");
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.toString());
    }

    @Test
    void twoBlackTwoWhitePawnsAllAtHomeDepth1() {
        gameState = new ChessGame(chessboard, new FENString(FEN_TWO_BLACK_TWO_WHITE_PAWNS_AT_STARTING_SQUARE));
        long result = perft(1, 1, stats);
        assertEquals(7, result, "There are 7 possible positions at depth 1");
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.toString());
    }

    @Test
    void twoBlackTwoWhitePawnsAllAtHomeDepth2() {
        gameState = new ChessGame(chessboard, new FENString(FEN_TWO_BLACK_TWO_WHITE_PAWNS_AT_STARTING_SQUARE));
        long result = perft(2, 2, stats);
        assertEquals(49, result, "There are 49 possible positions at depth 2");
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.toString());
    }

    @Test
    void perftNumberOfPositionsAtInitialPositionDepth1() {
        gameState = new ChessGame(chessboard, new FENString(FEN_INITIAL_POSITION));
        long result = perft(1, 1, stats);
        assertEquals(20, result, "From the initial position there are 20 possible legal moves at depth 1");
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.toString());
    }

    @Test
    void bulkPerftNumberOfPositionsAtInitialPositionDepth1() {
        gameState = new ChessGame(chessboard, new FENString(FEN_INITIAL_POSITION));
        long result = bulkPerft(1);
        assertEquals(20, result, "From the initial position there are 20 possible legal moves at depth 1");
    }

    @Test
    void perftLegalMovesNumberOfPositionsAtInitialPositionDepth1() {
        gameState = new ChessGame(chessboard, new FENString(FEN_INITIAL_POSITION));
        long result = perftLegalMoves(1);
        assertEquals(20, result, "From the initial position there are 20 possible legal moves at depth 1");
    }

    @Test
    void perftNumberOfPositionsAtInitialPositionDepth2() {
        gameState = new ChessGame(chessboard, new FENString(FEN_INITIAL_POSITION));
        long result = perft(2, 2, stats);
        assertEquals(400, result, "From the initial position there are 400 possible positions at depth 2");
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.toString());
    }

    @Test
    void bulkPerftNumberOfPositionsAtInitialPositionDepth2() {
        gameState = new ChessGame(chessboard, new FENString(FEN_INITIAL_POSITION));
        long result = bulkPerft(2);
        assertEquals(400, result, "From the initial position there are 400 possible legal moves at depth 2");
    }
    @Test
    void legalMovesPerftNumberOfPositionsAtInitialPositionDepth2() {
        gameState = new ChessGame(chessboard, new FENString(FEN_INITIAL_POSITION));
        long result = perftLegalMoves(2);
        assertEquals(400, result, "From the initial position there are 400 possible legal moves at depth 2");
    }

    @Test
    void perftNumberOfPositionsAtInitialPositionDepth3() {
        gameState = new ChessGame(chessboard, new FENString(FEN_INITIAL_POSITION));
        long result = perft(3, 3, stats);
        assertEquals(8902, result, "From the initial position there are 8902 possible positions at depth 3");
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.toString());
    }

    @Test
    void bulkPerftNumberOfPositionsAtInitialPositionDepth3() {
        gameState = new ChessGame(chessboard, new FENString(FEN_INITIAL_POSITION));
        long result = bulkPerft(3);
        assertEquals(8902, result, "From the initial position there are 8902 possible legal moves at depth 3");
    }

    @Test
    void perftNumberOfPositionsAtInitialPositionDepth4() {
        gameState = new ChessGame(chessboard, new FENString(FEN_INITIAL_POSITION));
        long result = perft(4, 4, stats);
        assertEquals(197_281L, result, "From the initial position there are 197_281 possible positions at depth 4");
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.toString());
        //takes 1s
    }
    @Test
    void bulkPerftNumberOfPositionsAtInitialPositionDepth4() {
        gameState = new ChessGame(chessboard, new FENString(FEN_INITIAL_POSITION));
        long result = bulkPerft(4);
        assertEquals(197_281L, result, "From the initial position there are 197_281 possible legal moves at depth 3");
        //takes <1s
    }
    @Test
    void perftNumberOfPositionsAtInitialPositionDepth5() {
        gameState = new ChessGame(chessboard, new FENString(FEN_INITIAL_POSITION));
        long result = perft(5, 5, stats);
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.getMovesMap().toString());
        assertEquals(4_865_609L, result, "From the initial position there are 4_865_609 possible positions at depth 5");
        //   LOGGER.info(stats.toString());
        //takes 40s
    }
    @Test
    void perftNumberOfPositionsAfterNa3(){
        gameState = new ChessGame(chessboard, new FENString(FEN_INITIAL_POSITION_NA3));
        long result = perft(4, 4, stats);
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.getMovesMap().toString());
        assertEquals(198_572, result, "From the initial position there are 198_572 possible positions at depth 4");
    }

    @Test
    void perftNumberOfPositionsAfterE3() {
        gameState = new ChessGame(chessboard, new FENString(FEN_POSITION_AFTER_E3));
        long result = perft(4, 4, stats);
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.getMovesMap().toString());
        assertEquals(402_988L, result, "From the initial position there are 402_988 possible positions at depth 4");
    }

    @Test
    void perftNumberOfPositionsAfterE3_D6() {
        gameState = new ChessGame(chessboard, new FENString(FEN_POSITION_AFTER_E3_D6));
        long result = perft(3, 3, stats);
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.getMovesMap().toString());
        assertEquals(23_960L, result, "From the initial position there are 23_960L possible positions at depth 3");
    }

    @Test
    void perftNumberOfPositionsAfterE3_D6_QG4() {
        gameState = new ChessGame(chessboard, new FENString(FEN_POSITION_AFTER_E3_D6_QG4));
        long result = perft(2, 2, stats);
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.getMovesMap().toString());
        assertEquals(1_111L, result, "From the initial position there are 1_111L possible positions at depth 2");
    }

    @Test
    void perftNumberOfPositionsAfterE3_D6_Bb5() {
        gameState = new ChessGame(chessboard, new FENString(FEN_POSITION_AFTER_E3_D6_BB5));
        long result = perft(2, 2, stats);
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.getMovesMap().toString());
        assertEquals(168, result, "From the initial position there are 168 possible positions at depth 2");
    }

    @Test
    void perftNumberOfPositionsAfterE3_D6_Bb5_Bd7() {
        gameState = new ChessGame(chessboard, new FENString(FEN_POSITION_AFTER_E3_D6_BB5_BD7));
        long result = perft(1, 1, stats);
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.getMovesMap().toString());
        assertEquals(34, result, "From the initial position there are 34 possible positions at depth 1");
    }

    @Test
    void perftNumberOfPositionsAfter_a4() {
        gameState = new ChessGame(chessboard, new FENString(FEN_POSITION_AFTER_A4));
        long result = perft(5, 5, stats);
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.getMovesMap().toString());
        assertEquals(5_363_555, result, "From the initial position there are 5_363_555 possible positions at depth 5");
    }

    @Test
    void perftNumberOfPositionsAfter_a4_c6() {
        gameState = new ChessGame(chessboard, new FENString(FEN_POSITION_AFTER_A4_C6));
        long result = perft(4, 4, stats);
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.getMovesMap().toString());
        assertEquals(244_282, result, "From the initial position there are 244_282 possible positions at depth 4");
    }

    @Test
    void perftNumberOfPositionsAfter_a4_c6_a5() {
        gameState = new ChessGame(chessboard, new FENString(FEN_POSITION_AFTER_A4_C6_A5));
        long result = perft(3, 3, stats);
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.getMovesMap().toString());
        assertEquals(10_292, result, "From the initial position there are 10_292 possible positions at depth 3");
    }

    @Test
    void perftNumberOfPositionsAfter_a4_e5() {
        gameState = new ChessGame(chessboard, new FENString(FEN_POSITION_AFTER_A4_E5));
        long result = perft(4, 4, stats);
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.getMovesMap().toString());
        assertEquals(447_849, result, "From the initial position there are 447_849 possible positions at depth 4");
    }

    @Test
    void perftNumberOfPositionsAfter_a4_e5_d4() {
        gameState = new ChessGame(chessboard, new FENString(FEN_POSITION_AFTER_A4_E5_D4));
        long result = perft(3, 3, stats);
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.getMovesMap().toString());
        assertEquals(28_212, result, "From the initial position there are 28_212 possible positions at depth 3");
    }

    @Test
    void perftNumberOfPositionsAfter_a4_Nc6() {
        gameState = new ChessGame(chessboard, new FENString(FEN_POSITION_AFTER_A4_NC6));
        long result = perft(4, 4, stats);
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.getMovesMap().toString());
        assertEquals(257_893, result, "From the initial position there are 257_893 possible positions at depth 4");
    }

    @Test
    void perftNumberOfPositionsAfter_a4_Nc6_a5() {
        gameState = new ChessGame(chessboard, new FENString(FEN_POSITION_AFTER_A4_NC6_A5));
        long result = perft(3, 3, stats);
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.getMovesMap().toString());
        assertEquals(10_729, result, "From the initial position there are 10_729 possible positions at depth 3");
    }

    @Test
    void perftNumberOfPositionsAfter_a4_a5() {
        gameState = new ChessGame(chessboard, new FENString(FEN_POSITION_AFTER_A4_A5));
        long result = perft(4, 4, stats);
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.getMovesMap().toString());
        assertEquals(204_508, result, "From the initial position there are 204_508 possible positions at depth 4");
    }

    @Test
    void perftNumberOfPositionsAfter_a4_a5_b4() {
        gameState = new ChessGame(chessboard, new FENString(FEN_POSITION_AFTER_A4_A5_B4));
        long result = perft(3, 3, stats);
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.getMovesMap().toString());
        assertEquals(10_798, result, "From the initial position there are 10_798 possible positions at depth 3");
    }

    @Test
    void perftNumberOfPositionsAfter_a4_a5_b4_ra6() {
        gameState = new ChessGame(chessboard, new FENString(FEN_POSITION_AFTER_A4_A5_B4_RA6));
        long result = perft(2, 2, stats);
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.getMovesMap().toString());
        assertEquals(592, result, "From the initial position there are 592 possible positions at depth 2");
    }

    @Test
    void perftNumberOfPositionsAfter_a4_a5_b4_ra6_b4a5() {
        gameState = new ChessGame(chessboard, new FENString(FEN_POSITION_AFTER_A4_A5_B4_RA6_PA4B5));
        long result = perft(1, 1, stats);
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.getMovesMap().toString());
        assertEquals(27, result, "From the initial position there are 27 possible positions at depth 1");
    }

    @Test
    void bulkPerftNumberOfPositionsAtInitialPositionDepth5() {
        gameState = new ChessGame(chessboard, new FENString(FEN_INITIAL_POSITION));
        long result = bulkPerft(5);
        assertEquals(4_865_609L, result, "From the initial position there are 4_865_609 possible legal moves at depth 5");
        //takes 14s
    }
    @Test
    void bulkPerftNumberOfPositionsAtInitialPositionDepth6() {
        gameState = new ChessGame(chessboard, new FENString(FEN_INITIAL_POSITION));
        long result = bulkPerft(6);
        assertEquals(119_060_324L, result, "From the initial position there are 119_060_324 possible legal moves at depth 6");
        //takes 5m04s
    }
   // @Test
    void perftNumberOfPositionsAtInitialPositionDepth6() {
        gameState = new ChessGame(chessboard, new FENString(FEN_INITIAL_POSITION));
        long result = perft(6, 6, stats);
        assertEquals(119_060_324L, result, "From the initial position there are 119_060_324 possible positions at depth 6");
        //takes too long
    }
    //@Test
    void bulkPerftNumberOfPositionsAtInitialPositionDepth7() {
        gameState = new ChessGame(chessboard, new FENString(FEN_INITIAL_POSITION));
        long result = bulkPerft(7);
        assertEquals(3_195_901_860L, result, "From the initial position there are 3_195_901_860 possible legal moves at depth 3");
        //takes too long > 12h
    }
    /**
     * Compare the result of this function applied to your moves generator to the
     * established results at the various depths.
     * @param startingDepth initial depth of moves tree search.
     * @param currentDepth  intermediate depth >=0 and <= startingDepth.
     * @param stats         statistics computed by move generator.
     * @return number of leaf nodes (legal moves) in a chess position.
     * @see "https://www.chessprogramming.org/Perft_Results"
     */
    private long perft(int startingDepth, int currentDepth, MoveGeneratorStatistics stats) {
        //    LOGGER.debug("{} is entering perft at current depth: {}", gameState.getCurrentColor(), currentDepth);
        if (currentDepth == 0) {
            //      LOGGER.info("Number of captures at depth " + currentDepth + ": " + stats.getNumberOfCaptures());
            gameState.switchCurrentPlayerColor();
            return 1L;
        }
        List<Move> movesList = (List<Move>) gameState.getLegalMovesForColor(gameState.getCurrentColor());
        //   LOGGER.debug("{} generated {} moves at depth {}: {}", gameState.getCurrentColor(), moves.size(), currentDepth, moves);
        long totalNumberOfPositions = 0;
        for (Move move : movesList) {
            //     LOGGER.debug("{} will now execute move {}: {} at depth {}",gameState.getCurrentColor(),i,movesList.get(i), currentDepth);
            gameState.makeMove(move, movesList);
            gameState.switchCurrentPlayerColor();
            long numberOfNodesFromPosition = perft(startingDepth, currentDepth - 1, stats);
            totalNumberOfPositions = totalNumberOfPositions + numberOfNodesFromPosition;
            //     LOGGER.debug("Back to depth {}", currentDepth);
            //   stats.addNumberOfCapturesPerDepth(currentDepth, stats.getNumberOfCaptures());
            //stats.addMoveNodes(move.toString(), numberOfNodesFromPosition);//stats.getNumberOfDepthRelatedLeaves(currentDepth));
            //      LOGGER.debug("{} will now undo move {}: {} at depth {}", gameState.getCurrentColor(), i,
            //            movesList.get(i), currentDepth);
            gameState.undo(move);
            if (startingDepth == currentDepth) {
                stats.addMoveNodes(move.toString(), numberOfNodesFromPosition);
                //       LOGGER.info("Number of captures at depth " + currentDepth + ": " + stats.getNumberOfCaptures());
                //LOGGER.info("Moves map for {} at depth {}: {}", gameState.getCurrentColor(), currentDepth, stats.getMovesMap());
                //       LOGGER.info("Number of checks at depth " + currentDepth + ": " + stats.getNumberOfChecks());
            }
        }
        //stats.resetNumberOfCapturesAtDepth(currentDepth);
        gameState.switchCurrentPlayerColor();
        //    LOGGER.debug("Exiting perft at current depth: {}",currentDepth);
        return totalNumberOfPositions;
    }

    private long bulkPerft (int depth) {
        long totalNumberOfPositions = 0;
        Collection<Move> movesList = gameState.getLegalMovesForColor(gameState.getCurrentColor());
        long numberOfNodesFromPosition = movesList.size();
        if (depth == 1) {
            gameState.switchCurrentPlayerColor();
            return numberOfNodesFromPosition;
        }
       // LOGGER.info("Depth: {}, {}: {}", depth, gameState.getCurrentColor().toString(), movesList);
        for (Move move : movesList) {
            gameState.makeMove(move, movesList);
            gameState.switchCurrentPlayerColor();
            numberOfNodesFromPosition = bulkPerft(depth - 1);
            totalNumberOfPositions += numberOfNodesFromPosition;
            gameState.undo(move);
        }
        gameState.switchCurrentPlayerColor();
        return totalNumberOfPositions;
    }

    public long perftLegalMoves(int depth) {
        long nodes = 0;
        if (depth == 0) {
            gameState.switchCurrentPlayerColor();
            return 1L;
        }
        Collection<Move> moves = gameState.getLegalMovesForColor(gameState.getCurrentColor());//generateMoves();
        for (Move move : moves) {
            LOGGER.info("About to check if move {} is legal", move);
            if (move.isLegal(chessboard)) {
                gameState.makeMove(move, moves);
                gameState.switchCurrentPlayerColor();
                nodes += perftLegalMoves(depth - 1);
                gameState.undo(move);
            }
        }
        gameState.switchCurrentPlayerColor();
        return nodes;
    }
}