package com.marcopiccionitraining.parkychess.model;

public enum GameEndReason {
    CHECKMATE, STALEMATE_DRAW, FIFTY_MOVES_RULE_DRAW, INSUFFICIENT_MATERIAL_DRAW,
    THREEFOLD_REPETITION_DRAW, AGREED_DRAW, RESIGNATION;

    @Override
    public String toString() {
        switch (this){
            case CHECKMATE -> { return "Checkmate!"; }
            case STALEMATE_DRAW -> {return "Draw because a stalemate occurred."; }
            case FIFTY_MOVES_RULE_DRAW -> {return "Draw because of the fifty moves rule";}
            case INSUFFICIENT_MATERIAL_DRAW -> {return "Draw because there are not eough pieces left for a checkmate to haooen.";}
            case THREEFOLD_REPETITION_DRAW -> {return "Draw because the position was reached three times.";}
            case AGREED_DRAW -> {return "A draw was agreed.";}
            case RESIGNATION -> {return "Resignation.";}
        }
        return "GameEndReason{}";
    }
}
