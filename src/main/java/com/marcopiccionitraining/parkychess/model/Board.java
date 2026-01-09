package com.marcopiccionitraining.parkychess.model;

import com.marcopiccionitraining.parkychess.model.moves.EmptyMove;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import com.marcopiccionitraining.parkychess.model.pieces.King;
import com.marcopiccionitraining.parkychess.model.pieces.Piece;
import com.marcopiccionitraining.parkychess.model.pieces.PieceNames;
import com.marcopiccionitraining.parkychess.model.pieces.Rook;
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
     * Given a valid game position, if the player who has the move makes a double pawn move, then the first square
     * associated to the double move is defined as pawnSkippedPosition.
     * The following HashMap has as key the color (white or black), and as value the associated pawnSkippedPosition.
     * The association can be recorded in the HashMap either when a double pawn move is created or when declared by a FEN.
     * Example: if d2d4 is white's move, the HashMap will contain {White --> d3, Black --> null}.
     */
    private final HashMap<PlayerColor, Position> pawnSkippedPositionByColor = new HashMap<>();
    /*
    if (getColor().equals(PlayerColor.WHITE)) {
            if (chessboard.getEnPassantCapturePosition().row() != 3) {
                log.error("{}'s player pawn is not on row 3 and therefore cannot capture en passant.", getColor());
                return null;
            }
        } else if (getColor().equals(PlayerColor.BLACK)){
            if (chessboard.getEnPassantCapturePosition().row() != 4){
                log.error("{}'s player pawn is not on row 4 and therefore cannot capture en passant.", getColor());
                return null;
            }
        }
     */
    /**
     * Square on which an en passant capture may legally take place on the next move.
     * @param color: player which just made a double pawn move making an en passant capture possible on next move.
     */
    public Position getPawnSkippedPosition(@NonNull PlayerColor color) {
        return pawnSkippedPositionByColor.get(color);
    }

    /**
     * Set a position for en passant capture.
     * @param color: player which just made a double pawn move making an en passant capture possible on next move.
     * @param position: skipped position, also called en passant capture position.
     */
    public void setPawnSkippedPosition(@NonNull PlayerColor color, @NonNull Position position) {
        assert ((position.row() == -1) && (position.column() == -1) || ((position.row() == 5) && (color.equals(PlayerColor.WHITE))) ||
                ((position.row() == 2) && (color.equals(PlayerColor.BLACK)))):
                "If there is a pawn skipped position, it must be on row 5 for White or on row 2 for Black. " +
                        "However, this method was erroneously invoked on row " + position.row() + " for " + color;
        pawnSkippedPositionByColor.put(color, position);
        log.trace("Just set pawnSkippedPositionByColor to {} and position to {}", color, position);
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

    public void switchCurrentPlayerColor(){
        currentPlayerColor = PlayerColor.getOpponentColor(currentPlayerColor);
        log.trace("Switched color to {}", currentPlayerColor);
    }

    public Board (PlayerColor currentColor) {
        this.currentPlayerColor = currentColor;
        log.trace("About to invoke setPawnSkippedPosition for both colors with undetermined position.");
        setPawnSkippedPosition(PlayerColor.WHITE, new Position(-1, -1));
        setPawnSkippedPosition(PlayerColor.BLACK, new Position(-1, -1));
        lastExecutedMove = new EmptyMove();
        gameStateContainer = new GameStateContainer(MAX_CONTAINER_SIZE);
        assert gameStateContainer.isEmpty() : "A game state object must be empty at game start.";
    }

    public Piece getPiece(Position position) {
        assert (position == null || (0 <= position.row()) && (position.row() < 8) &&
                (0 <= position.column()) && position.column() < 8) : "If exists, position should be within chessboard bounds.";
        if (position != null) {
            return pieces[position.row()][position.column()];
        }
        return null;
    }

    public Piece getPiece(int row, int column) {
        assert (0 <= row) && (row < 8) && (0 <= column) && column < 8 :
                "Row and column should be within chessboard bounds.";
        return pieces[row][column];
    }

    public void setPiece(@NonNull Piece piece, @NonNull Position position) {
        assert (0 <= position.row()) && (position.row() < 8) && (0 <= position.column()) && position.column() < 8 :
                "Position should be within chessboard bounds.";
        piece.setPosition(position);
        pieces[position.row()][position.column()] = piece;
    }

    public void setPiece(@NonNull Piece piece, int row, int column) {
        assert 0 <= row && row < 8 && 0 <= column && column < 8 : "Row and column should be within chessboard bounds.";
        piece.setPosition(new Position(row, column));
        pieces[row][column] = piece;
    }

    public void setEmpty(@NonNull Position position) {
        pieces[position.row()][position.column()] = null;
    }

    public boolean isEmpty(@NonNull Position position) {
        return pieces[position.row()][position.column()] == null;
    }

    public boolean isEmpty(int row, int column) {
        assert 0 <= row && row < 8 && 0 <= column && column < 8 : "Row and column should be within chessboard bounds.";
        return (pieces[row][column] == null);
    }

    public boolean isInsideBoard(@NonNull Position position) {
        return position.row() >= 0 && position.row() < 8 && position.column() >= 0 && position.column() < 8;
    }

    /**
     * @param playerColor is the color we are interested in.
     * @return a collection containing all the board positions containing pieces of playerColor.
     */
    public Collection<Position> getPiecesPositionsForColor(PlayerColor playerColor) {
        Collection<Position> blackPiecesPositions = new ArrayList<>();
        Collection<Position> whitePiecesPositions = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (!isEmpty(row, column)) {
                    Position position = new Position(row, column);
                    Piece piece = getPiece(position);
                    if (piece.getColor().equals(PlayerColor.BLACK)) {
                        blackPiecesPositions.add(position);
                    } else {
                        whitePiecesPositions.add(position);
                    }
                }
            }
        }
        if (playerColor.equals(PlayerColor.BLACK)) {
            return blackPiecesPositions;
        } else {
            return whitePiecesPositions;
        }
    }

    /**
     * @return a collection containing all the board positions containing pieces.
     */
    public Collection<Position> getPiecesPositions() {
        Collection<Position> allPiecesPositions = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (!isEmpty(row, column)) {
                    allPiecesPositions.add(new Position(row, column));
                }
            }
        }
        return allPiecesPositions;
    }

    public boolean isInDoubleCheck(PlayerColor playerColor) {
        return isPlayerInCheck(playerColor) && checkingPieces.size() == 2;
    }

    /**
     * @param playerColor is the color we are interested in.
     * @return true if playerColor's king is in check.
     */
    public boolean isPlayerInCheck(PlayerColor playerColor) {
        checkingPieces.clear();
        Collection<Position> piecesPositions = getPiecesPositionsForColor(PlayerColor.getOpponentColor(playerColor));
        log.debug("Pieces positions for {}: {}", PlayerColor.getOpponentColor(playerColor), piecesPositions);
        for (Position pos : piecesPositions) {
            Piece piece = getPiece(pos);
            if (piece.canCaptureEnemyKing(pos, this)) {
                log.trace("{} in {} can capture {} king.", piece.getName(), pos, playerColor);
                checkingPieces.add(piece);
                log.trace("{}'s king is in check.", playerColor);
            }
        }
        log.debug("{}'s king is not in check.", playerColor);
        if (checkingPieces.size() > 2) {
            log.trace("There are more than 2 {} pieces giving check. {}.",
                    PlayerColor.getOpponentColor(playerColor), checkingPieces);
            log.trace("Current board situation:\n{}", this);
            assert checkingPieces.size() <= 2 : "A king cannot be in check more than twice.";
        }
        return !checkingPieces.isEmpty();
    }

    /**
     * @return a copy of the current board.
     */
    public Board copy() {
        log.trace("Making a copy of the chessboard...");
        Board boardCopy = new Board(currentPlayerColor);
        Collection<Position> positions = getPiecesPositions();
        for (Position pos : positions) {
            boardCopy.setPiece(this.getPiece(pos).copy(), pos);
        }
        boardCopy.gameStateContainer = gameStateContainer.copy();
        log.trace("{}", boardCopy);
        //      boardCopy.setEnPassantCapturePosition(enPassantCapturePosition);
        boardCopy.setLastExecutedMove(lastExecutedMove);
        log.trace("Last executed move which was copied: {}", lastExecutedMove);
      //  Piece pieceThatMovedLast = getPiece(lastExecutedMove.getTo());
        Position whiteSkippedPosition = pawnSkippedPositionByColor.get(PlayerColor.WHITE);
        log.trace("About to invoke setPawnSkippedPosition ({}, {})", PlayerColor.WHITE, whiteSkippedPosition);
        boardCopy.setPawnSkippedPosition(PlayerColor.WHITE, whiteSkippedPosition);
        Position blackSkippedPosition = pawnSkippedPositionByColor.get(PlayerColor.BLACK);
        log.trace("About to invoke setPawnSkippedPosition ({}, {})", PlayerColor.BLACK, blackSkippedPosition);
        boardCopy.setPawnSkippedPosition(PlayerColor.BLACK, blackSkippedPosition);
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
        Collection<Position> positions = getPiecesPositions();
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

    private boolean areOnlyKingsLeft(PieceCounter pieceCounter) {
        return pieceCounter.getTotalNumberOfPieces() == 2;
    }

    private boolean areKingVsKingAndBishopLeft(PieceCounter pieceCounter) {
        return pieceCounter.getTotalNumberOfPieces() == 3 &&
                (pieceCounter.getBlackNumberOfPiecesNamed(PieceNames.BISHOP) == 1 ||
                        pieceCounter.getWhiteNumberOfPiecesNamed(PieceNames.BISHOP) == 1);
    }

    private boolean areKingVsKingAndKnightLeft(PieceCounter pieceCounter) {
        return pieceCounter.getTotalNumberOfPieces() == 3 &&
                (pieceCounter.getBlackNumberOfPiecesNamed(PieceNames.KNIGHT) == 1 ||
                        pieceCounter.getWhiteNumberOfPiecesNamed(PieceNames.KNIGHT) == 1);
    }

    private boolean areKingAndBishopVsKingAndBishopOfOppositeColorsLeft(PieceCounter pieceCounter) {
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
            return blackBishopPosition.getSquareColor().equals(whiteBishopPosition.getSquareColor());
        } else {
            return false;
        }
    }

    private Position findBishop(PlayerColor playerColor) {
        for (Position piecePosition : getPiecesPositionsForColor(playerColor)) {
            if (getPiece(piecePosition).getName().equals(PieceNames.BISHOP)) {
                return piecePosition;
            }
        }
        return null;
    }

    public boolean isKingsideCastlingPossibleFromFEN(PlayerColor playerColor) {
        if (playerColor.equals(PlayerColor.BLACK)) {
            return isBlackKingsideCastlingPossibleFromFEN;
        } else if (playerColor.equals(PlayerColor.WHITE)) {
            return isWhiteKingsideCastlingPossibleFromFEN;
        }
        assert false : "Player color must be either black or white.";
        return false;
    }

    public boolean isQueensideCastlingPossibleFromFEN(PlayerColor playerColor) {
        if (playerColor.equals(PlayerColor.BLACK)) {
            return isBlackQueensideCastlingPossibleFromFEN;
        } else if (playerColor.equals(PlayerColor.WHITE)) {
            return isWhiteQueensideCastlingPossibleFromFEN;
        }
        return false;
    }

