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

    Collection<Move> getPseudoLegalMovesInDirection(Collection<Position> positionsInDirection,
                                                       Position from, Board chessboard){
        assert from != null : "The from position must exist";
        Collection<Move> allPseudoLegalMovesInDirection = new ArrayList<>();
            for (Position position : positionsInDirection) {
                if (chessboard.isEmpty(position)){
                    allPseudoLegalMovesInDirection.add(new StandardMove(from, position));
                } else {
                    Piece pieceFound = chessboard.getPiece(position);
                    if (!(pieceFound.getColor().equals(chessboard.getPiece(from).getColor()))) {
                        allPseudoLegalMovesInDirection.add(new StandardMove(from, position));
                        return allPseudoLegalMovesInDirection;
                    }
                    return allPseudoLegalMovesInDirection;
                }
            }
        return allPseudoLegalMovesInDirection;
    }
}
