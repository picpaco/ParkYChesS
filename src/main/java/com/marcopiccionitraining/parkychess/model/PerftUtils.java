package com.marcopiccionitraining.parkychess.model;

import com.marcopiccionitraining.parkychess.model.moves.Move;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
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
     * @param currentDepth  intermediate depth >=0 and <= startingDepth.
     * @param stats         statistics computed by move generator.
     * @return number of leaf nodes (legal moves) in a chess position.
     * @see "https://www.chessprogramming.org/Perft_Results"
     */
    public long perft (int startingDepth, int currentDepth, MoveGeneratorStatistics stats) {
        log.debug("{} is entering perft at current depth: {}", gameState.getChessboard().getCurrentPlayerColor(), currentDepth);
        long totalNumberOfNodes = 0;
        if (currentDepth == 0) {
            log.trace("Number of captures at depth {}: {}", currentDepth, stats.getNumberOfCaptures());
            gameState.getChessboard().switchCurrentPlayerColor();
            return 1L;
        }
        Collection<Move> movesList = gameState.getLegalMovesForColor(gameState.getChessboard().getCurrentPlayerColor());
        long numberOfNodesFromPosition;
        log.debug("{} can make {} legal moves at depth {}: {}", gameState.getChessboard().getCurrentPlayerColor(),
                movesList.size(), currentDepth, movesList);
        //TODO: Why are we looping again? Refactoring: create just one loop through the pseudolegal moves
        //If a pseudolegal move is legal then execute the code below
        for (Move move : movesList) {
            log.trace("{} will now execute move {} at depth {}", gameState.getChessboard().getCurrentPlayerColor(),
                    move, currentDepth);
            FENStateString savedGameState = new FENStateString(gameState);
            log.debug("Position before push: {}", gameState.getChessboard());
            log.debug("Current game state before push: {}", savedGameState);
            gameState.getChessboard().getGameStateContainer().push(savedGameState);
            gameState.makeMove(move, movesList);
            gameState.getChessboard().switchCurrentPlayerColor();
            numberOfNodesFromPosition = perft(startingDepth, currentDepth - 1, stats);
            totalNumberOfNodes = totalNumberOfNodes + numberOfNodesFromPosition;
            log.debug("Back to depth {}", currentDepth);
            stats.addNumberOfCapturesPerDepth(currentDepth, stats.getNumberOfCaptures());
            stats.addMoveNodes(move.toString(), numberOfNodesFromPosition);
         //   stats.getNumberOfDepthRelatedLeaves(currentDepth);
            log.debug("{} will now undo move {} at depth {}", gameState.getChessboard().getCurrentPlayerColor(), move, currentDepth);
            gameState.undo(move);
            if (startingDepth == currentDepth) {
                stats.addMoveNodes(move.toString(), numberOfNodesFromPosition);
                log.trace("Number of captures at depth {}: {}", currentDepth, stats.getNumberOfCaptures());
                log.debug("Moves map for {} at depth {}: {}", gameState.getChessboard().getCurrentPlayerColor(), currentDepth, stats.getMovesMap());
                log.trace("Number of checks at depth {}: {}", currentDepth, stats.getNumberOfChecks());
            }
        }
        stats.resetNumberOfCapturesAtDepth(currentDepth);
        gameState.getChessboard().switchCurrentPlayerColor();
        log.debug("Exiting perft at current depth: {}",currentDepth);
        return totalNumberOfNodes;
    }

    /**
     * Compare the result of this function applied to your moves generator to the
     * established result (e.g. computed by Stockfish) at the given depth.
     * @param depth initial depth of moves tree search.
     * @return number of leaf nodes (legal moves) in a chess position.
     * @see "https://www.chessprogramming.org/Perft_Results"
     */
    public long bulkPerft (int depth) {
        assert depth > 0 : "Depth should be greater than 0";
        assert depth <= Board.MAX_DEPTH : "Depth should not be greater than " + Board.MAX_DEPTH;

        long totalNumberOfNodes = 0;
        Collection<Move> movesList = gameState.getLegalMovesForColor (gameState.getChessboard().getCurrentPlayerColor());
        long numberOfNodesFromPosition = movesList.size();
        log.debug("{} generated the following {} legal moves at depth {}: {}", gameState.getChessboard().getCurrentPlayerColor(),
                movesList.size(), depth, movesList);
        if (depth == 1) {
            gameState.getChessboard().switchCurrentPlayerColor();
            return numberOfNodesFromPosition;
        }
        log.debug("{} generated the following {} legal moves at depth {}: {}", gameState.getChessboard().getCurrentPlayerColor(),
                movesList.size(), depth, movesList);
        for (Move move : movesList) {
            log.trace("Move {} executed by {} at depth {}", move,
                    gameState.getChessboard().getCurrentPlayerColor(), depth);
            FENStateString currentGameState = gameState.getChessboard().getGameStateContainer().peek();
        //    gameState.getChessboard().getGameStateContainer().push(currentGameState.copy(gameState.getChessboard()));
            gameState.getChessboard().getGameStateContainer().push(new FENStateString(gameState));
            log.debug("About to invoke ChessGame.makeMove");
            gameState.makeMove(move, movesList);
            gameState.getChessboard().switchCurrentPlayerColor();
            numberOfNodesFromPosition = bulkPerft(depth - 1);
            totalNumberOfNodes = totalNumberOfNodes + numberOfNodesFromPosition;
            gameState.undo(move);
        }
        gameState.getChessboard().switchCurrentPlayerColor();
        return totalNumberOfNodes;
    }
}
