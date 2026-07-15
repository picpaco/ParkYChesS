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
        log.debug("{} is entering perft at depth: {}",
                gameState.getChessboard().getCurrentPlayerColor(), currentDepth);
        assert startingDepth >= 0 : "Starting depth should be non-negative.";
        assert startingDepth <= Board.MAX_DEPTH : "Starting depth should not be greater than " + Board.MAX_DEPTH;
        assert currentDepth >= 0 : "Current depth should be non-negative.";
        assert currentDepth <= Board.MAX_DEPTH : "Current depth should not be greater than " + Board.MAX_DEPTH;
        long totalNumberOfNodes = 0;
        if (currentDepth == 0) {
            log.debug("Number of captures at current depth {}: {}", currentDepth, stats.getNumberOfCaptures());
            return 1L;
        }
        long numberOfNodesFromPosition;
        List<Move> legalMovesForColor = gameState.getLegalMovesForColor(gameState.getChessboard().getCurrentPlayerColor());
        log.error("Legal moves for {} at depth {}: {}", gameState.getChessboard().getCurrentPlayerColor(),
                currentDepth, legalMovesForColor);
        log.trace("Just after legal moves for {}", gameState.getChessboard().getCurrentPlayerColor());
        for (Move legalMove : legalMovesForColor) {
            log.debug("{} moves {} at depth {}", gameState.getChessboard().getCurrentPlayerColor(),
                    legalMove, currentDepth);
            LocalGameState localGameStateBeforeMakeMove = new LocalGameState(gameState.getChessboard());
            saveLocalState (localGameStateBeforeMakeMove, legalMove);
            log.info("Local state before move {}: {}", legalMove, localGameStateBeforeMakeMove);
            gameState.makeMove(legalMove, legalMovesForColor);
            numberOfNodesFromPosition = perft(startingDepth, currentDepth - 1, stats);
            totalNumberOfNodes = totalNumberOfNodes + numberOfNodesFromPosition;
            stats.addNumberOfCapturesPerDepth(currentDepth, stats.getNumberOfCaptures());
            stats.addMoveNodes(gameState.getChessboard().getCurrentPlayerColor(), legalMove.toString(), numberOfNodesFromPosition);
            gameState.undo(legalMove, localGameStateBeforeMakeMove);
            LocalGameState localGameStateAfterUndo = new LocalGameState(gameState.getChessboard());
            saveLocalState (localGameStateAfterUndo, legalMove);
            log.info("Local state after undoing move {}: {}", legalMove, localGameStateAfterUndo);
            assert localGameStateBeforeMakeMove.equals(localGameStateAfterUndo) :
                    "The local game state has not been restored correctly between making a move and undoing it. Before:" +
                    localGameStateBeforeMakeMove + " \nAfter: " + localGameStateAfterUndo;
            if (startingDepth == currentDepth) {
                stats.addMoveNodes(gameState.getChessboard().getCurrentPlayerColor(), legalMove.toString(), numberOfNodesFromPosition);
                log.trace("Number of captures at depth {}: {}", currentDepth, stats.getNumberOfCaptures());
                log.info("Partial moves map for {} at depth {}: {}", gameState.getChessboard().getCurrentPlayerColor(),
                    currentDepth, stats.getMapValues(gameState.getChessboard().getCurrentPlayerColor()));
                log.trace("Number of checks at depth {}: {}", currentDepth, stats.getNumberOfChecks());
            }
        }
        stats.resetNumberOfCapturesAtDepth(currentDepth);
        log.info("Exiting perft at current depth: {}", currentDepth);
        return totalNumberOfNodes;
    }

    private void saveLocalState (LocalGameState localGameState, Move move) {
        localGameState.setPiecesPositions (localGameState.getChessboard().getPiecesPositions());
        localGameState.setPlayerColor(gameState.getChessboard().getCurrentPlayerColor());
        localGameState.setCapturedPiece(move.getCapturedPiece());
        localGameState.setBlackKingsideCastlingAllowed(gameState.getChessboard().isKingsideCastlingPossible(PlayerColor.BLACK));
        localGameState.setBlackQueensideCastlingAllowed(gameState.getChessboard().isQueensideCastlingPossible(PlayerColor.BLACK));
        localGameState.setWhiteKingsideCastlingAllowed(gameState.getChessboard().isKingsideCastlingPossible(PlayerColor.WHITE));
        localGameState.setWhiteQueensideCastlingAllowed(gameState.getChessboard().isQueensideCastlingPossible(PlayerColor.WHITE));
        if (move.getName().equals(MoveNames.DOUBLE_PAWN)) {
            DoublePawnMove doublePawnMove = (DoublePawnMove) move;
            localGameState.setSkippedPawnPosition(doublePawnMove.getSkippedPawnPosition());
        }
        localGameState.setHalfMoveCounter(gameState.getNumberOfHalfMovesSinceLastCaptureOrPawnMove());
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
        List<Move> movesList = gameState.getLegalMovesForColor (gameState.getChessboard().getCurrentPlayerColor());
        long numberOfNodesFromPosition = movesList.size();
        log.trace("{} generated the following {} legal moves at depth {}: {}",
                gameState.getChessboard().getCurrentPlayerColor(), movesList.size(), depth, movesList);
        if (depth == 1) {
            return numberOfNodesFromPosition;
        }
        log.info("{} generated the following {} legal moves at depth {}: {}", gameState.getChessboard().getCurrentPlayerColor(),
                movesList.size(), depth, movesList);
        for (Move legalMove : movesList) {
            log.trace("Move {} executed by {} at depth {}", legalMove,
                    gameState.getChessboard().getCurrentPlayerColor(), depth);
            log.trace("About to invoke ChessGame.makeMove");
            LocalGameState localGameStateBeforeMakeMove = new LocalGameState(gameState.getChessboard());
            saveLocalState (localGameStateBeforeMakeMove, legalMove);
            gameState.makeMove(legalMove, movesList);//, localGameStateBeforeMakeMove);
            numberOfNodesFromPosition = bulkPerft(depth - 1);
            totalNumberOfNodes = totalNumberOfNodes + numberOfNodesFromPosition;
            gameState.undo(legalMove, localGameStateBeforeMakeMove);
            LocalGameState localGameStateAfterUndo = new LocalGameState(gameState.getChessboard());
            saveLocalState (localGameStateAfterUndo, legalMove);
            log.debug("Local state after undoing move {}: {}", legalMove, localGameStateAfterUndo);
            assert localGameStateBeforeMakeMove.equals(localGameStateAfterUndo) :
                    "Local game state incorrectly restored after making and undoing move " + legalMove;
        }
        return totalNumberOfNodes;
    }
}