/*    public boolean haveKingAndKingsideRookNeverMoved(PlayerColor playerColor) {
        boolean result = false;
        if (playerColor.equals(PlayerColor.BLACK)) {
            result = haveKingAndRookNotMovedYet(new Position(0, 4), new Position(0, 7));
            log.trace("Have black king and rook not moved yet? {}", result);
            return result;
        } else if (playerColor.equals(PlayerColor.WHITE)) {
            result = haveKingAndRookNotMovedYet(new Position(7, 4), new Position(7, 7));
            log.trace("Have white king and rook not moved yet? {}", result);
            return result;
        }
        assert false : "Unreachable code. There should be only 2 colors.";
        return false;
    }
*/
 /*   public boolean haveKingAndQueensideRookNeverMoved(PlayerColor playerColor) {
        if (playerColor.equals(PlayerColor.BLACK)) {
            return haveKingAndRookNotMovedYet(new Position(0, 4), new Position(0, 0));
        } else if (playerColor.equals(PlayerColor.WHITE)) {
            return haveKingAndRookNotMovedYet(new Position(7, 4), new Position(7, 0));
        }
        return false;
    }*/

    private boolean haveKingAndRookNotMovedYet(Position kingInitialPosition, Position rookInitialPosition) {
        assert kingInitialPosition != null : "King initial position must exist.";
        assert rookInitialPosition != null : "Rook initial position must exist.";

        if (isEmpty(kingInitialPosition)) {
            log.trace("King initial position {} is empty.", kingInitialPosition);
            return false;
        }
        if (isEmpty(rookInitialPosition)) {
            log.trace("Rook initial position {} is empty.", rookInitialPosition);
            return false;
        }
        log.trace("King's expected initial position is {}", kingInitialPosition);
        Piece pieceOnKingInitialPosition = getPiece(kingInitialPosition);
        if (!(pieceOnKingInitialPosition.getName().equals(PieceNames.KING))) {
            log.trace("Piece on king initial position is {}", pieceOnKingInitialPosition.getName());
            return false;
        }
        log.trace("Rook's expected initial position is {}", rookInitialPosition);
        Piece pieceOnRookInitialPosition = getPiece(rookInitialPosition);
        if (!(pieceOnRookInitialPosition.getName().equals(PieceNames.ROOK))) {
            log.trace("Piece on rook initial position is {}", pieceOnRookInitialPosition.getName());
            return false;
        }
        King king = (King) getPiece(kingInitialPosition);
        Rook rook = (Rook) getPiece(rookInitialPosition);
        PlayerColor currentColor = king.getColor();
        if (!(currentColor.equals(rook.getColor()))) {
            log.trace("King and rook have different colors.");
            return false;
        }
        if (currentColor.equals(PlayerColor.BLACK)) {
            if (!isBlackQueensideCastlingPossibleFromFEN) {
                return false;
            }
            if (!isBlackKingsideCastlingPossibleFromFEN) {
                return false;
            }
            if (!isWhiteQueensideCastlingPossibleFromFEN) {
                return false;
            }
            if (!isWhiteKingsideCastlingPossibleFromFEN) {
                return false;
            }
        }
        return (!(king.isFlaggedAsHavingAlreadyMoved())) &&
                (!(rook.isFlaggedAsHavingAlreadyMoved()));
    }
