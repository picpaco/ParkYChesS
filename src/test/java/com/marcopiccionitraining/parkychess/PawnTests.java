package com.marcopiccionitraining.parkychess;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.*;
import com.marcopiccionitraining.parkychess.model.pieces.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;

import static com.marcopiccionitraining.parkychess.model.FENPositions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class PawnTests {


    @BeforeEach
    void setup(){}

    @Test
    void whitePawnNameCheck(){
        ChessGame gameState = new ChessGame(FEN_INITIAL_POSITION);
        Pawn whitePawn = (Pawn) gameState.getChessboard().getPiece (6,7);
        assertEquals(PlayerColor.WHITE, whitePawn.getColor(), "Pawn at square h2 should be white.");
        assertEquals(PieceNames.PAWN, whitePawn.getName(), "Pawn's name should be 'PAWN'");
        assertEquals("Ph2", whitePawn.toString(), "Pawn's name as string should be 'Ph2'");
    }
    @Test
    void blackPawnNameCheck(){
        ChessGame gameState = new ChessGame(FEN_INITIAL_POSITION);
        Pawn blackPawn = (Pawn) gameState.getChessboard().getPiece (1,7);
        assertEquals(PlayerColor.BLACK, blackPawn.getColor(), "Pawn at square h7 should be black.");
        assertEquals(PieceNames.PAWN, blackPawn.getName(), "Pawn's name should be 'PAWN'");
        assertEquals("ph7", blackPawn.toString(), "Pawn's name as string should be 'ph7'");
    }
    @Test
    void oppositeColorPawnsAtA5A4CannotMove() {
        ChessGame gameState = new ChessGame(FEN_TWO_MUTUALLY_BLOCKING_PAWNS_POSITION);
        assertTrue(gameState.getLegalMovesForPieceAtPosition(new Position(3, 0)).isEmpty());
        assertTrue(gameState.getLegalMovesForPieceAtPosition(new Position(4, 0)).isEmpty());
    }
    @Test
    void oppositeColorPawnsAtC5D5C4D4WhiteCannotMoveCanCapture() {
        ChessGame gameState = new ChessGame(FEN_FOUR_MUTUALLY_BLOCKING_PAWNS_POSITION);
        log.debug("{}", gameState.getChessboard());
        assertEquals(1, gameState.getLegalMovesForPieceAtPosition(new Position(4, 2)).size());
        assertEquals(1, gameState.getLegalMovesForPieceAtPosition(new Position(4, 3)).size());
        Collection<Move> whitePawnLegalMoves = new ArrayList<>();
        whitePawnLegalMoves.add(new StandardMove(new Position(4, 3), new Position(3, 2)));
        Collection<Move> computedPawnMoves = gameState.getLegalMovesForPieceAtPosition(new Position(4, 3));
        assertTrue(whitePawnLegalMoves.size() == computedPawnMoves.size() && whitePawnLegalMoves.containsAll(computedPawnMoves) &&
                computedPawnMoves.containsAll(whitePawnLegalMoves), "The expected list of moves: " + whitePawnLegalMoves +
                " is not the same as the produced one: " + computedPawnMoves);
        Collection<Move> whitePawn2LegalMoves = new ArrayList<>();
        whitePawn2LegalMoves.add(new StandardMove(new Position(4, 2), new Position(3, 3)));
        Collection<Move> computedPawn2Moves = gameState.getLegalMovesForPieceAtPosition(new Position(4, 2));
        assertTrue(whitePawn2LegalMoves.size() == computedPawn2Moves.size() && whitePawn2LegalMoves.containsAll(computedPawn2Moves) &&
                computedPawn2Moves.containsAll(whitePawn2LegalMoves), "The expected list of moves: " + whitePawnLegalMoves +
                " is not the same as the produced one: " + computedPawn2Moves);
    }

    @Test
    void oppositeColorPawnsAtC5D5C4D4BlackCannotMoveCanCapture() {
        ChessGame gameState = new ChessGame(FEN_FOUR_MUTUALLY_BLOCKING_PAWNS_POSITION);
        gameState.getChessboard().setCurrentPlayerColor(PlayerColor.BLACK);
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
        ChessGame gameState = new ChessGame(FEN_SIX_MUTUALLY_BLOCKING_PAWNS_POSITION);
        assertEquals(1, gameState.getLegalMovesForPieceAtPosition(new Position(4, 4)).size(),
                "We were expecting 1 possible move for white pawn in e4");
        assertEquals(2, gameState.getLegalMovesForPieceAtPosition(new Position(4, 5)).size(),
                "We were expecting 2 possible moves for white pawn in f4");
        assertEquals(1, gameState.getLegalMovesForPieceAtPosition(new Position(4, 6)).size(),
                "We were expecting 1 possible move for white pawn in g4");
        gameState.getChessboard().setCurrentPlayerColor(PlayerColor.BLACK);
        assertEquals(1, gameState.getLegalMovesForPieceAtPosition(new Position(3, 4)).size(),
                "We were expecting 1 possible move for black pawn in e5");
        assertEquals(2, gameState.getLegalMovesForPieceAtPosition(new Position(3, 5)).size(),
                "We were expecting 2 possible moves for black pawn in f5");
        assertEquals(1, gameState.getLegalMovesForPieceAtPosition(new Position(3, 6)).size(),
                "We were expecting 1 possible move for black pawn in g5");
    }

    @Test
    void WhitePawnA4CanMoveCannotCapture() {
        ChessGame gameState = new ChessGame(FEN_WHITE_PAWN_CAN_MOVE_NOT_CAPTURE_POSITION);
        assertEquals(1, gameState.getLegalMovesForPieceAtPosition(new Position(4, 0)).size());
        Collection<Move> whitePawnLegalMoves = new ArrayList<>();
        whitePawnLegalMoves.add(new StandardMove(new Position(4, 0), new Position(3, 0)));
        Collection<Move> computedPawnMoves = gameState.getLegalMovesForPieceAtPosition(new Position(4, 0));
        assertTrue(whitePawnLegalMoves.size() == computedPawnMoves.size() &&
                whitePawnLegalMoves.containsAll(computedPawnMoves) &&
                computedPawnMoves.containsAll(whitePawnLegalMoves), "The expected list of moves: " + whitePawnLegalMoves +
                " is not the same as the produced one: " + computedPawnMoves);
    }

    @Test
    void BlackPawnA4CanMoveCannotCapture() {
        ChessGame gameState = new ChessGame(FEN_BLACK_PAWN_CAN_MOVE_NOT_CAPTURE_POSITION);
        assertEquals(1, gameState.getLegalMovesForPieceAtPosition(new Position(4, 0)).size());
        Collection<Move> whitePawnLegalMoves = new ArrayList<>();
        whitePawnLegalMoves.add(new StandardMove(new Position(4, 0), new Position(5, 0)));
        Collection<Move> computedPawnMoves = gameState.getLegalMovesForPieceAtPosition(new Position(4, 0));
        assertTrue(whitePawnLegalMoves.size() == computedPawnMoves.size() &&
                whitePawnLegalMoves.containsAll(computedPawnMoves) &&
                computedPawnMoves.containsAll(whitePawnLegalMoves), "The expected list of moves: " + whitePawnLegalMoves +
                " is not the same as the produced one: " + computedPawnMoves);
    }

    @Test
    void noEnPassantForBlackPawn(){
        ChessGame gameState = new ChessGame(FEN_TWO_PAWNS_NO_EN_PASSANT_POSITION_BASIC);
        assertEquals(2, gameState.getLegalMovesForPieceAtPosition(new Position(1, 0)).size());
        gameState.getChessboard().setCurrentPlayerColor(PlayerColor.WHITE);
        assertEquals(1, gameState.getLegalMovesForPieceAtPosition(new Position(3, 1)).size());
    }
    @Test
    void noEnPassantForWhitePawnWithBlackPawnAtStartingSquare(){
        ChessGame gameState = new ChessGame(FEN_TWO_PAWNS_NO_EN_PASSANT_POSITION_BLACK_PAWN_START_SQUARE);
        assertEquals(1, gameState.getLegalMovesForPieceAtPosition(new Position(3, 1)).size());
        assertEquals(1, gameState.getLegalMovesForPieceAtPosition(new Position(3, 1)).size());
    }
    @Test
    void enPassantBasicCaseLeft(){
        ChessGame gameState = new ChessGame(FEN_TWO_PAWNS_EN_PASSANT_POSITION_BASIC_A5);
        log.debug("Initial position: {}", gameState.getChessboard());
        Collection<Move> whitePawnLegalMoves = new ArrayList<>();
        whitePawnLegalMoves.add(new EnPassantCapture(new Position(3,1), new Position(2,0)));
        whitePawnLegalMoves.add(new StandardMove (new Position(3,1), new Position(2,1)));
        Collection<Move> computedWhitePawnMoves = gameState.getLegalMovesForPieceAtPosition(new Position(3, 1));
        assertEquals(2, computedWhitePawnMoves.size(), "White pawn should have 2 possible moves.");
        assertTrue(whitePawnLegalMoves.containsAll(computedWhitePawnMoves) &&
                computedWhitePawnMoves.containsAll(whitePawnLegalMoves), "The expected list of moves: " + whitePawnLegalMoves +
                " is not the same as the produced one: " + computedWhitePawnMoves);
    }
    @Test
    void enPassantBasicCaseRight(){
        ChessGame gameState = new ChessGame(FEN_TWO_PAWNS_EN_PASSANT_POSITION_BASIC_H4);
        log.debug("Here is the initial position: {}", gameState.getChessboard());
        Collection<Move> blackPawnLegalMoves = new ArrayList<>();
        blackPawnLegalMoves.add(new StandardMove(new Position(4,7), new Position(5,7)));
        blackPawnLegalMoves.add(new EnPassantCapture(new Position(4,7), new Position(5,6)));
        Collection<Move> computedBlackPawnMoves = gameState.getLegalMovesForPieceAtPosition(new Position(4, 7));
        assertEquals(2, computedBlackPawnMoves.size(), "Black pawn should have 2 possible moves.");
        assertTrue(blackPawnLegalMoves.containsAll(computedBlackPawnMoves) &&
                computedBlackPawnMoves.containsAll(blackPawnLegalMoves), "The expected list of moves: " + blackPawnLegalMoves +
                " is not the same as the produced one: " + computedBlackPawnMoves);
    }
    @Test
    void whitePawnPromotion(){
        ChessGame gameState = new ChessGame(FEN_WHITE_PAWN_PROMOTION);
        Collection<Move> whitePawnLegalMoves = new ArrayList<>();
        whitePawnLegalMoves.add(new PawnPromotionMove(new Position(1,3), new Position(0,3),
                new Queen(PlayerColor.WHITE)));
        whitePawnLegalMoves.add(new PawnPromotionMove(new Position(1,3), new Position(0,3),
                new Rook(PlayerColor.WHITE)));
        whitePawnLegalMoves.add(new PawnPromotionMove(new Position(1,3), new Position(0,3),
                new Bishop(PlayerColor.WHITE)));
        whitePawnLegalMoves.add(new PawnPromotionMove(new Position(1,3), new Position(0,3),
                new Knight(PlayerColor.WHITE)));
        Collection<Move> computedWhitePawnMoves = gameState.getLegalMovesForPieceAtPosition(new Position(1, 3));
        assertEquals(4, computedWhitePawnMoves.size(), "White pawn should have 4 possible promotion moves.");
        assertTrue(whitePawnLegalMoves.containsAll(computedWhitePawnMoves) &&
                computedWhitePawnMoves.containsAll(whitePawnLegalMoves), "The expected list of moves: " + whitePawnLegalMoves +
                " is not the same as the produced one: " + computedWhitePawnMoves);
    }
    @Test
    void blackPawnPromotion(){
        ChessGame gameState = new ChessGame(FEN_BLACK_PAWN_PROMOTION);
        Collection<Move> blackPawnLegalMoves = new ArrayList<>();
        blackPawnLegalMoves.add(new PawnPromotionMove(new Position(6,0), new Position(7,0),
                new Queen(PlayerColor.BLACK)));
        blackPawnLegalMoves.add(new PawnPromotionMove(new Position(6,0), new Position(7,0),
                new Rook(PlayerColor.BLACK)));
        blackPawnLegalMoves.add(new PawnPromotionMove(new Position(6,0), new Position(7,0),
                new Bishop(PlayerColor.BLACK)));
        blackPawnLegalMoves.add(new PawnPromotionMove(new Position(6,0), new Position(7,0),
                new Knight(PlayerColor.BLACK)));
        Collection<Move> computedBlackPawnMoves = gameState.getLegalMovesForPieceAtPosition(new Position(6, 0));
        assertEquals(4, computedBlackPawnMoves.size(), "Black pawn should have 4 possible promotion moves.");
        assertTrue(blackPawnLegalMoves.containsAll(computedBlackPawnMoves) &&
                computedBlackPawnMoves.containsAll(blackPawnLegalMoves), "The expected list of moves: " + blackPawnLegalMoves +
                " is not the same as the produced one: " + computedBlackPawnMoves);
    }
}
