package com.marcopiccionitraining.parkychess.model;

public record GameResult(PlayerColor winner, GameEndReason gameEndReason) {
    /*   public GameResult win (PlayerColor winner, GameEndReason endReason){
        assert endReason == GameEndReason.CHECKMATE || endReason == GameEndReason.RESIGNATION :
                "A win can only be because of a checkmate or a resignation";
        return new GameResult(winner, endReason);
    }
    public GameResult draw (GameEndReason endReason){
        assert endReason == GameEndReason.AGREED_DRAW || endReason == GameEndReason.FIFTY_MOVES_RULE_DRAW ||
                endReason == GameEndReason.STALEMATE_DRAW || endReason == GameEndReason.THREEFOLD_REPETITION_DRAW ||
                endReason == GameEndReason.INSUFFICIENT_MATERIAL_DRAW : "A draw can only be because of an agreement," +
                " the fifty moves rule, stalemate, threefold repetition, or insufficient material.";
        return new GameResult(PlayerColor.UNDETERMINED, endReason);
    }*/

    @Override
    public String toString() {
        return "GameResult{" +
                "winner=" + winner +
                ", gameEndReason=" + gameEndReason +
                '}';
    }
}
