package com.marcopiccionitraining.parkychess.model.moves;

import com.marcopiccionitraining.parkychess.model.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public abstract class Move implements Command {
    @Getter @Setter
    private Position from;
    @Getter @Setter
    private Position to;
    @Getter @Setter
    private MoveNames name;

    public boolean isLegal (@NonNull Board chessboard){
        log.trace("Executing Move.isLegal()");
        PlayerColor playerColorOfPieceToMove = chessboard.getPiece(from).getColor();
        Board boardCopy = chessboard.copy();
        execute(boardCopy);
        return !(boardCopy.isPlayerInCheck(playerColorOfPieceToMove));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Move move)) return false;
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
    public abstract void undo (Board chessboard, FENStateString oldGameState);
}
