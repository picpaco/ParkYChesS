package com.marcopiccionitraining.parkychess.model.moves;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.pieces.Piece;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * This class encapsulates the properties of a double pawn (two-steps forward) move.
 * A pawn can perform a double pawn move only if:
 * 1. It has never been moved from its home square.
 * 2. The two squares in front of it are empty.
 */
@Slf4j
public class DoublePawnMove extends Move {
    /**
     Given a legal game position, if the player who has the move makes a double pawn move,
     then the first square in the double move is defined as skippedPawnPosition.
     */
    @Getter
    private final Position skippedPawnPosition;

    public DoublePawnMove(@NonNull Position from, @NonNull Position to){
        assert from.row() == 1 || from.row() == 6 : "The pawn must be in its home square.";
        assert from.column() == to.column():"The start and destination square must lie on the same column.";
        assert (from.row() == (to.row() + 2)) || (from.row() == (to.row() - 2)):
                "The start and destination square must lie on rows that are at a distance of +-2 from each other.";

        setName (MoveNames.DOUBLE_PAWN);
        setFrom (from);
        setTo (to);
        skippedPawnPosition = new Position (((from.row() + to.row()) / 2), from.column());
        log.trace("DoublePawnMove from {} to {} created. skippedPawnPosition is {}", from, to, skippedPawnPosition);
    }
    @Override
    public boolean execute (@NonNull Board chessboard){
        assert (chessboard.getPiece(getFrom()).getColor().equals(PlayerColor.BLACK) && getFrom().row() == 1) ||
                (chessboard.getPiece(getFrom()).getColor().equals(PlayerColor.WHITE) && getFrom().row() == 6) :
                "A pawn can execute a double move only from its starting square.";
        assert chessboard.isEmpty(skippedPawnPosition) : "The square in front of the pawn must be empty.";

        Position twoBlackStepsForward = new Position (skippedPawnPosition.row() + 1, skippedPawnPosition.column());
        Position twoWhiteStepsForward = new Position (skippedPawnPosition.row() - 1, skippedPawnPosition.column());
        assert chessboard.isEmpty(twoBlackStepsForward) || chessboard.isEmpty(twoWhiteStepsForward) :
                 "The second square in front of a black or white pawn must be empty.";
        PlayerColor currentPlayerColor = chessboard.getPiece(getFrom()).getColor();
        log.trace("While in DoublePawnMove.execute I am setting pawnSkippedPosition for {} to {}.",
                currentPlayerColor, skippedPawnPosition);
        chessboard.setPawnSkippedPosition (currentPlayerColor, skippedPawnPosition);
        chessboard.getGameStateContainer().peek().setCapturedGhostPawnSquare(getTo());
        if (currentPlayerColor.equals(PlayerColor.WHITE)) {
          //  return updateState(chessboard, twoWhiteStepsForward, currentPlayerColor);
            new StandardMove(getFrom(), twoWhiteStepsForward).execute (chessboard);
        } else {
            new StandardMove(getFrom(), twoBlackStepsForward).execute (chessboard);
          //  if (currentPlayerColor.equals(PlayerColor.BLACK) && chessboard.isEmpty(twoBlackStepsForward)) {
            //    return updateState(chessboard, twoBlackStepsForward, currentPlayerColor);
            //}
        }
        return true;
    }

  /*  private boolean updateState(@NonNull Board chessboard, @NonNull Position twoStepsForward, @NonNull PlayerColor currentColor) {
        boolean result = true;
        FENStateString currentState;
        GameStateContainer currentGameState = chessboard.getGameStateContainer();
        new StandardMove(getFrom(), twoStepsForward).execute(chessboard);
        if (currentGameState.isEmpty()) {
            currentState = new FENStateString(chessboard, currentColor);
            currentState.setMovedPiece(chessboard.getPiece(twoStepsForward));
            currentState.setCapturedGhostPawnSquare(getTo());
        } else {
            currentState = chessboard.getGameStateContainer().pop();
            log.trace("After pop in execute. state stack size: {}", currentGameState.size());
            currentState.setMovedPiece(chessboard.getPiece(twoStepsForward));
            currentState.setCapturedGhostPawnSquare(getTo());
            currentGameState.push(currentState);
            log.trace("After push in execute: state stack size: {}", currentGameState.size());
        }
        return result;
    }*/

    @Override
        public void undo (@NonNull Board chessboard, @NonNull FENStateString oldGameState) {
        Piece piece = chessboard.getPiece(oldGameState.getLastExecutedMove().getTo());
        chessboard.setPiece(piece, oldGameState.getLastExecutedMove().getFrom());
        chessboard.setEmpty(oldGameState.getLastExecutedMove().getTo());
    }
}
