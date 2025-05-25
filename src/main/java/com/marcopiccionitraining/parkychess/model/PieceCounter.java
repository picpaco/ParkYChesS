package com.marcopiccionitraining.parkychess.model;

import com.marcopiccionitraining.parkychess.model.pieces.PieceNames;
import lombok.Getter;

import java.util.HashMap;

public class PieceCounter {
    private final HashMap<PieceNames, Integer> blackPiecesCounter = new HashMap<>();
    private final HashMap<PieceNames, Integer> whitePiecesCounter = new HashMap<>();
    @Getter
    private int totalNumberOfPieces;

    public PieceCounter(){
        blackPiecesCounter.put(PieceNames.PAWN, 0);
        blackPiecesCounter.put(PieceNames.ROOK, 0);
        blackPiecesCounter.put(PieceNames.KNIGHT, 0);
        blackPiecesCounter.put(PieceNames.BISHOP, 0);
        blackPiecesCounter.put(PieceNames.QUEEN, 0);
        blackPiecesCounter.put(PieceNames.KING, 0);
        whitePiecesCounter.put(PieceNames.PAWN, 0);
        whitePiecesCounter.put(PieceNames.ROOK, 0);
        whitePiecesCounter.put(PieceNames.KNIGHT, 0);
        whitePiecesCounter.put(PieceNames.BISHOP, 0);
        whitePiecesCounter.put(PieceNames.QUEEN, 0);
        whitePiecesCounter.put(PieceNames.KING, 0);
        assert checkInvariant() : "Class invariant failure. Total number of pieces is wrong or there is some wrong key or value.";
    }

    public void incrementNumberOfPiecesForColor (PlayerColor playerColor, PieceNames pieceKey){
        if (playerColor.equals(PlayerColor.BLACK)){
            blackPiecesCounter.put(pieceKey, blackPiecesCounter.get(pieceKey) + 1);
        } else {
            if (playerColor.equals(PlayerColor.WHITE)) {
                whitePiecesCounter.put(pieceKey, whitePiecesCounter.get(pieceKey) + 1);
            }
        }
        totalNumberOfPieces++;
        assert checkInvariant() : "Class invariant failure. Total number of pieces is wrong or there is some wrong key or value.";
    }
    public int getBlackNumberOfPiecesNamed(PieceNames pieceNames){
        return blackPiecesCounter.get(pieceNames);
    }

    public int getWhiteNumberOfPiecesNamed(PieceNames pieceNames){
        return whitePiecesCounter.get(pieceNames);
    }

    @Override
    public String toString() {
        return "Total number of pieces = " + totalNumberOfPieces;
    }

    private boolean checkInvariant(){
        return totalNumberOfPieces <= 32 && blackPiecesCounter.size() <= 6 && whitePiecesCounter.size() <= 6 &&
                blackPiecesCounter.containsKey(PieceNames.KING) && whitePiecesCounter.containsKey(PieceNames.KING) &&
                (blackPiecesCounter.containsKey(PieceNames.PAWN) || blackPiecesCounter.containsKey(PieceNames.ROOK) ||
                        blackPiecesCounter.containsKey(PieceNames.KNIGHT) || blackPiecesCounter.containsKey(PieceNames.BISHOP) ||
                        blackPiecesCounter.containsKey(PieceNames.QUEEN)) && (whitePiecesCounter.containsKey(PieceNames.PAWN) ||
                whitePiecesCounter.containsKey(PieceNames.ROOK) || whitePiecesCounter.containsKey(PieceNames.KNIGHT) ||
                whitePiecesCounter.containsKey(PieceNames.BISHOP) || whitePiecesCounter.containsKey(PieceNames.QUEEN));
    }
}

