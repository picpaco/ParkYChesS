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

    private PawnPromotionMove pawnPromotionMove;

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
        if (getColor().equals(PlayerColor.BLACK) && forward.equals(Direction.SOUTH)) {
            return true;
        }
        return getColor().equals(PlayerColor.WHITE) && forward.equals(Direction.NORTH);
    }

    private List<Move> getForwardMovesAndPromotions(@NonNull Position from, @NonNull Board chessboard) {
        assert isPawnDirectionCorrect() : "Pawn direction is not correct.";
        List<Move> forwardMoves = new ArrayList<>();
        Position firstStepForward = from.stepTowardsDirection(forward);
        if (chessboard.isEmpty(firstStepForward)) {
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
                log.debug("Added standard pawn forward move {}.", firstStepForward);
                forwardMoves.add(new StandardMove(from, firstStepForward));
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
        log.debug("Entering pseudoLegalPawnCapturePositions loop.");
        for (Position capturePosition : pseudoLegalPawnCapturePositions) {
            if (chessboard.isEmpty(capturePosition)) {
                log.debug("Cannot perform a standard capture on {} because it is empty.", capturePosition);
                if (capturePosition.equals(chessboard.getPawnSkippedPosition(PlayerColor.getOpponentColor(getColor())))) {
                    if (chessboard.canCaptureEnPassant(chessboard.getCurrentPlayerColor())) {
                        log.debug("Adding an en passant capture from {} to {}.", from, capturePosition);
                        captureMoves.add(new EnPassantCapture(from, capturePosition, chessboard));
                    } else {
                        log.debug("Cannot capture en passant on {} .", capturePosition);
                    }
                } else {
                    log.debug("Cannot capture en passant because capture position: {} is different from pawn skipped " +
                            " position: {}.", capturePosition, chessboard.getPawnSkippedPosition(getColor()));
                }
            } else {//there is a piece on capturePosition
                if (chessboard.getPiece(capturePosition).getColor().equals(chessboard.getPiece(from).getColor())) {
                    log.debug("Cannot capture {} because it is a friendly piece.", chessboard.getPiece(capturePosition));
                    continue;
                }
                if (capturePosition.getRow() == lastRowNumber) {
                    log.debug("Capturing with promotion.");
                    if (!(chessboard.isEmpty(capturePosition))) {
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
                    }
                } else {
                    log.debug("Regular pawn capturing.");
                    captureMoves.add(new StandardMove(from, capturePosition));
                }
            }
        }
        log.debug("Capture moves at getCaptureMovesAndPromotions exit: {}.", captureMoves);
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
        log.debug("Moves returned by getPseudolegalMoves({}): {}", from, moves);
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