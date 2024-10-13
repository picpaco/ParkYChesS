package com.marcopiccionitraining.parkychess.model;

import com.marcopiccionitraining.parkychess.model.pieces.Piece;
import com.marcopiccionitraining.parkychess.model.pieces.PieceName;
import org.springframework.util.StringUtils;
import java.util.regex.Pattern;

public final class FENString {
    private static final Pattern FEN_VALIDATOR_REGEX = Pattern.compile
            ("((([pnbrqkPNBRQK1-8]{1,8})/?){8})\\s+([bw])\\s+(-|K?Q?k?q?)\\s+(-|([a-h][3-6])?)\\s+(\\d+)\\s+(\\d+)\\s*");

    private final StringBuilder customFENStringImpl;
  //  private static final Logger LOGGER = LoggerFactory.getLogger(FENString.class);

    public FENString(ChessGame gameState) {
//        LOGGER.trace("Entering FENString constructor. Current player: {}", gameState.getCurrentColor());
  //      LOGGER.trace("Add piece placement data.");
        customFENStringImpl = new StringBuilder();
        addPiecePlacementInfo(gameState.getChessboard());
        customFENStringImpl.append(' ');
    //    LOGGER.trace("Add current player.");
        addCurrentColor(gameState.getCurrentColor());
        customFENStringImpl.append(' ');
      //  LOGGER.trace("Add castling rights.");
        addCastlingRights(gameState.getChessboard());
        customFENStringImpl.append(' ');
     //   LOGGER.trace("Add en-passant data.");
        addEnPassantData(gameState.getCurrentColor(), gameState.getChessboard());
     //   LOGGER.trace("Add number of half moves since last capture or pawn move.");
        customFENStringImpl.append(' ');
        customFENStringImpl.append(gameState.getNumberOfHalfMovesSinceLastCaptureOrPawnMove());
     //   LOGGER.trace("Number of full moves in the game.");
        customFENStringImpl.append(' ');
        customFENStringImpl.append(gameState.getNumberOfFullMoves());
        assert isValidAsFEN(customFENStringImpl.toString()) : "the constructed FEN string failed to validate";
    }
    public FENString (String externalString){
        if (!(isValidAsFEN(externalString))){
            throw new IllegalArgumentException("The input string failed to validate as FEN.");
        }
     //   LOGGER.trace("Building FeNString with external string: {}", externalString);
        customFENStringImpl = new StringBuilder(externalString);
    }

    private boolean isValidAsFEN(String s){
        if (!(FEN_VALIDATOR_REGEX.matcher(s).matches())){
            return false;
        }
     //   LOGGER.trace("There are {} occurrences of '/'", StringUtils.countOccurrencesOf(s, "/"));
        if (StringUtils.countOccurrencesOf(s,"/") != 7){
            return false;
        }
        String[] fenSections = s.split(" ");
        String[] rows = fenSections[0].split("/");
        if (rows[0].contains("p") || rows[0].contains("P") || rows[7].contains("p") || rows[7].contains("P")){
            return false;
        }
        for (String row : rows) {
            //     LOGGER.trace("rows[{}] = {}", i, rows[i]);
            int numberOfEmptySquaresPerRow = 0;
            int numberOfSquaresPerRow = 0;
            for (int j = 0; j < row.length(); j++) {
                char alfaNum = row.charAt(j);
                if (Character.isDigit(alfaNum)) {
                    //           LOGGER.trace("alfaNum = {}", Character.getNumericValue (alfaNum));
                    numberOfEmptySquaresPerRow += Character.getNumericValue(alfaNum);
                    numberOfSquaresPerRow += Character.getNumericValue(alfaNum);
                    //         LOGGER.trace("numberOfEmptySquaresPerRow = {}", numberOfEmptySquaresPerRow);
                    //       LOGGER.trace("numberOfSquaresPerRow = {}", numberOfSquaresPerRow);
                    if (numberOfEmptySquaresPerRow > 8) {
                        //         LOGGER.trace("Just before returning false: numberOfEmptySquaresPerRow = {}", numberOfEmptySquaresPerRow);
                        return false;
                    }
                } else {
                    numberOfSquaresPerRow++;
                    //   LOGGER.trace("After a piece symbol: numberOfSquaresPerRow = {}", numberOfSquaresPerRow);
                    if (numberOfSquaresPerRow > 8) {
                        //     LOGGER.trace("Just before returning false: numberOfSquaresPerRow = {}", numberOfSquaresPerRow);
                        return false;
                    }
                }
            }
            if (numberOfSquaresPerRow < 8) {
                // LOGGER.debug("numberOfSquaresPerRow < 8 after analizing the row. Just before returning false: numberOfSquaresPerRow = {}", numberOfSquaresPerRow);
                return false;
            }
        }
        int numBlackKings = 0;
        int numWhiteKing = 0;
        for (int i = 0; i < fenSections[0].length(); i++) {
            if (fenSections[0].charAt(i) == 'k'){
                numBlackKings++;
            }
            if (fenSections[0].charAt(i) == 'K'){
                numWhiteKing++;
            }
                    }
        return numBlackKings == 1 && numWhiteKing == 1;
    }

