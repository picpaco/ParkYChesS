package com.marcopiccionitraining.parkychess.model.pieces;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.CastlingMove;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import com.marcopiccionitraining.parkychess.model.moves.MoveNames;
import com.marcopiccionitraining.parkychess.model.moves.StandardMove;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class King extends Piece {

    public King (PlayerColor playerColor) {
        super(playerColor);
        setName(PieceNames.KING);
        setFlaggedAsHavingAlreadyMoved(false);
    }

    private boolean isUnmovedRook(@NonNull Position rookPosition, @NonNull Board chessboard) {
        assert (chessboard.isEmpty(rookPosition)) || (chessboard.getPiece(rookPosition).getName().equals(PieceNames.ROOK)) :
                "If there is a piece on rookPosition it must be a " + getColor() + " rook. It is a " +
                chessboard.getPiece(rookPosition).getName() + " instead.";
        if (chessboard.isEmpty(rookPosition)) {
            return false;
        }
        Piece mayBeRook = chessboard.getPiece(rookPosition);
        return mayBeRook.getName().equals(PieceNames.ROOK) && !(mayBeRook.isFlaggedAsHavingAlreadyMoved());
    }

    private boolean areAllPositionsEmpty(@NonNull List<Position> positions, @NonNull Board chessboard) {
        for (Position position : positions) {
            if (!(chessboard.isEmpty(position))) {
                return false;
            }
        }
        return true;
    }

    /** 1. A king move is 'pseudolegal' if all the following conditions are true:
     * 1.1 The king move is within the chessboard,
     * 1.2 It is moving to an empty square, or
     * 1.3 It is moving to a square occupied by an enemy piece.
     * Note: It's OK if the king ends up with being under check.
     * 2. A Castling move is pseudolegal if allowed by the initial FEN (if applicable), and
     * 2.1. King and rook which are involved in the castling move are on their home square, and
     * 2.2. The path between king and rook is free of pieces, and
     * 2.3. King and rook have not moved before.
     */
    @Override
    public List<Move> getPseudoLegalMoves (@NonNull Position kingFromPos, @NonNull Board chessboard) {
        List<Move> pseudoLegalMoves = new ArrayList<>();
        List<Position> kingPseudoLegalPositionsTo = PseudoLegalPiecesPositionsInitializer.
                getKingPseudoLegalPositions(kingFromPos);
        log.debug("{} King pseudolegal 'to' positions from {} (castling excluded):\n {}", this.getColor(), kingFromPos, kingPseudoLegalPositionsTo);
        for (Position kingToPosition : kingPseudoLegalPositionsTo) {
            if (chessboard.isEmpty(kingToPosition) ||
                    !(chessboard.getPiece(kingToPosition).getColor()).equals(getColor())) {
                pseudoLegalMoves.add(new StandardMove(kingFromPos, kingToPosition));
            }
        }
        log.debug("Looking for castling moves...");
        if (chessboard.getPiece(kingFromPos).getName().equals(PieceNames.KING)) {
            log.error("There is a king on {}", kingFromPos);
            if (canCastleKingside(kingFromPos, chessboard)) {
                pseudoLegalMoves.add(new CastlingMove(MoveNames.KINGSIDE_CASTLE, kingFromPos));
                log.error("Added kingside castle for {} king in {}.", getColor(), kingFromPos);
            } else {
                log.error("No kingside castle possible for {} king in {}.", getColor(), kingFromPos);
            }
            if (canCastleQueenside(kingFromPos, chessboard)) {
                pseudoLegalMoves.add(new CastlingMove(MoveNames.QUEENSIDE_CASTLE, kingFromPos));
                log.debug("Added queenside castle for {} king in {}.", getColor(), kingFromPos);
            } else {
                log.debug("No queenside castle possible for {} king in {}.", getColor(), kingFromPos);
            }
        }
        log.debug("{} king pseudolegal moves: {}.", this.getColor(), pseudoLegalMoves);
        return pseudoLegalMoves;
    }

    private boolean canCastleKingside (@NonNull Position from, @NonNull Board chessboard) {
        assert chessboard.getPiece(from).getName().equals(PieceNames.KING) : "This method can be invoked only on kings. "
                + chessboard.getPiece(from).getName() + "s are not allowed.";
        boolean isKingsideCastlePossibleFromFEN;
        boolean isKingAtHome;
        Position rookHomePosition;
        log.error("Is this king flagged as having already moved? {}", isFlaggedAsHavingAlreadyMoved());
        if (isFlaggedAsHavingAlreadyMoved()) {
            return false;
        }
        PlayerColor color = chessboard.getCurrentPlayerColor();
        if (color.equals(PlayerColor.BLACK)) {
            isKingsideCastlePossibleFromFEN = chessboard.isBlackKingsideCastlingPossibleFromFEN();
            isKingAtHome = ((from.getRow() == 0) && (from.getColumn() == 4));
            rookHomePosition = new Position(0, 7);
        } else {
            isKingsideCastlePossibleFromFEN = chessboard.isWhiteKingsideCastlingPossibleFromFEN();
            isKingAtHome = ((from.getRow() == 7) && (from.getColumn() == 4));
            rookHomePosition = new Position(7, 7);
        }
        if (areKingOrRookNotPositionedForCastling(chessboard, isKingAtHome, rookHomePosition, color)) {
            return false;
        }
        if (isKingsideCastlePossibleFromFEN) {
            ArrayList<Position> betweenPositions = new ArrayList<>();
            betweenPositions.add(new Position(from.getRow(), 5));
            betweenPositions.add(new Position(from.getRow(), 6));
            return isUnmovedRook(rookHomePosition, chessboard) &&
                    areAllPositionsEmpty(betweenPositions, chessboard);
        } else {
            return false;
        }
    }

    private boolean canCastleQueenside(@NonNull Position from, @NonNull Board chessboard) {
        assert chessboard.getPiece(from).getName().equals(PieceNames.KING) : " This method is for kings only. "
                + chessboard.getPiece(from).getName() + "s are not allowed.";
        boolean isQueensideCastlePossibleFromFEN;
        boolean isKingAtHome;
        Position rookHomePosition;
        if (isFlaggedAsHavingAlreadyMoved()) {
            return false;
        }
        PlayerColor color = chessboard.getCurrentPlayerColor();
        if (color.equals(PlayerColor.BLACK)) {
            isQueensideCastlePossibleFromFEN = chessboard.isBlackQueensideCastlingPossibleFromFEN();
            isKingAtHome = ((from.getRow() == 0) && (from.getColumn() == 4));
            rookHomePosition = new Position(0, 0);
        } else {
            isQueensideCastlePossibleFromFEN = chessboard.isWhiteQueensideCastlingPossibleFromFEN();
            isKingAtHome = ((from.getRow()) == 7 && (from.getColumn() == 4));
            rookHomePosition = new Position(7, 0);
        }
        if (areKingOrRookNotPositionedForCastling(chessboard, isKingAtHome, rookHomePosition, color)) {
            return false;
        }
        if (isQueensideCastlePossibleFromFEN) {
            ArrayList<Position> betweenPositions = new ArrayList<>();
            betweenPositions.add(new Position(from.getRow(), 1));
            betweenPositions.add(new Position(from.getRow(), 2));
            betweenPositions.add(new Position(from.getRow(), 3));
            return isUnmovedRook(rookHomePosition, chessboard) &&
                areAllPositionsEmpty(betweenPositions, chessboard);
        } else {
            return false;
        }
    }

    private boolean areKingOrRookNotPositionedForCastling(@NonNull Board chessboard, boolean isKingAtHome,
                                                          Position rookHomePosition, PlayerColor color) {
        if (!isKingAtHome) {
            return true;
        }
        return chessboard.isEmpty(rookHomePosition) ||
                !chessboard.getPiece(rookHomePosition).getColor().equals(color) ||
                !chessboard.getPiece(rookHomePosition).getName().equals(PieceNames.ROOK);
    }

    @Override
    public boolean canCaptureOpponentKing (@NonNull Position from, @NonNull Board chessboard) {
        assert chessboard.getPiece(from) == null || chessboard.getPiece(from).getName().equals(PieceNames.KING) :
                "If there is a piece on " + from + ", it must be a King.";
        log.trace("King on {}: {}", from,chessboard.getPiece(from));
        List<Position> capturePositions = PseudoLegalPiecesPositionsInitializer.getKingPseudoLegalPositions(from);
        for (Position pseudoLegalPosition : capturePositions){
            if (chessboard.isEmpty(pseudoLegalPosition)) {
               continue;
            }
            if (chessboard.getPiece(pseudoLegalPosition).getColor().equals(getColor())){
                continue;
            }
            if (!(chessboard.getPiece(pseudoLegalPosition).getName().equals(PieceNames.KING))){
                continue;
            }
            return true;
        }
        return false;
    }

    @Override
    public Piece copy() {
        King kingCopy = new King(getColor());
        kingCopy.setFlaggedAsHavingAlreadyMoved(isFlaggedAsHavingAlreadyMoved());
        return kingCopy;
    }
}