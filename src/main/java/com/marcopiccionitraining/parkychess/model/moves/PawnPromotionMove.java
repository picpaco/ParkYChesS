package com.marcopiccionitraining.parkychess.model.moves;

import com.marcopiccionitraining.parkychess.model.Board;
import com.marcopiccionitraining.parkychess.model.PlayerColor;
import com.marcopiccionitraining.parkychess.model.Position;
import com.marcopiccionitraining.parkychess.model.pieces.*;

public class PawnPromotionMove extends Move {

    private final Piece promotionPiece;
    private Piece capturedPiece;
//    private final Logger LOGGER = LoggerFactory.getLogger(PawnPromotionMove.class);

    public PawnPromotionMove (Position from, Position to, Piece promotionPiece){
        setFrom(from);
        setTo(to);
        setName(MoveNames.PAWN_PROMOTION);
        this.promotionPiece = promotionPiece;
    }

    private Piece createPromotionPiece(PlayerColor playerColor) {
        return switch (promotionPiece.getName()) {
            case QUEEN -> new Queen(playerColor);
            case KNIGHT -> new Knight(playerColor);
            case ROOK -> new Rook(playerColor);
            case BISHOP -> new Bishop(playerColor);
            default -> throw new RuntimeException("Unexpected promotion piece string representation");
        };
    }

    @Override
    public boolean execute(Board chessboard) {
        Piece pawn = chessboard.getPiece(getFrom());
        chessboard.setEmpty(getFrom());
        Piece promotionPiece = createPromotionPiece(pawn.getColor());
        promotionPiece.setHasMoved(true);
     //   promotionPiece.setHasMovedForGood(true);
        boolean isCapture = !(chessboard.isEmpty(getTo()));
    //    LOGGER.trace ("isCapture? {}", isCapture);
        if (isCapture) {
            capturedPiece = chessboard.getPiece(getTo());
        //    capturedPiece.setHasMoved(true);
        }
        chessboard.setPiece(promotionPiece, getTo());
        return true;
    }

    @Override
    public void undo(Board chessboard) {
        chessboard.setPiece(new Pawn(chessboard.getPiece(getTo()).getColor()), getFrom());
        if (capturedPiece != null){
            chessboard.setPiece(capturedPiece, getTo());
        } else {
            chessboard.setEmpty(getTo());
        }
    }
}