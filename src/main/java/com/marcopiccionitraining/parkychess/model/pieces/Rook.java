package com.marcopiccionitraining.parkychess.model.pieces;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
@Slf4j
public class Rook extends SliderPiece {
    @Getter @Setter
    private boolean isBlackKingsideRook;
    @Getter @Setter
    private boolean isBlackQueensideRook;
    @Getter @Setter
    private boolean isWhiteKingsideRook;
    @Getter @Setter
    private boolean isWhiteQueensideRook;
    @Getter @Setter
    private boolean isBlackKingsideRookFlaggedAsHavingAlreadyMoved;
    @Getter @Setter
    private boolean isBlackQueensideRookFlaggedAsHavingAlreadyMoved;
    @Getter @Setter
    private boolean isWhiteKingsideRookFlaggedAsHavingAlreadyMoved;
    @Getter @Setter
    private boolean isWhiteQueensideRookFlaggedAsHavingAlreadyMoved;

    public Rook (PlayerColor playerColor) {
        super(playerColor);
        setName(PieceNames.ROOK);
        if (getPosition() != null) {
            if (playerColor == PlayerColor.BLACK) {
                if (getPosition().row() == 0) {
                    if (getPosition().column() == 7) {
                        isBlackKingsideRook = true;
                    } else {
                        if (getPosition().column() == 0) {
                            isBlackQueensideRook = true;
                        }
                    }
                }
            } else {
                if (getPosition().row() == 7) {
                    if (getPosition().column() == 7) {
                        isWhiteKingsideRook = true;
                    } else {
                        if (getPosition().column() == 0) {
                            isWhiteQueensideRook = true;
                        }
                    }
                }
            }
        }
    }

    @Override
    public Piece copy() {
        Rook rookCopy = new Rook(getColor());
        rookCopy.setPosition(new Position(getPosition().row(), getPosition().column()));
        rookCopy.setFlaggedAsHavingAlreadyMoved(isFlaggedAsHavingAlreadyMoved());
        return rookCopy;
    }
     public Collection<Move> getPseudoLegalMoves (Position from , Board chessboard){
        assert from != null : "Position 'from' must exist.";
        log.trace("Entering Rook.getPseudoLegalMoves(from = {})", from);
        Collection<Move> rookPseudoLegalMoves = new ArrayList<>();
        ArrayList<ArrayList<Position>> potentialRookDestinations =
                PseudoLegalPiecesPositionsInitializer.getRookPseudoLegalPositions(from);
        for (ArrayList<Position> positionsInOneDirection : potentialRookDestinations){
            rookPseudoLegalMoves.addAll(getPseudoLegalMovesInDirection(positionsInOneDirection, from, chessboard));
        }
        log.debug("Pseudolegal moves for {} rook {} {}", getColor(), from, rookPseudoLegalMoves);
        return rookPseudoLegalMoves;
    }
}