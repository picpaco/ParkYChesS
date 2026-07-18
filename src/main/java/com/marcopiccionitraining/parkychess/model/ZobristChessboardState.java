package com.marcopiccionitraining.parkychess.model;

import com.marcopiccionitraining.parkychess.model.pieces.PieceNames;

import java.security.SecureRandom;

public class ZobristChessboardState {

    private static final SecureRandom pseudorandomSequence = new SecureRandom();
    /*
    The first array component is for color: 0 for white and 1 for black.
    The second array component is for the piece kind: 0 is for pawn, 1 for knight, 2 for bishop,
    3 is for rook, 4 is for queen, and 5 is for king.
    The third array component is to indicate the position the piece is on the cheesboard.
     */
    private static final long[][][] zobristArray = new long[2][6][64];

    private static final long[] zobristEnPassant = new long[8];
    /** The 4 array components represent k, q, K, Q
     */
    private static final long[] zobristCastleRights = new long[4];

    private static long zobristBlackToMove;

    static {
        fillZobristArrays();
    }

    private static void fillZobristArrays() {
        for (int pieceColor = 0; pieceColor < 2; pieceColor++) {
            for (int pieceType = 0; pieceType < 6; pieceType++) {
                for (int piecePosition = 0; piecePosition < 64; piecePosition++) {
                    zobristArray[pieceColor][pieceType][piecePosition] = pseudorandomSequence.nextLong();
                }
            }
        }
        for (int i = 0; i < 8; i++) {
            zobristEnPassant[i] = pseudorandomSequence.nextLong();
        }
        for (int i = 0; i < 4; i++) {
            zobristCastleRights[i] = pseudorandomSequence.nextLong();
        }
        zobristBlackToMove = pseudorandomSequence.nextLong();
    }
    /**
     * Conventions:
        The 1st array component [x][][] is for color: 0 for white, 1 for black.
        The 2nd array component [][x][] is for piece kind: 0: pawn, 1: knight, 2: bishop, 3: rook, 4: queen, 5: king.
        The 3rd array component [][][x] is for piece position on the chessboard.
         */
    public static long getZobristHash(LocalGameState localState) {
        long zobristHash = 0L;
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (!(localState.getChessboard().isEmpty(row, column))) {
                    if (localState.getChessboard().getPiece(row, column).getColor().equals(PlayerColor.WHITE)) {
                        if (localState.getChessboard().getPiece(row, column).getName().equals(PieceNames.PAWN)) {
                            zobristHash ^= zobristArray[0][0][8 * row + column];
                        } else if (localState.getChessboard().getPiece(row, column).getName().equals(PieceNames.KNIGHT)) {
                            zobristHash ^= zobristArray[0][1][8 * row + column];
                        } else if (localState.getChessboard().getPiece(row, column).getName().equals(PieceNames.BISHOP)) {
                            zobristHash ^= zobristArray[0][2][8 * row + column];
                        } else if (localState.getChessboard().getPiece(row, column).getName().equals(PieceNames.ROOK)) {
                            zobristHash ^= zobristArray[0][3][8 * row + column];
                        } else if (localState.getChessboard().getPiece(row, column).getName().equals(PieceNames.QUEEN)) {
                            zobristHash ^= zobristArray[0][4][8 * row + column];
                        } else if (localState.getChessboard().getPiece(row, column).getName().equals(PieceNames.KING)) {
                            zobristHash ^= zobristArray[0][5][8 * row + column];
                        }
                    } else {
                        if (localState.getChessboard().getPiece(row, column).getName().equals(PieceNames.PAWN)) {
                            zobristHash ^= zobristArray[1][0][8 * row + column];
                        } else if (localState.getChessboard().getPiece(row, column).getName().equals(PieceNames.KNIGHT)) {
                            zobristHash ^= zobristArray[1][1][8 * row + column];
                        } else if (localState.getChessboard().getPiece(row, column).getName().equals(PieceNames.BISHOP)) {
                            zobristHash ^= zobristArray[1][2][8 * row + column];
                        } else if (localState.getChessboard().getPiece(row, column).getName().equals(PieceNames.ROOK)) {
                            zobristHash ^= zobristArray[1][3][8 * row + column];
                        } else if (localState.getChessboard().getPiece(row, column).getName().equals(PieceNames.QUEEN)) {
                            zobristHash ^= zobristArray[1][4][8 * row + column];
                        } else if (localState.getChessboard().getPiece(row, column).getName().equals(PieceNames.KING)) {
                            zobristHash ^= zobristArray[1][5][8 * row + column];
                        }
                    }
                }
            }
        }
        for (int enPassantColumn = 0; enPassantColumn < 8; enPassantColumn++) {

            zobristHash ^= zobristEnPassant[enPassantColumn]; // epColumns in which the en passant location is.
        }
        if (localState.isBlackKingsideCastlingAllowed()) {
            zobristHash ^= zobristCastleRights[0];
        }
        if (localState.isBlackQueensideCastlingAllowed()) {
            zobristHash ^= zobristCastleRights[1];
        }
        if (localState.isWhiteKingsideCastlingAllowed()) {
            zobristHash ^= zobristCastleRights[2];
        }
        if (localState.isWhiteQueensideCastlingAllowed()) {
            zobristHash ^= zobristCastleRights[3];
        }
        if (localState.isBlacktoMove()){
            zobristHash ^= zobristBlackToMove;
        }
        return zobristHash;
    }
}