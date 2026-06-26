package com.marcopiccionitraining.parkychess.model;

import com.marcopiccionitraining.parkychess.model.pieces.Piece;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Arrays;
import java.util.Objects;

public class LocalGameState {

    @Getter @Setter
    private char[][] piecesPositionOnChessboard = new char[8][8];
    @Getter @Setter
    private PlayerColor playerColor;
    @Getter @Setter
    private Piece capturedPiece;
    @Getter @Setter
    private boolean isBlackKingsideCastlingAllowed;
    @Getter @Setter
    private boolean isWhiteKingsideCastlingAllowed;
    @Getter @Setter
    private boolean isBlackQueensideCastlingAllowed;
    @Getter @Setter
    private boolean isWhiteQueensideCastlingAllowed;
    @Getter @Setter
    private Position skippedPawnPosition;
    @Getter @Setter
    private int halfMoveCounter;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LocalGameState that)) return false;
        return Arrays.deepEquals(piecesPositionOnChessboard, that.piecesPositionOnChessboard) &&
                isBlackKingsideCastlingAllowed == that.isBlackKingsideCastlingAllowed &&
                isWhiteKingsideCastlingAllowed == that.isWhiteKingsideCastlingAllowed &&
                isBlackQueensideCastlingAllowed == that.isBlackQueensideCastlingAllowed &&
                isWhiteQueensideCastlingAllowed == that.isWhiteQueensideCastlingAllowed &&
                halfMoveCounter == that.halfMoveCounter && Objects.equals(capturedPiece, that.capturedPiece) &&
                Objects.deepEquals(playerColor, that.playerColor) &&
                Objects.deepEquals(skippedPawnPosition, that.skippedPawnPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.deepHashCode(piecesPositionOnChessboard), playerColor, capturedPiece, isBlackKingsideCastlingAllowed, isWhiteKingsideCastlingAllowed,
                isBlackQueensideCastlingAllowed, isWhiteQueensideCastlingAllowed, skippedPawnPosition,
                halfMoveCounter);
    }

    @Override @NonNull
    public String toString(){
        @NonNull StringBuilder result = new StringBuilder("{");
        result.append("Chess pieces position: ").append(Arrays.deepToString(piecesPositionOnChessboard));
        result.append(" ");
        result.append(playerColor.toString().toLowerCase());
        result.append(" to play; capt: ");
        result.append(capturedPiece != null ? capturedPiece : "no; ");
        result.append("castle: ");
        if (isBlackKingsideCastlingAllowed) {
            result.append('k');
        }
        if (isWhiteKingsideCastlingAllowed) {
            result.append('K');
        }
        if (isBlackQueensideCastlingAllowed){
            result.append('q');
        }
        if (isWhiteQueensideCastlingAllowed){
            result.append('Q');
        }
        result.append("; skip: ");
        result.append(skippedPawnPosition != null ? skippedPawnPosition.toString() : "no ");
        result.append("; moves: ");
        result.append(halfMoveCounter);
        result.append("}");
        return result.toString();
    }
}