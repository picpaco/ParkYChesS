package com.marcopiccionitraining.parkychess.model.pieces;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.*;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
@Slf4j
public class Pawn extends Piece {

    private final Direction forward;

    public Pawn(PlayerColor playerColor) {
        super(playerColor);
        if (playerColor.equals(PlayerColor.BLACK)) {
            forward = Direction.SOUTH;
        } else {
            forward = Direction.NORTH;
        }
        setName(PieceNames.PAWN);
        log.trace("Created {} pawn", playerColor);
    }

    private Collection<Move> getOneAndTwoStepsForwardMovesIncludingPromotions(@NonNull Position from, @NonNull Board chessboard) {
        Collection<Move> forwardMoves = new ArrayList<>();
        Position firstStepForward = from.stepTowardsDirection(forward);
        if (!chessboard.isEmpty(firstStepForward)) {
            return forwardMoves;
        }
        if (firstStepForward.getRow() == 0 || firstStepForward.getRow() == 7) {
            forwardMoves.addAll(getPromotionMoves(from, firstStepForward));
        } else {
            log.trace("Adding a standard pawn forward move.");
            forwardMoves.add(new StandardMove(from, firstStepForward));
        }
        DoublePawnMove twoStepsForwardMove;
        Position secondStepForward = firstStepForward.stepTowardsDirection(forward);
        if (!chessboard.isEmpty(secondStepForward)) {
            return forwardMoves;
        }
        if (isPawnOnStartingSquare(from, getColor())) {
            twoStepsForwardMove = new DoublePawnMove(from, secondStepForward, chessboard);
            log.trace("Adding a DoublePawnMove: {}", twoStepsForwardMove);
            forwardMoves.add(twoStepsForwardMove);
        }
        return forwardMoves;
    }

    //FIXME Use precomputed data structures
    private Collection<Move> getPseudolegalCaptureMoves(@NonNull Position from, @NonNull Board chessboard) {
        log.debug("About to compute capture moves from {} {} ", chessboard.getCurrentPlayerColor(), chessboard.getPiece(from));
        Collection<Move> captureMoves = new ArrayList<>();
        final Direction[] sideSteps = {Direction.EAST, Direction.WEST};
        Position oneStepForward = from.stepTowardsDirection(forward);
        for (Direction dir : sideSteps) {
            Position capturePos = oneStepForward.stepTowardsDirection(dir);
            if (!(capturePos.isInsideBoard())) {
                log.debug("Capture position must be inside the board.");
                continue;
            }
            if (chessboard.isEmpty(capturePos)) {
                log.debug("Cannot capture on {} because it is an empty square.", capturePos);
                continue;
            }
            if (chessboard.getPiece(capturePos).getColor().equals(getColor())) {
                log.debug("Cannot capture a friendly piece.");
                continue;
            }
            if (capturePos.getRow() == 7 || capturePos.getRow() == 0) {
                captureMoves.addAll(getPromotionMoves(from, capturePos));
            } else {
                captureMoves.add(new StandardMove(from, capturePos));
            }
        }
        return captureMoves;
    }

    private boolean isPawnOnStartingSquare(@NonNull Position from, @NonNull PlayerColor color) {
        if (color.equals(PlayerColor.BLACK)) {
            return from.getRow() == 1;
        } else {
            return from.getRow() == 6;
        }
    }
    //FIXME: where the argument 'from' comes from?
    @Override
    public Collection<Move> getPseudoLegalMoves(Position from, Board chessboard){
        Collection<Move> moves = new ArrayList<>();
        Collection<Move> oneTwoStepForwardMovesWithPromotions =
                getOneAndTwoStepsForwardMovesIncludingPromotions(from, chessboard);
        if (!oneTwoStepForwardMovesWithPromotions.isEmpty()) {
            log.debug("Pseudolegal non-capture pawn moves: {}", oneTwoStepForwardMovesWithPromotions);
            moves.addAll(oneTwoStepForwardMovesWithPromotions);
        }
        Collection<Move> captureMovesWithPromotions = getPseudolegalCaptureMoves(from, chessboard);
        if (!captureMovesWithPromotions.isEmpty()) {
            moves.addAll(captureMovesWithPromotions);
        }
        return moves;
    }

    /**
     * @return a copy of current pawn.
     */
        @Override
        public Piece copy () {
            Pawn pawnCopy = new Pawn(getColor());
            pawnCopy.setFlaggedAsHavingAlreadyMoved(isFlaggedAsHavingAlreadyMoved());
            return pawnCopy;
        }

        private Collection<Move> getPromotionMoves (Position from, Position to){
            assert from.getRow() == 1 || from.getRow() == 6 : "Pawn should be on 2nd or 7th row before promoting.";
            assert to.getRow() == 0 || to.getRow() == 7 : "Pawn should be on 1st or 8th row after promoting.";
            Collection<Move> promotionMoves = new ArrayList<>();
            promotionMoves.add(new PawnPromotionMove(from, to, new Queen(getColor())));
            promotionMoves.add(new PawnPromotionMove(from, to, new Knight(getColor())));
            promotionMoves.add(new PawnPromotionMove(from, to, new Rook(getColor())));
            promotionMoves.add(new PawnPromotionMove(from, to, new Bishop(getColor())));
            return promotionMoves;
    }
}