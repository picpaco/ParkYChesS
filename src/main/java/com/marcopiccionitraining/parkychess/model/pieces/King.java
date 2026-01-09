package com.marcopiccionitraining.parkychess.model.pieces;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.CastlingMove;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import com.marcopiccionitraining.parkychess.model.moves.MoveNames;
import com.marcopiccionitraining.parkychess.model.moves.StandardMove;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
@Slf4j
public class King extends Piece {

    public final Direction[] allowedDirections = new Direction[]{
            Direction.NORTH, Direction.WEST, Direction.SOUTH, Direction.EAST,
            Direction.NORTH_EAST, Direction.NORTH_WEST, Direction.SOUTH_EAST, Direction.SOUTH_WEST
    };

    public King (PlayerColor playerColor){
        super(playerColor);
        setName(PieceNames.KING);
    }

    private boolean hasRookNeverMoved (@NonNull Position rookPosition, @NonNull Board chessboard) {
        if (chessboard.isEmpty(rookPosition)) {
            log.trace("The rook is not at its initial square {} .", rookPosition);
            return false;
        }
        Piece piece = chessboard.getPiece(rookPosition);
        if (piece.getName().equals(PieceNames.ROOK)) {
            Rook rook = (Rook) piece;
            if (rook.isFlaggedAsHavingAlreadyMoved()){
                log.trace("The rook at {} is flagged as having already moved.", rookPosition);
                return false;
            } else {
                return true;
            }
        } else {
            log.trace("The piece at {} is not a rook. It is a {}.", rookPosition, piece.getName());
            return false;
        }
    }

    private boolean isCastlingPathFree (@NonNull Collection<Position> positions, @NonNull Board chessboard){
        for (Position pos : positions) {
            if (!(chessboard.isEmpty(pos))){
                log.trace("The castling path made of {} is not free.", positions);
                return false;
            }
        }
        return true;
    }

    public boolean isKingsideCastlingPseudoLegal(Position from, Board chessboard){
        if (getColor() == PlayerColor.BLACK){
            if (!chessboard.isBlackKingsideCastlingPossibleFromFEN()){
                log.debug("Kingside castling is not possible for Black because FEN prohibits it.");
                return false;
            }
        } else {
                if (!chessboard.isWhiteKingsideCastlingPossibleFromFEN()){
                    log.debug("Kingside castling is not possible for White because FEN prohibits it.");
                    return false;
            }
        }
        if (isKingPositionIncorrectForCastling(from)) {
            log.debug("Kingside castling is not possible for {} because the king is on {}. ", getColor(), from);
            return false;
        }
        if (isFlaggedAsHavingAlreadyMoved()){
            log.debug("Kingside castling is not possible for {} because the king has already moved", getColor());
            return false;
        }
        Position castlingRookInitialPosition = new Position(from.row(), 7);
        if (chessboard.isEmpty(castlingRookInitialPosition)){
            log.debug("Kingside castling is not possible for {} because the rook is not on its home square {}. ",
                    getColor(), castlingRookInitialPosition);
            return false;
        }
        Piece maybeRook = chessboard.getPiece(castlingRookInitialPosition);
        if (!(maybeRook.getName().equals(PieceNames.ROOK))){
            log.debug("Kingside castling is not possible for {} because there is a {} on {}. ",
                    getColor(), getName(), castlingRookInitialPosition);
            return false;
        }
        if (maybeRook.isFlaggedAsHavingAlreadyMoved()){
            log.debug("Kingside castling is not possible for {} because the rook has already moved", getColor());
            return false;
        }
        ArrayList<Position> positionsInBetween = new ArrayList<>();
        positionsInBetween.add(new Position(from.row(), 5));
        positionsInBetween.add(new Position(from.row(), 6));
        return (hasRookNeverMoved(castlingRookInitialPosition, chessboard)) &&
                (isCastlingPathFree(positionsInBetween, chessboard));
    }

