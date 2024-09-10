package com.marcopiccionitraining.parkychess.model.moves;

import com.marcopiccionitraining.parkychess.model.Board;
import com.marcopiccionitraining.parkychess.model.PlayerColor;
import com.marcopiccionitraining.parkychess.model.Position;
import com.marcopiccionitraining.parkychess.model.pieces.Pawn;
import com.marcopiccionitraining.parkychess.model.pieces.Piece;

public class EnPassantMove extends Move{

    private final Position capturedPawnPosition;

    public EnPassantMove(Position from, Position to){
        setFrom(from);
        setTo(to);
        setName(MoveNames.EN_PASSANT_CAPTURE);
        capturedPawnPosition = new Position(getFrom().row(), getTo().column());
    }

    @Override
    public boolean execute (Board chessboard) {
        new StandardMove(getFrom(), getTo()).execute(chessboard);
        chessboard.setEmpty(capturedPawnPosition);
        return true;
    }

    @Override
    public void undo (Board chessboard) {
        Piece piece = chessboard.getPiece(getTo());
        chessboard.setPiece(piece, getFrom());
        chessboard.setEmpty(getTo());
        chessboard.setPiece(new Pawn(PlayerColor.getOpponentColor(piece.getColor())), capturedPawnPosition);
    }
}
