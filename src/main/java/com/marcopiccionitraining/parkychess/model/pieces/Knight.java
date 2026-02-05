package com.marcopiccionitraining.parkychess.model.pieces;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import com.marcopiccionitraining.parkychess.model.moves.StandardMove;
import java.util.*;

public class Knight extends Piece {

    public Knight(PlayerColor playerColor){
        super(playerColor);
        setName(PieceNames.KNIGHT);
    }

    @Override
    public Collection<Move> getPseudoLegalMoves(Position from, Board chessboard){
        Collection<Move> moves = new ArrayList<>();
        for (Position to : PseudoLegalPiecesPositionsInitializer.getKnightPseudoLegalPositions(from)){
            if (chessboard.isEmpty(to) || !(chessboard.getPiece(to).getColor().equals(getColor()))){
                moves.add(new StandardMove(from, to));
            }
        }
        return moves;
    }

    @Override
    public Piece copy() {
        Knight knightCopy = new Knight(getColor());
        knightCopy.setPosition(new Position(getPosition().getRow(), getPosition().getColumn()));
        knightCopy.setFlaggedAsHavingAlreadyMoved(isFlaggedAsHavingAlreadyMoved());
        return knightCopy;
    }
}