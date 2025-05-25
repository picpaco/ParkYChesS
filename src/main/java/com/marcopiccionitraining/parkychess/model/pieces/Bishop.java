package com.marcopiccionitraining.parkychess.model.pieces;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.Move;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class Bishop extends SliderPiece {
    public Bishop(PlayerColor playerColor){
        super(playerColor);
        setName(PieceNames.BISHOP);
    }

  //  private final Logger LOGGER = LoggerFactory.getLogger(Bishop.class);

    public Collection<Move> getPseudoLegalMoves(Position from , Board chessboard){
        Collection<Move> bishopPseudoLegalMoves = new ArrayList<>();
        HashSet<ArrayList<Position>> existingDiagonals = chessboard.getPotentialBishopDestinations(from);
        for (ArrayList<Position> diagonalPositions : existingDiagonals) {
            bishopPseudoLegalMoves.addAll(pseudoLegalMovesOnDirection(diagonalPositions, from, chessboard));
        }
        return bishopPseudoLegalMoves;
          //    LOGGER.trace("Exiting getPseudoLegalMoves(Position from = {}, Board chessboard). Moves found: {}", from, moves);
    }

    @Override
    public Piece copy() {
        Bishop bishopCopy = new Bishop(getColor());
        bishopCopy.setPosition(getPosition());
        bishopCopy.hasMoved = hasMoved;
        bishopCopy.hasMovedForGood = this.hasMovedForGood;
   //     bishopCopy.currentDepth = this.currentDepth;
        return bishopCopy;
    }
}