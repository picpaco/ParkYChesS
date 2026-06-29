package com.marcopiccionitraining.parkychess.model;

import com.marcopiccionitraining.parkychess.model.moves.*;
import com.marcopiccionitraining.parkychess.model.pieces.King;
import com.marcopiccionitraining.parkychess.model.pieces.Pawn;
import com.marcopiccionitraining.parkychess.model.pieces.Piece;
import com.marcopiccionitraining.parkychess.model.pieces.PieceNames;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import java.util.*;
/** An object of this class represents a chessboard.
 * Assumption made associating coordinates to chessboard squares:
 * (0,0) is a8 and is located top left; (0,7) is h8 and is located top right.
 * (7,0) is a1 and is located bottom left; (7,7) is h1 and is located bottom right.
 */
@Slf4j
public class Board {
    /**
     * Number of squares in a chessboard row.
     */
    private static final int CHESSBOARD_ROW_SIZE = 8;
    /**
     * Number of squares in a chessboard column.
     */
    private static final int CHESSBOARD_COLUMN_SIZE = 8;
    /**
     * Default max depth search.
     */
    public static final int MAX_DEPTH = 14;

    /**
     * Internal representation of the current chessboard as an array of arrays of squares.
     * Each square can be empty or contain a piece.
     */
    private final Piece[][] pieces = new Piece[CHESSBOARD_ROW_SIZE][CHESSBOARD_COLUMN_SIZE];

    /**
     * List of pieces which are giving check.
     */
    @Getter
    private final ArrayList<Piece> checkingPieces = new ArrayList<>();

    /**
     * Custom stack data structure used to save game state and restore it when needed.
     */
    @Getter @Setter
    private GameStateContainer gameStateContainer;

    /**
     * Default size for saved game state.
     */
    public static final int MAX_CONTAINER_SIZE = 50;
    /**
     * Given a valid game position, if the player who has the move makes a double pawn move, then the first position
     * associated to the double move is defined as pawnSkippedPosition.
     * The following HashMap has as key the color (White or Black), and as value the associated pawnSkippedPosition.
     * The association can be recorded in the HashMap either when a double pawn move is created or when declared by a FEN.
     * Example: if d2d4 is white's move, the HashMap will contain {White --> d3, Black --> null}.
     */
    private final HashMap<PlayerColor, Position> pawnSkippedPositionByColor = new HashMap<>();

    /**
     * Position on which an en passant capture may legally take place on the next move.
     * @param color: player which just made a double pawn move making an en passant capture possible on next move.
     */
    public Position getPawnSkippedPosition(@NonNull PlayerColor color) {
        return pawnSkippedPositionByColor.get(color);
    }
    /**
     * Set a skipped position for en passant capture.
     * @param color: color of player which just made a double pawn move making an en passant capture possible on next move.
     * @param position: skipped position, also called en passant capture position.
     */
    public void setSkippedPawnPosition(@NonNull PlayerColor color, @NonNull Position position) {
        assert (((color.equals(PlayerColor.WHITE)) && ((position.getRow() == 5))) ||
                ((color.equals(PlayerColor.BLACK)) && ((position.getRow() == 2)))) :
                "If there is a pawn skipped position, it must be on row 5 for White and on row 2 for Black. " +
                        "\nHowever, this method was erroneously invoked on row " + position.getRow() + " for " + color;
        pawnSkippedPositionByColor.put(color, position);
        log.debug("Just set pawnSkippedPositionByColor to {} and position to {}", color, position);
    }
    /**
     * Last executed move. Useful to determine if a castling or an en passant capture are valid.
     */
    @Getter @Setter
    private Move lastExecutedMove;
    /**
     * Is black kingside castling possible according to info from FEN string?
     */
    @Getter @Setter
    private boolean isBlackKingsideCastlingPossibleFromFEN;
    /**
     * Is black queenside castling possible according to info from FEN string?
     */
    @Getter @Setter
    private boolean isBlackQueensideCastlingPossibleFromFEN;
    /**
     * Is white kingside castling possible according to info from FEN string?
     */
    @Getter @Setter
    private boolean isWhiteKingsideCastlingPossibleFromFEN;
    /**
     * Is white queenside castling possible according to info from FEN string?
     */
    @Getter @Setter
    private boolean isWhiteQueensideCastlingPossibleFromFEN;
    /**
     * Current player color
     */
    @Getter @Setter
    private PlayerColor currentPlayerColor;

