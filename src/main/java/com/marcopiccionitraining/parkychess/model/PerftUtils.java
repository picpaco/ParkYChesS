package com.marcopiccionitraining.parkychess.model;

import com.marcopiccionitraining.parkychess.model.moves.DoublePawnMove;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import com.marcopiccionitraining.parkychess.model.moves.MoveNames;
import com.marcopiccionitraining.parkychess.model.pieces.Piece;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PerftUtils {

    private final ChessGame gameState;



    public PerftUtils (ChessGame gameState) {
        this.gameState = gameState;
    }
    /**
     * Compare the result of this function applied to your moves generator to the
     * established results at the various depths.
     * @param startingDepth initial depth of moves tree search.
     * @param currentDepth  intermediate depth which must be >= 0 and <= startingDepth.
     * @param stats statistics computed by move generator.
     * @return number of leaf nodes (legal moves) in a chess position.
     * @see "https://www.chessprogramming.org/Perft_Results"
     */
    public long perft (int startingDepth, int currentDepth, MoveGeneratorStatistics stats) {
        log.error("{} has just entered perft at depth: {}",
                gameState.getChessboard().getCurrentPlayerColor(), currentDepth);
        assert startingDepth >= 0 : "Starting depth should be non-negative.";
        assert startingDepth <= Board.MAX_DEPTH : "Starting depth should not be greater than " + Board.MAX_DEPTH;
        assert currentDepth >= 0 : "Current depth should be non-negative.";
        assert currentDepth <= Board.MAX_DEPTH : "Current depth should not be greater than " + Board.MAX_DEPTH;
        long totalNumberOfNodes = 0;
        if (currentDepth == 0) {
            log.debug("Number of captures at current depth {}: {}", currentDepth, stats.getNumberOfCaptures());
            //    gameState.getChessboard().switchCurrentPlayerColor();
            //log.error("Switched color from {} to {}", gameState.getChessboard().getCurrentPlayerColor(), PlayerColor.getOpponentColor(
              //     gameState.getChessboard().getCurrentPlayerColor()));
            return 1L;
        }
        long numberOfNodesFromPosition;
        List<Move> legalMovesForColor = gameState.getLegalMovesForColor(gameState.getChessboard().getCurrentPlayerColor());
        log.error("Legal moves for {} at depth {}: {}", gameState.getChessboard().getCurrentPlayerColor(),
                currentDepth, legalMovesForColor);
        for (Move legalMove : legalMovesForColor) {
            log.error("{} is about to make move {} at depth {}", gameState.getChessboard().getCurrentPlayerColor(),
                    legalMove, currentDepth);
            LocalGameState localGameStateBeforeMakeMove = new LocalGameState(gameState.getChessboard());
            Long zobristKeyBeforeMakeMove = gameState.getChessboard().getNewZobristKey(localGameStateBeforeMakeMove);
            log.error("Local state before make move {}: {}", legalMove, localGameStateBeforeMakeMove);
            if (gameState.getChessboard().containsZobristKey(zobristKeyBeforeMakeMove)) {
                int numberOfOccurrences = gameState.getChessboard().getZobristValue(zobristKeyBeforeMakeMove);
                numberOfOccurrences++;
                log.error("The current position has been reached {} times.", numberOfOccurrences);
                gameState.getChessboard().saveZobristEntry(zobristKeyBeforeMakeMove, numberOfOccurrences);
            } else {
                //log.error("The current position {} was never reached before.", gameState.getChessboard());
                log.error("The current position was never reached before.");
                gameState.getChessboard().saveZobristEntry(zobristKeyBeforeMakeMove, 1);
            }
            gameState.makeMove(legalMovesForColor, legalMove);
            log.error("About to move from depth {} to {}", currentDepth, currentDepth - 1);
            //gameState.getChessboard().switchCurrentPlayerColor();
            numberOfNodesFromPosition = perft(startingDepth, currentDepth - 1, stats);
            totalNumberOfNodes = totalNumberOfNodes + numberOfNodesFromPosition;
            stats.addNumberOfCapturesPerDepth(currentDepth, stats.getNumberOfCaptures());
            stats.addMoveNodes(gameState.getChessboard().getCurrentPlayerColor(), legalMove.toString(), numberOfNodesFromPosition);
            log.error("{} is about to undo move {} at depth {}", gameState.getChessboard().getCurrentPlayerColor(),
                    legalMove, currentDepth);
            gameState.undo(legalMove, localGameStateBeforeMakeMove);
            LocalGameState localGameStateAfterUndo = new LocalGameState(gameState.getChessboard());
            Long zobristKeyAfterUndo = gameState.getChessboard().getNewZobristKey(localGameStateAfterUndo);
            assert localGameStateBeforeMakeMove.equals(localGameStateAfterUndo) :
                    "Local game state not restored correctly when undoing a previously made move." + "\nBefore:" +
                    localGameStateBeforeMakeMove + "\nAfter: " + localGameStateAfterUndo;
            if (startingDepth == currentDepth) {
                stats.addMoveNodes(gameState.getChessboard().getCurrentPlayerColor(), legalMove.toString(), numberOfNodesFromPosition);
                log.trace("Number of captures at depth {}: {}", currentDepth, stats.getNumberOfCaptures());
                log.error("Partial moves map for {} at depth {}: {}", gameState.getChessboard().getCurrentPlayerColor(),
                    currentDepth, stats.getMovesMapAsString(gameState.getChessboard().getCurrentPlayerColor()));
                log.trace("Number of checks at depth {}: {}", currentDepth, stats.getNumberOfChecks());
            }
        }
        stats.resetNumberOfCapturesAtDepth(currentDepth);
        log.error("Exiting perft at depth: {}", currentDepth);
        return totalNumberOfNodes;
    }

    /**
     * Compare the result of this function applied to your moves generator to the
     * established result (e.g. computed by Stockfish) at the given depth.
     * @param depth initial depth of moves tree search.
     * @return number of leaf nodes (legal positions) for each legal move in a given chess position.
     * @see "https://www.chessprogramming.org/Perft_Results"
     */
    public long bulkPerft (int depth) {
        assert depth > 0 : "Depth should be strictly positive.";
        assert depth <= Board.MAX_DEPTH : "Depth should not be greater than " + Board.MAX_DEPTH;
        long totalNumberOfNodes = 0;
        List<Move> legalMovesForColor = gameState.getLegalMovesForColor (gameState.getChessboard().getCurrentPlayerColor());
        log.error("List of legal moves for {} at depth {}", gameState.getChessboard(), depth);
        long numberOfNodesFromPosition = legalMovesForColor.size();
        log.error("{} generated the following {} legal moves at depth {}: {}",
                gameState.getChessboard().getCurrentPlayerColor(), legalMovesForColor.size(), depth, legalMovesForColor);
        if (depth == 1) {
            return numberOfNodesFromPosition;
        }
        for (Move legalMove : legalMovesForColor) {
            log.error("Move {} executed by {} at depth {}", legalMove,
                    gameState.getChessboard().getCurrentPlayerColor(), depth);
            log.error("About to invoke ChessGame.makeMove on move {}", legalMove);
            LocalGameState localGameStateBeforeMakeMove = new LocalGameState(gameState.getChessboard());
            log.error("Local state before making move {}: {}", legalMove, localGameStateBeforeMakeMove);
            gameState.makeMove(legalMovesForColor, legalMove);
            //LocalGameState localGameStateImmediatelyAfterMakeMove = new LocalGameState(gameState.getChessboard());
            //log.error("Local state immediately after making move {}: {}", legalMove, localGameStateImmediatelyAfterMakeMove);
            numberOfNodesFromPosition = bulkPerft(depth - 1);
            totalNumberOfNodes = totalNumberOfNodes + numberOfNodesFromPosition;
            gameState.undo(legalMove, localGameStateBeforeMakeMove);
            LocalGameState localGameStateAfterUndo = new LocalGameState(gameState.getChessboard());
            log.error("Local state after undoing move {}: {}", legalMove, localGameStateAfterUndo);
            assert localGameStateBeforeMakeMove.equals(localGameStateAfterUndo) :
                    "Local game state incorrectly restored after making and undoing move " + legalMove +
                            "\nlocal game state before making move: " + localGameStateBeforeMakeMove + " " +
                            "\nlocal game state after undoing move: " + localGameStateAfterUndo;
        }
        return totalNumberOfNodes;
    }
}
