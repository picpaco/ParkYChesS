package com.marcopiccionitraining.parkychess.model.pieces;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.Move;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class Bishop extends SliderPiece {

    public Bishop(PlayerColor playerColor){
        super(playerColor);
        setName(PieceNames.BISHOP);
    }

    @Override
    public List<Move> getPseudoLegalMoves(Position from, Board chessboard){
        List<Move> bishopPseudoLegalMoves = new ArrayList<>();
        List<List<Position>> allBishopDiagonals =
                PseudoLegalPiecesPositionsInitializer.getBishopPseudoLegalPositions(from);
        for (List<Position> positionsAlongOneDiagonal : allBishopDiagonals) {
            List<Move> pseudoLegalMovesInOneDirection = getPseudoLegalMovesInDirection(positionsAlongOneDiagonal,
                    from, chessboard);
            bishopPseudoLegalMoves.addAll(pseudoLegalMovesInOneDirection);
        }
        log.debug("Pseudolegal moves for {} bishop {}: {}", getColor(), from, bishopPseudoLegalMoves);
        return bishopPseudoLegalMoves;
    }

    @Override
    public Piece copy() {
        Bishop bishopCopy = new Bishop(getColor());
        bishopCopy.setFlaggedAsHavingAlreadyMoved(isFlaggedAsHavingAlreadyMoved());
        return bishopCopy;
    }
}