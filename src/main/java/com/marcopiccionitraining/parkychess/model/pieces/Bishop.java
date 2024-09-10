package com.marcopiccionitraining.parkychess.model.pieces;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import com.marcopiccionitraining.parkychess.model.moves.StandardMove;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;

public class Bishop extends Piece {
    public Bishop(PlayerColor playerColor){
        super(playerColor);
        setName(PieceName.BISHOP);
    }

    private final Direction[] allowedDirections = new Direction[]{
            Direction.NORTH_EAST, Direction.NORTH_WEST, Direction.SOUTH_EAST, Direction.SOUTH_WEST
    };
  //  private final Logger LOGGER = LoggerFactory.getLogger(Bishop.class);

    public Collection<Move> getPseudoLegalMoves(Position from , Board chessboard){
        Collection<Move> bishopPseudoLegalMoves = new ArrayList<>();
        Collection<Position> allowedTiles = positionsInDirections(from, allowedDirections, chessboard);
        for (Position pos : allowedTiles) {
            StandardMove move = new StandardMove(from, pos);
            bishopPseudoLegalMoves.add(move);
        }
        return bishopPseudoLegalMoves;
    }
    @Override
    public Piece copy() {
        Bishop bishopCopy = new Bishop(getColor());
        bishopCopy.setHasMoved(hasMoved());
        return bishopCopy;
    }
}