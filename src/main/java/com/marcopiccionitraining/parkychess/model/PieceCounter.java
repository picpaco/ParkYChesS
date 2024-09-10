package com.marcopiccionitraining.parkychess.model;

import java.util.HashMap;

public class PieceCounter {
    private final HashMap<PieceName, Integer> blackPiecesCounter = new HashMap<>();
    private final HashMap<PieceName, Integer> whitePiecesCounter = new HashMap<>();
    private int totalNumberOfPieces;

    public PieceCounter(){
        blackPiecesCounter.put(PieceName.PAWN, 0);
        blackPiecesCounter.put(PieceName.ROOK, 0);
        blackPiecesCounter.put(PieceName.KNIGHT, 0);
        blackPiecesCounter.put(PieceName.BISHOP, 0);
        blackPiecesCounter.put(PieceName.QUEEN, 0);
        blackPiecesCounter.put(PieceName.KING, 0);
        whitePiecesCounter.put(PieceName.PAWN, 0);
        whitePiecesCounter.put(PieceName.ROOK, 0);
        whitePiecesCounter.put(PieceName.KNIGHT, 0);
        whitePiecesCounter.put(PieceName.BISHOP, 0);
        whitePiecesCounter.put(PieceName.QUEEN, 0);
        whitePiecesCounter.put(PieceName.KING, 0);
        assert checkInvariant() : "Class invariant failure. Total number of pieces is wrong or there is some wrong key or value.";
    }
    public int getTotalNumberOfPieces() {
        return totalNumberOfPieces;
    }

    public void incrementNumberOfPiecesForColor (PlayerColor playerColor, PieceName pieceKey){
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
    public int getBlackNumberOfPiecesNamed(PieceName pieceName){
        return blackPiecesCounter.get(pieceName);
    }

    public int getWhiteNumberOfPiecesNamed(PieceName pieceName){
        return whitePiecesCounter.get(pieceName);
    }

    @Override
    public String toString() {
        return "Total number of pieces = " + totalNumberOfPieces;
    }

    private boolean checkInvariant(){
        return totalNumberOfPieces <= 32 && blackPiecesCounter.size() <= 6 && whitePiecesCounter.size() <= 6 &&
                blackPiecesCounter.containsKey(PieceName.KING) && whitePiecesCounter.containsKey(PieceName.KING) &&
                (blackPiecesCounter.containsKey(PieceName.PAWN) || blackPiecesCounter.containsKey(PieceName.ROOK) ||
                        blackPiecesCounter.containsKey(PieceName.KNIGHT) || blackPiecesCounter.containsKey(PieceName.BISHOP) ||
                        blackPiecesCounter.containsKey(PieceName.QUEEN)) && (whitePiecesCounter.containsKey(PieceName.PAWN) ||
                whitePiecesCounter.containsKey(PieceName.ROOK) || whitePiecesCounter.containsKey(PieceName.KNIGHT) ||
                whitePiecesCounter.containsKey(PieceName.BISHOP) || whitePiecesCounter.containsKey(PieceName.QUEEN));
    }
}

