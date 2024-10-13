package com.marcopiccionitraining.parkychess.model.moves;

import com.marcopiccionitraining.parkychess.model.Board;
import com.marcopiccionitraining.parkychess.model.PlayerColor;
import com.marcopiccionitraining.parkychess.model.Direction;
import com.marcopiccionitraining.parkychess.model.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CastlingMove extends Move {
    private Direction kingCastlingSide;
    private Position rookFromPosition;
    private Position rookToPosition;
    //private final Logger LOGGER = LoggerFactory.getLogger(CastlingMove.class);

    public CastlingMove (MoveNames moveName, Position kingPosition){
        assert kingPosition != null: "King position must exist when castling.";
        assert (kingPosition.row() == 0 || kingPosition.row() == 7 ) && kingPosition.column() == 4 :
                "The king cannot castle because it is positioned on the wrong square.";
    //      LOGGER.trace("In castling move constructor");
        setName(moveName);
        setFrom(kingPosition);
        if (getName().equals(MoveNames.KINGSIDE_CASTLE)){
            kingCastlingSide = Direction.EAST;
            setTo(new Position(kingPosition.row(), 6));
            rookFromPosition = new Position(kingPosition.row(), 7);
            rookToPosition = new Position(kingPosition.row(), 5);
        } else {
            if (getName().equals(MoveNames.QUEENSIDE_CASTLE)) {
                kingCastlingSide = Direction.WEST;
                setTo(new Position(kingPosition.row(), 2));
                rookFromPosition = new Position(kingPosition.row(), 0);
                rookToPosition = new Position(kingPosition.row(), 3);
            }
        }
    //    LOGGER.trace("Exiting CastlingMove constructor...");
    }

    @Override
    public boolean isLegal(Board chessboard){
  //      LOGGER.debug("Is castling move legal?");
        PlayerColor playerColor = chessboard.getPiece(getFrom()).getColor();
        if (chessboard.isInCheck(playerColor)) {
            return false;
        }
        Board copy = chessboard.copy();
        Position kingPositionInCopy = getFrom();
        for (int i = 0; i < 2; i++) {
             Position castlingKingStep = kingPositionInCopy.stepTowardsDirection(kingCastlingSide);
            if (castlingKingStep != null) {
                new StandardMove(kingPositionInCopy, castlingKingStep).execute(copy);
                kingPositionInCopy = castlingKingStep;
            } else {
      //          LOGGER.debug("Castling move is not legal because king move is not valid.");
                return false;
            }
            if (copy.isInCheck(playerColor)){
         //       LOGGER.debug("Castling move is not legal because player is in check.");
                return false;
            }
        }
  //      LOGGER.debug("Castling move is legal.");
        return true;
    }

    @Override
    public boolean execute (Board chessboard) {
        assert chessboard != null : "Chessboard must exist.";
        new StandardMove(getFrom(), getTo()).execute(chessboard);
        new StandardMove(rookFromPosition, rookToPosition).execute(chessboard);
        return false;
    }

    @Override
    public void undo (Board chessboard) {
        assert chessboard != null : "Chessboard must exist.";
        assert chessboard.isEmpty(getFrom()):"Original king's from position should be empty before undoing a move.";
        chessboard.setPiece(chessboard.getPiece(getTo()), getFrom());
        chessboard.setEmpty(getTo());
        assert chessboard.isEmpty(rookFromPosition):"Original rook's from position should be empty before undoing a move.";
        chessboard.setPiece(chessboard.getPiece(rookToPosition), rookFromPosition);
        chessboard.setEmpty(rookToPosition);
    }
}
