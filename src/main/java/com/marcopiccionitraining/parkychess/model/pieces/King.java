package com.marcopiccionitraining.parkychess.model.pieces;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.CastlingMove;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import com.marcopiccionitraining.parkychess.model.moves.MoveNames;
import com.marcopiccionitraining.parkychess.model.moves.StandardMove;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class King extends Piece {
   // private final Logger LOGGER = LoggerFactory.getLogger(King.class);
    public final Direction[] allowedDirections = new Direction[]{
            Direction.NORTH, Direction.WEST, Direction.SOUTH, Direction.EAST,
            Direction.NORTH_EAST, Direction.NORTH_WEST, Direction.SOUTH_EAST, Direction.SOUTH_WEST
    };

    public King(PlayerColor playerColor){
        super(playerColor);
        setName(PieceName.KING);
    }

  //  @Override
   // public List<StandardMove> generateMoves(Board chessboard){
     //   return null;
   // }

    private boolean hasRookNotMovedYet (Position rookPosition, Board chessboard){
        if (chessboard.isEmpty(rookPosition)){
            return false;
        }
        Piece rook = chessboard.getPiece(rookPosition.row(), rookPosition.column());
        if (rook != null && rook.getName().equals(PieceName.ROOK)) {
            return !rook.hasMoved();
        } else {
            return false;
        }
    }
    private boolean isCastlingPathFree(Collection<Position> positions, Board chessboard){
        for (Position pos : positions) {
            if (!(chessboard.isEmpty(pos))){
                return false;
            }
        }
        return true;
    }
    private boolean isKingsideCastlingAllowed (Position from, Board chessboard){
        if (!(chessboard.isKingsideCastlingPossibleFromFEN(getColor()))){
            return false;
        }
        if (hasMoved()){
            return false;
        }
        Position rookPosition = new Position(from.row(), 7);
        ArrayList<Position> positionsBetween = new ArrayList<>();
        positionsBetween.add(new Position(from.row(), 6));
        positionsBetween.add(new Position(from.row(), 5));
    //    PlayerColor rookColor = chessboard.getPiece(rookPosition).getColor();
    //    boolean result = rookColor.equals(getColor()) && hasRookNotMovedYet(rookPosition, chessboard) &&
        return hasRookNotMovedYet(rookPosition, chessboard) &&
                isCastlingPathFree(positionsBetween, chessboard);
    }

    private boolean isQueensideCastlingAllowed (Position from, Board chessboard){
        if (!(chessboard.isQueensideCastlingPossibleFromFEN(getColor()))){
            return false;
        }
        if (hasMoved()){
            return false;
        }
        Position rookPosition = new Position(from.row(), 0);
        ArrayList<Position> positionsBetween = new ArrayList<>();
        positionsBetween.add(new Position(from.row(), 1));
        positionsBetween.add(new Position(from.row(), 2));
        positionsBetween.add(new Position(from.row(), 3));
 //       PlayerColor rookColor = chessboard.getPiece(rookPosition).getColor();
 //       boolean result = rookColor.equals(getColor()) && hasRookNotMovedYet(rookPosition, chessboard) &&
        return hasRookNotMovedYet(rookPosition, chessboard) &&
                isCastlingPathFree(positionsBetween, chessboard);
    }
    private Collection<Position> getPotentialDestinationPositions(Position from, Board chessboard) {
   // private Collection<Optional<Position>> getPotentialDestinationPositions(Position from, Board chessboard) {
    //    Collection<Optional<Position>> potentialDestinationPositions = new ArrayList<>();
        Collection<Position> potentialDestinationPositions = new ArrayList<>();
        for (Direction direction : allowedDirections) {
            Position to = from.stepTowardsDirection(direction);
            if (to != null) {
                if (!(chessboard.isInside(to))){
                    continue;
                }
                if (chessboard.isEmpty(to) || !(chessboard.getPiece(to).getColor()).equals(getColor())) {
                    potentialDestinationPositions.add(to);
                }
            }
            /*
            Optional<Position> to = from.stepTowardsDirection(direction);
            if (to.isPresent()){
                if (!(chessboard.isInside(to.get()))){
                    continue;
                }
                if (chessboard.isEmpty(to.get()) || !(chessboard.getPiece(to.get()).getColor()).equals(getColor())) {
                    potentialDestinationPositions.add(to);
                }
            }
             */
        }
        return  potentialDestinationPositions;
    }

    @Override
    public Collection<Move> getPseudoLegalMoves(Position from, Board chessboard){
        Collection<Move> pseudoLegalMoves = new ArrayList<>();
        for (Position to : getPotentialDestinationPositions(from, chessboard)) {
        //    for (Optional<Position> to : getPotentialDestinationPositions(from, chessboard)) {
            if (to != null){
                StandardMove pseudoLegalMove = new StandardMove(from, to);
      //      if (to.isPresent()){
       //         StandardMove pseudoLegalMove = new StandardMove(from, to.get());
                pseudoLegalMoves.add(pseudoLegalMove);
            }
        }
        if (isKingsideCastlingAllowed(from, chessboard)){
            pseudoLegalMoves.add(new CastlingMove(MoveNames.KINGSIDE_CASTLE, from));
        }
        if (isQueensideCastlingAllowed(from, chessboard)){
            pseudoLegalMoves.add(new CastlingMove(MoveNames.QUEENSIDE_CASTLE, from));
        }
        return  pseudoLegalMoves;
    }
    @Override
    public boolean canCaptureEnemyKing (Position from, Board chessboard) {
   /*     for (Move aMove : getPseudoLegalMoves(from, chessboard)) {
            Piece targetPiece = chessboard.getPiece(aMove.getTo());
            if (targetPiece != null && targetPiece.getName().equals(PieceName.KING)){
                return true;
            }
        }*/
        for (Position to : getPotentialDestinationPositions(from, chessboard)) {
            if (to != null) {
                Piece targetPiece = chessboard.getPiece(to);
                if (targetPiece != null && targetPiece.getName().equals(PieceName.KING)) {
                    return true;
                }
            }
        }
        /*for (Optional<Position> to : getPotentialDestinationPositions(from, chessboard)) {
            if (to.isPresent()) {
                Piece targetPiece = chessboard.getPiece(to.get());
                if (targetPiece != null && targetPiece.getName().equals(PieceName.KING)) {
                    return true;
                }
            }
        }*/
        return false;
    }
    @Override
    public Piece copy() {
        King kingCopy = new King(getColor());
        kingCopy.setHasMoved(hasMoved());
        return kingCopy;
    }
}
