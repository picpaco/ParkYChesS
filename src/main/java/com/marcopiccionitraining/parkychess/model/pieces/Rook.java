package com.marcopiccionitraining.parkychess.model.pieces;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.Move;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class Rook extends SliderPiece {
    public Rook(PlayerColor playerColor) {
        super(playerColor);
        setName(PieceNames.ROOK);
    }

    @Override
    public Piece copy() {
        Rook rookCopy = new Rook(getColor());
        rookCopy.setPosition(getPosition());
        rookCopy.hasMoved = this.hasMoved;
        rookCopy.hasMovedForGood = this.hasMovedForGood;
 //       rookCopy.currentDepth = this.currentDepth;
        return rookCopy;
    }
    public Collection<Move> getPseudoLegalMoves(Position from , Board chessboard){
            Collection<Move> rookPseudoLegalMoves = new ArrayList<>();
            HashSet<ArrayList<Position>> existingFilesDestinations = chessboard.getPotentialRookDestinations(from);
            for (ArrayList<Position> filePositions : existingFilesDestinations) {
                rookPseudoLegalMoves.addAll(pseudoLegalMovesOnDirection(filePositions, from, chessboard));
            }
            return rookPseudoLegalMoves;
            //    LOGGER.trace("Exiting getPseudoLegalMoves(Position from = {}, Board chessboard). Moves found: {}", from, moves);
        }

}
