package com.marcopiccionitraining.parkychess.model.pieces;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import com.marcopiccionitraining.parkychess.model.moves.StandardMove;


import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import java.util.*;
@Slf4j
public class Knight extends Piece {

    public Knight(PlayerColor playerColor){
        super(playerColor);
        setName(PieceNames.KNIGHT);
    }

    @Override
    public List<Move> getPseudoLegalMoves(@NonNull Position from, @NonNull Board chessboard) {
        List<Move> moves = new ArrayList<>();
        List<Position> possiblePseudoLegalPositions =
                PseudoLegalPiecesPositionsInitializer.getKnightPseudoLegalPositions(from);
        for (Position to : possiblePseudoLegalPositions) {
            if (chessboard.isEmpty(to)) {
                moves.add(new StandardMove(from, to));
                continue;
            }
            Piece pieceFound = chessboard.getPiece(to);
            if (!(pieceFound.getColor().equals(chessboard.getPiece(from).getColor()))) {
                moves.add(new StandardMove(from, to));
            }
        }
        log.debug("PseudoLegalMoves for Knight: {}", moves);
        return moves;
    }

    @Override
    public Piece copy() {
        Knight knightCopy = new Knight(getColor());
        knightCopy.setFlaggedAsHavingAlreadyMoved(isFlaggedAsHavingAlreadyMoved());
        return knightCopy;
    }
}