/*
    public boolean canCaptureEnPassant (@NonNull PlayerColor playerColor) {
            Position pawnSkippedPosition = getPawnSkippedPosition (PlayerColor.getOpponentColor(playerColor));
            if (pawnSkippedPosition == null) {
                return false;
            }
            log.trace("pawnSkippedPosition: {}", pawnSkippedPosition);
            ArrayList<Position> possibleEnPassantCapturePawnPositions = new ArrayList<>();
            switch (playerColor) {
                case WHITE: {
                    possibleEnPassantCapturePawnPositions.add(pawnSkippedPosition.stepTowardsDirection(Direction.SOUTH_WEST));
                    log.trace("possibleEnPassantCapturePawnPositions: {}", possibleEnPassantCapturePawnPositions);
                    possibleEnPassantCapturePawnPositions.add(pawnSkippedPosition.stepTowardsDirection(Direction.SOUTH_EAST));
                    log.trace("possibleEnPassantCapturePawnPositions: {}", possibleEnPassantCapturePawnPositions);
                    break;
                }
                case BLACK:  {
                    possibleEnPassantCapturePawnPositions.add(pawnSkippedPosition.stepTowardsDirection(Direction.NORTH_WEST));
                    possibleEnPassantCapturePawnPositions.add(pawnSkippedPosition.stepTowardsDirection(Direction.NORTH_EAST));
                    break;
                }
                default: throw new RuntimeException("Unreachable code. There should be only 2 colors.");
            }
            return hasPawnInPosition(playerColor, possibleEnPassantCapturePawnPositions, pawnSkippedPosition);
        }

 */
    /*
    private boolean hasPawnInPosition(@NonNull PlayerColor playerColor, @NonNull ArrayList<Position> possibleEnPassantCapturePawnPositions,
                                      @NonNull Position skipPos) {
        log.trace("possibleEnPassantCapturePawnPositions: {}", possibleEnPassantCapturePawnPositions);
        for (Position pos : possibleEnPassantCapturePawnPositions) {
            if (pos == null){
                continue;
            }
            Piece piece = pieces[pos.row()][pos.column()];
            if (piece == null || !piece.getColor().equals(playerColor) || !piece.getName().equals(PieceNames.PAWN)) {
                continue;
            }
            if ((pos.row() == 5) && (playerColor.equals(PlayerColor.WHITE)) ||
                    ((pos.row() == 2) && (playerColor.equals(PlayerColor.BLACK)))) {
                log.trace("The pawn skip square must be located on row 5 for White or on row 2 for Black. " +
                    " Row {} was passed as row for {}", pos.row(), playerColor);
                continue;
            }
            EnPassantCapture enPassantMove = new EnPassantCapture(pos, skipPos);
            if (enPassantMove.isLegal(this)) {
                return true;
            }
        }
        return false;
    }*/
