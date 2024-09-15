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

/*    @Test
    void perft2NumberOfPositionsAtInitialPositionDepth1() {
        gameState = new ChessGame(chessboard, new FENString(FEN_INITIAL_POSITION));
        long result = perft2(1, 1, stats);
        assertEquals(20, result, "From the initial position there are 20 possible legal moves at depth 1");
    }

    @Test
    void perft3NumberOfPositionsAtInitialPositionDepth1() {
        gameState = new ChessGame(chessboard, new FENString(FEN_INITIAL_POSITION));
        long result = perft3(1);
        assertEquals(20, result, "From the initial position there are 20 possible legal moves at depth 1");
    }*/

    @Test
    void perftNumberOfPositionsAtInitialPositionDepth2() {
        gameState = new ChessGame(chessboard, new FENString(FEN_INITIAL_POSITION));
        long result = perft(2, 2, stats);
        assertEquals(400, result, "From the initial position there are 400 possible positions at depth 2");
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.toString());
    }

/*@Test
    void perft2NumberOfPositionsAtInitialPositionDepth2() {
        gameState = new ChessGame(chessboard, new FENString(FEN_INITIAL_POSITION));
        long result = perft2(2, 2, stats);
        assertEquals(400, result, "From the initial position there are 400 possible legal moves at depth 2");
    }*/

    @Test
    void perftNumberOfPositionsAtInitialPositionDepth3() {
        gameState = new ChessGame(chessboard, new FENString(FEN_INITIAL_POSITION));
        long result = perft(3, 3, stats);
        assertEquals(8902, result, "From the initial position there are 8902 possible positions at depth 3");
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.toString());
    }

   /* @Test
    void perft2NumberOfPositionsAtInitialPositionDepth3() {
        gameState = new ChessGame(chessboard, new FENString(FEN_INITIAL_POSITION));
        long result = perft2(3, 3, stats);
        assertEquals(8902, result, "From the initial position there are 8902 possible legal moves at depth 3");
    }*/

    @Test
    void perftNumberOfPositionsAtInitialPositionDepth4() {
        gameState = new ChessGame(chessboard, new FENString(FEN_INITIAL_POSITION));
        long result = perft(4, 4, stats);
        assertEquals(197281, result, "From the initial position there are 197281 possible positions at depth 4");
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.toString());
        //takes 12s
    }

    @Test
    void perftNumberOfPositionsAtInitialPositionDepth5() {
        gameState = new ChessGame(chessboard, new FENString(FEN_INITIAL_POSITION));
        long result = perft(5, 5, stats);
        assertEquals(4865609, result, "From the initial position there are 4865609 possible positions at depth 5");
        stats.setNumberOfLeaves(result);
        LOGGER.info(stats.toString());
        //takes 5m
    }

    @Test
    void perftNumberOfPositionsAtInitialPositionDepth6() {
        gameState = new ChessGame(chessboard, new FENString(FEN_INITIAL_POSITION));
        long result = perft(6, 6, stats);
        assertEquals(119060324, result, "From the initial position there are 119060324 possible positions at depth 6");
        //  stats.setNumberOfLeaves(result);
        //  LOGGER.info(stats.toString());
        //takes too long > 24h
        /*
Stockfish 15:
a2a3: 4463267
b2b3: 5310358
c2c3: 5417640
d2d3: 8073082
e2e3: 9726018
f2f3: 4404141
g2g3: 5346260
h2h3: 4463070
a2a4: 5363555
b2b4: 5293555
c2c4: 5866666
d2d4: 8879566
e2e4: 9771632
f2f4: 4890429
g2g4: 5239875
h2h4: 5385554
b1a3: 4856835
b1c3: 5708064
g1f3: 5723523
g1h3: 4877234
Nodes searched: 119060324
*/
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
            gameState.makeMove(move);
            gameState.switchCurrentPlayerColor();
            long numberOfNodesFromPosition = perft(startingDepth, currentDepth - 1, stats);
            totalNumberOfPositions = totalNumberOfPositions + numberOfNodesFromPosition;
            //     LOGGER.debug("Back to depth {}", currentDepth);
            //   stats.addNumberOfCapturesPerDepth(currentDepth, stats.getNumberOfCaptures());
            //stats.addMoveNodes(moves.get(i).getKey(), stats.getNumberOfDepthRelatedLeaves(currentDepth));
            //      LOGGER.debug("{} will now undo move {}: {} at depth {}", gameState.getCurrentColor(), i,
            //            movesList.get(i), currentDepth);
            gameState.undo(move);
            if (startingDepth == currentDepth) {
                stats.addMoveNodes(move.toString(), numberOfNodesFromPosition);
                //       LOGGER.info("Number of captures at depth " + currentDepth + ": " + stats.getNumberOfCaptures());
                LOGGER.info("Moves map for {} at depth {}: {}", gameState.getCurrentColor(), currentDepth, stats.getMovesMap());
                //       LOGGER.info("Number of checks at depth " + currentDepth + ": " + stats.getNumberOfChecks());
                //        LOGGER.info("moves map at depth " + currentDepth + ": " + stats.getMovesMap());
            }
        }
        stats.resetNumberOfCapturesAtDepth(currentDepth);
        gameState.switchCurrentPlayerColor();
        //    LOGGER.debug("Exiting perft at current depth: {}",currentDepth);
        return totalNumberOfPositions;
    }
/*
    private long perft2(int startingDepth, int currentDepth, MoveGeneratorStatistics stats) {
        if (currentDepth == 0) {
            gameState.switchCurrentPlayerColor();
            return 1L;
        }
        List<Move> movesList = gameState.generateMoves();
        long totalNumberOfPositions = 0;
        long numberOfNodesFromPosition = 0;
        for (Move move : movesList) {
            //     LOGGER.info("{} will now execute move: {} at depth {}", gameState.getCurrentColor(), move, depth);
            gameState.makeMove(move);
            gameState.switchCurrentPlayerColor();
            if (!(chessboard.isInCheck(gameState.getCurrentColor()))) {
                //     if (!(chessboard.isInCheck(PlayerColor.getOpponentColor(gameState.getCurrentColor())))) {
                numberOfNodesFromPosition = perft2(startingDepth, currentDepth - 1, stats);
                totalNumberOfPositions = totalNumberOfPositions + numberOfNodesFromPosition;
            }
            gameState.undo(move);
            if (startingDepth == currentDepth) {
                stats.addMoveNodes(move.toString(), numberOfNodesFromPosition);
                LOGGER.info("Moves map for {} at depth {}: {}", gameState.getCurrentColor(), currentDepth, stats.getMovesMap());
            }
        }
        gameState.switchCurrentPlayerColor();
        return totalNumberOfPositions;
    }

    public long perft3(int depth) {
        long moveCount = 0;
        if (depth == 0) {
            return moveCount;
        }
        List<Move> moves = gameState.generateMoves();
        for (int i = 0; i < moves.size(); i++) {
            gameState.makeMove(moves.get(i));
         //   if (moves.get(i).isLegal(chessboard)) {
                long subPerftMoveCount = perft3(depth - 1);
                gameState.undo(moves.get(i));
                moveCount += subPerftMoveCount;
           // }
        }
        return moveCount;
    }
*/
}

