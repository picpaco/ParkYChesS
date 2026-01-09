package com.marcopiccionitraining.parkychess.model;

import com.marcopiccionitraining.parkychess.model.moves.DoublePawnMove;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import com.marcopiccionitraining.parkychess.model.moves.MoveNames;
import com.marcopiccionitraining.parkychess.model.pieces.King;
import com.marcopiccionitraining.parkychess.model.pieces.Piece;
import com.marcopiccionitraining.parkychess.model.pieces.PieceNames;
import com.marcopiccionitraining.parkychess.model.pieces.Rook;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
@Slf4j
public final class FENStateString {

    private StringBuilder customFENStringImpl;
    private final ChessGame gameState;
    @Getter @Setter
    private Move lastExecutedMove;
    @Getter @Setter
    private boolean isBlackQueensideRookFlaggedAsHavingAlreadyMoved;
    @Getter @Setter
    private boolean isWhiteQueensideRookFlaggedAsHavingAlreadyMoved;
    @Getter @Setter
    private boolean isBlackKingsideRookFlaggedAsHavingAlreadyMoved;
    @Getter @Setter
    private boolean isWhiteKingsideRookFlaggedAsHavingAlreadyMoved;
    @Getter @Setter
    private boolean isBlackKingFlaggedAsHavingAlreadyMoved;
    @Getter @Setter
    private boolean isWhiteKingFlaggedAsHavingAlreadyMoved;
// Yes, considering that capturedPiece is nullable because the move might not be a capture.
  //  @Getter @Setter
  //  private Piece movedPiece;
  //  @Getter @Setter
  //  private Piece capturedPiece;
    /**
     * The attribute capturedGhostPawnSquare is the landing square of the pawn which just made a double move.
     * If the en-passant capture conditions are satisfied, an en-passant capture can take place on the next move.
     */
    @Getter @Setter
    private Position capturedGhostPawnSquare;
    /**
     * The square on which an en passant capture will take place provided the necessary conditions are met.
     */
    @Getter @Setter
    private Position pawnSkipSquare;

    /**
     * The square where sits a rook still capable of castling.
     */
    @Getter @Setter
    private Position castlingRookFrom;

    /**
     * The square where will sit a rook once it has executed castling.
     */
    @Getter @Setter
    private Position castlingRookTo;

