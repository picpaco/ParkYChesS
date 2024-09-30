package com.marcopiccionitraining.parkychess.model.pieces;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

/**
 * Chess pieces representation.
 * Descendants of this class are immutable objects. This means that their name, playerColor, and position on the chessboard
 * are established once and forever at creation time.
 * It also means that two instances of a descendant of Piece are equal if and only if they are the same instance.
 */
public abstract class Piece {

    private final PlayerColor playerColor;
    private boolean isChecker;
    private PieceName name;
 //   private final Logger LOGGER = LoggerFactory.getLogger(Piece.class);

    public boolean isChecker() {
        return isChecker;
    }

    public void setChecker(boolean checker) {
        isChecker = checker;
    }

    public PieceName getName() {
        return name;
    }

    void setName(PieceName name) {
        this.name = name;
    }

    Piece (PlayerColor playerColor){
        this.playerColor = playerColor;
    }

  //  public abstract Collection<? extends Move> generateMoves(Board chessboard);

    public PlayerColor getColor(){
        return playerColor;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

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

    Collection<Position> positionsInDirection(Position from, Direction direction, Board chessboard){
        Collection<Position> positions = new ArrayList<>();
        for (Position position = from.stepTowardsDirection(direction); position != null && chessboard.isInside(position);
             position = position.stepTowardsDirection(direction)){
            if (chessboard.isEmpty(position)){
                positions.add(position);
                continue;
            }
            Piece piece = chessboard.getPiece(position);
            if (!(piece.playerColor.equals(playerColor))){
                positions.add(position);
            }
            /*if (chessboard.isEmpty(position.get())){
                positions.add(position.get());
                continue;
            }
            Piece piece = chessboard.getPiece(position.get());
            if (!(piece.playerColor.equals(playerColor))){
                positions.add(position.get());
            }*/
            break;
        }
        return positions;
    }
//TODO make it polymorphic: each piece implementation will invoke the ad hoc precomputed code.
    Collection<Position> positionsInDirections(Position from, Direction[] directions, Board chessboard){
        Collection<Position> tiles = new ArrayList<>();
        for (Direction direction : directions) {
            tiles.addAll(positionsInDirection(from, direction, chessboard));
        }
        return tiles;
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