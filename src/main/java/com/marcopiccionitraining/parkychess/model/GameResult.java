package com.marcopiccionitraining.parkychess.model;
import lombok.NonNull;
public record GameResult(PlayerColor winner, GameEndReason gameEndReason) {

    @NonNull
    @Override
    public String toString() {
        if (winner.equals(PlayerColor.UNDETERMINED)){
            return gameEndReason.toString();
        }
        return "Winner: " + winner +
                ", game end reason: " + gameEndReason;
    }
}