    @Override
    public String toString(){
        return customFENStringImpl.toString();
    }

    private char getCharForPiece (Piece piece) {
        char charForPiece = ' ';
        PieceName pieceName = piece.getName();
        switch (pieceName) {
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
                assert false : "Unrecognizable piece name: " + pieceName;
        }
        if (piece.getColor().equals(PlayerColor.WHITE)) {
            return Character.toUpperCase(charForPiece);
        }
        return charForPiece;
    }
    private void addRowOfData(Board chessboard, int row){
        int numberOfEmptyPositions = 0;
        for (int col = 0; col < 8; col++){
            if (chessboard.getPiece(row, col) == null){
                numberOfEmptyPositions++;
                continue;
            }
            if (numberOfEmptyPositions > 0){
                customFENStringImpl.append(numberOfEmptyPositions);
                numberOfEmptyPositions = 0;
            }
            customFENStringImpl.append(getCharForPiece(chessboard.getPiece(row, col)));
        }
        if (numberOfEmptyPositions > 0){
            customFENStringImpl.append(numberOfEmptyPositions);
        }
    }
    private void addPiecePlacementInfo(Board chessboard){
        for (int row = 0; row < 8; row++) {
            if (row != 0){
                customFENStringImpl.append("/");
            }
            addRowOfData(chessboard, row);
        }
    }
    private void addCurrentColor(PlayerColor currentPlayerColor){
        if (currentPlayerColor.equals(PlayerColor.WHITE)){
            customFENStringImpl.append("w");
        } else {
            customFENStringImpl.append("b");
        }
    }
    private void addCastlingRights(Board chessboard){
        boolean whiteKingsideCastling = chessboard.isKingsideCastlingPossibleFromFEN(PlayerColor.WHITE);
        boolean whiteQueensideCastling = chessboard.isQueensideCastlingPossibleFromFEN(PlayerColor.WHITE);
        boolean blackKingsideCastling = chessboard.isKingsideCastlingPossibleFromFEN(PlayerColor.BLACK);
        boolean blackQueensideCastling = chessboard.isQueensideCastlingPossibleFromFEN(PlayerColor.BLACK);
        if (!(whiteKingsideCastling || whiteQueensideCastling || blackKingsideCastling || blackQueensideCastling)){
            customFENStringImpl.append("-");
            return;
        }
        if (whiteKingsideCastling){
            customFENStringImpl.append("K");
        }
        if (whiteQueensideCastling){
            customFENStringImpl.append("Q");
        }
        if (blackKingsideCastling){
            customFENStringImpl.append("k");
        }
        if (blackQueensideCastling){
            customFENStringImpl.append("q");
        }
    }
    private void addEnPassantData(PlayerColor currentPlayerColor, Board chessboard){
        if (!(chessboard.canCaptureEnPassant(currentPlayerColor))){
            customFENStringImpl.append("-");
            return;
        }
        Position pawnEnPassantPosition = chessboard.getEnPassantCapturePositionForColor
                (PlayerColor.getOpponentColor(currentPlayerColor));
        char columnInAlgebraic = (char) ('a' + pawnEnPassantPosition.column());
        int rowInAlgebraic = 8 - pawnEnPassantPosition.row();
        customFENStringImpl.append(columnInAlgebraic);
        customFENStringImpl.append(rowInAlgebraic);
    }
}