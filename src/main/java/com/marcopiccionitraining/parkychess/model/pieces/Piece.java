package com.marcopiccionitraining.parkychess.model.pieces;

import com.marcopiccionitraining.parkychess.model.Board;
import com.marcopiccionitraining.parkychess.model.PlayerColor;
import com.marcopiccionitraining.parkychess.model.Position;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
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
    private boolean isFlaggedAsHavingAlreadyMoved;

    public Piece (PlayerColor color) {
        assert color != null : "Player color cannot be null";
        this.color = color;
    }

    public abstract Piece copy();

    //TODO: decide if it is better to add the argument 'from' everywhere or to have pieces store their own position.
    public abstract List<Move> getPseudoLegalMoves(Position from, Board chessboard);

    public boolean canCaptureOpponentKing(@NonNull Position from, @NonNull Board chessboard){
        assert chessboard.getPiece(from) == null ||
                chessboard.getPiece(from).getName().equals(PieceNames.BISHOP) ||
                chessboard.getPiece(from).getName().equals(PieceNames.ROOK) ||
                chessboard.getPiece(from).getName().equals(PieceNames.QUEEN) ||
                chessboard.getPiece(from).getName().equals(PieceNames.KNIGHT) :
                "If there is a piece on " + from + ", it must be a slider (bishop, rook, or queen) or a knight.";
        log.trace("There is a piece on {}: {}", from,chessboard.getPiece(from));
        List<Move> piecePseudolegalMoves = getPseudoLegalMoves(from, chessboard);
        log.debug("{} {} pseudolegal moves (used to check if can capture opponent's king): {}",
                getColor(), getName(), piecePseudolegalMoves);
        for (Move move : piecePseudolegalMoves) {
            if (chessboard.isEmpty(move.getTo())) {
                continue;
            }
            Piece piece = chessboard.getPiece(move.getTo());
            assert !(piece.getColor().equals(chessboard.getPiece(from).getColor())) :
                    "Piece at 'to' position must be of opposite color with respect to piece at from position";
            if (piece.getName().equals(PieceNames.KING)) {
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
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, name);
    }

    @Override
    public String toString() {
        return String.valueOf(toOneChar());
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