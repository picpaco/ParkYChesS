package com.marcopiccionitraining.parkychess.model.pieces;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import com.marcopiccionitraining.parkychess.model.moves.StandardMove;

import java.util.ArrayList;
import java.util.Collection;

public class Queen extends Piece {
    public final Direction[] allowedDirections = new Direction[]{
            Direction.NORTH, Direction.WEST, Direction.SOUTH, Direction.EAST,
            Direction.NORTH_EAST, Direction.NORTH_WEST, Direction.SOUTH_EAST, Direction.SOUTH_WEST
    };
    public Queen(PlayerColor playerColor) {
        super(playerColor);
        setName(PieceName.QUEEN);
    }

    public Collection<Move> getPseudoLegalMoves(Position from , Board chessboard){
        Collection<Move> queenPseudoLegalMoves = new ArrayList<>();
        Collection<Position> allowedTiles = positionsInDirections(from, allowedDirections, chessboard);
        for (Position pos : allowedTiles) {
            StandardMove move = new StandardMove(from, pos);
            queenPseudoLegalMoves.add(move);
        }
        return queenPseudoLegalMoves;
    }
    @Override
    public Piece copy() {
        Queen queenCopy = new Queen(getColor());
        queenCopy.setHasMoved(hasMoved());
        return queenCopy;
    }
}