    public void switchCurrentPlayerColor() {
        log.trace("Switching color from {} to {}", currentPlayerColor, PlayerColor.getOpponentColor(currentPlayerColor));
        currentPlayerColor = (currentPlayerColor.equals(PlayerColor.WHITE) ? PlayerColor.BLACK : PlayerColor.WHITE);
    }

    public Board (PlayerColor currentColor) {
        log.debug("Empty board: {}", this);
        this.currentPlayerColor = currentColor;
        lastExecutedMove = new EmptyMove();
        gameStateContainer = new GameStateContainer(MAX_CONTAINER_SIZE);
        assert gameStateContainer.isEmpty() : "A game state object must be empty at game start.";
    }

    /**
     * @param position: the position on the chessboard.
     * @return null if there is no piece at position,
     * the piece at position otherwise.
     */
    public Piece getPiece(@NonNull Position position) {
        assert (position.isInsideBoard()) : "Position should be within chessboard bounds. Position:" + position;
            return pieces[position.getRow()][position.getColumn()];
        }

    public Piece getPiece(int row, int column) {
        assert (0 <= row) && (row < 8) && (0 <= column) && column < 8 :
                "Row and column should be within chessboard bounds.";
        return pieces[row][column];
    }

    public void setPiece(@NonNull Piece piece, @NonNull Position position) {
        assert (0 <= position.getRow()) && (position.getRow() < 8) && (0 <= position.getColumn()) && position.getColumn() < 8 :
                "Position should be within chessboard bounds.";
        pieces[position.getRow()][position.getColumn()] = piece;
    }

    public void setPiece (@NonNull Piece piece, int row, int column) {
        assert 0 <= row && row < 8 && 0 <= column && column < 8 : "Row and column should be within chessboard bounds.";
        pieces[row][column] = piece;
    }

    public void setEmpty (int row, int column) {
        assert new Position(row, column).isInsideBoard() : "position must be within this board.";
        pieces[row][column] = null;
    }

