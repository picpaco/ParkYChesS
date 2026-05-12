package com.marcopiccionitraining.parkychess.model;

import com.marcopiccionitraining.parkychess.model.moves.*;
import com.marcopiccionitraining.parkychess.model.pieces.King;
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
    public static final int MAX_DEPTH = 10;

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
     * Custom data structure used to save game state and restore it when needed.
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
        currentPlayerColor = PlayerColor.getOpponentColor(currentPlayerColor);
        log.debug("Switched color to {}", currentPlayerColor);
    }

    public Board (PlayerColor currentColor) {
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
    //TODO: see if it is better to insert a precondition 'position not null' and check, before
    // invoking getPiece, that !(chessboard(position).isEmpty()).
    public Piece getPiece(Position position) {
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
     //   piece.setPosition(position);
        pieces[position.getRow()][position.getColumn()] = piece;
    }

    public void setPiece (@NonNull Piece piece, int row, int column) {
        assert 0 <= row && row < 8 && 0 <= column && column < 8 : "Row and column should be within chessboard bounds.";
   //     piece.setPosition(new Position(row, column));
        pieces[row][column] = piece;
    }

    public void setEmpty (@NonNull Position position) {
        assert position.isInsideBoard() : "position must be within this board.";
        pieces[position.getRow()][position.getColumn()] = null;
    }

    /**
     *
     * @param position on the chessboard.
     * @return true if there is not a piece at position, false otherwise.
     */
    public boolean isEmpty(@NonNull Position position) {
        if (position.isInsideBoard()) {
            return pieces[position.getRow()][position.getColumn()] == null;
        }
        return true;
    }
    /**
     * @param row the position's row.
     * @param column the position's column.
     * @return true if there is not a piece at position, false otherwise.
     */
    public boolean isEmpty(int row, int column) {
        assert 0 <= row && row < 8 && 0 <= column && column < 8 : "Row and column should be within chessboard bounds.";
        return (pieces[row][column] == null);
    }
    /**
     * @param playerColor is the color we are interested in.
     * @return a list containing all the board positions with pieces having playerColor.
     */
    public List<Position> getPiecesPositionsForColor(PlayerColor playerColor) {
        List<Position> allPiecesPositions = getPiecesPositions();
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
        return allPiecesPositions;
    }

    public boolean isPlayerInDoubleCheck(PlayerColor playerColor) {
        if (checkingPieces.size() == 2){
            return true;
        }
        return false;
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
        log.trace("Making a copy of the chessboard...");
        Board boardCopy = new Board(currentPlayerColor);
        List<Position> positions = getPiecesPositions();
        for (Position pos : positions) {
            boardCopy.setPiece(this.getPiece(pos).copy(), pos);
        }
        boardCopy.gameStateContainer = gameStateContainer.copy();
        log.trace("{}", boardCopy);
        boardCopy.setLastExecutedMove(lastExecutedMove);
        log.trace("Last executed move which was copied: {}", lastExecutedMove);
        Position whiteSkippedPosition = pawnSkippedPositionByColor.get(PlayerColor.WHITE);
        if (whiteSkippedPosition != null) {
            log.debug("About to invoke setSkippedPawnPosition ({}, {})", PlayerColor.WHITE, whiteSkippedPosition);
            boardCopy.setSkippedPawnPosition(PlayerColor.WHITE, whiteSkippedPosition);
        }
        Position blackSkippedPosition = pawnSkippedPositionByColor.get(PlayerColor.BLACK);
        if (blackSkippedPosition != null) {
            log.debug("About to invoke setSkippedPawnPosition ({}, {})", PlayerColor.BLACK, blackSkippedPosition);
            boardCopy.setSkippedPawnPosition(PlayerColor.BLACK, blackSkippedPosition);
        }
        boardCopy.setBlackKingsideCastlingPossibleFromFEN(isBlackKingsideCastlingPossibleFromFEN);
        boardCopy.setBlackQueensideCastlingPossibleFromFEN(isBlackQueensideCastlingPossibleFromFEN);
        boardCopy.setWhiteQueensideCastlingPossibleFromFEN(isWhiteQueensideCastlingPossibleFromFEN);
        boardCopy.setWhiteKingsideCastlingPossibleFromFEN(isWhiteKingsideCastlingPossibleFromFEN);
        return boardCopy;
    }

    public void flip() {
        //TODO implement me
    }

    public PieceCounter getPiecesCounter() {
        PieceCounter counter = new PieceCounter();
        List<Position> positions = getPiecesPositions();
        for (Position piecePosition : positions) {
            Piece piece = getPiece(piecePosition);
            counter.incrementNumberOfPiecesForColor(piece.getColor(), piece.getName());
        }
        return counter;
    }

    public boolean checkForInsufficientMaterialDraw() {
        PieceCounter pieceCounter = getPiecesCounter();
        return areOnlyKingsLeft(pieceCounter) || areKingVsKingAndBishopLeft(pieceCounter) ||
                areKingVsKingAndKnightLeft(pieceCounter) || areKingAndBishopVsKingAndBishopOfOppositeColorsLeft(pieceCounter);
    }

    private boolean areOnlyKingsLeft(@NonNull PieceCounter pieceCounter) {
        return pieceCounter.getTotalNumberOfPieces() == 2;
    }

    private boolean areKingVsKingAndBishopLeft(@NonNull PieceCounter pieceCounter) {
        return pieceCounter.getTotalNumberOfPieces() == 3 &&
                (pieceCounter.getBlackNumberOfPiecesNamed(PieceNames.BISHOP) == 1 ||
                        pieceCounter.getWhiteNumberOfPiecesNamed(PieceNames.BISHOP) == 1);
    }

    private boolean areKingVsKingAndKnightLeft(@NonNull PieceCounter pieceCounter) {
        return pieceCounter.getTotalNumberOfPieces() == 3 &&
                (pieceCounter.getBlackNumberOfPiecesNamed(PieceNames.KNIGHT) == 1 ||
                        pieceCounter.getWhiteNumberOfPiecesNamed(PieceNames.KNIGHT) == 1);
    }

    private boolean areKingAndBishopVsKingAndBishopOfOppositeColorsLeft(@NonNull PieceCounter pieceCounter) {
        if (pieceCounter.getTotalNumberOfPieces() != 4) {
            return false;
        }
        if (pieceCounter.getBlackNumberOfPiecesNamed(PieceNames.BISHOP) != 1 ||
                pieceCounter.getWhiteNumberOfPiecesNamed(PieceNames.BISHOP) != 1) {
            return false;
        }
        Position blackBishopPosition = findBishop(PlayerColor.BLACK);
        Position whiteBishopPosition = findBishop(PlayerColor.WHITE);
        if (blackBishopPosition != null && whiteBishopPosition != null) {
            return getSquareColor(blackBishopPosition).equals(getSquareColor(whiteBishopPosition));
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
        //log.debug("Piece at kingInitialPosition: {}", king);
        //assert king.getName().equals(PieceNames.KING) : "There should be a king on " + kingInitialPosition;
        //log.debug("Piece at rookInitialPosition: {}", rook);
        //assert rook.getName().equals(PieceNames.ROOK) : "There should be a rook on " + rookInitialPosition;
        return !king.isFlaggedAsHavingAlreadyMoved() && !rook.isFlaggedAsHavingAlreadyMoved();
    }

  /*  private List<Pawn> getPawnsInPositionForCapturingEnPassant(@NonNull PlayerColor playerColor,
                                                               @NonNull Position skippedPosition) {
        List<Pawn> readyForEnPassantPawns = new ArrayList<>();
        List<Position> possibleAttackersPositions = PseudoLegalPiecesPositionsInitializer.
                getAttackingPawnsAssociatedWithSkippedPositions(skippedPosition, playerColor);
        for (Position attackingPiecefromPosition : possibleAttackersPositions) {
            Piece piece = getPiece(attackingPiecefromPosition);
            if (isEmpty(attackingPiecefromPosition)) {
                continue;
            }
            if (!(piece.getColor().equals(playerColor))) {
                continue;
            }
            if (!(piece.getName().equals(PieceNames.PAWN))){
                continue;
            }
            Pawn pawn = (Pawn) piece;
            readyForEnPassantPawns.add(pawn);
        }
        return readyForEnPassantPawns;
    }*/
//-----------------------------------------------------------------
 /* public bool CanCaptureEnPassant(Player player){
      Position skipPos = GetPawnSkipPosition(player.Opponent());
      if (skipPos == null){
          return false;
      }
      Position[] pawnPositions = player switch{
          Player.White => new Position[] { skipPos + Direction.SouthWest, skipPos + Direction.SouthEast },
          Player.Black => new Position[] { skipPos + Direction.NorthWest, skipPos + Direction.NorthEast },
                     _ => Array.Empty<Position>()
      };
      return HasPawnInPosition(player, pawnPositions, skipPos);
  }

   private bool HasPawnInPosition(Player player, Position[] pawnPositions, Position skipPos){
            foreach (Position pos in pawnPositions.Where(IsInside)){
                Piece piece = this[pos];
                if (piece == null || piece.Color != player || piece.Type != PieceType.Pawn){
                    continue;
                }
                EnPassant move = new EnPassant(pos, skipPos);
                if (move.IsLegal(this)){
                    return true;
                }
            }
            return false;
        }*/
  /// //////////////////////////////////////////////////////////////////////////



    public boolean canCaptureEnPassant(@NonNull PlayerColor playerColor) {
        Position skippedPosition = getPawnSkippedPosition(PlayerColor.getOpponentColor(playerColor));
        log.debug("Skipped position for {} is {}", PlayerColor.getOpponentColor(playerColor), skippedPosition);
        if (skippedPosition == null) {
            log.debug("There is no skippedPosition for {}.", PlayerColor.getOpponentColor(playerColor));
            return false;
        }
        if (!lastExecutedMove.getName().equals(MoveNames.DOUBLE_PAWN)) {
            log.debug("Last executed move is a {} move; it should rather be a Double Pawn move.", lastExecutedMove.getName());
            return false;
        }
        List<Position> attackingPawnsPositions = PseudoLegalPiecesPositionsInitializer.
                getAttackingPawnsAssociatedWithSkippedPositions(skippedPosition, PlayerColor.getOpponentColor(playerColor));
        log.debug("attackingPawnsPositions: {}", attackingPawnsPositions);
        for (Position attackingPawnfromPosition : attackingPawnsPositions) {
            if (isEmpty(attackingPawnfromPosition)) {
                log.debug("{} is empty.", attackingPawnfromPosition);
                continue;
            }
            Piece attackingPiece = getPiece(attackingPawnfromPosition);
            if (!(attackingPiece.getName().equals(PieceNames.PAWN))){
                log.debug("Piece {} should be a pawn.", attackingPiece);
                continue;
            }
            if (!(getPiece(attackingPawnfromPosition).getColor().equals(playerColor))) {
                log.debug("Pawn {} is {} but the captured pawn should be the opposite color.",
                        attackingPawnfromPosition, attackingPiece.getColor());
                continue;
            }
            List<Position> pawnCapturePositions = PseudoLegalPiecesPositionsInitializer.
                    getPawnCapturePseudoLegalPositions(attackingPawnfromPosition, playerColor);
            for (Position pawnCapturePosition : pawnCapturePositions) {
                if (!(pawnCapturePosition.equals(skippedPosition))) {
                    log.debug("Pawn capture position: {} is not the same as skipped position: {}",
                            pawnCapturePosition, skippedPosition);
                } else {
                    EnPassantCapture enPassantCapture = new EnPassantCapture(attackingPawnfromPosition,
                            skippedPosition, this);
                    if (enPassantCapture.isLegal(this.copy())) {
                        log.debug("En passant capture {} is legal.", enPassantCapture);
                        return true;
                    } else {
                        log.debug("En passant capture {} is not legal.", enPassantCapture);
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
    public PlayerColor getSquareColor(Position position) {
        if ((position.getRow() + position.getColumn()) % 2 == 0) {
            return PlayerColor.WHITE;
        } else {
            return PlayerColor.BLACK;
        }
    }
}