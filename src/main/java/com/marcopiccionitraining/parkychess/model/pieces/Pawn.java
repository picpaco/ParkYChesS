package com.marcopiccionitraining.parkychess.model.pieces;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.*;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.marcopiccionitraining.parkychess.model.PseudoLegalPiecesPositionsInitializer.getPawnCapturePseudoLegalPositions;

@Slf4j
public class Pawn extends Piece {

    private final Direction forward;

    public Pawn (PlayerColor playerColor) {
        super(playerColor);
        if (playerColor.equals(PlayerColor.BLACK)) {
            forward = Direction.SOUTH;
        } else {
            forward = Direction.NORTH;
        }
        setName(PieceNames.PAWN);
        assert isPawnDirectionCorrect();
        log.trace("Created {} pawn", playerColor);
    }

    private boolean isPawnDirectionCorrect(){
        if (getColor().equals(PlayerColor.BLACK) && forward.equals(Direction.SOUTH)
        ) {
            return true;
        }
        return getColor().equals(PlayerColor.WHITE) && forward.equals(Direction.NORTH);
    }

    private List<Move> getForwardMovesAndPromotions(@NonNull Position from, @NonNull Board chessboard) {
        assert isPawnDirectionCorrect() : "Pawn direction is not correct.";
        List<Move> forwardMoves = new ArrayList<>();
        Position firstStepForward = from.stepTowardsDirection(forward);
        if (chessboard.isEmpty(firstStepForward)) {
            log.info("First step forward is {}", firstStepForward);
            if ((firstStepForward.getRow() == 0 && getColor().equals(PlayerColor.WHITE)) ||
                    (firstStepForward.getRow() == 7 && getColor().equals(PlayerColor.BLACK))) {
                log.debug("Adding promotion moves.");
                PawnPromotionMove pawnPromotionToQueenMove = new PawnPromotionMove(from, firstStepForward,
                        new Queen(getColor()), null);
                forwardMoves.add(pawnPromotionToQueenMove);
                PawnPromotionMove pawnPromotionToRookMove = new PawnPromotionMove(from, firstStepForward,
                        new Rook(getColor()), null);
                forwardMoves.add(pawnPromotionToRookMove);
                PawnPromotionMove pawnPromotionToBishopMove = new PawnPromotionMove(from, firstStepForward,
                        new Bishop(getColor()), null);
                forwardMoves.add(pawnPromotionToBishopMove);
                PawnPromotionMove pawnPromotionToKnightMove = new PawnPromotionMove(from, firstStepForward,
                        new Knight(getColor()), null);
                forwardMoves.add(pawnPromotionToKnightMove);
            } else {
                forwardMoves.add(new StandardMove(from, firstStepForward));
                log.debug("Added standard pawn forward move {}.", new StandardMove(from, firstStepForward));
            }
            if (isOnStartingSquare(from, getColor())) {
                DoublePawnMove twoStepsForwardMove;
                Position secondStepForward = firstStepForward.stepTowardsDirection(forward);
                if (chessboard.isEmpty(secondStepForward)) {
                    log.debug("From: {}, To: {}", from, secondStepForward);
                    twoStepsForwardMove = new DoublePawnMove(from, secondStepForward, chessboard);
                    log.debug("Adding a DoublePawnMove: {}", twoStepsForwardMove);
                    forwardMoves.add(twoStepsForwardMove);
                }
            }
        }
        log.debug("Forward moves: {}", forwardMoves);
        return forwardMoves;
    }

