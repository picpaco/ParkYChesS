package com.marcopiccionitraining.parkychess.model.pieces;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.Move;

import java.util.ArrayList;
import java.util.Collection;

public class Queen extends SliderPiece {

    public Queen(PlayerColor playerColor) {
        super(playerColor);
        setName(PieceNames.QUEEN);
    }

    public Collection<Move> getPseudoLegalMoves(Position from , Board chessboard){
        Collection<Move> queenPseudoLegalMoves = new ArrayList<>();
        ArrayList<ArrayList<Position>> existingFilesDestinations =
                PseudoLegalPiecesPositionsInitializer.getQueenPseudoLegalPositions(from);
        for (ArrayList<Position> filePositions : existingFilesDestinations) {
            queenPseudoLegalMoves.addAll(getPseudoLegalMovesInDirection(filePositions, from, chessboard));
        }
        return queenPseudoLegalMoves;
        //    LOGGER.trace("Exiting getPseudoLegalMoves(Position from = {}, Board chessboard). Moves found: {}", from, moves);
    }

    @Override
    public Piece copy() {
        Queen queenCopy = new Queen(getColor());
        queenCopy.setPosition(new Position(getPosition().row(), getPosition().column()));
        queenCopy.setFlaggedAsHavingAlreadyMoved(isFlaggedAsHavingAlreadyMoved());
        return queenCopy;
    }
}
