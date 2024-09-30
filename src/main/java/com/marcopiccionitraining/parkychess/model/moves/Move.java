package com.marcopiccionitraining.parkychess.model.moves;

import com.marcopiccionitraining.parkychess.model.Board;
import com.marcopiccionitraining.parkychess.model.PlayerColor;
import com.marcopiccionitraining.parkychess.model.Command;
import com.marcopiccionitraining.parkychess.model.Position;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@Getter
public abstract class Move implements Command {
    private Position from;
    private Position to;
    private MoveNames name;
 //   private final Logger LOGGER = LoggerFactory.getLogger(Move.class);

    void setName (MoveNames name){
        this.name = name;
    }

    void setFrom (Position from) {
        this.from = from;
    }

    void setTo (Position to) {
        this.to = to;
    }

    public boolean isLegal (Board chessboard){
        PlayerColor playerColorOfPieceToMove = chessboard.getPiece(from).getColor();
        Board boardCopy = chessboard.copy();
    //    LOGGER.trace(boardCopy.toString());
        execute(boardCopy);
     //   LOGGER.trace("After executing {} on a copy of the board is {} in check? {}", this, playerColorOfPieceToMove, boardCopy.isInCheck(playerColorOfPieceToMove));
        return !(boardCopy.isInCheck(playerColorOfPieceToMove));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Move move)) return false;
        return Objects.equals(from, move.from) && Objects.equals(to, move.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

    @Override
    public String toString() {
        return "" + from + to;
    }

    public abstract boolean execute (Board chessboard);
    public abstract void undo (Board chessboard);

}
