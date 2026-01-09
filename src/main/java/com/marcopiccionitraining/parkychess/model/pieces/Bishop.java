package com.marcopiccionitraining.parkychess.model.pieces;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.Move;

import java.util.ArrayList;
import java.util.Collection;

public class Bishop extends SliderPiece {
    public Bishop(PlayerColor playerColor){
        super(playerColor);
        setName(PieceNames.BISHOP);
    }

  //  private final Logger LOGGER = LoggerFactory.getLogger(Bishop.class);

    public Collection<Move> getPseudoLegalMoves(Position from , Board chessboard){
        Collection<Move> bishopPseudoLegalMoves = new ArrayList<>();
        ArrayList<ArrayList<Position>> existingDiagonals =
                PseudoLegalPiecesPositionsInitializer.getBishopPseudoLegalPoitions(from);
        for (ArrayList<Position> diagonalPositions : existingDiagonals) {
            bishopPseudoLegalMoves.addAll(getPseudoLegalMovesInDirection(diagonalPositions, from, chessboard));
        }
        return bishopPseudoLegalMoves;
          //    LOGGER.trace("Exiting getPseudoLegalMoves(Position from = {}, Board chessboard). Moves found: {}", from, moves);
    }

    @Override
    public Piece copy() {
        Bishop bishopCopy = new Bishop(getColor());
        bishopCopy.setPosition(new Position(getPosition().row(), getPosition().column()));
        bishopCopy.setFlaggedAsHavingAlreadyMoved(isFlaggedAsHavingAlreadyMoved());
        return bishopCopy;
    }
}