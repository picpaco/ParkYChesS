package com.marcopiccionitraining.parkychess.model;

import lombok.NonNull;

public interface Command {

    boolean execute (Board chessboard);
    void undo (Board chessboard, FENStateString oldGameState);
}
