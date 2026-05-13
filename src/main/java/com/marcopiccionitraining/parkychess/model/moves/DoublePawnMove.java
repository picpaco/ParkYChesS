package com.marcopiccionitraining.parkychess.model.moves;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.pieces.Pawn;
import com.marcopiccionitraining.parkychess.model.pieces.PieceNames;
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
     * Given a legal game position, if the player who has the move makes a double pawn move,
     * then the first square in the double move is defined as skippedPawnPosition.
     */
    @Getter
    private final Position skippedPawnPosition;

    public DoublePawnMove(@NonNull Position from, @NonNull Position to, Board chessboard) {
        log.debug("from: {}; to: {}", from, to);
        log.debug("from is empty?: {}; to is empty?: {}", chessboard.isEmpty(from), chessboard.isEmpty(to));
        assert from.getRow() == 1 || from.getRow() == 6 : "The pawn must be in its home square.";
        assert from.getColumn() == to.getColumn() : "The start and destination square must lie on the same column.";
        assert Math.abs(to.getRow() - from.getRow()) == 2 : "From and to positions should differ by +-2.";
        assert !chessboard.isEmpty(from) : "On " + from + " there should be a pawn.";
        assert chessboard.getPiece(from).getName().equals(PieceNames.PAWN) : "The piece performing a double pawn move must be a pawn.";
        setName(MoveNames.DOUBLE_PAWN);
        setFrom(from);
        setTo(to);
        skippedPawnPosition = new Position(((from.getRow() + to.getRow()) / 2), from.getColumn());
        if (skippedPawnPosition.getRow() == 2) {
            chessboard.setSkippedPawnPosition(PlayerColor.BLACK, skippedPawnPosition);
        } else {
            chessboard.setSkippedPawnPosition(PlayerColor.WHITE, skippedPawnPosition);
        }
        log.debug("DoublePawnMove for {}  {}-{} (({},{})-({},{})) created. skippedPawnPosition is {}",
                chessboard.getPiece(from).getColor(), from, to, from.getRow(), from.getColumn(), to.getRow(),
                to.getColumn(), skippedPawnPosition);
    }

    @Override
    public boolean execute (@NonNull Board chessboard) {
        Pawn pawn = (Pawn) chessboard.getPiece(getFrom());
        assert pawn.getColor().equals(PlayerColor.BLACK) && pawn.isOnStartingSquare(getFrom(), PlayerColor.BLACK) ||
                (pawn.getColor().equals(PlayerColor.WHITE) && pawn.isOnStartingSquare(getFrom(), PlayerColor.WHITE)) :
                "A pawn can execute a double move only from its starting square.";
        assert chessboard.isEmpty(skippedPawnPosition) : "The square in front of the pawn must be empty.";
        Position twoBlackStepsForward = new Position(skippedPawnPosition.getRow() + 1, skippedPawnPosition.getColumn());
        Position twoWhiteStepsForward = new Position(skippedPawnPosition.getRow() - 1, skippedPawnPosition.getColumn());
        //pawn.setHasJustMovedTwoSteps(true);
        chessboard.setSkippedPawnPosition(chessboard.getCurrentPlayerColor(), skippedPawnPosition);
        assert (chessboard.getPiece(getFrom())).getName().equals(PieceNames.PAWN);
        PlayerColor currentPlayerColor = chessboard.getCurrentPlayerColor();
        if (currentPlayerColor.equals(PlayerColor.WHITE)) {
            assert chessboard.isEmpty(twoWhiteStepsForward) : "The second square in front of a white pawn must be empty.";
            new StandardMove(getFrom(), twoWhiteStepsForward).execute(chessboard);
        } else {
            assert chessboard.isEmpty(twoBlackStepsForward) : "The second square in front of a black pawn must be empty.";
            new StandardMove(getFrom(), twoBlackStepsForward).execute(chessboard);
        }
        return true;
    }

    @Override
    public void undo (@NonNull Board chessboard) {
        Pawn pawn = (Pawn) chessboard.getPiece(getTo());
        chessboard.setPiece(pawn, getFrom());
        chessboard.setEmpty(getTo());
        chessboard.setSkippedPawnPosition(chessboard.getCurrentPlayerColor(), skippedPawnPosition);
    }
}