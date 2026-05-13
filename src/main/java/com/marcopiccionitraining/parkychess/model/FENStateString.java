package com.marcopiccionitraining.parkychess.model;

import com.marcopiccionitraining.parkychess.model.moves.MoveNames;
import com.marcopiccionitraining.parkychess.model.pieces.Piece;
import com.marcopiccionitraining.parkychess.model.pieces.PieceNames;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import java.util.Objects;

@Slf4j
public final class FENStateString {
    @NonNull
    private final StringBuilder customFENStringImpl = new StringBuilder();

    public FENStateString(@NonNull PlayerColor playerColor, Board chessboard) {
        addPiecePlacementInfo(chessboard);
        customFENStringImpl.append(' ');
        if (playerColor.equals(PlayerColor.WHITE)) {
            customFENStringImpl.append("w");
        } else {
            customFENStringImpl.append("b");
        }
        customFENStringImpl.append(' ');
        addCastlingRights(chessboard);
        customFENStringImpl.append(' ');
        addEnPassantData(chessboard, chessboard.getCurrentPlayerColor());
        log.debug("Constructed FEN state string: {}", this);
    }

    private char getCharForPiece(@NonNull Piece piece) {
        char charForPiece = ' ';
        PieceNames pieceNames = piece.getName();
        switch (pieceNames) {
            case PAWN:
                charForPiece = 'p';
                break;
            case ROOK:
                charForPiece = 'r';
                break;
            case BISHOP:
                charForPiece = 'b';
                break;
            case KNIGHT:
                charForPiece = 'n';
                break;
            case KING:
                charForPiece = 'k';
                break;
            case QUEEN:
                charForPiece = 'q';
                break;
            default:
                assert false : "Unrecognizable piece name: " + pieceNames;
        }
        if (piece.getColor().equals(PlayerColor.WHITE)) {
            return Character.toUpperCase(charForPiece);
        }
        return charForPiece;
    }

    private void addRowOfData(@NonNull Board chessboard, int row) {
        int numberOfEmptyPositions = 0;
        for (int col = 0; col < 8; col++) {
            if (chessboard.getPiece(row, col) == null) {
                numberOfEmptyPositions++;
                continue;
            }
            if (numberOfEmptyPositions > 0) {
                customFENStringImpl.append(numberOfEmptyPositions);
                numberOfEmptyPositions = 0;
            }
            customFENStringImpl.append(getCharForPiece(chessboard.getPiece(row, col)));
        }
        if (numberOfEmptyPositions > 0) {
            customFENStringImpl.append(numberOfEmptyPositions);
        }
    }

    private void addPiecePlacementInfo(@NonNull Board chessboard) {
        for (int row = 0; row < 8; row++) {
            if (row != 0) {
                customFENStringImpl.append("/");
            }
            addRowOfData(chessboard, row);
        }
    }

    private void addCastlingRights(@NonNull Board chessboard) {
        boolean castleWKS = chessboard.isKingsideCastlingPossible(PlayerColor.WHITE);
        boolean castleWQS = chessboard.isQueensideCastlingPossible(PlayerColor.WHITE);
        boolean castleBKS = chessboard.isKingsideCastlingPossible(PlayerColor.BLACK);
        boolean castleBQS = chessboard.isQueensideCastlingPossible(PlayerColor.BLACK);
        if (!(castleWKS || castleWQS || castleBKS || castleBQS)) {
            customFENStringImpl.append('-');
            return;
        }
        if (castleWKS) {
            customFENStringImpl.append('K');
        }
        if (castleWQS) {
            customFENStringImpl.append('Q');
        }
        if (castleBKS) {
            customFENStringImpl.append('k');
        }
        if (castleBQS) {
            customFENStringImpl.append('q');
        }
    }

    private void addEnPassantData (@NonNull Board chessboard, @NonNull PlayerColor playerColor) {
        if (chessboard.getLastExecutedMove().getName().equals(MoveNames.DOUBLE_PAWN)) {
            Position skippedPos = chessboard.getPawnSkippedPosition(PlayerColor.getOpponentColor(playerColor));
            if (skippedPos != null) {
                char file = (char) ('a' + skippedPos.getColumn());
                int rank = 8 - skippedPos.getRow();
                customFENStringImpl.append(file);
                customFENStringImpl.append(rank);
                log.debug("Added en passant data to FEN state string.");
            } else  {
                customFENStringImpl.append('-');
                log.debug("Skipped position is null: added '-' to FEN state string.");
            }
        } else {
            customFENStringImpl.append('-');
            log.debug("Last executed move is not a double pawn move, but {} instead: added '-' to FEN state string.",
                    chessboard.getLastExecutedMove().getName());
        }
    }

    @Override
    public String toString(){
        return customFENStringImpl.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FENStateString fenStateString)) {
            return false;
        }
        return fenStateString.toString().equals(this.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(customFENStringImpl);
    }
}