package com.marcopiccionitraining.parkychess.model.pieces;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.*;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
@Slf4j
public class Pawn extends Piece {

    private Direction forward = null;

    public Pawn (PlayerColor playerColor){
        super (playerColor);
        log.trace("Created {} pawn", playerColor);
        if (playerColor.equals(PlayerColor.BLACK)){
            forward = Direction.SOUTH;
        } else {
            forward = Direction.NORTH;
        }
        setName (PieceNames.PAWN);
    }

    private Collection<Move> getOneTwoStepsForwardMovesWithPromotions(@NonNull Position from, @NonNull Board chessboard) {
        Collection<Move> forwardMoves = new ArrayList<>();
        Position firstStepForward = from.stepTowardsDirection (forward);
        if (firstStepForward == null) {
            return forwardMoves;
        }
        if (!chessboard.isEmpty(firstStepForward)) {
            return forwardMoves;
        }
        if (firstStepForward.row() == 0 || firstStepForward.row() == 7) {
            forwardMoves.addAll(getPromotionMoves(from, firstStepForward));
        } else {
            log.trace("Adding a standard pawn forward move.");
            forwardMoves.add(new StandardMove(from, firstStepForward));
        }
        DoublePawnMove twoStepsForwardMove;
        Position secondStepForward = firstStepForward.stepTowardsDirection(forward);
        if (secondStepForward == null) {
            return forwardMoves;
        }
        if(!chessboard.isEmpty(secondStepForward)) {
            return forwardMoves;
        }
        if (isPawnOnStartingSquare(from, chessboard.getPiece(from).getColor())) {
            twoStepsForwardMove = new DoublePawnMove(from, secondStepForward);
            log.trace("Adding a DoublePawnMove: {}", twoStepsForwardMove);
            forwardMoves.add(twoStepsForwardMove);
        }
        return forwardMoves;
    }

    private boolean canMoveTo(@NonNull Position position, @NonNull Board chessboard){
        return  chessboard.isInsideBoard(position) && chessboard.isEmpty(position);
    }

    //FIXME Use precomputed data structures
    private Collection<Move> getCaptureMovesWithPromotions (@NonNull Position from, @NonNull Board chessboard) {
        log.debug("from arg: {}", from);
        Collection<Move> captureMoves = new ArrayList<>();
        final Direction[] sideSteps = {Direction.EAST, Direction.WEST};
        Position oneStepForward = from.stepTowardsDirection(forward);
        assert oneStepForward != null : "One step forward should exist.";
        for (Direction dir : sideSteps) {
            Position capturePos = oneStepForward.stepTowardsDirection(dir);
            if (capturePos != null) {
                if (canStandardCaptureAt(capturePos, chessboard)) {
                    if (capturePos.row() == 7 || capturePos.row() == 0) {
                        captureMoves.addAll(getPromotionMoves(from, capturePos));
                    } else {
                        captureMoves.add(new StandardMove(from, capturePos));
                    }
                }
                //FIXME
                Position opponentPawnSkippedPos = chessboard.getPawnSkippedPosition(PlayerColor.getOpponentColor(getColor()));
                log.debug("capture pos: ({},{}) <=> {}, current color; {}, opponent pawn skipped pos: {}",
                        capturePos.row(), capturePos.column(), capturePos, chessboard.getCurrentPlayerColor(), opponentPawnSkippedPos);
                if ((chessboard.getLastExecutedMove().getName().equals(MoveNames.DOUBLE_PAWN)) ||
                        (opponentPawnSkippedPos != null)){
                    if (capturePos.equals(opponentPawnSkippedPos)) {
                        captureMoves.add(new EnPassantCapture(from, opponentPawnSkippedPos));
                        log.debug("Added en passant capture move {}{}", from, opponentPawnSkippedPos);
                        return captureMoves;
                    } else {
                        log.debug("No en passant capture is added because the capture pos {} is not equal to opponent's skip pos {}",
                                capturePos, opponentPawnSkippedPos);
                    }
                } else {
                    log.debug("opponent pawn skipped position {} does not exist or last executed move was not a double pawn move.", capturePos);
                }
            } else {
                log.debug("Capture position is not within chessboard.");
            }
        }
        return captureMoves;
    }
/*
    private Move getEnPassantCaptureMove(@NonNull Position from, @NonNull Board chessboard){
        assert chessboard.isInsideBoard(from) && !chessboard.isEmpty(from) :
                "The 'from' square must be not empty and inside the board.";
    //    Position pawnSkipSquare = chessboard.getPawnSkipSquare(PlayerColor.getOpponentColor(getColor()));
        Position enPassantCapturePosition = chessboard.getPawnSkippedPosition(PlayerColor.getOpponentColor(getColor()));
        if (enPassantCapturePosition != null) {
            if (chessboard.canCaptureEnPassant(getColor())) {
                log.trace("{} can capture en passant on {}", getColor(), from);
                //if (pawnSkipSquare != null) {
                //   return new EnPassantCapture(from, pawnSkipSquare);
                return new EnPassantCapture(from, enPassantCapturePosition);
            }
        }
        log.trace("{} cannot capture en passant from {}", PlayerColor.getOpponentColor(getColor()), from);
        return null;
    }
*/
    @Override
    public boolean canCaptureEnemyKing(@NonNull Position from, @NonNull Board chessboard){
        Collection<Move> captureMovesWithPromotions = getCaptureMovesWithPromotions (from, chessboard);
        for (Move move : captureMovesWithPromotions) {
            Piece targetPiece = chessboard.getPiece(move.getTo());
            if (targetPiece != null && targetPiece.getName().equals(PieceNames.KING)){
                return true;
            }
        }
        return false;
    }

    private boolean isPawnOnStartingSquare(@NonNull Position from, @NonNull PlayerColor color) {
        if (color.equals(PlayerColor.BLACK)) {
            return from.row() == 1;
        } else {
            return from.row() == 6;
        }
    }

    private boolean canStandardCaptureAt(@NonNull Position position, @NonNull Board chessBoard) {
        if (!(chessBoard.isInsideBoard(position))) {
            return false;
        }
        if (chessBoard.isEmpty(position)) {
            return false;
        }
        return !(chessBoard.getPiece(position).getColor()).equals(getColor());
    }

    @Override
    public Collection<Move> getPseudoLegalMoves(Position from, Board chessboard){
        Collection<Move> moves = new ArrayList<>();
        Collection<Move> oneTwoStepForwardMovesWithPromotions = getOneTwoStepsForwardMovesWithPromotions(from, chessboard);
        if (!oneTwoStepForwardMovesWithPromotions.isEmpty()) {
            moves.addAll(oneTwoStepForwardMovesWithPromotions);
        }
        Collection<Move> captureMovesWithPromotions = getCaptureMovesWithPromotions(from, chessboard);
        if (!captureMovesWithPromotions.isEmpty()) {
            moves.addAll(captureMovesWithPromotions);
        }
        return moves;
    }

    /**
     * @return a copy of current pawn.
     */
    @Override
    public Piece copy() {
        Pawn pawnCopy = new Pawn(getColor());
     //   pawnCopy.setPosition(getPosition());
        pawnCopy.setFlaggedAsHavingAlreadyMoved(isFlaggedAsHavingAlreadyMoved());
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