package com.marcopiccionitraining.parkychess.model.moves;

import com.marcopiccionitraining.parkychess.model.Board;
import com.marcopiccionitraining.parkychess.model.LocalGameState;
import com.marcopiccionitraining.parkychess.model.Position;
import com.marcopiccionitraining.parkychess.model.pieces.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PawnPromotionMove extends Move {
    @Getter
    private Piece pawnToPromote;
    @Getter
    private final Piece promotionPiece;
    @Getter
    private Piece capturedPiece = null;

    //TODO Try varargs here:
    public PawnPromotionMove (@NonNull Position from, @NonNull Position to,
                              @NonNull Piece promotionPiece, Piece capturedPiece){
        setFrom(from);
        setTo(to);
        setName(MoveNames.PAWN_PROMOTION);
        this.promotionPiece = promotionPiece;
        if (capturedPiece != null) {
            this.capturedPiece = capturedPiece;
        }
    }

    @Override
    public boolean execute (Board chessboard){//, LocalGameState localGameState) {
        assert chessboard.getPiece(getFrom()).getName().equals(PieceNames.PAWN);
        pawnToPromote = chessboard.getPiece(getFrom());
        if (!chessboard.isEmpty(getTo())) {
            capturedPiece = chessboard.getPiece(getTo());
            log.info("I have just saved the captured piece {}.", capturedPiece);
        }
        promotionPiece.setFlaggedAsHavingAlreadyMoved(true);
        chessboard.setPiece(promotionPiece, getTo());
        chessboard.setEmpty(getFrom().getRow(), getFrom().getColumn());
        log.error("Chessboard after executing {}: {}.", this, chessboard);
        return true;
    }

    @Override
        public void undo (@NonNull Board chessboard, LocalGameState localGameState) {
            assert chessboard.isEmpty(getFrom()) : "The 'from' position "  + getFrom() + "should be empty.";
            assert !(chessboard.isEmpty(getTo())) : "On the 'to' position "  + getTo() + "there should be a piece.";
            chessboard.setPiece(pawnToPromote, getFrom());
            log.info("I have put back the promoting pawn on {}", getFrom());
            if (capturedPiece != null) {
                chessboard.setPiece(capturedPiece, getTo());
                log.info("Previously captured pawn {} put on {}", capturedPiece, getTo());
            } else {
                chessboard.setEmpty(getTo().getRow(), getTo().getColumn());
                log.info("There is no piece now on {}", getTo());
            }
            log.info("I have just undone move {}. Board situation is {}", this, chessboard);
            }
}