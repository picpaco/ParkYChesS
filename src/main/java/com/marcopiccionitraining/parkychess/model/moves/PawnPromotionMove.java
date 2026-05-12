package com.marcopiccionitraining.parkychess.model.moves;

import com.marcopiccionitraining.parkychess.model.Board;
import com.marcopiccionitraining.parkychess.model.PlayerColor;
import com.marcopiccionitraining.parkychess.model.Position;
import com.marcopiccionitraining.parkychess.model.pieces.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import static com.marcopiccionitraining.parkychess.model.pieces.PieceNames.*;

@Slf4j
public class PawnPromotionMove extends Move {

    @Getter
    private final Piece promotionPiece;

    @Getter
    private Piece capturedPiece = null;

    //TODO Try varargs here below.
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

    private Piece getPromotionPiece (PieceNames pieceName, PlayerColor playerColor, Board chessboard) {
        return switch (pieceName) {
            case QUEEN -> new Queen(playerColor);
            case KNIGHT -> new Knight(playerColor);
            case ROOK -> new Rook(playerColor);
            case BISHOP -> new Bishop(playerColor);
            case PAWN -> new Pawn(playerColor);
            default -> throw new RuntimeException("Unexpected piece string representation");
        };
    }

    @Override
    public boolean execute (Board chessboard) {
        assert chessboard.getPiece(getFrom()).getName().equals(PieceNames.PAWN);
        chessboard.setEmpty(getFrom());
        if (!chessboard.isEmpty(getTo())) {
            capturedPiece = chessboard.getPiece(getTo());
            log.info("I have just saved the captured piece {}.", capturedPiece);
            promotionPiece.setFlaggedAsHavingAlreadyMoved(true);
            chessboard.setPiece(promotionPiece, getTo());
        } else {
            promotionPiece.setFlaggedAsHavingAlreadyMoved(true);
            chessboard.setPiece(promotionPiece, getTo());
        }
        log.info("Chessboard after executing {}: {}.", this, chessboard);
        return true;
    }

    @Override
        public void undo (@NonNull Board chessboard) {
            assert chessboard.isEmpty(getFrom()) : "The 'from' position "  + getFrom() + "should be empty.";
            assert !(chessboard.isEmpty(getTo())) : "On the 'to' position "  + getTo() + "there should be a piece.";
            Pawn oldNewPawn = (Pawn) getPromotionPiece(PieceNames.PAWN, chessboard.getCurrentPlayerColor(), chessboard);
            chessboard.setPiece(oldNewPawn, getFrom());
            log.info("I have put back the promoting pawn on {}", getFrom());
            if (capturedPiece != null) {
                chessboard.setPiece(capturedPiece, getTo());
                log.info("Previously captured {} put on {}", promotionPiece, getTo());
            } else {
                chessboard.setEmpty(getTo());
                log.info("There is no piece now on {}", getTo());
            }
            log.info("I have just undone move {}. Board situation is {}", this, chessboard);
            }
}