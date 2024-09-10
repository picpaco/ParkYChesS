package com.marcopiccionitraining.parkychess.model.pieces;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import com.marcopiccionitraining.parkychess.model.moves.StandardMove;

import java.util.ArrayList;
import java.util.Collection;

public class Rook extends Piece {
    public Rook(PlayerColor playerColor) {
        super(playerColor);
        setName(PieceName.ROOK);
    }

    public final Direction[] allowedDirections = new Direction[]{
            Direction.NORTH, Direction.WEST, Direction.SOUTH, Direction.EAST
    };

    @Override
    public Piece copy() {
        Rook rookCopy = new Rook(getColor());
        rookCopy.setHasMoved(hasMoved());
        return rookCopy;
    }
    public Collection<Move> getPseudoLegalMoves(Position from , Board chessboard){
        Collection<Move> rookPseudoLegalMoves = new ArrayList<>();
        Collection<Position> allowedTiles = positionsInDirections(from, allowedDirections, chessboard);
        for (Position pos : allowedTiles) {
            StandardMove move = new StandardMove(from, pos);
            rookPseudoLegalMoves.add(move);
        }
        return rookPseudoLegalMoves;
    }
}
