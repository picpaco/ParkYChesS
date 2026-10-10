package com.marcopiccionitraining.parkychess.model;

import com.marcopiccionitraining.parkychess.ObjectFactory;
import com.marcopiccionitraining.parkychess.model.moves.*;
import com.marcopiccionitraining.parkychess.model.pieces.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import java.util.*;
import java.util.regex.Pattern;

@Slf4j
public class ChessGame {
    /**
     * Pattern used to validate a FEN string.
     */
    private static final Pattern FEN_VALIDATOR_REGEX = Pattern.compile
            ("((([pnbrqkPNBRQK1-8]{1,8})/?){8})\\s+([bw])\\s+(-|K?Q?k?q?)\\s+(-|([a-h][3-6])?)\\s+(\\d+)\\s+(\\d+)\\s*");
    /**
     * The chessboard on which the current chess game is played.
     */
    @Getter
    private final Board chessboard;
    /**
     * The final result of the current chess game.
     */
    @Getter
    private GameResult gameResult;
    /**
     * Counts moves that are neither captures nor pawn moves.
     * It is used to check the 50-moves draw rule.
     */
    @Getter
    private int numberOfNonCaptureNonPawnMoves;

    @Getter @Setter
    private LocalGameState localGameState;

    /**
     * Counts all consecutive non-capture, non-pawn half-moves.
     * When a non-capture, non-pawn move is executed, this counter is reset to be 0.
     * It is used to check the 50 moves draw rule.
     */
    @Getter @Setter
    private int numberOfHalfMovesSinceLastCaptureOrPawnMove;
    /**
     * Counts all full moves in the game.
     * A full move is composed by a pair (white move, black move).
     */
    @Getter
    private int totalNumberOfFullMoves;
    /**
     * Contains non-capture, non-pawn consecutive moves.
     * When a non-capture, non-pawn move is executed, this list is reset to be empty.
     * It is used to check the 50 moves draw rule.
     * Check if it is a stack: if so, change the name accordingly.
     */
    private final LinkedList<Command> partialListOfExecutedMoves = new LinkedList<>();
    /**
     * Contains all moves which were executed and not undone.
     */
    private final LinkedList<Command> totalListOfExecutedMoves = new LinkedList<>();
    /**
     * Contains all moves which can be re-executed after the corresponding undo command.
     */
    private final LinkedList<Command> listOfReExecutableMoves = new LinkedList<>();
    /**
     * Stores all the commands used on the moves, including execute, undo, and redo.
     */
    private final LinkedList<String> commandHistory = new LinkedList<>();

    //TODO: Implement redo

    /**
     * Initializes an instance of a chess game.
     * @param externalFEN provides the initial position in FEN format.
     */
    public ChessGame(@NonNull String externalFEN) {
        //FIXME: pass externalFEN arg to class Board constructor and
        // move isValidAsFEN check to the Board class.
        log.debug("External FEN: {}", externalFEN);
        if (!(isValidAsFEN(externalFEN))) {
            throw new IllegalArgumentException("The input FEN string is not valid.");
        }
        //FIXME: Update the following LOCs so that Board takes over responsibility for handling
        // FEN and zobristKey
        chessboard = new Board(PlayerColor.UNDETERMINED);
        setPositionFromFEN(externalFEN);
        Long zobristKey = chessboard.getNewZobristKey(new LocalGameState(chessboard));
        chessboard.saveZobristEntry(zobristKey, 1);
        log.error("Initial position set and saved in memory with key {}.", zobristKey);
        //FIXME up to here.
        assert !(chessboard.getPiecesPositions().isEmpty()) : "The chessboard should contain at least two pieces.";
        assert !(chessboard.isEmpty(chessboard.getKingPosition(PlayerColor.BLACK))) :
                "The black king should be on the chessboard.";
        assert !(chessboard.isEmpty(chessboard.getKingPosition(PlayerColor.WHITE))) :
                "The white king should be on the chessboard.";
        assert chessboard.getCurrentPlayerColor().equals(PlayerColor.WHITE) ||
                chessboard.getCurrentPlayerColor().equals(PlayerColor.BLACK) :
                "Player color must be either white or black.";
        log.debug("Color moving first: {}", chessboard.getCurrentPlayerColor());
        List<Move> legalMovesForColor = getLegalMovesForColor(chessboard.getCurrentPlayerColor());
        checkGameOver(legalMovesForColor, chessboard, zobristKey);
        if (isGameOver()){
            log.error ("Game over. Computation may continue if needed.");
        }
        log.error("Finished constructing ChessGame instance.");
    }

    public List<Move> getLegalMovesForPieceAtPosition(@NonNull Position position){
        assert !(chessboard.isEmpty(position)) : "There must be a piece at position " + position;
        log.debug("In getLegalMovesForPieceAtPosition(position  = {})", position);
        List<Move> legalMovesForPiece = new ArrayList<>();
        Piece piece = chessboard.getPiece(position);
        List<Move> pseudoLegalMoves = piece.getPseudoLegalMoves(position, chessboard);
        log.error("PseudoLegalMoves for piece {}: {}", piece, pseudoLegalMoves);
        for (Move pseudoLegalMove : pseudoLegalMoves) {
            log.error("Analyzing pseudoLegalMove: {} to see if it is legal:", pseudoLegalMove);
            if (pseudoLegalMove.isLegal(chessboard)){
                log.error("PseudoLegalMove: {} is legal.", pseudoLegalMove);
                legalMovesForPiece.add(pseudoLegalMove);
            }
        }
        log.debug("Legal moves for piece {}:", chessboard.getPiece(position) + position.toString() +
                " " + legalMovesForPiece);
        return legalMovesForPiece;
    }

