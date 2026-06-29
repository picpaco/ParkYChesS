package com.marcopiccionitraining.parkychess.model.moves;

import com.marcopiccionitraining.parkychess.model.Board;
import com.marcopiccionitraining.parkychess.model.LocalGameState;
import com.marcopiccionitraining.parkychess.model.Position;
import lombok.NonNull;

public class EmptyMove
extends Move {

    public EmptyMove() {
        setFrom(new Position(-1,-1));
        setTo(new Position(-1,-1));
        setName(MoveNames.EMPTY);
    }
    @Override
    public boolean execute(Board chessboard){
        return false;
    }

    @Override
    public void undo(@NonNull Board chessboard, LocalGameState localGameState) {
    }

    @Override
    public String toString() {
        return "empty move.";
    }
}
