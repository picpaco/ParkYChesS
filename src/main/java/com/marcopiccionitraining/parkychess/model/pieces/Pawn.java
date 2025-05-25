package com.marcopiccionitraining.parkychess.model.pieces;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.*;

import java.util.ArrayList;
import java.util.Collection;

public class Pawn extends Piece {

    private Direction forward = null;
  //  private final Logger LOGGER = LoggerFactory.getLogger(Pawn.class);

    public Pawn (PlayerColor playerColor){
        super (playerColor);
        if (playerColor.equals(PlayerColor.BLACK)){
            forward = Direction.SOUTH;
        } else if (playerColor.equals (PlayerColor.WHITE)){
            forward = Direction.NORTH;
        }
        setName (PieceNames.PAWN);

    }

    Collection<Move> getForwardMoves (Position from, Board chessboard){
        Collection<Move> forwardMoves = new ArrayList<>();
        Position oneStepForward = from.stepTowardsDirection(forward);
        if (oneStepForward != null){
            if (canMoveTo(oneStepForward, chessboard)){
                if (oneStepForward.row() == 0 || oneStepForward.row() == 7){
                    forwardMoves.addAll(getPromotionMoves(from, oneStepForward));
                } else {
                    forwardMoves.add(new StandardMove(from, oneStepForward));
                }
                Position twoStepsForward = oneStepForward.stepTowardsDirection(forward);
                if (twoStepsForward != null) {
                    if (isPawnAtStartingSquare(from, chessboard.getPiece(from).getColor()) &&
                            canMoveTo(twoStepsForward, chessboard)) {
                        forwardMoves.add(new DoublePawnMove(from, twoStepsForward));
                    }
                }
            }
        }
        return  forwardMoves;
    }

    private boolean isPawnAtStartingSquare(Position from, PlayerColor color) {
        if (color.equals(PlayerColor.BLACK)){
            return from.row() == 1;
        } else {
            if (color.equals(PlayerColor.WHITE)){
                return from.row() == 6;
            }
        }
        return false;
    }

    private boolean canMoveTo(Position position, Board chessboard){
        return  chessboard.isInsideBoard(position) && chessboard.isEmpty(position);
    }
    Collection<Move> getCapturingMoves(Position from, Board chessboard){
        Collection<Move> capturingMoves = new ArrayList<>();
        for ( Direction direction : new Direction[] { Direction.EAST, Direction.WEST}) {
            Position oneStepForward = from.stepTowardsDirection(forward);
            if (oneStepForward != null) {
                Position oneStepDiagonally = oneStepForward.stepTowardsDirection(direction);
                if (oneStepDiagonally != null) {
           //         LOGGER.trace("En passant capture position for ({})={}", PlayerColor.getOpponentColor(getColor()),
                    chessboard.getEnPassantCapturePositionForColor(PlayerColor.getOpponentColor(getColor()));
          //          LOGGER.trace("oneStepDiagonally: {}", oneStepDiagonally.get());
                    if (oneStepDiagonally.equals(chessboard.getEnPassantCapturePositionForColor(PlayerColor.getOpponentColor(getColor())))) {
                        capturingMoves.add(new EnPassantMove(from, oneStepDiagonally));
                    } else {
                        if (canCaptureAt(oneStepDiagonally, chessboard)) {
                            if (oneStepDiagonally.row() == 0 || oneStepDiagonally.row() == 7) {
                                capturingMoves.addAll(getPromotionMoves(from, oneStepDiagonally));
                            } else {
                                capturingMoves.add(new StandardMove(from, oneStepDiagonally));
                            }
                        }
                    }
                }
            }
        }
        return  capturingMoves;
    }
    @Override
    public boolean canCaptureEnemyKing (Position from, Board chessboard){
  //      LOGGER.trace("In canCaptureEnemyKing.");
        for (Move move : getCapturingMoves(from, chessboard)) {
            Piece targetPiece = chessboard.getPiece(move.getTo());
            if (targetPiece != null && targetPiece.getClass().equals(King.class)){
                return true;
            }
        }
        return false;
    }

    private boolean canCaptureAt (Position position, Board chessBoard){
        if (!(chessBoard.isInsideBoard(position)) || chessBoard.isEmpty(position)){
            return false;
        }
        return !((chessBoard.getPiece(position).getColor()).equals(getColor()));
    }

    @Override
    public Collection<Move> getPseudoLegalMoves(Position from, Board chessboard){
        Collection<Move> moves = new ArrayList<>();
        moves.addAll(getForwardMoves(from, chessboard));
        moves.addAll(getCapturingMoves(from, chessboard));
        return moves;
    }

    /**
     * @return copy of current pawn.
     */
    @Override
    public Piece copy() {
        Pawn pawnCopy = new Pawn(getColor());
        pawnCopy.setPosition(getPosition());
        pawnCopy.hasMoved = this.hasMoved;
        pawnCopy.hasMovedForGood = this.hasMovedForGood;
      //  pawnCopy.currentDepth = this.currentDepth;
        return pawnCopy;
    }

    private Collection<Move> getPromotionMoves(Position from, Position to){
        assert from.row() == 1 || from.row() == 6:"Pawn should be on 2nd or 7th row before promoting.";
        assert to.row() == 0 || to.row() == 7:"Pawn should be on 1st or 8th row after promoting.";
        Collection<Move> promotionMoves = new ArrayList<>();
        promotionMoves.add(new PawnPromotionMove(from, to, new Queen(getColor())));
        promotionMoves.add(new PawnPromotionMove(from, to, new Knight(getColor())));
        promotionMoves.add(new PawnPromotionMove(from, to, new Rook(getColor())));
        promotionMoves.add(new PawnPromotionMove(from, to, new Bishop(getColor())));
        return promotionMoves;
    }
}