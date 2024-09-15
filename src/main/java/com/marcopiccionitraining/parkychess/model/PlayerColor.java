package com.marcopiccionitraining.parkychess.model;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Player colors, also indicating color which has to move next and the color of the winner.
 * UNDETERMINED is used to indicate that there is no winner (the game ended in a draw).
 */
public enum PlayerColor {
    WHITE, BLACK, UNDETERMINED;
    public static PlayerColor getOpponentColor(PlayerColor currentPlayerColor){
        if (currentPlayerColor.equals(BLACK)){
            return WHITE;
        } else if (currentPlayerColor.equals(WHITE)){
            return BLACK;
        }
        return UNDETERMINED;
    }
}
