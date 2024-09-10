package com.marcopiccionitraining.parkychess;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

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
        long result = perft(0,0, stats);
        assertEquals(1, result, "Perft (0) must evaluate to 1.");
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.toString());
    }
    @Test
    void numberOfMovesAtSimplestPlayablePositionDepth1() {
        gameState = new ChessGame(chessboard, new FENString(FEN_SIMPLEST_PLAYABLE));
        long result = perft(1,1, stats);
        assertEquals(4, result, "There are 4 possible positions at depth 1");
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.toString());
    }
    @Test
    void numberOfMovesAtSimplestPlayablePositionDepth2() {
        gameState = new ChessGame(chessboard, new FENString(FEN_SIMPLEST_PLAYABLE));
        long result = perft(2,2, stats);
        assertEquals(12, result, "There are 12 possible positions at depth 2");
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.toString());
    }
    @Test
    void oneBlackOneWhitePawnAtHomeDepth1() {
        gameState = new ChessGame(chessboard, new FENString(FEN_ONE_BLACK_ONE_WHITE_PAWN_AT_STARTING_SQUARE));
        long result = perft(1,1, stats);
        assertEquals(5, result, "There are 5 possible positions at depth 1");
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.toString());
    }
    @Test
    void oneBlackOneWhitePawnAtHomeDepth2() {
        gameState = new ChessGame(chessboard, new FENString(FEN_ONE_BLACK_ONE_WHITE_PAWN_AT_STARTING_SQUARE));
        long result = perft(2,2, stats);
        assertEquals(25, result, "There are 25 possible positions at depth 2");
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.toString());
    }
    @Test
    void oneBlackOneWhitePawnAtHomeOnAdjacentColumnsDepth1() {
        gameState = new ChessGame(chessboard, new FENString(FEN_ONE_BLACK_ONE_WHITE_PAWN_AT_STARTING_SQUARE_ON_ADJACENT_COLUMNS));
        long result = perft(1,1, stats);
        assertEquals(5, result, "There are 5 possible positions at depth 1");
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.toString());
    }
    @Test
    void oneBlackOneWhitePawnAtHomeOnAdjacentColumnsDepth2() {
        gameState = new ChessGame(chessboard, new FENString(FEN_ONE_BLACK_ONE_WHITE_PAWN_AT_STARTING_SQUARE_ON_ADJACENT_COLUMNS));
        long result = perft(2,2, stats);
        assertEquals(25, result, "There are 25 possible positions at depth 2");
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.toString());
    }
    @Test
    void twoBlackTwoWhitePawnsAllAtHomeDepth1() {
        gameState = new ChessGame(chessboard, new FENString(FEN_TWO_BLACK_TWO_WHITE_PAWNS_AT_STARTING_SQUARE));
        long result = perft(1,1, stats);
        assertEquals(7, result, "There are 7 possible positions at depth 1");
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.toString());
    }
    @Test
    void twoBlackTwoWhitePawnsAllAtHomeDepth2() {
        gameState = new ChessGame(chessboard, new FENString(FEN_TWO_BLACK_TWO_WHITE_PAWNS_AT_STARTING_SQUARE));
        long result = perft(2,2, stats);
        assertEquals(49, result, "There are 49 possible positions at depth 2");
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.toString());
    }
    @Test
    void numberOfPositionsAtInitialPositionDepth1() {
        gameState = new ChessGame(chessboard, new FENString(FEN_INITIAL_POSITION));
        long result = perft(1,1, stats);
        assertEquals(20, result, "From the initial position there are 20 possible legal moves at depth 1");
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.toString());
    }
    @Test
    void numberOfPositionsAtInitialPositionDepth2() {
        gameState = new ChessGame(chessboard, new FENString(FEN_INITIAL_POSITION));
        long result = perft(2,2, stats);
        assertEquals(400, result, "From the initial position there are 400 possible positions at depth 2");
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.toString());
    }
    @Test
    void numberOfPositionsAtInitialPositionDepth3() {
        gameState = new ChessGame(chessboard, new FENString(FEN_INITIAL_POSITION));
        long result = perft(3,3, stats);
        assertEquals(8902, result, "From the initial position there are 8902 possible positions at depth 3");
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.toString());
    }
    @Test
    void numberOfPositionsAtInitialPositionDepth4() {
        gameState = new ChessGame(chessboard, new FENString(FEN_INITIAL_POSITION));
        long result = perft(4,4, stats);
        assertEquals(197281, result, "From the initial position there are 197281 possible positions at depth 4");
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.toString());
        //takes 22s
    }
    @Test
    void numberOfPositionsAtInitialPositionDepth5() {
        gameState = new ChessGame(chessboard, new FENString(FEN_INITIAL_POSITION));
        long result = perft(5,5, stats);
        assertEquals(4865609, result, "From the initial position there are 4865609 possible positions at depth 5");
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.toString());
        //takes 10m
    }
    @Test
    void numberOfPositionsAtInitialPositionDepth6() {
        gameState = new ChessGame(chessboard, new FENString(FEN_INITIAL_POSITION));
        long result = perft(6,6, stats);
        assertEquals(119060324, result, "From the initial position there are 119060324 possible positions at depth 6");
      //  stats.setNumberOfLeaves(result);
      //  LOGGER.info(stats.toString());
        //takes roughly 36hrs
    }
    /**
     * Compare the result of this function applied to your moves generator to the
     * established results at the various depths.
     * @param startingDepth initial depth of moves tree search.
     * @param currentDepth intermediate depth >=0 and <= startingDepth.
     * @param stats statistics computed by move generator.
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
        Collection<Move> moves = gameState.getLegalMovesForColor(gameState.getCurrentColor());
     //   LOGGER.debug("{} generated {} moves at depth {}: {}", gameState.getCurrentColor(), moves.size(), currentDepth, moves);
        List<Move> movesList = moves.stream().toList();
        long totalNumberOfPositions = 0;
        for (int i = 0; i < moves.size(); i++) {
       //     LOGGER.debug("{} will now execute move {}: {} at depth {}", gameState.getCurrentColor(), i,
             //       movesList.get(i), currentDepth);
            gameState.makeMove(movesList.get(i));
            gameState.switchCurrentPlayerColor();
            long numberOfNodesFromPosition = perft(startingDepth, currentDepth - 1, stats);
            totalNumberOfPositions = totalNumberOfPositions + numberOfNodesFromPosition;
       //     LOGGER.debug("Back to depth {}", currentDepth);
            //   stats.addNumberOfCapturesPerDepth(currentDepth, stats.getNumberOfCaptures());
            //stats.addMoveNodes(moves.get(i).getKey(), stats.getNumberOfDepthRelatedLeaves(currentDepth));
      //      LOGGER.debug("{} will now undo move {}: {} at depth {}", gameState.getCurrentColor(), i,
        //            movesList.get(i), currentDepth);
            gameState.undo(movesList.get(i));
            if (startingDepth == currentDepth) {
                stats.addMoveNodes(movesList.get(i).toString(), numberOfNodesFromPosition);
                //       LOGGER.info("Number of captures at depth " + currentDepth + ": " + stats.getNumberOfCaptures());
                LOGGER.info("Moves map for {} at depth {}: {}", gameState.getCurrentColor(), currentDepth, stats.getMovesMap());
                //       LOGGER.info("Number of checks at depth " + currentDepth + ": " + stats.getNumberOfChecks());
                //        LOGGER.info("moves map at depth " + currentDepth + ": " + stats.getMovesMap());
            }
        }
        stats.resetNumberOfCapturesAtDepth(currentDepth);
        gameState.switchCurrentPlayerColor();
    //    LOGGER.debug("Exiting perft at current depth: {}",currentDepth);
        return  totalNumberOfPositions;
    }
}
