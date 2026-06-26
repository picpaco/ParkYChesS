package com.marcopiccionitraining.parkychess.model;

import lombok.extern.slf4j.Slf4j;

/**
 * Player colors, also indicating color which has to move next and the color of the winner.
 * UNDETERMINED is used to indicate that there is no winner (the game ended in a draw).
 */
@Slf4j
public enum PlayerColor {
    WHITE, BLACK, UNDETERMINED;
    public static PlayerColor getOpponentColor(PlayerColor currentPlayerColor){
        if (currentPlayerColor.equals(BLACK)){
            log.debug("Getting opponent color (from black to white)");
            return WHITE;
        } else if (currentPlayerColor.equals(WHITE)){
            log.debug("Getting opponent color (from white to black)");
            return BLACK;
        }
        return UNDETERMINED;
    }
}
