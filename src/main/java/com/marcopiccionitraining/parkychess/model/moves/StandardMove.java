package com.marcopiccionitraining.parkychess.model.moves;

import com.marcopiccionitraining.parkychess.model.Board;
import com.marcopiccionitraining.parkychess.model.LocalGameState;
import com.marcopiccionitraining.parkychess.model.PlayerColor;
import com.marcopiccionitraining.parkychess.model.pieces.PieceNames;
import com.marcopiccionitraining.parkychess.model.Position;
import com.marcopiccionitraining.parkychess.model.pieces.Piece;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
    @Getter
    public class StandardMove extends Move {
    /**
     * Represents the status of the piece which is about to make this Standard move.
     * As a default is false, but can be set to true in case of input of the position in FEN.
     */
    @Getter @Setter
    private boolean isPieceFlaggedAsHavingAlreadyMoved;

    public StandardMove(@NonNull Position from, @NonNull Position to) {
        setFrom(from);
        setTo(to);
        setName(MoveNames.STANDARD);
    }

    @Override
    public boolean execute (@NonNull Board chessboard){//}, LocalGameState localGameState) {
        log.debug("Executing standard move {}{}", getFrom(), getTo());
        assert !(chessboard.isEmpty(getFrom())) : "There must be a piece on " + getFrom();
        boolean isCaptureOrPawnMove = false;
        Piece piece = chessboard.getPiece(getFrom());
        log.debug("Piece to move: {}", piece);
        isPieceFlaggedAsHavingAlreadyMoved = piece.isFlaggedAsHavingAlreadyMoved();
        piece.setFlaggedAsHavingAlreadyMoved(true);
        if (!chessboard.isEmpty(getTo()) &&
                !(chessboard.getPiece(getTo()).getColor().equals(chessboard.getPiece(getFrom()).getColor()))) {
            setCapturedPiece(chessboard.getPiece(getTo()));
            log.debug("Captured piece just set: {}", getCapturedPiece());
            isCaptureOrPawnMove = true;
        }
        chessboard.setPiece(piece, getTo());
        assert !(chessboard.isEmpty(getTo())) : "The capturing piece must exist at position " + getTo();
        chessboard.setEmpty(getFrom().getRow(), getFrom().getColumn());
        assert chessboard.isEmpty(getFrom().getRow(), getFrom().getColumn()) : "Position " + getFrom() + " should be empty.";
        if (piece.getName().equals(PieceNames.PAWN)) {
            isCaptureOrPawnMove = true;
        }
        log.debug("Chessboard state after executing {}: {}", this, chessboard);
        return isCaptureOrPawnMove;
    }

    @Override
    public void undo (@NonNull Board chessboard, LocalGameState localGameState) {
        Piece piece = chessboard.getPiece(getTo());
        //FIXME; the following will not work at deeper levels of depth?
        //FIXME: this matters for king and rooks, because affects their ability to castle.
        //FIXME: scenario 1: start: castle possible; king moves, then goes back ---> cannot castle anymore.
        //FIXME: scenario 2: start: castle possible; a-rook moves, then goes back ---> cannot castle anymore.
        //FIXME: scenario 2: start: castle possible; h-rook moves, then goes back ---> cannot castle anymore.
        //FIXME:
        //FIXME: Solution: the state, in this case a boolean 'isFlaggedAsHavingAlreadyMoved', should be
        //FIXME: saved in the move itself (StandardMove or CastlingMove, same for rooks). Then when
        //FIXME: undoing the move, revert its value to whatever it was when the move was executed.
        //FIXME:
        //FIXME:
        piece.setFlaggedAsHavingAlreadyMoved(isPieceFlaggedAsHavingAlreadyMoved);
        chessboard.setPiece(piece, getFrom());
        log.trace("Just set {} back on {}.", piece, getFrom());
        if (getCapturedPiece() != null) {
            chessboard.setPiece(getCapturedPiece(), getTo());
            log.trace("Just set captured piece {} back on {}.", getCapturedPiece(), getTo());
        } else {
            chessboard.setEmpty(getTo().getRow(), getTo().getColumn());
            log.trace("Just set {} to empty.", getTo());
        }
    }
}