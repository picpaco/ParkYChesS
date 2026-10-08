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
     * then the first position in the double move is defined as pawn skipped position.
     */
    @Getter
    private Position pawnSkippedPosition;

    public DoublePawnMove (@NonNull Position from, @NonNull Position to, @NonNull Board chessboard) {
        log.debug("{} will move from: {}; to: {}", PlayerColor.getOpponentColor(chessboard.getCurrentPlayerColor()), from, to);
        log.debug("from is empty?: {}; to is empty?: {}", chessboard.isEmpty(from), chessboard.isEmpty(to));
        assert chessboard.getPiece(from).getName().equals(PieceNames.PAWN) : "The subject of a double move is a pawn.";
        assert (from.getRow() == 1 && chessboard.getPiece(from).getColor().equals(PlayerColor.BLACK)) ||
                (from.getRow() == 6 && chessboard.getPiece(from).getColor().equals(PlayerColor.WHITE)) :
                "The pawn executing a double move must be in its home square.";
        assert from.getColumn() == to.getColumn() : "The start and destination square must lie on the same column.";
        assert Math.abs (to.getRow() - from.getRow()) == 2 : "From and to positions should differ by 2 rows.";
        assert !chessboard.isEmpty(from) : "On " + from + " there should be a piece.";
        assert chessboard.getPiece(from).getName().equals(PieceNames.PAWN) : "The piece performing a double pawn move must be a pawn.";
        setName(MoveNames.DOUBLE_PAWN);
        setFrom(from);
        setTo(to);
        log.debug ("DoublePawnMove for {}  {}-{} (({},{}) - ({},{})) created.",
                chessboard.getPiece(from).getColor(), from, to, from.getRow(), from.getColumn(),
                to.getRow(), to.getColumn());
    }

    @Override
    public boolean execute (@NonNull Board chessboard){
        assert (chessboard.getPiece(getFrom())).getName().equals(PieceNames.PAWN) :
                "There must be a pawn on " + getFrom();
        Pawn pawn = (Pawn) chessboard.getPiece(getFrom());
        PlayerColor pawnColor = pawn.getColor();
        assert (pawnColor.equals (PlayerColor.BLACK) && pawn.isOnStartingSquare(getFrom(), PlayerColor.BLACK)) ||
                (pawnColor.equals (PlayerColor.WHITE) && pawn.isOnStartingSquare(getFrom(), PlayerColor.WHITE)) :
                "A pawn can execute a double move only from its starting square.";
        pawnSkippedPosition = new Position(((getFrom().getRow() + getTo().getRow()) / 2),
                getFrom().getColumn());
        if (pawnSkippedPosition.getRow() == 2) {
            chessboard.setPawnSkippedPosition(PlayerColor.BLACK, pawnSkippedPosition);
        } else {
            if (pawnSkippedPosition.getRow() == 5) {
                chessboard.setPawnSkippedPosition(PlayerColor.WHITE, pawnSkippedPosition);
            } else {
                assert false : "Skipped pawn position is wrong. Its position cannot be " + pawnSkippedPosition + ".";
            }
        }
        assert chessboard.isEmpty(pawnSkippedPosition) : "The skipped position must be empty.";
        Position twoStepsForward;
        if (pawnColor.equals (PlayerColor.BLACK)) {
            twoStepsForward = new Position(pawnSkippedPosition.getRow() + 1, pawnSkippedPosition.getColumn());
        } else {
            twoStepsForward = new Position(pawnSkippedPosition.getRow() - 1, pawnSkippedPosition.getColumn());
        }
        log.error("Executing double pawn move {}{}", getFrom(),twoStepsForward);
        assert getTo().equals(twoStepsForward) : "The positions 'to' and 'twoStepsForward' should be the same.";
        log.debug("From: {}, to: {}", getFrom(), twoStepsForward);
        new StandardMove(getFrom(), twoStepsForward).execute(chessboard);
        return true;
    }

    @Override
    public void undo (@NonNull Board chessboard, LocalGameState localGameState) {
        log.error("Undoing double pawn move {}{}", getFrom(),getTo());
        Pawn pawn = (Pawn) chessboard.getPiece(getTo());
        chessboard.setPiece(pawn, getFrom());
        chessboard.setEmpty(getTo());
        chessboard.setCurrentPlayerColor(localGameState.getCurrentPlayerColor());
        pawnSkippedPosition = localGameState.getPawnSkippedPosition();
        if (localGameState.getPawnSkippedPosition() != null) {
            chessboard.setPawnSkippedPosition(localGameState.getCurrentPlayerColor(), pawnSkippedPosition);
        }
        log.debug("Just undone double move {}.", this);
    }
}