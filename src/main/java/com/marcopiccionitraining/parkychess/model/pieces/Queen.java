package com.marcopiccionitraining.parkychess.model.pieces;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Queen extends SliderPiece {

    public Queen(PlayerColor playerColor) {
        super(playerColor);
        setName(PieceNames.QUEEN);
    }
    @Override
    public List<Move> getPseudoLegalMoves(@NotNull Position from, @NotNull Board chessboard) {
        List<Move> queenPseudoLegalMoves = new ArrayList<>();
        List<List<Position>> allQueenFilesRanksAndDiagonals =
                PseudoLegalPiecesPositionsInitializer.getQueenPseudoLegalPositions(from);
        for (List<Position> positionsAlongOneDirection : allQueenFilesRanksAndDiagonals) {
            List<Move> pseudoLegalMovesInOneDirection = getPseudoLegalMovesInDirection(positionsAlongOneDirection,
                    from, chessboard);
            queenPseudoLegalMoves.addAll(pseudoLegalMovesInOneDirection);
        }
        log.debug("Pseudolegal moves for {} queen {} {}", getColor(), from, queenPseudoLegalMoves);
        return queenPseudoLegalMoves;
    }

    @Override
    public Piece copy() {
        Queen queenCopy = new Queen(getColor());
        queenCopy.setFlaggedAsHavingAlreadyMoved(isFlaggedAsHavingAlreadyMoved());
        return queenCopy;
    }
}
