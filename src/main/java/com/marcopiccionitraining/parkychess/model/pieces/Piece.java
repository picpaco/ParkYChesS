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

    public boolean canCaptureKing(Position kingPosition, Board chessboard){
        Collection<Move> piecePseudolegalMoves = getPseudoLegalMoves(getPosition(), chessboard);
        for (Move move : piecePseudolegalMoves) {
            if (move.getTo().equals(kingPosition)) {
                Piece piece = chessboard.getPiece(move.getFrom());
                if (!(piece.isPinned(kingPosition, chessboard))) {
                    return true;
                }
            }
        }
        return false;
    }
    /** //FIXME: infinite recursion with canCaptureKing?
     * Is this piece pinned? That is, among the enemy pieces, is there at least one
     * that could capture our King with a pseudolegal move?
     */
    public final boolean isPinned(@NonNull Position friendlyKingPosition, @NonNull Board chessboard) {
        Collection<Position> enemyPiecesPositions = chessboard.getPiecesPositionsForColor(PlayerColor.getOpponentColor(getColor()));
        for (Position enemyPos : enemyPiecesPositions){
            Piece piece = chessboard.getPiece(enemyPos);
            if (piece.canCaptureKing(friendlyKingPosition, chessboard)){
                return true;
            }
        }
        return false;
    }

  //  public abstract boolean canCaptureKing(Position pos, Board chessboard);

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