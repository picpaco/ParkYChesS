package com.marcopiccionitraining.parkychess.model;

import com.marcopiccionitraining.parkychess.model.pieces.Piece;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

public class LocalGameState {
    @Getter @Setter
    private PlayerColor playerColor;
    @Getter
    private Board chessboard;

    @Getter @Setter
    private Piece capturedPiece;
    @Getter @Setter
    List<Position> piecesPositions;
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
    @Getter
    private final long zobristHash;

    public LocalGameState(Board chessboard) {
        this.chessboard = chessboard;
        this.playerColor = chessboard.getCurrentPlayerColor();
        halfMoveCounter = chessboard.getHalfMoveCounter();
        zobristHash = ZobristChessboardState.getZobristHash(this);
    }
    public boolean isBlacktoMove() {
        return playerColor.equals(PlayerColor.BLACK);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LocalGameState that)) return false;
        return //Arrays.deepEquals(piecesPositionOnChessboard, that.piecesPositionOnChessboard) &&
                isBlackKingsideCastlingAllowed == that.isBlackKingsideCastlingAllowed &&
                isWhiteKingsideCastlingAllowed == that.isWhiteKingsideCastlingAllowed &&
                isBlackQueensideCastlingAllowed == that.isBlackQueensideCastlingAllowed &&
                isWhiteQueensideCastlingAllowed == that.isWhiteQueensideCastlingAllowed &&
                halfMoveCounter == that.halfMoveCounter && Objects.equals(capturedPiece, that.capturedPiece) &&
                Objects.deepEquals(playerColor, that.playerColor) &&
                Objects.deepEquals(skippedPawnPosition, that.skippedPawnPosition);
    }
//TODO: careful to when hashCode() is used vs. when zobristHash is used.
    @Override
    public int hashCode() {
        return Objects.hash(//Arrays.deepHashCode(piecesPositionOnChessboard),
                 playerColor, capturedPiece, isBlackKingsideCastlingAllowed, isWhiteKingsideCastlingAllowed,
                isBlackQueensideCastlingAllowed, isWhiteQueensideCastlingAllowed, skippedPawnPosition,
                halfMoveCounter);
    }

    @Override @NonNull
    public String toString(){
        @NonNull StringBuilder result = new StringBuilder("{");
        //result.append("Chess pieces position: ").append(Arrays.deepToString(piecesPositionOnChessboard));
        result.append(" player color=").append(playerColor).append(", ");
        //result.append(" ");
        //result.append(playerColor.toString().toLowerCase());
        result.append(", captured piece?: ");
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
        result.append("; zobristHash: ");
        result.append(zobristHash);
        result.append("}");
        return result.toString();
    }
}