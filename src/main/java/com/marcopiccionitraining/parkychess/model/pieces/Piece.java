package com.marcopiccionitraining.parkychess.model.pieces;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Chess pieces representation.
 * Descendants of this class are immutable objects. This means that their name, playerColor, and position on the chessboard
 * are established once and forever at creation time.
 * It also means that two instances of a descendant of Piece are equal if and only if they are the same instance.
 */
public abstract class Piece {

    private final PlayerColor playerColor;
    @Getter
    private PieceName name;
 //   private final Logger LOGGER = LoggerFactory.getLogger(Piece.class);

    void setName(PieceName name) {
        this.name = name;
    }

    Piece (PlayerColor playerColor){
        this.playerColor = playerColor;
    }

    public PlayerColor getColor(){
        return playerColor;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    @Setter
    private boolean hasMoved = false;

    public abstract Piece copy();

    public abstract Collection<Move> getPseudoLegalMoves(Position from, Board chessboard);

    public boolean canCaptureEnemyKing (Position from, Board chessboard){
    //    LOGGER.trace("In canCaptureEnemyKing (from = {}", from);
        for (Move move : getPseudoLegalMoves(from, chessboard)) {
            Piece targetPiece = chessboard.getPiece(move.getTo());
            if (targetPiece != null && targetPiece.getName().equals(PieceName.KING)){
            //    LOGGER.trace("canCaptureEnemyKing? Yes");
                return true;
            }
        }
       // LOGGER.trace("canCaptureEnemyKing? No");
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Piece piece)) return false;
        return hasMoved == piece.hasMoved && playerColor == piece.playerColor && name.toString().equals(piece.name.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerColor, name, hasMoved);
    }

    @Override
    public String toString() {
        if (getColor().equals(PlayerColor.BLACK)) {
            return name.toString().toLowerCase();
        } else {
            return name.toString();
        }
    }

    public char toOneChar() {
        if (getColor().equals(PlayerColor.BLACK)) {
            if (getName().equals(PieceName.KNIGHT)) {
                return 'n';
            } else {
                return Character.toLowerCase(getName().toString().charAt(0));
            }
        } else {
            if ( getName().equals(PieceName.KNIGHT)) {
                return 'N';
            } else {
                return getName().toString().charAt(0);
            }
        }
    }
}