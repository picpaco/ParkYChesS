package com.marcopiccionitraining.parkychess.model.moves;

import com.marcopiccionitraining.parkychess.model.Board;
import com.marcopiccionitraining.parkychess.model.LocalGameState;
import com.marcopiccionitraining.parkychess.model.PlayerColor;
import com.marcopiccionitraining.parkychess.model.pieces.PieceNames;
import com.marcopiccionitraining.parkychess.model.Position;
import com.marcopiccionitraining.parkychess.model.pieces.Piece;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
    @Getter
    public class StandardMove extends Move {
    /**
     * Represents the status of the piece which is about to make this Standard move.
     * As a default is false, but can be set to true in case of input of the position in FEN.
     */
    public StandardMove(@NonNull Position from, @NonNull Position to) {
        setFrom(from);
        setTo(to);
        setName(MoveNames.STANDARD);
    }

    @Override
    public boolean execute (@NonNull Board chessboard) {
        log.error("Executing standard move {}{}.", getFrom(), getTo());
        assert !(chessboard.isEmpty(getFrom())) : "There must be a piece on " + getFrom();
        boolean isCaptureOrPawnMove = false;
        Piece pieceToMove = chessboard.getPiece(getFrom());
        pieceToMove.setFlaggedAsHavingAlreadyMoved(true);
        if (!chessboard.isEmpty(getTo())) {
            if (!(chessboard.getPiece(getTo()).getColor().equals(chessboard.getPiece(getFrom()).getColor()))) {
                log.debug("Saving captured piece: {}", chessboard.getPiece(getTo()));
                setCapturedPiece(chessboard.getPiece(getTo()));
                isCaptureOrPawnMove = true;
            } else {
                log.error("The piece on {} is a friendly piece. I expected an enemy piece.", getTo());
                assert false : "The piece on " + getTo() + " should be an enemy piece.";
            }
        }
        chessboard.setPiece(pieceToMove, getTo());
        assert !(chessboard.isEmpty(getTo())) : "The capturing piece must exist at position " + getTo();
        chessboard.setEmpty(getFrom());
        assert chessboard.isEmpty(getFrom()) : "Position " + getFrom() + " should be empty.";
        if (pieceToMove.getName().equals(PieceNames.KING)) {
            if (pieceToMove.getColor().equals(PlayerColor.WHITE)) {
                chessboard.setWhiteKingPosition(getTo());
                chessboard.setWhiteKingsideCastlingPossibleFromFEN(false);
                chessboard.setWhiteQueensideCastlingPossibleFromFEN(false);
            } else {
                chessboard.setBlackKingPosition(getTo());
                chessboard.setBlackKingsideCastlingPossibleFromFEN(false);
                chessboard.setBlackQueensideCastlingPossibleFromFEN(false);
            }
        }
        if (pieceToMove.getName().equals(PieceNames.ROOK)){
            if (pieceToMove.getColor().equals(PlayerColor.WHITE)){
                if (Objects.equals(chessboard.getPiece(7, 7), pieceToMove)) {
                    chessboard.setWhiteKingsideCastlingPossibleFromFEN(false);
                } else {
                    if (Objects.equals(chessboard.getPiece(7, 0), pieceToMove)) {
                        chessboard.setWhiteQueensideCastlingPossibleFromFEN(false);
                    }
                }
            } else {
                if (Objects.equals(chessboard.getPiece(0, 0), pieceToMove)) {
                    chessboard.setBlackKingsideCastlingPossibleFromFEN(false);
                } else {
                    if (Objects.equals(chessboard.getPiece(0, 7), pieceToMove)) {
                        chessboard.setBlackQueensideCastlingPossibleFromFEN(false);
                    }
                }
            }
        }
        if (pieceToMove.getName().equals(PieceNames.PAWN)) {
            isCaptureOrPawnMove = true;
        }
        log.debug("Chessboard after executing {}: {}", this, chessboard);
        log.debug ("Just executed {} {}.", getName(), this);
        log.debug("{} should be empty: {}. On {} there should be a {}.",
                getFrom(), chessboard.isEmpty(getFrom()), getTo(), chessboard.getPiece(getTo()));
        return isCaptureOrPawnMove;
    }

    @Override
    public void undo (@NonNull Board chessboard, LocalGameState localGameState) {
        log.error("Undoing standard move {}{}", getFrom(), getTo());
        assert !(chessboard.isEmpty(getTo())) : "The capturing piece must exist at 'to' position " + getTo();
        Piece piece = chessboard.getPiece(getTo());
        if (localGameState.getPieceMovedLast() != null) {
            piece.setFlaggedAsHavingAlreadyMoved(localGameState.getPieceMovedLast().isFlaggedAsHavingAlreadyMoved());
        } else {
            piece.setFlaggedAsHavingAlreadyMoved(false);
        }
        chessboard.setCurrentPlayerColor(localGameState.getCurrentPlayerColor());
        if (localGameState.getPawnSkippedPosition() != null) {
            chessboard.setPawnSkippedPosition(localGameState.getCurrentPlayerColor(),
                    localGameState.getPawnSkippedPosition());
        }
        chessboard.setPiece(piece, getFrom());
        if (piece.getName().equals(PieceNames.KING)) {
            if (piece.getColor().equals(PlayerColor.WHITE)) {
                chessboard.setWhiteKingPosition(getFrom());
                chessboard.setWhiteQueensideCastlingPossibleFromFEN(localGameState.isWhiteQueensideCastlingPossible());
                chessboard.setWhiteKingsideCastlingPossibleFromFEN(localGameState.isWhiteKingsideCastlingPossible());
            }  else  {
                chessboard.setBlackKingPosition(getFrom());
                chessboard.setBlackQueensideCastlingPossibleFromFEN(localGameState.isBlackQueensideCastlingPossible());
                chessboard.setBlackKingsideCastlingPossibleFromFEN(localGameState.isBlackKingsideCastlingPossible());
            }
        }
        log.debug ("Just set {} back on {}.", piece, getFrom());
        if (getCapturedPiece() != null) {
            chessboard.setPiece(getCapturedPiece(), getTo());
            log.debug("Just set captured piece {} back on {}.", localGameState.getPieceCapturedLast(), getTo());
        } else {
            chessboard.setEmpty(getTo());
            log.debug("Just set {} to empty.", getTo());
        }
    }
}