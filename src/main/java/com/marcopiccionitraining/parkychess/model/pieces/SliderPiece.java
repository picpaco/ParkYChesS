package com.marcopiccionitraining.parkychess.model.pieces;

import com.marcopiccionitraining.parkychess.model.Board;
import com.marcopiccionitraining.parkychess.model.PlayerColor;
import com.marcopiccionitraining.parkychess.model.Position;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import com.marcopiccionitraining.parkychess.model.moves.StandardMove;

import java.util.ArrayList;
import java.util.Collection;

public abstract class SliderPiece extends Piece {
    SliderPiece(PlayerColor playerColor) {
        super(playerColor);
    }

    Collection<Move> pseudoLegalMovesOnDirection(Collection<Position> positionsInDirection, Position from, Board chessboard){
        Collection<Move> allPseudoLegalMoves = new ArrayList<>();
            for (Position position : positionsInDirection) {
                if (chessboard.isEmpty(position)){
                    allPseudoLegalMoves.add(new StandardMove(from, position));
                } else {
                    Piece pieceFound = chessboard.getPiece(position);
                    if (!(pieceFound.getColor().equals(chessboard.getPiece(from).getColor()))) {
                        allPseudoLegalMoves.add(new StandardMove(from, position));
                        return allPseudoLegalMoves;
                    }
                    return allPseudoLegalMoves;
                }
            }
        return allPseudoLegalMoves;
    }
}