    /**
     * @param playerColor the color of the player for whom we compute the legal moves.
     * @return all the legal moves available to playerColor.
     */
    public List<Move> getLegalMovesForColor (PlayerColor playerColor) {
        log.error("Computing legal moves for {}...", playerColor);
        List<Move> legalMovesForColor = new ArrayList<>();
        List<Position> piecesPositionsForColor = chessboard.getPiecesPositionsForColor(playerColor);
        log.debug("1. Pieces positions for {}: {}. Loop across them.", playerColor, piecesPositionsForColor);
        //TODO: check if in the following loop I can first check for a move legality and then
        // execute it and then undo it. This way I could avoid one loop execution cost. In fact, now
        // I execute 3 loops, one for checking legality only, which I use in the ChessGame constructor;
        // the second loop is for checking legality (the same as the first loop) in the main loop;
        // the third loop is to execute a move and then undo it, also in the msin loop.
        // The idea is to have a check-legality-only loop to use in the constructor and a
        // "check legality + execute + undo" loop to use in the main loop.
        for (Position piecePositionForColor : piecesPositionsForColor) {
            Piece piece = chessboard.getPiece(piecePositionForColor);
            log.debug("2. Piece at current position is {}{}", piece, piecePositionForColor);
            List<Move> pseudoLegalMovesForColor = piece.getPseudoLegalMoves (piecePositionForColor, chessboard);
            log.debug("3. Pseudolegal moves for {} at position {}: {}", playerColor, piecePositionForColor,
                    pseudoLegalMovesForColor);
            log.debug("4. Loop across pseudolegal moves to select the legal moves:");
            for (Move pseudoLegalMove : pseudoLegalMovesForColor) {
                if (pseudoLegalMove.isLegal(chessboard)){
                    legalMovesForColor.add(pseudoLegalMove);
                    log.debug("5a. PseudoLegalMove {} {} is legal.",
                            pseudoLegalMove.getName(), pseudoLegalMove);
                } else {
                    log.debug("5b. PseudoLegalMove {} {} is illegal.", pseudoLegalMove.getName(), pseudoLegalMove);
                }
            }
        }
        log.debug("Found the following legal moves for {}: {}", playerColor, legalMovesForColor);
        return legalMovesForColor;
    }

    private boolean isValidAsFEN(String s) {
        if (!(FEN_VALIDATOR_REGEX.matcher(s).matches())) {
            return false;
        }
        log.trace("There are {} occurrences of '/'", StringUtils.countOccurrencesOf(s, "/"));
        if (StringUtils.countOccurrencesOf(s, "/") != 7) {
            return false;
        }
        String[] fenSections = s.split(" ");
        String[] rows = fenSections[0].split("/");
        if (rows[0].contains("p") || rows[0].contains("P") || rows[7].contains("p") || rows[7].contains("P")) {
            return false;
        }
        for (String row : rows) {
            int numberOfEmptySquaresPerRow = 0;
            int numberOfSquaresPerRow = 0;
            for (int j = 0; j < row.length(); j++) {
                char alfaNum = row.charAt(j);
                if (Character.isDigit(alfaNum)) {
                    log.trace("alfaNum = {}", Character.getNumericValue(alfaNum));
                    numberOfEmptySquaresPerRow += Character.getNumericValue(alfaNum);
                    numberOfSquaresPerRow += Character.getNumericValue(alfaNum);
                    log.trace("numberOfEmptySquaresPerRow = {}", numberOfEmptySquaresPerRow);
                    log.trace("numberOfSquaresPerRow = {}", numberOfSquaresPerRow);
                    if (numberOfEmptySquaresPerRow > 8) {
                        log.trace("Just before returning false: numberOfEmptySquaresPerRow = {}", numberOfEmptySquaresPerRow);
                        return false;
                    }
                } else {
                    numberOfSquaresPerRow++;
                    log.trace("After a piece symbol: numberOfSquaresPerRow = {}", numberOfSquaresPerRow);
                    if (numberOfSquaresPerRow > 8) {
                        log.trace("Just before returning false: numberOfSquaresPerRow = {}", numberOfSquaresPerRow);
                        return false;
                    }
                }
            }
            if (numberOfSquaresPerRow < 8) {
                log.trace("numberOfSquaresPerRow < 8 after analizing the row. Just before returning false: numberOfSquaresPerRow = {}", numberOfSquaresPerRow);
                return false;
            }
        }
        int numBlackKings = 0;
        int numWhiteKing = 0;
        for (int i = 0; i < fenSections[0].length(); i++) {
            if (fenSections[0].charAt(i) == 'k') {
                numBlackKings++;
            }
            if (fenSections[0].charAt(i) == 'K') {
                numWhiteKing++;
            }
        }
        return numBlackKings == 1 && numWhiteKing == 1;
    }

