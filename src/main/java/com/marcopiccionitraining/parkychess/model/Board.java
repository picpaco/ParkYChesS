package com.marcopiccionitraining.parkychess.model;


import com.marcopiccionitraining.parkychess.model.moves.EnPassantMove;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import com.marcopiccionitraining.parkychess.model.pieces.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Board {
    private static final int CHESSBOARD_ROW_SIZE = 8;
    private static final int CHESSBOARD_COLUMN_SIZE = 8;
    private final Piece[][] pieces = new Piece[CHESSBOARD_ROW_SIZE][CHESSBOARD_COLUMN_SIZE];
    private final HashMap<PlayerColor, Position> pawnEnPassantCapturePositions = new HashMap<>();
    private Move lastExecutedMove;
    private boolean isBlackKingsideCastlingPossible = true;
    private boolean isBlackQueensideCastlingPossible = true;
    private boolean isWhiteKingsideCastlingPossible = true;
    private boolean isWhiteQueensideCastlingPossible = true;
  //  private static final Logger LOGGER = LoggerFactory.getLogger(Board.class);

    /**
     * (0,0) is a8 and is located top left.
     * (0,7) is h8 and is located top right.
     * (7,0) is a1 and is located bottom left.
     * (7,7) is h1 and is located bottom right.
     */
    public Board() {
        pawnEnPassantCapturePositions.put(PlayerColor.BLACK, null);
        pawnEnPassantCapturePositions.put(PlayerColor.WHITE, null);
    }

    public boolean isBlackKingsideCastlingPossible() {
        return isBlackKingsideCastlingPossible;
    }

    public void setBlackKingsideCastlingPossible(boolean blackKingsideCastlingPossible) {
        isBlackKingsideCastlingPossible = blackKingsideCastlingPossible;
    }

    public boolean isBlackQueensideCastlingPossible() {
        return isBlackQueensideCastlingPossible;
    }

    public void setBlackQueensideCastlingPossible(boolean blackQueensideCastlingPossible) {
        isBlackQueensideCastlingPossible = blackQueensideCastlingPossible;
    }

    public boolean isWhiteKingsideCastlingPossible() {
        return isWhiteKingsideCastlingPossible;
    }

    public void setWhiteKingsideCastlingPossible(boolean whiteKingsideCastlingPossible) {
        isWhiteKingsideCastlingPossible = whiteKingsideCastlingPossible;
    }

    public boolean isWhiteQueensideCastlingPossible() {
        return isWhiteQueensideCastlingPossible;
    }

    public void setWhiteQueensideCastlingPossible(boolean whiteQueensideCastlingPossible) {
        isWhiteQueensideCastlingPossible = whiteQueensideCastlingPossible;
    }

    public Move getLastExecutedMove() {
        return lastExecutedMove;
    }

    public void setLastExecutedMove(Move lastExecutedMove) {
        this.lastExecutedMove = lastExecutedMove;
    }

    public Position getEnPassantCapturePositionForColor (PlayerColor playerColor){
        return pawnEnPassantCapturePositions.get(playerColor);
    }

    public void setEnPassantCapturePositionForColor (PlayerColor playerColor, Position position){
     //   LOGGER.trace("Entering setEnPassantCapturePositionForColor(playerColor = {}, position = {}", playerColor, position);
        pawnEnPassantCapturePositions.put(playerColor, position);
    }
    public Piece getPiece(Position position) {
        return pieces[position.row()][position.column()];
    }
    public Piece getPiece(int row, int column) {
        return pieces[row][column];
    }
    public void setPiece (Piece piece, Position position){
        pieces [position.row()][position.column()] = piece;
    }
    public void setPiece (Piece piece, int row, int column){
        pieces[row][column] = piece;
    }
    public void setEmpty (Position position){
      //  LOGGER.trace("setEmpty(position = {}", position);
        pieces [position.row()][position.column()] = null;
    }

    public boolean isEmpty (Position position){
        return pieces [position.row()][position.column()] == null;
    }

    public boolean isInside (Position position){
        if (position == null){
            return false;
        }
        return position.row() >= 0 && position.row() <= 7 && position.column() >= 0 && position.column() <= 7;
    }

    //TODO implement code detecting initial checkers


    public Collection<Position> getPiecesPositions(){
      //  LOGGER.trace("Entering getPiecesPositions()");
        Collection<Position> piecesPositions = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                Position position = new Position(row, column);
                if (!isEmpty(position)){
                    piecesPositions.add(position);
                }
            }
        }
      //  LOGGER.trace("piecesPositions: {}", piecesPositions);
        return  piecesPositions;
    }

    public Collection<Position> getPiecesPositionsFor(PlayerColor playerColor){
       // LOGGER.trace("Entering getPiecesPositionsFor {}", playerColor);
        Collection<Position> piecesOfColorPositions = new ArrayList<>();
        Collection<Position> piecesPositions = getPiecesPositions();
        for (Position position : piecesPositions){
                if (getPiece(position).getColor().equals(playerColor)) {
                    piecesOfColorPositions.add(position);
                }
        }
     //   LOGGER.trace("piecesOfColorPositions for {}: {}", playerColor, piecesOfColorPositions);
        return  piecesOfColorPositions;
    }
    //TODO implement isInDoubleCheck()

    public boolean isInCheck(PlayerColor playerColor){
      //  LOGGER.trace("Entering isInCheck({}", playerColor);
        boolean isInCheck;
        Collection<Position> piecesPositions = getPiecesPositionsFor(PlayerColor.getOpponentColor(playerColor));
        for (Position pos : piecesPositions) {
            Piece piece = getPiece(pos);
            isInCheck = piece.canCaptureEnemyKing(pos, this);
            if (isInCheck) {
           //     LOGGER.debug("{}'s king is in check.", playerColor);
                return true;
            }
        }
       // LOGGER.trace("{}'s king is not in check.", playerColor);
        return false;
    }

    public Board copy(){
       // LOGGER.trace("Making a copy of the chessboard...");
        Board copy = new Board();
        for (Position pos : getPiecesPositions()) {
            copy.setPiece(getPiece(pos).copy(), pos);
        }
        return copy;
    }

    public void flip() {
        //TODO implement me
    }

    public PieceCounter getPiecesCounter(){
    //    LOGGER.trace("Entering getPiecesCounter");
        PieceCounter counter = new PieceCounter();
        for (Position piecePosition : getPiecesPositions()) {
            Piece piece = getPiece(piecePosition);
            counter.incrementNumberOfPiecesForColor(piece.getColor(), piece.getName());
        }
        return counter;
    }

    public boolean checkForInsufficientMaterialDraw(){
      //  LOGGER.trace("Entering checkForInsufficientMaterialDraw");
        PieceCounter pieceCounter = getPiecesCounter();
        return areOnlyKingsLeft(pieceCounter) || areKingVsKingAndBishopLeft(pieceCounter) ||
                areKingVsKingAndKnightLeft(pieceCounter) || areKingAndBishopVsKingAndBishopOfOppositeColorsLeft(pieceCounter);
    }

    private boolean areOnlyKingsLeft(PieceCounter pieceCounter){
      //  LOGGER.trace ("Entering areOnlyKingsLeft");
        return pieceCounter.getTotalNumberOfPieces() == 2;
    }

    private boolean areKingVsKingAndBishopLeft(PieceCounter pieceCounter){
      //  LOGGER.trace("Entering areKingVsKingAndBishopLeft(pieceCounter={}", pieceCounter);
        return pieceCounter.getTotalNumberOfPieces() == 3 &&
                (pieceCounter.getBlackNumberOfPiecesNamed(PieceName.BISHOP) == 1 ||
                        pieceCounter.getWhiteNumberOfPiecesNamed(PieceName.BISHOP) == 1);
    }
    private boolean areKingVsKingAndKnightLeft(PieceCounter pieceCounter){
        return pieceCounter.getTotalNumberOfPieces() == 3 &&
                (pieceCounter.getBlackNumberOfPiecesNamed(PieceName.KNIGHT) == 1 ||
                        pieceCounter.getWhiteNumberOfPiecesNamed(PieceName.KNIGHT) == 1);
    }
    private boolean areKingAndBishopVsKingAndBishopOfOppositeColorsLeft(PieceCounter pieceCounter){
        if (pieceCounter.getTotalNumberOfPieces() != 4){
            return false;
        }
        if (pieceCounter.getBlackNumberOfPiecesNamed(PieceName.BISHOP) != 1 ||
                pieceCounter.getWhiteNumberOfPiecesNamed(PieceName.BISHOP) != 1) {
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
    private Position findBishop(PlayerColor playerColor){
        for (Position piecePosition : getPiecesPositionsFor(playerColor)) {
            if (getPiece(piecePosition).getName().equals(PieceName.BISHOP)){
                return piecePosition;
            }
        }
        return null;
    }
    private boolean haveKingAndRookNotMovedYet (Position kingPosition, Position rookPosition){
        if (isEmpty(kingPosition) || isEmpty(rookPosition)){
            return false;
        }
        Piece king = getPiece(kingPosition);
        Piece rook = getPiece(rookPosition);
        return !(king.hasMoved()) && !(rook.hasMoved());
    }
    public boolean isKingsideCastlingPossibleFromFEN(PlayerColor playerColor){
        if (playerColor.equals(PlayerColor.BLACK)){
            return isBlackKingsideCastlingPossible;
        } else if (playerColor.equals(PlayerColor.WHITE)){
            return isWhiteKingsideCastlingPossible;
        }
        return true;
    }
    public boolean isQueensideCastlingPossibleFromFEN(PlayerColor playerColor){
        if (playerColor.equals(PlayerColor.BLACK)){
            return isBlackQueensideCastlingPossible;
        } else if (playerColor.equals(PlayerColor.WHITE)){
            return isWhiteQueensideCastlingPossible;
        }
        return true;
    }

    public boolean canCaptureEnPassant (PlayerColor playerColor) {
     //   LOGGER.trace("Entering canCaptureEnPassant(playerColor = {}", playerColor);
        Position enPassantCapturePosition = getEnPassantCapturePositionForColor(PlayerColor.getOpponentColor(playerColor));
        if (enPassantCapturePosition == null) {
            return false;
        }
        Position[] potentialEnPassantPawnPositions = new Position[2];
        if (playerColor.equals(PlayerColor.BLACK)) {
            Position enPassantCaptureSE = enPassantCapturePosition.stepTowardsDirection(Direction.SOUTH_EAST);
            if (enPassantCaptureSE != null) {
             //   Optional<Position> enPassantCaptureSE = enPassantCapturePosition.stepTowardsDirection(Direction.SOUTH_EAST);
             //   if (enPassantCaptureSE.isPresent()){
                if (isInside(enPassantCaptureSE)) {
                    potentialEnPassantPawnPositions[0] = new Position(enPassantCaptureSE.row(), enPassantCaptureSE.column());
                } //else {
                 //   LOGGER.trace("Black pawn position at potentialEnPassantPawnPositions[0]: {} is outside the chessboard", potentialEnPassantPawnPositions[0]);
                //}
            }
            Position enPassantCaptureSW = enPassantCapturePosition.stepTowardsDirection(Direction.SOUTH_WEST);
            if (enPassantCaptureSW != null) {
                if (isInside(enPassantCaptureSW)) {
                    potentialEnPassantPawnPositions[1] = new Position(enPassantCaptureSW.row(), enPassantCaptureSW.column());
                }// else {
                  //  LOGGER.trace("Black pawn position at potentialEnPassantPawnPositions[1]: {} is outside the chessboard", potentialEnPassantPawnPositions[1]);
                //}
            }
        } else {
            //TODO en passant check playerColor in if
            if (playerColor.equals(PlayerColor.WHITE)) {
                Position enPassantCaptureNE = enPassantCapturePosition.stepTowardsDirection(Direction.NORTH_EAST);
                if (enPassantCaptureNE != null) {
                    if (isInside(enPassantCaptureNE)) {
                        potentialEnPassantPawnPositions[0] = new Position(enPassantCaptureNE.row(), enPassantCaptureNE.column());
                    } //else {
                      //  LOGGER.trace("White pawn position at potentialEnPassantPawnPositions[0]: {} is outside the chessboard", potentialEnPassantPawnPositions[0]);
                    //}
                }
                Position enPassantCaptureNW = enPassantCapturePosition.stepTowardsDirection(Direction.NORTH_WEST);
                if (enPassantCaptureNW != null){
                    if (isInside(enPassantCaptureNW)) {
                        potentialEnPassantPawnPositions[1] = new Position(enPassantCaptureNW.row(), enPassantCaptureNW.column());
                    } //else {
                       // LOGGER.trace("White pawn position at potentialEnPassantPawnPositions[1]: {} is outside the chessboard", potentialEnPassantPawnPositions[1]);
                    //}
                }
            }
        }
      //  LOGGER.trace("potentialEnPassantPawnPositions: {{},{}}", potentialEnPassantPawnPositions[0], potentialEnPassantPawnPositions[1]);
     //   LOGGER.trace("enPassantCapturePosition: {}", enPassantCapturePosition);
        return isPawnReadyForCapturingEnPassant(playerColor, potentialEnPassantPawnPositions, enPassantCapturePosition);
    }
    private boolean isPawnReadyForCapturingEnPassant (PlayerColor currentPlayerColor, Position[] pawnPositionsForEnPassant,
                                                      Position enPassantCapturePosition){
    //    LOGGER.trace("Entering isPawnReadyForCapturingEnPassant(color={} pawnPositionsForEnPassant={}", currentPlayerColor, pawnPositionsForEnPassant);
        for (Position position : pawnPositionsForEnPassant) {
            if (isInside(position)){
                Piece piece = getPiece(position);
                if (piece == null){
                    continue;
                }
                if (piece.getColor().equals(currentPlayerColor)){
                    continue;
                }
                if (!piece.getName().equals(PieceName.PAWN)) {
                    continue;
                }
                EnPassantMove enPassantMove = new EnPassantMove(position, enPassantCapturePosition);
                if (enPassantMove.isLegal(this)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Board board)) return false;
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
}
