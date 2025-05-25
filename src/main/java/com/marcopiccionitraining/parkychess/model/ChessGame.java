package com.marcopiccionitraining.parkychess.model;

import com.marcopiccionitraining.parkychess.ObjectFactory;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import com.marcopiccionitraining.parkychess.model.pieces.Piece;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ChessGame {

    @Getter
    private final Board chessboard;
    private PlayerColor currentPlayerColor;
    @Getter
    private GameResult gameResult;
    //TODO reset numberOfNoCaptureNoPawnMoves where appropriate.
    private int numberOfNoCaptureNoPawnMoves = 0;
    private String stateString;
    private final HashMap<String, Integer> gameStateHistory = new HashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(ChessGame.class);
    @Setter
    @Getter
    private int numberOfHalfMovesSinceLastCaptureOrPawnMove;
    @Getter
    private int numberOfFullMoves = 1;
    private final LinkedList<Command> listOfExecutedMoves = new LinkedList<>();
    private final LinkedList<Command> listOfReExecutableMoves = new LinkedList<>();
    private final LinkedList<String> commandHistory = new LinkedList<>();

    public ChessGame(Board chessboard, String externalFEN){
        this.chessboard = chessboard;
        FENString incapsulatedExternalFEN = new FENString(externalFEN);
        setPositionFromFen(incapsulatedExternalFEN);
        stateString = externalFEN;
        //LOGGER.info("incapsulatedExternalFEN: {}", incapsulatedExternalFEN);
        if (chessboard.isInCheck(PlayerColor.getOpponentColor(currentPlayerColor))){
            throw new RuntimeException("Color that has to move cannot be checking the opposing king at the same time.");
        }
        if (chessboard.isInCheck(currentPlayerColor)){
       //     System.out.println(currentPlayerColor + " is in check.");
            LOGGER.info("{} is in check.", currentPlayerColor);
            if (chessboard.isInDoubleCheck()){
                LOGGER.info("{} is in double check.", currentPlayerColor);
                //TODO: implement code that lists all the checkers.
            }
        }
        gameStateHistory.put(stateString, 1);
     //   LOGGER.trace("Exiting ChessGame 2-args constructor");
    }

    public void switchCurrentPlayerColor(){
        currentPlayerColor = PlayerColor.getOpponentColor(currentPlayerColor);
     //   LOGGER.trace("Switched color to {}", currentPlayerColor);
    }

    public PlayerColor getCurrentColor(){
        return currentPlayerColor;
    }

    private Position fromAlgebraicToPosition(String fenSection) {
        assert fenSection.length() == 2 : "An algebraic coordinate is made of 2 characters.";
        assert fenSection.startsWith("a") || fenSection.startsWith("b") || fenSection.startsWith("c") ||
                fenSection.startsWith("d") || fenSection.startsWith("e") || fenSection.startsWith("f") ||
                fenSection.startsWith("g") || fenSection.startsWith("h") : "fenSection string must start with " +
                "a, b, c, d, e, f, g, or h";
        assert fenSection.endsWith("1") || fenSection.endsWith("2") || fenSection.endsWith("3") ||
                fenSection.endsWith("4") || fenSection.endsWith("5") || fenSection.endsWith("6") ||
                fenSection.endsWith("7") || fenSection.endsWith("8") : "fenSection string must end with " +
                "1, 2, 3, 4, 5, 6, 7, 8";
     //   LOGGER.trace("Entered fromAlgebraicToPosition");
     //   LOGGER.trace("fenSection argument: {}", fenSection);
        return getPositionFromFEN(fenSection);
    }

    private static Position getPositionFromFEN(String fenSection) {
     //   LOGGER.trace("In getPositionFromFEN ({}", fenSection);
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
                assert false : "unexpected character for column.";
            }
        }
        int row = Integer.parseInt(fenSection.substring(1,2));
        return new Position(8 - row, column);
    }

    public void setPositionFromFen(FENString externalFen) {
        //   LOGGER.trace("entering setPositionFromFen");
        assert externalFen != null : "External FEN string must exist";
        String[] fenSections = externalFen.toString().split(" ");
        //  for (int i = 0; i < fenSections.length; i++) {
        //     LOGGER.trace("fenSections[{}]={} ", i, fenSections[i]);
        //  }
        //Place pieces on chessboard
        fromFenToChessboard(fenSections[0]);
        //  LOGGER.trace(chessboard.toString());
        //Set current color
        if (fenSections[1].equals("w")) {
            currentPlayerColor = PlayerColor.WHITE;
        } else {
            if (fenSections[1].equals("b")) {
                currentPlayerColor = PlayerColor.BLACK;
            } else {
                throw new RuntimeException("Unexpected character '" +
                        fenSections[1] + "' encountered in fen string while analyzing which color moves first.");
            }
        }
        //Is castle still possible?
        if (fenSections[2].length() == 1){
            compute1charCastlingRights(fenSections[2]);
        } else if (fenSections[2].length() == 2){
            compute2CharCastlingRights(fenSections[2]);
        } else if (fenSections[2].length() == 3){
            compute3CharCastlingRights(fenSections[2]);
        } else {
            chessboard.setBlackKingsideCastlingPossible(true);
            chessboard.setBlackQueensideCastlingPossible(true);
            chessboard.setWhiteKingsideCastlingPossible(true);
            chessboard.setWhiteQueensideCastlingPossible(true);
        }

        //Active tile for en passant capture
        if (fenSections[3].equals("-")) {
            chessboard.setEnPassantCapturePositionForColor(currentPlayerColor, null);
        } else {
            if (fenSections[3].contains("6")) {
                chessboard.setEnPassantCapturePositionForColor(PlayerColor.BLACK, fromAlgebraicToPosition(fenSections[3]));
            } else {
                if (fenSections[3].contains("3")) {
                    chessboard.setEnPassantCapturePositionForColor(PlayerColor.WHITE, fromAlgebraicToPosition(fenSections[3]));
                }
            }
        }
        //Number of half moves since last capture or pawn move.
        if (fenSections[4] != null) {
            numberOfHalfMovesSinceLastCaptureOrPawnMove = Integer.parseInt(fenSections[4]);
        }
        //Number of full moves in the game.
        if (fenSections[5] != null) {
            numberOfFullMoves = Integer.parseInt(fenSections[5]);
        }
    //    LOGGER.trace("exiting setPositionFromFen");
    }

    private void compute3CharCastlingRights(String fenSection) {
        assert fenSection.length() == 3 : "Castle rights should be 3 chars long in this case";
        if (fenSection.equals("KQk")) {
            chessboard.setWhiteKingsideCastlingPossible(true);
            chessboard.setWhiteQueensideCastlingPossible(true);
            chessboard.setBlackKingsideCastlingPossible(true);
            chessboard.setBlackQueensideCastlingPossible(false);
        } else {
            if (fenSection.equals("KQq")) {
                chessboard.setWhiteKingsideCastlingPossible(true);
                chessboard.setWhiteQueensideCastlingPossible(true);
                chessboard.setBlackKingsideCastlingPossible(false);
                chessboard.setBlackQueensideCastlingPossible(true);
            } else {
                if (fenSection.equals("Kkq")) {
                    chessboard.setWhiteKingsideCastlingPossible(true);
                    chessboard.setWhiteQueensideCastlingPossible(false);
                    chessboard.setBlackKingsideCastlingPossible(true);
                    chessboard.setBlackQueensideCastlingPossible(true);

                } else {
                    if (fenSection.equals("Qkq")) {
                        chessboard.setWhiteKingsideCastlingPossible(false);
                        chessboard.setWhiteQueensideCastlingPossible(true);
                        chessboard.setBlackKingsideCastlingPossible(true);
                        chessboard.setBlackQueensideCastlingPossible(true);
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
            chessboard.setWhiteKingsideCastlingPossible(true);
            chessboard.setWhiteQueensideCastlingPossible(true);
            chessboard.setBlackKingsideCastlingPossible(false);
            chessboard.setBlackQueensideCastlingPossible(false);
        } else {
            if (fenSection.equals("Kk")) {
                chessboard.setWhiteKingsideCastlingPossible(true);
                chessboard.setWhiteQueensideCastlingPossible(false);
                chessboard.setBlackKingsideCastlingPossible(true);
                chessboard.setBlackQueensideCastlingPossible(false);
            } else {
                if (fenSection.equals("Kq")) {
                    chessboard.setWhiteKingsideCastlingPossible(true);
                    chessboard.setWhiteQueensideCastlingPossible(false);
                    chessboard.setBlackKingsideCastlingPossible(false);
                    chessboard.setBlackQueensideCastlingPossible(true);
                } else {
                    if (fenSection.equals("Qk")) {
                        chessboard.setWhiteKingsideCastlingPossible(false);
                        chessboard.setWhiteQueensideCastlingPossible(true);
                        chessboard.setBlackKingsideCastlingPossible(true);
                        chessboard.setBlackQueensideCastlingPossible(false);
                    } else {
                        if (fenSection.equals("Qq")) {
                            chessboard.setWhiteKingsideCastlingPossible(false);
                            chessboard.setWhiteQueensideCastlingPossible(true);
                            chessboard.setBlackKingsideCastlingPossible(false);
                            chessboard.setBlackQueensideCastlingPossible(true);
                        } else {
                            if (fenSection.equals("kq")) {
                                chessboard.setWhiteKingsideCastlingPossible(false);
                                chessboard.setWhiteQueensideCastlingPossible(false);
                                chessboard.setBlackKingsideCastlingPossible(true);
                                chessboard.setBlackQueensideCastlingPossible(true);
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
            chessboard.setWhiteKingsideCastlingPossible(false);
            chessboard.setWhiteQueensideCastlingPossible(false);
            chessboard.setBlackKingsideCastlingPossible(false);
            chessboard.setBlackQueensideCastlingPossible(false);
        } else {
            if (fenSection.equals("K")) {
                chessboard.setWhiteKingsideCastlingPossible(true);
                chessboard.setWhiteQueensideCastlingPossible(false);
                chessboard.setBlackKingsideCastlingPossible(false);
                chessboard.setBlackQueensideCastlingPossible(false);
            } else {
                if (fenSection.equals("Q")) {
                    chessboard.setWhiteKingsideCastlingPossible(false);
                    chessboard.setWhiteQueensideCastlingPossible(true);
                    chessboard.setBlackKingsideCastlingPossible(false);
                    chessboard.setBlackQueensideCastlingPossible(false);
                } else {
                    if (fenSection.equals("k")) {
                        chessboard.setWhiteKingsideCastlingPossible(false);
                        chessboard.setWhiteQueensideCastlingPossible(false);
                        chessboard.setBlackKingsideCastlingPossible(true);
                        chessboard.setBlackQueensideCastlingPossible(false);
                    } else {
                        if (fenSection.equals("q")) {
                            chessboard.setWhiteKingsideCastlingPossible(false);
                            chessboard.setWhiteQueensideCastlingPossible(false);
                            chessboard.setBlackKingsideCastlingPossible(false);
                            chessboard.setBlackQueensideCastlingPossible(true);
                        } else {
                            assert false : "The 1-char arg string " + fenSection + " is wrong.";
                        }
                    }
                }
            }
        }
    }

    private void fromFenToChessboard (String fenPosition) {
      //  LOGGER.trace("Entering fromFenToChessboard");
        int col = 0;
        int row = 0;
        for (char symbol : fenPosition.toCharArray()) {
       //     LOGGER.trace("Symbol just read: {}", symbol);
            if (symbol == '/') {
                col = 0;
        //        LOGGER.trace("Switching from row {} to row {}", row, row + 1);
                row++;
            } else {
                if (Character.isDigit(symbol)) {
                    int numOfEmptySquares = Integer.parseInt(String.valueOf(symbol));
         //           LOGGER.trace("I am about to set {} empty squares.", numOfEmptySquares);
                    for (int i = 0; i < numOfEmptySquares; i++, col++) {
          //              LOGGER.trace("At coordinate {},{} I just set an empty square.", row, col);
                        chessboard.setEmpty(new Position(row,col));
                    }
                } else {
                    Piece piece = ObjectFactory.getPieceFromFENSymbol(symbol);
                    piece.setPosition(new Position(row,col));
                    chessboard.setPiece(piece, row, col);
                //    LOGGER.trace("At position {}, {} I just placed a {} {}", row, col, piece.getColor(), piece.getName());
                    col++;
                }
            }
        }
    //    LOGGER.trace("Exiting fromFenToChessboard");
    }

    public Collection<Move> getLegalMovesForPieceAtPosition(Position position){
   //     LOGGER.trace("In getLegalMovesForPieceAtPosition(position  = {}", position);
        if (chessboard.isEmpty(position)) {
     //       LOGGER.trace("Exit: no piece at square {}", position);
            return new ArrayList<>();
        }
        if (!(chessboard.getPiece(position).getColor().equals(currentPlayerColor))){
       //     LOGGER.trace("Exit: it is not {} turn to move", currentPlayerColor);
            return new ArrayList<>();
        }
        Piece piece = chessboard.getPiece(position);
   //     LOGGER.trace("There is a {} on square {}", piece, position);
        Collection<Move> legalMovesForPiece = new ArrayList<>();
        for (Move pseudoLegalMove : piece.getPseudoLegalMoves(position, chessboard)) {
   //         LOGGER.trace("Analyzing pseudoLegalMove: {}", pseudoLegalMove);
            if (pseudoLegalMove.isLegal(chessboard)){
      //          LOGGER.trace("PseudoLegalMove: {} is legal.", pseudoLegalMove);
                legalMovesForPiece.add(pseudoLegalMove);
            }
        }
   //     LOGGER.trace("getLegalMovesForPieceAtPosition(): Normal exit");
   //     LOGGER.trace("Legal moves for piece {} ", chessboard.getPiece(position) + position.toString() + " " + legalMovesForPiece);
        return legalMovesForPiece;
    }

    public Collection<Move> getLegalMovesForColor (PlayerColor playerColor) {
    //    LOGGER.info("In getLegalMovesForColor(playerColor = {}", playerColor);
        Collection<Move> legalMovesForColor = new ArrayList<>();
        for (Position piecePositionForColor : chessboard.getPiecesPositionsFor(playerColor)) {
            Piece piece = chessboard.getPiece(piecePositionForColor);
            for (Move pseudoLegalMove : piece.getPseudoLegalMoves(piecePositionForColor, chessboard)) {
                if (!(pseudoLegalMove.isLegal(chessboard))) {
                 //   System.out.println("Legal pseudoLegalMove: " + pseudoLegalMove.getName() + " " + pseudoLegalMove);
                //    System.out.println("isKingsideCastlingPossibleFromFEN for " + playerColor + ": " + chessboard.isKingsideCastlingPossibleFromFEN(playerColor));
                   // if (pseudoLegalMove.getName().equals(MoveNames.KINGSIDE_CASTLE) &&
                     //       !(chessboard.isKingsideCastlingPossibleFromFEN(playerColor))) {
                  //      System.out.println("Excluding kingside castle from legal Moves For " + playerColor);
                        continue;
                    }
     //               System.out.println("isQueensideCastlingPossibleFromFEN for " + playerColor + ": " +  chessboard.isQueensideCastlingPossibleFromFEN(playerColor));
             //       if (pseudoLegalMove.getName().equals(MoveNames.QUEENSIDE_CASTLE) &&
               //             !(chessboard.isQueensideCastlingPossibleFromFEN(playerColor))) {
                  //      System.out.println("Excluding queenside castle from legal Moves For " + playerColor);
                   //     continue;
                 //   }
                 //   System.out.println("Adding pseudoLegalMove: " + pseudoLegalMove.getName() + " " + pseudoLegalMove);
                    legalMovesForColor.add(pseudoLegalMove);
              //  }
            }
        }
        //    LOGGER.trace("Legal moves for {}: {}", playerColor, legalMovesForColor);
        return legalMovesForColor;
    }

    public void makeMove(Move move, Collection<Move> legalMovesforColor ){
      //  LOGGER.trace("Entering makeMove ({}", move);
        chessboard.setEnPassantCapturePositionForColor(currentPlayerColor, null);
        boolean isCaptureOrPawnMove = move.execute(chessboard);
        if (isCaptureOrPawnMove){
            numberOfNoCaptureNoPawnMoves = 0;
            gameStateHistory.clear();
        } else {
            numberOfNoCaptureNoPawnMoves++;
        }
        listOfExecutedMoves.add(move);
        chessboard.setLastExecutedMove(move);
        listOfReExecutableMoves.clear();
        commandHistory.add(move.toString());
    //    LOGGER.trace("listOfExecutedMoves:{}", listOfExecutedMoves);
    //    LOGGER.trace("history immediately after executing a move:{}", commandHistory);
        Move lastMove = (Move) listOfExecutedMoves.getLast();
        chessboard.setLastExecutedMove(lastMove);
        updateStateString();
        /*checkGameEnd(legalMovesforColor);
        if (isGameOver()){
            LOGGER.info("Game over. {}", gameResult);
        }*/
    }

    public void undo (Move move){
    //    LOGGER.debug("{} is about to undo move {}", currentPlayerColor, move);
        assert chessboard.getPiece(move.getFrom()) == null:"To be able to undo move " + move + " the starting square should be empty.";
        assert chessboard.getPiece(move.getTo()) != null:"To be able to undo move " + move + " the destination square should contain a piece.";
        Command poppedCommand = pop (listOfExecutedMoves);
        //Optional<Command> poppedCommand = pop (listOfExecutedMoves);
        if(poppedCommand != null){
            Move moveToRestoreFromListOfExecuted = (Move) poppedCommand;
            assert moveToRestoreFromListOfExecuted.equals(move) : "StandardMove to restore should be the same as move on top of list of executed moves." ;
      //      LOGGER.trace("Current move to restore: {}", move);
            commandHistory.add(move + " undone");
            } else {
            commandHistory.add("Undo not possible.");
        //    LOGGER.warn("Undo not possible: there is no move to undo.");
        }
        move.undo(chessboard);
    }

    private Command pop (List<Command> list) {
      //  private Optional<Command> pop (List<Command> list) {
        if(!list.isEmpty()) {
          //  return Optional.of(list.remove(list.size() - 1));
            return list.remove(list.size() - 1);
        } else {
          //  return Optional.empty();
            return null;
        }
    }

    public void checkGameEnd(Collection<Move> legalMovesforColor){
     //   LOGGER.trace("In checkGameEnd");
        if (legalMovesforColor.isEmpty()) {
        //    LOGGER.trace("Checking for checkmate and stalemate...");
            if (chessboard.isInCheck(currentPlayerColor)) {
                LOGGER.info("There is a checkmate.");
                gameResult = new GameResult(PlayerColor.getOpponentColor(currentPlayerColor), GameEndReason.CHECKMATE);
            } else if (!(chessboard.isInCheck (currentPlayerColor))) {
                LOGGER.info("There is a stalemate.");
                gameResult = new GameResult(PlayerColor.UNDETERMINED, GameEndReason.STALEMATE_DRAW);
            }
        } else {
            if (chessboard.checkForInsufficientMaterialDraw()){
                LOGGER.info("There is a draw due to insufficient material.");
                gameResult = new GameResult(PlayerColor.UNDETERMINED, GameEndReason.INSUFFICIENT_MATERIAL_DRAW);
            } else {
                if (isFiftyMovesRuleDraw()){
                    LOGGER.info("There is a draw due to the fifty moves rule.");
                    gameResult = new GameResult(PlayerColor.UNDETERMINED, GameEndReason.FIFTY_MOVES_RULE_DRAW);
                } else if (isThreefoldRepetitionHappening()){
                    LOGGER.info("There is a draw due to the threefold repetition rule.");
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
        int fullMoves = numberOfNoCaptureNoPawnMoves / 2;
        return fullMoves == 50;
    }

    private void updateStateString(){
     //   LOGGER.trace("Entering updateStateString");
        stateString = new FENString(this).toString();
        if (!(gameStateHistory.containsKey(stateString))){
            gameStateHistory.put(stateString, 1);
        } else {
            int numberOfOccurrences = gameStateHistory.get(stateString);
            numberOfOccurrences++;
            gameStateHistory.put(stateString, numberOfOccurrences);
        }
    }

    private boolean isThreefoldRepetitionHappening(){
        return gameStateHistory.get(stateString) == 3;
    }

    public void setCurrentColor(PlayerColor playerColor) {
        currentPlayerColor = playerColor;
    }
}