/*
    /**
     * @param playerColor: the color of the player who is attempting to capture en passant.
     * @param from: the 'from' position of the en passant capture.
     * @return true if the player can capture en passant from 'from' position.

    public boolean canCaptureEnPassant (@NonNull PlayerColor playerColor, @NonNull Position from){
        if ((playerColor.equals(PlayerColor.WHITE) && from.row() != 3) ||
                (playerColor.equals(PlayerColor.BLACK) && from.row() != 4)) {
            log.trace("On row {} there is a {} pawn.", from.row(), playerColor);
            log.trace("On 'from' square {} there is a {} {}", from, getPiece(from).getColor(), getPiece(from));
            log.trace("{}'s pawn cannot capture en passant because it is positioned on {}.", playerColor, from);
            return false;
        }
        Collection<Position> enPassantCaptureToPositions = PseudoLegalPiecesPositionsInitializer.
                getPawnCapturePseudoLegalPositions(playerColor, from);
        assert enPassantCaptureToPositions != null : "Cannot capture en passant because enPassantCaptureToPositions is null.";
        log.trace("The {} enPassantCaptureToPositions related to {} are the following: {}",
                PlayerColor.getOpponentColor(playerColor),from, enPassantCaptureToPositions);
        log.trace("lastExecutedMove:{}",lastExecutedMove);
        if (lastExecutedMove != null) {
            if (lastExecutedMove.getName().equals(MoveNames.DOUBLE_PAWN) || !enPassantCaptureToPositions.().toString().equals("-")) {
                log.trace("enPassantCaptureToPositions: {}", enPassantCaptureToPositions);
                boolean canCaptureEp = enPassantCaptureToPositions.contains(getEnPassantCapturePosition());
                if (canCaptureEp) {
                    log.trace("{} can capture en passant because last executed move {} was a double pawn move and " +
                            "the en passant capture square is correct.", playerColor, lastExecutedMove);
                } else {
                    log.trace("{} cannot capture en passant because the en passant capture square {} is not correct.",
                            playerColor, getEnPassantCapturePosition());
                }
                return canCaptureEp;
            }
        } else {
            if (enPassantCapturePosition != null) {
                return true;
            }
        }
        log.trace("Cannot capture en passant because lastExecutedMove is null or not a double pawn move.");
        return false;
    }*/

   /*     public boolean isPawnReadyToCaptureEnPassant (PlayerColor currentPlayerColor, Position[] pawnPositionsForEnPassant,
                                                      Position enPassantCapturePosition){
            //    log.trace("Entering isPawnReadyForCapturingEnPassant(color={} pawnPositionsForEnPassant={}", currentPlayerColor, pawnPositionsForEnPassant);
            for (Position position : pawnPositionsForEnPassant) {
                Piece piece = getPiece(position);
                if (piece == null){
                    continue;
                }
                if (piece.getColor().equals(currentPlayerColor)){
                    continue;
                }
                if (!piece.getName().equals(PieceNames.PAWN)) {
                    continue;
                }
                EnPassantCapture enPassantCapture = new EnPassantCapture(position, enPassantCapturePosition);
                //        if (enPassantCapture.isLegal(this, new FENStateString(FENPositions.FEN_INITIAL_POSITION))) {
                if (enPassantCapture.isLegal(this, null)) {
                    return true;
                }
            }
        return false;
    }*/

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Board board)) return false;
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (!(Objects.equals(pieces [row][column], board.pieces[row][column]))){
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
                if (pieces [row][column] == null){
                    charView.append("|___|");
                } else {
                    charView.append("|_");
                    charView.append ((pieces [row][column]).toOneChar());
                    charView.append("_|");
                }
            }
            charView.append("\n");
        }
        return charView.toString();
    }

    //   public void setBlackKingsideCastlingPossible(boolean isBlackKingsideCastlingPossible) {
    //      this.isBlackKingsideCastlingPossible = isBlackKingsideCastlingPossible;
    // }

    public Position getKingPosition(PlayerColor playerColor) {
        Collection<Position> piecesPositions = getPiecesPositions();
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

    public King getKing (PlayerColor playerColor) {
        Position kingPosition = getKingPosition(playerColor);
        return (King) getPiece(kingPosition);
    }

 /*   public Rook getNoCastlingRook(PlayerColor playerColor) {
    Collection<Position> piecesPositions = getPiecesPositions();
    for (Position piecePosition : piecesPositions) {
        Piece piece = getPiece(piecePosition);
        if (piece.getName().equals(PieceNames.ROOK) && piece.getColor().equals(playerColor)) {
            Rook rook = (Rook) piece;
            if (rook.isNoCastlingRook()) {
                return rook;
            }
        }
    }
    return null;
}*/

 /*   public Rook getKingsideRook(PlayerColor playerColor) {
        Collection<Position> piecesPositions = getPiecesPositions();
        for (Position piecePosition : piecesPositions) {
            Piece piece = getPiece(piecePosition);
            if (piece.getName().equals(PieceNames.ROOK) && piece.getColor().equals(playerColor)) {
                Rook rook = (Rook) piece;
                if (rook.isKingsideRook()) {
                    return rook;
                }
            }
        }
        return null;
    }*/

 /*   public Rook getQueensideRook(PlayerColor playerColor) {
        Collection<Position> piecesPositions = getPiecesPositions();
        for (Position piecePosition : piecesPositions) {
            Piece piece = getPiece(piecePosition);
            if (piece.getName().equals(PieceNames.ROOK) && piece.getColor().equals(playerColor)) {
                Rook rook = (Rook) piece;
                if (rook.isQueensideRook()) {
                    return rook;
                }
            }
        }
        return null;
    }*/
}