package com.marcopiccionitraining.parkychess.model;

public interface Command {

    boolean execute (Board chessboard);
    void undo (Board chessboard);
}
