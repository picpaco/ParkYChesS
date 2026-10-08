package com.marcopiccionitraining.parkychess.model.moves;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.pieces.King;
import com.marcopiccionitraining.parkychess.model.pieces.PieceNames;
import com.marcopiccionitraining.parkychess.model.pieces.Rook;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CastlingMove extends Move {

    private final Direction kingCastlingSide;
    @Getter
    private final Position rookFromPosition;
    @Getter
    private final Position rookToPosition;
    @Getter
    private StandardMove kingStandardMove;
    @Getter
    private StandardMove rookStandardMove;

    public CastlingMove(@NonNull MoveNames moveName, @NonNull Position kingFromPosition) {
        log.debug("Castling Move {} created.", moveName);
        assert moveName.equals(MoveNames.KINGSIDE_CASTLE) || moveName.equals(MoveNames.QUEENSIDE_CASTLE) :
                "The allowed names for this move are: KINGSIDE_CASTLE and QUEENSIDE_CASTLE";
        assert (kingFromPosition.getRow() == 0 || kingFromPosition.getRow() == 7) &&
                (kingFromPosition.getColumn() == 4) :
                "The king cannot perform move " + moveName + " because it is mispositioned: " + kingFromPosition;
        setName(moveName);
        setFrom(kingFromPosition);
        if (getName().equals(MoveNames.KINGSIDE_CASTLE)) {
            kingCastlingSide = Direction.EAST;
            setTo(new Position(kingFromPosition.getRow(), 6));
            rookFromPosition = new Position(kingFromPosition.getRow(), 7);
            rookToPosition = new Position(kingFromPosition.getRow(), 5);
        } else {
            kingCastlingSide = Direction.WEST;
            setTo(new Position(kingFromPosition.getRow(), 2));
            rookFromPosition = new Position(kingFromPosition.getRow(), 0);
            rookToPosition = new Position(kingFromPosition.getRow(), 3);
        }
        assert getFrom().equals(kingFromPosition) : "The king must be on " + kingFromPosition;
    }

    @Override
    public boolean execute(@NonNull Board chessboard) {
        assert (chessboard.getPiece(getFrom()).getName()).equals(PieceNames.KING) :
                "There should be a king in the from position";
        assert (chessboard.getPiece(rookFromPosition).getName()).equals(PieceNames.ROOK) :
                "There should be a rook in the rookFrom position";
        log.error("Executing castling move {}.", getName());
        kingStandardMove = new StandardMove(getFrom(), getTo());
        log.error("Executing castling part 1: moving king from {} to {}...", getFrom(), getTo());
        kingStandardMove.execute(chessboard);
        rookStandardMove = new StandardMove(rookFromPosition, rookToPosition);
        log.error("Executing castling part 2: moving rook from {} to {}...", rookFromPosition, rookToPosition);
        rookStandardMove.execute(chessboard);
        if (getName().equals(MoveNames.KINGSIDE_CASTLE) &&
                (chessboard.getPiece(getTo()).getColor()).equals(PlayerColor.BLACK) ) {
            chessboard.setBlackKingsideCastlingPossibleFromFEN(false);
            chessboard.setBlackKingPosition(getTo());
        }
        if (getName().equals(MoveNames.KINGSIDE_CASTLE) &&
                (chessboard.getPiece(getTo()).getColor()).equals(PlayerColor.WHITE) ) {
            chessboard.setWhiteKingsideCastlingPossibleFromFEN(false);
            chessboard.setWhiteKingPosition(getTo());
        }
        if (getName().equals(MoveNames.QUEENSIDE_CASTLE) &&
                (chessboard.getPiece(getTo()).getColor()).equals(PlayerColor.BLACK) ) {
            chessboard.setBlackQueensideCastlingPossibleFromFEN(false);
            chessboard.setBlackKingPosition(getTo());
        }
        if (getName().equals(MoveNames.QUEENSIDE_CASTLE) && (chessboard.getPiece(getTo()).getColor()).equals(PlayerColor.WHITE) ) {
            chessboard.setWhiteQueensideCastlingPossibleFromFEN(false);
            chessboard.setWhiteKingPosition(getTo());
        }
        log.error("Castling executed.");
        return false;
    }

    @Override
    public void undo(@NonNull Board chessboard, LocalGameState localGameState) {
        assert chessboard.isEmpty(rookFromPosition) : "Original rook's 'from' position should be empty before undoing this move.";
        assert chessboard.isEmpty(getFrom()) : "Original king's 'from' position should be empty before undoing this move.";
        log.debug("Before castling undo: king is in {}; rook is in {}.", getTo(), rookToPosition);
        Rook rook = (Rook) chessboard.getPiece(getRookToPosition());
        King king = chessboard.getKing(rook.getColor());
        chessboard.setPiece(king, getFrom());
        chessboard.setEmpty(getTo());
        chessboard.setPiece(rook, rookFromPosition);
        chessboard.setEmpty(rookToPosition);
        chessboard.setBlackKingsideCastlingPossibleFromFEN(localGameState.isBlackKingsideCastlingPossible());
        chessboard.setBlackQueensideCastlingPossibleFromFEN(localGameState.isBlackQueensideCastlingPossible());
        chessboard.setWhiteKingsideCastlingPossibleFromFEN(localGameState.isWhiteKingsideCastlingPossible());
        chessboard.setWhiteQueensideCastlingPossibleFromFEN(localGameState.isWhiteQueensideCastlingPossible());
        chessboard.setBlackKingPosition(getFrom());
        chessboard.setWhiteKingPosition(getFrom());
        log.debug("After castling undo: king is in {}; rook is in {}.", getFrom(), rookFromPosition);
    }

    /**
     * Conditions that must hold in order to declare a castling move legal:
     * // 1. Castling must be a pseudolegal move:
     * // 1.1. Castling must be allowed by FEN, or
     * // 1.1.1. King and rook should be in their home square, and
     * // 1.1.2. Path between king and rook must be free of pieces, and
     * // 1.1.3. King and rook must have not moved before.
     * // 2. King must not be under check.
     * // 3. Path between king and rook must not be under check.
     */
    @Override
    public boolean isLegal(@NonNull Board chessboard) {
        PlayerColor kingColor = (chessboard.getPiece(getFrom())).getColor();
        log.error("Checking if castling move {} is legal:", this);
        if (chessboard.isPlayerInCheck(kingColor)) {
            log.error("{} for {} is illegal when under check).", getName(), kingColor);
            return false;
        }
        assert !(chessboard.isEmpty(getFrom())) : "There must be a piece in " + getFrom();
        log.debug("kingCastlingSide: {}", kingCastlingSide);
        LocalGameState localGameState1 = new LocalGameState(chessboard);
        Position firstCastlingKingStep = getFrom().stepTowardsDirection(kingCastlingSide);
        StandardMove kingStep1 = new StandardMove(getFrom(), firstCastlingKingStep);
        kingStep1.execute(chessboard);
        if (chessboard.isPlayerInCheck(kingColor)) {
            log.error("{} is illegal because {} would be in check while passing over the first square of the castling path.",
                    getName(), kingColor);
            kingStep1.undo(chessboard, localGameState1);
            return false;
        }
        Position secondCastlingKingStep = firstCastlingKingStep.stepTowardsDirection(kingCastlingSide);
        log.debug("Performing the second castling king step and checking if there is a check.");
        StandardMove kingStep2 = new StandardMove(firstCastlingKingStep, secondCastlingKingStep);
        kingStep2.execute(chessboard);
        if (chessboard.isPlayerInCheck(kingColor)) {
            log.error("{} is illegal because {} would be in check after performing the second castling step of the path.",
                    getName(), kingColor);
            kingStep2.undo(chessboard, localGameState1);
            kingStep1.undo(chessboard, localGameState1);
            return false;
        }
        StandardMove rookMove = new StandardMove(rookFromPosition, rookToPosition);
        rookMove.execute(chessboard);
        if (chessboard.isPlayerInCheck(kingColor)) {
            log.error("{} is illegal because {} would be in check after performing the final castling rook move.",
                    getName(), kingColor);
            return false;
        }
        rookMove.undo(chessboard, localGameState1);
        kingStep2.undo(chessboard, localGameState1);
        kingStep1.undo(chessboard, localGameState1);
        log.error("{} is legal.", getName());
        return true;
    }
}