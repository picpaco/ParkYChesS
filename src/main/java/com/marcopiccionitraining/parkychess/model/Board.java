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
     * Default max depth search.
     * TODO: Leave as a default, move the setting in a configuration file.
     */
    public static final int MAX_DEPTH = 14;

    /**
     * Number of positions (squares) in a chessboard row.
     */
    private static final int CHESSBOARD_ROW_SIZE = 8;

    /**
     * Number of positions (squares) in a chessboard column.
     */
    private static final int CHESSBOARD_COLUMN_SIZE = 8;

    /**
     * Internal representation of the current chessboard as an array of arrays of squares.
     * Each square can be empty or contain a piece. This representation is called 'mailbox'.
     */
    private final Piece[][] pieces = new Piece[CHESSBOARD_ROW_SIZE][CHESSBOARD_COLUMN_SIZE];

    /**
     * Captured piece from last move, if any.
     * TODO: assess: is it really needed?
     */
    @Getter @Setter
    private Piece lastCapturedPiece;

    /**
     * Was piece which moved last flagged appropriately?
     * TODO: assess: is it really needed?
     */
    @Getter @Setter
    private boolean isPieceMovedLastFlaggedAsHavingAlreadyMoved;

    /**
     * List of pieces which are giving check.
     */
    @Getter
    private final ArrayList<Piece> checkerPieces = new ArrayList<>();

    /**
     * Given a valid game position, if the player who has the move executes a double pawn move,
     * then the first position (step) associated with the double move is defined as pawnSkippedPosition.
     * The HashMap used as implementation has the color (White or Black) as key, and
     * the associated pawnSkippedPosition as value.
     * The association between color and position will be recorded in the following two occasions:
     *  1. When a DoublePawnMove is executed.
     *  2. When a DoublePawnMove is implied by a FEN string.
     * Example 1: if d2d4 is white's move, the HashMap will contain {White --> d3, Black --> null}.
     * Example 2: if the following FEN is read: "R5k1/8/8/7P/8/8/8/7K b - h3 0 1",
     * it implies that last move was h2h4 by white and consequently the pawn skipped position is h3.
     */
    private final HashMap<PlayerColor, Position> pawnSkippedPositionByColor = new HashMap<>();

    /**
     * Game positions history where keys are unique numbers identifying a position and values
     * counting how many times that position has been reached.
     * It is used to check the 3-times game state repetition draw rule.
     */
    private final HashMap<Long, Integer> zobristChessboardStates;

    /**
     * Position on which an en passant capture may legally take place on the next move.
     * @param color: color of player who just made a double pawn move,
     *             making an en passant capture possible on next move.
     * @link pawnSkippedPositionByColor.
     */
    public Position getPawnSkippedPosition(@NonNull PlayerColor color) {
        return pawnSkippedPositionByColor.get(color);
    }

    /**
     * Set a skipped position for en passant capture.
     * @param color: color of player who just executed a double pawn move,
     *             making an en passant capture possible on the next move.
     * @param skippedPosition: pawn skipped position, also called
     *                       en passant capture skipped position.
     */
    public void setPawnSkippedPosition(@NonNull PlayerColor color, @NonNull Position skippedPosition) {
        assert (((color.equals(PlayerColor.WHITE)) && ((skippedPosition.getRow() == 5))) ||
                ((color.equals(PlayerColor.BLACK)) && ((skippedPosition.getRow() == 2)))) :
                "If there is a pawn skipped position, it must be on row 5 for White and on row 2 for Black. " +
                        "\nHowever, this method was erroneously invoked on row " + skippedPosition.getRow() + " for " + color;
        pawnSkippedPositionByColor.put(color, skippedPosition);
        log.debug("Just set color to {} and the corresponding skipped position to {}", color, skippedPosition);
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
     * Counter of semimoves, used for checking if the 50-move draw rule applies.
     */
    @Getter@Setter
    private int halfMoveCounter;

    /**
     * Color of player making next move.
     */
    @Getter @Setter
    private PlayerColor currentPlayerColor;

    /**
     * Current position of black king.
     */
    @Getter @Setter
    private Position blackKingPosition;

    /**
     * Current position of white king.
     */
    @Getter @Setter
    private Position whiteKingPosition;

    /**
     * Switch active player color to the opposite color.
     */
    public void switchCurrentPlayerColor() {
        currentPlayerColor = (currentPlayerColor.equals(PlayerColor.WHITE) ? PlayerColor.BLACK : PlayerColor.WHITE);
    }

    /**
     * Creates an empty board setting the active player.
     * @param currentlyActiveColor color of player playing the next move.
     */
    public Board (@NonNull PlayerColor currentlyActiveColor) {
        log.debug("Empty board: {}", this);
        this.currentPlayerColor = currentlyActiveColor;
        lastExecutedMove = new EmptyMove();
        zobristChessboardStates = new HashMap<>();
        log.error("After board initialization there are no keys in the database.");
    }

    public Long getNewZobristKey(@NonNull LocalGameState localGameState) {
        Long key = ZobristKeyProvider.getZobristHash(localGameState);
        localGameState.setZobristKey(key);
        log.error("Created zobrist key {} for local state {}", key, localGameState);
        return key;
    }

    public Integer getZobristValue (@NonNull Long zobristKey){
        assert containsZobristKey(zobristKey) : "Zobrist key should exist.";
        Integer value = zobristChessboardStates.get(zobristKey);
        log.error("Found {} occurences for key {}", value, zobristKey);
        return value;
    }

    public boolean containsZobristKey (@NonNull Long zobristKey){
        return zobristChessboardStates.containsKey(zobristKey);
    }

    public void saveZobristEntry(Long zobristKey, Integer numberOfOccurences){
        log.error("Position {} occurred {} times.", zobristKey, numberOfOccurences);
        zobristChessboardStates.put(zobristKey, numberOfOccurences);
    //    log.error("Entry {} saved. Number of keys present: {}. Entries list: {}.", zobristKey,
      //          zobristChessboardStates.size(), zobristChessboardStates);
        log.error("Entry {} saved. Number of keys present: {}.", zobristKey, zobristChessboardStates.size());
    }

    public Integer deleteZobristEntry (@NonNull Long zobristKey){
        assert containsZobristKey(zobristKey) : "Zobrist key should exist.";
        log.error("Deleting zobrist entry for key {}.", zobristKey);
        return zobristChessboardStates.remove(zobristKey);

    }

    public List<Long> getZobristListOfKeys(){
        return zobristChessboardStates.keySet().stream().toList();
    }

    public boolean areZobristEqual (long zobristKey1, long zobristKey2){
        return zobristKey1 == zobristKey2;
    }

    /**
     * @param position: the position on the chessboard.
     * @return null if there is no piece at position, the piece at position otherwise.
     */
    public Piece getPiece(@NonNull Position position) {
        assert (position.isInsideBoard()) : "Position should be within chessboard bounds. Position:" + position;
            return pieces[position.getRow()][position.getColumn()];
        }

    /**
     * @param row: the row on the chessboard where the piece is supposed to be.
     * @param column: the column on the chessboard where the piece is supposed to be.
     * @return null if there is no piece at position, the piece at position otherwise.
     */
    public Piece getPiece(int row, int column) {
        assert (0 <= row) && (row < 8) && (0 <= column) && column < 8 :
                "Row and column should be within chessboard bounds.";
        return pieces[row][column];
    }

    public void setPiece (@NonNull Piece piece, @NonNull Position position) {
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

    public void setEmpty (Position position) {
        assert position.isInsideBoard() : "position must be within this board.";
        pieces[position.getRow()][position.getColumn()] = null;
    }

    /**
     * @param row the position's row.
     * @param column the position's column.
     * @return true if there is not a piece at row and column, false otherwise.
     */
    public boolean isEmpty(int row, int column) {
        assert 0 <= row && row <= 7 && 0 <= column && column <= 7 :
                "Row and column should be within chessboard bounds.";
        return (pieces[row][column] == null);
    }
    /**
     * @param position on the chessboard.
     * @return true if there is not a piece at position, false otherwise.
     */
    public boolean isEmpty(@NonNull Position position) {
        assert position.isInsideBoard() : "Position should be within chessboard bounds.";
        return pieces[position.getRow()][position.getColumn()] == null;
    }

    /**
     * @param positionAsInt in this program, by convention, 0 represents the top left square.
     * @return true if there is not a piece at positionAsInt, false otherwise.
     */
    public boolean isEmpty(int positionAsInt){
        assert 0 <= positionAsInt && positionAsInt < 64 : "Position index should be within chessboard bounds.";
        return isEmpty(positionAsInt % 8, positionAsInt / 8);
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
                    Position position = new Position(row, column);
                    allPiecesPositions.add(position);
                }
            }
        }
        assert allPiecesPositions.size() <= 32 : "There cannot be more than 32 pieces on the board." + this;
        return allPiecesPositions;
    }

    public boolean isPlayerInDoubleCheck(PlayerColor playerColor) {
        return isPlayerInCheck(playerColor) && checkerPieces.size() == 2;
    }

    /**
     * @param currentPlayerColor is the color of the player who must play the next move.
     * @return true if currentPlayerColor's king is in check.
     */
    public boolean isPlayerInCheck(PlayerColor currentPlayerColor) {
        checkerPieces.clear();
        List<Position> enemyPiecesPositions = getPiecesPositionsForColor(PlayerColor.
                getOpponentColor(currentPlayerColor));
        log.debug("{} (enemy) pieces positions: {}", PlayerColor.getOpponentColor(currentPlayerColor), enemyPiecesPositions);
        for (Position pos : enemyPiecesPositions) {
            Piece enemyPiece = getPiece(pos);
            if (enemyPiece.canCaptureOpponentKing(pos, this)){
                log.debug("{} {} on {} can capture {} king.", PlayerColor.getOpponentColor(currentPlayerColor),
                        enemyPiece.getName(), pos, currentPlayerColor);
                checkerPieces.add(enemyPiece);
                log.debug("{}'s king is in check. Checker: {}", currentPlayerColor, checkerPieces);
                if (checkerPieces.size() > 1) {
                    log.debug("{}'s king is in double check. Checker: {}", currentPlayerColor, checkerPieces);
                    log.info("Position: {}", this);
                }
            } else {
                log.debug("{} king is not in check.", currentPlayerColor);
            }
        }
        return !checkerPieces.isEmpty();
    }

  /*  public void flip() {//TODO implement me!}*/

    private PieceCounter getPiecesCounter() {
        PieceCounter counter = new PieceCounter();
        List<Position> positions = getPiecesPositions();
        assert positions.size() <= 32 : "There cannot be more than 32 pieces on the board.";
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
                areKingVsKingAndKnightLeft(pieceCounter) || areKingAndTwoKnightsVsKingLeft(pieceCounter) ||
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

    private boolean areKingAndTwoKnightsVsKingLeft(@NonNull PieceCounter pieceCounter) {
        return pieceCounter.getTotalNumberOfPieces() == 4 &&
                (pieceCounter.getNumberOfBlackPiecesNamed(PieceNames.KNIGHT) == 2 ||
                        pieceCounter.getNumberOfWhitePiecesNamed(PieceNames.KNIGHT) == 2);
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
            return getSquareColorAtPosition(blackBishopPosition).equals(getSquareColorAtPosition(whiteBishopPosition));
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

    public boolean canCaptureEnPassant(@NonNull PlayerColor playerColor) {
        assert playerColor.equals(currentPlayerColor) : "en passant capture should be assessed for current color.";
        log.debug("Skipped position for enemy player {}: {}", PlayerColor.getOpponentColor(playerColor), getPawnSkippedPosition(PlayerColor.getOpponentColor(playerColor)));
        Position skippedPosition = getPawnSkippedPosition(PlayerColor.getOpponentColor(playerColor));
        if (skippedPosition == null) {
            log.debug("{} cannot capture en passant because there is no skippedPosition for {}.", playerColor, PlayerColor.getOpponentColor(playerColor));
            return false;
        }
        if (!(lastExecutedMove.getName().equals(MoveNames.DOUBLE_PAWN))) {
            log.debug("{} cannot capture en passant because last executed move was {} instead of a Double Pawn move.",
                    playerColor, lastExecutedMove);
            return false;
        }
        log.debug("Analyzing {}'s possible attacking positions...", playerColor);
        List<Position> attackingPawnsFromPositions = PseudoLegalPiecesPositionsInitializer.
                getAttackingPawnsAssociatedWithSkippedPositions(skippedPosition,
                        PlayerColor.getOpponentColor(playerColor));
        log.debug("Positions from which {}'s attacking piece could capture: {}", playerColor,
                attackingPawnsFromPositions);
        Piece attackingPiece;
        Pawn attackingPawn;
        for (Position attackingPawnfromPosition : attackingPawnsFromPositions) {
            log.debug("{}'s attacking pawn 'from' position: {}", playerColor, attackingPawnfromPosition);
            if (!(isEmpty(attackingPawnfromPosition))) {
                log.debug("An attacking piece exists and it is on {}", attackingPawnfromPosition);
                attackingPiece = getPiece(attackingPawnfromPosition);
                if (!(attackingPiece.getName().equals(PieceNames.PAWN))) {
                    log.debug("{} cannot capture en passant because attacking piece {} is not a pawn.",
                            playerColor, attackingPiece);
                    continue;
                }
                attackingPawn = (Pawn) getPiece(attackingPawnfromPosition);
                if (!(attackingPawn.getColor().equals(playerColor))) {
                    log.debug("The attacking pawn's color {} should be the same as current player color {}.",
                            attackingPawn.getColor(), playerColor);
                    continue;
                }
                log.debug("Considering possible targets for attacking pawn {}.", attackingPawn);
                List<Position> pawnCaptureToPositions = PseudoLegalPiecesPositionsInitializer.
                        getPawnCapturePseudoLegalPositions(attackingPawnfromPosition, playerColor);
                for (Position pawnCaptureToPosition : pawnCaptureToPositions) {
                    if (!(pawnCaptureToPosition.equals(skippedPosition))) {
                        log.debug("Attacking pawn {} cannot capture en passant on pawn capture 'to' position: {} " +
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
                    log.debug("equals failed for row {}, column {}", row, column);
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
        log.debug("Starting Board.toString()");
        StringBuilder charView = new StringBuilder();
        charView.append("\n");
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                log.debug("[{},{},{}]", row, column, pieces[row][column]);
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
        String s = charView.toString();
        log.debug("Using log.err: {}",s);
        return s;
    }

    public Position getKingPosition(PlayerColor playerColor) {
        if (playerColor.equals(PlayerColor.WHITE)){
            assert !(isEmpty(whiteKingPosition)) : "White king position " + whiteKingPosition +
                    " should not be empty.";;
            assert getPiece(whiteKingPosition).getName().equals(PieceNames.KING) :
                    "There must be a king on " + whiteKingPosition;
            assert getPiece(whiteKingPosition).getColor().equals(PlayerColor.WHITE) :
            "There must be a white king on " + whiteKingPosition;
            return whiteKingPosition;
        } else {
            assert !(isEmpty(blackKingPosition)) : "Black king position " + blackKingPosition +
                    " should not be empty.";
            assert getPiece(blackKingPosition).getName().equals(PieceNames.KING) :
                    "There should be a king on " + blackKingPosition;
            assert getPiece(blackKingPosition).getColor().equals(PlayerColor.BLACK) :
                    "There should be a black king on " + blackKingPosition;
            return blackKingPosition;
        }
    }

    public King getKing(PlayerColor playerColor) {
        Position kingPosition = getKingPosition(playerColor);
        return (King) getPiece(kingPosition);
    }

    /**
     * @return the color of the chessboard square corresponding to position.
     */
    public PlayerColor getSquareColorAtPosition(Position position) {
        if ((position.getRow() + position.getColumn()) % 2 == 0) {
            return PlayerColor.WHITE;
        } else {
            return PlayerColor.BLACK;
        }
    }
}