    @NonNull
    private Position fromAlgebraicToPosition(@NonNull String fenSection) {
        assert fenSection.length() == 2 : "The algebraic coordinate " + fenSection + " should be made of 2 characters.";
        assert fenSection.startsWith("a") || fenSection.startsWith("b") || fenSection.startsWith("c") ||
                fenSection.startsWith("d") || fenSection.startsWith("e") || fenSection.startsWith("f") ||
                fenSection.startsWith("g") || fenSection.startsWith("h") : "fenSection string must start with " +
                "a, b, c, d, e, f, g, or h";
        assert fenSection.endsWith("1") || fenSection.endsWith("2") || fenSection.endsWith("3") ||
                fenSection.endsWith("4") || fenSection.endsWith("5") || fenSection.endsWith("6") ||
                fenSection.endsWith("7") || fenSection.endsWith("8") : "fenSection string must end with " +
                "1, 2, 3, 4, 5, 6, 7, 8";
        return getPositionFromFENSection(fenSection);
    }

    private Position getPositionFromFENSection(@NonNull String fenSection) {
        assert fenSection.length() == 2 : "An algebraic chess coordinate is made of 2 chars.";
        int column = -1;
        switch (fenSection.charAt(0)) {
            case 'a' -> column = 0;
            case 'b' -> column = 1;
            case 'c' -> column = 2;
            case 'd' -> column = 3;
            case 'e' -> column = 4;
            case 'f' -> column = 5;
            case 'g' -> column = 6;
            case 'h' -> column = 7;
            default -> {
                assert false : "Unexpected character for column " + column;
            }
        }
        int row = Integer.parseInt(fenSection.substring(1, 2));
        return new Position(8 - row, column);
    }

    public void setPositionFromFEN(@NonNull String externalFEN) {
        String[] fenSections = externalFEN.split(" ");
        log.trace("Placing pieces on chessboard...");
        fromFENToChessboard(fenSections[0]);
        log.trace("Setting current player color; color read on FEN: {}", fenSections[1]);
        if (fenSections[1].equals("w")) {
            chessboard.setCurrentPlayerColor(PlayerColor.WHITE);
        } else {
            if (fenSections[1].equals("b")) {
                chessboard.setCurrentPlayerColor(PlayerColor.BLACK);
            } else {
                throw new RuntimeException("Unexpected character '" +
                        fenSections[1] + "' encountered in FEN string while analyzing which color moves first.");
            }
        }
        log.trace("Current color set: {}", chessboard.getCurrentPlayerColor());
        log.trace("Computing castling rights...");
        if (fenSections[2].length() == 1) {
            compute1charCastlingRights(fenSections[2]);
        } else {
            if (fenSections[2].length() == 2) {
                compute2CharCastlingRights(fenSections[2]);
            } else {
                if (fenSections[2].length() == 3) {
                    compute3CharCastlingRights(fenSections[2]);
                } else {
                    if (fenSections[2].length() == 4) {
                        log.trace("fenSections[2]: {}", fenSections[2]);
                        assert isKingAtHome(PlayerColor.BLACK) : "There should be a black king in (0,4)";
                        assert isKingAtHome(PlayerColor.WHITE) : "There should be a white king in (7,4)";
                        assert isKingsideRookAtHome(PlayerColor.BLACK) : "There should be a black rook in (0,7)";
                        assert isKingsideRookAtHome(PlayerColor.WHITE) : "There should be a white rook in (7,7)";
                        assert isQueensideRookAtHome(PlayerColor.WHITE) : "There should be a white rook in (7,0)";
                        assert isQueensideRookAtHome(PlayerColor.BLACK) : "There should be a black rook in (0,0)";
                        chessboard.setBlackKingsideCastlingPossibleFromFEN(true);
                        chessboard.setBlackQueensideCastlingPossibleFromFEN(true);
                        chessboard.setWhiteKingsideCastlingPossibleFromFEN(true);
                        chessboard.setWhiteQueensideCastlingPossibleFromFEN(true);
                    } else {
                        assert false : "Unexpected fenSections[2] length: " + fenSections[2].length();
                    }
                }
            }
        }
        log.trace("Computing en passant capture position from FEN - aka skipped position for Black.");
        if (!(fenSections[3].equals("-"))) {
            Position lastMoveWhiteFromPosition;
            Position lastMoveWhiteToPosition;
            Position lastMoveBlackFromPosition;
            Position lastMoveBlackToPosition;
            if (fenSections[3].equals("a6") || fenSections[3].equals("b6") || fenSections[3].equals("c6") ||
                    fenSections[3].equals("d6") || fenSections[3].equals("e6") || fenSections[3].equals("f6") ||
                    fenSections[3].equals("g6") || fenSections[3].equals("h6")) {
                log.trace("About to set the pawn skipped position for Black to {}", fenSections[3]);
                chessboard.setPawnSkippedPosition(PlayerColor.BLACK, fromAlgebraicToPosition(fenSections[3]));
                lastMoveBlackFromPosition = (new Position(chessboard.
                        getPawnSkippedPosition(PlayerColor.BLACK).getRow() - 1,
                        chessboard.getPawnSkippedPosition(PlayerColor.BLACK).getColumn()));
                lastMoveBlackToPosition = (new Position(chessboard.
                        getPawnSkippedPosition(PlayerColor.BLACK).getRow() + 1,
                        chessboard.getPawnSkippedPosition(PlayerColor.BLACK).getColumn()));
                log.debug("lastMoveBlackFromPosition: {} lastMoveBlackToPosition: {}", lastMoveBlackFromPosition, lastMoveBlackToPosition);
                createDoubleMoveFromFEN(lastMoveBlackFromPosition, lastMoveBlackToPosition);
            } else {
                if (fenSections[3].equals("a3") || fenSections[3].equals("b3") || fenSections[3].equals("c3") ||
                        fenSections[3].equals("d3") || fenSections[3].equals("e3") || fenSections[3].equals("f3") ||
                        fenSections[3].equals("g3") || fenSections[3].equals("h3")) {
                    log.trace("About to set the pawn skipped position for White to {}", fenSections[3]);
                    chessboard.setPawnSkippedPosition(PlayerColor.WHITE, fromAlgebraicToPosition(fenSections[3]));
                    lastMoveWhiteFromPosition = (new Position(chessboard.
                            getPawnSkippedPosition(PlayerColor.WHITE).getRow() + 1,
                            chessboard.getPawnSkippedPosition(PlayerColor.WHITE).getColumn()));
                    lastMoveWhiteToPosition = (new Position(chessboard.
                            getPawnSkippedPosition(PlayerColor.WHITE).getRow() - 1,
                            chessboard.getPawnSkippedPosition(PlayerColor.WHITE).getColumn()));
                    log.debug("lastMoveWhiteFromPosition: {} lastMoveWhiteToPosition: {}", lastMoveWhiteFromPosition, lastMoveWhiteToPosition);
                    createDoubleMoveFromFEN(lastMoveWhiteFromPosition, lastMoveWhiteToPosition);
                } else {
                    throw new RuntimeException("The en-passant capture positions in the FEN refers to an invalid position.");
                }
            }
        }
        log.trace("Last executed move while setting position from FEN: {}", chessboard.getLastExecutedMove());
        log.trace("Setting number of half moves since last capture or pawn move");
        if (fenSections[4] != null) {
            numberOfHalfMovesSinceLastCaptureOrPawnMove = Integer.parseInt(fenSections[4]);
        }
        log.trace("Setting number of full moves in the game.");
        if (fenSections[5] != null) {
            totalNumberOfFullMoves = Integer.parseInt(fenSections[5]);
        }
    }

