package com.marcopiccionitraining.parkychess.model;

public enum GameEndReason {
    CHECKMATE, STALEMATE_DRAW, FIFTY_MOVES_RULE_DRAW, INSUFFICIENT_MATERIAL_DRAW,
    THREEFOLD_REPETITION_DRAW, AGREED_DRAW, RESIGNATION;

    @Override
    public String toString() {
        switch (this){
            case CHECKMATE -> { return "Checkmate."; }
            case STALEMATE_DRAW -> {return "Draw (stalemate)."; }
            case FIFTY_MOVES_RULE_DRAW -> {return "Draw (50 moves rule).";}
            case INSUFFICIENT_MATERIAL_DRAW -> {return "Draw (not enough pieces for a decisive result).";}
            case THREEFOLD_REPETITION_DRAW -> {return "Draw (the current position was reached 3 times).";}
            case AGREED_DRAW -> {return "Draw (by agreement).";}
            case RESIGNATION -> {return "Resignation.";}
        }
        return "GameEndReason{}";
    }
}
