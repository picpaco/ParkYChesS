package com.marcopiccionitraining.parkychess;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.DoublePawnMove;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import com.marcopiccionitraining.parkychess.model.moves.StandardMove;
import com.marcopiccionitraining.parkychess.model.pieces.King;
import com.marcopiccionitraining.parkychess.model.pieces.PieceNames;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static com.marcopiccionitraining.parkychess.model.FENPositions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class KingTests {
    private MoveGeneratorStatistics stats;
    private GameStateContainer gameStateContainer;
    private PerftUtils ptu;


    @BeforeEach
    void setup(){
        stats = new MoveGeneratorStatistics();
        gameStateContainer = new GameStateContainer(Board.MAX_CONTAINER_SIZE);

    }

    @Test
    void whiteKingInitialPositionHasNotMovedYet(){
        ChessGame gameState = new ChessGame(FEN_INITIAL_POSITION);
        King whiteKing = (King) gameState.getChessboard().getPiece (7,4);
        assertFalse(whiteKing.isFlaggedAsHavingAlreadyMoved(),"King should not be flagged as having already moved");
    }

    @Test
    void blackKingInitialPositionHasNotMovedYet(){
        ChessGame gameState = new ChessGame(FEN_INITIAL_POSITION);
        King blackKing = (King) gameState.getChessboard().getPiece (0,4);
        assertFalse(blackKing.isFlaggedAsHavingAlreadyMoved(),"King should not be flagged as having already moved");
    }

    @Test
    void whiteKingHasNotMovedAfterOneMoveWithUndoDepth_1(){
        ChessGame gameState = new ChessGame(FEN_KINGS_ONLY);
        ptu = new PerftUtils(gameState);
        King whiteKing = (King) gameState.getChessboard().getPiece (7,4);
        assertFalse(whiteKing.isFlaggedAsHavingAlreadyMoved(),"Initially, the king should not be flagged as having already moved.");
        long result = ptu.perft(1, 1, stats);
        assertFalse(whiteKing.isFlaggedAsHavingAlreadyMoved(),"After all the moves have been undone, " +
                "the white king should not be flagged as having already moved.");
        assertEquals(5, result, "There should be 5 legal positions (leaf nodes).");
    }

    @Test
    void kingsHaveNotMovedAfterTwoMovesWithUndoDepth_2(){
        ChessGame gameState = new ChessGame(FEN_KINGS_ONLY);
        ptu = new PerftUtils(gameState);
        King whiteKing = (King) gameState.getChessboard().getPiece (7,4);
        King blackKing = (King) gameState.getChessboard().getPiece (0,4);
        assertFalse(whiteKing.isFlaggedAsHavingAlreadyMoved(),"Initially, the white king should not be flagged as having already moved.");
        assertFalse(blackKing.isFlaggedAsHavingAlreadyMoved(),"Initially, the black king should not be flagged as having already moved.");
        long result = ptu.perft(2, 2, stats);
        assertFalse(whiteKing.isFlaggedAsHavingAlreadyMoved(),"After all the moves have been undone, " +
                "the white king should not be flagged as having already moved.");
        assertFalse(blackKing.isFlaggedAsHavingAlreadyMoved(),"After all the moves have been undone, " +
                "the black king should not be flagged as having already moved.");
        assertEquals(25, result, "There should be 25 legal positions (leaf nodes).");
    }

    @Test
    void whiteKkingHasNotMovedAfterThreeMovesWithUndoDepth_3(){
        ChessGame gameState = new ChessGame(FEN_KINGS_ONLY);
        ptu = new PerftUtils(gameState);
        King whiteKing = (King) gameState.getChessboard().getPiece (7,4);
        King blackKing = (King) gameState.getChessboard().getPiece (0,4);
        assertFalse(whiteKing.isFlaggedAsHavingAlreadyMoved(),"Initially, the white king should not be flagged as having already moved.");
        assertFalse(blackKing.isFlaggedAsHavingAlreadyMoved(),"Initially, the black king should not be flagged as having already moved.");
        long result = ptu.perft(3, 3, stats);
        assertFalse(whiteKing.isFlaggedAsHavingAlreadyMoved(),"After all the moves have been undone, " +
                "the white king should not be flagged as having already moved.");
        assertFalse(blackKing.isFlaggedAsHavingAlreadyMoved(),"After all the moves have been undone, " +
                "the black king should not be flagged as having already moved.");
        assertEquals(170, result, "There should be 170 legal positions (leaf nodes).");
    }

    @Test
    void kingsHaveNotMovedAfterFourMovesWithUndoDepth_4(){
        ChessGame gameState = new ChessGame(FEN_KINGS_ONLY);
        ptu = new PerftUtils(gameState);
        King whiteKing = (King) gameState.getChessboard().getPiece (7,4);
        King blackKing = (King) gameState.getChessboard().getPiece (0,4);
        assertFalse(whiteKing.isFlaggedAsHavingAlreadyMoved(),"Initially, the white king should not be flagged as having already moved.");
        assertFalse(blackKing.isFlaggedAsHavingAlreadyMoved(),"Initially, the black king should not be flagged as having already moved.");
        long result = ptu.perft(4, 4, stats);
        assertFalse(whiteKing.isFlaggedAsHavingAlreadyMoved(),"After all the moves have been undone, " +
                "the white king should not be flagged as having already moved.");
        assertFalse(blackKing.isFlaggedAsHavingAlreadyMoved(),"After all the moves have been undone, " +
                "the black king should not be flagged as having already moved.");
        //TODO: fix the expected result with Stockfish
        assertEquals(1156, result, "There should be 1156 legal positions (leaf nodes).");
    }

    @Test
    void whiteKingHasNotMovedAfterThreeMovesWithUndoDepth_3(){
        ChessGame gameState = new ChessGame(FEN_KINGS_ONLY);
        ptu = new PerftUtils(gameState);
        King whiteKing = (King) gameState.getChessboard().getPiece (7,4);
        assertFalse(whiteKing.isFlaggedAsHavingAlreadyMoved(),"Initially, the king should not be flagged as having already moved.");
        long result = ptu.perft(3, 3, stats);
        assertFalse(whiteKing.isFlaggedAsHavingAlreadyMoved(),"After all the moves have been undone, " +
                "the white king should not be flagged as having already moved.");
        assertEquals(170, result, "There should be 170 legal positions (leaf nodes).");
    }

    @Test
    void whiteKingNameCheck(){
        ChessGame gameState = new ChessGame(FEN_INITIAL_POSITION);
        King whiteKing = (King) gameState.getChessboard().getPiece (7,4);
        assertEquals(PlayerColor.WHITE, whiteKing.getColor(), "King at square e1 should be white.");
        assertEquals(PieceNames.KING, whiteKing.getName(), "King's name should be 'KING'");
        assertEquals("Ke1", whiteKing.toString(), "King's name as string should be 'Ke1'");
    }
    @Test
    void blackKingNameCheck(){
        ChessGame gameState = new ChessGame(FEN_INITIAL_POSITION);
        King blackKing = (King) gameState.getChessboard().getPiece (0,4);
        assertEquals(PlayerColor.BLACK, blackKing.getColor(), "King at square e8 should be black.");
        assertEquals(PieceNames.KING, blackKing.getName(), "King's name should be 'KING'");
        assertEquals("ke8", blackKing.toString(), "King's name as string should be 'ke8'");
    }
    @Test
    void blackKingCannotMoveAtStartingPosition() {
        ChessGame gameState = new ChessGame(FEN_INITIAL_POSITION);
        gameState.getChessboard().setCurrentPlayerColor(PlayerColor.BLACK);
        assertTrue(gameState.getLegalMovesForPieceAtPosition(new Position(0, 4)).isEmpty());
    }
    @Test
    void whiteKingCannotMoveAtStartingPosition() {
        ChessGame gameState = new ChessGame(FEN_INITIAL_POSITION);
        assertTrue(gameState.getLegalMovesForPieceAtPosition(new Position(7, 4)).isEmpty());
    }
    @Test
    void blackKingLegalMovesBishopCheck() {
        ChessGame gameState = new ChessGame(FEN_KING_BISHOP_CHECK_POSITION);
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
        ChessGame gameState = new ChessGame(FEN_KING_BISHOP_CHECK_INTERPOSE_POSITION);
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
        ChessGame gameState = new ChessGame(FEN_BLACK_KING_WHITE_ROOK_CHECK_NO_QUEENSIDE_CASTLE_POSITION);
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
        ChessGame gameState = new ChessGame(FEN_BLACK_KING_WHITE_ROOK_CHECK_NO_KINGSIDE_CASTLE_POSITION);
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
        ChessGame gameState = new ChessGame(FEN_WHITE_KING_BLACK_ROOK_CHECK_NO_QUEENSIDE_CASTLE_POSITION);
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
        ChessGame gameState = new ChessGame(FEN_WHITE_KING_BLACK_ROOK_CHECK_NO_KINGSIDE_CASTLE_POSITION);
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
        ChessGame gameState = new ChessGame(FEN_WHITE_KING_NO_QUEENSIDE_CASTLE_WITH_INTERPOSING_FRIENDLY_PIECE);
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
        ChessGame gameState = new ChessGame(FEN_BLACK_KING_NO_QUEENSIDE_CASTLE_WITH_INTERPOSING_FRIENDLY_PIECE);
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
        ChessGame gameState = new ChessGame(FEN_WHITE_KING_NO_KINGSIDE_CASTLE_WITH_INTERPOSING_FRIENDLY_PIECE);
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
        ChessGame gameState = new ChessGame(FEN_BLACK_KING_NO_KINGSIDE_CASTLE_WITH_INTERPOSING_FRIENDLY_PIECE);
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
        ChessGame gameState = new ChessGame(FEN_BLACK_KING_NO_CASTLE_PATH_UNDER_CHECK);
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
        ChessGame gameState = new ChessGame(FEN_WHITE_KING_NO_CASTLE_PATH_UNDER_CHECK);
        log.warn("Current position: {}", gameState.getChessboard());
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
        ChessGame gameState = new ChessGame(FEN_BLACK_KING_NO_CASTLE_UNDER_CHECK);
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
        ChessGame gameState = new ChessGame(FEN_WHITE_KING_NO_CASTLE_UNDER_CHECK);
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
        ChessGame gameState = new ChessGame(FEN_BLACK_ROOK_NON_ADJACENT_TO_KING_VERTICALLY_PINNED_BY_WHITE_ROOK);
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
        ChessGame gameState = new ChessGame(FEN_BLACK_ROOK_ADJACENT_TO_KING_VERTICALLY_PINNED_BY_WHITE_ROOK);
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
        ChessGame gameState = new ChessGame(FEN_BLACK_ROOK_ADJACENT_TO_KING_HORIZONTALLY_PINNED_BY_WHITE_ROOK);
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
        ChessGame gameState = new ChessGame(FEN_BLACK_ROOK_NON_ADJACENT_TO_KING_HORIZONTALLY_PINNED_BY_WHITE_ROOK);
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
        ChessGame gameState = new ChessGame(FEN_BLACK_BISHOP_PINNED_BY_WHITE_BISHOP);
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
        ChessGame gameState = new ChessGame(FEN_BLACK_BISHOP_NOT_PINNED_BY_WHITE_BISHOP_BECAUSE_OF_INTERPOSING_KNIGHT);
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
        ChessGame gameState = new ChessGame(FEN_WHITE_BISHOP_PINNED_BY_BLACK_BISHOP);
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
        ChessGame gameState = new ChessGame(FEN_WHITE_KNIGHT_PINNED_BY_BLACK_ROOK_ON_ROW);
        Collection<Move> computedKnightMoves = gameState.getLegalMovesForPieceAtPosition(new Position(3, 3));
        assertTrue(computedKnightMoves.isEmpty(), "Knight should have no moves.");
    }
    @Test
    void blackKnightPinnedByWhiteQueenOnColumn() {
        ChessGame gameState = new ChessGame(FEN_BLACK_KNIGHT_PINNED_BY_WHITE_QUEEN_ON_COLUMN);
        Collection<Move> computedKnightMoves = gameState.getLegalMovesForPieceAtPosition(new Position(3, 1));
        assertTrue(computedKnightMoves.isEmpty(), "Knight should have no moves.");
    }
    @Test
    void whiteKnightPinnedByBlackQueenOnDiagonal() {
        ChessGame gameState = new ChessGame(FEN_WHITE_KNIGHT_PINNED_BY_BLACK_QUEEN_ON_DIAGONAL);
        Collection<Move> computedKnightMoves = gameState.getLegalMovesForPieceAtPosition(new Position(3, 1));
        assertTrue(computedKnightMoves.isEmpty(), "Knight should have no moves.");
    }
    @Test
    void blackPawnPinnedByWhiteQueenOnDiagonal() {
        ChessGame gameState = new ChessGame(FEN_BLACK_PAWN_PINNED_BY_WHITE_QUEEN_ON_DIAGONAL);
        Collection<Move> computedPawnMoves = gameState.getLegalMovesForPieceAtPosition(new Position(1, 3));
        assertTrue(computedPawnMoves.isEmpty(), "Pawn should have no moves.");
    }
    @Test
    void blackPawnPinnedByWhiteQueenOnRow() {
        ChessGame gameState = new ChessGame(FEN_BLACK_PAWN_PINNED_BY_WHITE_QUEEN_ON_ROW);
        Collection<Move> computedPawnMoves = gameState.getLegalMovesForPieceAtPosition(new Position(1, 3));
        assertTrue(computedPawnMoves.isEmpty(), "Pawn should have no moves.");
    }
    @Test
    void whitePawnPinnedNoEnPassantCapture() {
        ChessGame gameState = new ChessGame(FEN_WHITE_PAWN_PINNED_NO_EN_PASSANT);
        log.debug("Initial position: {}", gameState.getChessboard());
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
        ChessGame gameState = new ChessGame(FEN_WHITE_PAWNS_NOT_PINNED_BY_BLACK_QUEEN);
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
        ChessGame gameState = new ChessGame(FEN_KING_DOUBLE_CHECKED_AND_MATED_BY_WHITE_KNIGHT);
        log.debug("Position: {}", gameState.getChessboard());
        Collection<Move> computedKingMoves = gameState.getLegalMovesForPieceAtPosition(new Position(0, 7));
        assertTrue(computedKingMoves.isEmpty(), "King should have no moves.");
        Collection<Move> legalMovesforColor = gameState.getLegalMovesForColor(gameState.getChessboard().getCurrentPlayerColor());
        assertTrue(legalMovesforColor.isEmpty(), "Black should have no moves.");
        assertTrue(gameState.getChessboard().isPlayerInCheck(PlayerColor.BLACK), "Black should be in check.");
        assertTrue(gameState.getChessboard().isInDoubleCheck(PlayerColor.BLACK), "Black should be in double check.");
        gameState.checkGameEnd(legalMovesforColor);
        assertTrue(gameState.isGameOver(), "Game should be over.");
        assertEquals(GameEndReason.CHECKMATE, gameState.getGameResult().gameEndReason(), "A checkmate should be on the board.");
        assertEquals(PlayerColor.WHITE, gameState.getGameResult().winner(), "Winner should be white.");
    }
    @Test
    void whiteKnightTwoPossibleMovesOneIsDoubleCheckAndDiscovery() {
        ChessGame gameState = new ChessGame(FEN_KNIGHT_DOUBLE_CHECKS_AND_MATES_WHITE_KING);
        Collection<Move> computedKnightMoves = gameState.getLegalMovesForPieceAtPosition(new Position(0, 5));
        computedKnightMoves.add(new StandardMove(new Position(0,5), new Position(1,7)));
        computedKnightMoves.add(new StandardMove(new Position(0,5), new Position(2,6)));
        assertEquals(2, computedKnightMoves.size(), "Knight should have 2 moves.");
    }

//TODO The following test dictates the app behavior in presence of a checker when the checker's color has the move.
    @Test
    void whiteRookInitialCheckAndWhiteMoves() {
        assertThrows(AssertionError.class, () -> {
            ChessGame gameState = new ChessGame(FEN_WHITE_ROOK_INITIAL_CHECKER);
            assertTrue(gameState.getChessboard().isPlayerInCheck(PlayerColor.WHITE), "White player should be in check");
        }, "We are expecting an error.");
    }
}