    private void createDoubleMoveFromFEN(Position lastMoveFromPosition, Position lastMoveToPosition) {
        log.debug("Last move from position: {}, last move to position: {}", lastMoveFromPosition, lastMoveToPosition);
        log.debug("{} has to move. Position: {}", chessboard.getCurrentPlayerColor(), chessboard);
        chessboard.setPiece(chessboard.getPiece(lastMoveToPosition), lastMoveFromPosition);
        log.debug("Position after setting piece: {}", chessboard);
        chessboard.setEmpty(lastMoveToPosition);
        log.debug("Position after setting empty: {}", chessboard);
        DoublePawnMove doublePawnMove = new DoublePawnMove(lastMoveFromPosition, lastMoveToPosition, chessboard);
        if (doublePawnMove.isLegal(chessboard)){
            doublePawnMove.execute(chessboard);
            chessboard.setLastExecutedMove(doublePawnMove);
        }
    }

    private boolean isKingAtHome(@NonNull PlayerColor kingColor) {
        if (kingColor.equals(PlayerColor.WHITE)) {
            King whiteKing = (King) chessboard.getPiece(7, 4);
            return (whiteKing.getName().equals(PieceNames.KING)) &&
                    !(whiteKing.isFlaggedAsHavingAlreadyMoved());
        } else {
            King blackKing = (King) chessboard.getPiece(0, 4);
            return (blackKing.getName().equals(PieceNames.KING) &&
                    !(blackKing).isFlaggedAsHavingAlreadyMoved());
        }
    }

    private boolean isKingsideRookAtHome(PlayerColor kingsideRookColor) {
        if (kingsideRookColor == PlayerColor.WHITE) {
            Rook kingsideWhiteRook = (Rook) chessboard.getPiece(7, 7);
            return kingsideWhiteRook.getName().equals(PieceNames.ROOK) &&
                    !(kingsideWhiteRook.isFlaggedAsHavingAlreadyMoved());
        } else {
            Rook kingsideBlackRook = (Rook) chessboard.getPiece(0, 7);
            return kingsideBlackRook.getName().equals(PieceNames.ROOK) &&
                    !(kingsideBlackRook.isFlaggedAsHavingAlreadyMoved());
        }
    }

    private boolean isQueensideRookAtHome(PlayerColor queensideRookColor) {
        if (queensideRookColor == PlayerColor.WHITE) {
            Rook queensideWhiteRook = (Rook) chessboard.getPiece(7, 0);
            return queensideWhiteRook.getName().equals(PieceNames.ROOK) &&
                    !(queensideWhiteRook.isFlaggedAsHavingAlreadyMoved());
        } else {
            Rook queensideBlackRook = (Rook) chessboard.getPiece(0, 0);
            return queensideBlackRook.getName().equals(PieceNames.ROOK) &&
                    !(queensideBlackRook.isFlaggedAsHavingAlreadyMoved());
        }
    }

