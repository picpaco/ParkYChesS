package com.marcopiccionitraining.parkychess.model.pieces;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.CastlingMove;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import com.marcopiccionitraining.parkychess.model.moves.MoveNames;
import com.marcopiccionitraining.parkychess.model.moves.StandardMove;

import java.util.ArrayList;
import java.util.Collection;

public class King extends Piece {
   // private final Logger LOGGER = LoggerFactory.getLogger(King.class);
    public final Direction[] allowedDirections = new Direction[]{
            Direction.NORTH, Direction.WEST, Direction.SOUTH, Direction.EAST,
            Direction.NORTH_EAST, Direction.NORTH_WEST, Direction.SOUTH_EAST, Direction.SOUTH_WEST
    };

    public King(PlayerColor playerColor){
        super(playerColor);
        setName(PieceNames.KING);
    }

    private boolean hasRookNeverMoved (Position rookPosition, Board chessboard){
        assert rookPosition == null || chessboard.getPiece(rookPosition).getName().equals(PieceNames.ROOK) :
                "Either there is no piece at position " + rookPosition + " or such piece is a rook.";
        if (rookPosition == null || chessboard.isEmpty(rookPosition)){
            return false;
        }
        Piece rook = chessboard.getPiece(rookPosition);
        return rook != null && rook.getName().equals(PieceNames.ROOK) && !rook.hasMovedForGood;
    }

    private boolean isCastlingPathFree (Collection<Position> positions, Board chessboard){
        for (Position pos : positions) {
            if (!(chessboard.isEmpty(pos))){
                return false;
            }
        }
        return true;
    }

    public boolean isKingsideCastlingPseudoLegal(Position from, Board chessboard){
        if (!(chessboard.haveKingAndKingsideRookNeverMoved(getColor()))){
         //   System.out.println("from: " + from + ", " + getColor() + "King or Kingside Rook have Moved");
            return false;
        }
        Position rookPosition = new Position(from.row(), 7);
        assert chessboard.getPiece(rookPosition).getColor().equals(getColor()) : "I was expecting a " + getColor() + " piece on " + rookPosition;
        assert chessboard.getPiece(rookPosition).getName().equals(PieceNames.ROOK) : "I was expecting a rook on " + rookPosition;
        ArrayList<Position> positionsBetween = new ArrayList<>();
        positionsBetween.add(new Position(from.row(), 6));
        positionsBetween.add(new Position(from.row(), 5));
//        System.out.println("hasRookNeverMoved: " + hasRookNeverMoved(rookPosition, chessboard));
//        System.out.println("isCastlingPathFree: " + isCastlingPathFree(positionsBetween, chessboard));
        return hasRookNeverMoved(rookPosition, chessboard) &&
                isCastlingPathFree(positionsBetween, chessboard);
    }

    public boolean isQueensideCastlingPseudoLegal(Position from, Board chessboard){
        if (!(chessboard.haveKingAndQueensideRookNeverMoved(getColor()))){
            return false;
        }
        Position rookPosition = new Position(from.row(), 0);//last changed 3
        assert chessboard.getPiece(rookPosition).getColor().equals(getColor()) : "I was expecting a " + getColor() + " piece on " + rookPosition;
        assert chessboard.getPiece(rookPosition).getName().equals(PieceNames.ROOK) : "I was expecting a rook on " + rookPosition;
        ArrayList<Position> positionsBetween = new ArrayList<>();
        positionsBetween.add(new Position(from.row(), 1));
        positionsBetween.add(new Position(from.row(), 2));
        positionsBetween.add(new Position(from.row(), 3));
        return hasRookNeverMoved(rookPosition, chessboard) &&//last changed 4
                isCastlingPathFree(positionsBetween, chessboard);
    }
    private Collection<Position> getPotentialDestinationPositions(Position from, Board chessboard) {
        Collection<Position> potentialDestinationPositions = new ArrayList<>();
        for (Direction direction : allowedDirections) {
            Position to = from.stepTowardsDirection(direction);
            if (to != null) {
                if (!(chessboard.isInsideBoard(to))){
                    continue;
                }
                if (chessboard.isEmpty(to) || !(chessboard.getPiece(to).getColor()).equals(getColor())) {
                    potentialDestinationPositions.add(to);
                }
            }
        }
        return  potentialDestinationPositions;
    }

    @Override
    public Collection<Move> getPseudoLegalMoves(Position from, Board chessboard){
        Collection<Move> pseudoLegalMoves = new ArrayList<>();
        for (Position to : getPotentialDestinationPositions(from, chessboard)) {
                StandardMove pseudoLegalMove = new StandardMove(from, to);
                pseudoLegalMoves.add(pseudoLegalMove);
        }
 //       System.out.println("Is kingside castle pseudolegal in this position? " +
  //              isKingsideCastlingPseudoLegal(from, chessboard));
        if (isKingsideCastlingPseudoLegal(from, chessboard)){
            pseudoLegalMoves.add(new CastlingMove(MoveNames.KINGSIDE_CASTLE, from));
        }
        if (isQueensideCastlingPseudoLegal(from, chessboard)){
            pseudoLegalMoves.add(new CastlingMove(MoveNames.QUEENSIDE_CASTLE, from));
        }
        return  pseudoLegalMoves;
    }
    @Override
    public boolean canCaptureEnemyKing (Position from, Board chessboard) {
        for (Position to : getPotentialDestinationPositions(from, chessboard)) {
            if (to != null) {
                Piece targetPiece = chessboard.getPiece(to);
                if (targetPiece != null && targetPiece.getName().equals(PieceNames.KING)) {
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public Piece copy() {
        King kingCopy = new King(getColor());
        kingCopy.setPosition(getPosition());
        kingCopy.hasMoved = this.hasMoved;
        kingCopy.hasMovedForGood = this.hasMovedForGood;
   //     kingCopy.currentDepth = this.currentDepth;
        return kingCopy;
    }
}
