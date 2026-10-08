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
    private final boolean isPieceFlaggedAsHavingAlreadyMoved;

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
        isPieceFlaggedAsHavingAlreadyMoved = chessboard.isPieceMovedLastFlaggedAsHavingAlreadyMoved();
        isBlackKingsideCastlingPossible = chessboard.isBlackKingsideCastlingPossibleFromFEN();
        isBlackQueensideCastlingPossible = chessboard.isBlackQueensideCastlingPossibleFromFEN();
        isWhiteKingsideCastlingPossible = chessboard.isWhiteKingsideCastlingPossibleFromFEN();
        isWhiteQueensideCastlingPossible = chessboard.isWhiteQueensideCastlingPossibleFromFEN();
        pawnSkippedPosition = chessboard.getPawnSkippedPosition(chessboard.getCurrentPlayerColor());
        halfMoveCounter = chessboard.getHalfMoveCounter();
     //   zobristKey = ZobristKeyProvider.getZobristHash(this);
     /*   if (chessboard.containsZobristKey(zobristKey)) {
            int numberOfOccurrences = chessboard.getZobristValue(zobristKey);
            log.error("The current position {} was already reached {} times.", chessboard, numberOfOccurrences);
            numberOfOccurrences++;
            chessboard.saveZobristEntry(zobristKey, numberOfOccurrences);
        } else {
            log.error("The current position {} was never reached before.", chessboard);
            chessboard.saveZobristEntry(zobristKey, 1);
        }*/
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
                //        Objects.deepEquals(currentPlayerColor, that.currentPlayerColor) &&
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
                pieceCapturedLast, isPieceFlaggedAsHavingAlreadyMoved,
                isBlackKingsideCastlingPossible, isWhiteKingsideCastlingPossible,
                isBlackQueensideCastlingPossible, isWhiteQueensideCastlingPossible,
                pawnSkippedPosition, halfMoveCounter, zobristKey);
    }

    @Override @NonNull
    public String toString(){
        @NonNull StringBuilder result = new StringBuilder("{");
        result.append("Pieces positions: ").append(piecesPositions);
        result.append(", move to: ").append(currentPlayerColor);
        result.append(", last moved piece: ").append(pieceMovedLast != null ? pieceMovedLast : "-");
        result.append(", last captured piece: ").append(pieceCapturedLast != null ? pieceCapturedLast : "-");
       // result.append(", piece already moved: ").append(isPieceFlaggedAsHavingAlreadyMoved);
        result.append(", castle rights: ");
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
        result.append("; half moves: ").append(halfMoveCounter);
        result.append("; zobrist key: ").append(zobristKey);
     //   result.append(" zobrist key: ").append(zobristKey);
        result.append("}");
        return result.toString();
    }
}