    private void compute3CharCastlingRights(@NonNull String fenSection) {
        assert fenSection.length() == 3 : "Castle rights should be 3 chars long in this case";
        if (fenSection.equals("KQk")) {
            assert (isKingAtHome(PlayerColor.BLACK)) : "There should be a black king in (0,4)";
            assert (isKingAtHome(PlayerColor.WHITE)) : "There should be a white king in (7,4)";
            assert (isKingsideRookAtHome(PlayerColor.BLACK)) : "There should be a black rook in (0,7)";
            assert (isKingsideRookAtHome(PlayerColor.WHITE)) : "There should be a white rook in (7,7)";
            assert (isQueensideRookAtHome(PlayerColor.WHITE)) : "There should be a white rook in (7,0)";
            chessboard.setBlackKingsideCastlingPossibleFromFEN(true);
            chessboard.setBlackQueensideCastlingPossibleFromFEN(false);
            chessboard.setWhiteKingsideCastlingPossibleFromFEN(true);
            chessboard.setWhiteQueensideCastlingPossibleFromFEN(true);
        } else {
            if (fenSection.equals("KQq")) {
                assert isKingAtHome(PlayerColor.BLACK) : "There should be a black king in (0,4)";
                assert isKingAtHome(PlayerColor.WHITE) : "There should be a white king in (7,4)";
                assert isQueensideRookAtHome(PlayerColor.BLACK) : "There should be a black rook in (0,0)";
                assert (isKingsideRookAtHome(PlayerColor.WHITE)) : "There should be a white rook in (7,7)";
                assert (isQueensideRookAtHome(PlayerColor.WHITE)) : "There should be a white rook in (7,0)";
                chessboard.setBlackQueensideCastlingPossibleFromFEN(true);
                chessboard.setWhiteQueensideCastlingPossibleFromFEN(true);
                chessboard.setWhiteKingsideCastlingPossibleFromFEN(true);
                chessboard.setBlackKingsideCastlingPossibleFromFEN(false);
            } else {
                if (fenSection.equals("Kkq")) {
                    assert (isKingAtHome(PlayerColor.BLACK)) : "There should be a black king in (0,4)";
                    assert (isKingAtHome(PlayerColor.WHITE)) : "There should be a white king in (7,4)";
                    assert (isKingsideRookAtHome(PlayerColor.BLACK)) : "There should be a black rook in (0,7)";
                    assert isQueensideRookAtHome(PlayerColor.BLACK) : "There should be a black rook in (0,0)";
                    assert (isKingsideRookAtHome(PlayerColor.WHITE)) : "There should be a white rook in (7,7)";
                    chessboard.setBlackKingsideCastlingPossibleFromFEN(true);
                    chessboard.setWhiteKingsideCastlingPossibleFromFEN(true);
                    chessboard.setBlackQueensideCastlingPossibleFromFEN(true);
                    chessboard.setWhiteQueensideCastlingPossibleFromFEN(false);
                } else {
                    if (fenSection.equals("Qkq")) {
                        assert isKingAtHome(PlayerColor.BLACK) : "There should be a black king in (0,4)";
                        assert isKingAtHome(PlayerColor.WHITE) : "There should be a white king in (7,4)";
                        assert isKingsideRookAtHome(PlayerColor.BLACK) : "There should be a black rook in (0,7)";
                        assert isQueensideRookAtHome(PlayerColor.BLACK) : "There should be a black rook in (0,0)";
                        assert isQueensideRookAtHome(PlayerColor.WHITE) : "There should be a white rook in (7,0)";
                        chessboard.setBlackKingsideCastlingPossibleFromFEN(true);
                        chessboard.setBlackQueensideCastlingPossibleFromFEN(true);
                        chessboard.setWhiteKingsideCastlingPossibleFromFEN(false);
                        chessboard.setWhiteQueensideCastlingPossibleFromFEN(true);
                    } else {
                        assert false : "The 3-char arg string " + fenSection + " is wrong.";
                    }
                }
            }
        }
    }

