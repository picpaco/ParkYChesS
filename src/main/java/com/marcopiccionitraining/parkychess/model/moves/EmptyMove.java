package com.marcopiccionitraining.parkychess.model.moves;

import com.marcopiccionitraining.parkychess.model.Board;
import com.marcopiccionitraining.parkychess.model.FENStateString;
import com.marcopiccionitraining.parkychess.model.Position;
import lombok.NonNull;

public class EmptyMove
extends Move {

    public EmptyMove() {
        setName(MoveNames.EMPTY);
    }
    @Override
    public boolean execute(Board chessboard) {
        return false;
    }

    @Override
    public void undo(@NonNull Board chessboard, @NonNull FENStateString oldGameState) {
    }

    @Override
    public String toString() {
        return "empty move.";
    }

}
