package com.marcopiccionitraining.parkychess.model.moves;

import com.marcopiccionitraining.parkychess.model.Board;
import com.marcopiccionitraining.parkychess.model.PlayerColor;
import com.marcopiccionitraining.parkychess.model.Direction;
import com.marcopiccionitraining.parkychess.model.Position;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CastlingMove extends Move {
    private Direction kingCastlingSide;

    @Getter
    private Position rookFromPosition;

    @Getter
    private Position rookToPosition;

    private final Logger LOGGER = LoggerFactory.getLogger(CastlingMove.class);

    public CastlingMove (MoveNames moveName, Position kingPosition){
        assert kingPosition != null: "King position must exist when castling.";
        assert (kingPosition.row() == 0 || kingPosition.row() == 7 ) && kingPosition.column() == 4 :
                "The king cannot perform move " + moveName + " because it is positioned on the wrong square: " + kingPosition;
    //      LOGGER.trace("In castling move constructor");
 //       System.out.println("CastlingMove constructor; king position: " + kingPosition);
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
        PlayerColor playerColor = chessboard.getPiece(getFrom()).getColor();
    //    System.out.println("Is castling move " + getName() + " legal for " + playerColor + "?");
        if (chessboard.isInCheck(playerColor)) {
         //   LOGGER.info("Castling move {} for {} is not legal because {} is in check.", getName(), playerColor, playerColor);
 //           System.out.println("Castling move " + getName() + " for " + playerColor + " is not legal because " +
//                    playerColor + " is in check.");
            //TODO: update castling variables in Board?
            return false;
        }
        if (kingCastlingSide.equals(Direction.WEST) &&
                !(chessboard.isQueensideCastlingPossibleFromFEN(playerColor))) {
            return false;
        }
        if (kingCastlingSide.equals(Direction.EAST) &&
                !(chessboard.isKingsideCastlingPossibleFromFEN(playerColor))) {
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
  //              LOGGER.info("Castling move is not legal because king move is not valid.");
    //            System.out.println("No (invalid king move)");
                return false;
            }
            if (copy.isInCheck(playerColor)){
 //               LOGGER.info("Castling move is not legal because {} would be in check while castling.", playerColor);
   //             System.out.println("No (in check while castling)");
                return false;
            }
        }
//        LOGGER.debug("Castling move {} is legal.", this);
//        System.out.println("Yes");
        return true;
    }

    @Override
    public boolean execute (Board chessboard) {
        assert chessboard != null : "Chessboard must exist.";
        new StandardMove (getFrom(), getTo()).execute(chessboard);
        chessboard.getPiece(getTo()).setHasMoved(true);
        new StandardMove (rookFromPosition, rookToPosition).execute(chessboard);
        chessboard.getPiece(rookToPosition).setHasMoved(true);
        return false;
    }

    @Override
    public void undo (Board chessboard) {
        assert chessboard != null : "Chessboard must exist.";
        assert chessboard.isEmpty(getFrom()):"Original king's from position should be empty before undoing a move.";
        chessboard.setPiece(chessboard.getPiece(getTo()), getFrom());
        chessboard.getPiece(getFrom()).setHasMoved(false);
        chessboard.setEmpty(getTo());
        assert chessboard.isEmpty(rookFromPosition):"Original rook's from position should be empty before undoing a move.";
        chessboard.setPiece(chessboard.getPiece(rookToPosition), rookFromPosition);
        chessboard.getPiece(rookFromPosition).setHasMoved(false);
        chessboard.setEmpty(rookToPosition);
    }
}