    private List<Move> getCaptureMovesAndPromotions(@NonNull Position from, @NonNull Board chessboard) {
        assert isPawnDirectionCorrect() : "Pawn direction is not correct.";
        List<Position> pseudoLegalPawnCapturePositions;
        int lastRowNumber;
        List<Move> captureMoves = new ArrayList<>();
        if (chessboard.getPiece(from).getColor().equals(PlayerColor.BLACK)) {
            pseudoLegalPawnCapturePositions = PseudoLegalPiecesPositionsInitializer.
                    getPawnCapturePseudoLegalPositions(from, PlayerColor.BLACK);
            lastRowNumber = 7;
        } else {
            pseudoLegalPawnCapturePositions = PseudoLegalPiecesPositionsInitializer.
                    getPawnCapturePseudoLegalPositions(from, PlayerColor.WHITE);
            lastRowNumber = 0;
        }
        log.debug("pseudoLegalPawnCapturePositions for {}: {}",
                chessboard.getCurrentPlayerColor(), pseudoLegalPawnCapturePositions);
        for (Position capturePosition : pseudoLegalPawnCapturePositions) {
            if (chessboard.isEmpty(capturePosition)) {
                log.debug("{} cannot perform a standard capture on {} because {} is empty.",
                        chessboard.getCurrentPlayerColor(), capturePosition, capturePosition);
                if ((from.getRow() == 3 && getColor().equals(PlayerColor.WHITE)) ||
                        (from.getRow() == 4 && getColor().equals(PlayerColor.BLACK))){
                    if (capturePosition.equals(chessboard.getPawnSkippedPosition(
                        PlayerColor.getOpponentColor(getColor())))) {
                        if (chessboard.canCaptureEnPassant(chessboard.getCurrentPlayerColor())) {
                            log.debug("Adding an en passant capture from {} to {}.", from, capturePosition);
          //              if (!((chessboard.getPiece(capturePosition)).getColor().equals(
            //                    (chessboard.getPiece(from).getColor())))) {
                            captureMoves.add(new EnPassantCapture(from, capturePosition, chessboard));
                //        } else {
                  //          log.debug("Cannot capture {} (a friendly piece).", chessboard.getPiece(capturePosition));
                    //    }
                        } else {
                            log.debug("Cannot capture en passant on {}. Possible reasons: last executed move is not " +
                                    "the correct double move, or the attacking pawn is not in the right position.", capturePosition);
                        }
                    } else {
                        log.debug("{} cannot capture en passant on {} because {} does not match pawn skipped position: {}.",
                                getColor(), capturePosition, capturePosition,
                                chessboard.getPawnSkippedPosition(PlayerColor.getOpponentColor(getColor())));
                    }
                } else {
                    log.debug("{} cannot capture en passant on {} because the attacking pawn is not on the correct row.",
                            getColor(), capturePosition);
                }
            } else {
                if (!((chessboard.getPiece(capturePosition).getColor()).equals(
                        (chessboard.getPiece(from).getColor())))) {
                    if (capturePosition.getRow() == lastRowNumber) {
                        log.debug("Capturing with promotion.");
                        PawnPromotionMove pawnPromotionToQueenMove = new PawnPromotionMove(from, capturePosition,
                                new Queen(getColor()), chessboard.getPiece(capturePosition));
                        captureMoves.add(pawnPromotionToQueenMove);
                        PawnPromotionMove pawnPromotionToRookMove = new PawnPromotionMove(from, capturePosition,
                                new Rook(getColor()), chessboard.getPiece(capturePosition));
                        captureMoves.add(pawnPromotionToRookMove);
                        PawnPromotionMove pawnPromotionToBishopMove = new PawnPromotionMove(from, capturePosition,
                                new Bishop(getColor()), chessboard.getPiece(capturePosition));
                        captureMoves.add(pawnPromotionToBishopMove);
                        PawnPromotionMove pawnPromotionToKnightMove = new PawnPromotionMove(from, capturePosition,
                                new Knight(getColor()), chessboard.getPiece(capturePosition));
                        captureMoves.add(pawnPromotionToKnightMove);
                    } else {
                        StandardMove standardMove = new StandardMove(from, capturePosition);
                        log.debug("Regular pawn capture: {}. A {} {} captures a {} {}.", standardMove,
                                chessboard.getPiece(from).getColor(), chessboard.getPiece(from).getName(),
                                chessboard.getPiece(capturePosition).getColor(),
                                chessboard.getPiece(capturePosition).getName());
                        captureMoves.add(standardMove);
                    }
                } else {
                    log.debug("Cannot capture friendly piece.");
                }
            }
        }
        log.info("Capture moves at getCaptureMovesAndPromotions exit: {}.", captureMoves);
        return captureMoves;
    }

    public boolean isOnStartingSquare (@NonNull Position from, @NonNull PlayerColor color) {
        assert isPawnDirectionCorrect() : "Pawn direction is not correct.";
        if (color.equals(PlayerColor.BLACK)) {
            return from.getRow() == 1;
        } else {
            return from.getRow() == 6;
        }
    }

    @Override
    public List<Move> getPseudoLegalMoves (Position from, Board chessboard){
        List<Move> moves = new ArrayList<>();
        assert isPawnDirectionCorrect() : "Pawn direction is not correct.";
        List<Move> oneTwoStepForwardMovesWithPromotions = getForwardMovesAndPromotions(from, chessboard);
        if (oneTwoStepForwardMovesWithPromotions.isEmpty()) {
            log.debug("There are no pseudolegal non-capture pawn moves inclusive of promotions to add for {}.",this);
        }  else {
            log.debug("Pseudolegal non-capture pawn moves inclusive of promotions for {}: {}",
                    this, oneTwoStepForwardMovesWithPromotions);
            moves.addAll(oneTwoStepForwardMovesWithPromotions);
        }
        List<Move> captureMovesWithPromotions = getCaptureMovesAndPromotions(from, chessboard);
        if (captureMovesWithPromotions.isEmpty()) {
            log.debug("There are no pseudolegal capture pawn moves inclusive of en passant and promotions to add for {}", this);
        }
        moves.addAll(captureMovesWithPromotions);
        log.info("Moves returned by getPseudolegalMoves({}) in class Pawn: {}", from, moves);
        return moves;
    }

    @Override
    public boolean canCaptureOpponentKing (@NonNull Position from, @NonNull Board chessboard) {
        assert isPawnDirectionCorrect() : "Pawn direction is not correct.";
        assert chessboard.getPiece(from) == null || chessboard.getPiece(from).getName().equals(PieceNames.PAWN) :
            "If there is a piece on " + from + ", it must be a pawn.";
        log.trace("Pawn on {}: {}", from,chessboard.getPiece(from));
        Collection<Position> capturePositions = getPawnCapturePseudoLegalPositions (from, chessboard.getPiece(from).getColor());
        for (Position capturePosition : capturePositions) {
            if (chessboard.isEmpty(capturePosition)) {
                continue;
            }
            if (chessboard.getPiece(capturePosition).getColor().equals(chessboard.getPiece(from).getColor())) {
                continue;
            }
            Move captureMove = new StandardMove(from, capturePosition);
            if (chessboard.getPiece(captureMove.getTo()).getName().equals(PieceNames.KING)) {
                return true;
            }
        }
        return false;
    }
        /**
         * @return a copy of current pawn.
         */
        @Override
        public Piece copy () {
            assert isPawnDirectionCorrect() : "Pawn direction is not correct.";
            Pawn pawnCopy = new Pawn(getColor());
            pawnCopy.setFlaggedAsHavingAlreadyMoved(isFlaggedAsHavingAlreadyMoved());
            return pawnCopy;
        }
}