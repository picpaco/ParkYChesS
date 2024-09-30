package com.marcopiccionitraining.parkychess.model.pieces;

import com.marcopiccionitraining.parkychess.ObjectFactory;
import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import com.marcopiccionitraining.parkychess.model.moves.StandardMove;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.*;

public class Knight extends Piece {
    private final Logger LOGGER = LoggerFactory.getLogger(Knight.class);

    public Knight(PlayerColor playerColor){
        super(playerColor);
        setName(PieceName.KNIGHT);
    }

   // private Collection<Position> getLegalDestinationPositions(Position from, Board chessboard){
    //    LOGGER.trace("Entering getLegalDestinationPositions, from = {} chessboard)", from);
 //       Collection<Position> tiles = new ArrayList<>();
 //       for (Position position : chessboard.getPotentialKnightDestinations(from)) {
 //           if (chessboard.isEmpty(position) || !(chessboard.getPiece(position).getColor().equals(getColor()))){
 //               tiles.add(position);
//            }
  //      }
    //    LOGGER.trace("Exiting getLegalDestinationPositions(from, chessboard). Tiles: {}", tiles);
    //    return tiles;
   // }
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