    private void compute2CharCastlingRights(String fenSection) {
        assert fenSection.length() == 2 : "Castle rights should be 2 chars long in this case";
        if (fenSection.equals("KQ")) {
            assert isKingAtHome(PlayerColor.WHITE) : "There should be a white king in (7,4)";
            assert isQueensideRookAtHome(PlayerColor.WHITE) : "There should be a white rook in (7,0)";
            assert isKingsideRookAtHome(PlayerColor.WHITE) : "There should be a white rook in (7,7)";
            chessboard.setBlackQueensideCastlingPossibleFromFEN(false);
            chessboard.setBlackKingsideCastlingPossibleFromFEN(false);
            chessboard.setWhiteKingsideCastlingPossibleFromFEN(true);
            chessboard.setWhiteQueensideCastlingPossibleFromFEN(true);
        } else {
            if (fenSection.equals("Kk")) {
                assert isKingAtHome(PlayerColor.BLACK) : "There should be a black king in (0,4)";
                assert isKingAtHome(PlayerColor.WHITE) : "There should be a white king in (7,4)";
                assert isKingsideRookAtHome(PlayerColor.BLACK) : "There should be a black rook in (0,7)";
                assert isKingsideRookAtHome(PlayerColor.WHITE) : "There should be a white rook in (7,7)";
                chessboard.setBlackKingsideCastlingPossibleFromFEN(true);
                chessboard.setWhiteKingsideCastlingPossibleFromFEN(true);
                chessboard.setBlackQueensideCastlingPossibleFromFEN(false);
                chessboard.setWhiteQueensideCastlingPossibleFromFEN(false);
            } else {
                if (fenSection.equals("Kq")) {
                    assert isKingAtHome(PlayerColor.BLACK) : "There should be a black king in (0,4)";
                    assert isKingAtHome(PlayerColor.WHITE) : "There should be a white king in (7,4)";
                    assert isKingsideRookAtHome(PlayerColor.WHITE) : "There should be a white rook in (7,7)";
                    assert (isQueensideRookAtHome(PlayerColor.BLACK)) : "There should be a black rook in (0,0)";
                    chessboard.setWhiteKingsideCastlingPossibleFromFEN(true);
                    chessboard.setBlackQueensideCastlingPossibleFromFEN(true);
                    chessboard.setWhiteQueensideCastlingPossibleFromFEN(false);
                    chessboard.setBlackKingsideCastlingPossibleFromFEN(false);
                } else {
                    if (fenSection.equals("Qk")) {
                        assert isKingAtHome(PlayerColor.BLACK) : "There should be a black king in (0,4)";
                        assert isKingAtHome(PlayerColor.WHITE) : "There should be a white king in (7,4)";
                        assert isKingsideRookAtHome(PlayerColor.BLACK) : "There should be a black rook in (0,7)";
                        assert (isQueensideRookAtHome(PlayerColor.WHITE)) : "There should be a white rook in (7,0)";
                        chessboard.setWhiteQueensideCastlingPossibleFromFEN(true);
                        chessboard.setBlackKingsideCastlingPossibleFromFEN(true);
                        chessboard.setWhiteKingsideCastlingPossibleFromFEN(false);
                        chessboard.setBlackQueensideCastlingPossibleFromFEN(false);
                    } else {
                        if (fenSection.equals("Qq")) {
                            assert isKingAtHome(PlayerColor.BLACK) : "There should be a black king in (0,4)";
                            assert isKingAtHome(PlayerColor.WHITE) : "There should be a white king in (7,4)";
                            assert (isQueensideRookAtHome(PlayerColor.WHITE)) : "There should be a white rook in (7,0)";
                            assert (isQueensideRookAtHome(PlayerColor.BLACK)) : "There should be a black rook in (0,0)";
                            chessboard.setWhiteQueensideCastlingPossibleFromFEN(true);
                            chessboard.setBlackQueensideCastlingPossibleFromFEN(true);
                            chessboard.setWhiteKingsideCastlingPossibleFromFEN(false);
                            chessboard.setBlackKingsideCastlingPossibleFromFEN(false);
                        } else {
                            if (fenSection.equals("kq")) {
                                assert isKingAtHome(PlayerColor.BLACK) : "There should be a black king in (0,4)";
                                assert isKingsideRookAtHome(PlayerColor.BLACK) : "There should be a black rook in (0,7)";
                                assert (isQueensideRookAtHome(PlayerColor.BLACK)) : "There should be a black rook in (0,0)";
                                chessboard.setBlackKingsideCastlingPossibleFromFEN(true);
                                chessboard.setBlackQueensideCastlingPossibleFromFEN(true);
                                chessboard.setWhiteKingsideCastlingPossibleFromFEN(false);
                                chessboard.setWhiteQueensideCastlingPossibleFromFEN(false);
                            } else {
                                assert false : "The 2-char arg string " + fenSection + " is wrong.";
                            }
                        }
                    }
                }
            }
        }
    }

    private void compute1charCastlingRights(String fenSection) {
        assert fenSection.length() == 1 : "Castle rights should be one char long in this case";
        if (fenSection.equals("-")) {
            chessboard.setBlackQueensideCastlingPossibleFromFEN(false);
            chessboard.setBlackKingsideCastlingPossibleFromFEN(false);
            chessboard.setWhiteKingsideCastlingPossibleFromFEN(false);
            chessboard.setWhiteQueensideCastlingPossibleFromFEN(false);
        } else {
            if (fenSection.equals("K")) {
                assert isKingAtHome(PlayerColor.WHITE) : "There should be a white king in (7,4)";
                assert isKingsideRookAtHome(PlayerColor.WHITE) : "There should be a white rook in (7,7)";
                chessboard.setWhiteKingsideCastlingPossibleFromFEN(true);
                chessboard.setBlackQueensideCastlingPossibleFromFEN(false);
                chessboard.setBlackKingsideCastlingPossibleFromFEN(false);
                chessboard.setWhiteQueensideCastlingPossibleFromFEN(false);
            } else {
                if (fenSection.equals("Q")) {
                    assert isKingAtHome(PlayerColor.WHITE) : "There should be a white king in (7,4)";
                    assert (isQueensideRookAtHome(PlayerColor.WHITE)) : "There should be a white rook in (7,0)";
                    chessboard.setWhiteQueensideCastlingPossibleFromFEN(true);
                    chessboard.setWhiteKingsideCastlingPossibleFromFEN(false);
                    chessboard.setBlackQueensideCastlingPossibleFromFEN(false);
                    chessboard.setBlackKingsideCastlingPossibleFromFEN(false);
                } else {
                    if (fenSection.equals("k")) {
                        assert isKingAtHome(PlayerColor.BLACK) : "There should be a black king in (0,4)";
                        assert isKingsideRookAtHome(PlayerColor.BLACK) : "There should be a black rook in (0,7)";
                        chessboard.setWhiteQueensideCastlingPossibleFromFEN(false);
                        chessboard.setBlackQueensideCastlingPossibleFromFEN(false);
                        chessboard.setWhiteKingsideCastlingPossibleFromFEN(false);
                        chessboard.setBlackKingsideCastlingPossibleFromFEN(true);
                    } else {
                        if (fenSection.equals("q")) {
                            assert isKingAtHome(PlayerColor.BLACK) : "There should be a black king in (0,4)";
                            assert (isQueensideRookAtHome(PlayerColor.BLACK)) : "There should be a black rook in (0,0)";
                            chessboard.setBlackQueensideCastlingPossibleFromFEN(true);
                            chessboard.setBlackKingsideCastlingPossibleFromFEN(false);
                            chessboard.setWhiteQueensideCastlingPossibleFromFEN(false);
                            chessboard.setWhiteKingsideCastlingPossibleFromFEN(false);
                        } else {
                            assert false : "The 1-char arg string " + fenSection + " is wrong.";
                        }
                    }
                }
            }
        }
    }

