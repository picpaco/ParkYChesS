package com.marcopiccionitraining.parkychess.model.pieces;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.Move;

import java.util.ArrayList;
import java.util.Collection;

public class Bishop extends SliderPiece {
    public Bishop(PlayerColor playerColor){
        super(playerColor);
        setName(PieceNames.BISHOP);
    }

    public Collection<Move> getPseudoLegalMoves(Position from , Board chessboard){
        Collection<Move> bishopPseudoLegalMoves = new ArrayList<>();
        ArrayList<ArrayList<Position>> existingDiagonals =
                PseudoLegalPiecesPositionsInitializer.getBishopPseudoLegalPoitions(from);
        for (ArrayList<Position> diagonalPositions : existingDiagonals) {
            bishopPseudoLegalMoves.addAll(getPseudoLegalMovesInDirection(diagonalPositions, from, chessboard));
        }
        return bishopPseudoLegalMoves;
    }

    @Override
    public Piece copy() {
        Bishop bishopCopy = new Bishop(getColor());
        bishopCopy.setPosition(new Position(getPosition().getRow(), getPosition().getColumn()));
        bishopCopy.setFlaggedAsHavingAlreadyMoved(isFlaggedAsHavingAlreadyMoved());
        return bishopCopy;
    }
}