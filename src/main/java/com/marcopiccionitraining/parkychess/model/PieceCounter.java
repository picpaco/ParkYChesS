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
        assert checkInvariant() : "Class invariant failure. Total number of pieces is wrong: " + totalNumberOfPieces + " is wrong or there is some wrong key or value.";
    }

    public void incrementNumberOfPiecesForColor (PlayerColor color, PieceNames pieceKey){
        if (color.equals(PlayerColor.BLACK)){
            Integer blackPiecesCount = blackPiecesCounter.get(pieceKey);
            int incrementedValue = blackPiecesCount + 1;
            blackPiecesCounter.put(pieceKey, incrementedValue);
        } else {
            Integer whitePiecesCount = whitePiecesCounter.get(pieceKey);
            int incrementedValue = whitePiecesCount + 1;
            whitePiecesCounter.put(pieceKey, incrementedValue);
        }
        totalNumberOfPieces++;
        assert checkInvariant() : "Class invariant failure. Total number of pieces is wrong: " + totalNumberOfPieces + " is wrong or there is some wrong key or value.";
    }
    public int getNumberOfBlackPiecesNamed(PieceNames pieceName){
        return blackPiecesCounter.get(pieceName);
    }

    public int getNumberOfWhitePiecesNamed(PieceNames pieceName){
        return whitePiecesCounter.get(pieceName);
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

