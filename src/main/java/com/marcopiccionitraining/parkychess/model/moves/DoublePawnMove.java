package com.marcopiccionitraining.parkychess.model.moves;

import com.marcopiccionitraining.parkychess.model.Board;
import com.marcopiccionitraining.parkychess.model.PlayerColor;
import com.marcopiccionitraining.parkychess.model.Position;
import com.marcopiccionitraining.parkychess.model.pieces.Piece;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DoublePawnMove extends Move {

    private final Position enPassantCapturePosition;
 //   private static final Logger LOGGER = LoggerFactory.getLogger(Move.class);

    public DoublePawnMove(Position from, Position to){
        assert from.column() == to.column():"The start and destination square must lie on the same column.";
        assert (from.row() == (to.row() + 2)) || (from.row() == (to.row() - 2)):
                "The start and destination square must lie on rows that are at a distance of plus or minus two from each other.";
        setFrom(from);
        setTo(to);
        setName(MoveNames.DOUBLE_PAWN);
        enPassantCapturePosition = new Position ((from.row() + to.row()) / 2, from.column());
    }
    @Override
    public boolean execute(Board chessboard){
     //   LOGGER.trace(chessboard.toString());
        PlayerColor currentPawnColor = chessboard.getPiece(getFrom()).getColor();
        chessboard.setEnPassantCapturePositionForColor(currentPawnColor, enPassantCapturePosition);
        new StandardMove(getFrom(), getTo()).execute(chessboard);
        return true;
    }

    @Override
    public void undo(Board chessboard) {
        Piece piece = chessboard.getPiece(getTo());
        chessboard.setPiece(piece, getFrom());
        chessboard.setEmpty(getTo());
        PlayerColor currentPawnColor = chessboard.getPiece(getFrom()).getColor();
        chessboard.setEnPassantCapturePositionForColor(currentPawnColor, null);
    }
}
