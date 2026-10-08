package com.marcopiccionitraining.parkychess.model.pieces;

public class SequentialIdGenerator {
    private static int id = 0;
    public static int getNextPieceId() {
        return id++;
    }
}
