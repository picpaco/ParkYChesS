package com.marcopiccionitraining.parkychess.model.moves;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.pieces.King;
import com.marcopiccionitraining.parkychess.model.pieces.PieceNames;
import com.marcopiccionitraining.parkychess.model.pieces.Rook;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
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

    public CastlingMove (@NonNull MoveNames moveName, @NonNull Position kingFromPosition){
        assert moveName.equals(MoveNames.KINGSIDE_CASTLE) || moveName.equals(MoveNames.QUEENSIDE_CASTLE) :
                "The allowed names for this move are: KINGSIDE_CASTLE and QUEENSIDE_CASTLE";
        assert (kingFromPosition.getRow() == 0 || kingFromPosition.getRow() == 7 ) &&
                (kingFromPosition.getColumn() == 4) :
                "The king cannot perform move " + moveName + " because it is mispositioned: " + kingFromPosition;
        setName(moveName);
        setFrom(kingFromPosition);
        if (getName().equals(MoveNames.KINGSIDE_CASTLE)){
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
    public boolean execute (@NonNull Board chessboard) {
        assert (chessboard.getPiece(getFrom()).getName()).equals(PieceNames.KING) :
                "There should be a king in the from position";
        assert (chessboard.getPiece(rookFromPosition).getName()).equals(PieceNames.ROOK) :
                "There should be a rook in the rookFrom position";
     /*   PlayerColor playerColor = chessboard.getCurrentPlayerColor();
        King king = chessboard.getKing(playerColor);
        Rook rook;
        if (chessboard.getCurrentPlayerColor().equals(PlayerColor.BLACK)) {
            if (getName().equals(MoveNames.KINGSIDE_CASTLE)) {
                rook = (Rook) chessboard.getPiece(0,7);
            } else {
                if (getName().equals(MoveNames.QUEENSIDE_CASTLE)) {
                    rook = (Rook) chessboard.getPiece(0,0);
                }
            }
        } else {
            if (getName().equals(MoveNames.KINGSIDE_CASTLE)) {
                rook = (Rook) chessboard.getPiece(7,7);
            } else {
                if (getName().equals(MoveNames.QUEENSIDE_CASTLE)) {
                    rook = (Rook) chessboard.getPiece(7,0);
                }
            }
        } */
        kingStandardMove = new StandardMove (getFrom(), getTo());
        log.info("Executing castling part 1: moving king from {} to {}...", getFrom(), getTo());
        kingStandardMove.execute(chessboard);
        rookStandardMove = new StandardMove (rookFromPosition, rookToPosition);
        log.info("Executing castling part 2: moving rook from {} to {}...", rookFromPosition, rookToPosition);
        rookStandardMove.execute(chessboard);
        log.info("Castling executed.");
        log.info("Immediately after castling: {}", chessboard);
        return false;
    }

    @Override
    public void undo (@NonNull Board chessboard) {
        assert chessboard.isEmpty(rookFromPosition): "Original rook's from position should be empty before undoing this move.";
        assert chessboard.isEmpty(getFrom()): "Original king's from position should be empty before undoing this move.";
        log.debug("King position: ({}, {}); Rook position: ({}, {})", getFrom(), getTo(), rookFromPosition, rookToPosition);
        kingStandardMove.undo(chessboard);
        rookStandardMove.undo(chessboard);
        Rook rook = (Rook)chessboard.getPiece(getRookFromPosition());
        King king = chessboard.getKing(rook.getColor());
        chessboard.setPiece(king, getFrom());
        chessboard.setEmpty(getTo());
    }

    /** Conditions that must hold in order to declare a castling move legal:
    // 1. Castling must be a pseudolegal move:
    // 1.1. Castling must be allowed by FEN, or
    // 1.1.1. King and rook should be in their home square, and
    // 1.1.2. Path between king and rook must be free of pieces, and
    // 1.1.3. King and rook must have not moved before.
    // 2. King must not be under check.
    // 3. Path between king and rook must not be under check.
    */
    @Override
    public boolean isLegal (@NonNull Board chessboard) {
        PlayerColor kingColor = chessboard.getPiece(getFrom()).getColor();
        if (chessboard.isPlayerInCheck(kingColor)) {
            log.debug("{} for {} is illegal when under check).", getName(), kingColor);
            return false;
        }
        assert chessboard.getPiece(getFrom()) != null : "There must be a piece in " + getFrom();
        log.debug("kingCastlingSide: {}", kingCastlingSide);
        Position firstCastlingKingStep = getFrom().stepTowardsDirection(kingCastlingSide);
        log.debug("Performing first castling king step on a separate chessboard and checking that the king is not in check...");
        new StandardMove(getFrom(), firstCastlingKingStep).execute(chessboard);
        if (chessboard.isPlayerInCheck(kingColor)) {
            log.trace("{} is illegal because {} would be in check while passing on the first square of the castling path.",
                    getName(), kingColor);
            return false;
        }
        Position secondCastlingKingStep = firstCastlingKingStep.stepTowardsDirection(kingCastlingSide);
        log.debug("Performing second castling king step on a separate chessboard and checking if there is a discovery check.");
        new StandardMove(firstCastlingKingStep, secondCastlingKingStep).execute(chessboard);
        if (chessboard.isPlayerInCheck(kingColor)) {
            log.trace("{} is illegal because {} would be in check after performing the second step of the castling path.",
                    getName(), kingColor);
            return false;
        }
        log.debug("Castling move {} is legal.", this);
        return true;
    }
}