package com.marcopiccionitraining.parkychess.model.pieces;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import com.marcopiccionitraining.parkychess.model.moves.StandardMove;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Knight extends Piece {
 //   private final Logger LOGGER = LoggerFactory.getLogger(Knight.class);

    public Knight(PlayerColor playerColor){
        super(playerColor);
        setName(PieceName.KNIGHT);
    }

    @Override
    public Collection<Move> getPseudoLegalMoves(Position from, Board chessboard){
    //    LOGGER.trace("Entering getPseudoLegalMoves(from = {}, chessboard)", from);
        Collection<Move> moves = new ArrayList<>();
        for (Position to : chessboard.getPotentialKnightDestinations(from)){
            if (chessboard.isEmpty(to) || !(chessboard.getPiece(to).getColor().equals(getColor()))){
                moves.add(new StandardMove(from, to));
            }
        }
    //    LOGGER.trace("Exiting getPseudoLegalMoves(Position from = {}, Board chessboard). Moves found: {}", from, moves);
        return moves;
    }
    @Override
    public Piece copy() {
        Knight knightCopy = new Knight(getColor());
        knightCopy.setHasMoved(hasMoved());
        return knightCopy;
    }
}