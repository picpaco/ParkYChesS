package com.marcopiccionitraining.parkychess;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.EnPassantMove;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import com.marcopiccionitraining.parkychess.model.moves.PawnPromotionMove;
import com.marcopiccionitraining.parkychess.model.moves.StandardMove;
import com.marcopiccionitraining.parkychess.model.pieces.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;

import static com.marcopiccionitraining.parkychess.model.FENPositions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PawnTests {
    private Board chessboard;

    @BeforeEach
    void setup(){
        chessboard = new Board();
    }

    @Test
    void whitePawnsInitialPositionHaveNotMovedYet(){
        ChessGame gameState = new ChessGame(chessboard, FEN_INITIAL_POSITION);
        Pawn whitePawn1 = (Pawn) chessboard.getPiece (6,0);
        assertFalse(whitePawn1.hasMoved(), "Pawn should not have already moved at starting position.");
        Pawn whitePawn2 = (Pawn) chessboard.getPiece (6,1);
        assertFalse(whitePawn2.hasMoved(), "Pawn should not have already moved at starting position.");
        Pawn whitePawn3 = (Pawn) chessboard.getPiece (6,2);
        assertFalse(whitePawn3.hasMoved(), "Pawn should not have already moved at starting position.");
        Pawn whitePawn4 = (Pawn) chessboard.getPiece (6,3);
        assertFalse(whitePawn4.hasMoved(), "Pawn should not have already moved at starting position.");
        Pawn whitePawn5 = (Pawn) chessboard.getPiece (6,4);
        assertFalse(whitePawn5.hasMoved(), "Pawn should not have already moved at starting position.");
        Pawn whitePawn6 = (Pawn) chessboard.getPiece (6,5);
        assertFalse(whitePawn6.hasMoved(), "Pawn should not have already moved at starting position.");
        Pawn whitePawn7 = (Pawn) chessboard.getPiece (6,6);
        assertFalse(whitePawn7.hasMoved(), "Pawn should not have already moved at starting position.");
        Pawn whitePawn8 = (Pawn) chessboard.getPiece (6,7);
        assertFalse(whitePawn8.hasMoved(), "Pawn should not have already moved at starting position.");
    }

    @Test
    void blackPawnsInitialPositionHaveNotMovedYet(){
        ChessGame gameState = new ChessGame(chessboard, FEN_INITIAL_POSITION);
        Pawn blackPawn1 = (Pawn) chessboard.getPiece (1,0);
        assertFalse(blackPawn1.hasMoved(), "Pawn should not have already moved at starting position.");
        Pawn blackPawn2 = (Pawn) chessboard.getPiece (1,1);
        assertFalse(blackPawn2.hasMoved(), "Pawn should not have already moved at starting position.");
        Pawn blackPawn3 = (Pawn) chessboard.getPiece (1,2);
        assertFalse(blackPawn3.hasMoved(), "Pawn should not have already moved at starting position.");
        Pawn blackPawn4 = (Pawn) chessboard.getPiece (1,3);
        assertFalse(blackPawn4.hasMoved(), "Pawn should not have already moved at starting position.");
        Pawn blackPawn5 = (Pawn) chessboard.getPiece (1,4);
        assertFalse(blackPawn5.hasMoved(), "Pawn should not have already moved at starting position.");
        Pawn blackPawn6 = (Pawn) chessboard.getPiece (1,5);
        assertFalse(blackPawn6.hasMoved(), "Pawn should not have already moved at starting position.");
        Pawn blackPawn7 = (Pawn) chessboard.getPiece (1,6);
        assertFalse(blackPawn7.hasMoved(), "Pawn should not have already moved at starting position.");
    }
    @Test
    void whitePawnNameCheck(){
        ChessGame gameState = new ChessGame(chessboard, FEN_INITIAL_POSITION);
        Pawn whitePawn = (Pawn) chessboard.getPiece (6,7);
        assertEquals(PlayerColor.WHITE, whitePawn.getColor(), "Pawn at square a2 should be white.");
        assertEquals(PieceNames.PAWN, whitePawn.getName(), "Pawn's name should be 'PAWN'");
        assertEquals("PAWN", whitePawn.toString(), "Pawn's name as string should be 'PAWN'");
    }
    @Test
    void blackPawnNameCheck(){
        ChessGame gameState = new ChessGame(chessboard, FEN_INITIAL_POSITION);
        Pawn blackPawn = (Pawn) chessboard.getPiece (1,7);
        assertEquals(PlayerColor.BLACK, blackPawn.getColor(), "Pawn at square a7 should be black.");
        assertEquals(PieceNames.PAWN, blackPawn.getName(), "Pawn's name should be 'PAWN'");
        assertEquals("pawn", blackPawn.toString(), "Pawn's name as string should be 'pawn'");
    }
    @Test
    void oppositeColorPawnsAtA5A4CannotMove() {
        ChessGame gameState = new ChessGame(chessboard, FEN_TWO_MUTUALLY_BLOCKING_PAWNS_POSITION);
        assertTrue(gameState.getLegalMovesForPieceAtPosition(new Position(3, 0)).isEmpty());
        assertTrue(gameState.getLegalMovesForPieceAtPosition(new Position(4, 0)).isEmpty());
    }
    @Test
    void oppositeColorPawnsAtC5D5C4D4WhiteCannotMoveCanCapture() {
        ChessGame gameState = new ChessGame(chessboard, FEN_FOUR_MUTUALLY_BLOCKING_PAWNS_POSITION);
        assertEquals(1, gameState.getLegalMovesForPieceAtPosition(new Position(4, 2)).size());
        assertEquals(1, gameState.getLegalMovesForPieceAtPosition(new Position(4, 3)).size());
        Collection<Move> whitePawn1LegalMoves = new ArrayList<>();
        whitePawn1LegalMoves.add(new StandardMove(new Position(4, 3), new Position(3, 2)));
        Collection<Move> computedPawn1Moves = gameState.getLegalMovesForPieceAtPosition(new Position(4, 3));
        assertTrue(whitePawn1LegalMoves.size() == computedPawn1Moves.size() &&
                whitePawn1LegalMoves.containsAll(computedPawn1Moves) &&
                computedPawn1Moves.containsAll(whitePawn1LegalMoves), "The expected list of moves: " + whitePawn1LegalMoves +
                " is not the same as the produced one: " + computedPawn1Moves);
        Collection<Move> whitePawn2LegalMoves = new ArrayList<>();
        whitePawn2LegalMoves.add(new StandardMove(new Position(4, 2), new Position(3, 3)));
        Collection<Move> computedPawn2Moves = gameState.getLegalMovesForPieceAtPosition(new Position(4, 2));
        assertTrue(whitePawn2LegalMoves.size() == computedPawn2Moves.size() &&
                whitePawn2LegalMoves.containsAll(computedPawn2Moves) &&
                computedPawn2Moves.containsAll(whitePawn2LegalMoves), "The expected list of moves: " + whitePawn1LegalMoves +
                " is not the same as the produced one: " + computedPawn2Moves);
    }
    @Test
    void oppositeColorPawnsAtC5D5C4D4BlackCannotMoveCanCapture() {
        ChessGame gameState = new ChessGame(chessboard, FEN_FOUR_MUTUALLY_BLOCKING_PAWNS_POSITION);
        gameState.setCurrentColor(PlayerColor.BLACK);
        assertEquals(1, gameState.getLegalMovesForPieceAtPosition(new Position(3, 2)).size());
        assertEquals(1, gameState.getLegalMovesForPieceAtPosition(new Position(3, 3)).size());
        Collection<Move> blackPawn1LegalMoves = new ArrayList<>();
        blackPawn1LegalMoves.add(new StandardMove(new Position(3, 2), new Position(4, 3)));
        Collection<Move> computedPawn1Moves = gameState.getLegalMovesForPieceAtPosition(new Position(3, 2));
        assertTrue(blackPawn1LegalMoves.size() == computedPawn1Moves.size() &&
                blackPawn1LegalMoves.containsAll(computedPawn1Moves) &&
                computedPawn1Moves.containsAll(blackPawn1LegalMoves), "The expected list of moves: " + blackPawn1LegalMoves +
                " is not the same as the produced one: " + computedPawn1Moves);
        Collection<Move> blackPawn2LegalMoves = new ArrayList<>();
        blackPawn2LegalMoves.add(new StandardMove(new Position(3, 3), new Position(4, 2)));
        Collection<Move> computedPawn2Moves = gameState.getLegalMovesForPieceAtPosition(new Position(3, 3));
        assertTrue(blackPawn2LegalMoves.size() == computedPawn2Moves.size() &&
                blackPawn2LegalMoves.containsAll(computedPawn2Moves) &&
                computedPawn2Moves.containsAll(blackPawn2LegalMoves), "The expected list of moves: " + blackPawn1LegalMoves +
                " is not the same as the produced one: " + computedPawn2Moves);
    }
    @Test
    void oppositeColorPawnsAtE5F5G5E4F4G4CannotMoveCanCapture() {
        ChessGame gameState = new ChessGame(chessboard, FEN_SIX_MUTUALLY_BLOCKING_PAWNS_POSITION);
        assertEquals(1, gameState.getLegalMovesForPieceAtPosition(new Position(4, 4)).size());
        assertEquals(2, gameState.getLegalMovesForPieceAtPosition(new Position(4, 5)).size());
        assertEquals(1, gameState.getLegalMovesForPieceAtPosition(new Position(4, 6)).size());
        gameState.setCurrentColor(PlayerColor.BLACK);
        assertEquals(1, gameState.getLegalMovesForPieceAtPosition(new Position(3, 4)).size());
        assertEquals(2, gameState.getLegalMovesForPieceAtPosition(new Position(3, 5)).size());
        assertEquals(1, gameState.getLegalMovesForPieceAtPosition(new Position(3, 6)).size());
    }
    @Test
    void noEnPassantForBlackPawn(){
        ChessGame gameState = new ChessGame(chessboard, FEN_TWO_PAWNS_NO_EN_PASSANT_POSITION_BASIC);
        assertEquals(2, gameState.getLegalMovesForPieceAtPosition(new Position(1, 0)).size());
        gameState.setCurrentColor(PlayerColor.WHITE);
        assertEquals(1, gameState.getLegalMovesForPieceAtPosition(new Position(3, 1)).size());
    }
    @Test
    void noEnPassantForWhitePawnWithBlackPawnAtStartingSquare(){
        ChessGame gameState = new ChessGame(chessboard, FEN_TWO_PAWNS_NO_EN_PASSANT_POSITION_BLACK_PAWN_START_SQUARE);
        assertEquals(1, gameState.getLegalMovesForPieceAtPosition(new Position(3, 1)).size());
        assertEquals(1, gameState.getLegalMovesForPieceAtPosition(new Position(3, 1)).size());
    }
    @Test
    void enPassantBasicCaseLeft(){
        ChessGame gameState = new ChessGame(chessboard, FEN_TWO_PAWNS_EN_PASSANT_POSITION_BASIC_A5);
        Collection<Move> whitePawnLegalMoves = new ArrayList<>();
        whitePawnLegalMoves.add(new StandardMove(new Position(3,1), new Position(2,1)));
        whitePawnLegalMoves.add(new EnPassantMove(new Position(3,1), new Position(2,0)));
        Collection<Move> computedWhitePawnMoves = gameState.getLegalMovesForPieceAtPosition(new Position(3, 1));
        assertEquals(2, computedWhitePawnMoves.size(), "White pawn should have 2 possible moves.");
        assertTrue(whitePawnLegalMoves.containsAll(computedWhitePawnMoves) &&
                computedWhitePawnMoves.containsAll(whitePawnLegalMoves), "The expected list of moves: " + whitePawnLegalMoves +
                " is not the same as the produced one: " + computedWhitePawnMoves);
    }
    @Test
    void enPassantBasicCaseRight(){
        ChessGame gameState = new ChessGame(chessboard, FEN_TWO_PAWNS_EN_PASSANT_POSITION_BASIC_H4);
        Collection<Move> blackPawnLegalMoves = new ArrayList<>();
        blackPawnLegalMoves.add(new StandardMove(new Position(4,7), new Position(5,7)));
        blackPawnLegalMoves.add(new EnPassantMove(new Position(4,7), new Position(5,6)));
        Collection<Move> computedWhitePawnMoves = gameState.getLegalMovesForPieceAtPosition(new Position(4, 7));
        assertEquals(2, computedWhitePawnMoves.size(), "Black pawn should have 2 possible moves.");
        assertTrue(blackPawnLegalMoves.containsAll(computedWhitePawnMoves) &&
                computedWhitePawnMoves.containsAll(blackPawnLegalMoves), "The expected list of moves: " + blackPawnLegalMoves +
                " is not the same as the produced one: " + computedWhitePawnMoves);
    }
    @Test
    void whitePawnPromotion(){
        ChessGame gameState = new ChessGame(chessboard, FEN_WHITE_PAWN_PROMOTION);
        Collection<Move> whitePawnLegalMoves = new ArrayList<>();
        whitePawnLegalMoves.add(new PawnPromotionMove(new Position(1,3), new Position(0,3), new Queen(PlayerColor.WHITE)));
        whitePawnLegalMoves.add(new PawnPromotionMove(new Position(1,3), new Position(0,3), new Rook(PlayerColor.WHITE)));
        whitePawnLegalMoves.add(new PawnPromotionMove(new Position(1,3), new Position(0,3), new Bishop(PlayerColor.WHITE)));
        whitePawnLegalMoves.add(new PawnPromotionMove(new Position(1,3), new Position(0,3), new Knight(PlayerColor.WHITE)));
        Collection<Move> computedWhitePawnMoves = gameState.getLegalMovesForPieceAtPosition(new Position(1, 3));
        assertEquals(4, computedWhitePawnMoves.size(), "White pawn should have 4 possible promotion moves.");
        assertTrue(whitePawnLegalMoves.containsAll(computedWhitePawnMoves) &&
                computedWhitePawnMoves.containsAll(whitePawnLegalMoves), "The expected list of moves: " + whitePawnLegalMoves +
                " is not the same as the produced one: " + computedWhitePawnMoves);
    }
    @Test
    void blackPawnPromotion(){
        ChessGame gameState = new ChessGame(chessboard, FEN_BLACK_PAWN_PROMOTION);
        Collection<Move> blackPawnLegalMoves = new ArrayList<>();
        blackPawnLegalMoves.add(new PawnPromotionMove(new Position(6,0), new Position(7,0), new Queen(PlayerColor.BLACK)));
        blackPawnLegalMoves.add(new PawnPromotionMove(new Position(6,0), new Position(7,0), new Rook(PlayerColor.BLACK)));
        blackPawnLegalMoves.add(new PawnPromotionMove(new Position(6,0), new Position(7,0), new Bishop(PlayerColor.BLACK)));
        blackPawnLegalMoves.add(new PawnPromotionMove(new Position(6,0), new Position(7,0), new Knight(PlayerColor.BLACK)));
        Collection<Move> computedBlackPawnMoves = gameState.getLegalMovesForPieceAtPosition(new Position(6, 0));
        assertEquals(4, computedBlackPawnMoves.size(), "Black pawn should have 4 possible promotion moves.");
        assertTrue(blackPawnLegalMoves.containsAll(computedBlackPawnMoves) &&
                computedBlackPawnMoves.containsAll(blackPawnLegalMoves), "The expected list of moves: " + blackPawnLegalMoves +
                " is not the same as the produced one: " + computedBlackPawnMoves);
    }

}
