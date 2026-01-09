package com.marcopiccionitraining.parkychess.model.moves;

import com.marcopiccionitraining.parkychess.model.Board;
import com.marcopiccionitraining.parkychess.model.FENStateString;
import com.marcopiccionitraining.parkychess.model.PlayerColor;
import com.marcopiccionitraining.parkychess.model.pieces.King;
import com.marcopiccionitraining.parkychess.model.pieces.PieceNames;
import com.marcopiccionitraining.parkychess.model.Position;
import com.marcopiccionitraining.parkychess.model.pieces.Piece;
import com.marcopiccionitraining.parkychess.model.pieces.Rook;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
    @Getter
    public class StandardMove extends Move {

    private Piece capturedPiece;

    public StandardMove(@NonNull Position from, @NonNull Position to) {
        setFrom(from);
        setTo(to);
        setName(MoveNames.STANDARD);
    }
    @Override
    public boolean isLegal(@NonNull Board chessboard) {
        assert chessboard.getPiece(getFrom()) != null : "There must be a piece in " + getFrom();
        log.trace("Executing StandardMove isLegal()");
        Position maybePawnSkippedPosition = chessboard.getPawnSkippedPosition(chessboard.getCurrentPlayerColor());
        log.trace("While checking if standard move {} is legal, I am setting pawnSkipped Position to {}.",
                this, maybePawnSkippedPosition);
        chessboard.setPawnSkippedPosition(chessboard.getCurrentPlayerColor(), maybePawnSkippedPosition);
        execute(chessboard);
        PlayerColor playerColorOfPieceMoved = chessboard.getPiece(getTo()).copy().getColor();
        boolean wasKingCaptured = (capturedPiece != null) && (capturedPiece.getName().equals(PieceNames.KING)) &&
                capturedPiece.getColor().equals(PlayerColor.getOpponentColor(playerColorOfPieceMoved));
        return !(chessboard.isPlayerInCheck(playerColorOfPieceMoved)) && !(wasKingCaptured);
    }

    @Override
    public boolean execute(@NonNull Board chessboard) {
        log.debug("Executing standard move {}{}", getFrom(), getTo());
        assert chessboard.getPiece(getFrom()) != null : "There must be a piece in " + getFrom();
        Piece piece = chessboard.getPiece(getFrom());
        log.trace("Piece to move: {}", piece);
        boolean isStandardNoEnPassantCapture = !(chessboard.isEmpty(getTo()));
        //FIXME? It does not consider an en passant capture as a capture because the 'to' square
        // is empty.  Maybe it is better to process everything in En Passant move?
        if (isStandardNoEnPassantCapture) {
            capturedPiece = chessboard.getPiece(getTo());
        }
        if (piece.getName().equals(PieceNames.KING)) {
            King king = (King) piece;
            king.setFlaggedAsHavingAlreadyMoved(true);
        }
        if (piece.getName().equals(PieceNames.ROOK)) {
            Rook rook = (Rook) piece;
            if (rook.getColor().equals(PlayerColor.BLACK)){
                if (rook.isBlackKingsideRook()) {
                    rook.setBlackKingsideRookFlaggedAsHavingAlreadyMoved(true);
                }
                if (rook.isBlackQueensideRook()) {
                    rook.setBlackQueensideRookFlaggedAsHavingAlreadyMoved(true);
                }
            } else {
                if (rook.isWhiteKingsideRook()) {
                    rook.setWhiteKingsideRookFlaggedAsHavingAlreadyMoved(true);
                }
                if (rook.isWhiteQueensideRook()) {
                    rook.setWhiteQueensideRookFlaggedAsHavingAlreadyMoved(true);
                }
            }
        }
        chessboard.setPiece(piece, getTo());
        assert !(chessboard.isEmpty(getTo())) : "The captured piece must exist at position " + getTo();
        chessboard.setEmpty(getFrom());
        assert chessboard.isEmpty(getFrom()) : "Position " + getFrom() + " should be empty.";
        log.debug("Chessboard state after executing {}: {}", this, chessboard);
        return isStandardNoEnPassantCapture || (piece.getName().equals(PieceNames.PAWN));
    }

    @Override
    public void undo(@NonNull Board chessboard, @NonNull FENStateString oldGameState) {
        log.debug(" Last executed move: {}",oldGameState.getLastExecutedMove());
        Piece piece = chessboard.getPiece(oldGameState.getLastExecutedMove().getTo());
        log.debug("Piece: {}", piece);
        if (piece.getName().equals(PieceNames.KING)) {
            King king = (King) piece;
            if (king.getColor() == PlayerColor.BLACK) {
                king.setFlaggedAsHavingAlreadyMoved(oldGameState.isBlackKingFlaggedAsHavingAlreadyMoved());
            } else {
                king.setFlaggedAsHavingAlreadyMoved(oldGameState.isWhiteKingFlaggedAsHavingAlreadyMoved());
            }
            log.trace("After setting back the original value, king.isFlaggedAsHavingAlreadyMoved(): {}", king.isFlaggedAsHavingAlreadyMoved());
        }
        if (piece.getName().equals(PieceNames.ROOK)) {
            Rook rook = (Rook) piece;
            if (rook.getColor() == PlayerColor.BLACK) {
                if (rook.isBlackKingsideRook()){
                    rook.setFlaggedAsHavingAlreadyMoved(oldGameState.isBlackKingsideRookFlaggedAsHavingAlreadyMoved());
                }
                if(rook.isBlackQueensideRook()){
                    rook.setFlaggedAsHavingAlreadyMoved(oldGameState.isBlackQueensideRookFlaggedAsHavingAlreadyMoved());
                }
            } else {
                if (rook.isWhiteKingsideRook()) {
                    rook.setFlaggedAsHavingAlreadyMoved(oldGameState.isWhiteKingsideRookFlaggedAsHavingAlreadyMoved());
                }
                if (rook.isWhiteQueensideRook()) {
                    rook.setFlaggedAsHavingAlreadyMoved(oldGameState.isWhiteQueensideRookFlaggedAsHavingAlreadyMoved());
                }
            }
        }
        chessboard.setPiece(piece, oldGameState.getLastExecutedMove().getFrom());
        log.debug("Just set {} back on {}.", piece, oldGameState.getLastExecutedMove().getFrom());
        if (capturedPiece != null) {
            chessboard.setPiece(capturedPiece, oldGameState.getLastExecutedMove().getTo());
            log.debug("Just set captured piece {} back on {}.", capturedPiece, oldGameState.getLastExecutedMove().getTo());
        } else {
            chessboard.setEmpty(chessboard.getLastExecutedMove().getTo());
            log.debug("Just set {} to empty.", chessboard.getLastExecutedMove().getTo());
        }
    }
}