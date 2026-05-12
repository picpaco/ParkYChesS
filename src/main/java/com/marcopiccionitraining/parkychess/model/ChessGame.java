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
    private int numberOfMovesWhichAreNeitherCaptureNorPawnMoves;

    @Getter @Setter
    private String stateString;

    /**
     * Game history where keys are unique strings identifying a position and values
     * counting how many times that position has been reached.
     * It is used to check the 50-moves draw rule.
     */
    private final HashMap<String, Integer> gameStateHistory = new HashMap<>();
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
     */
    private final LinkedList<Command> partialListOfExecutedMoves = new LinkedList<>();
    /**
     * Contains all executed moves.
     */
    private final LinkedList<Command> totalListOfExecutedMoves = new LinkedList<>();
    /**
     * Contains all moves which can be re-executed after the corresponding undo command.
     * TODO: Implement redo
     */
    private final LinkedList<Command> listOfReExecutableMoves = new LinkedList<>();
    /**
     * Stores all the commands used on the moves, including execute, undo, and redo.
     */
    private final LinkedList<String> commandHistory = new LinkedList<>();

    /**
     * Initializes an instance of a chess game.
     *
     * @param externalFEN provides the initial position in FEN format.
     */
    public ChessGame(@NonNull String externalFEN) {
        log.trace("External FEN: {}", externalFEN);
        if (!(isValidAsFEN(externalFEN))) {
            throw new IllegalArgumentException("The input FEN string is not valid.");
        }
        gameStateHistory.put(stateString, 1);
        chessboard = new Board(PlayerColor.UNDETERMINED);
        setPositionFromFEN(externalFEN);
        assert !(chessboard.getPiecesPositions().isEmpty()) : "The chessboard should contain at least tWo pieces.";
        assert chessboard.getCurrentPlayerColor().equals(PlayerColor.WHITE) || chessboard.getCurrentPlayerColor().equals(PlayerColor.BLACK) :
                "Player color must be either white or black.";
        log.debug("Chessboard after setting position from FEN: {}", chessboard);
        log.debug("Color moving first: {}", chessboard.getCurrentPlayerColor());
        stateString = new FENStateString(chessboard.getCurrentPlayerColor(),chessboard).toString();
        log.debug("Just created the first state string: {}", stateString);
        if (chessboard.isPlayerInCheck(chessboard.getCurrentPlayerColor())) {
            List<Piece> checkingPieces = chessboard.getCheckingPieces();
            assert checkingPieces.size() <= 2 : "Number of checking pieces too high:" + checkingPieces;
            if (checkingPieces.size() == 2) {
                log.debug("Player {} is in double check. Checkers list: {}", chessboard.getCurrentPlayerColor(), checkingPieces);
            } else {
                if (checkingPieces.size() == 1) {
                    log.debug("Player {} is in check. Checkers list: {}", chessboard.getCurrentPlayerColor(), checkingPieces);
                } else {
                    log.debug("Player {} is not in check.", chessboard.getCurrentPlayerColor());
                }
            }
        }
        FENStateString historyItem = chessboard.getGameStateContainer().peek();
        log.debug("After a peek at the game state stack its size is {} and the last executed move is {}",
                chessboard.getGameStateContainer().size(), chessboard.getLastExecutedMove());
        String previousHistoryItem = String.valueOf(gameStateHistory.put(
                (historyItem == null ? null : historyItem.toString()), 1));
        if (previousHistoryItem.equals("null")) {
            log.trace("No history item to record.");
        } else {
            log.trace("First history item: {} was recorded.", previousHistoryItem);
        }
        log.trace("Finished creating chess game.");
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
        //   log.trace("In getColumnFromFENSection ({}", fenSection);
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
        log.trace(chessboard.toString());
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
            if (fenSections[3].equals("a6") || fenSections[3].equals("b6") || fenSections[3].equals("c6") ||
                    fenSections[3].equals("d6") || fenSections[3].equals("e6") || fenSections[3].equals("f6") ||
                    fenSections[3].equals("g6") || fenSections[3].equals("h6")) {
                chessboard.setCurrentPlayerColor(PlayerColor.BLACK);
                //TODO: assert that there must be a black pawn in one of a5, b5, c5, d5, e5, f5, g5, h5.
                log.trace("About to set the pawn skipped position for Black to {}", fenSections[3]);
                chessboard.setSkippedPawnPosition(PlayerColor.BLACK, fromAlgebraicToPosition(fenSections[3]));
                Position lastMoveBlackFromPosition = (new Position(chessboard.getPawnSkippedPosition(PlayerColor.BLACK).getRow() - 1,
                        chessboard.getPawnSkippedPosition(PlayerColor.BLACK).getColumn()));
                Position lastMoveBlackToPosition = (new Position(chessboard.getPawnSkippedPosition(PlayerColor.BLACK).getRow() + 1,
                        chessboard.getPawnSkippedPosition(PlayerColor.BLACK).getColumn()));
                log.trace("blackFromPosition: {} blackToPosition: {}", lastMoveBlackFromPosition, lastMoveBlackToPosition);
                chessboard.setPiece(chessboard.getPiece(lastMoveBlackToPosition), lastMoveBlackFromPosition);
                chessboard.setEmpty(lastMoveBlackToPosition);
                DoublePawnMove doublePawnMove = new DoublePawnMove(lastMoveBlackFromPosition, lastMoveBlackToPosition, chessboard);
                doublePawnMove.execute(chessboard);
                chessboard.setLastExecutedMove(doublePawnMove);
                chessboard.setCurrentPlayerColor(PlayerColor.WHITE);
            } else {
                if (fenSections[3].equals("a3") || fenSections[3].equals("b3") || fenSections[3].equals("c3") ||
                        fenSections[3].equals("d3") || fenSections[3].equals("e3") || fenSections[3].equals("f3") ||
                        fenSections[3].equals("g3") || fenSections[3].equals("h3")) {
                    chessboard.setCurrentPlayerColor(PlayerColor.WHITE);
                    //TODO: assert there must be a white pawn in one of a4, b4, c4, d4, e4, f4, g4, h4.
                    log.trace("About to set the pawn skipped position for White to {}", fenSections[3]);
                    chessboard.setSkippedPawnPosition(PlayerColor.WHITE, fromAlgebraicToPosition(fenSections[3]));
                    Position whiteFromPosition = (new Position(chessboard.getPawnSkippedPosition(PlayerColor.WHITE).getRow() + 1,
                            chessboard.getPawnSkippedPosition(PlayerColor.WHITE).getColumn()));
                    Position whiteToPosition = (new Position(chessboard.getPawnSkippedPosition(PlayerColor.WHITE).getRow() - 1,
                            chessboard.getPawnSkippedPosition(PlayerColor.WHITE).getColumn()));
                    log.trace("whiteFromPosition: {} whiteToPosition: {}", whiteFromPosition, whiteToPosition);
                    chessboard.setPiece(chessboard.getPiece(whiteToPosition), whiteFromPosition);
                    chessboard.setEmpty(whiteToPosition);
                    DoublePawnMove doublePawnMove = new DoublePawnMove(whiteFromPosition, whiteToPosition, chessboard);
                    doublePawnMove.execute(chessboard);
                    chessboard.setLastExecutedMove(doublePawnMove);
                    chessboard.setCurrentPlayerColor(PlayerColor.BLACK);
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
            log.trace("Symbol just read: {}", symbol);
            if (symbol == '/') {
                col = 0;
                log.trace("Switching from row {} to row {}", row, row + 1);
                row++;
            } else {
                if (Character.isDigit(symbol)) {
                    int numOfEmptySquares = Integer.parseInt(String.valueOf(symbol));
                    log.trace("I am about to set {} empty squares.", numOfEmptySquares);
                    for (int i = 0; i < numOfEmptySquares; i++, col++) {
                        log.trace("At coordinate {},{} I just created an empty square.", row, col);
                        chessboard.setEmpty(new Position(row, col));
                    }
                } else {
                    Piece piece = ObjectFactory.getPieceFromFENSymbol(symbol);
                 //   piece.setPosition(new Position(row, col));
                    if (piece.getName().equals(PieceNames.KING)) {
                        King king = (King) piece;
                        if (king.getColor().equals(PlayerColor.BLACK)) {
                            if (row != 0 || col != 4) {
                                king.setFlaggedAsHavingAlreadyMoved(true);
                                log.debug("Black King is flaggedAsHavingAlreadyMoved because it is not on its initial position.");
                            }
                        } else {
                            if (row != 7 || col != 4) {
                                king.setFlaggedAsHavingAlreadyMoved(true);
                                log.debug("White King is flaggedAsHavingAlreadyMoved because it is not on its initial position.");
                            }
                        }
                    }
                    if (piece.getName().equals(PieceNames.ROOK)) {
                        Rook rook = (Rook) piece;
                        if (rook.getColor().equals(PlayerColor.BLACK)) {
                            if (row != 0 || (col != 0 && col != 7)) {
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
                    log.trace("At position {}, {} I just placed a {} {}", row, col, piece.getColor(), piece.getName());
                    col++;
                }
            }
        }
    }

     public List<Move> getLegalMovesForPieceAtPosition(@NonNull Position position){
        log.trace("In getLegalMovesForPieceAtPosition(position  = {})", position);
        List<Move> legalMovesForPiece = new ArrayList<>();
        if (chessboard.isEmpty(position)){
            return legalMovesForPiece;
        }
        Piece piece = chessboard.getPiece(position);
        List<Move> pseudoLegalMoves = piece.getPseudoLegalMoves(position, chessboard);
        log.debug("PseudoLegalMoves for {}: {}", position, pseudoLegalMoves);
        for (Move pseudoLegalMove : pseudoLegalMoves) {
            log.debug("Analyzing pseudoLegalMove: {} to see if it is legal:", pseudoLegalMove);
            if (pseudoLegalMove.isLegal(chessboard.copy())){
                log.debug("PseudoLegalMove: {} is legal.", pseudoLegalMove);
                legalMovesForPiece.add(pseudoLegalMove);
            }
        }
        log.debug("Legal moves for piece {}:", chessboard.getPiece(position) + position.toString() +
                " " + legalMovesForPiece);
        return legalMovesForPiece;
    }

    /**
     * @param playerColor the color of the player who must make the next move.
     * @return all the legal moves available to playerColor.
     */
    public List<Move> getLegalMovesForColor (PlayerColor playerColor) {
        List<Move> legalMovesForColor = new ArrayList<>();
        List<Position> piecesPositionsForCurrentColor = chessboard.getPiecesPositionsForColor(playerColor);
        for (Position piecePositionForColor : piecesPositionsForCurrentColor) {
            Piece piece = chessboard.getPiece(piecePositionForColor);
            List<Move> pseudoLegalMovesForColor = piece.getPseudoLegalMoves (piecePositionForColor, chessboard);
            log.trace("Finding legal moves for {}: ", playerColor);
            for (Move pseudoLegalMove : pseudoLegalMovesForColor) {
                if (pseudoLegalMove.isLegal(chessboard.copy())) {
                    log.trace("Adding pseudoLegalMove: {} {} to legal moves for {}.",
                            pseudoLegalMove.getName(), pseudoLegalMove, playerColor);
                    legalMovesForColor.add(pseudoLegalMove);
                } else {
                    log.trace("PseudoLegalMove: {} {} is illegal. Position before the illegal move: {}",
                            pseudoLegalMove.getName(), pseudoLegalMove, chessboard);
                }
            }
        }
        return legalMovesForColor;
    }

    public void makeMove(@NonNull Move move, @NonNull List<Move> legalMovesforColor) {
        log.trace("About to process move {}", move);
        boolean isCaptureOrPawnMove = move.execute(chessboard);
        if (isCaptureOrPawnMove) {
            numberOfMovesWhichAreNeitherCaptureNorPawnMoves = 0;
            gameStateHistory.clear();
        } else {
            numberOfMovesWhichAreNeitherCaptureNorPawnMoves++;
        }
        partialListOfExecutedMoves.add(move);
        totalListOfExecutedMoves.add(move);
        listOfReExecutableMoves.add(move);
        log.trace("Partial list of executed moves: {}", partialListOfExecutedMoves);
        log.trace("Total list of executed moves: {}", totalListOfExecutedMoves);
        log.trace("Total list of re-executable moves: {}", listOfReExecutableMoves);
        log.trace("About to set last executed move to {} for {}", move, chessboard.getCurrentPlayerColor());
        GameStateContainer container = chessboard.getGameStateContainer();
        chessboard.setLastExecutedMove(move);
        if (!(container.isEmpty())) {
            listOfReExecutableMoves.clear();
            commandHistory.add(move.toString());
            log.trace("Command history: {}", commandHistory);
            updateStateAfterMoveToEnforceDrawByRepetition();
            checkGameEnd(legalMovesforColor);
            if (isGameOver()) {
                log.debug("Game over. {}", gameResult);
                log.debug(chessboard.toString());
                //System.exit(0);
            }
        } else {
            container.push(new FENStateString(chessboard.getCurrentPlayerColor(), chessboard));
            log.debug("Container was empty. Created a new FENStateString: {}", stateString);
        }
    }

    public void undo (@NonNull Move move) {
        assert !(chessboard.getGameStateContainer().isEmpty()) : "Game state container cannot be empty here";
        log.trace("{} is about to undo move {}", chessboard.getCurrentPlayerColor(), move);
        assert chessboard.isEmpty(move.getFrom()) : "To be able to undo move " + move +
                " the original 'from' square " + move.getFrom() + " should be empty." +
                " There is instead a " + chessboard.getPiece(move.getFrom());
        assert !(chessboard.isEmpty(move.getTo())): "To be able to undo move " + move +
                " the destination square " + move.getTo() + " should contain a piece." + chessboard;
        log.trace("chessboard before undo of move {}: {}", move, chessboard);
        Command poppedCommand = pop (partialListOfExecutedMoves);
        if(poppedCommand != null){
            Move moveToUndoFromListOfExecuted = (Move) poppedCommand;
            assert moveToUndoFromListOfExecuted.equals(move) : "Move to undo should be the same " +
                    "as move on top of list of executed moves." ;
            log.trace("About to undo: {}", move);
            move.undo (chessboard);
            chessboard.setLastExecutedMove((Move) partialListOfExecutedMoves.peek());
            log.debug("After undoing move: {} last move is now {}", move, partialListOfExecutedMoves.peek());
            commandHistory.add(move + " undone");
        } else {
            commandHistory.add("Undo not possible.");
            log.debug("Undo not possible: there is no move to undo.");
        }
    }

    private Command pop (List<Command> commandList) {
        if(!commandList.isEmpty()) {
            return commandList.remove(commandList.size() - 1);
        } else {
            return null;
        }
    }

    public void checkGameEnd(@NonNull List<Move> legalMovesforColor){
        log.trace("In checkGameEnd");
        if (legalMovesforColor.isEmpty()) {
            log.trace("Checking for checkmate and stalemate...");
            if (chessboard.isPlayerInCheck(chessboard.getCurrentPlayerColor())) {
                log.trace("There is a checkmate on the board.");
                gameResult = new GameResult(PlayerColor.getOpponentColor(chessboard.getCurrentPlayerColor()), GameEndReason.CHECKMATE);
            } else if (!(chessboard.isPlayerInCheck(chessboard.getCurrentPlayerColor()))) {
                log.trace("There is a stalemate.");
                gameResult = new GameResult(PlayerColor.UNDETERMINED, GameEndReason.STALEMATE_DRAW);
            }
        } else {
            if (chessboard.checkForInsufficientMaterialDraw()){
                log.trace("There is a draw due to insufficient material.");
                gameResult = new GameResult(PlayerColor.UNDETERMINED, GameEndReason.INSUFFICIENT_MATERIAL_DRAW);
            } else {
                if (isFiftyMovesRuleDraw()){
                    log.trace("There is a draw due to the fifty moves rule.");
                    gameResult = new GameResult(PlayerColor.UNDETERMINED, GameEndReason.FIFTY_MOVES_RULE_DRAW);
                } else if (isThreefoldRepetitionHappening()){
                    log.trace("There is a draw due to the threefold repetition rule.");
                    gameResult  = new GameResult(PlayerColor.UNDETERMINED, GameEndReason.THREEFOLD_REPETITION_DRAW);
                }
            }
        }
        //TODO add win because of resignation and agreed draw.
    }

    public boolean isGameOver(){
        return gameResult != null;
    }

    private boolean isFiftyMovesRuleDraw (){
        int fullMoves = numberOfMovesWhichAreNeitherCaptureNorPawnMoves / 2;
        return fullMoves == 50;
    }

    private void updateStateAfterMoveToEnforceDrawByRepetition(){
        stateString = new FENStateString(chessboard.getCurrentPlayerColor(), chessboard).toString();
        log.debug("Created state string: {} to check if a draw by repetition is happening.", stateString);
        if (gameStateHistory.containsKey(stateString)){
            log.debug("The current position {} was already reached {} times.", chessboard, gameStateHistory.get(stateString));
            int numberOfOccurrences = gameStateHistory.get(stateString);
            numberOfOccurrences++;
            gameStateHistory.put(stateString, numberOfOccurrences);
        } else {
            log.trace("The current position was never encountered before.");
            gameStateHistory.put(stateString, 1);
        }
    }

    private boolean isThreefoldRepetitionHappening(){
                return gameStateHistory.get(stateString) == 3;
    }
}