    private void fromFENToChessboard(String fenPosition) {
        int col = 0;
        int row = 0;
        for (char symbol : fenPosition.toCharArray()) {
            log.debug("Symbol just read: {}", symbol);
            if (symbol == '/') {
                col = 0;
                log.debug("Switching from row {} to row {}", row, row + 1);
                row++;
            } else {
                if (Character.isDigit(symbol)) {
                    int numOfEmptySquares = Integer.parseInt(String.valueOf(symbol));
                    log.debug("I am about to set {} empty squares.", numOfEmptySquares);
                    for (int i = 0; i < numOfEmptySquares; i++) {
                        log.debug("At coordinates {},{} I just created an empty square.", row, col);
                        chessboard.setEmpty(row, col);
                        col++;
                    }
                } else {
                    Piece piece = ObjectFactory.getPieceFromFENSymbol(symbol);
                    if (piece.getName().equals(PieceNames.KING)) {
                        King king = (King) piece;
                        if (king.getColor().equals(PlayerColor.BLACK)) {
                            chessboard.setBlackKingPosition(new Position(row, col));
                            if (!(row == 0 && col == 4)) {
                                king.setFlaggedAsHavingAlreadyMoved(true);
                                log.error("Black King is flaggedAsHavingAlreadyMoved because it is not on its initial position.");
                            }
                        } else {
                            chessboard.setWhiteKingPosition(new Position(row, col));
                            if (!(row == 7 && col == 4)) {
                                king.setFlaggedAsHavingAlreadyMoved(true);
                                log.error("White King is flaggedAsHavingAlreadyMoved because it is not on its initial position.");
                            }
                        }
                    }
                    if (piece.getName().equals(PieceNames.ROOK)) {
                        Rook rook = (Rook) piece;
                        if (rook.getColor().equals(PlayerColor.BLACK)) {
                            if (row != 0 || (col != 0 && col != 7)){
                                rook.setFlaggedAsHavingAlreadyMoved(true);
                                log.debug("Black Rook {} is flaggedAsHavingAlreadyMoved because it is not on any of its initial positions.", rook);
                            }
                        } else {
                            if (row != 7 || (col != 0 && col != 7)) {
                                rook.setFlaggedAsHavingAlreadyMoved(true);
                                log.debug("White Rook {} is flaggedAsHavingAlreadyMoved because it is not on any of its initial positions.", rook);
                            }
                        }
                    }
                    chessboard.setPiece(piece, row, col);
                    log.debug("At coordinates {}, {} I just placed a {} {}", row, col, piece.getColor(), piece.getName());
                    col++;
                }
            }
        }
    }

    public void makeMove (@NonNull List<Move> legalMovesForColor, @NonNull Move move) {
        log.debug("About to execute {} move {}", move.getName(), move);
        boolean isCaptureOrPawnMove = move.execute(chessboard);
        if (isCaptureOrPawnMove) {
            log.debug("Reinitializing counter of non-capture and non-pawn moves to 1.");
            numberOfNonCaptureNonPawnMoves = 1;
            //TODO: re-introduce gameStateHistory for the 50 moves draw
            //gameStateHistory.clear();
        } else {
            numberOfNonCaptureNonPawnMoves++;
            log.debug("Incrementing numberOfNonCaptureNonPawnMoves counter to {}.", numberOfNonCaptureNonPawnMoves);
        }
        partialListOfExecutedMoves.add(move);
        totalListOfExecutedMoves.add(move);
        listOfReExecutableMoves.add(move);
        log.debug("Partial list of executed moves: {}", partialListOfExecutedMoves);
        log.debug("Total list of executed moves: {}", totalListOfExecutedMoves);
        log.debug("Total list of re-executable moves: {}", listOfReExecutableMoves);
        log.debug("About to set last executed move to {} for {}", move, chessboard.getCurrentPlayerColor());
        chessboard.setLastExecutedMove(move);
        listOfReExecutableMoves.clear();
        log.trace("Command history: {}", commandHistory);
        commandHistory.add (move.toString());
        LocalGameState localGameStateAfterMakeMove = new LocalGameState(chessboard);
        Long zobristKeyAfterMakeMove = chessboard.getNewZobristKey(localGameStateAfterMakeMove);
        log.error("Local state after make move {}: {}", move, localGameStateAfterMakeMove);
        if (chessboard.containsZobristKey(zobristKeyAfterMakeMove)) {
            int numberOfOccurrences = chessboard.getZobristValue(zobristKeyAfterMakeMove);
            numberOfOccurrences++;
            log.error("The current position has been reached {} times.", numberOfOccurrences);
            chessboard.saveZobristEntry(zobristKeyAfterMakeMove, numberOfOccurrences);
        } else {
            chessboard.saveZobristEntry(zobristKeyAfterMakeMove, 1);
        }
        checkGameOver(legalMovesForColor, chessboard, zobristKeyAfterMakeMove);
        if (isGameOver()) {
            commandHistory.add("Game over");
            log.error("Game Over. Game result: {}. Analysis can continue if needed.", getGameResult());
        }  else {
            log.error("No game ending condition found.");
        }
        chessboard.switchCurrentPlayerColor();
      //  log.error("Switched color from {} to {}", chessboard.getCurrentPlayerColor(),
       //         PlayerColor.getOpponentColor(chessboard.getCurrentPlayerColor()));
    }

