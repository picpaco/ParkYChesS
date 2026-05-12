package com.marcopiccionitraining.parkychess.model.pieces;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class Rook extends SliderPiece {

    public Rook (PlayerColor playerColor) {
        super(playerColor);
        setName(PieceNames.ROOK);
    }

    @Override
    public List<Move> getPseudoLegalMoves (Position from, Board chessboard){
        List<Move> rookPseudoLegalMoves = new ArrayList<>();
        List<List<Position>> allRookFilesAndRanks =
                PseudoLegalPiecesPositionsInitializer.getRookPseudoLegalPositions(from);
        for (List<Position> positionsAlongOneDirection : allRookFilesAndRanks) {
            List<Move> pseudoLegalMovesInOneDirection = getPseudoLegalMovesInDirection(positionsAlongOneDirection,
                    from, chessboard);
            rookPseudoLegalMoves.addAll(pseudoLegalMovesInOneDirection);
        }
        log.debug("Pseudolegal moves for {} rook {} {}", getColor(), from, rookPseudoLegalMoves);
        return rookPseudoLegalMoves;
    }

    @Override
    public Piece copy() {
        Rook rookCopy = new Rook(getColor());
        rookCopy.setFlaggedAsHavingAlreadyMoved(isFlaggedAsHavingAlreadyMoved());
        return rookCopy;
    }
}