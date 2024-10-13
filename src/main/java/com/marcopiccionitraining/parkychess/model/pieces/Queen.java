package com.marcopiccionitraining.parkychess.model.pieces;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import com.marcopiccionitraining.parkychess.model.moves.StandardMove;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class Queen extends SliderPiece {

    public Queen(PlayerColor playerColor) {
        super(playerColor);
        setName(PieceName.QUEEN);
    }

    public Collection<Move> getPseudoLegalMoves(Position from , Board chessboard){
        Collection<Move> queenPseudoLegalMoves = new ArrayList<>();
        HashSet<ArrayList<Position>> existingFilesDestinations = chessboard.getPotentialQueenDestinations(from);
        for (ArrayList<Position> filePositions : existingFilesDestinations) {
            queenPseudoLegalMoves.addAll(pseudoLegalMovesOnDirection(filePositions, from, chessboard));
        }
        return queenPseudoLegalMoves;
        //    LOGGER.trace("Exiting getPseudoLegalMoves(Position from = {}, Board chessboard). Moves found: {}", from, moves);
    }

    @Override
    public Piece copy() {
        Queen queenCopy = new Queen(getColor());
        queenCopy.setHasMoved(hasMoved());
        return queenCopy;
    }
}