    /**
     *
     * @param position on the chessboard.
     * @return true if there is not a piece at position, false otherwise.
     */
    public boolean isEmpty(@NonNull Position position) {
        assert position.isInsideBoard() : "Position should be within chessboard bounds.";
            return pieces[position.getRow()][position.getColumn()] == null;
    }
    /**
     * @param row the position's row.
     * @param column the position's column.
     * @return true if there is not a piece at position, false otherwise.
     */
    public boolean isEmpty(int row, int column) {
        assert 0 <= row && row <= 7 && 0 <= column && column <= 7 :
                "Row and column should be within chessboard bounds.";
        return (pieces[row][column] == null);
    }
    /**
     * @param playerColor is the color we are interested in.
     * @return a list containing all the board positions with 'playerColor' pieces.
     */
    public List<Position> getPiecesPositionsForColor(PlayerColor playerColor) {
        List<Position> filteredPiecesPositions = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (!isEmpty(row, column)) {
                    Position position = new Position(row, column);
                    Piece piece = getPiece(position);
                    if (piece.getColor().equals(playerColor)) {
                        filteredPiecesPositions.add(position);
                        log.debug("Piece {} has been added to the board at position {}.",
                                getPiece(position).toString(), position);
                    }
                }
            }
        }
        log.debug("{} pieces positions added for {}: {}", filteredPiecesPositions.size(),
                playerColor, filteredPiecesPositions);
        assert filteredPiecesPositions.size() <= 16 : "There cannot be more than 16 pieces per color.";
        return filteredPiecesPositions;
    }

    /**
     * @return a list containing all the board positions containing pieces.
     */
    public List<Position> getPiecesPositions() {
        List<Position> allPiecesPositions = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (!isEmpty(row, column)) {
                    allPiecesPositions.add(new Position(row, column));
                }
            }
        }
        assert allPiecesPositions.size() <= 32 : "There cannot be more than 32 pieces on the board." + this;
        return allPiecesPositions;
    }

    public boolean isPlayerInDoubleCheck(PlayerColor playerColor) {
        return isPlayerInCheck(playerColor) && checkingPieces.size() == 2;
    }

    /**
     * @param currentPlayerColor is the color of the player who must play the next move.
     * @return true if currentPlayerColor's king is in check.
     */
    public boolean isPlayerInCheck(PlayerColor currentPlayerColor) {
        checkingPieces.clear();
        List<Position> enemyPiecesPositions = getPiecesPositionsForColor(PlayerColor.getOpponentColor(currentPlayerColor));
        log.debug("{} pieces positions: {}", PlayerColor.getOpponentColor(currentPlayerColor), enemyPiecesPositions);
        for (Position pos : enemyPiecesPositions) {
            Piece enemyPiece = getPiece(pos);
            if (enemyPiece.canCaptureOpponentKing(pos, this)){
                log.debug("{} {} on {} can capture {} king.", PlayerColor.getOpponentColor(currentPlayerColor),
                        enemyPiece.getName(), pos, currentPlayerColor);
                log.debug("{}'s king is in check. Chessboard: {}", currentPlayerColor, this);
                checkingPieces.add(enemyPiece);
                if (checkingPieces.size() > 2) {
                    log.info("A king is in check {} times.. Checkers: {}", checkingPieces.size(), checkingPieces);
                    log.info("Position: {}", this);
                }
            } else {
                log.debug("Piece {} cannot capture {} king.", enemyPiece, currentPlayerColor);
            }
        }
        return !checkingPieces.isEmpty();
    }

    /**
     * @return a copy of the current board.
     */
    public Board copy() {
        log.debug("Making a copy of this chessboard: {}", this);
        Board boardCopy = new Board(currentPlayerColor);
        List<Position> positions = getPiecesPositions();
        log.debug("+positions: {}", positions);
        for (Position pos : positions) {
            boardCopy.setPiece((getPiece(pos).copy()), pos);
        }
        boardCopy.gameStateContainer = gameStateContainer.copy();
        log.debug("Board copy: {}", boardCopy);
        boardCopy.setLastExecutedMove(lastExecutedMove);
        log.debug("Last executed move which was copied: {}", boardCopy.lastExecutedMove);
        Position whiteSkippedPosition = pawnSkippedPositionByColor.get(PlayerColor.WHITE);
        if (whiteSkippedPosition != null) {
            log.debug("White skipped position: {}", whiteSkippedPosition);
            log.debug("About to invoke setSkippedPawnPosition ({}, {})", PlayerColor.WHITE, whiteSkippedPosition);
            boardCopy.setSkippedPawnPosition(PlayerColor.WHITE, whiteSkippedPosition);
        }
        Position blackSkippedPosition = pawnSkippedPositionByColor.get(PlayerColor.BLACK);
        if (blackSkippedPosition != null) {
            log.debug("Black skipped position: {}", blackSkippedPosition);
            log.debug("About to invoke setSkippedPawnPosition ({}, {})", PlayerColor.BLACK, blackSkippedPosition);
            boardCopy.setSkippedPawnPosition(PlayerColor.BLACK, blackSkippedPosition);
        }
        boardCopy.setBlackKingsideCastlingPossibleFromFEN(isBlackKingsideCastlingPossibleFromFEN);
        boardCopy.setBlackQueensideCastlingPossibleFromFEN(isBlackQueensideCastlingPossibleFromFEN);
        boardCopy.setWhiteQueensideCastlingPossibleFromFEN(isWhiteQueensideCastlingPossibleFromFEN);
        boardCopy.setWhiteKingsideCastlingPossibleFromFEN(isWhiteKingsideCastlingPossibleFromFEN);
        log.debug("Result of board copy: {}", boardCopy);
        assert this.equals(boardCopy) : "The board copy must be equal to this board.";
        return boardCopy;
    }

  /*  public void flip() {//TODO implement me}*/

    private PieceCounter getPiecesCounter() {
        PieceCounter counter = new PieceCounter();
        List<Position> positions = getPiecesPositions();
        assert positions.size() <= 32 : "There cannot be more than 32 pieces in the board.";
        log.info("{} pieces positions: {}", positions.size(), positions);
        for (Position piecePosition : positions) {
            Piece piece = getPiece(piecePosition);
            counter.incrementNumberOfPiecesForColor(piece.getColor(), piece.getName());
            log.info("{} pieces: {}", counter, piece);
        }
        return counter;
    }

    public boolean isInsufficientMaterialDraw() {
        PieceCounter pieceCounter = getPiecesCounter();
        return areOnlyKingsLeft(pieceCounter) || areKingVsKingAndBishopLeft(pieceCounter) ||
                areKingVsKingAndKnightLeft(pieceCounter) ||
                areKingAndBishopVsKingAndBishopOfOppositeColorsLeft(pieceCounter);
    }

    private boolean areOnlyKingsLeft(@NonNull PieceCounter pieceCounter) {
        return pieceCounter.getTotalNumberOfPieces() == 2;
    }

    private boolean areKingVsKingAndBishopLeft(@NonNull PieceCounter pieceCounter) {
        return pieceCounter.getTotalNumberOfPieces() == 3 &&
                (pieceCounter.getNumberOfBlackPiecesNamed(PieceNames.BISHOP) == 1 ||
                        pieceCounter.getNumberOfWhitePiecesNamed(PieceNames.BISHOP) == 1);
    }

    private boolean areKingVsKingAndKnightLeft(@NonNull PieceCounter pieceCounter) {
        return pieceCounter.getTotalNumberOfPieces() == 3 &&
                (pieceCounter.getNumberOfBlackPiecesNamed(PieceNames.KNIGHT) == 1 ||
                        pieceCounter.getNumberOfWhitePiecesNamed(PieceNames.KNIGHT) == 1);
    }

    private boolean areKingAndBishopVsKingAndBishopOfOppositeColorsLeft(@NonNull PieceCounter pieceCounter) {
        if (pieceCounter.getTotalNumberOfPieces() != 4) {
            return false;
        }
        if (pieceCounter.getNumberOfBlackPiecesNamed(PieceNames.BISHOP) != 1 ||
                pieceCounter.getNumberOfWhitePiecesNamed(PieceNames.BISHOP) != 1) {
            return false;
        }
        Position blackBishopPosition = findBishop(PlayerColor.BLACK);
        Position whiteBishopPosition = findBishop(PlayerColor.WHITE);
        if (blackBishopPosition != null && whiteBishopPosition != null) {
            return getColorAtPosition(blackBishopPosition).equals(getColorAtPosition(whiteBishopPosition));
        } else {
            return false;
        }
    }

    private Position findBishop(@NonNull PlayerColor playerColor) {
        for (Position piecePosition : getPiecesPositionsForColor(playerColor)) {
            if (getPiece(piecePosition).getName().equals(PieceNames.BISHOP)) {
                return piecePosition;
            }
        }
        return null;
    }

    public boolean isKingsideCastlingPossible(@NonNull PlayerColor playerColor) {
        if (playerColor.equals(PlayerColor.BLACK)) {
            if (isBlackKingsideCastlingPossibleFromFEN) {
                return isUnmovedKingAndRook(new Position(0, 4), new Position(0, 7));
            }
        } else {
            if (playerColor.equals(PlayerColor.WHITE)) {
                if (isWhiteKingsideCastlingPossibleFromFEN) {
                    return isUnmovedKingAndRook(new Position(7, 4), new Position(7, 7));
                }
            }
        }
        return false;
    }
    public boolean isQueensideCastlingPossible (@NonNull PlayerColor playerColor) {
        if (playerColor.equals(PlayerColor.BLACK)) {
            if (isBlackQueensideCastlingPossibleFromFEN) {
                return isUnmovedKingAndRook(new Position(0, 4), new Position(0, 0));
            }
        } else {
            if (playerColor.equals(PlayerColor.WHITE)) {
                if (isWhiteQueensideCastlingPossibleFromFEN) {
                    return isUnmovedKingAndRook(new Position(7, 4), new Position(7, 0));
                }
            }
        }
        return false;
    }

    private boolean isUnmovedKingAndRook(@NonNull Position kingInitialPosition,
                                         @NonNull Position rookInitialPosition) {
        if (isEmpty(kingInitialPosition) || isEmpty(rookInitialPosition)) {
            return false;
        }
        Piece king = getPiece(kingInitialPosition);
        Piece rook = getPiece(rookInitialPosition);
        if (!king.getName().equals(PieceNames.KING) || !rook.getName().equals(PieceNames.ROOK)) {
            return false;
        }
        return !king.isFlaggedAsHavingAlreadyMoved() && !rook.isFlaggedAsHavingAlreadyMoved();
    }

    public boolean canCaptureEnPassant(@NonNull PlayerColor playerColor) {
        assert playerColor.equals(currentPlayerColor) : "en passant capture should be assessed for current color.";
        log.error("Skipped position for enemy player {}: {}", PlayerColor.getOpponentColor(playerColor), getPawnSkippedPosition(PlayerColor.getOpponentColor(playerColor)));
        Position skippedPosition = getPawnSkippedPosition(PlayerColor.getOpponentColor(playerColor));
        if (skippedPosition == null) {
            log.error("{} cannot capture en passant because there is no skippedPosition for {}.", playerColor, PlayerColor.getOpponentColor(playerColor));
            return false;
        }
        if (lastExecutedMove == null) {
        log.error("{} cannot capture en passant because last executed move does not exist.",
                playerColor);
        return false;
        }
        if (!(lastExecutedMove.getName().equals(MoveNames.DOUBLE_PAWN))) {
            log.error("{} cannot capture en passant because last executed move was {} instead of a Double Pawn move.",
                    playerColor, lastExecutedMove);
            return false;
        }
        log.error("Analyzing {}'s possible attacking positions...", playerColor);
        List<Position> attackingPawnsFromPositions = PseudoLegalPiecesPositionsInitializer.
                getAttackingPawnsAssociatedWithSkippedPositions(skippedPosition, PlayerColor.getOpponentColor(playerColor));
        log.error("Positions from which {}'s attacking piece could capture: {}", playerColor,
                attackingPawnsFromPositions);
        Piece attackingPiece;
        Pawn attackingPawn;
        for (Position attackingPawnfromPosition : attackingPawnsFromPositions) {
            log.error("{}'s attacking pawn 'from' position: {}", playerColor, attackingPawnfromPosition);
            if (!(isEmpty(attackingPawnfromPosition))) {
                log.error("An attacking piece exists and it is on {}", attackingPawnfromPosition);
                attackingPiece = getPiece(attackingPawnfromPosition);
                if (!(attackingPiece.getName().equals(PieceNames.PAWN))) {
                    log.error("{} cannot capture en passant because attacking piece {} is not a pawn.",
                            playerColor, attackingPiece);
                    continue;
                }
                attackingPawn = (Pawn) getPiece(attackingPawnfromPosition);
                if (!(attackingPawn.getColor().equals(playerColor))) {
                    log.error("The attacking pawn's color {} should be the same as current player color {}.",
                            attackingPawn.getColor(), playerColor);
                    continue;
                }
                log.error("Considering possible targets for attacking pawn {}.", attackingPawn);
                List<Position> pawnCaptureToPositions = PseudoLegalPiecesPositionsInitializer.
                        getPawnCapturePseudoLegalPositions(attackingPawnfromPosition, playerColor);
                for (Position pawnCaptureToPosition : pawnCaptureToPositions) {
                    if (!(pawnCaptureToPosition.equals(skippedPosition))) {
                        log.error("Attacking pawn {} cannot capture en passant on pawn capture 'to' position: {} " +
                                        "because this is not the same as skipped position: {}",
                                attackingPawn, pawnCaptureToPosition, skippedPosition);
                    } else {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Board board)) return false;
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (!(Objects.equals(pieces[row][column], board.pieces[row][column]))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(pieces);
    }

    @Override
    public String toString() {
        StringBuilder charView = new StringBuilder();
        charView.append("\n");
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (pieces[row][column] == null) {
                    charView.append("|___|");
                } else {
                    charView.append("|_");
                    charView.append((pieces[row][column]).toOneChar());
                    charView.append("_|");
                }
            }
            charView.append("\n");
        }
        return charView.toString();
    }

    public Position getKingPosition(PlayerColor playerColor) {
        List<Position> piecesPositions = getPiecesPositions();
        log.trace("Pieces positions are {}", piecesPositions);
        for (Position piecePosition : piecesPositions) {
            Piece piece = getPiece(piecePosition);
            if (piece.getName().equals(PieceNames.KING) && piece.getColor().equals(playerColor)) {
                return piecePosition;
            }
        }
        assert false : "I could not find a " + playerColor + " king on the chessboard";
        return null;
    }

    public King getKing(PlayerColor playerColor) {
        Position kingPosition = getKingPosition(playerColor);
        return (King) getPiece(kingPosition);
    }

    /**
     * @return the color of the chessboard square corresponding to position.
     */
    public PlayerColor getColorAtPosition(Position position) {
        if ((position.getRow() + position.getColumn()) % 2 == 0) {
            return PlayerColor.WHITE;
        } else {
            return PlayerColor.BLACK;
        }
    }
}