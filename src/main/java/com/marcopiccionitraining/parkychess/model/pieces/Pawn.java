package com.marcopiccionitraining.parkychess.model.pieces;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.*;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

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
        if (getColor().equals(PlayerColor.BLACK) && forward.equals(Direction.SOUTH)) {
            return true;
        }
        return getColor().equals(PlayerColor.WHITE) && forward.equals(Direction.NORTH);
    }

    public List<Move> getPseudolegalForwardMovesAndPromotions(@NonNull Position from, @NonNull Board chessboard) {
        assert isPawnDirectionCorrect() : "Pawn direction is not correct.";
        log.debug("Invoked getPseudolegalForwardMovesAndPromotions for {}", this);
        List<Move> forwardMoves = new ArrayList<>();
        Position firstStepForward = from.stepTowardsDirection(forward);
        if (chessboard.isEmpty(firstStepForward)) {
            log.debug("First step forward is {}", firstStepForward);
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
                if (isOnStartingSquare(from, getColor())) {
                    DoublePawnMove twoStepsForwardMove;
                    Position secondStepForward = firstStepForward.stepTowardsDirection(forward);
                    if (chessboard.isEmpty(secondStepForward)) {
                        twoStepsForwardMove = new DoublePawnMove(from, secondStepForward, chessboard);
                        log.debug("Adding a DoublePawnMove: {} to the possible forward moves.", twoStepsForwardMove);
                        forwardMoves.add(twoStepsForwardMove);
                    }
                }
            }
        }
        log.debug("Forward moves: {}", forwardMoves);
        return forwardMoves;
    }

    public List<Move> getPseudolegalCaptureMovesAndPromotions(@NonNull Position from, @NonNull Board chessboard) {
        //FIXME: reflection: this method is invoked twice via polymorphic calls with both current color black and white.
        //FIXME Problem: it is needed by pawns of the opposite color when checking the legality of a move:
        // solved with adding a parameter color to the call.
        //FIXME: Example: White: Ke1, Pc4, Pd4; Black: ke8, pc5, pd5. White to move.
        //FIXME: White has 2 possible pseudolegal moves: c4d5 and d4c5.
        //FIXME: To check if c4d5 is legal consider the possible answers for black:
        // ke8d8, ke8d7, ke8e7, ke8f8, ke8f7, c5c4, c5d4. None of these captures the white king on e1,
        // therefore c4d5 is legal.
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
                chessboard.getPiece(from).getColor(), pseudoLegalPawnCapturePositions);
        for (Position capturePosition : pseudoLegalPawnCapturePositions) {
            if (chessboard.isEmpty(capturePosition)) {
                log.debug("{} cannot perform a standard capture on {} because it is empty.",
                        chessboard.getCurrentPlayerColor(), capturePosition);
                      //  chessboard.getPiece(from).getColor(), capturePosition);
                if ((from.getRow() == 3 && chessboard.getCurrentPlayerColor().equals(PlayerColor.WHITE)) ||
                        (from.getRow() == 4 && chessboard.getCurrentPlayerColor().equals(PlayerColor.BLACK))){
                    if (capturePosition.equals(chessboard.getPawnSkippedPosition(
                            PlayerColor.getOpponentColor(chessboard.getPiece(from).getColor())))) {
                        if (chessboard.canCaptureEnPassant(chessboard.getPiece(from).getColor())) {
                            log.debug("Adding an en passant capture from {} to {}.", from, capturePosition);
                            captureMoves.add(new EnPassantCapture(from, capturePosition, chessboard));
                        } else {
                            log.debug("Cannot capture en passant on {}. Possible reasons: last executed move is not " +
                                    "the correct double move, or the attacking pawn is not in the right position.", capturePosition);
                        }
                    } else {
                        log.debug("{} cannot capture en passant on {} because {} does not match pawn skipped position: {}.",
                                chessboard.getPiece(from).getColor(), capturePosition, capturePosition,
                                chessboard.getPawnSkippedPosition(PlayerColor.getOpponentColor(chessboard.getPiece(from).getColor())));
                    }
                } else {
                    log.debug("{} cannot capture en passant on {} because the attacking pawn is not on the correct row.",
                            chessboard.getPiece(from).getColor(), capturePosition);
                }
            } else {
                if (!((chessboard.getPiece(capturePosition).getColor()).equals(
                        (chessboard.getPiece(from).getColor())))) {
                    if (capturePosition.getRow() == lastRowNumber) {
                        log.debug("Capturing with promotion.");
                        PawnPromotionMove pawnPromotionToQueenMove = new PawnPromotionMove(from, capturePosition,
                                new Queen(chessboard.getPiece(from).getColor()), chessboard.getPiece(capturePosition));
                        captureMoves.add(pawnPromotionToQueenMove);
                        PawnPromotionMove pawnPromotionToRookMove = new PawnPromotionMove(from, capturePosition,
                                new Rook(chessboard.getPiece(from).getColor()), chessboard.getPiece(capturePosition));
                        captureMoves.add(pawnPromotionToRookMove);
                        PawnPromotionMove pawnPromotionToBishopMove = new PawnPromotionMove(from, capturePosition,
                                new Bishop(chessboard.getPiece(from).getColor()), chessboard.getPiece(capturePosition));
                        captureMoves.add(pawnPromotionToBishopMove);
                        PawnPromotionMove pawnPromotionToKnightMove = new PawnPromotionMove(from, capturePosition,
                                new Knight(chessboard.getPiece(from).getColor()), chessboard.getPiece(capturePosition));
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
        log.debug("Capture moves at getPseudolegalCaptureMovesAndPromotions exit: {}.", captureMoves);
        return captureMoves;
    }

    /*
    public List<Move> getPseudolegalCaptureMovesAndPromotions(@NonNull Position from, @NonNull Board chessboard) {
        //FIXME: reflection: this method is invoked twice via polymorphic calls with both current color black and white.
        //FIXME Problem: it is needed by pawns of the opposite color when checking the legality of a move:
        // solved with adding a parameter color to the call.
        //FIXME: Example: White: Ke1, Pc4, Pd4; Black: ke8, pc5, pd5. White to move.
        //FIXME: White has 2 possible pseudolegal moves: c4d5 and d4c5.
        //FIXME: To check if c4d5 is legal consider the possible answers for black:
        // ke8d8, ke8d7, ke8e7, ke8f8, ke8f7, c5c4, c5d4. None of these captures the white king on e1,
        // therefore c4d5 is legal.
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
                            captureMoves.add(new EnPassantCapture(from, capturePosition, chessboard));
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
        log.debug("Capture moves at getPseudolegalCaptureMovesAndPromotions exit: {}.", captureMoves);
        return captureMoves;
    }
*/
    public boolean isOnStartingSquare (@NonNull Position from, @NonNull PlayerColor color) {
        assert isPawnDirectionCorrect() : "Pawn direction is not correct.";
        if (color.equals(PlayerColor.BLACK)) {
            return from.getRow() == 1;
        } else {
            return from.getRow() == 6;
        }
    }

    @Override
    public List<Move> getPseudoLegalMoves (@NotNull Position from, @NotNull Board chessboard){
        assert isPawnDirectionCorrect() : "Pawn direction is not correct.";
        List<Move> moves = getPseudolegalForwardMovesAndPromotions(from, chessboard);
        moves.addAll(getPseudolegalCaptureMovesAndPromotions(from, chessboard));
        log.debug("Moves returned by getPseudolegalMoves({}) in class Pawn: {}", from, moves);
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