    public FENStateString(@NonNull ChessGame gameState) {
        this.gameState = gameState;
        customFENStringImpl = new StringBuilder();
        addPiecePlacementInfo(gameState.getChessboard());
        customFENStringImpl.append(' ');
        if (gameState.getChessboard().getCurrentPlayerColor() == PlayerColor.WHITE) {
            customFENStringImpl.append("w");
        } else {
            customFENStringImpl.append("b");
        }
        customFENStringImpl.append(' ');
        log.trace("Adding castling rights.");
        addCastlingRights(gameState.getChessboard());
        customFENStringImpl.append(' ');
        log.trace("Adding en-passant data.");
        addEnPassantData(gameState.getChessboard());
        log.trace("Adding number of half moves since last capture or pawn move.");
        customFENStringImpl.append(' ');
        customFENStringImpl.append(gameState.getNumberOfHalfMovesSinceLastCaptureOrPawnMove());
        log.trace("Adding number of full moves in the game.");
        customFENStringImpl.append(' ');
        customFENStringImpl.append(gameState.getTotalNumberOfFullMoves());
        King king = gameState.getChessboard().getKing(gameState.getChessboard().getCurrentPlayerColor());
        log.trace("is {} King flagged as having already moved? ", gameState.getChessboard().getCurrentPlayerColor());
        if (king.getColor() == PlayerColor.WHITE) {
            isWhiteKingFlaggedAsHavingAlreadyMoved = king.isFlaggedAsHavingAlreadyMoved();
            log.trace("{}", isWhiteKingFlaggedAsHavingAlreadyMoved);
        } else {
            isBlackKingFlaggedAsHavingAlreadyMoved = king.isFlaggedAsHavingAlreadyMoved();
            log.trace("{}", isBlackKingFlaggedAsHavingAlreadyMoved);
        }
        Piece maybeBlackKingsideRook = gameState.getChessboard().getPiece(new Position(0,7));
        if (maybeBlackKingsideRook != null) {
            if (maybeBlackKingsideRook.getName().equals(PieceNames.ROOK) && maybeBlackKingsideRook.getColor().equals(PlayerColor.BLACK)) {
                isBlackKingsideRookFlaggedAsHavingAlreadyMoved = maybeBlackKingsideRook.isFlaggedAsHavingAlreadyMoved();
                castlingRookFrom = maybeBlackKingsideRook.getPosition();
                castlingRookTo = Objects.requireNonNull(Objects.requireNonNull(castlingRookFrom.stepTowardsDirection(Direction.WEST)).
                        stepTowardsDirection(Direction.WEST));
            }
        }
        Piece maybeBlackQueensideRook = gameState.getChessboard().getPiece(new Position(0,0));
        if (maybeBlackQueensideRook != null) {
            if (maybeBlackQueensideRook.getName().equals(PieceNames.ROOK) &&
                    maybeBlackQueensideRook.getColor().equals(PlayerColor.BLACK)) {
                isBlackQueensideRookFlaggedAsHavingAlreadyMoved = maybeBlackQueensideRook.isFlaggedAsHavingAlreadyMoved();
                castlingRookFrom = maybeBlackQueensideRook.getPosition();
                castlingRookTo = Objects.requireNonNull(Objects.requireNonNull(castlingRookFrom.stepTowardsDirection(Direction.EAST)).
                        stepTowardsDirection(Direction.EAST)).stepTowardsDirection(Direction.EAST);
            }
        }
        Piece maybeWhiteKingsideRook = gameState.getChessboard().getPiece(new Position(7,7));
        if (maybeWhiteKingsideRook != null) {
            if (maybeWhiteKingsideRook.getName().equals(PieceNames.ROOK) &&
                    maybeWhiteKingsideRook.getColor().equals(PlayerColor.WHITE)) {
                isWhiteKingsideRookFlaggedAsHavingAlreadyMoved = maybeWhiteKingsideRook.isFlaggedAsHavingAlreadyMoved();
                castlingRookFrom = maybeWhiteKingsideRook.getPosition();
                castlingRookTo = Objects.requireNonNull(Objects.requireNonNull(castlingRookFrom.stepTowardsDirection(Direction.WEST)).
                        stepTowardsDirection(Direction.WEST));
            }
        }
        Piece maybeWhiteQueensideRook = gameState.getChessboard().getPiece(new Position(7,0));
        if (maybeWhiteQueensideRook != null) {
            if (maybeWhiteQueensideRook.getName().equals(PieceNames.ROOK) && maybeWhiteQueensideRook.getColor().equals(PlayerColor.WHITE)) {
                isWhiteQueensideRookFlaggedAsHavingAlreadyMoved = maybeWhiteQueensideRook.isFlaggedAsHavingAlreadyMoved();
                castlingRookFrom = maybeWhiteQueensideRook.getPosition();
                castlingRookTo = Objects.requireNonNull(Objects.requireNonNull(castlingRookFrom.stepTowardsDirection(Direction.EAST)).
                        stepTowardsDirection(Direction.EAST)).stepTowardsDirection(Direction.EAST);
            }
        }
        lastExecutedMove = gameState.getChessboard().getLastExecutedMove();
        log.debug("Set lastExecutedMove to {}", lastExecutedMove);
        log.debug("Constructed FEN string: {}", customFENStringImpl);
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
//TODO: Try here to assign a value to castlingRookFrom and castlingRookTo
    private void addCastlingRights(@NonNull Board chessboard) {
    //    King blackKing = chessboard.getKing(PlayerColor.BLACK);
    //    King whiteKing = chessboard.getKing(PlayerColor.WHITE);
        //log.trace(chessboard.toString());
        int castlingCounter = 0;
        Piece maybeWhiteKingsideRook = chessboard.getPiece(7, 7);
        if (maybeWhiteKingsideRook != null && (maybeWhiteKingsideRook.getName().equals(PieceNames.ROOK))) {
         //   Rook whiteKingsideRook = (Rook) chessboard.getPiece(7, 7);

            customFENStringImpl.append("K");
            castlingCounter++;
        }
        Piece maybeWhiteQueensideRook = chessboard.getPiece(7, 0);
        if (maybeWhiteQueensideRook != null && (maybeWhiteQueensideRook.getName().equals(PieceNames.ROOK))) {
            Rook whiteQueensideRook = (Rook) chessboard.getPiece(7, 0);
            customFENStringImpl.append("Q");
            castlingCounter++;
        }
        Piece maybeBlackKingsideRook = chessboard.getPiece(0, 7);
        if (maybeBlackKingsideRook != null && (maybeBlackKingsideRook.getName().equals(PieceNames.ROOK))) {
            Rook blackKingsideRook = (Rook) chessboard.getPiece(0, 7);
            customFENStringImpl.append("k");
            castlingCounter++;
        }
        Piece maybeBlackQueensideRook = chessboard.getPiece(0, 0);
        if (maybeBlackQueensideRook != null && (maybeBlackQueensideRook.getName().equals(PieceNames.ROOK))) {
            Rook blackQueensideRook = (Rook) chessboard.getPiece(0, 0);
            customFENStringImpl.append("q");
            castlingCounter++;
        }
        // Question: what about castling path free, king not ending up in check,
        // and king not being in check along castling path?
        // These are temporary conditions, should they be included in the state string?
        // They could, I choose not to include them for now.
        if (castlingCounter == 0) {
            customFENStringImpl.append("-");
        }
    }

    private void addEnPassantData (@NonNull Board chessboard) {
        if (lastExecutedMove == null) {
            customFENStringImpl.append("-");
            return;
        }
        if (lastExecutedMove.getName().equals(MoveNames.EMPTY)) {
            customFENStringImpl.append("-");
            return;
        }
        if (!(lastExecutedMove.getName().equals(MoveNames.DOUBLE_PAWN))){
            customFENStringImpl.append("-");
            return;
        }
        DoublePawnMove doublePawnMove = (DoublePawnMove) lastExecutedMove;
        Position skippedPawnPosition = doublePawnMove.getSkippedPawnPosition();
        log.trace("Adding en passant data on a newly created FENStateString: opposite player color is {} " +
                        "doublePawnMove is {}, and skippedPawnPosition is {} ",
                PlayerColor.getOpponentColor(chessboard.getCurrentPlayerColor()), doublePawnMove, skippedPawnPosition);
        if ((chessboard.getCurrentPlayerColor().equals(PlayerColor.BLACK) && skippedPawnPosition.row() == 2) ||
        (chessboard.getCurrentPlayerColor().equals(PlayerColor.WHITE) && skippedPawnPosition.row() == 5)) {
            chessboard.setPawnSkippedPosition(chessboard.getCurrentPlayerColor(), skippedPawnPosition);
            char columnInAlgebraic = (char) ('a' + skippedPawnPosition.column());
            int rowInAlgebraic = 8 - skippedPawnPosition.row();
            customFENStringImpl.append(columnInAlgebraic);
            customFENStringImpl.append(rowInAlgebraic);
        } else{
            customFENStringImpl.append("-");
        }
    }
    @NonNull
    public String getCompactFENStateString() {
        StringBuilder compactStringBuilder = new StringBuilder(customFENStringImpl.toString());
        String compactString = compactStringBuilder.delete(compactStringBuilder.length() - 4,
                compactStringBuilder.length()).toString();
        log.trace("Compact string: {}", compactString);
        return compactString;
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
   //     String shortenedCustomFENStringImpl = customFENStringImpl.delete(customFENStringImpl.length() - 4,
    //            customFENStringImpl.length()).toString();
      //  String shortenedCustomFENStringImplArg = fenStateString.customFENStringImpl.delete(fenStateString.customFENStringImpl.length() - 4,
      //          fenStateString.customFENStringImpl.length()).toString();
     //   log.error("shortenedCustomFENStringImpl: " + shortenedCustomFENStringImpl);
     //   log.error("shortenedCustomFENStringImplArg: " + shortenedCustomFENStringImplArg);
 //       assert shortenedCustomFENStringImpl.length() == customFENStringImpl.length() :
   //             "The 2 shortened strings should have the same length.";
   //     return Objects.equals(shortenedCustomFENStringImpl, shortenedCustomFENStringImplArg);
        return customFENStringImpl.compareTo(fenStateString.customFENStringImpl) == 0;
    }

    /**
     * @return a copy of the current state.
     */
    @NonNull //FIXME use a copy constructor instead? Do we need a copy feature at all?
    public FENStateString copy (Board chessboard) {
        log.trace("Making a copy of the state.");
        FENStateString stateCopy = new FENStateString(gameState);
        stateCopy.customFENStringImpl = new StringBuilder(customFENStringImpl);
     //   stateCopy.movedPiece = movedPiece.copy();
    //    stateCopy.capturedPiece = capturedPiece.copy();
        stateCopy.capturedGhostPawnSquare = capturedGhostPawnSquare;
        stateCopy.isBlackKingsideRookFlaggedAsHavingAlreadyMoved = isBlackKingsideRookFlaggedAsHavingAlreadyMoved;
        stateCopy.isWhiteKingsideRookFlaggedAsHavingAlreadyMoved = isWhiteKingsideRookFlaggedAsHavingAlreadyMoved;
        stateCopy.isBlackQueensideRookFlaggedAsHavingAlreadyMoved = isBlackQueensideRookFlaggedAsHavingAlreadyMoved;
        stateCopy.isWhiteQueensideRookFlaggedAsHavingAlreadyMoved = isWhiteQueensideRookFlaggedAsHavingAlreadyMoved;
        stateCopy.isBlackKingFlaggedAsHavingAlreadyMoved = isBlackKingFlaggedAsHavingAlreadyMoved;
        stateCopy.isWhiteKingFlaggedAsHavingAlreadyMoved = isWhiteKingFlaggedAsHavingAlreadyMoved;
        stateCopy.lastExecutedMove = lastExecutedMove;
        return stateCopy;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(customFENStringImpl);
    }
}