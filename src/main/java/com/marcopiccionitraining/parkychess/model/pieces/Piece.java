package com.marcopiccionitraining.parkychess.model.pieces;

import com.marcopiccionitraining.parkychess.model.Board;
import com.marcopiccionitraining.parkychess.model.PlayerColor;
import com.marcopiccionitraining.parkychess.model.Position;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Objects;

/**
 * Chess pieces representation.
 */
@Slf4j
public abstract class Piece {
    @Getter
    private final PlayerColor color;
    @Getter @Setter
    private PieceNames name;
    @Getter @Setter
    private Position position;

    Piece (PlayerColor color) {
        assert color != null : "Player color cannot be null";
        this.color = color;
    }

    @Getter @Setter
    private boolean isFlaggedAsHavingAlreadyMoved;

    public abstract Piece copy();

    public abstract Collection<Move> getPseudoLegalMoves(Position from, Board chessboard);

    public boolean canCaptureEnemyKing (@NonNull Position from, @NonNull Board chessboard) {
        assert chessboard.getPiece(from) != null : "Piece at position " + from + " must exist.";
        for (Move move : getPseudoLegalMoves(from, chessboard)) {
            Piece targetPiece = chessboard.getPiece(move.getTo());
            if (targetPiece != null && targetPiece.getName().equals(PieceNames.KING)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Piece piece)) return false;
        return hashCode() == piece.hashCode();
     //   return playerColor.equals(piece.playerColor) && name.toString().equals(piece.name.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, name);
    }

    @Override
    public String toString() {
        return toOneChar() + ((getPosition() == null) ? "N.A." : getPosition().toString());
    }

    public char toOneChar() {
        if (color.equals(PlayerColor.BLACK)) {
            if (getName().equals(PieceNames.KNIGHT)) {
                return 'n';
            } else {
                return Character.toLowerCase(getName().toString().charAt(0));
            }
        } else {
            if (getName().equals(PieceNames.KNIGHT)) {
                return 'N';
            } else {
                return getName().toString().charAt(0);
            }
        }
    }
}