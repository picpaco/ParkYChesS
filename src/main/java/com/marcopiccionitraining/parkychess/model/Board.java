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
    private static final HashMap<Position, ArrayList<Position>> knightInsideBoardPositions = new HashMap<>();
    private static final HashMap<Position, ArrayList<ArrayList<Position>>> bishopInsideBoardPositions = new HashMap<>();

    static {
        initializeKnightPositionsInsideBoard();
        initializeBishopPositionsInsideBoard();
    }

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

    private static void initializeBishopPositionsInsideBoard() {
        ArrayList<ArrayList<Position>> positionsGroupedByDirection = new ArrayList<>();
        ArrayList<Position> positionsInDirection1 = new ArrayList<>();
        for (int i = 1; i < 8; i++) {//11,22,33,44,55,66,77
            positionsInDirection1.add(new Position(i, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        bishopInsideBoardPositions.put(new Position(0,0), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(1, 0));
        positionsGroupedByDirection.add(positionsInDirection1);
        ArrayList<Position> positionsInDirection2 = new ArrayList<>();
        for (int i = 1; i < 7; i++) {//12,23,34,45,56,67
            positionsInDirection2.add(new Position(i, i+1));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        bishopInsideBoardPositions.put(new Position(0,1), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(1, 1));
        positionsInDirection1.add(new Position(2, 0));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 1; i < 6; i++) {//13,24,35,46,57
            positionsInDirection2.add(new Position(i, i+2));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        bishopInsideBoardPositions.put(new Position(0,2), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection2 = new ArrayList<>();
        for (int i = 1; i < 5; i++) {//14,25,36,47
            positionsInDirection1.add(new Position(i, i+3));
        }
        for (int i = 1; i < 4; i++) {//12,21,30
            positionsInDirection2.add(new Position(i, 3-i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsGroupedByDirection.add(positionsInDirection2);
        bishopInsideBoardPositions.put(new Position(0,3), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection2 = new ArrayList<>();
        for (int i = 1; i < 4; i++) {//15,26,37
            positionsInDirection1.add(new Position(i, i+4));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        for (int i = 1; i < 5; i++) {//13,22,31,40
            positionsInDirection2.add(new Position(i, 4-i));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        bishopInsideBoardPositions.put(new Position(0,4), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection2 = new ArrayList<>();
        for (int i = 1; i < 3; i++) {//16,27
            positionsInDirection1.add(new Position(i, i+5));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        for (int i = 1; i < 6; i++) {//14,23,32,41,50
            positionsInDirection2.add(new Position(i, 5-i));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        bishopInsideBoardPositions.put(new Position(0,5), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection1.add(new Position(1, 7));
        positionsGroupedByDirection.add(positionsInDirection1);
        for (int i = 1; i < 7; i++) {//15,24,33,42,51,60
            positionsInDirection2.add(new Position(i, 6-i));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        bishopInsideBoardPositions.put(new Position(0,6), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 1; i < 8; i++) {//16,25,34,43,52,61,70
            positionsInDirection1.add(new Position(i, 7-i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        bishopInsideBoardPositions.put(new Position(0,7), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection1.add(new Position(0, 1));
        positionsGroupedByDirection.add(positionsInDirection1);
        for (int i = 2; i < 8; i++) {//21,32,43,54,65,76
            positionsInDirection2.add(new Position(i, i-1));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        bishopInsideBoardPositions.put(new Position(1,0), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection2 = new ArrayList<>();
        ArrayList<Position> positionsInDirection3 = new ArrayList<>();
        ArrayList<Position> positionsInDirection4 = new ArrayList<>();
        positionsInDirection1.add(new Position(0, 0));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2.add(new Position(0, 2));
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3.add(new Position(2, 0));
        positionsGroupedByDirection.add(positionsInDirection3);
        for (int i = 2; i < 8; i++) {//22,33,44,55,66,77
            positionsInDirection4.add(new Position(i, i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        bishopInsideBoardPositions.put(new Position(1,1), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection3 = new ArrayList<>();
        positionsInDirection4 = new ArrayList<>();
        positionsInDirection1.add(new Position(0, 1));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2.add(new Position(0, 3));
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3.add(new Position(2, 1));
        positionsInDirection3.add(new Position(3, 0));
        positionsGroupedByDirection.add(positionsInDirection3);
        for (int i = 2; i < 7; i++) {//23,34,45,56,67
            positionsInDirection4.add(new Position(i, i+1));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        bishopInsideBoardPositions.put(new Position(1,2), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(0, 2));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection2.add(new Position(0, 4));
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 2; i < 6; i++) {//24,35,46,57
            positionsInDirection3.add(new Position(i, i+2));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 2; i < 5; i++) {//22,31,40
            positionsInDirection4.add(new Position(i, 4-i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        bishopInsideBoardPositions.put(new Position(1,3), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(0, 3));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection2.add(new Position(0, 5));
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 2; i < 5; i++) {//25,36,47
            positionsInDirection3.add(new Position(i, i+3));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 2; i < 6; i++) {//23,32,41,50
            positionsInDirection4.add(new Position(i, 5-i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        bishopInsideBoardPositions.put(new Position(1,4), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(0, 4));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection2.add(new Position(0, 6));
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 2; i < 4; i++) {//26,37
            positionsInDirection3.add(new Position(i, i+4));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 2; i < 7; i++) {//24,33,42,51,60
            positionsInDirection4.add(new Position(i, 6-i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        bishopInsideBoardPositions.put(new Position(1,5), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(0, 5));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection2.add(new Position(0, 7));
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        positionsInDirection3.add(new Position(2, 7));
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 2; i < 8; i++) {//25,34,43,52,61,70
            positionsInDirection4.add(new Position(i, 7-i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        bishopInsideBoardPositions.put(new Position(1,6), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(0, 6));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 2; i < 8; i++) {//26,35,44,53,62,71
            positionsInDirection2.add(new Position(i, 8-i));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        bishopInsideBoardPositions.put(new Position(1,7), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(1, 1));
        positionsInDirection1.add(new Position(0, 2));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 3; i < 8; i++) {//31,42,53,64,75
            positionsInDirection2.add(new Position(i, i-2));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        bishopInsideBoardPositions.put(new Position(2,0), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(1, 0));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection2.add(new Position(3, 0));
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        positionsInDirection3.add(new Position(1, 2));
        positionsInDirection3.add(new Position(0, 3));
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 3; i < 8; i++) {//32,43,54,65,76
            positionsInDirection4.add(new Position(i, i-1));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        bishopInsideBoardPositions.put(new Position(2,1), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(1, 1));
        positionsInDirection1.add(new Position(0, 0));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection2.add(new Position(1, 3));
        positionsInDirection2.add(new Position(0, 4));
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        positionsInDirection3.add(new Position(3, 1));
        positionsInDirection3.add(new Position(4, 0));
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 3; i < 8; i++) {//33,44,55,66,77
            positionsInDirection4.add(new Position(i, i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        bishopInsideBoardPositions.put(new Position(2,2), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(1, 4));
        positionsInDirection1.add(new Position(0, 5));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection2.add(new Position(1, 2));
        positionsInDirection2.add(new Position(0, 1));
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        positionsInDirection3.add(new Position(3, 2));
        positionsInDirection3.add(new Position(4, 1));
        positionsInDirection3.add(new Position(5, 0));
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 3; i < 7; i++) {//34,45,56,67
            positionsInDirection4.add(new Position(i, i+1));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        bishopInsideBoardPositions.put(new Position(2,3), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(1, 3));
        positionsInDirection1.add(new Position(0, 2));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection2.add(new Position(3, 5));
        positionsInDirection2.add(new Position(4, 6));
        positionsInDirection2.add(new Position(5, 7));
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        positionsInDirection3.add(new Position(1, 5));
        positionsInDirection3.add(new Position(0, 6));
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 3; i < 7; i++) {//33,42,51,60
            positionsInDirection4.add(new Position(i, 6-i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        bishopInsideBoardPositions.put(new Position(2,4), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(1, 4));
        positionsInDirection1.add(new Position(0, 3));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection2.add(new Position(1, 6));
        positionsInDirection2.add(new Position(0, 7));
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        positionsInDirection3.add(new Position(3, 6));
        positionsInDirection3.add(new Position(4, 7));
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 3; i < 8; i++) {//34,43,52,61,70
            positionsInDirection4.add(new Position(i, 7-i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        bishopInsideBoardPositions.put(new Position(2,5), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(1, 7));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection2.add(new Position(1, 5));
        positionsInDirection2.add(new Position(0, 4));
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        positionsInDirection3.add(new Position(3, 7));
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 3; i < 8; i++) {//35,44,53,62,71
            positionsInDirection4.add(new Position(i, 8-i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        bishopInsideBoardPositions.put(new Position(2,6), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(1, 6));
        positionsInDirection1.add(new Position(0, 5));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 3; i < 8; i++) {//36,45,54,63,72
            positionsInDirection2.add(new Position(i, 9-i));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        bishopInsideBoardPositions.put(new Position(2,7), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 2; i >= 0; i--) {//21,12,03
            positionsInDirection1.add(new Position(i, 3-i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 4; i < 8; i++) {//41,52,63,74
            positionsInDirection2.add(new Position(i, i-3));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        bishopInsideBoardPositions.put(new Position(3,0), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(2, 0));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection2.add(new Position(4, 0));
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 2; i >= 0; i--) {//22,13,04
            positionsInDirection3.add(new Position(i, 4-i));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 4; i < 8; i++) {//42,53,64,75
            positionsInDirection4.add(new Position(i, i-2));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        bishopInsideBoardPositions.put(new Position(3,1), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(2, 1));
        positionsInDirection1.add(new Position(1, 0));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection2.add(new Position(4, 1));
        positionsInDirection2.add(new Position(5, 0));
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 4; i < 8; i++) {//43,54,65,76
            positionsInDirection3.add(new Position(i, i-1));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 2; i >= 0; i--) {//23,14,05
            positionsInDirection4.add(new Position(i, 5-i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        bishopInsideBoardPositions.put(new Position(3,2), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 2; i >= 0; i--) {//22,11,00
            positionsInDirection1.add(new Position(i, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 2; i >= 0; i--) {//24,15,06
            positionsInDirection2.add(new Position(i, 6-i));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 4; i < 8; i++) {//44,55,66,77
            positionsInDirection3.add(new Position(i, i));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 2; i >= 0; i--) {//22,11,00
            positionsInDirection4.add(new Position(i, i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        bishopInsideBoardPositions.put(new Position(3,3), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 2; i >= 0; i--) {//25,16,07
            positionsInDirection1.add(new Position(i, 7-i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 4; i < 8; i++) {//43,52,61,70
            positionsInDirection2.add(new Position(i, 7-i));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 4; i < 7; i++) {//45,56,67
            positionsInDirection3.add(new Position(i, i+1));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 2; i >= 0; i--) {//23,12,01
            positionsInDirection4.add(new Position(i, i+1));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        bishopInsideBoardPositions.put(new Position(3,4), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(2, 6));
        positionsInDirection1.add(new Position(1, 7));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection2.add(new Position(4, 6));
        positionsInDirection2.add(new Position(5, 7));
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 2; i >= 0; i--) {//24,13,02
            positionsInDirection3.add(new Position(i, i+2));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 4; i < 8; i++) {//44,53,62,71
            positionsInDirection4.add(new Position(i, 8-i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        bishopInsideBoardPositions.put(new Position(3,5), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(2, 7));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection2.add(new Position(4, 7));
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 2; i >= 0; i--) {//25,14,03
            positionsInDirection3.add(new Position(i, 3+i));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 4; i < 8; i++) {//45,54,63,72
            positionsInDirection4.add(new Position(i, 9-i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        bishopInsideBoardPositions.put(new Position(3,6), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 2; i >= 0; i--) {//26,15,04
            positionsInDirection1.add(new Position(i, 4+i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 4; i < 8; i++) {//46,55,64,73
            positionsInDirection2.add(new Position(i, 10-i));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        bishopInsideBoardPositions.put(new Position(3,7), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 3; i >= 0; i--) {//31,22,13,04
            positionsInDirection1.add(new Position(i, 4-i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 5; i < 8; i++) {//51,62,73
            positionsInDirection2.add(new Position(i, i-4));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        bishopInsideBoardPositions.put(new Position(4,0), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(3,0));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection2.add(new Position(5, 0));
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 3; i >= 0; i--) {//32,23,14,05
            positionsInDirection3.add(new Position(i, 5-i));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 5; i < 8; i++) {//52,63,74
            positionsInDirection4.add(new Position(i, i-3));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        bishopInsideBoardPositions.put(new Position(4,1), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(3,1));
        positionsInDirection1.add(new Position(2, 0));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection2.add(new Position(5,1));
        positionsInDirection2.add(new Position(6, 0));
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 3; i >= 0; i--) {//33,24,15,06
            positionsInDirection3.add(new Position(i, 6-i));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 5; i < 8; i++) {//53,64,75
            positionsInDirection4.add(new Position(i, i-2));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        bishopInsideBoardPositions.put(new Position(4,2), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 3; i > 0; i--) {//32,21,10
            positionsInDirection1.add(new Position(i, i-1));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 3; i >= 0; i--) {//34,25,16,07
            positionsInDirection2.add(new Position(i, 7-i));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 5; i < 8; i++) {//52,61,70
            positionsInDirection3.add(new Position(i, 7-i));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 5; i < 8; i++) {//54,65,76
            positionsInDirection4.add(new Position(i, i-1));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        bishopInsideBoardPositions.put(new Position(4,3), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 3; i >= 0; i--) {//33,22,11,00
            positionsInDirection1.add(new Position(i, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 3; i > 0; i--) {//35,26,17
            positionsInDirection2.add(new Position(i, 8-i));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 5; i < 8; i++) {//53,62,71
            positionsInDirection3.add(new Position(i, 8-i));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 5; i < 8; i++) {//55,66,77
            positionsInDirection4.add(new Position(i, i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        bishopInsideBoardPositions.put(new Position(4,4), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(3, 6));
        positionsInDirection1.add(new Position(2, 7));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection2.add(new Position(5, 6));
        positionsInDirection2.add(new Position(6, 7));
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 5; i < 8; i++) {//54,63,72
            positionsInDirection3.add(new Position(i, 9-i));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 3; i >= 0; i--) {//34,23,12,01
            positionsInDirection4.add(new Position(i, i+1));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        bishopInsideBoardPositions.put(new Position(4,5), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(3, 7));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection2.add(new Position(5, 7));
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 5; i < 8; i++) {//55,64,73
            positionsInDirection3.add(new Position(i, 10-i));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 3; i >= 0; i--) {//35,24,13,02
            positionsInDirection4.add(new Position(i, i+2));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        bishopInsideBoardPositions.put(new Position(4,6), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 5; i < 8; i++) {//56,65,74
            positionsInDirection1.add(new Position(i, 11-i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 3; i >= 0; i--) {//36,25,14,03
            positionsInDirection2.add(new Position(i, i+3));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        bishopInsideBoardPositions.put(new Position(4,7), positionsGroupedByDirection);
        //FIXME proceed from here

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(6, 1));
        positionsInDirection1.add(new Position(7, 2));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 4; i >= 0; i--) {//41,32,23,14,05
            positionsInDirection2.add(new Position(i, 5-i));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        bishopInsideBoardPositions.put(new Position(5,0), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(4, 0));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection2.add(new Position(6, 0));
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        positionsInDirection3.add(new Position(6, 2));
        positionsInDirection3.add(new Position(7, 3));
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 4; i >= 0; i--) {//42,33,24,15,06
            positionsInDirection4.add(new Position(i, 6-i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        bishopInsideBoardPositions.put(new Position(5,1), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(4, 1));
        positionsInDirection1.add(new Position(3, 0));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection2.add(new Position(6, 1));
        positionsInDirection2.add(new Position(7, 0));
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        positionsInDirection3.add(new Position(6, 3));
        positionsInDirection3.add(new Position(7, 4));
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 4; i >= 0; i--) {//43,34,25,16,07
            positionsInDirection4.add(new Position(i, 7-i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        bishopInsideBoardPositions.put(new Position(5,2), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(6, 2));
        positionsInDirection1.add(new Position(7, 1));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection2.add(new Position(6, 4));
        positionsInDirection2.add(new Position(7, 5));
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 4; i > 1; i--) {//42,31,20
            positionsInDirection3.add(new Position(i, i-2));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 4; i >= 1; i--) {//44,35,26,17
            positionsInDirection4.add(new Position(i, 8-i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        bishopInsideBoardPositions.put(new Position(5,3), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(6, 3));
        positionsInDirection1.add(new Position(7, 2));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection2.add(new Position(6, 5));
        positionsInDirection2.add(new Position(7, 6));
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 4; i > 1; i--) {//45,36,27
            positionsInDirection3.add(new Position(i, 9-i));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 4; i >= 1; i--) {//43,32,21,10
            positionsInDirection4.add(new Position(i, i-1));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        bishopInsideBoardPositions.put(new Position(5,4), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(6, 4));
        positionsInDirection1.add(new Position(7, 3));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection2.add(new Position(6, 6));
        positionsInDirection2.add(new Position(7, 7));
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        positionsInDirection3.add(new Position(4, 6));
        positionsInDirection3.add(new Position(3, 7));
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 4; i >= 0; i--) {//44,33,22,11,00
            positionsInDirection4.add(new Position(i, i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        bishopInsideBoardPositions.put(new Position(5,5), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(4, 7));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection2.add(new Position(6, 7));
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        positionsInDirection3.add(new Position(6, 5));
        positionsInDirection3.add(new Position(7, 4));
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 4; i >= 0; i--) {//45,34,23,12,01
            positionsInDirection4.add(new Position(i, i+1));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        bishopInsideBoardPositions.put(new Position(5,6), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(6, 6));
        positionsInDirection1.add(new Position(7, 5));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 4; i >= 0; i--) {//46,35,24,13,02
            positionsInDirection2.add(new Position(i, i+2));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        bishopInsideBoardPositions.put(new Position(5,7), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(7, 1));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {//51,42,33,24,15,06
            positionsInDirection2.add(new Position(i, 6-i));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        bishopInsideBoardPositions.put(new Position(6,0), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(5, 0));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection2.add(new Position(7, 0));
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        positionsInDirection3.add(new Position(7, 2));
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {//52,43,34,25,16,07
            positionsInDirection4.add(new Position(i, 7-i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        bishopInsideBoardPositions.put(new Position(6,1), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(5, 1));
        positionsInDirection1.add(new Position(4, 0));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection2.add(new Position(7, 1));
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        positionsInDirection3.add(new Position(7, 3));
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 5; i > 0; i--) {//53,44,35,26,17
            positionsInDirection4.add(new Position(i, 8-i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        bishopInsideBoardPositions.put(new Position(6,2), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(7, 2));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection2.add(new Position(7, 4));
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 5; i > 2; i--) {//52,41,30
            positionsInDirection3.add(new Position(i, i-3));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 5; i > 1; i--) {//54,45,36,27
            positionsInDirection4.add(new Position(i, 9-i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        bishopInsideBoardPositions.put(new Position(6,3), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(7, 3));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection2.add(new Position(7, 5));
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 5; i > 1; i--) {//53,42,31,20
            positionsInDirection3.add(new Position(i, i-2));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 5; i > 2; i--) {//55,46,37
            positionsInDirection4.add(new Position(i, 10-i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        bishopInsideBoardPositions.put(new Position(6,4), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(7, 4));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection2.add(new Position(7, 6));
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        positionsInDirection3.add(new Position(5, 6));
        positionsInDirection3.add(new Position(4, 7));
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 5; i > 0; i--) {//54,43,32,21,10
            positionsInDirection4.add(new Position(i, i-1));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        bishopInsideBoardPositions.put(new Position(6,5), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(7, 5));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection2.add(new Position(7, 7));
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        positionsInDirection3.add(new Position(5, 7));
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {//55,44,33,22,11,00
            positionsInDirection4.add(new Position(i, i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        bishopInsideBoardPositions.put(new Position(6,6), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();

        positionsInDirection1.add(new Position(7, 6));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {//56,45,34,23,12,01
            positionsInDirection2.add(new Position(i, i+1));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        bishopInsideBoardPositions.put(new Position(6,7), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {//61,52,43,34,25,16,07
            positionsInDirection1.add(new Position(i, 7-i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        bishopInsideBoardPositions.put(new Position(7,0), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(6, 0));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 6; i > 0; i--) {//62,53,44,35,26,17
            positionsInDirection2.add(new Position(i, 8-i));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        bishopInsideBoardPositions.put(new Position(7,1), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(6, 1));
        positionsInDirection1.add(new Position(5, 0));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 6; i > 1; i--) {//63,54,45,36,27
            positionsInDirection2.add(new Position(i, 9-i));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        bishopInsideBoardPositions.put(new Position(7,2), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 6; i > 2; i--) {//64,55,46,37
            positionsInDirection1.add(new Position(i, 10-i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 6; i > 3; i--) {//62,51,40
            positionsInDirection2.add(new Position(i, i-4));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        bishopInsideBoardPositions.put(new Position(7,3), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 6; i > 3; i--) {//65,56,47
            positionsInDirection1.add(new Position(i, 11-i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 6; i > 2; i--) {//63,52,41,30
            positionsInDirection2.add(new Position(i, i-3));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        bishopInsideBoardPositions.put(new Position(7,4), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(6, 6));
        positionsInDirection1.add(new Position(5, 7));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 6; i > 1; i--) {//64,53,42,31,20
            positionsInDirection2.add(new Position(i, i-2));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        bishopInsideBoardPositions.put(new Position(7,5), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(6, 7));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 6; i > 0; i--) {//65,54,43,32,21,10
            positionsInDirection2.add(new Position(i, i-1));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        bishopInsideBoardPositions.put(new Position(7,6), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {//66,55,44,33,22,11,00
            positionsInDirection1.add(new Position(i, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        bishopInsideBoardPositions.put(new Position(7,7), positionsGroupedByDirection);
    }

    private static void initializeKnightPositionsInsideBoard() {
        ArrayList<Position> destinations = new ArrayList<>();
        destinations.add(new Position(1, 2));
        destinations.add(new Position(2, 1));
        knightInsideBoardPositions.put(new Position(0,0), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(1, 3));
        destinations.add(new Position(2, 0));
        destinations.add(new Position(2, 2));
        knightInsideBoardPositions.put(new Position(0,1), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(1, 0));
        destinations.add(new Position(2, 1));
        destinations.add(new Position(2, 3));
        destinations.add(new Position(1, 4));
        knightInsideBoardPositions.put(new Position(0,2), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(1, 1));
        destinations.add(new Position(2, 2));
        destinations.add(new Position(2, 4));
        destinations.add(new Position(1, 5));
        knightInsideBoardPositions.put(new Position(0,3), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(1, 2));
        destinations.add(new Position(2, 3));
        destinations.add(new Position(2, 5));
        destinations.add(new Position(1, 6));
        knightInsideBoardPositions.put(new Position(0,4), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(1, 3));
        destinations.add(new Position(2, 4));
        destinations.add(new Position(2, 6));
        destinations.add(new Position(1, 7));
        knightInsideBoardPositions.put(new Position(0,5), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(1, 4));
        destinations.add(new Position(2, 5));
        destinations.add(new Position(2, 7));
        knightInsideBoardPositions.put(new Position(0,6), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(1, 5));
        destinations.add(new Position(2, 6));
        knightInsideBoardPositions.put(new Position(0,7), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(0, 2));
        destinations.add(new Position(2, 2));
        destinations.add(new Position(3, 1));
        knightInsideBoardPositions.put(new Position(1,0), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(0, 3));
        destinations.add(new Position(2, 3));
        destinations.add(new Position(3, 0));
        destinations.add(new Position(3, 2));
        knightInsideBoardPositions.put(new Position(1,1), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(0, 0));
        destinations.add(new Position(2, 0));
        destinations.add(new Position(3, 1));
        destinations.add(new Position(3, 3));
        destinations.add(new Position(2, 4));
        destinations.add(new Position(0, 4));
        knightInsideBoardPositions.put(new Position(1,2), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(0, 1));
        destinations.add(new Position(2, 1));
        destinations.add(new Position(3, 2));
        destinations.add(new Position(3, 4));
        destinations.add(new Position(2, 5));
        destinations.add(new Position(0, 5));
        knightInsideBoardPositions.put(new Position(1,3), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(0, 2));
        destinations.add(new Position(2, 2));
        destinations.add(new Position(3, 3));
        destinations.add(new Position(3, 5));
        destinations.add(new Position(2, 6));
        destinations.add(new Position(0, 6));
        knightInsideBoardPositions.put(new Position(1,4), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(0, 3));
        destinations.add(new Position(2, 3));
        destinations.add(new Position(3, 4));
        destinations.add(new Position(3, 6));
        destinations.add(new Position(2, 7));
        destinations.add(new Position(0, 7));
        knightInsideBoardPositions.put(new Position(1,5), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(0, 4));
        destinations.add(new Position(2, 4));
        destinations.add(new Position(3, 5));
        destinations.add(new Position(3, 7));
        knightInsideBoardPositions.put(new Position(1,6), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(0, 5));
        destinations.add(new Position(2, 5));
        destinations.add(new Position(3, 6));
        knightInsideBoardPositions.put(new Position(1,7), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(0, 1));
        destinations.add(new Position(1, 2));
        destinations.add(new Position(3, 2));
        destinations.add(new Position(4, 1));
        knightInsideBoardPositions.put(new Position(2,0), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(0, 2));
        destinations.add(new Position(1, 3));
        destinations.add(new Position(3, 3));
        destinations.add(new Position(4, 2));
        destinations.add(new Position(4, 0));
        knightInsideBoardPositions.put(new Position(2,1), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(0, 1));
        destinations.add(new Position(0, 3));
        destinations.add(new Position(1, 0));
        destinations.add(new Position(1, 4));
        destinations.add(new Position(3, 0));
        destinations.add(new Position(3, 4));
        destinations.add(new Position(4, 1));
        destinations.add(new Position(4, 3));
        knightInsideBoardPositions.put(new Position(2,2), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(0, 2));
        destinations.add(new Position(0, 4));
        destinations.add(new Position(1, 1));
        destinations.add(new Position(1, 5));
        destinations.add(new Position(3, 1));
        destinations.add(new Position(3, 5));
        destinations.add(new Position(4, 2));
        destinations.add(new Position(4, 4));
        knightInsideBoardPositions.put(new Position(2,3), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(0, 3));
        destinations.add(new Position(0, 5));
        destinations.add(new Position(1, 2));
        destinations.add(new Position(1, 6));
        destinations.add(new Position(3, 2));
        destinations.add(new Position(3, 6));
        destinations.add(new Position(4, 3));
        destinations.add(new Position(4, 5));
        knightInsideBoardPositions.put(new Position(2,4), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(0, 4));
        destinations.add(new Position(0, 6));
        destinations.add(new Position(1, 3));
        destinations.add(new Position(1, 7));
        destinations.add(new Position(3, 3));
        destinations.add(new Position(3, 7));
        destinations.add(new Position(4, 4));
        destinations.add(new Position(4, 6));
        knightInsideBoardPositions.put(new Position(2,5), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(0, 5));
        destinations.add(new Position(0, 7));
        destinations.add(new Position(1, 4));
        destinations.add(new Position(3, 4));
        destinations.add(new Position(4, 5));
        destinations.add(new Position(4, 7));
        knightInsideBoardPositions.put(new Position(2,6), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(0, 6));
        destinations.add(new Position(1, 5));
        destinations.add(new Position(3, 5));
        destinations.add(new Position(4, 6));
        knightInsideBoardPositions.put(new Position(2,7), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(1, 1));
        destinations.add(new Position(2, 2));
        destinations.add(new Position(4, 2));
        destinations.add(new Position(5, 1));
        knightInsideBoardPositions.put(new Position(3,0), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(1, 0));
        destinations.add(new Position(1, 2));
        destinations.add(new Position(2, 3));
        destinations.add(new Position(4, 3));
        destinations.add(new Position(5, 0));
        destinations.add(new Position(5, 2));
        knightInsideBoardPositions.put(new Position(3,1), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(1, 1));
        destinations.add(new Position(1, 3));
        destinations.add(new Position(2, 0));
        destinations.add(new Position(2, 4));
        destinations.add(new Position(4, 0));
        destinations.add(new Position(4, 4));
        destinations.add(new Position(5, 1));
        destinations.add(new Position(5, 3));
        knightInsideBoardPositions.put(new Position(3,2), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(1, 2));
        destinations.add(new Position(1, 4));
        destinations.add(new Position(2, 1));
        destinations.add(new Position(2, 5));
        destinations.add(new Position(4, 1));
        destinations.add(new Position(4, 5));
        destinations.add(new Position(5, 2));
        destinations.add(new Position(5, 4));
        knightInsideBoardPositions.put(new Position(3,3), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(1, 3));
        destinations.add(new Position(1, 5));
        destinations.add(new Position(2, 2));
        destinations.add(new Position(2, 6));
        destinations.add(new Position(4, 2));
        destinations.add(new Position(4, 6));
        destinations.add(new Position(5, 3));
        destinations.add(new Position(5, 5));
        knightInsideBoardPositions.put(new Position(3,4), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(1, 4));
        destinations.add(new Position(1, 6));
        destinations.add(new Position(2, 3));
        destinations.add(new Position(2, 7));
        destinations.add(new Position(4, 3));
        destinations.add(new Position(4, 7));
        destinations.add(new Position(5, 4));
        destinations.add(new Position(5, 6));
        knightInsideBoardPositions.put(new Position(3,5), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(1, 5));
        destinations.add(new Position(1, 7));
        destinations.add(new Position(2, 4));
        destinations.add(new Position(4, 4));
        destinations.add(new Position(5, 5));
        destinations.add(new Position(5, 7));
        knightInsideBoardPositions.put(new Position(3,6), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(1, 6));
        destinations.add(new Position(2, 5));
        destinations.add(new Position(4, 5));
        destinations.add(new Position(5, 6));
        knightInsideBoardPositions.put(new Position(3,7), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(2, 1));
        destinations.add(new Position(3, 2));
        destinations.add(new Position(5, 2));
        destinations.add(new Position(6, 1));
        knightInsideBoardPositions.put(new Position(4,0), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(2, 0));
        destinations.add(new Position(2, 2));
        destinations.add(new Position(3, 3));
        destinations.add(new Position(5, 3));
        destinations.add(new Position(6, 0));
        destinations.add(new Position(6, 2));
        knightInsideBoardPositions.put(new Position(4,1), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(2, 1));
        destinations.add(new Position(2, 3));
        destinations.add(new Position(3, 0));
        destinations.add(new Position(3, 4));
        destinations.add(new Position(5, 0));
        destinations.add(new Position(5, 4));
        destinations.add(new Position(6, 1));
        destinations.add(new Position(6, 3));
        knightInsideBoardPositions.put(new Position(4,2), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(2, 2));
        destinations.add(new Position(2, 4));
        destinations.add(new Position(3, 1));
        destinations.add(new Position(3, 5));
        destinations.add(new Position(5, 1));
        destinations.add(new Position(5, 5));
        destinations.add(new Position(6, 2));
        destinations.add(new Position(6, 4));
        knightInsideBoardPositions.put(new Position(4,3), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(2, 3));
        destinations.add(new Position(2, 5));
        destinations.add(new Position(3, 2));
        destinations.add(new Position(3, 6));
        destinations.add(new Position(5, 2));
        destinations.add(new Position(5, 6));
        destinations.add(new Position(6, 3));
        destinations.add(new Position(6, 5));
        knightInsideBoardPositions.put(new Position(4,4), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(2, 4));
        destinations.add(new Position(2, 6));
        destinations.add(new Position(3, 3));
        destinations.add(new Position(3, 7));
        destinations.add(new Position(5, 3));
        destinations.add(new Position(5, 7));
        destinations.add(new Position(6, 4));
        destinations.add(new Position(6, 6));
        knightInsideBoardPositions.put(new Position(4,5), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(2, 5));
        destinations.add(new Position(2, 7));
        destinations.add(new Position(3, 4));
        destinations.add(new Position(5, 4));
        destinations.add(new Position(6, 5));
        destinations.add(new Position(6, 7));
        knightInsideBoardPositions.put(new Position(4,6), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(2, 6));
        destinations.add(new Position(3, 5));
        destinations.add(new Position(5, 5));
        destinations.add(new Position(6, 6));
        knightInsideBoardPositions.put(new Position(4,7), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(3, 1));
        destinations.add(new Position(4, 2));
        destinations.add(new Position(6, 2));
        destinations.add(new Position(7, 1));
        knightInsideBoardPositions.put(new Position(5,0), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(3, 0));
        destinations.add(new Position(3, 2));
        destinations.add(new Position(4, 3));
        destinations.add(new Position(6, 3));
        destinations.add(new Position(7, 0));
        destinations.add(new Position(7, 2));
        knightInsideBoardPositions.put(new Position(5,1), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(3, 1));
        destinations.add(new Position(3, 3));
        destinations.add(new Position(4, 0));
        destinations.add(new Position(4, 4));
        destinations.add(new Position(6, 0));
        destinations.add(new Position(6, 4));
        destinations.add(new Position(7, 1));
        destinations.add(new Position(7, 3));
        knightInsideBoardPositions.put(new Position(5,2), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(3, 2));
        destinations.add(new Position(3, 4));
        destinations.add(new Position(4, 1));
        destinations.add(new Position(4, 5));
        destinations.add(new Position(6, 1));
        destinations.add(new Position(6, 5));
        destinations.add(new Position(7, 2));
        destinations.add(new Position(7, 4));
        knightInsideBoardPositions.put(new Position(5,3), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(3, 3));
        destinations.add(new Position(3, 5));
        destinations.add(new Position(4, 2));
        destinations.add(new Position(4, 6));
        destinations.add(new Position(6, 2));
        destinations.add(new Position(6, 6));
        destinations.add(new Position(7, 3));
        destinations.add(new Position(7, 5));
        knightInsideBoardPositions.put(new Position(5,4), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(3, 4));
        destinations.add(new Position(3, 6));
        destinations.add(new Position(4, 3));
        destinations.add(new Position(4, 7));
        destinations.add(new Position(6, 3));
        destinations.add(new Position(6, 7));
        destinations.add(new Position(7, 4));
        destinations.add(new Position(7, 6));
        knightInsideBoardPositions.put(new Position(5,5), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(3, 5));
        destinations.add(new Position(3, 7));
        destinations.add(new Position(4, 4));
        destinations.add(new Position(6, 4));
        destinations.add(new Position(7, 5));
        destinations.add(new Position(7, 7));
        knightInsideBoardPositions.put(new Position(5,6), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(3, 6));
        destinations.add(new Position(4, 5));
        destinations.add(new Position(6, 5));
        destinations.add(new Position(7, 6));
        knightInsideBoardPositions.put(new Position(5,7), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(4, 1));
        destinations.add(new Position(5, 2));
        destinations.add(new Position(7, 2));
        knightInsideBoardPositions.put(new Position(6,0), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(4, 0));
        destinations.add(new Position(4, 2));
        destinations.add(new Position(5, 3));
        destinations.add(new Position(7, 3));
        knightInsideBoardPositions.put(new Position(6,1), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(4, 1));
        destinations.add(new Position(4, 3));
        destinations.add(new Position(5, 0));
        destinations.add(new Position(5, 4));
        destinations.add(new Position(7, 0));
        destinations.add(new Position(7, 4));
        knightInsideBoardPositions.put(new Position(6,2), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(4, 2));
        destinations.add(new Position(4, 4));
        destinations.add(new Position(5, 1));
        destinations.add(new Position(5, 5));
        destinations.add(new Position(7, 1));
        destinations.add(new Position(7, 5));
        knightInsideBoardPositions.put(new Position(6,3), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(4, 3));
        destinations.add(new Position(4, 5));
        destinations.add(new Position(5, 2));
        destinations.add(new Position(5, 6));
        destinations.add(new Position(7, 2));
        destinations.add(new Position(7, 6));
        knightInsideBoardPositions.put(new Position(6,4), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(4, 4));
        destinations.add(new Position(4, 6));
        destinations.add(new Position(5, 3));
        destinations.add(new Position(5, 7));
        destinations.add(new Position(7, 3));
        destinations.add(new Position(7, 7));
        knightInsideBoardPositions.put(new Position(6,5), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(4, 5));
        destinations.add(new Position(4, 7));
        destinations.add(new Position(5, 4));
        destinations.add(new Position(7, 4));
        knightInsideBoardPositions.put(new Position(6,6), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(4, 6));
        destinations.add(new Position(5, 5));
        destinations.add(new Position(7, 5));
        knightInsideBoardPositions.put(new Position(6,7), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(5, 1));
        destinations.add(new Position(6, 2));
        knightInsideBoardPositions.put(new Position(7,0), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(5, 0));
        destinations.add(new Position(5, 2));
        destinations.add(new Position(6, 3));
        knightInsideBoardPositions.put(new Position(7,1), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(5, 1));
        destinations.add(new Position(5, 3));
        destinations.add(new Position(6, 0));
        destinations.add(new Position(6, 4));
        knightInsideBoardPositions.put(new Position(7,2), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(5, 2));
        destinations.add(new Position(5, 4));
        destinations.add(new Position(6, 1));
        destinations.add(new Position(6, 5));
        knightInsideBoardPositions.put(new Position(7,3), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(5, 3));
        destinations.add(new Position(5, 5));
        destinations.add(new Position(6, 2));
        destinations.add(new Position(6, 6));
        knightInsideBoardPositions.put(new Position(7,4), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(5, 4));
        destinations.add(new Position(5, 6));
        destinations.add(new Position(6, 3));
        destinations.add(new Position(6, 7));
        knightInsideBoardPositions.put(new Position(7,5), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(5, 5));
        destinations.add(new Position(5, 7));
        destinations.add(new Position(6, 4));
        knightInsideBoardPositions.put(new Position(7,6), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(5, 6));
        destinations.add(new Position(6, 5));
        knightInsideBoardPositions.put(new Position(7,7), destinations);
    }
    public List<Position> getPotentialKnightDestinations(Position from){
        return knightInsideBoardPositions.get(from);
    }

    public ArrayList<ArrayList<Position>> getPotentialBishopDestinations(Position from){
        return bishopInsideBoardPositions.get(from);
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