    public boolean isQueensideCastlingPseudoLegal(Position from, Board chessboard) {
        if (getColor() == PlayerColor.BLACK){
            if (!chessboard.isBlackQueensideCastlingPossibleFromFEN()){
                log.trace("Queenside castling is not possible for Black because FEN forbids it.");
                return false;
            }
        } else {
            if (getColor() == PlayerColor.WHITE) {
                if (!chessboard.isWhiteQueensideCastlingPossibleFromFEN()) {
                    log.trace("Queenside castling is not possible for White because FEN prohibits it. ");
                    return false;
                }
            }
        }
        if (isKingPositionIncorrectForCastling(from)) {
            log.trace("Queenside castling is not possible for {} because the king is on {}. ", getColor(), from);
            return false;
        }
        if (isFlaggedAsHavingAlreadyMoved()){
            log.trace("Queenside castling is not possible for {} because the king has already moved", getColor());
            return false;
        }
        Position castlingRookInitialPosition = new Position(from.row(), 0);
        if (chessboard.isEmpty(castlingRookInitialPosition)) {
            log.trace("Queenside castling is not possible for {} because there is a {} on {}. ",
                    getColor(), getName(), castlingRookInitialPosition);
            return false;
        }
        if (!(chessboard.getPiece(castlingRookInitialPosition).getName().equals(PieceNames.ROOK))) {
            log.trace("Queenside castling is not permitted for {} because there is a {} on {}. ",
                    getColor(), getName(), castlingRookInitialPosition);
            return false;
        }
        ArrayList<Position> positionsBetween = new ArrayList<>();
        positionsBetween.add(new Position(from.row(), 1));
        positionsBetween.add(new Position(from.row(), 2));
        positionsBetween.add(new Position(from.row(), 3));
        return (hasRookNeverMoved(castlingRookInitialPosition, chessboard)) &&
                (isCastlingPathFree(positionsBetween, chessboard));
    }

    private boolean isKingPositionIncorrectForCastling(Position from) {
        if ((getColor().equals(PlayerColor.BLACK)) && ((from.row() != 0) || (from.column() != 4))) {
            return true;
        }
        return (getColor().equals(PlayerColor.WHITE)) && ((from.row() != 7) || (from.column() != 4));
    }
    /** Conditions that must hold in order to declare a king move 'pseudolegal':
     * 1. A valid king move (within the chessboard) to an empty square or a square occupied by
     * an opposite color piece. It's OK if the king ends up with being under check.
     * 2. A Castling move must be allowed by the initial FEN (if applicable), or
     * 2.1. King and rook must be on their home square, and
     * 2.2. Path between king and rook must be free of pieces, and
     * 2.3. King and rook must not have moved before.
     */
    @Override
    public Collection<Move> getPseudoLegalMoves (Position from, Board chessboard){
        Collection<Move> pseudoLegalMoves = new ArrayList<>();
        for (Direction direction : allowedDirections) {
            Position to = from.stepTowardsDirection(direction);
            if (to != null) {
                if (!(chessboard.isInsideBoard(to))) {
                    continue;
                }
                if (chessboard.isEmpty(to) || !(chessboard.getPiece(to).getColor()).equals(getColor())) {
                    pseudoLegalMoves.add(new StandardMove(from, to));
                }
            }
        }
        boolean isKingsideCastlingPseudoLegal = isKingsideCastlingPseudoLegal(from, chessboard);
        log.trace("Is {} kingside castle pseudolegal in this position? {}", getColor(), isKingsideCastlingPseudoLegal);
        if (isKingsideCastlingPseudoLegal){
            pseudoLegalMoves.add(new CastlingMove(MoveNames.KINGSIDE_CASTLE, from));
        }
        boolean isQueensideCastlingPseudoLegal = isQueensideCastlingPseudoLegal(from, chessboard);
        log.trace("Is {} queenside castle pseudolegal in this position? {}", getColor(), isQueensideCastlingPseudoLegal);
        if (isQueensideCastlingPseudoLegal){
            pseudoLegalMoves.add(new CastlingMove(MoveNames.QUEENSIDE_CASTLE, from));
        }
        log.debug("Pseudolegal moves for {} king {} {}", getColor(), from, pseudoLegalMoves);
        return  pseudoLegalMoves;
    }

    @Override
    public boolean canCaptureEnemyKing(@NonNull Position from, @NonNull Board chessboard) {
        return false;
    }

    @Override
    public Piece copy() {
        King kingCopy = new King(getColor());
        kingCopy.setPosition(new Position(getPosition().row(), getPosition().column()));
        kingCopy.setFlaggedAsHavingAlreadyMoved(isFlaggedAsHavingAlreadyMoved());
        return kingCopy;
    }
}