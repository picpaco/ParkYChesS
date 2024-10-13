package com.marcopiccionitraining.parkychess;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.DoublePawnMove;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import com.marcopiccionitraining.parkychess.model.moves.StandardMove;
import com.marcopiccionitraining.parkychess.model.pieces.King;
import com.marcopiccionitraining.parkychess.model.pieces.PieceName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;

import static com.marcopiccionitraining.parkychess.model.FENPositions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class KingTests {
    private Board chessboard;

    @BeforeEach
    void setup(){
        chessboard = new Board();
    }
    @Test
    void whiteKingInitialPositionHasNotMovedYet(){
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_INITIAL_POSITION));
        King whiteKing = (King) chessboard.getPiece (7,4);
        assertFalse(whiteKing.hasMoved(), "King should not have already moved at starting position.");
    }
    @Test
    void blackKingInitialPositionHasNotMovedYet(){
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_INITIAL_POSITION));
        King blackKing = (King) chessboard.getPiece (0,4);
        assertFalse(blackKing.hasMoved(), "King should not have already moved at starting position.");
    }
    @Test
    void whiteKingNameCheck(){
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_INITIAL_POSITION));
        King whiteKing = (King) chessboard.getPiece (7,4);
        assertEquals(PlayerColor.WHITE, whiteKing.getColor(), "King at square e1 should be white.");
        assertEquals(PieceName.KING, whiteKing.getName(), "King's name should be 'KING'");
        assertEquals("KING", whiteKing.toString(), "King's name as string should be 'KING'");
    }
    @Test
    void blackKingNameCheck(){
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_INITIAL_POSITION));
        King blackKing = (King) chessboard.getPiece (0,4);
        assertEquals(PlayerColor.BLACK, blackKing.getColor(), "King at square e8 should be black.");
        assertEquals(PieceName.KING, blackKing.getName(), "King's name should be 'KING'");
        assertEquals("king", blackKing.toString(), "King's name as string should be 'king'");
    }
    @Test
    void blackKingCannotMoveAtStartingPosition() {
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_INITIAL_POSITION));
        gameState.setCurrentColor(PlayerColor.BLACK);
        assertTrue(gameState.getLegalMovesForPieceAtPosition(new Position(0, 4)).isEmpty());
    }
    @Test
    void whiteKingCannotMoveAtStartingPosition() {
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_INITIAL_POSITION));
        assertTrue(gameState.getLegalMovesForPieceAtPosition(new Position(7, 4)).isEmpty());
    }
    @Test
    void blackKingLegalMovesBishopCheck() {
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_KING_BISHOP_CHECK_POSITION));
        Collection<Move> blackKingLegalMoves = new ArrayList<>();
        blackKingLegalMoves.add(new StandardMove(new Position(0,4), new Position(0,3)));
        blackKingLegalMoves.add(new StandardMove(new Position(0,4), new Position(1,4)));
        blackKingLegalMoves.add(new StandardMove(new Position(0,4), new Position(1,5)));
        blackKingLegalMoves.add(new StandardMove(new Position(0,4), new Position(0,5)));
        Collection<Move> computedKingMoves = gameState.getLegalMovesForPieceAtPosition(new Position(0, 4));
        assertTrue(blackKingLegalMoves.size() == computedKingMoves.size() &&
                blackKingLegalMoves.containsAll(computedKingMoves) &&
                computedKingMoves.containsAll(blackKingLegalMoves), "The expected list of moves: " + blackKingLegalMoves +
                " is not the same as the produced one: " + computedKingMoves);
    }
    @Test
    void blackKingLegalMovesBishopCheckInterposePossible() {
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_KING_BISHOP_CHECK_INTERPOSE_POSITION));
        Collection<Move> blackLegalMoves = new ArrayList<>();
        blackLegalMoves.add(new StandardMove(new Position(0,4), new Position(0,3)));
        blackLegalMoves.add(new StandardMove(new Position(0,4), new Position(1,4)));
        blackLegalMoves.add(new StandardMove(new Position(0,4), new Position(1,5)));
        blackLegalMoves.add(new StandardMove(new Position(0,4), new Position(0,5)));
        blackLegalMoves.add(new StandardMove(new Position(0,2), new Position(1,3)));
        Collection<Move> computedBlackMoves = gameState.getLegalMovesForColor(PlayerColor.BLACK);
        assertTrue(blackLegalMoves.size() == computedBlackMoves.size() &&
                blackLegalMoves.containsAll(computedBlackMoves) &&
                computedBlackMoves.containsAll(blackLegalMoves), "The expected list of moves: " + blackLegalMoves +
                " is not the same as the produced one: " + computedBlackMoves);
    }
    @Test
    void blackKingLegalMovesWhiteRookCheckNoQueensideCastleFromFEN() {
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_BLACK_KING_WHITE_ROOK_CHECK_NO_QUEENSIDE_CASTLE_POSITION));
        Collection<Move> blackKingLegalMoves = new ArrayList<>();
        blackKingLegalMoves.add(new StandardMove(new Position(0,4), new Position(1,3)));
        blackKingLegalMoves.add(new StandardMove(new Position(0,4), new Position(1,4)));
        blackKingLegalMoves.add(new StandardMove(new Position(0,4), new Position(1,5)));
        Collection<Move> computedKingMoves = gameState.getLegalMovesForPieceAtPosition(new Position(0, 4));
        assertTrue(blackKingLegalMoves.size() == computedKingMoves.size() &&
                blackKingLegalMoves.containsAll(computedKingMoves) &&
                computedKingMoves.containsAll(blackKingLegalMoves), "The expected list of moves: " + blackKingLegalMoves +
                " is not the same as the produced one: " + computedKingMoves);
    }
    @Test
    void blackKingLegalMovesWhiteRookCheckNoKingsideCastleFromFEN() {
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_BLACK_KING_WHITE_ROOK_CHECK_NO_KINGSIDE_CASTLE_POSITION));
        Collection<Move> blackKingLegalMoves = new ArrayList<>();
        blackKingLegalMoves.add(new StandardMove(new Position(0,4), new Position(1,3)));
        blackKingLegalMoves.add(new StandardMove(new Position(0,4), new Position(1,4)));
        blackKingLegalMoves.add(new StandardMove(new Position(0,4), new Position(1,5)));
        Collection<Move> computedKingMoves = gameState.getLegalMovesForPieceAtPosition(new Position(0, 4));
        assertTrue(blackKingLegalMoves.size() == computedKingMoves.size() &&
                blackKingLegalMoves.containsAll(computedKingMoves) &&
                computedKingMoves.containsAll(blackKingLegalMoves), "The expected list of moves: " + blackKingLegalMoves +
                " is not the same as the produced one: " + computedKingMoves);
    }
    @Test
    void whiteKingLegalMovesBlackRookCheckNoQueensideCastleFromFEN() {
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_WHITE_KING_BLACK_ROOK_CHECK_NO_QUEENSIDE_CASTLE_POSITION));
        Collection<Move> whiteKingLegalMoves = new ArrayList<>();
        whiteKingLegalMoves.add(new StandardMove(new Position(7,4), new Position(6,3)));
        whiteKingLegalMoves.add(new StandardMove(new Position(7,4), new Position(6,4)));
        whiteKingLegalMoves.add(new StandardMove(new Position(7,4), new Position(6,5)));
        Collection<Move> computedKingMoves = gameState.getLegalMovesForPieceAtPosition(new Position(7, 4));
        assertTrue(whiteKingLegalMoves.size() == computedKingMoves.size() &&
                whiteKingLegalMoves.containsAll(computedKingMoves) &&
                computedKingMoves.containsAll(whiteKingLegalMoves), "The expected list of moves: " + whiteKingLegalMoves +
                " is not the same as the produced one: " + computedKingMoves);
    }
    @Test
    void whiteKingLegalMovesBlackRookCheckNoKingsideCastleFromFEN() {
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_WHITE_KING_BLACK_ROOK_CHECK_NO_KINGSIDE_CASTLE_POSITION));
        Collection<Move> blackKingLegalMoves = new ArrayList<>();
        blackKingLegalMoves.add(new StandardMove(new Position(7,4), new Position(6,3)));
        blackKingLegalMoves.add(new StandardMove(new Position(7,4), new Position(6,4)));
        blackKingLegalMoves.add(new StandardMove(new Position(7,4), new Position(6,5)));
        Collection<Move> computedKingMoves = gameState.getLegalMovesForPieceAtPosition(new Position(7, 4));
        assertTrue(blackKingLegalMoves.size() == computedKingMoves.size() &&
                blackKingLegalMoves.containsAll(computedKingMoves) &&
                computedKingMoves.containsAll(blackKingLegalMoves), "The expected list of moves: " + blackKingLegalMoves +
                " is not the same as the produced one: " + computedKingMoves);
    }
    @Test
    void whiteKingNoQueensideCastleWithInterposingFriendlyPiece() {
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_WHITE_KING_NO_QUEENSIDE_CASTLE_WITH_INTERPOSING_FRIENDLY_PIECE));
        Collection<Move> whiteKingLegalMoves = new ArrayList<>();
        whiteKingLegalMoves.add(new StandardMove(new Position(7,4), new Position(7,3)));
        whiteKingLegalMoves.add(new StandardMove(new Position(7,4), new Position(7,5)));
        whiteKingLegalMoves.add(new StandardMove(new Position(7,4), new Position(6,5)));
        whiteKingLegalMoves.add(new StandardMove(new Position(7,4), new Position(6,4)));
        whiteKingLegalMoves.add(new StandardMove(new Position(7,4), new Position(6,3)));
        Collection<Move> computedKingMoves = gameState.getLegalMovesForPieceAtPosition(new Position(7, 4));
        assertTrue(whiteKingLegalMoves.size() == computedKingMoves.size() &&
                whiteKingLegalMoves.containsAll(computedKingMoves) &&
                computedKingMoves.containsAll(whiteKingLegalMoves), "The expected list of moves: " + whiteKingLegalMoves +
                " is not the same as the produced one: " + computedKingMoves);
    }
    @Test
    void blackKingNoQueensideCastleWithInterposingFriendlyPiece() {
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_BLACK_KING_NO_QUEENSIDE_CASTLE_WITH_INTERPOSING_FRIENDLY_PIECE));
        Collection<Move> blackKingLegalMoves = new ArrayList<>();
        blackKingLegalMoves.add(new StandardMove(new Position(0,4), new Position(0,3)));
        blackKingLegalMoves.add(new StandardMove(new Position(0,4), new Position(0,5)));
        blackKingLegalMoves.add(new StandardMove(new Position(0,4), new Position(1,5)));
        blackKingLegalMoves.add(new StandardMove(new Position(0,4), new Position(1,4)));
        blackKingLegalMoves.add(new StandardMove(new Position(0,4), new Position(1,3)));
        Collection<Move> computedKingMoves = gameState.getLegalMovesForPieceAtPosition(new Position(0, 4));
        assertTrue(blackKingLegalMoves.size() == computedKingMoves.size() &&
                blackKingLegalMoves.containsAll(computedKingMoves) &&
                computedKingMoves.containsAll(blackKingLegalMoves), "The expected list of moves: " + blackKingLegalMoves +
                " is not the same as the produced one: " + computedKingMoves);
    }
    @Test
    void whiteKingNoKingsideCastleWithInterposingFriendlyPiece() {
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_WHITE_KING_NO_KINGSIDE_CASTLE_WITH_INTERPOSING_FRIENDLY_PIECE));
        Collection<Move> whiteKingLegalMoves = new ArrayList<>();
        whiteKingLegalMoves.add(new StandardMove(new Position(7,4), new Position(7,3)));
        whiteKingLegalMoves.add(new StandardMove(new Position(7,4), new Position(7,5)));
        whiteKingLegalMoves.add(new StandardMove(new Position(7,4), new Position(6,5)));
        whiteKingLegalMoves.add(new StandardMove(new Position(7,4), new Position(6,4)));
        whiteKingLegalMoves.add(new StandardMove(new Position(7,4), new Position(6,3)));
        Collection<Move> computedKingMoves = gameState.getLegalMovesForPieceAtPosition(new Position(7, 4));
        assertTrue(whiteKingLegalMoves.size() == computedKingMoves.size() &&
                whiteKingLegalMoves.containsAll(computedKingMoves) &&
                computedKingMoves.containsAll(whiteKingLegalMoves), "The expected list of moves: " + whiteKingLegalMoves +
                " is not the same as the produced one: " + computedKingMoves);
    }
    @Test
    void blackKingNoKingsideCastleWithInterposingFriendlyPiece() {
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_BLACK_KING_NO_KINGSIDE_CASTLE_WITH_INTERPOSING_FRIENDLY_PIECE));
        Collection<Move> blackKingLegalMoves = new ArrayList<>();
        blackKingLegalMoves.add(new StandardMove(new Position(0,4), new Position(0,3)));
        blackKingLegalMoves.add(new StandardMove(new Position(0,4), new Position(1,5)));
        blackKingLegalMoves.add(new StandardMove(new Position(0,4), new Position(1,4)));
        blackKingLegalMoves.add(new StandardMove(new Position(0,4), new Position(1,3)));
        Collection<Move> computedKingMoves = gameState.getLegalMovesForPieceAtPosition(new Position(0, 4));
        assertTrue(blackKingLegalMoves.size() == computedKingMoves.size() &&
                blackKingLegalMoves.containsAll(computedKingMoves) &&
                computedKingMoves.containsAll(blackKingLegalMoves), "The expected list of moves: " + blackKingLegalMoves +
                " is not the same as the produced one: " + computedKingMoves);
    }
    @Test
    void blackKingNoCastlePathUnderCheck() {
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_BLACK_KING_NO_CASTLE_PATH_UNDER_CHECK));
        Collection<Move> blackKingLegalMoves = new ArrayList<>();
        blackKingLegalMoves.add(new StandardMove(new Position(0,4), new Position(0,5)));
        blackKingLegalMoves.add(new StandardMove(new Position(0,4), new Position(1,5)));
        blackKingLegalMoves.add(new StandardMove(new Position(0,4), new Position(1,4)));
        Collection<Move> computedKingMoves = gameState.getLegalMovesForPieceAtPosition(new Position(0, 4));
        assertTrue(blackKingLegalMoves.size() == computedKingMoves.size() &&
                blackKingLegalMoves.containsAll(computedKingMoves) &&
                computedKingMoves.containsAll(blackKingLegalMoves), "The expected list of moves: " + blackKingLegalMoves +
                " is not the same as the produced one: " + computedKingMoves);
    }
    @Test
    void whiteKingNoCastlePathUnderCheck() {
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_WHITE_KING_NO_CASTLE_PATH_UNDER_CHECK));
        Collection<Move> whiteKingLegalMoves = new ArrayList<>();
        whiteKingLegalMoves.add(new StandardMove(new Position(7,4), new Position(7,3)));
        whiteKingLegalMoves.add(new StandardMove(new Position(7,4), new Position(6,3)));
        whiteKingLegalMoves.add(new StandardMove(new Position(7,4), new Position(6,5)));
        Collection<Move> computedKingMoves = gameState.getLegalMovesForPieceAtPosition(new Position(7, 4));
        assertTrue(whiteKingLegalMoves.size() == computedKingMoves.size() &&
                whiteKingLegalMoves.containsAll(computedKingMoves) &&
                computedKingMoves.containsAll(whiteKingLegalMoves), "The expected list of moves: " + whiteKingLegalMoves +
                " is not the same as the produced one: " + computedKingMoves);
    }
    @Test
    void blackKingNoCastleUnderCheck() {
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_BLACK_KING_NO_CASTLE_UNDER_CHECK));
        Collection<Move> blackKingLegalMoves = new ArrayList<>();
        blackKingLegalMoves.add(new StandardMove(new Position(0,4), new Position(0,5)));
        blackKingLegalMoves.add(new StandardMove(new Position(0,4), new Position(0,3)));
        blackKingLegalMoves.add(new StandardMove(new Position(0,4), new Position(1,5)));
        blackKingLegalMoves.add(new StandardMove(new Position(0,4), new Position(1,3)));
        Collection<Move> computedKingMoves = gameState.getLegalMovesForPieceAtPosition(new Position(0, 4));
        assertTrue(blackKingLegalMoves.size() == computedKingMoves.size() &&
                blackKingLegalMoves.containsAll(computedKingMoves) &&
                computedKingMoves.containsAll(blackKingLegalMoves), "The expected list of moves: " + blackKingLegalMoves +
                " is not the same as the produced one: " + computedKingMoves);
    }
    @Test
    void whiteKingNoCastleUnderCheck() {
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_WHITE_KING_NO_CASTLE_UNDER_CHECK));
        Collection<Move> whiteKingLegalMoves = new ArrayList<>();
        whiteKingLegalMoves.add(new StandardMove(new Position(7,4), new Position(7,3)));
        whiteKingLegalMoves.add(new StandardMove(new Position(7,4), new Position(7,5)));
        whiteKingLegalMoves.add(new StandardMove(new Position(7,4), new Position(6,3)));
        whiteKingLegalMoves.add(new StandardMove(new Position(7,4), new Position(6,5)));
        Collection<Move> computedKingMoves = gameState.getLegalMovesForPieceAtPosition(new Position(7, 4));
        assertTrue(whiteKingLegalMoves.size() == computedKingMoves.size() &&
                whiteKingLegalMoves.containsAll(computedKingMoves) &&
                computedKingMoves.containsAll(whiteKingLegalMoves), "The expected list of moves: " + whiteKingLegalMoves +
                " is not the same as the produced one: " + computedKingMoves);
    }
    @Test
    void verticallyPinnedBlackRook() {
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_BLACK_ROOK_NON_ADJACENT_TO_KING_VERTICALLY_PINNED_BY_WHITE_ROOK));
        Collection<Move> blackRookLegalMoves = new ArrayList<>();
        blackRookLegalMoves.add(new StandardMove(new Position(2,4), new Position(1,4)));
        blackRookLegalMoves.add(new StandardMove(new Position(2,4), new Position(3,4)));
        blackRookLegalMoves.add(new StandardMove(new Position(2,4), new Position(4,4)));
        blackRookLegalMoves.add(new StandardMove(new Position(2,4), new Position(5,4)));
        blackRookLegalMoves.add(new StandardMove(new Position(2,4), new Position(6,4)));
        blackRookLegalMoves.add(new StandardMove(new Position(2,4), new Position(7,4)));
        Collection<Move> computedRookMoves = gameState.getLegalMovesForPieceAtPosition(new Position(2, 4));
        assertTrue(blackRookLegalMoves.size() == computedRookMoves.size() &&
                blackRookLegalMoves.containsAll(computedRookMoves) &&
                computedRookMoves.containsAll(blackRookLegalMoves), "The expected list of moves: " + blackRookLegalMoves +
                " is not the same as the produced one: " + computedRookMoves);
    }

    @Test
    void verticallyPinnedBlackRookAdjacentToKing() {
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_BLACK_ROOK_ADJACENT_TO_KING_VERTICALLY_PINNED_BY_WHITE_ROOK));
        Collection<Move> blackRookLegalMoves = new ArrayList<>();
        blackRookLegalMoves.add(new StandardMove(new Position(1,4), new Position(2,4)));
        blackRookLegalMoves.add(new StandardMove(new Position(1,4), new Position(3,4)));
        blackRookLegalMoves.add(new StandardMove(new Position(1,4), new Position(4,4)));
        blackRookLegalMoves.add(new StandardMove(new Position(1,4), new Position(5,4)));
        blackRookLegalMoves.add(new StandardMove(new Position(1,4), new Position(6,4)));
        blackRookLegalMoves.add(new StandardMove(new Position(1,4), new Position(7,4)));
        Collection<Move> computedRookMoves = gameState.getLegalMovesForPieceAtPosition(new Position(1, 4));
        assertTrue(blackRookLegalMoves.size() == computedRookMoves.size() &&
                blackRookLegalMoves.containsAll(computedRookMoves) &&
                computedRookMoves.containsAll(blackRookLegalMoves), "The expected list of moves: " + blackRookLegalMoves +
                " is not the same as the produced one: " + computedRookMoves);
    }
    @Test
    void horizontallyPinnedBlackRookAdjacentToKing() {
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_BLACK_ROOK_ADJACENT_TO_KING_HORIZONTALLY_PINNED_BY_WHITE_ROOK));
        Collection<Move> blackRookLegalMoves = new ArrayList<>();
        blackRookLegalMoves.add(new StandardMove(new Position(1,2), new Position(1,3)));
        blackRookLegalMoves.add(new StandardMove(new Position(1,2), new Position(1,4)));
        blackRookLegalMoves.add(new StandardMove(new Position(1,2), new Position(1,5)));
        blackRookLegalMoves.add(new StandardMove(new Position(1,2), new Position(1,6)));
        blackRookLegalMoves.add(new StandardMove(new Position(1,2), new Position(1,7)));
        Collection<Move> computedRookMoves = gameState.getLegalMovesForPieceAtPosition(new Position(1, 2));
        assertTrue(blackRookLegalMoves.size() == computedRookMoves.size() &&
                blackRookLegalMoves.containsAll(computedRookMoves) &&
                computedRookMoves.containsAll(blackRookLegalMoves), "The expected list of moves: " + blackRookLegalMoves +
                " is not the same as the produced one: " + computedRookMoves);
    }
    @Test
    void horizontallyPinnedBlackRookNonAdjacentToKing() {
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_BLACK_ROOK_NON_ADJACENT_TO_KING_HORIZONTALLY_PINNED_BY_WHITE_ROOK));
        Collection<Move> blackRookLegalMoves = new ArrayList<>();
        blackRookLegalMoves.add(new StandardMove(new Position(1,3), new Position(1,2)));
        blackRookLegalMoves.add(new StandardMove(new Position(1,3), new Position(1,4)));
        blackRookLegalMoves.add(new StandardMove(new Position(1,3), new Position(1,5)));
        blackRookLegalMoves.add(new StandardMove(new Position(1,3), new Position(1,6)));
        blackRookLegalMoves.add(new StandardMove(new Position(1,3), new Position(1,7)));
        Collection<Move> computedRookMoves = gameState.getLegalMovesForPieceAtPosition(new Position(1, 3));
        assertTrue(blackRookLegalMoves.size() == computedRookMoves.size() &&
                blackRookLegalMoves.containsAll(computedRookMoves) &&
                computedRookMoves.containsAll(blackRookLegalMoves), "The expected list of moves: " + blackRookLegalMoves +
                " is not the same as the produced one: " + computedRookMoves);
    }
    @Test
    void blackBishopPinnedByWhiteBishop() {
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_BLACK_BISHOP_PINNED_BY_WHITE_BISHOP));
        Collection<Move> blackBishopLegalMoves = new ArrayList<>();
        blackBishopLegalMoves.add(new StandardMove(new Position(2,4), new Position(1,3)));
        blackBishopLegalMoves.add(new StandardMove(new Position(2,4), new Position(3,5)));
        blackBishopLegalMoves.add(new StandardMove(new Position(2,4), new Position(4,6)));
        blackBishopLegalMoves.add(new StandardMove(new Position(2,4), new Position(5,7)));
        Collection<Move> computedBishopMoves = gameState.getLegalMovesForPieceAtPosition(new Position(2, 4));
        assertTrue(blackBishopLegalMoves.size() == computedBishopMoves.size() &&
                blackBishopLegalMoves.containsAll(computedBishopMoves) &&
                computedBishopMoves.containsAll(blackBishopLegalMoves), "The expected list of moves: " + blackBishopLegalMoves +
                " is not the same as the produced one: " + computedBishopMoves);
    }
    @Test
    void blackBishopNotPinnedByWhiteBishopBecauseOfInterposingKnight() {
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_BLACK_BISHOP_NOT_PINNED_BY_WHITE_BISHOP_BECAUSE_OF_INTERPOSING_KNIGHT));
        Collection<Move> blackBishopLegalMoves = new ArrayList<>();
        blackBishopLegalMoves.add(new StandardMove(new Position(2,4), new Position(1,3)));
        blackBishopLegalMoves.add(new StandardMove(new Position(2,4), new Position(3,5)));
        blackBishopLegalMoves.add(new StandardMove(new Position(2,4), new Position(1,5)));
        blackBishopLegalMoves.add(new StandardMove(new Position(2,4), new Position(0,6)));
        blackBishopLegalMoves.add(new StandardMove(new Position(2,4), new Position(3,3)));
        blackBishopLegalMoves.add(new StandardMove(new Position(2,4), new Position(4,2)));
        blackBishopLegalMoves.add(new StandardMove(new Position(2,4), new Position(5,1)));
        blackBishopLegalMoves.add(new StandardMove(new Position(2,4), new Position(6,0)));

        Collection<Move> computedBishopMoves = gameState.getLegalMovesForPieceAtPosition(new Position(2, 4));
        assertTrue(blackBishopLegalMoves.size() == computedBishopMoves.size() &&
                blackBishopLegalMoves.containsAll(computedBishopMoves) &&
                computedBishopMoves.containsAll(blackBishopLegalMoves), "The expected list of moves: " + blackBishopLegalMoves +
                " is not the same as the produced one: " + computedBishopMoves);
    }
    @Test
    void whiteBishopPinnedByBlackBishop() {
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_WHITE_BISHOP_PINNED_BY_BLACK_BISHOP));
        Collection<Move> whiteBishopLegalMoves = new ArrayList<>();
        whiteBishopLegalMoves.add(new StandardMove(new Position(5,6), new Position(1,2)));
        whiteBishopLegalMoves.add(new StandardMove(new Position(5,6), new Position(2,3)));
        whiteBishopLegalMoves.add(new StandardMove(new Position(5,6), new Position(3,4)));
        whiteBishopLegalMoves.add(new StandardMove(new Position(5,6), new Position(4,5)));
        Collection<Move> computedBishopMoves = gameState.getLegalMovesForPieceAtPosition(new Position(5, 6));
        assertTrue(whiteBishopLegalMoves.size() == computedBishopMoves.size() &&
                whiteBishopLegalMoves.containsAll(computedBishopMoves) &&
                computedBishopMoves.containsAll(whiteBishopLegalMoves), "The expected list of moves: " + whiteBishopLegalMoves +
                " is not the same as the produced one: " + computedBishopMoves);
    }

    @Test
    void whiteKnightPinnedByBlackRookOnRow() {
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_WHITE_KNIGHT_PINNED_BY_BLACK_ROOK_ON_ROW));
        Collection<Move> computedKnightMoves = gameState.getLegalMovesForPieceAtPosition(new Position(3, 3));
        assertTrue(computedKnightMoves.isEmpty(), "Knight should have no moves.");
    }
    @Test
    void blackKnightPinnedByWhiteQueenOnColumn() {
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_BLACK_KNIGHT_PINNED_BY_WHITE_QUEEN_ON_COLUMN));
        Collection<Move> computedKnightMoves = gameState.getLegalMovesForPieceAtPosition(new Position(3, 1));
        assertTrue(computedKnightMoves.isEmpty(), "Knight should have no moves.");
    }
    @Test
    void whiteKnightPinnedByBlackQueenOnDiagonal() {
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_WHITE_KNIGHT_PINNED_BY_BLACK_QUEEN_ON_DIAGONAL));
        Collection<Move> computedKnightMoves = gameState.getLegalMovesForPieceAtPosition(new Position(3, 1));
        assertTrue(computedKnightMoves.isEmpty(), "Knight should have no moves.");
    }
    @Test
    void blackPawnPinnedByWhiteQueenOnDiagonal() {
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_BLACK_PAWN_PINNED_BY_WHITE_QUEEN_ON_DIAGONAL));
        Collection<Move> computedPawnMoves = gameState.getLegalMovesForPieceAtPosition(new Position(1, 3));
        assertTrue(computedPawnMoves.isEmpty(), "Pawn should have no moves.");
    }
    @Test
    void blackPawnPinnedByWhiteQueenOnRow() {
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_BLACK_PAWN_PINNED_BY_WHITE_QUEEN_ON_ROW));
        Collection<Move> computedPawnMoves = gameState.getLegalMovesForPieceAtPosition(new Position(1, 3));
        assertTrue(computedPawnMoves.isEmpty(), "Pawn should have no moves.");
    }
    @Test
    void whitePawnPinnedNoEnPassantCapture() {
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_WHITE_PAWN_PINNED_NO_EN_PASSANT));
        Collection<Move> whitePawnLegalMoves = new ArrayList<>();
        whitePawnLegalMoves.add(new StandardMove(new Position(3,3), new Position(2,3)));
        Collection<Move> computedPawnMoves = gameState.getLegalMovesForPieceAtPosition(new Position(3, 3));
        assertTrue(whitePawnLegalMoves.size() == computedPawnMoves.size() &&
                whitePawnLegalMoves.containsAll(computedPawnMoves) &&
                computedPawnMoves.containsAll(whitePawnLegalMoves), "The expected list of moves: " + whitePawnLegalMoves +
                " is not the same as the produced one: " + computedPawnMoves);
    }
    @Test
    void whitePawnsNotPinnedByBlackQueen() {
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_WHITE_PAWNS_NOT_PINNED_BY_BLACK_QUEEN));
        Collection<Move> whitePawnC3LegalMoves = new ArrayList<>();
        whitePawnC3LegalMoves.add(new StandardMove(new Position(5,2), new Position(4,2)));
        Collection<Move> computedPawnC3Moves = gameState.getLegalMovesForPieceAtPosition(new Position(5, 2));
        assertTrue(whitePawnC3LegalMoves.size() == computedPawnC3Moves.size() &&
                whitePawnC3LegalMoves.containsAll(computedPawnC3Moves) &&
                computedPawnC3Moves.containsAll(whitePawnC3LegalMoves), "The expected list of moves for pawn in c3: " + whitePawnC3LegalMoves +
                " is not the same as the produced one: " + computedPawnC3Moves);
        Collection<Move> whitePawnD2LegalMoves = new ArrayList<>();
        whitePawnD2LegalMoves.add(new StandardMove(new Position(6,3), new Position(5,3)));
        whitePawnD2LegalMoves.add(new DoublePawnMove(new Position(6,3), new Position(4,3)));
        Collection<Move> computedPawnD2Moves = gameState.getLegalMovesForPieceAtPosition(new Position(6, 3));
        assertTrue(whitePawnD2LegalMoves.size() == computedPawnD2Moves.size() &&
                whitePawnD2LegalMoves.containsAll(computedPawnD2Moves) &&
                computedPawnD2Moves.containsAll(whitePawnD2LegalMoves), "The expected list of moves for pawn in d2: " + whitePawnC3LegalMoves +
                " is not the same as the produced one: " + computedPawnD2Moves);
    }
    @Test
    void blackKingInDoubleCheckedWithDiscoveryAndMateByWhiteKnight() {
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_KING_DOUBLE_CHECKED_AND_MATED_BY_WHITE_KNIGHT));
        Collection<Move> computedKingMoves = gameState.getLegalMovesForPieceAtPosition(new Position(0, 7));
        assertTrue(computedKingMoves.isEmpty(), "King should have no moves.");
        Collection<Move> legalMovesforColor = gameState.getLegalMovesForColor(gameState.getCurrentColor());
        gameState.checkGameEnd(legalMovesforColor);
        assertTrue(gameState.isGameOver(), "Game should be over.");
        assertTrue(chessboard.isInCheck(PlayerColor.BLACK), "Black should be in check.");
        assertEquals(gameState.getGameResult().winner(), PlayerColor.WHITE, "Winner should be white.");
    }
    @Test
    void whiteKnightTwoPossibleMovesOneIsDoubleCheckAndDiscovery() {
        ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_KNIGHT_DOUBLE_CHECKS_AND_MATES_WHITE_KING));
        Collection<Move> computedKnightMoves = gameState.getLegalMovesForPieceAtPosition(new Position(0, 5));
        computedKnightMoves.add(new StandardMove(new Position(0,5), new Position(1,7)));
        computedKnightMoves.add(new StandardMove(new Position(0,5), new Position(2,6)));
        assertEquals(2, computedKnightMoves.size(), "Knight should have 2 moves.");
    }

}
