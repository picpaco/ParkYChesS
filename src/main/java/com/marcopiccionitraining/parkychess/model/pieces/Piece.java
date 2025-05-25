package com.marcopiccionitraining.parkychess.model.pieces;

import com.marcopiccionitraining.parkychess.model.Board;
import com.marcopiccionitraining.parkychess.model.PlayerColor;
import com.marcopiccionitraining.parkychess.model.Position;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Objects;

/**
 * Chess pieces representation.
 */
public abstract class Piece {

    private final PlayerColor playerColor;
    @Getter
    private PieceNames name;

    @Getter
    @Setter
    private Position position;

    private final Logger LOGGER = LoggerFactory.getLogger(Piece.class);

    //  @Getter
    //  int currentDepth;

    void setName(PieceNames name) {
        this.name = name;
    }

    Piece(PlayerColor playerColor) {
        this.playerColor = playerColor;
    }

    public PlayerColor getColor() {
        return playerColor;
    }

    //FIXME; also check code in StandardMove
    /*  if (hasMoved) {
            if (this.hasMoved) {
                this.hasMovedForGood = true;
            } else {
                this.hasMoved = true;
            }
        } else {
            if (!this.hasMovedForGood) {
                this.hasMoved = false;
            }
        }*/
    //public HashMap<Integer, Boolean> hasMoved = new HashMap<>();
    @Setter
    @Getter
    boolean hasMoved;

    /**
     * This attribute is true when a certain piece (king or rook) has moved from its starting square
     * and therefore has lost forever the ability to castle.
     */
    @Setter
    @Getter
    public boolean hasMovedForGood;

    public abstract Piece copy();

    public abstract Collection<Move> getPseudoLegalMoves(Position from, Board chessboard);

    public boolean canCaptureEnemyKing(Position from, Board chessboard) {
        //    LOGGER.trace("In canCaptureEnemyKing (from = {}", from);
        for (Move move : getPseudoLegalMoves(from, chessboard)) {
            Piece targetPiece = chessboard.getPiece(move.getTo());
            if (targetPiece != null && targetPiece.getName().equals(PieceNames.KING)) {
                //    LOGGER.trace("canCaptureEnemyKing? Yes");
                return true;
            }
        }
        // LOGGER.trace("canCaptureEnemyKing? No");
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Piece piece)) return false;
        return playerColor.equals(piece.playerColor) && name.toString().equals(piece.name.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerColor, name);
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

    public boolean hasMoved() {
        return hasMoved;
    }

    /*
    public Boolean hasMoved() {
        if (this.hasMoved.get(currentDepth) == null) {
            this.hasMoved.put(currentDepth, Boolean.FALSE);
            return Boolean.FALSE;
        } else {
            return this.hasMoved.get(currentDepth);
        }
    }

    public void setHasMoved(Integer currentDepth, Boolean updatedHasMoved) {
        assert currentDepth != null : "currentDepth cannot be null.";
        assert updatedHasMoved != null : "updatedHasMoved cannot be null.";
        this.hasMoved.put(currentDepth, updatedHasMoved);
        this.currentDepth = currentDepth;
        if (name.equals(PieceNames.KING)) {
            LOGGER.info("{} {} depth: {}, value: {}, hasMoved: {}",
                    playerColor, name, currentDepth, updatedHasMoved, this.hasMoved);
        }
    }

 */
}