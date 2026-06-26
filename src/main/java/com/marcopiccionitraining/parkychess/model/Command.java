package com.marcopiccionitraining.parkychess.model;

public interface Command {

    boolean execute (Board chessboard);//, LocalGameState localGameState);
    void undo (Board chessboard, LocalGameState localGameState);
}