    public void undo (@NonNull Move move, LocalGameState localGameStateBeforeMakeMove) {
        assert chessboard.isEmpty(move.getFrom()) : "To be able to undo move " + move +
                " the original 'from' position " + move.getFrom() + " should be empty." +
                " There is instead a " + chessboard.getPiece(move.getFrom());
        assert !(chessboard.isEmpty(move.getTo())): "To be able to undo move " + move +
                " the destination position " + move.getTo() + " should contain a piece." + chessboard;
        log.trace("chessboard before undo of move {}: {}", move, chessboard);
        log.error("Move to undo: {}", move);
        log.error("Key associated to local state before execute: {}.", localGameStateBeforeMakeMove.getZobristKey());
        log.error("List of keys saved in memory: {}", chessboard.getZobristListOfKeys());
        assert chessboard.containsZobristKey(localGameStateBeforeMakeMove.getZobristKey()) : "Zobrist key "
                 + localGameStateBeforeMakeMove.getZobristKey() + " must exist in memory before move " + move +
                " can be undone.";
        assert chessboard.getZobristValue(localGameStateBeforeMakeMove.getZobristKey()) >= 1 :
                "Value associated to key from localGameStateBeforeExecute must be at least 1.";
        getChessboard().switchCurrentPlayerColor();
      //  log.error("Switched color from {} to {}", chessboard.getCurrentPlayerColor(),
       //         PlayerColor.getOpponentColor (chessboard.getCurrentPlayerColor()));
        Command poppedNonCaptureNonPawnCommand = pop (partialListOfExecutedMoves);
        log.debug("poppedNonCaptureNonPawnCommand: {}", poppedNonCaptureNonPawnCommand);
        if(poppedNonCaptureNonPawnCommand != null){
            Move moveToUndoFromListOfExecuted = (Move) poppedNonCaptureNonPawnCommand;
            assert moveToUndoFromListOfExecuted.equals(move) :
                    "Move to undo should be the same as move on top of stack of executed moves." ;
    //        if (isGameOver()) {
    //            log.error("Send 'game over' event to GUI, console, and/or evaluation engine.");
    //        }
            move.undo (chessboard, localGameStateBeforeMakeMove);
            numberOfNonCaptureNonPawnMoves--;
            log.error("Decrementing numberOfNonCaptureNonPawnMoves counter to {}.", numberOfNonCaptureNonPawnMoves);
            chessboard.setLastExecutedMove((Move) totalListOfExecutedMoves.peek());
            log.info("After undoing move: {} last executed move is now {}", move, partialListOfExecutedMoves.peek());
            commandHistory.add (move + " undone");
        } else {
            commandHistory.add ("Undo not possible.");
            log.error("There is no move to undo.");
        }
    }

    public void checkGameOver(List<Move> legalMovesForColor, Board chessboard, Long zobristKey) {
        log.error("Looking for checkmates, stalemates, and different kinds of draws...");
      //  List<Move> legalMovesForColor = getLegalMovesForColor(chessboard.getCurrentPlayerColor());
        if (legalMovesForColor.isEmpty()) {
            if (chessboard.isPlayerInCheck(chessboard.getCurrentPlayerColor())) {
                gameResult = new GameResult(PlayerColor.getOpponentColor(chessboard.getCurrentPlayerColor()),
                        GameEndReason.CHECKMATE);
            } else {
                gameResult = new GameResult(PlayerColor.UNDETERMINED, GameEndReason.STALEMATE_DRAW);
            }
        } else {
            if (chessboard.isPlayerInCheck(chessboard.getCurrentPlayerColor())) {
                log.debug("{} is in check.", chessboard.getCurrentPlayerColor());
            } else {
                log.debug("{} is not in check.", chessboard.getCurrentPlayerColor());
            }
            if (chessboard.isInsufficientMaterialDraw()) {
                gameResult = new GameResult(PlayerColor.UNDETERMINED, GameEndReason.INSUFFICIENT_MATERIAL_DRAW);
            }
            if (isThreefoldRepetitionDraw(zobristKey)) {
                gameResult = new GameResult(PlayerColor.UNDETERMINED, GameEndReason.THREEFOLD_REPETITION_DRAW);
            }
            if (isFiftyMovesRuleDraw()) {
                gameResult = new GameResult(PlayerColor.UNDETERMINED, GameEndReason.FIFTY_MOVES_RULE_DRAW);
            }
            //TODO add two more outcomes: resignation and agreed draw.
        }
        if (isGameOver()){
            log.error("GAME OVER. Game result: {}", getGameResult());
        } else {
            log.error("There are no checkmates, stalemates, or insufficient material on the board.");
        }
        log.error("Checkers pieces: {}", ((chessboard.getCheckerPieces().isEmpty()) ?
                "None" : chessboard.getCheckerPieces().toString()));
    }

    private Command pop (List<Command> commandList) {
        if(!commandList.isEmpty()) {
            return commandList.remove(commandList.size() - 1);
        } else {
            return null;
        }
    }

    public boolean isGameOver(){
        return gameResult != null;
    }

    private boolean isFiftyMovesRuleDraw (){
        int fullMoves = numberOfNonCaptureNonPawnMoves / 2;
        return (fullMoves == 50);
    }

    private boolean isThreefoldRepetitionDraw(Long zobristKey){
        if (chessboard.containsZobristKey(zobristKey)) {
            int value = chessboard.getZobristValue(zobristKey);
            log.error("zobristKey: {} encountered {} times.", zobristKey, value);
            return (value >= 3);
        }
        return  false;
    }
}