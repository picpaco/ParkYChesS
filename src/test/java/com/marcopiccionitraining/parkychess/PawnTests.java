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
import java.util.List;

import static com.marcopiccionitraining.parkychess.model.FENPositions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class PawnTests {

    @BeforeEach
    void setup(){}

    @Test
    void pawnEqualityCheck(){
        ChessGame gameState = new ChessGame(FEN_INITIAL_POSITION);
        Pawn whitePawnColumn0 = (Pawn) gameState.getChessboard().getPiece (6,0);
        Pawn whitePawnColumn7 = (Pawn) gameState.getChessboard().getPiece (6,7);
        assertNotEquals(whitePawnColumn0, whitePawnColumn7, "Pawns at columns a and h should be semantically different.");
        assertNotSame(whitePawnColumn0, whitePawnColumn7, "Pawns at columns a and h should be different objects.");
    }

    @Test
    void whitePawnNameCheck(){
        ChessGame gameState = new ChessGame(FEN_INITIAL_POSITION);
        Pawn whitePawn = (Pawn) gameState.getChessboard().getPiece (6,7);
        assertEquals(PlayerColor.WHITE, whitePawn.getColor(), "Pawn at square h2 should be white.");
        assertEquals(PieceNames.PAWN, whitePawn.getName(), "Pawn's name should be 'PAWN'");
        assertEquals("P", whitePawn.toString(), "Pawn's name as string should be 'P'");
    }
    @Test
    void blackPawnNameCheck(){
        ChessGame gameState = new ChessGame(FEN_INITIAL_POSITION);
        Pawn blackPawn = (Pawn) gameState.getChessboard().getPiece (1,7);
        assertEquals(PlayerColor.BLACK, blackPawn.getColor(), "Pawn at square h7 should be black.");
        assertEquals(PieceNames.PAWN, blackPawn.getName(), "Pawn's name should be 'PAWN'");
        assertEquals("p", blackPawn.toString(), "Pawn's name as string should be 'p'");
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
        log.error("{}", gameState.getChessboard());
       // assertEquals(1, gameState.getLegalMovesForPieceAtPosition(new Position(4, 2)).size());
       // assertEquals(1, gameState.getLegalMovesForPieceAtPosition(new Position(4, 3)).size());
        Collection<Move> whitePawnC4LegalMoves = new ArrayList<>();
        whitePawnC4LegalMoves.add(new StandardMove(new Position(4, 2), new Position(3, 3)));
        Collection<Move> computedWhitePawnC4LegalMoves = gameState.getLegalMovesForPieceAtPosition(new Position(4, 2));
        assertTrue(whitePawnC4LegalMoves.size() == computedWhitePawnC4LegalMoves.size() &&
                        whitePawnC4LegalMoves.containsAll(computedWhitePawnC4LegalMoves) && computedWhitePawnC4LegalMoves.containsAll(whitePawnC4LegalMoves),
                "The expected list of legal moves for c4 pawn: " + whitePawnC4LegalMoves +
                        " and the produced one: " + computedWhitePawnC4LegalMoves + " are not the same.");
   /*     Collection<Move> whitePawnD4LegalMoves = new ArrayList<>();
        whitePawnD4LegalMoves.add(new StandardMove(new Position(4, 3), new Position(3, 2)));
        Collection<Move> computedWhitePawnD4LegalMoves = gameState.getLegalMovesForPieceAtPosition(new Position(4, 3));
        assertTrue(whitePawnD4LegalMoves.size() == computedWhitePawnD4LegalMoves.size() &&
                whitePawnD4LegalMoves.containsAll(computedWhitePawnD4LegalMoves) && computedWhitePawnD4LegalMoves.containsAll(whitePawnD4LegalMoves),
                "The expected list of legal moves for d4 pawn: " + whitePawnD4LegalMoves + " and the produced one: " +
                        computedWhitePawnD4LegalMoves + " are not the same.");

    */
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
    void blackPawns_B5_C6_WhitePawnA5_Ep_Right() {
        ChessGame gameState = new ChessGame(FEN_FOUR_PAWNS_ONE_EN_PASSANT_POSITION_BASIC);
        Board chessboard = gameState.getChessboard();
        assertEquals(2, gameState.getLegalMovesForPieceAtPosition(new Position(3, 0)).size());
        Collection<Move> whitePawnLegalMoves = new ArrayList<>();
        whitePawnLegalMoves.add(new StandardMove(new Position(3, 0), new Position(2, 0)));
        whitePawnLegalMoves.add(new EnPassantCapture(new Position(3, 0), new Position(2, 1), chessboard));
        Collection<Move> computedPawnMoves = gameState.getLegalMovesForPieceAtPosition(new Position(3, 0));
        assertTrue(whitePawnLegalMoves.size() == computedPawnMoves.size() &&
                whitePawnLegalMoves.containsAll(computedPawnMoves) &&
                computedPawnMoves.containsAll(whitePawnLegalMoves), "The expected list of moves: " + whitePawnLegalMoves +
                " is not the same as the produced one: " + computedPawnMoves);
    }

    @Test
    void blackPawns_E5_G5_WhitePawnF5_Ep_Right() {
        ChessGame gameState = new ChessGame(FEN_THREE_PAWNS_ONE_EN_PASSANT_POSITION_BASIC);
        Board chessboard = gameState.getChessboard();
        Position whitePawnF5 = new Position(3, 5);
        Position whitePawnF6 = new Position(2, 5);
        Position whitePawnG6 = new Position(2, 6);
        assertEquals(2, gameState.getLegalMovesForPieceAtPosition(whitePawnF5).size());
        Collection<Move> whitePawnLegalMoves = new ArrayList<>();
        whitePawnLegalMoves.add(new StandardMove(whitePawnF5, whitePawnF6));
        whitePawnLegalMoves.add(new EnPassantCapture(whitePawnF5, whitePawnG6, chessboard));
        Collection<Move> computedPawnMoves = gameState.getLegalMovesForPieceAtPosition(whitePawnF5);
        assertTrue(whitePawnLegalMoves.size() == computedPawnMoves.size() &&
                whitePawnLegalMoves.containsAll(computedPawnMoves) &&
                computedPawnMoves.containsAll(whitePawnLegalMoves), "The expected list of moves: "
                + whitePawnLegalMoves + " does not match the produced one: " + computedPawnMoves);
    }

    @Test
    void oppositeColorPawnsAtE5F5G5E4F4G4CannotMoveCanCapture() {
        ChessGame gameState = new ChessGame(FEN_SIX_MUTUALLY_BLOCKING_PAWNS_POSITION);
        assertEquals(1, gameState.getLegalMovesForPieceAtPosition(new Position(4, 4)).size(),
                "We were expecting 1 possible capture move for white pawn in e4");
        assertEquals(2, gameState.getLegalMovesForPieceAtPosition(new Position(4, 5)).size(),
                "We were expecting 2 possible capture moves for white pawn in f4");
        assertEquals(1, gameState.getLegalMovesForPieceAtPosition(new Position(4, 6)).size(),
                "We were expecting 1 possible capture move for white pawn in g4");
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
    void noEnPassantCaptureForBlackPawn(){
        ChessGame gameState = new ChessGame(FEN_TWO_PAWNS_NO_EN_PASSANT_POSITION_BASIC);
        assertEquals(2, gameState.getLegalMovesForPieceAtPosition(new Position(1, 0)).size());
    }

    @Test
    void noEnPassantCaptureForWhitePawn(){
        ChessGame gameState = new ChessGame(FEN_TWO_PAWNS_NO_EN_PASSANT_POSITION_BASIC);
        gameState.getChessboard().setCurrentPlayerColor(PlayerColor.WHITE);
        assertEquals(1, gameState.getLegalMovesForPieceAtPosition(new Position(3, 1)).size());
    }

    @Test
    void noEnPassantForWhitePawnWithBlackPawnAtStartingSquare(){
        ChessGame gameState = new ChessGame(FEN_TWO_PAWNS_NO_EN_PASSANT_POSITION_BLACK_PAWN_START_SQUARE);
        assertEquals(1, gameState.getLegalMovesForPieceAtPosition(new Position(3, 1)).size());
    }
    @Test
    void enPassantBasicCaseFromFENLeft(){
        ChessGame gameState = new ChessGame(FEN_TWO_PAWNS_EN_PASSANT_POSITION_BASIC_A5);
        log.trace("{}", gameState.getChessboard());
        Collection<Move> whitePawnLegalMoves = new ArrayList<>();
        whitePawnLegalMoves.add(new EnPassantCapture(new Position(3,1),
                new Position(2,0), gameState.getChessboard()));
        whitePawnLegalMoves.add(new StandardMove (new Position(3,1), new Position(2,1)));
        Collection<Move> computedWhitePawnLegalMoves = gameState.getLegalMovesForPieceAtPosition(new Position(3, 1));
        assertEquals(2, computedWhitePawnLegalMoves.size(), "White pawn should have 2 possible moves.");
        assertTrue(whitePawnLegalMoves.containsAll(computedWhitePawnLegalMoves) &&
                computedWhitePawnLegalMoves.containsAll(whitePawnLegalMoves), "The expected list of moves: " +
                whitePawnLegalMoves + " is not the same as the computed one: " + computedWhitePawnLegalMoves);
    }
    @Test
    void enPassantBasicCaseFromFENRight(){
        ChessGame gameState = new ChessGame(FEN_TWO_PAWNS_EN_PASSANT_POSITION_BASIC_H4);
        log.trace("{}", gameState.getChessboard());
        Collection<Move> blackPawnLegalMoves = new ArrayList<>();
        blackPawnLegalMoves.add(new EnPassantCapture(new Position(4,7),
                new Position(5,6), gameState.getChessboard()));
        blackPawnLegalMoves.add(new StandardMove(new Position(4,7), new Position(5,7)));
        Collection<Move> computedBlackPawnLegalMoves = gameState.getLegalMovesForPieceAtPosition(new Position(4,7));
        assertEquals(2, computedBlackPawnLegalMoves.size(), "Black pawn should have 2 possible moves.");
        assertTrue(blackPawnLegalMoves.containsAll(computedBlackPawnLegalMoves) &&
                computedBlackPawnLegalMoves.containsAll(blackPawnLegalMoves), "The expected list of moves: " +
                blackPawnLegalMoves + " is not the same as the computed one: " + computedBlackPawnLegalMoves);
    }
    @Test
    void enPassantBasicCaseFromFENRightNonEpPlayerTurn(){
        ChessGame gameState = new ChessGame(FEN_TWO_PAWNS_EN_PASSANT_POSITION_BASIC_H4_WHITE_MOVES);
        log.trace("{}", gameState.getChessboard());
        Collection<Move> whitePawnLegalMoves = new ArrayList<>();
        whitePawnLegalMoves.add(new StandardMove(new Position(4,6), new Position(3,6)));
        Collection<Move> computedWhitePawnLegalMoves = gameState.getLegalMovesForPieceAtPosition(new Position(4,6));
        assertEquals(1, computedWhitePawnLegalMoves.size(), "White pawn should have 1 possible move.");
        assertTrue(whitePawnLegalMoves.containsAll(computedWhitePawnLegalMoves) &&
                computedWhitePawnLegalMoves.containsAll(whitePawnLegalMoves), "The expected list of moves: " +
                whitePawnLegalMoves + " is not the same as the computed one: " + computedWhitePawnLegalMoves);
    }
    @Test
    void whitePawnPromotion(){
        ChessGame gameState = new ChessGame(FEN_WHITE_PAWN_PROMOTION);
        Collection<Move> whitePawnLegalMoves = new ArrayList<>();
        whitePawnLegalMoves.add(new PawnPromotionMove(new Position(1,3), new Position(0,3),
                new Queen(PlayerColor.WHITE), null));
        whitePawnLegalMoves.add(new PawnPromotionMove(new Position(1,3), new Position(0,3),
                new Rook(PlayerColor.WHITE), null));
        whitePawnLegalMoves.add(new PawnPromotionMove(new Position(1,3), new Position(0,3),
                new Bishop(PlayerColor.WHITE), null));
        whitePawnLegalMoves.add(new PawnPromotionMove(new Position(1,3), new Position(0,3),
                new Knight(PlayerColor.WHITE), null));
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
                new Queen(PlayerColor.BLACK), null));
        blackPawnLegalMoves.add(new PawnPromotionMove(new Position(6,0), new Position(7,0),
                new Rook(PlayerColor.BLACK), null));
        blackPawnLegalMoves.add(new PawnPromotionMove(new Position(6,0), new Position(7,0),
                new Bishop(PlayerColor.BLACK), null));
        blackPawnLegalMoves.add(new PawnPromotionMove(new Position(6,0), new Position(7,0),
                new Knight(PlayerColor.BLACK), null));
        Collection<Move> computedBlackPawnMoves = gameState.getLegalMovesForPieceAtPosition(new Position(6, 0));
        assertEquals(4, computedBlackPawnMoves.size(), "Black pawn should have 4 possible promotion moves.");
        assertTrue(blackPawnLegalMoves.containsAll(computedBlackPawnMoves) &&
                computedBlackPawnMoves.containsAll(blackPawnLegalMoves), "The expected list of moves: " + blackPawnLegalMoves +
                " is not the same as the produced one: " + computedBlackPawnMoves);
    }

    @Test
    void testOnlyOnePawnEnPassantCapturePositionAssociatedWithBlackPawnSkippedPosition(){
        assertEquals(1, PseudoLegalPiecesPositionsInitializer.getAttackingPawnsAssociatedWithSkippedPositions(
                new Position(2, 0), PlayerColor.BLACK).size());
        List<Position> positionsList = new ArrayList<>();
        positionsList.add(new Position(3, 1));
        List<Position> preComputedPositionsList = PseudoLegalPiecesPositionsInitializer.getAttackingPawnsAssociatedWithSkippedPositions(
                new Position(2, 0), PlayerColor.BLACK);
        assertTrue(preComputedPositionsList.contains(positionsList.get(0)) &&
                        positionsList.contains(preComputedPositionsList.get(0)),
                "The preconstructed list " + preComputedPositionsList + " and the computed list " +
                        positionsList + " do not match.");
    }

    @Test
    void testTwoPawnEnPassantCapturePositionsAssociatedWithBlackPawnSkippedPosition(){
        assertEquals(2, PseudoLegalPiecesPositionsInitializer.getAttackingPawnsAssociatedWithSkippedPositions(
                new Position(2, 6), PlayerColor.BLACK).size());
        List<Position> positionsList = new ArrayList<>();
        positionsList.add(new Position(3, 5));
        positionsList.add(new Position(3, 7));
        List<Position> preComputedPositionsList = PseudoLegalPiecesPositionsInitializer.getAttackingPawnsAssociatedWithSkippedPositions(
                new Position(2, 6), PlayerColor.BLACK);
        assertTrue(preComputedPositionsList.containsAll(positionsList) &&
                        positionsList.containsAll(preComputedPositionsList),
                "The preconstructed list " + preComputedPositionsList + " and the computed list " +
                        positionsList + " do not match.");
    }

    @Test
    void testOnlyOnePawnEnPassantCapturePositionAssociatedWithWhitePawnSkippedPosition(){
        assertEquals(1, PseudoLegalPiecesPositionsInitializer.getAttackingPawnsAssociatedWithSkippedPositions(
                new Position(5, 7), PlayerColor.WHITE).size());
        List<Position> positionsList = new ArrayList<>();
        positionsList.add(new Position(4, 6));
        List<Position> preComputedPositionsList = PseudoLegalPiecesPositionsInitializer.getAttackingPawnsAssociatedWithSkippedPositions(
                new Position(5, 7), PlayerColor.WHITE);
        assertTrue(preComputedPositionsList.contains(positionsList.get(0)) &&
                        positionsList.contains(preComputedPositionsList.get(0)),
                "The preconstructed list " + preComputedPositionsList + " and the computed list " +
                        positionsList + " do not match.");
    }

    @Test
    void testTwoPawnEnPassantCapturePositionsAssociatedWithWhitePawnSkippedPosition(){
        assertEquals(2, PseudoLegalPiecesPositionsInitializer.getAttackingPawnsAssociatedWithSkippedPositions(
                new Position(5, 6), PlayerColor.WHITE).size());
        List<Position> positionsList = new ArrayList<>();
        positionsList.add(new Position(4, 5));
        positionsList.add(new Position(4, 7));
        List<Position> preComputedPositionsList = PseudoLegalPiecesPositionsInitializer.getAttackingPawnsAssociatedWithSkippedPositions(
                new Position(5, 6), PlayerColor.WHITE);
        assertTrue(preComputedPositionsList.containsAll(positionsList) &&
                        positionsList.containsAll(preComputedPositionsList),
                "The preconstructed list " + preComputedPositionsList + " and the computed list " +
                        positionsList + " do not match.");
    }
}
