package com.marcopiccionitraining.parkychess.model.moves;

import com.marcopiccionitraining.parkychess.model.Board;
import com.marcopiccionitraining.parkychess.model.FENStateString;
import com.marcopiccionitraining.parkychess.model.PlayerColor;
import com.marcopiccionitraining.parkychess.model.Position;
import com.marcopiccionitraining.parkychess.model.pieces.*;
import lombok.NonNull;

public class PawnPromotionMove extends Move {

    private final Piece promotionPiece;
    private Piece capturedPiece;
//    private final Logger LOGGER = LoggerFactory.getLogger(PawnPromotionMove.class);

    public PawnPromotionMove (Position from, Position to, Piece promotionPiece){
        assert from != null : "The from position must exist.";
        assert to != null : "The to position must exist.";
        assert promotionPiece != null : "The promotion piece must exist.";
        setFrom(from);
        setTo(to);
        setName(MoveNames.PAWN_PROMOTION);
        this.promotionPiece = promotionPiece;
    }

    private Piece createPromotionPiece (PlayerColor playerColor) {
        return switch (promotionPiece.getName()) {
            case QUEEN -> new Queen(playerColor);
            case KNIGHT -> new Knight(playerColor);
            case ROOK -> new Rook(playerColor); //TODO: ad info about having already moved?
            case BISHOP -> new Bishop(playerColor);
            default -> throw new RuntimeException("Unexpected promotion piece string representation");
        };
    }

    @Override
    public boolean execute (Board chessboard) {
        Piece pawn = chessboard.getPiece(getFrom());
        chessboard.setEmpty(getFrom());
        Piece promotionPiece = createPromotionPiece(pawn.getColor());
        promotionPiece.setFlaggedAsHavingAlreadyMoved(true);
        chessboard.setPiece(promotionPiece, getTo());
        return true;
    }

    @Override
        public void undo (@NonNull Board chessboard, @NonNull FENStateString oldGameState) {
            chessboard.setPiece(chessboard.getPiece(oldGameState.getLastExecutedMove().getTo()), getFrom());
            chessboard.setEmpty(oldGameState.getLastExecutedMove().getTo());
    }
}