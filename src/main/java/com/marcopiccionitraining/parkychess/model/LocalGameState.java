package com.marcopiccionitraining.parkychess.model;

import com.marcopiccionitraining.parkychess.model.moves.MoveNames;
import com.marcopiccionitraining.parkychess.model.pieces.Piece;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;

@Slf4j
public class LocalGameState {

    @Getter
    private final Board chessboard;

    @Getter
    private final  List<Position> piecesPositions;

    @Getter
    private final PlayerColor currentPlayerColor;

    @Getter @Setter
    private Piece pieceMovedLast;

    //TODO: do I need pieceCapturedLast attribute?
    @Getter
    private final Piece pieceCapturedLast;

    @Getter
    private boolean isKingFlaggedAsHavingAlreadyMoved;

    @Getter
    private boolean isRookFlaggedAsHavingAlreadyMoved;

    @Getter
    private final boolean isBlackKingsideCastlingPossible;

    @Getter
    private final boolean isWhiteKingsideCastlingPossible;

    @Getter
    private final boolean isBlackQueensideCastlingPossible;

    @Getter
    private final boolean isWhiteQueensideCastlingPossible;

    @Getter
    private final Position pawnSkippedPosition;

    @Getter
    private final int halfMoveCounter;

    @Getter @Setter
    private long zobristKey;

    public LocalGameState(Board chessboard) {
        this.chessboard = chessboard;
        piecesPositions = chessboard.getPiecesPositions();
        currentPlayerColor = chessboard.getCurrentPlayerColor();
        if (!(chessboard.getLastExecutedMove().getName().equals(MoveNames.EMPTY))) {
            pieceMovedLast = chessboard.getPiece(chessboard.getLastExecutedMove().getTo());
        }
        pieceCapturedLast = chessboard.getLastCapturedPiece();
        isKingFlaggedAsHavingAlreadyMoved = chessboard.isPieceMovedLastFlaggedAsHavingAlreadyMoved();
        isRookFlaggedAsHavingAlreadyMoved = chessboard.isPieceMovedLastFlaggedAsHavingAlreadyMoved();
        isBlackKingsideCastlingPossible = chessboard.isBlackKingsideCastlingPossibleFromFEN();
        isBlackQueensideCastlingPossible = chessboard.isBlackQueensideCastlingPossibleFromFEN();
        isWhiteKingsideCastlingPossible = chessboard.isWhiteKingsideCastlingPossibleFromFEN();
        isWhiteQueensideCastlingPossible = chessboard.isWhiteQueensideCastlingPossibleFromFEN();
        pawnSkippedPosition = chessboard.getPawnSkippedPosition(chessboard.getCurrentPlayerColor());
        halfMoveCounter = chessboard.getHalfMoveCounter();
    }

    public boolean isBlackToMove() {
        return currentPlayerColor.equals(PlayerColor.BLACK);
    }

    public boolean isWhiteToMove() {
        return currentPlayerColor.equals(PlayerColor.WHITE);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LocalGameState that)) return false;
        return
                Objects.equals(piecesPositions, that.piecesPositions) &&
                        Objects.equals(currentPlayerColor, that.currentPlayerColor) &&
                   //     Objects.deepEquals(pieceMovedLast, that.pieceMovedLast) &&
                        Objects.deepEquals(pieceCapturedLast, that.pieceCapturedLast) &&
                        isBlackKingsideCastlingPossible == that.isBlackKingsideCastlingPossible &&
                        isWhiteKingsideCastlingPossible == that.isWhiteKingsideCastlingPossible &&
                        isBlackQueensideCastlingPossible == that.isBlackQueensideCastlingPossible &&
                        isWhiteQueensideCastlingPossible == that.isWhiteQueensideCastlingPossible &&
                        Objects.deepEquals(pawnSkippedPosition, that.pawnSkippedPosition) &&
                        halfMoveCounter == that.halfMoveCounter &&
                        zobristKey == (that.zobristKey);
    }
//TODO: be careful to when hashCode() is used vs. when zobristHash is used.
    @Override
    public int hashCode() {
        return Objects.hash(piecesPositions, currentPlayerColor, pieceMovedLast,
                pieceCapturedLast, isKingFlaggedAsHavingAlreadyMoved, isRookFlaggedAsHavingAlreadyMoved,
                isBlackKingsideCastlingPossible, isWhiteKingsideCastlingPossible,
                isBlackQueensideCastlingPossible, isWhiteQueensideCastlingPossible,
                pawnSkippedPosition, halfMoveCounter, zobristKey);
    }

    @Override @NonNull
    public String toString(){
        @NonNull StringBuilder result = new StringBuilder("{");
        result.append("Pieces at ").append(piecesPositions).append("; ");
        result.append(currentPlayerColor).append(" moves; ");
        result.append("has piece moved? ");
        result.append(isKingFlaggedAsHavingAlreadyMoved ? " Yes" : " No; ");
        result.append(isRookFlaggedAsHavingAlreadyMoved ? " Yes" : " No; ");
        result.append(pieceMovedLast != null ? pieceMovedLast : "-").append(" was moved last; ");
        result.append(pieceCapturedLast != null ? pieceCapturedLast : "-").append(" was captured last; ");
        result.append("\ncastle rights: ");
        if (isBlackKingsideCastlingPossible) {
            result.append('k');
        }
        if (isWhiteKingsideCastlingPossible) {
            result.append('K');
        }
        if (isBlackQueensideCastlingPossible) {
            result.append('q');
        }
        if (isWhiteQueensideCastlingPossible) {
            result.append('Q');
        }
        if (!isBlackKingsideCastlingPossible && !isWhiteKingsideCastlingPossible &&
                !isBlackQueensideCastlingPossible && !isWhiteQueensideCastlingPossible){
            result.append('-');
        }
        result.append("; skip: ").append(pawnSkippedPosition != null ? pawnSkippedPosition.toString() : "- ");
        result.append("; zobrist key: ").append(zobristKey);
        result.append("; half moves: ").append(halfMoveCounter);
        result.append("}");
        return result.toString();
    }
}