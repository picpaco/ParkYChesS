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
    private Direction kingCastlingSide;
    @Getter
    private Position rookFromPosition;
    @Getter
    private Position rookToPosition;

    public CastlingMove (@NonNull MoveNames moveName, @NonNull Position kingPosition){
        assert (kingPosition.row() == 0 || kingPosition.row() == 7 ) && kingPosition.column() == 4 :
                "The king cannot perform move " + moveName + " because it is positioned on the wrong square: " + kingPosition;
        assert moveName.equals(MoveNames.KINGSIDE_CASTLE) || moveName.equals(MoveNames.QUEENSIDE_CASTLE) :
                "This move has a wrong name.";

        setName(moveName);
        setFrom(kingPosition);
        if (getName().equals(MoveNames.KINGSIDE_CASTLE)){
            kingCastlingSide = Direction.EAST;
            setTo(new Position(kingPosition.row(), 6));
            rookFromPosition = new Position(kingPosition.row(), 7);
            rookToPosition = new Position(kingPosition.row(), 5);
        } else {
            if (getName().equals(MoveNames.QUEENSIDE_CASTLE)) {
                kingCastlingSide = Direction.WEST;
                setTo(new Position(kingPosition.row(), 2));
                rookFromPosition = new Position(kingPosition.row(), 0);
                rookToPosition = new Position(kingPosition.row(), 3);
            }
        }
        assert getFrom() != null : "The from position must exist when castling.";
        assert getFrom().equals(kingPosition) : "The king must be in " + kingPosition;
        assert getTo() != null : "The to position must exist when castling.";
    }

    @Override
    public boolean execute (@NonNull Board chessboard) {
        assert getFrom() != null && (chessboard.getPiece(getFrom()).getName()).equals(PieceNames.KING) :
                "There should be a king in the from position";
        assert rookFromPosition != null && (chessboard.getPiece(rookFromPosition).getName()).equals(PieceNames.ROOK) :
                "There should be a rook in the rookFrom position";
        King king = chessboard.getKing(chessboard.getCurrentPlayerColor());
        king.setFlaggedAsHavingAlreadyMoved(true);
        log.debug("Executing castling...");
        new StandardMove (getFrom(), getTo()).execute(chessboard);
        new StandardMove (rookFromPosition, rookToPosition).execute(chessboard);
        log.debug("Castling executed.");
        log.debug("Immediately after castling: {}", chessboard);
        return false;
    }

    @Override
    public void undo (@NonNull Board chessboard, @NonNull FENStateString oldGameState) {
        assert chessboard.isEmpty(oldGameState.getCastlingRookFrom()):
                "Original rook's from position should be empty before undoing a move.";
        assert chessboard.isEmpty(oldGameState.getLastExecutedMove().getFrom()):
                "Original king's from position should be empty before undoing a move.";
        //FIXME.
        log.debug("King from position: {}", oldGameState.getLastExecutedMove().getFrom());
        log.debug("King to position: {}", oldGameState.getLastExecutedMove().getTo());
        log.debug("Rook from position: {}", oldGameState.getCastlingRookFrom());
        log.debug("Rook to position: {}", oldGameState.getCastlingRookTo());

        Rook rook = (Rook)chessboard.getPiece(oldGameState.getCastlingRookTo());
        chessboard.setPiece(rook, oldGameState.getCastlingRookFrom());
        chessboard.setEmpty(oldGameState.getCastlingRookTo());
        if (rook.getColor().equals(PlayerColor.BLACK)){
            if (rook.isBlackKingsideRook()) {
                rook.setBlackKingsideRookFlaggedAsHavingAlreadyMoved(oldGameState.isBlackKingsideRookFlaggedAsHavingAlreadyMoved());
            } else {
                if (rook.isBlackQueensideRook()) {
                    rook.setBlackQueensideRookFlaggedAsHavingAlreadyMoved(oldGameState.isBlackQueensideRookFlaggedAsHavingAlreadyMoved());
                }
            }
        } else{
            if (rook.isWhiteKingsideRook()) {
                    rook.setWhiteKingsideRookFlaggedAsHavingAlreadyMoved(oldGameState.isWhiteKingsideRookFlaggedAsHavingAlreadyMoved());
            }  else {
                if (rook.isWhiteQueensideRook()) {
                    rook.setWhiteKingsideRookFlaggedAsHavingAlreadyMoved(oldGameState.isWhiteQueensideRookFlaggedAsHavingAlreadyMoved());
                }
            }
        }
        King king = chessboard.getKing(rook.getColor());
        chessboard.setPiece(king, oldGameState.getLastExecutedMove().getFrom());
        chessboard.setEmpty(oldGameState.getLastExecutedMove().getTo());
        if(king.getColor().equals(PlayerColor.BLACK)) {
            king.setFlaggedAsHavingAlreadyMoved(oldGameState.isBlackKingFlaggedAsHavingAlreadyMoved());
        } else{
            king.setFlaggedAsHavingAlreadyMoved(oldGameState.isWhiteKingFlaggedAsHavingAlreadyMoved());
        }
            log.debug("Is {} King flagged as having already moved? {}",king.getColor(), king.isFlaggedAsHavingAlreadyMoved());
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
    public boolean isLegal (@NonNull Board chessboard){
        King king = (King) chessboard.getPiece(getFrom());
        PlayerColor playerColor = king.getColor();
        assert king.getPseudoLegalMoves(getFrom(), chessboard).contains(this) : "Castling must be a pseudolegal move first.";
        if (chessboard.isPlayerInCheck(playerColor)) {
            log.info("Castling move {} for {} is illegal because {} is in check.", getName(), playerColor, playerColor);
            return false;
        }
        assert chessboard.getPiece(getFrom()) != null: "There must be a piece in " + getFrom();
        log.debug("kingCastlingSide: {}", kingCastlingSide);
        Position firstCastlingKingStep = getFrom().stepTowardsDirection(kingCastlingSide);
        assert firstCastlingKingStep != null : "The first castling step must exist when attempting castling.";
        log.debug("Performing first castling king step on a separate chessboard and checking that the king is not in check..."   );
        new StandardMove(getFrom(), firstCastlingKingStep).execute(chessboard);
        if (chessboard.isPlayerInCheck(playerColor)) {
            log.trace("{} is illegal because {} would be in check while passing on the first square of the castling path.",
                    getName(), playerColor);
            return false;
        }
        Position secondCastlingKingStep = firstCastlingKingStep.stepTowardsDirection(kingCastlingSide);
        assert secondCastlingKingStep != null : "The second castling step must exist when attempting a castling.";
        log.debug("Performing second castling king step on a separate chessboard and checking that the king is not in check..."   );
        new StandardMove(firstCastlingKingStep, secondCastlingKingStep).execute(chessboard);
        if (chessboard.isPlayerInCheck(playerColor)) {
            log.trace("{} is illegal because {} would be in check after performing the second step of the castling path.",
                    getName(), playerColor);
            return false;
        }
        new StandardMove(rookFromPosition, rookToPosition);
        if (chessboard.isPlayerInCheck(playerColor)) {
            log.trace("{} is illegal because {} would be in check after moving the rook to complete castling.",
                    getName(), playerColor);
            return false;
        }
        log.debug("Castling move {} is legal.", this);
        return true;
    }
}