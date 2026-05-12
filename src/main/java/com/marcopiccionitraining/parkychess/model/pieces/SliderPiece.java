package com.marcopiccionitraining.parkychess.model.pieces;

import com.marcopiccionitraining.parkychess.model.Board;
import com.marcopiccionitraining.parkychess.model.PlayerColor;
import com.marcopiccionitraining.parkychess.model.Position;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import com.marcopiccionitraining.parkychess.model.moves.StandardMove;

import java.util.ArrayList;
import java.util.List;

public abstract class SliderPiece extends Piece {
    SliderPiece(PlayerColor playerColor) {
        super(playerColor);
    }

    List<Move> getPseudoLegalMovesInDirection(List<Position> positionsInDirection,
                                              Position from, Board chessboard) {
        List<Move> allPseudoLegalMovesInDirection = new ArrayList<>();
        for (Position destination : positionsInDirection) {
            if (!(chessboard.isEmpty(destination))) {
                Piece pieceFound = chessboard.getPiece(destination);
                if (!(pieceFound.getColor().equals(chessboard.getPiece(from).getColor()))) {
                    allPseudoLegalMovesInDirection.add(new StandardMove(from, destination));
                }
                break;
            } else {
                allPseudoLegalMovesInDirection.add(new StandardMove(from, destination));
            }
        }
        return allPseudoLegalMovesInDirection;
    }
}
