package com.marcopiccionitraining.parkychess.model.moves;

import com.marcopiccionitraining.parkychess.model.Board;
import com.marcopiccionitraining.parkychess.model.pieces.PieceName;
import com.marcopiccionitraining.parkychess.model.Position;
import com.marcopiccionitraining.parkychess.model.pieces.Piece;
import lombok.Getter;

@Getter
public class StandardMove extends Move {

    private Piece capturedPiece;
   // private final Logger LOGGER = LoggerFactory.getLogger(StandardMove.class);

    public StandardMove(Position from, Position to){
        setFrom(from);
        setTo(to);
        setName(MoveNames.STANDARD);
    }

    @Override
    public boolean execute(Board chessBoard){
     //   LOGGER.trace("Entering Standard move.execute");
     //   LOGGER.trace("Executing standard move from: {} to: {}", getFrom(), getTo());
        Piece piece = chessBoard.getPiece(getFrom());
     //   LOGGER.trace("Piece to move: {}", piece);
        boolean isCapture = !(chessBoard.isEmpty(getTo()));
     //   LOGGER.trace ("isCapture? {}", isCapture);
        if (isCapture) {
            capturedPiece = chessBoard.getPiece(getTo());
        }
        chessBoard.setPiece(piece, getTo());
      //  LOGGER.trace("About to set square {} to empty", getFrom());
        chessBoard.setEmpty(getFrom());
        assert chessBoard.isEmpty(getFrom()) : "Position " + getFrom() + " should be empty.";
        piece.setHasMoved(true);
      //  LOGGER.trace(chessBoard.toString());
        return isCapture || (piece.getName().equals(PieceName.PAWN));
    }

    @Override
    public void undo(Board chessboard) {
        Piece piece = chessboard.getPiece(getTo());
        chessboard.setPiece(piece, getFrom());
        if (capturedPiece != null){
            chessboard.setPiece(capturedPiece, getTo());
        } else {
            chessboard.setEmpty(getTo());
        }
    }
}
