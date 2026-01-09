package com.marcopiccionitraining.parkychess.model;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public record PseudoLegalPiecesPositionsInitializer() {
    private static final HashMap<Position, ArrayList<Position>> knightPseudoLegalPositions = new HashMap<>();
    private static final HashMap<Position, ArrayList<ArrayList<Position>>> bishopPseudoLegalPositions = new HashMap<>();
    private static final HashMap<Position, ArrayList<ArrayList<Position>>> rookPseudoLegalPositions = new HashMap<>();
    private static final HashMap<Position, ArrayList<ArrayList<Position>>> queenPseudoLegalPositions = new HashMap<>();
    private static final HashMap<Position, ArrayList<Position>> blackPawnPseudoLegalCapturePositions = new HashMap<>();
    private static final HashMap<Position, ArrayList<Position>> whitePawnPseudoLegalCapturePositions = new HashMap<>();

    static {
        initializeKnightPseudoLegalPositions();
        initializeBishopPseudoLegalPositions();
        initializeRookPseudoLegalPositions();
        initializeQueenPseudoLegalPositions();
        initializeBlackPawnPseudoLegalCapturePositions();
        initializeWhitePawnCapturePseudoLegalPositions();
    }

    private static void initializeBlackPawnPseudoLegalCapturePositions() {
        //row 1
        ArrayList<Position> destinations = new ArrayList<>();
        destinations.add(new Position(2, 1));
        blackPawnPseudoLegalCapturePositions.put(new Position(1,0), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(2, 0));
        destinations.add(new Position(2, 2));
        blackPawnPseudoLegalCapturePositions.put(new Position(1,1), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(2, 1));
        destinations.add(new Position(2, 3));
        blackPawnPseudoLegalCapturePositions.put(new Position(1,2), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(2, 2));
        destinations.add(new Position(2, 4));
        blackPawnPseudoLegalCapturePositions.put(new Position(1,3), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(2, 3));
        destinations.add(new Position(2, 5));
        blackPawnPseudoLegalCapturePositions.put(new Position(1,4), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(2, 4));
        destinations.add(new Position(2, 6));
        blackPawnPseudoLegalCapturePositions.put(new Position(1,5), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(2, 5));
        destinations.add(new Position(2, 7));
        blackPawnPseudoLegalCapturePositions.put(new Position(1,6), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(2, 6));
        blackPawnPseudoLegalCapturePositions.put(new Position(1,7), destinations);
        //row 2
        destinations = new ArrayList<>();
        destinations.add(new Position(3, 1));
        blackPawnPseudoLegalCapturePositions.put(new Position(2,0), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(3, 0));
        destinations.add(new Position(3, 2));
        blackPawnPseudoLegalCapturePositions.put(new Position(2,1), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(3, 1));
        destinations.add(new Position(3, 3));
        blackPawnPseudoLegalCapturePositions.put(new Position(2,2), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(3, 2));
        destinations.add(new Position(3, 4));
        blackPawnPseudoLegalCapturePositions.put(new Position(2,3), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(3, 3));
        destinations.add(new Position(3, 5));
        blackPawnPseudoLegalCapturePositions.put(new Position(2,4), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(3, 4));
        destinations.add(new Position(3, 6));
        blackPawnPseudoLegalCapturePositions.put(new Position(2,5), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(3, 5));
        destinations.add(new Position(3, 7));
        blackPawnPseudoLegalCapturePositions.put(new Position(2,6), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(3, 6));
        blackPawnPseudoLegalCapturePositions.put(new Position(2,7), destinations);
        //row 3
        destinations = new ArrayList<>();
        destinations.add(new Position(4, 1));
        blackPawnPseudoLegalCapturePositions.put(new Position(3,0), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(4, 0));
        destinations.add(new Position(4, 2));
        blackPawnPseudoLegalCapturePositions.put(new Position(3,1), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(4, 1));
        destinations.add(new Position(4, 3));
        blackPawnPseudoLegalCapturePositions.put(new Position(3,2), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(4, 2));
        destinations.add(new Position(4, 4));
        blackPawnPseudoLegalCapturePositions.put(new Position(3,3), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(4, 3));
        destinations.add(new Position(4, 5));
        blackPawnPseudoLegalCapturePositions.put(new Position(3,4), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(4, 4));
        destinations.add(new Position(4, 6));
        blackPawnPseudoLegalCapturePositions.put(new Position(3,5), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(4, 5));
        destinations.add(new Position(4, 7));
        blackPawnPseudoLegalCapturePositions.put(new Position(3,6), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(4, 6));
        blackPawnPseudoLegalCapturePositions.put(new Position(3,7), destinations);
        //row 4
        destinations = new ArrayList<>();
        destinations.add(new Position(5, 1));
        blackPawnPseudoLegalCapturePositions.put(new Position(4,0), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(5, 0));
        destinations.add(new Position(5, 2));
        blackPawnPseudoLegalCapturePositions.put(new Position(4,1), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(5, 1));
        destinations.add(new Position(5, 3));
        blackPawnPseudoLegalCapturePositions.put(new Position(4,2), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(5, 2));
        destinations.add(new Position(5, 4));
        blackPawnPseudoLegalCapturePositions.put(new Position(4,3), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(5, 3));
        destinations.add(new Position(5, 5));
        blackPawnPseudoLegalCapturePositions.put(new Position(4,4), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(5, 4));
        destinations.add(new Position(5, 6));
        blackPawnPseudoLegalCapturePositions.put(new Position(4,5), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(5, 5));
        destinations.add(new Position(5, 7));
        blackPawnPseudoLegalCapturePositions.put(new Position(4,6), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(5, 6));
        blackPawnPseudoLegalCapturePositions.put(new Position(4,7), destinations);
        //row 5
        destinations = new ArrayList<>();
        destinations.add(new Position(6, 1));
        blackPawnPseudoLegalCapturePositions.put(new Position(5,0), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(6, 0));
        destinations.add(new Position(6, 2));
        blackPawnPseudoLegalCapturePositions.put(new Position(5,1), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(6, 1));
        destinations.add(new Position(6, 3));
        blackPawnPseudoLegalCapturePositions.put(new Position(5,2), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(6, 2));
        destinations.add(new Position(6, 4));
        blackPawnPseudoLegalCapturePositions.put(new Position(5,3), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(6, 3));
        destinations.add(new Position(6, 5));
        blackPawnPseudoLegalCapturePositions.put(new Position(5,4), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(6, 4));
        destinations.add(new Position(6, 6));
        blackPawnPseudoLegalCapturePositions.put(new Position(5,5), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(6, 5));
        destinations.add(new Position(6, 7));
        blackPawnPseudoLegalCapturePositions.put(new Position(5,6), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(6, 6));
        blackPawnPseudoLegalCapturePositions.put(new Position(5,7), destinations);
        //row 6
        destinations = new ArrayList<>();
        destinations.add(new Position(7, 1));
        blackPawnPseudoLegalCapturePositions.put(new Position(6,0), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(7, 0));
        destinations.add(new Position(7, 2));
        blackPawnPseudoLegalCapturePositions.put(new Position(6,1), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(7, 1));
        destinations.add(new Position(7, 3));
        blackPawnPseudoLegalCapturePositions.put(new Position(6,2), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(7, 2));
        destinations.add(new Position(7, 4));
        blackPawnPseudoLegalCapturePositions.put(new Position(6,3), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(7, 3));
        destinations.add(new Position(7, 5));
        blackPawnPseudoLegalCapturePositions.put(new Position(6,4), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(7, 4));
        destinations.add(new Position(7, 6));
        blackPawnPseudoLegalCapturePositions.put(new Position(6,5), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(7, 5));
        destinations.add(new Position(7, 7));
        blackPawnPseudoLegalCapturePositions.put(new Position(6,6), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(7, 6));
        blackPawnPseudoLegalCapturePositions.put(new Position(6,7), destinations);
    }

    private static void initializeWhitePawnCapturePseudoLegalPositions() {
        //row 6
        ArrayList<Position> destinations = new ArrayList<>();
        destinations.add(new Position(5, 1));
        whitePawnPseudoLegalCapturePositions.put(new Position(6,0), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(5, 0));
        destinations.add(new Position(5, 2));
        whitePawnPseudoLegalCapturePositions.put(new Position(6,1), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(5, 1));
        destinations.add(new Position(5, 3));
        whitePawnPseudoLegalCapturePositions.put(new Position(6,2), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(5, 2));
        destinations.add(new Position(5, 4));
        whitePawnPseudoLegalCapturePositions.put(new Position(6,3), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(5, 3));
        destinations.add(new Position(5, 5));
        whitePawnPseudoLegalCapturePositions.put(new Position(6,4), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(5, 4));
        destinations.add(new Position(5, 6));
        whitePawnPseudoLegalCapturePositions.put(new Position(6,5), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(5, 5));
        destinations.add(new Position(5, 7));
        whitePawnPseudoLegalCapturePositions.put(new Position(6,6), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(5, 6));
        whitePawnPseudoLegalCapturePositions.put(new Position(6,7), destinations);
        //row 5
        destinations = new ArrayList<>();
        destinations.add(new Position(4, 1));
        whitePawnPseudoLegalCapturePositions.put(new Position(5,0), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(4, 0));
        destinations.add(new Position(4, 2));
        whitePawnPseudoLegalCapturePositions.put(new Position(5,1), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(4, 1));
        destinations.add(new Position(4, 3));
        whitePawnPseudoLegalCapturePositions.put(new Position(5,2), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(4, 2));
        destinations.add(new Position(4, 4));
        whitePawnPseudoLegalCapturePositions.put(new Position(5,3), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(4, 3));
        destinations.add(new Position(4, 5));
        whitePawnPseudoLegalCapturePositions.put(new Position(5,4), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(4, 4));
        destinations.add(new Position(4, 6));
        whitePawnPseudoLegalCapturePositions.put(new Position(5,5), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(4, 5));
        destinations.add(new Position(4, 7));
        whitePawnPseudoLegalCapturePositions.put(new Position(5,6), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(4, 6));
        whitePawnPseudoLegalCapturePositions.put(new Position(5,7), destinations);
        //row 4
        destinations = new ArrayList<>();
        destinations.add(new Position(3, 1));
        whitePawnPseudoLegalCapturePositions.put(new Position(4,0), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(3, 0));
        destinations.add(new Position(3, 2));
        whitePawnPseudoLegalCapturePositions.put(new Position(4,1), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(3, 1));
        destinations.add(new Position(3, 3));
        whitePawnPseudoLegalCapturePositions.put(new Position(4,2), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(3, 2));
        destinations.add(new Position(3, 4));
        whitePawnPseudoLegalCapturePositions.put(new Position(4,3), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(3, 3));
        destinations.add(new Position(3, 5));
        whitePawnPseudoLegalCapturePositions.put(new Position(4,4), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(3, 4));
        destinations.add(new Position(3, 6));
        whitePawnPseudoLegalCapturePositions.put(new Position(4,5), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(3, 5));
        destinations.add(new Position(3, 7));
        whitePawnPseudoLegalCapturePositions.put(new Position(4,6), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(3, 6));
        whitePawnPseudoLegalCapturePositions.put(new Position(4,7), destinations);
        //row 3
        destinations = new ArrayList<>();
        destinations.add(new Position(2, 1));
        whitePawnPseudoLegalCapturePositions.put(new Position(3,0), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(2, 0));
        destinations.add(new Position(2, 2));
        whitePawnPseudoLegalCapturePositions.put(new Position(3,1), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(2, 1));
        destinations.add(new Position(2, 3));
        whitePawnPseudoLegalCapturePositions.put(new Position(3,2), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(2, 2));
        destinations.add(new Position(2, 4));
        whitePawnPseudoLegalCapturePositions.put(new Position(3,3), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(2, 3));
        destinations.add(new Position(2, 5));
        whitePawnPseudoLegalCapturePositions.put(new Position(3,4), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(2, 4));
        destinations.add(new Position(2, 6));
        whitePawnPseudoLegalCapturePositions.put(new Position(3,5), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(2, 5));
        destinations.add(new Position(2, 7));
        whitePawnPseudoLegalCapturePositions.put(new Position(3,6), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(2, 6));
        whitePawnPseudoLegalCapturePositions.put(new Position(3,7), destinations);
        //row 2
        destinations = new ArrayList<>();
        destinations.add(new Position(1, 1));
        whitePawnPseudoLegalCapturePositions.put(new Position(2,0), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(1, 0));
        destinations.add(new Position(1, 2));
        whitePawnPseudoLegalCapturePositions.put(new Position(2,1), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(1, 1));
        destinations.add(new Position(1, 3));
        whitePawnPseudoLegalCapturePositions.put(new Position(2,2), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(1, 2));
        destinations.add(new Position(1, 4));
        whitePawnPseudoLegalCapturePositions.put(new Position(2,3), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(1, 3));
        destinations.add(new Position(1, 5));
        whitePawnPseudoLegalCapturePositions.put(new Position(2,4), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(1, 4));
        destinations.add(new Position(1, 6));
        whitePawnPseudoLegalCapturePositions.put(new Position(2,5), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(1, 5));
        destinations.add(new Position(1, 7));
        whitePawnPseudoLegalCapturePositions.put(new Position(2,6), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(1, 6));
        whitePawnPseudoLegalCapturePositions.put(new Position(2,7), destinations);
        //row 1
        destinations = new ArrayList<>();
        destinations.add(new Position(0, 1));
        whitePawnPseudoLegalCapturePositions.put(new Position(1,0), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(0, 0));
        destinations.add(new Position(0, 2));
        whitePawnPseudoLegalCapturePositions.put(new Position(1,1), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(0, 1));
        destinations.add(new Position(0, 3));
        whitePawnPseudoLegalCapturePositions.put(new Position(1,2), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(0, 2));
        destinations.add(new Position(0, 4));
        whitePawnPseudoLegalCapturePositions.put(new Position(1,3), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(0, 3));
        destinations.add(new Position(0, 5));
        whitePawnPseudoLegalCapturePositions.put(new Position(1,4), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(0, 4));
        destinations.add(new Position(0, 6));
        whitePawnPseudoLegalCapturePositions.put(new Position(1,5), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(0, 5));
        destinations.add(new Position(0, 7));
        whitePawnPseudoLegalCapturePositions.put(new Position(1,6), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(0, 6));
        whitePawnPseudoLegalCapturePositions.put(new Position(1,7), destinations);
    }


    private static void initializeKnightPseudoLegalPositions() {
        ArrayList<Position> destinations = new ArrayList<>();
        destinations.add(new Position(1, 2));
        destinations.add(new Position(2, 1));
        knightPseudoLegalPositions.put(new Position(0,0), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(1, 3));
        destinations.add(new Position(2, 0));
        destinations.add(new Position(2, 2));
        knightPseudoLegalPositions.put(new Position(0,1), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(1, 0));
        destinations.add(new Position(2, 1));
        destinations.add(new Position(2, 3));
        destinations.add(new Position(1, 4));
        knightPseudoLegalPositions.put(new Position(0,2), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(1, 1));
        destinations.add(new Position(2, 2));
        destinations.add(new Position(2, 4));
        destinations.add(new Position(1, 5));
        knightPseudoLegalPositions.put(new Position(0,3), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(1, 2));
        destinations.add(new Position(2, 3));
        destinations.add(new Position(2, 5));
        destinations.add(new Position(1, 6));
        knightPseudoLegalPositions.put(new Position(0,4), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(1, 3));
        destinations.add(new Position(2, 4));
        destinations.add(new Position(2, 6));
        destinations.add(new Position(1, 7));
        knightPseudoLegalPositions.put(new Position(0,5), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(1, 4));
        destinations.add(new Position(2, 5));
        destinations.add(new Position(2, 7));
        knightPseudoLegalPositions.put(new Position(0,6), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(1, 5));
        destinations.add(new Position(2, 6));
        knightPseudoLegalPositions.put(new Position(0,7), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(0, 2));
        destinations.add(new Position(2, 2));
        destinations.add(new Position(3, 1));
        knightPseudoLegalPositions.put(new Position(1,0), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(0, 3));
        destinations.add(new Position(2, 3));
        destinations.add(new Position(3, 0));
        destinations.add(new Position(3, 2));
        knightPseudoLegalPositions.put(new Position(1,1), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(0, 0));
        destinations.add(new Position(2, 0));
        destinations.add(new Position(3, 1));
        destinations.add(new Position(3, 3));
        destinations.add(new Position(2, 4));
        destinations.add(new Position(0, 4));
        knightPseudoLegalPositions.put(new Position(1,2), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(0, 1));
        destinations.add(new Position(2, 1));
        destinations.add(new Position(3, 2));
        destinations.add(new Position(3, 4));
        destinations.add(new Position(2, 5));
        destinations.add(new Position(0, 5));
        knightPseudoLegalPositions.put(new Position(1,3), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(0, 2));
        destinations.add(new Position(2, 2));
        destinations.add(new Position(3, 3));
        destinations.add(new Position(3, 5));
        destinations.add(new Position(2, 6));
        destinations.add(new Position(0, 6));
        knightPseudoLegalPositions.put(new Position(1,4), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(0, 3));
        destinations.add(new Position(2, 3));
        destinations.add(new Position(3, 4));
        destinations.add(new Position(3, 6));
        destinations.add(new Position(2, 7));
        destinations.add(new Position(0, 7));
        knightPseudoLegalPositions.put(new Position(1,5), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(0, 4));
        destinations.add(new Position(2, 4));
        destinations.add(new Position(3, 5));
        destinations.add(new Position(3, 7));
        knightPseudoLegalPositions.put(new Position(1,6), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(0, 5));
        destinations.add(new Position(2, 5));
        destinations.add(new Position(3, 6));
        knightPseudoLegalPositions.put(new Position(1,7), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(0, 1));
        destinations.add(new Position(1, 2));
        destinations.add(new Position(3, 2));
        destinations.add(new Position(4, 1));
        knightPseudoLegalPositions.put(new Position(2,0), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(0, 2));
        destinations.add(new Position(1, 3));
        destinations.add(new Position(3, 3));
        destinations.add(new Position(4, 2));
        destinations.add(new Position(4, 0));
        knightPseudoLegalPositions.put(new Position(2,1), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(0, 1));
        destinations.add(new Position(0, 3));
        destinations.add(new Position(1, 0));
        destinations.add(new Position(1, 4));
        destinations.add(new Position(3, 0));
        destinations.add(new Position(3, 4));
        destinations.add(new Position(4, 1));
        destinations.add(new Position(4, 3));
        knightPseudoLegalPositions.put(new Position(2,2), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(0, 2));
        destinations.add(new Position(0, 4));
        destinations.add(new Position(1, 1));
        destinations.add(new Position(1, 5));
        destinations.add(new Position(3, 1));
        destinations.add(new Position(3, 5));
        destinations.add(new Position(4, 2));
        destinations.add(new Position(4, 4));
        knightPseudoLegalPositions.put(new Position(2,3), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(0, 3));
        destinations.add(new Position(0, 5));
        destinations.add(new Position(1, 2));
        destinations.add(new Position(1, 6));
        destinations.add(new Position(3, 2));
        destinations.add(new Position(3, 6));
        destinations.add(new Position(4, 3));
        destinations.add(new Position(4, 5));
        knightPseudoLegalPositions.put(new Position(2,4), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(0, 4));
        destinations.add(new Position(0, 6));
        destinations.add(new Position(1, 3));
        destinations.add(new Position(1, 7));
        destinations.add(new Position(3, 3));
        destinations.add(new Position(3, 7));
        destinations.add(new Position(4, 4));
        destinations.add(new Position(4, 6));
        knightPseudoLegalPositions.put(new Position(2,5), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(0, 5));
        destinations.add(new Position(0, 7));
        destinations.add(new Position(1, 4));
        destinations.add(new Position(3, 4));
        destinations.add(new Position(4, 5));
        destinations.add(new Position(4, 7));
        knightPseudoLegalPositions.put(new Position(2,6), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(0, 6));
        destinations.add(new Position(1, 5));
        destinations.add(new Position(3, 5));
        destinations.add(new Position(4, 6));
        knightPseudoLegalPositions.put(new Position(2,7), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(1, 1));
        destinations.add(new Position(2, 2));
        destinations.add(new Position(4, 2));
        destinations.add(new Position(5, 1));
        knightPseudoLegalPositions.put(new Position(3,0), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(1, 0));
        destinations.add(new Position(1, 2));
        destinations.add(new Position(2, 3));
        destinations.add(new Position(4, 3));
        destinations.add(new Position(5, 0));
        destinations.add(new Position(5, 2));
        knightPseudoLegalPositions.put(new Position(3,1), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(1, 1));
        destinations.add(new Position(1, 3));
        destinations.add(new Position(2, 0));
        destinations.add(new Position(2, 4));
        destinations.add(new Position(4, 0));
        destinations.add(new Position(4, 4));
        destinations.add(new Position(5, 1));
        destinations.add(new Position(5, 3));
        knightPseudoLegalPositions.put(new Position(3,2), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(1, 2));
        destinations.add(new Position(1, 4));
        destinations.add(new Position(2, 1));
        destinations.add(new Position(2, 5));
        destinations.add(new Position(4, 1));
        destinations.add(new Position(4, 5));
        destinations.add(new Position(5, 2));
        destinations.add(new Position(5, 4));
        knightPseudoLegalPositions.put(new Position(3,3), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(1, 3));
        destinations.add(new Position(1, 5));
        destinations.add(new Position(2, 2));
        destinations.add(new Position(2, 6));
        destinations.add(new Position(4, 2));
        destinations.add(new Position(4, 6));
        destinations.add(new Position(5, 3));
        destinations.add(new Position(5, 5));
        knightPseudoLegalPositions.put(new Position(3,4), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(1, 4));
        destinations.add(new Position(1, 6));
        destinations.add(new Position(2, 3));
        destinations.add(new Position(2, 7));
        destinations.add(new Position(4, 3));
        destinations.add(new Position(4, 7));
        destinations.add(new Position(5, 4));
        destinations.add(new Position(5, 6));
        knightPseudoLegalPositions.put(new Position(3,5), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(1, 5));
        destinations.add(new Position(1, 7));
        destinations.add(new Position(2, 4));
        destinations.add(new Position(4, 4));
        destinations.add(new Position(5, 5));
        destinations.add(new Position(5, 7));
        knightPseudoLegalPositions.put(new Position(3,6), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(1, 6));
        destinations.add(new Position(2, 5));
        destinations.add(new Position(4, 5));
        destinations.add(new Position(5, 6));
        knightPseudoLegalPositions.put(new Position(3,7), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(2, 1));
        destinations.add(new Position(3, 2));
        destinations.add(new Position(5, 2));
        destinations.add(new Position(6, 1));
        knightPseudoLegalPositions.put(new Position(4,0), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(2, 0));
        destinations.add(new Position(2, 2));
        destinations.add(new Position(3, 3));
        destinations.add(new Position(5, 3));
        destinations.add(new Position(6, 0));
        destinations.add(new Position(6, 2));
        knightPseudoLegalPositions.put(new Position(4,1), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(2, 1));
        destinations.add(new Position(2, 3));
        destinations.add(new Position(3, 0));
        destinations.add(new Position(3, 4));
        destinations.add(new Position(5, 0));
        destinations.add(new Position(5, 4));
        destinations.add(new Position(6, 1));
        destinations.add(new Position(6, 3));
        knightPseudoLegalPositions.put(new Position(4,2), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(2, 2));
        destinations.add(new Position(2, 4));
        destinations.add(new Position(3, 1));
        destinations.add(new Position(3, 5));
        destinations.add(new Position(5, 1));
        destinations.add(new Position(5, 5));
        destinations.add(new Position(6, 2));
        destinations.add(new Position(6, 4));
        knightPseudoLegalPositions.put(new Position(4,3), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(2, 3));
        destinations.add(new Position(2, 5));
        destinations.add(new Position(3, 2));
        destinations.add(new Position(3, 6));
        destinations.add(new Position(5, 2));
        destinations.add(new Position(5, 6));
        destinations.add(new Position(6, 3));
        destinations.add(new Position(6, 5));
        knightPseudoLegalPositions.put(new Position(4,4), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(2, 4));
        destinations.add(new Position(2, 6));
        destinations.add(new Position(3, 3));
        destinations.add(new Position(3, 7));
        destinations.add(new Position(5, 3));
        destinations.add(new Position(5, 7));
        destinations.add(new Position(6, 4));
        destinations.add(new Position(6, 6));
        knightPseudoLegalPositions.put(new Position(4,5), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(2, 5));
        destinations.add(new Position(2, 7));
        destinations.add(new Position(3, 4));
        destinations.add(new Position(5, 4));
        destinations.add(new Position(6, 5));
        destinations.add(new Position(6, 7));
        knightPseudoLegalPositions.put(new Position(4,6), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(2, 6));
        destinations.add(new Position(3, 5));
        destinations.add(new Position(5, 5));
        destinations.add(new Position(6, 6));
        knightPseudoLegalPositions.put(new Position(4,7), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(3, 1));
        destinations.add(new Position(4, 2));
        destinations.add(new Position(6, 2));
        destinations.add(new Position(7, 1));
        knightPseudoLegalPositions.put(new Position(5,0), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(3, 0));
        destinations.add(new Position(3, 2));
        destinations.add(new Position(4, 3));
        destinations.add(new Position(6, 3));
        destinations.add(new Position(7, 0));
        destinations.add(new Position(7, 2));
        knightPseudoLegalPositions.put(new Position(5,1), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(3, 1));
        destinations.add(new Position(3, 3));
        destinations.add(new Position(4, 0));
        destinations.add(new Position(4, 4));
        destinations.add(new Position(6, 0));
        destinations.add(new Position(6, 4));
        destinations.add(new Position(7, 1));
        destinations.add(new Position(7, 3));
        knightPseudoLegalPositions.put(new Position(5,2), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(3, 2));
        destinations.add(new Position(3, 4));
        destinations.add(new Position(4, 1));
        destinations.add(new Position(4, 5));
        destinations.add(new Position(6, 1));
        destinations.add(new Position(6, 5));
        destinations.add(new Position(7, 2));
        destinations.add(new Position(7, 4));
        knightPseudoLegalPositions.put(new Position(5,3), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(3, 3));
        destinations.add(new Position(3, 5));
        destinations.add(new Position(4, 2));
        destinations.add(new Position(4, 6));
        destinations.add(new Position(6, 2));
        destinations.add(new Position(6, 6));
        destinations.add(new Position(7, 3));
        destinations.add(new Position(7, 5));
        knightPseudoLegalPositions.put(new Position(5,4), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(3, 4));
        destinations.add(new Position(3, 6));
        destinations.add(new Position(4, 3));
        destinations.add(new Position(4, 7));
        destinations.add(new Position(6, 3));
        destinations.add(new Position(6, 7));
        destinations.add(new Position(7, 4));
        destinations.add(new Position(7, 6));
        knightPseudoLegalPositions.put(new Position(5,5), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(3, 5));
        destinations.add(new Position(3, 7));
        destinations.add(new Position(4, 4));
        destinations.add(new Position(6, 4));
        destinations.add(new Position(7, 5));
        destinations.add(new Position(7, 7));
        knightPseudoLegalPositions.put(new Position(5,6), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(3, 6));
        destinations.add(new Position(4, 5));
        destinations.add(new Position(6, 5));
        destinations.add(new Position(7, 6));
        knightPseudoLegalPositions.put(new Position(5,7), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(4, 1));
        destinations.add(new Position(5, 2));
        destinations.add(new Position(7, 2));
        knightPseudoLegalPositions.put(new Position(6,0), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(4, 0));
        destinations.add(new Position(4, 2));
        destinations.add(new Position(5, 3));
        destinations.add(new Position(7, 3));
        knightPseudoLegalPositions.put(new Position(6,1), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(4, 1));
        destinations.add(new Position(4, 3));
        destinations.add(new Position(5, 0));
        destinations.add(new Position(5, 4));
        destinations.add(new Position(7, 0));
        destinations.add(new Position(7, 4));
        knightPseudoLegalPositions.put(new Position(6,2), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(4, 2));
        destinations.add(new Position(4, 4));
        destinations.add(new Position(5, 1));
        destinations.add(new Position(5, 5));
        destinations.add(new Position(7, 1));
        destinations.add(new Position(7, 5));
        knightPseudoLegalPositions.put(new Position(6,3), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(4, 3));
        destinations.add(new Position(4, 5));
        destinations.add(new Position(5, 2));
        destinations.add(new Position(5, 6));
        destinations.add(new Position(7, 2));
        destinations.add(new Position(7, 6));
        knightPseudoLegalPositions.put(new Position(6,4), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(4, 4));
        destinations.add(new Position(4, 6));
        destinations.add(new Position(5, 3));
        destinations.add(new Position(5, 7));
        destinations.add(new Position(7, 3));
        destinations.add(new Position(7, 7));
        knightPseudoLegalPositions.put(new Position(6,5), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(4, 5));
        destinations.add(new Position(4, 7));
        destinations.add(new Position(5, 4));
        destinations.add(new Position(7, 4));
        knightPseudoLegalPositions.put(new Position(6,6), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(4, 6));
        destinations.add(new Position(5, 5));
        destinations.add(new Position(7, 5));
        knightPseudoLegalPositions.put(new Position(6,7), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(5, 1));
        destinations.add(new Position(6, 2));
        knightPseudoLegalPositions.put(new Position(7,0), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(5, 0));
        destinations.add(new Position(5, 2));
        destinations.add(new Position(6, 3));
        knightPseudoLegalPositions.put(new Position(7,1), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(5, 1));
        destinations.add(new Position(5, 3));
        destinations.add(new Position(6, 0));
        destinations.add(new Position(6, 4));
        knightPseudoLegalPositions.put(new Position(7,2), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(5, 2));
        destinations.add(new Position(5, 4));
        destinations.add(new Position(6, 1));
        destinations.add(new Position(6, 5));
        knightPseudoLegalPositions.put(new Position(7,3), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(5, 3));
        destinations.add(new Position(5, 5));
        destinations.add(new Position(6, 2));
        destinations.add(new Position(6, 6));
        knightPseudoLegalPositions.put(new Position(7,4), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(5, 4));
        destinations.add(new Position(5, 6));
        destinations.add(new Position(6, 3));
        destinations.add(new Position(6, 7));
        knightPseudoLegalPositions.put(new Position(7,5), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(5, 5));
        destinations.add(new Position(5, 7));
        destinations.add(new Position(6, 4));
        knightPseudoLegalPositions.put(new Position(7,6), destinations);
        destinations = new ArrayList<>();
        destinations.add(new Position(5, 6));
        destinations.add(new Position(6, 5));
        knightPseudoLegalPositions.put(new Position(7,7), destinations);
    }


    private static void initializeBishopPseudoLegalPositions() {
        ArrayList<ArrayList<Position>> positionsGroupedByDirection = new ArrayList<>();
        ArrayList<Position> positionsInDirection1 = new ArrayList<>();
        for (int i = 1; i < 8; i++) {//11,22,33,44,55,66,77
            positionsInDirection1.add(new Position(i, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        bishopPseudoLegalPositions.put(new Position(0,0), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(1, 0));
        positionsGroupedByDirection.add(positionsInDirection1);
        ArrayList<Position> positionsInDirection2 = new ArrayList<>();
        for (int i = 1; i < 7; i++) {//12,23,34,45,56,67
            positionsInDirection2.add(new Position(i, i+1));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        bishopPseudoLegalPositions.put(new Position(0,1), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(0,2), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(0,3), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(0,4), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(0,5), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection1.add(new Position(1, 7));
        positionsGroupedByDirection.add(positionsInDirection1);
        for (int i = 1; i < 7; i++) {//15,24,33,42,51,60
            positionsInDirection2.add(new Position(i, 6-i));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        bishopPseudoLegalPositions.put(new Position(0,6), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 1; i < 8; i++) {//16,25,34,43,52,61,70
            positionsInDirection1.add(new Position(i, 7-i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        bishopPseudoLegalPositions.put(new Position(0,7), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection1.add(new Position(0, 1));
        positionsGroupedByDirection.add(positionsInDirection1);
        for (int i = 2; i < 8; i++) {//21,32,43,54,65,76
            positionsInDirection2.add(new Position(i, i-1));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        bishopPseudoLegalPositions.put(new Position(1,0), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(1,1), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(1,2), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(1,3), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(1,4), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(1,5), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(1,6), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(0, 6));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 2; i < 8; i++) {//26,35,44,53,62,71
            positionsInDirection2.add(new Position(i, 8-i));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        bishopPseudoLegalPositions.put(new Position(1,7), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(2,0), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(2,1), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(2,2), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(2,3), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(2,4), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(2,5), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(2,6), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(2,7), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(3,0), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(3,1), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(3,2), positionsGroupedByDirection);

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
        for (int i = 2; i >= 0; i--) {//42,51,60
            positionsInDirection4.add(new Position(6-i, i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        bishopPseudoLegalPositions.put(new Position(3,3), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(3,4), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(3,5), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(3,6), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(3,7), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(4,0), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(4,1), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(4,2), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(4,3), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(4,4), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(4,5), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(4,6), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(4,7), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(5,0), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(5,1), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(5,2), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(5,3), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(5,4), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(5,5), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(5,6), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(5,7), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(7, 1));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {//51,42,33,24,15,06
            positionsInDirection2.add(new Position(i, 6-i));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        bishopPseudoLegalPositions.put(new Position(6,0), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(6,1), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(6,2), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(6,3), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(6,4), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(6,5), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(6,6), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();

        positionsInDirection1.add(new Position(7, 6));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {//56,45,34,23,12,01
            positionsInDirection2.add(new Position(i, i+1));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        bishopPseudoLegalPositions.put(new Position(6,7), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {//61,52,43,34,25,16,07
            positionsInDirection1.add(new Position(i, 7-i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        bishopPseudoLegalPositions.put(new Position(7,0), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(6, 0));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 6; i > 0; i--) {//62,53,44,35,26,17
            positionsInDirection2.add(new Position(i, 8-i));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        bishopPseudoLegalPositions.put(new Position(7,1), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(7,2), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(7,3), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(7,4), positionsGroupedByDirection);

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
        bishopPseudoLegalPositions.put(new Position(7,5), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(6, 7));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 6; i > 0; i--) {//65,54,43,32,21,10
            positionsInDirection2.add(new Position(i, i-1));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        bishopPseudoLegalPositions.put(new Position(7,6), positionsGroupedByDirection);

        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {//66,55,44,33,22,11,00
            positionsInDirection1.add(new Position(i, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        bishopPseudoLegalPositions.put(new Position(7,7), positionsGroupedByDirection);
    }


    private static void initializeRookPseudoLegalPositions(){
        ArrayList<ArrayList<Position>> positionsGroupedByDirection = new ArrayList<>();
        ArrayList<Position> positionsInDirection1 = new ArrayList<>();
        for (int i = 1; i < 8; i++) {//10,20,30,40,50,60,70
            positionsInDirection1.add(new Position(i, 0));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        ArrayList<Position> positionsInDirection2 = new ArrayList<>();
        for (int i = 1; i < 8; i++) {//01,02,03,04,05,06,07
            positionsInDirection2.add(new Position(0, i));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        rookPseudoLegalPositions.put(new Position(0,0), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 1; i < 8; i++) {//11,21,31,41,51,61,71
            positionsInDirection1.add(new Position(i, 1));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 2; i < 8; i++) {//02,03,04,05,06,07
            positionsInDirection2.add(new Position(0, i));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        ArrayList<Position> positionsInDirection3 = new ArrayList<>();
        positionsInDirection3.add(new Position(0, 0));
        positionsGroupedByDirection.add(positionsInDirection3);
        rookPseudoLegalPositions.put(new Position(0,1), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 1; i < 8; i++) {//12,22,32,42,52,62,72
            positionsInDirection1.add(new Position(i, 2));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 3; i < 8; i++) {//03,04,05,06,07
            positionsInDirection2.add(new Position(0, i));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        positionsInDirection3.add(new Position(0, 1));
        positionsInDirection3.add(new Position(0, 0));
        positionsGroupedByDirection.add(positionsInDirection3);
        rookPseudoLegalPositions.put(new Position(0,2), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 1; i < 8; i++) {//13,23,33,43,53,63,73
            positionsInDirection1.add(new Position(i, 3));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 4; i < 8; i++) {//04,05,06,07
            positionsInDirection2.add(new Position(0, i));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 2; i >= 0; i--) {//02,01,00
            positionsInDirection3.add(new Position(0, i));
        }positionsGroupedByDirection.add(positionsInDirection3);
        rookPseudoLegalPositions.put(new Position(0,3), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 1; i < 8; i++) {//14,24,34,44,54,64,74
            positionsInDirection1.add(new Position(i, 4));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 5; i < 8; i++) {//05,06,07
            positionsInDirection2.add(new Position(0, i));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 3; i >= 0; i--) {//03,02,01,00
            positionsInDirection3.add(new Position(0, i));
        }positionsGroupedByDirection.add(positionsInDirection3);
        rookPseudoLegalPositions.put(new Position(0,4), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 1; i < 8; i++) {//15,25,35,45,55,65,75
            positionsInDirection1.add(new Position(i, 5));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 6; i < 8; i++) {//06,07
            positionsInDirection2.add(new Position(0, i));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 4; i >= 0; i--) {//04,03,02,01,00
            positionsInDirection3.add(new Position(0, i));
        }positionsGroupedByDirection.add(positionsInDirection3);
        rookPseudoLegalPositions.put(new Position(0,5), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 1; i < 8; i++) {//16,26,36,46,56,66,76
            positionsInDirection1.add(new Position(i, 6));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection2.add(new Position(0, 7));
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {//05,04,03,02,01,00
            positionsInDirection3.add(new Position(0, i));
        }positionsGroupedByDirection.add(positionsInDirection3);
        rookPseudoLegalPositions.put(new Position(0,6), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 1; i < 8; i++) {//17,27,37,47,57,67,77
            positionsInDirection1.add(new Position(i, 7));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {//06,05,04,03,02,01,00
            positionsInDirection2.add(new Position(0, i));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        rookPseudoLegalPositions.put(new Position(0,7), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 1; i < 8; i++) {//11,12,13,14,15,16,17
            positionsInDirection1.add(new Position(1, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 2; i < 8; i++) {//20,30,40,50,60,70
            positionsInDirection2.add(new Position(i,0));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        positionsInDirection3.add(new Position(0, 0));
        positionsGroupedByDirection.add(positionsInDirection3);
        rookPseudoLegalPositions.put(new Position(1,0), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 2; i < 8; i++) {//12,13,14,15,16,17
            positionsInDirection1.add(new Position(1, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 2; i < 8; i++) {//21,31,41,51,61,71
            positionsInDirection2.add(new Position(i, 1));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        positionsInDirection3.add(new Position(0, 1));
        positionsGroupedByDirection.add(positionsInDirection3);
        ArrayList<Position> positionsInDirection4 = new ArrayList<>();
        positionsInDirection4.add(new Position(1, 0));
        positionsGroupedByDirection.add(positionsInDirection4);
        rookPseudoLegalPositions.put(new Position(1,1), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 3; i < 8; i++) {//13,14,15,16,17
            positionsInDirection1.add(new Position(1, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 2; i < 8; i++) {//22,32,42,52,62,72
            positionsInDirection2.add(new Position(i, 2));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        positionsInDirection3.add(new Position(0, 2));
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        positionsInDirection4.add(new Position(1, 1));
        positionsInDirection4.add(new Position(1, 0));
        positionsGroupedByDirection.add(positionsInDirection4);
        rookPseudoLegalPositions.put(new Position(1,2), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 4; i < 8; i++) {//14,15,16,17
            positionsInDirection1.add(new Position(1, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 2; i < 8; i++) {//23,33,43,53,63,73
            positionsInDirection2.add(new Position(i, 3));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        positionsInDirection3.add(new Position(0, 3));
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 2; i >= 0; i--) {//12,11,10
            positionsInDirection4.add(new Position(1, i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        rookPseudoLegalPositions.put(new Position(1,3), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 5; i < 8; i++) {//15,16,17
            positionsInDirection1.add(new Position(1, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 2; i < 8; i++) {//24,34,44,54,64,74
            positionsInDirection2.add(new Position(i, 4));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        positionsInDirection3.add(new Position(0, 4));
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 3; i >= 0; i--) {//13,12,11,10
            positionsInDirection4.add(new Position(1, i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        rookPseudoLegalPositions.put(new Position(1,4), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 6; i < 8; i++) {//16,17
            positionsInDirection1.add(new Position(1, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 2; i < 8; i++) {//25,35,45,55,65,75
            positionsInDirection2.add(new Position(i, 5));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        positionsInDirection3.add(new Position(0, 5));
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 4; i >= 0; i--) {//14,13,12,11,10
            positionsInDirection4.add(new Position(1, i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        rookPseudoLegalPositions.put(new Position(1,5), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(1, 7));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 2; i < 8; i++) {//26,36,46,56,66,76
            positionsInDirection2.add(new Position(i, 6));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        positionsInDirection3.add(new Position(0, 6));
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {//15,14,13,12,11,10
            positionsInDirection4.add(new Position(1, i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        rookPseudoLegalPositions.put(new Position(1,6), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(0, 7));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 2; i < 8; i++) {//27,37,47,57,67,77
            positionsInDirection2.add(new Position(i, 7));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {//16,15,14,13,12,11,10
            positionsInDirection3.add(new Position(1, i));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        rookPseudoLegalPositions.put(new Position(1,7), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 1; i < 8; i++) {//21,22,23,24,25,26,27
            positionsInDirection1.add(new Position(2, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 3; i < 8; i++) {//30,40,50,60,70
            positionsInDirection2.add(new Position(i, 0));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        positionsInDirection3.add(new Position(1, 0));
        positionsInDirection3.add(new Position(0, 0));
        positionsGroupedByDirection.add(positionsInDirection3);
        rookPseudoLegalPositions.put(new Position(2,0), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 2; i < 8; i++) {//22,23,24,25,26,27
            positionsInDirection1.add(new Position(2, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 3; i < 8; i++) {//31,41,51,61,71
            positionsInDirection2.add(new Position(i,1));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        positionsInDirection3.add(new Position(1, 1));
        positionsInDirection3.add(new Position(0, 1));
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        positionsInDirection4.add(new Position(2, 0));
        positionsGroupedByDirection.add(positionsInDirection4);
        rookPseudoLegalPositions.put(new Position(2,1), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 3; i < 8; i++) {//23,24,25,26,27
            positionsInDirection1.add(new Position(2, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 3; i < 8; i++) {//32,42,52,62,72
            positionsInDirection2.add(new Position(i, 2));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        positionsInDirection3.add(new Position(1, 2));
        positionsInDirection3.add(new Position(0, 2));
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        positionsInDirection4.add(new Position(2, 1));
        positionsInDirection4.add(new Position(2, 0));
        positionsGroupedByDirection.add(positionsInDirection4);
        rookPseudoLegalPositions.put(new Position(2,2), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 4; i < 8; i++) {//24,25,26,27
            positionsInDirection1.add(new Position(2, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 3; i < 8; i++) {//33,43,53,63,73
            positionsInDirection2.add(new Position(i, 3));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        positionsInDirection3.add(new Position(1, 3));
        positionsInDirection3.add(new Position(0, 3));
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        positionsInDirection4.add(new Position(2, 2));
        positionsInDirection4.add(new Position(2, 1));
        positionsInDirection4.add(new Position(2, 0));
        positionsGroupedByDirection.add(positionsInDirection4);
        rookPseudoLegalPositions.put(new Position(2,3), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 5; i < 8; i++) {//25,26,27
            positionsInDirection1.add(new Position(2, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 3; i < 8; i++) {//34,44,54,64,74
            positionsInDirection2.add(new Position(i, 4));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        positionsInDirection3.add(new Position(1, 4));
        positionsInDirection3.add(new Position(0, 4));
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 3; i >= 0; i--) {
            positionsInDirection4.add(new Position(2, i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        rookPseudoLegalPositions.put(new Position(2,4), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 6; i < 8; i++) {//26,27
            positionsInDirection1.add(new Position(2, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 3; i < 8; i++) {//35,45,55,65,75
            positionsInDirection2.add(new Position(i, 5));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        positionsInDirection3.add(new Position(1, 5));
        positionsInDirection3.add(new Position(0, 5));
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 4; i >= 0; i--) {
            positionsInDirection4.add(new Position(2, i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        rookPseudoLegalPositions.put(new Position(2,5), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(2, 7));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 3; i < 8; i++) {//36,46,56,66,76
            positionsInDirection2.add(new Position(i, 6));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        positionsInDirection3.add(new Position(1, 6));
        positionsInDirection3.add(new Position(0, 6));
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {//25,24,23,22,21,20
            positionsInDirection4.add(new Position(2, i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        rookPseudoLegalPositions.put(new Position(2,6), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(1, 7));
        positionsInDirection1.add(new Position(0, 7));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 3; i < 8; i++) {//37,47,57,67,77
            positionsInDirection2.add(new Position(i, 7));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {//26,25,24,23,22,21,20
            positionsInDirection3.add(new Position(2, i));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        rookPseudoLegalPositions.put(new Position(2,7), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 1; i < 8; i++) {//31,32,33,34,35,36,37
            positionsInDirection1.add(new Position(3, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 4; i < 8; i++) {//40,50,60,70
            positionsInDirection2.add(new Position(i, 0));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        positionsInDirection3.add(new Position(2, 0));
        positionsInDirection3.add(new Position(1, 0));
        positionsInDirection3.add(new Position(0, 0));
        positionsGroupedByDirection.add(positionsInDirection3);
        rookPseudoLegalPositions.put(new Position(3,0), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 2; i < 8; i++) {//32,33,34,35,36,37
            positionsInDirection1.add(new Position(3, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 4; i < 8; i++) {//41,51,61,71
            positionsInDirection2.add(new Position(i,1));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        positionsInDirection3.add(new Position(2, 1));
        positionsInDirection3.add(new Position(1, 1));
        positionsInDirection3.add(new Position(0, 1));
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        positionsInDirection4.add(new Position(3, 0));
        positionsGroupedByDirection.add(positionsInDirection4);
        rookPseudoLegalPositions.put(new Position(3,1), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 3; i < 8; i++) {//33,34,35,36,37
            positionsInDirection1.add(new Position(3, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 4; i < 8; i++) {//42,52,62,72
            positionsInDirection2.add(new Position(i, 2));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        positionsInDirection3.add(new Position(2, 2));
        positionsInDirection3.add(new Position(1, 2));
        positionsInDirection3.add(new Position(0, 2));
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        positionsInDirection4.add(new Position(3, 1));
        positionsInDirection4.add(new Position(3, 0));
        positionsGroupedByDirection.add(positionsInDirection4);
        rookPseudoLegalPositions.put(new Position(3,2), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 4; i < 8; i++) {//34,35,36,37
            positionsInDirection1.add(new Position(3, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 4; i < 8; i++) {//43,53,63,73
            positionsInDirection2.add(new Position(i, 3));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        positionsInDirection3.add(new Position(2, 3));
        positionsInDirection3.add(new Position(1, 3));
        positionsInDirection3.add(new Position(0, 3));
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        positionsInDirection4.add(new Position(3, 2));
        positionsInDirection4.add(new Position(3, 1));
        positionsInDirection4.add(new Position(3, 0));
        positionsGroupedByDirection.add(positionsInDirection4);
        rookPseudoLegalPositions.put(new Position(3,3), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 5; i < 8; i++) {//35,36,37
            positionsInDirection1.add(new Position(3, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 4; i < 8; i++) {//44,54,64,74
            positionsInDirection2.add(new Position(i, 4));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        positionsInDirection3.add(new Position(2, 4));
        positionsInDirection3.add(new Position(1, 4));
        positionsInDirection3.add(new Position(0, 4));
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 3; i >= 0; i--) {//33,32,31,30
            positionsInDirection4.add(new Position(3, i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        rookPseudoLegalPositions.put(new Position(3,4), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 6; i < 8; i++) {//36,37
            positionsInDirection1.add(new Position(3, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 4; i < 8; i++) {//45,55,65,75
            positionsInDirection2.add(new Position(i, 5));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        positionsInDirection3.add(new Position(2, 5));
        positionsInDirection3.add(new Position(1, 5));
        positionsInDirection3.add(new Position(0, 5));
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 4; i >= 0; i--) {//34,33,32,31,30
            positionsInDirection4.add(new Position(3, i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        rookPseudoLegalPositions.put(new Position(3,5), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(3, 7));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 4; i < 8; i++) {//46,56,66,76
            positionsInDirection2.add(new Position(i, 6));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        positionsInDirection3.add(new Position(2, 6));
        positionsInDirection3.add(new Position(1, 6));
        positionsInDirection3.add(new Position(0, 6));
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {//35,34,33,32,31,30
            positionsInDirection4.add(new Position(3, i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        rookPseudoLegalPositions.put(new Position(3,6), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 4; i < 8; i++) {//47,57,67,77
            positionsInDirection1.add(new Position(i, 7));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection2.add(new Position(2, 7));
        positionsInDirection2.add(new Position(1, 7));
        positionsInDirection2.add(new Position(0, 7));
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {//36,35,34,33,32,31,30
            positionsInDirection3.add(new Position(3, i));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        rookPseudoLegalPositions.put(new Position(3,7), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 1; i < 8; i++) {//41,42,43,44,45,46,47
            positionsInDirection1.add(new Position(4, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 5; i < 8; i++) {//50,60,70
            positionsInDirection2.add(new Position(i,0));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 3; i >= 0; i--) {//30,20,10,00
            positionsInDirection3.add(new Position(i,0));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        rookPseudoLegalPositions.put(new Position(4,0), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 2; i < 8; i++) {//42,43,44,45,46,47
            positionsInDirection1.add(new Position(4, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 5; i < 8; i++) {//51,61,71
            positionsInDirection2.add(new Position(i, 1));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 3; i >= 0; i--) {//31,21,11,01
            positionsInDirection3.add(new Position(i,1));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        positionsInDirection4.add(new Position(4, 0));
        positionsGroupedByDirection.add(positionsInDirection4);
        rookPseudoLegalPositions.put(new Position(4,1), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 3; i < 8; i++) {//43,44,45,46,47
            positionsInDirection1.add(new Position(4, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 5; i < 8; i++) {//52,62,72
            positionsInDirection2.add(new Position(i, 2));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 3; i >= 0; i--) {//32,22,12,02
            positionsInDirection3.add(new Position(i,2));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        positionsInDirection4.add(new Position(4, 1));
        positionsInDirection4.add(new Position(4, 0));
        positionsGroupedByDirection.add(positionsInDirection4);
        rookPseudoLegalPositions.put(new Position(4,2), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 4; i < 8; i++) {//44,45,46,47
            positionsInDirection1.add(new Position(4, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 5; i < 8; i++) {//53,63,73
            positionsInDirection2.add(new Position(i, 3));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 3; i >= 0; i--) {//33,23,13,03
            positionsInDirection3.add(new Position(i,3));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        positionsInDirection4.add(new Position(4, 2));
        positionsInDirection4.add(new Position(4, 1));
        positionsInDirection4.add(new Position(4, 0));
        positionsGroupedByDirection.add(positionsInDirection4);
        rookPseudoLegalPositions.put(new Position(4,3), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 5; i < 8; i++) {//45,46,47
            positionsInDirection1.add(new Position(4, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 5; i < 8; i++) {//54,64,74
            positionsInDirection2.add(new Position(i, 4));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 3; i >= 0; i--) {//34,24,14,04
            positionsInDirection3.add(new Position(i,4));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 3; i >= 0 ; i--) {//43,42,41,40
            positionsInDirection4.add(new Position(4, i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        rookPseudoLegalPositions.put(new Position(4,4), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 6; i < 8; i++) {//46,47
            positionsInDirection1.add(new Position(4, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 5; i < 8; i++) {//55,65,75
            positionsInDirection2.add(new Position(i, 5));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 3; i >= 0; i--) {//35,25,15,05
            positionsInDirection3.add(new Position(i,5));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 4; i >= 0 ; i--) {//44,43,42,41,40
            positionsInDirection4.add(new Position(4, i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        rookPseudoLegalPositions.put(new Position(4,5), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(4, 7));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 5; i < 8; i++) {//56,66,76
            positionsInDirection2.add(new Position(i, 6));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 3; i >= 0; i--) {//36,26,16,06
            positionsInDirection3.add(new Position(i,6));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 5; i >= 0 ; i--) {//45,44,43,42,41,40
            positionsInDirection4.add(new Position(4, i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        rookPseudoLegalPositions.put(new Position(4,6), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 5; i < 8; i++) {//57,67,77
            positionsInDirection1.add(new Position(i, 7));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 3; i >= 0; i--) {//37,27,17,07
            positionsInDirection2.add(new Position(i,7));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 6; i >= 0 ; i--) {//46,45,44,43,42,41,40
            positionsInDirection3.add(new Position(4, i));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        rookPseudoLegalPositions.put(new Position(4,7), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 1; i < 8; i++) {//51,52,53,54,55,56,57
            positionsInDirection1.add(new Position(5, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 6; i < 8; i++) {//60,70
            positionsInDirection2.add(new Position(i,0));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 4; i >= 0; i--) {//40,30,20,10,00
            positionsInDirection3.add(new Position(i,0));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        rookPseudoLegalPositions.put(new Position(5,0), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 2; i < 8; i++) {//52,53,54,55,56,57
            positionsInDirection1.add(new Position(5, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 6; i < 8; i++) {//61,71
            positionsInDirection2.add(new Position(i,1));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 4; i >= 0; i--) {//41,31,21,11,01
            positionsInDirection3.add(new Position(i,1));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        positionsInDirection4.add(new Position(5,0));
        positionsGroupedByDirection.add(positionsInDirection4);
        rookPseudoLegalPositions.put(new Position(5,1), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 3; i < 8; i++) {//53,54,55,56,57
            positionsInDirection1.add(new Position(5, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 6; i < 8; i++) {//62,72
            positionsInDirection2.add(new Position(i,2));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 4; i >= 0; i--) {//42,32,22,12,02
            positionsInDirection3.add(new Position(i,2));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        positionsInDirection4.add(new Position(5,1));
        positionsInDirection4.add(new Position(5,0));
        positionsGroupedByDirection.add(positionsInDirection4);
        rookPseudoLegalPositions.put(new Position(5,2), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 4; i < 8; i++) {//54,55,56,57
            positionsInDirection1.add(new Position(5, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 6; i < 8; i++) {//63,73
            positionsInDirection2.add(new Position(i,3));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 4; i >= 0; i--) {//43,33,23,13,03
            positionsInDirection3.add(new Position(i,3));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        positionsInDirection4.add(new Position(5,2));
        positionsInDirection4.add(new Position(5,1));
        positionsInDirection4.add(new Position(5,0));
        positionsGroupedByDirection.add(positionsInDirection4);
        rookPseudoLegalPositions.put(new Position(5,3), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 5; i < 8; i++) {//55,56,57
            positionsInDirection1.add(new Position(5, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 6; i < 8; i++) {//64,74
            positionsInDirection2.add(new Position(i,4));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 4; i >= 0; i--) {//44,34,24,14,04
            positionsInDirection3.add(new Position(i,4));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 3; i >= 0; i--) {//53,52,51,50
            positionsInDirection4.add(new Position(5, i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        rookPseudoLegalPositions.put(new Position(5,4), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 6; i < 8; i++) {//56,57
            positionsInDirection1.add(new Position(5, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 6; i < 8; i++) {//65,75
            positionsInDirection2.add(new Position(i,5));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 4; i >= 0; i--) {//45,35,25,15,05
            positionsInDirection3.add(new Position(i,5));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 4; i >= 0; i--) {//54,53,52,51,50
            positionsInDirection4.add(new Position(5, i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        rookPseudoLegalPositions.put(new Position(5,5), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(5, 7));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 6; i < 8; i++) {//66,76
            positionsInDirection2.add(new Position(i,6));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 4; i >= 0; i--) {//46,36,26,16,06
            positionsInDirection3.add(new Position(i,6));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {//55,54,53,52,51,50
            positionsInDirection4.add(new Position(5, i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        rookPseudoLegalPositions.put(new Position(5,6), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 6; i < 8; i++) {//67,77
            positionsInDirection1.add(new Position(i,7));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 4; i >= 0; i--) {//47,37,27,17,07
            positionsInDirection2.add(new Position(i,7));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {//56,55,54,53,52,51,50
            positionsInDirection3.add(new Position(5, i));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        rookPseudoLegalPositions.put(new Position(5,7), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 1; i < 8; i++) {//61,62,63,64,65,66,67
            positionsInDirection1.add(new Position(6, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection2.add(new Position(7,0));
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {//50,40,30,20,10,00
            positionsInDirection3.add(new Position(i,0));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        rookPseudoLegalPositions.put(new Position(6,0), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 2; i < 8; i++) {//62,63,64,65,66,67
            positionsInDirection1.add(new Position(6, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection2.add(new Position(7,1));
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {//51,41,31,21,11,01
            positionsInDirection3.add(new Position(i,1));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        positionsInDirection4.add(new Position(6,0));
        positionsGroupedByDirection.add(positionsInDirection4);
        rookPseudoLegalPositions.put(new Position(6,1), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 3; i < 8; i++) {//63,64,65,66,67
            positionsInDirection1.add(new Position(6, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection2.add(new Position(7,2));
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {//52,42,32,22,12,02
            positionsInDirection3.add(new Position(i,2));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        positionsInDirection4.add(new Position(6,1));
        positionsInDirection4.add(new Position(6,0));
        positionsGroupedByDirection.add(positionsInDirection4);
        rookPseudoLegalPositions.put(new Position(6,2), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 4; i < 8; i++) {//64,65,66,67
            positionsInDirection1.add(new Position(6, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection2.add(new Position(7,3));
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {//53,43,33,23,13,03
            positionsInDirection3.add(new Position(i,3));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        positionsInDirection4.add(new Position(6,2));
        positionsInDirection4.add(new Position(6,1));
        positionsInDirection4.add(new Position(6,0));
        positionsGroupedByDirection.add(positionsInDirection4);
        rookPseudoLegalPositions.put(new Position(6,3), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 5; i < 8; i++) {//65,66,67
            positionsInDirection1.add(new Position(6, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection2.add(new Position(7,4));
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {//54,44,34,24,14,04
            positionsInDirection3.add(new Position(i,4));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 3; i >= 0; i--) {//63,62,61,60
            positionsInDirection4.add(new Position(6, i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        rookPseudoLegalPositions.put(new Position(6,4), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 6; i < 8; i++) {//66,67
            positionsInDirection1.add(new Position(6, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection2.add(new Position(7,5));
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {//55,45,35,25,15,05
            positionsInDirection3.add(new Position(i,5));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 4; i >= 0; i--) {//64,63,62,61,60
            positionsInDirection4.add(new Position(6, i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        rookPseudoLegalPositions.put(new Position(6,5), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(6, 7));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        positionsInDirection2.add(new Position(7,6));
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {//56,46,36,26,16,06
            positionsInDirection3.add(new Position(i,6));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        positionsInDirection4 = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {//65,64,63,62,61,60
            positionsInDirection4.add(new Position(6, i));
        }
        positionsGroupedByDirection.add(positionsInDirection4);
        rookPseudoLegalPositions.put(new Position(6,6), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(7,7));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {//57,47,37,27,17,07
            positionsInDirection2.add(new Position(i,7));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {//66,65,64,63,62,61,60
            positionsInDirection3.add(new Position(6, i));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        rookPseudoLegalPositions.put(new Position(6,7), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 1; i < 8; i++) {//71,72,73,74,75,76,77
            positionsInDirection1.add(new Position(7, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {//60,50,40,30,20,10,00
            positionsInDirection2.add(new Position(i,0));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        rookPseudoLegalPositions.put(new Position(7,0), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 2; i < 8; i++) {//72,73,74,75,76,77
            positionsInDirection1.add(new Position(7, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {//61,51,41,31,21,11,01
            positionsInDirection2.add(new Position(i,1));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        positionsInDirection3.add(new Position(7,0));
        positionsGroupedByDirection.add(positionsInDirection3);
        rookPseudoLegalPositions.put(new Position(7,1), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 3; i < 8; i++) {//73,74,75,76,77
            positionsInDirection1.add(new Position(7, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {//62,52,42,32,22,12,02
            positionsInDirection2.add(new Position(i,2));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        positionsInDirection3.add(new Position(7,1));
        positionsInDirection3.add(new Position(7,0));
        positionsGroupedByDirection.add(positionsInDirection3);
        rookPseudoLegalPositions.put(new Position(7,2), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 4; i < 8; i++) {//74,75,76,77
            positionsInDirection1.add(new Position(7, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {//63,53,43,33,23,13,03
            positionsInDirection2.add(new Position(i,3));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        positionsInDirection3.add(new Position(7,2));
        positionsInDirection3.add(new Position(7,1));
        positionsInDirection3.add(new Position(7,0));
        positionsGroupedByDirection.add(positionsInDirection3);
        rookPseudoLegalPositions.put(new Position(7,3), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 5; i < 8; i++) {//75,76,77
            positionsInDirection1.add(new Position(7, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {//64,54,44,34,24,14,04
            positionsInDirection2.add(new Position(i,4));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 3; i >= 0; i--) {//73,72,71,70
            positionsInDirection3.add(new Position(7, i));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        rookPseudoLegalPositions.put(new Position(7,4), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 6; i < 8; i++) {//76,77
            positionsInDirection1.add(new Position(7, i));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {//65,55,45,35,25,15,05
            positionsInDirection2.add(new Position(i,5));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 4; i >= 0; i--) {//74,73,72,71,70
            positionsInDirection3.add(new Position(7, i));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        rookPseudoLegalPositions.put(new Position(7,5), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        positionsInDirection1.add(new Position(7, 7));
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {//66,56,46,36,26,16,06
            positionsInDirection2.add(new Position(i,6));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        positionsInDirection3 = new ArrayList<>();
        for (int i = 5; i >= 0; i--) {//75,74,73,72,71,70
            positionsInDirection3.add(new Position(7, i));
        }
        positionsGroupedByDirection.add(positionsInDirection3);
        rookPseudoLegalPositions.put(new Position(7,6), positionsGroupedByDirection);
//validated
        positionsGroupedByDirection = new ArrayList<>();
        positionsInDirection1 = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {//67,57,47,37,27,17,07
            positionsInDirection1.add(new Position(i,7));
        }
        positionsGroupedByDirection.add(positionsInDirection1);
        positionsInDirection2 = new ArrayList<>();
        for (int i = 6; i >= 0; i--) {//76,75,74,73,72,71,70
            positionsInDirection2.add(new Position(7, i));
        }
        positionsGroupedByDirection.add(positionsInDirection2);
        rookPseudoLegalPositions.put(new Position(7,7), positionsGroupedByDirection);
        //validated
    }

    private static void initializeQueenPseudoLegalPositions(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Position currentPosition = new Position(i, j);
                ArrayList<ArrayList<Position>> setOfDirectionsPerPosition =
                        new ArrayList<>(bishopPseudoLegalPositions.get(currentPosition));
                setOfDirectionsPerPosition.addAll(rookPseudoLegalPositions.get(currentPosition));
                queenPseudoLegalPositions.put(currentPosition, setOfDirectionsPerPosition);
            }
        }
    }

    public static List<Position> getKnightPseudoLegalPositions(Position from){
        return knightPseudoLegalPositions.get(from);
    }

    public static ArrayList<ArrayList<Position>> getBishopPseudoLegalPoitions(Position from){
        return bishopPseudoLegalPositions.get(from);
    }

    public static ArrayList<ArrayList<Position>> getRookPseudoLegalPositions(Position from){
        return rookPseudoLegalPositions.get(from);
    }

    public static ArrayList<ArrayList<Position>> getQueenPseudoLegalPositions(Position from){
        return queenPseudoLegalPositions.get(from);
    }

    public static Collection<Position> getPawnCapturePseudoLegalPositions (@NonNull PlayerColor playerColor,
                                                                           @NonNull Position pawnPosition){
        if (playerColor.equals(PlayerColor.BLACK)){
            return blackPawnPseudoLegalCapturePositions.get(pawnPosition);
        } else {
            return whitePawnPseudoLegalCapturePositions.get(pawnPosition);
        }
    }
}