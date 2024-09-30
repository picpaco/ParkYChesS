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
        ArrayList<ArrayList<Position>> existingDiagonals = chessboard.getPotentialBishopDestinations(from);
        for (ArrayList<Position> diagonalPositions : existingDiagonals) {
            bishopPseudoLegalMoves.addAll(pseudoLegalMovesOnDiagonal(diagonalPositions, from, chessboard));
        }
     //   System.out.println(chessboard);
     //   System.out.println("bishopPseudoLegalMoves: " + bishopPseudoLegalMoves);
        return bishopPseudoLegalMoves;
          //    LOGGER.trace("Exiting getPseudoLegalMoves(Position from = {}, Board chessboard). Moves found: {}", from, moves);
    }

    private Collection<Move> pseudoLegalMovesOnDiagonal (Collection<Position> diagonalSquares, Position from, Board chessboard){
        Collection<Move> bishopPseudoLegalMoves = new ArrayList<>();
            for (Position position : diagonalSquares) {
                if (chessboard.isEmpty(position)){
                    bishopPseudoLegalMoves.add(new StandardMove(from, position));
                } else {
                    Piece pieceFound = chessboard.getPiece(position);
                    if (!(pieceFound.getColor().equals(chessboard.getPiece(from).getColor()))) {
                        bishopPseudoLegalMoves.add(new StandardMove(from, position));
                        return bishopPseudoLegalMoves;
                    }
                    return bishopPseudoLegalMoves;
                }
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