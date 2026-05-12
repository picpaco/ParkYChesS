package com.marcopiccionitraining.parkychess;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.*;
import com.marcopiccionitraining.parkychess.model.pieces.King;
import com.marcopiccionitraining.parkychess.model.pieces.PieceNames;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.marcopiccionitraining.parkychess.model.FENPositions.*;
import static org.junit.jupiter.api.Assertions.*;

import static com.marcopiccionitraining.parkychess.model.FENPositions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class KingTests {
    private MoveGeneratorStatistics stats;
    private PerftUtils ptu;


    @BeforeEach
    void setup() {
        stats = new MoveGeneratorStatistics();
    }

    @Test
    void whiteKingInitialPositionHasNotMovedYet() {
        ChessGame gameState = new ChessGame(FEN_INITIAL_POSITION);
        King whiteKing = (King) gameState.getChessboard().getPiece(7, 4);
        assertFalse(whiteKing.isFlaggedAsHavingAlreadyMoved(), "King should not be flagged as having already moved");
    }

    @Test
    void blackKingInitialPositionHasNotMovedYet() {
        ChessGame gameState = new ChessGame(FEN_INITIAL_POSITION);
        King blackKing = (King) gameState.getChessboard().getPiece(0, 4);
        assertFalse(blackKing.isFlaggedAsHavingAlreadyMoved(), "King should not be flagged as having already moved");
    }

    @Test
    void whiteKingHasNotMovedAfterOneMoveWithUndoDepth_1() {
        ChessGame gameState = new ChessGame(FEN_KINGS_ONLY);
        ptu = new PerftUtils(gameState);
        King whiteKing = (King) gameState.getChessboard().getPiece(7, 4);
        assertFalse(whiteKing.isFlaggedAsHavingAlreadyMoved(), "Initially, the king should not be flagged as having already moved.");
        long result = ptu.perft(1, 1, stats);
        assertEquals(5, result, "There should be 5 legal positions (leaf nodes).");
        assertFalse(whiteKing.isFlaggedAsHavingAlreadyMoved(), "After all the moves have been undone, " +
                "the white king should not be flagged as having already moved.");
    }

    @Test
    void kingsHaveNotMovedAfterTwoMovesWithUndoDepth_2() {
        ChessGame gameState = new ChessGame(FEN_KINGS_ONLY);
        ptu = new PerftUtils(gameState);
        King whiteKing = (King) gameState.getChessboard().getPiece(7, 4);
        King blackKing = (King) gameState.getChessboard().getPiece(0, 4);
        assertFalse(whiteKing.isFlaggedAsHavingAlreadyMoved(), "Initially, the white king should not be flagged as having already moved.");
        assertFalse(blackKing.isFlaggedAsHavingAlreadyMoved(), "Initially, the black king should not be flagged as having already moved.");
        long result = ptu.perft(2, 2, stats);
        assertFalse(whiteKing.isFlaggedAsHavingAlreadyMoved(), "After all the moves have been undone, " +
                "the white king should not be flagged as having already moved.");
        assertFalse(blackKing.isFlaggedAsHavingAlreadyMoved(), "After all the moves have been undone, " +
                "the black king should not be flagged as having already moved.");
        assertEquals(25, result, "There should be 25 legal positions (leaf nodes).");
    }

    @Test
    void kingsHaveNotMovedAfterThreeMovesWithUndoDepth_3() {
        ChessGame gameState = new ChessGame(FEN_KINGS_ONLY);
        ptu = new PerftUtils(gameState);
        King whiteKing = (King) gameState.getChessboard().getPiece(7, 4);
        King blackKing = (King) gameState.getChessboard().getPiece(0, 4);
        assertFalse(whiteKing.isFlaggedAsHavingAlreadyMoved(), "Initially, the white king should not be flagged as having already moved.");
        assertFalse(blackKing.isFlaggedAsHavingAlreadyMoved(), "Initially, the black king should not be flagged as having already moved.");
        long result = ptu.perft(3, 3, stats);
        assertFalse(whiteKing.isFlaggedAsHavingAlreadyMoved(), "After all the moves have been undone, " +
                "the white king should not be flagged as having already moved.");
        assertFalse(blackKing.isFlaggedAsHavingAlreadyMoved(), "After all the moves have been undone, " +
                "the black king should not be flagged as having already moved.");
        assertEquals(170, result, "There should be 170 legal positions (leaf nodes).");
    }

    @Test
    void kingsHaveNotMovedAfterFourMovesWithUndoDepth_4() {
        ChessGame gameState = new ChessGame(FEN_KINGS_ONLY);
        ptu = new PerftUtils(gameState);
        King whiteKing = (King) gameState.getChessboard().getPiece(7, 4);
        King blackKing = (King) gameState.getChessboard().getPiece(0, 4);
        assertFalse(whiteKing.isFlaggedAsHavingAlreadyMoved(), "Initially, the white king should not be flagged as having already moved.");
        assertFalse(blackKing.isFlaggedAsHavingAlreadyMoved(), "Initially, the black king should not be flagged as having already moved.");
        long result = ptu.perft(4, 4, stats);
        assertFalse(whiteKing.isFlaggedAsHavingAlreadyMoved(), "After all the moves have been undone, " +
                "the white king should not be flagged as having already moved.");
        assertFalse(blackKing.isFlaggedAsHavingAlreadyMoved(), "After all the moves have been undone, " +
                "the black king should not be flagged as having already moved.");
        assertEquals(1156, result, "There should be 1156 legal positions (leaf nodes).");
    }

    @Test
    void kingsHaveNotMovedAfterFiveMovesWithUndoDepth_5() {
        ChessGame gameState = new ChessGame(FEN_KINGS_ONLY);
        ptu = new PerftUtils(gameState);
        King whiteKing = (King) gameState.getChessboard().getPiece(7, 4);
        King blackKing = (King) gameState.getChessboard().getPiece(0, 4);
        assertFalse(whiteKing.isFlaggedAsHavingAlreadyMoved(), "Initially, the white king should not be flagged as having already moved.");
        assertFalse(blackKing.isFlaggedAsHavingAlreadyMoved(), "Initially, the black king should not be flagged as having already moved.");
        long result = ptu.perft(5, 5, stats);
        assertFalse(whiteKing.isFlaggedAsHavingAlreadyMoved(), "After all the moves have been undone, " +
                "the white king should not be flagged as having already moved.");
        assertFalse(blackKing.isFlaggedAsHavingAlreadyMoved(), "After all the moves have been undone, " +
                "the black king should not be flagged as having already moved.");
        assertEquals(7922, result, "There should be 7922 legal positions (leaf nodes).");
    }

    @Test
    void whiteKingNameCheck() {
        ChessGame gameState = new ChessGame(FEN_INITIAL_POSITION);
        King whiteKing = (King) gameState.getChessboard().getPiece(7, 4);
        assertEquals(PlayerColor.WHITE, whiteKing.getColor(), "King at square e1 should be white.");
        assertEquals(PieceNames.KING, whiteKing.getName(), "King's name should be 'KING'");
        assertEquals("K", whiteKing.toString(), "King's name as string should be 'K'");
    }

    @Test
    void blackKingNameCheck() {
        ChessGame gameState = new ChessGame(FEN_INITIAL_POSITION);
        King blackKing = (King) gameState.getChessboard().getPiece(0, 4);
        assertEquals(PlayerColor.BLACK, blackKing.getColor(), "King at square e8 should be black.");
        assertEquals(PieceNames.KING, blackKing.getName(), "King's name should be 'KING'");
        assertEquals("k", blackKing.toString(), "King's name as string should be 'k'");
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
    void blackKingHasRestrictedMovesBecauseOfWhitePawn() {
        ChessGame gameState = new ChessGame(FEN_PAWN_RESTRICTS_KING_POSITION);
        assertEquals(7, gameState.getLegalMovesForPieceAtPosition(new Position(1, 4)).size());
    }

    @Test
    void blackKingHasRestrictedMovesBecauseOfWhiteRook() {
        ChessGame gameState = new ChessGame(FEN_ROOK_RESTRICTS_KING_POSITION);
        assertEquals(5, gameState.getLegalMovesForPieceAtPosition(new Position(1, 4)).size());
    }

    @Test
    void blackKingHasRestrictedMovesBecauseOfWhiteKnight() {
        ChessGame gameState = new ChessGame(FEN_KNIGHT_RESTRICTS_KING_POSITION);
        assertEquals(6, gameState.getLegalMovesForPieceAtPosition(new Position(1, 4)).size());
    }

    @Test
    void blackKingHasRestrictedMovesBecauseOfWhiteBishop() {
        ChessGame gameState = new ChessGame(FEN_BISHOP_RESTRICTS_KING_POSITION);
        assertEquals(6, gameState.getLegalMovesForPieceAtPosition(new Position(1, 4)).size());
    }

    @Test
    void blackKingHasRestrictedMovesBecauseOfWhiteQueen() {
        ChessGame gameState = new ChessGame(FEN_QUEEN_RESTRICTS_KING_POSITION);
        assertEquals(3, gameState.getLegalMovesForPieceAtPosition(new Position(1, 4)).size());
    }

    @Test
    void blackKingLegalMovesBishopCheck() {
        ChessGame gameState = new ChessGame(FEN_KING_BISHOP_CHECK_POSITION);
        List<Move> blackKingLegalMoves = new ArrayList<>();
        blackKingLegalMoves.add(new StandardMove(new Position(0, 4), new Position(0, 3)));
        blackKingLegalMoves.add(new StandardMove(new Position(0, 4), new Position(1, 4)));
        blackKingLegalMoves.add(new StandardMove(new Position(0, 4), new Position(1, 5)));
        blackKingLegalMoves.add(new StandardMove(new Position(0, 4), new Position(0, 5)));
        List<Move> computedKingMoves = gameState.getLegalMovesForPieceAtPosition(new Position(0, 4));
        assertTrue(blackKingLegalMoves.size() == computedKingMoves.size() &&
                blackKingLegalMoves.containsAll(computedKingMoves) &&
                computedKingMoves.containsAll(blackKingLegalMoves), "The expected list of moves: " + blackKingLegalMoves +
                " is not the same as the produced one: " + computedKingMoves);
    }

    @Test
    void blackKingLegalMovesBishopCheckInterposePossible() {
        ChessGame gameState = new ChessGame(FEN_KING_BISHOP_CHECK_INTERPOSE_POSITION);
        List<Move> blackLegalMoves = new ArrayList<>();
        blackLegalMoves.add(new StandardMove(new Position(0, 4), new Position(0, 3)));
        blackLegalMoves.add(new StandardMove(new Position(0, 4), new Position(1, 4)));
        blackLegalMoves.add(new StandardMove(new Position(0, 4), new Position(1, 5)));
        blackLegalMoves.add(new StandardMove(new Position(0, 4), new Position(0, 5)));
        blackLegalMoves.add(new StandardMove(new Position(0, 2), new Position(1, 3)));
        List<Move> computedBlackMoves = gameState.getLegalMovesForColor(PlayerColor.BLACK);
        assertTrue(blackLegalMoves.size() == computedBlackMoves.size() &&
                blackLegalMoves.containsAll(computedBlackMoves) &&
                computedBlackMoves.containsAll(blackLegalMoves), "The expected list of moves: " + blackLegalMoves +
                " is not the same as the produced one: " + computedBlackMoves);
    }

    @Test
    void blackKingLegalMovesWhiteRookCheckNoQueensideCastleFromFEN() {
        ChessGame gameState = new ChessGame(FEN_BLACK_KING_WHITE_ROOK_CHECK_NO_QUEENSIDE_CASTLE_POSITION);
        List<Move> blackKingLegalMoves = new ArrayList<>();
        blackKingLegalMoves.add(new StandardMove(new Position(0, 4), new Position(1, 3)));
        blackKingLegalMoves.add(new StandardMove(new Position(0, 4), new Position(1, 4)));
        blackKingLegalMoves.add(new StandardMove(new Position(0, 4), new Position(1, 5)));
        List<Move> computedKingMoves = gameState.getLegalMovesForPieceAtPosition(new Position(0, 4));
        assertTrue(blackKingLegalMoves.size() == computedKingMoves.size() &&
                blackKingLegalMoves.containsAll(computedKingMoves) &&
                computedKingMoves.containsAll(blackKingLegalMoves), "The expected list of moves: " + blackKingLegalMoves +
                " is not the same as the produced one: " + computedKingMoves);
    }

    @Test
    void blackKingLegalMovesWhiteRookCheckNoKingsideCastleFromFEN() {
        ChessGame gameState = new ChessGame(FEN_BLACK_KING_WHITE_ROOK_CHECK_NO_KINGSIDE_CASTLE_POSITION);
        List<Move> blackKingLegalMoves = new ArrayList<>();
        blackKingLegalMoves.add(new StandardMove(new Position(0, 4), new Position(1, 3)));
        blackKingLegalMoves.add(new StandardMove(new Position(0, 4), new Position(1, 4)));
        blackKingLegalMoves.add(new StandardMove(new Position(0, 4), new Position(1, 5)));
        List<Move> computedKingMoves = gameState.getLegalMovesForPieceAtPosition(new Position(0, 4));
        assertTrue(blackKingLegalMoves.size() == computedKingMoves.size() &&
                blackKingLegalMoves.containsAll(computedKingMoves) &&
                computedKingMoves.containsAll(blackKingLegalMoves), "The expected list of moves: " + blackKingLegalMoves +
                " is not the same as the produced one: " + computedKingMoves);
    }

    @Test
    void whiteKingLegalMovesBlackRookCheckNoQueensideCastleFromFEN() {
        ChessGame gameState = new ChessGame(FEN_WHITE_KING_BLACK_ROOK_CHECK_NO_QUEENSIDE_CASTLE_POSITION);
        List<Move> whiteKingLegalMoves = new ArrayList<>();
        whiteKingLegalMoves.add(new StandardMove(new Position(7, 4), new Position(6, 3)));
        whiteKingLegalMoves.add(new StandardMove(new Position(7, 4), new Position(6, 4)));
        whiteKingLegalMoves.add(new StandardMove(new Position(7, 4), new Position(6, 5)));
        List<Move> computedKingMoves = gameState.getLegalMovesForPieceAtPosition(new Position(7, 4));
        assertTrue(whiteKingLegalMoves.size() == computedKingMoves.size() &&
                whiteKingLegalMoves.containsAll(computedKingMoves) &&
                computedKingMoves.containsAll(whiteKingLegalMoves), "The expected list of moves: " + whiteKingLegalMoves +
                " is not the same as the produced one: " + computedKingMoves);
    }

    @Test
    void whiteKingLegalMovesBlackRookCheckNoKingsideCastleFromFEN() {
        ChessGame gameState = new ChessGame(FEN_WHITE_KING_BLACK_ROOK_CHECK_NO_KINGSIDE_CASTLE_POSITION);
        List<Move> blackKingLegalMoves = new ArrayList<>();
        blackKingLegalMoves.add(new StandardMove(new Position(7, 4), new Position(6, 3)));
        blackKingLegalMoves.add(new StandardMove(new Position(7, 4), new Position(6, 4)));
        blackKingLegalMoves.add(new StandardMove(new Position(7, 4), new Position(6, 5)));
        List<Move> computedKingMoves = gameState.getLegalMovesForPieceAtPosition(new Position(7, 4));
        assertTrue(blackKingLegalMoves.size() == computedKingMoves.size() &&
                blackKingLegalMoves.containsAll(computedKingMoves) &&
                computedKingMoves.containsAll(blackKingLegalMoves), "The expected list of moves: " + blackKingLegalMoves +
                " is not the same as the produced one: " + computedKingMoves);
    }

    @Test
    void whiteKingNoQueensideCastleWithInterposingFriendlyPiece() {
        ChessGame gameState = new ChessGame(FEN_WHITE_KING_NO_QUEENSIDE_CASTLE_WITH_INTERPOSING_FRIENDLY_PIECE);
        List<Move> whiteKingLegalMoves = new ArrayList<>();
        whiteKingLegalMoves.add(new StandardMove(new Position(7, 4), new Position(7, 3)));
        whiteKingLegalMoves.add(new StandardMove(new Position(7, 4), new Position(7, 5)));
        whiteKingLegalMoves.add(new StandardMove(new Position(7, 4), new Position(6, 5)));
        whiteKingLegalMoves.add(new StandardMove(new Position(7, 4), new Position(6, 4)));
        whiteKingLegalMoves.add(new StandardMove(new Position(7, 4), new Position(6, 3)));
        List<Move> computedKingMoves = gameState.getLegalMovesForPieceAtPosition(new Position(7, 4));
        assertTrue(whiteKingLegalMoves.size() == computedKingMoves.size() &&
                whiteKingLegalMoves.containsAll(computedKingMoves) &&
                computedKingMoves.containsAll(whiteKingLegalMoves), "The expected list of moves: " + whiteKingLegalMoves +
                " is not the same as the produced one: " + computedKingMoves);
    }

    @Test
    void blackKingNoQueensideCastleWithInterposingFriendlyPiece() {
        ChessGame gameState = new ChessGame(FEN_BLACK_KING_NO_QUEENSIDE_CASTLE_WITH_INTERPOSING_FRIENDLY_PIECE);
        List<Move> blackKingLegalMoves = new ArrayList<>();
        blackKingLegalMoves.add(new StandardMove(new Position(0, 4), new Position(0, 3)));
        blackKingLegalMoves.add(new StandardMove(new Position(0, 4), new Position(0, 5)));
        blackKingLegalMoves.add(new StandardMove(new Position(0, 4), new Position(1, 5)));
        blackKingLegalMoves.add(new StandardMove(new Position(0, 4), new Position(1, 4)));
        blackKingLegalMoves.add(new StandardMove(new Position(0, 4), new Position(1, 3)));
        List<Move> computedKingMoves = gameState.getLegalMovesForPieceAtPosition(new Position(0, 4));
        assertTrue(blackKingLegalMoves.size() == computedKingMoves.size() &&
                blackKingLegalMoves.containsAll(computedKingMoves) &&
                computedKingMoves.containsAll(blackKingLegalMoves), "The expected list of moves: " + blackKingLegalMoves +
                " is not the same as the produced one: " + computedKingMoves);
    }

    @Test
    void whiteKingNoKingsideCastleWithInterposingFriendlyPiece() {
        ChessGame gameState = new ChessGame(FEN_WHITE_KING_NO_KINGSIDE_CASTLE_WITH_INTERPOSING_FRIENDLY_PIECE);
        List<Move> whiteKingLegalMoves = new ArrayList<>();
        whiteKingLegalMoves.add(new StandardMove(new Position(7, 4), new Position(7, 3)));
        whiteKingLegalMoves.add(new StandardMove(new Position(7, 4), new Position(7, 5)));
        whiteKingLegalMoves.add(new StandardMove(new Position(7, 4), new Position(6, 5)));
        whiteKingLegalMoves.add(new StandardMove(new Position(7, 4), new Position(6, 4)));
        whiteKingLegalMoves.add(new StandardMove(new Position(7, 4), new Position(6, 3)));
        List<Move> computedKingMoves = gameState.getLegalMovesForPieceAtPosition(new Position(7, 4));
        assertTrue(whiteKingLegalMoves.size() == computedKingMoves.size() &&
                whiteKingLegalMoves.containsAll(computedKingMoves) &&
                computedKingMoves.containsAll(whiteKingLegalMoves), "The expected list of moves: " + whiteKingLegalMoves +
                " is not the same as the produced one: " + computedKingMoves);
    }

    @Test
    void blackKingNoKingsideCastleWithInterposingFriendlyPiece() {
        ChessGame gameState = new ChessGame(FEN_BLACK_KING_NO_KINGSIDE_CASTLE_WITH_INTERPOSING_FRIENDLY_PIECE);
        List<Move> blackKingLegalMoves = new ArrayList<>();
        blackKingLegalMoves.add(new StandardMove(new Position(0, 4), new Position(0, 3)));
        blackKingLegalMoves.add(new StandardMove(new Position(0, 4), new Position(1, 5)));
        blackKingLegalMoves.add(new StandardMove(new Position(0, 4), new Position(1, 4)));
        blackKingLegalMoves.add(new StandardMove(new Position(0, 4), new Position(1, 3)));
        List<Move> computedKingMoves = gameState.getLegalMovesForPieceAtPosition(new Position(0, 4));
        assertTrue(blackKingLegalMoves.size() == computedKingMoves.size() &&
                blackKingLegalMoves.containsAll(computedKingMoves) &&
                computedKingMoves.containsAll(blackKingLegalMoves), "The expected list of moves: " + blackKingLegalMoves +
                " is not the same as the produced one: " + computedKingMoves);
    }

    @Test
    void blackKingNoCastlePathUnderCheck() {
        ChessGame gameState = new ChessGame(FEN_BLACK_KING_NO_CASTLE_PATH_UNDER_CHECK);
        List<Move> blackKingLegalMoves = new ArrayList<>();
        blackKingLegalMoves.add(new StandardMove(new Position(0, 4), new Position(0, 5)));
        blackKingLegalMoves.add(new StandardMove(new Position(0, 4), new Position(1, 5)));
        blackKingLegalMoves.add(new StandardMove(new Position(0, 4), new Position(1, 4)));
        List<Move> computedKingMoves = gameState.getLegalMovesForPieceAtPosition(new Position(0, 4));
        assertTrue(blackKingLegalMoves.size() == computedKingMoves.size() &&
                blackKingLegalMoves.containsAll(computedKingMoves) &&
                computedKingMoves.containsAll(blackKingLegalMoves), "The expected list of moves: " + blackKingLegalMoves +
                " is not the same as the produced one: " + computedKingMoves);
    }

    @Test
    void whiteKingNoCastlePathUnderCheck() {
        ChessGame gameState = new ChessGame(FEN_WHITE_KING_NO_CASTLE_PATH_UNDER_CHECK);
        log.warn("Current position: {}", gameState.getChessboard());
        List<Move> whiteKingLegalMoves = new ArrayList<>();
        whiteKingLegalMoves.add(new StandardMove(new Position(7, 4), new Position(7, 3)));
        whiteKingLegalMoves.add(new StandardMove(new Position(7, 4), new Position(6, 3)));
        whiteKingLegalMoves.add(new StandardMove(new Position(7, 4), new Position(6, 5)));
        List<Move> computedKingMoves = gameState.getLegalMovesForPieceAtPosition(new Position(7, 4));
        assertTrue(whiteKingLegalMoves.size() == computedKingMoves.size() &&
                whiteKingLegalMoves.containsAll(computedKingMoves) &&
                computedKingMoves.containsAll(whiteKingLegalMoves), "The expected list of moves: " + whiteKingLegalMoves +
                " is not the same as the produced one: " + computedKingMoves);
    }

    @Test
    void blackKingNoCastleUnderCheck() {
        ChessGame gameState = new ChessGame(FEN_BLACK_KING_NO_CASTLE_UNDER_CHECK);
        List<Move> blackKingLegalMoves = new ArrayList<>();
        blackKingLegalMoves.add(new StandardMove(new Position(0, 4), new Position(0, 5)));
        blackKingLegalMoves.add(new StandardMove(new Position(0, 4), new Position(0, 3)));
        blackKingLegalMoves.add(new StandardMove(new Position(0, 4), new Position(1, 5)));
        blackKingLegalMoves.add(new StandardMove(new Position(0, 4), new Position(1, 3)));
        List<Move> computedKingMoves = gameState.getLegalMovesForPieceAtPosition(new Position(0, 4));
        assertTrue(blackKingLegalMoves.size() == computedKingMoves.size() &&
                blackKingLegalMoves.containsAll(computedKingMoves) &&
                computedKingMoves.containsAll(blackKingLegalMoves), "The expected list of moves: " + blackKingLegalMoves +
                " is not the same as the produced one: " + computedKingMoves);
    }

    @Test
    void whiteKingNoCastleUnderCheck() {
        ChessGame gameState = new ChessGame(FEN_WHITE_KING_NO_CASTLE_UNDER_CHECK);
        List<Move> whiteKingLegalMoves = new ArrayList<>();
        whiteKingLegalMoves.add(new StandardMove(new Position(7, 4), new Position(7, 3)));
        whiteKingLegalMoves.add(new StandardMove(new Position(7, 4), new Position(7, 5)));
        whiteKingLegalMoves.add(new StandardMove(new Position(7, 4), new Position(6, 3)));
        whiteKingLegalMoves.add(new StandardMove(new Position(7, 4), new Position(6, 5)));
        List<Move> computedKingMoves = gameState.getLegalMovesForPieceAtPosition(new Position(7, 4));
        assertTrue(whiteKingLegalMoves.size() == computedKingMoves.size() &&
                whiteKingLegalMoves.containsAll(computedKingMoves) &&
                computedKingMoves.containsAll(whiteKingLegalMoves), "The expected list of moves: " + whiteKingLegalMoves +
                " is not the same as the produced one: " + computedKingMoves);
    }

    @Test
    void verticallyPinnedBlackRook() {
        ChessGame gameState = new ChessGame(FEN_BLACK_ROOK_NON_ADJACENT_TO_KING_VERTICALLY_PINNED_BY_WHITE_ROOK);
        List<Move> blackRookLegalMoves = new ArrayList<>();
        blackRookLegalMoves.add(new StandardMove(new Position(2, 4), new Position(1, 4)));
        blackRookLegalMoves.add(new StandardMove(new Position(2, 4), new Position(3, 4)));
        blackRookLegalMoves.add(new StandardMove(new Position(2, 4), new Position(4, 4)));
        blackRookLegalMoves.add(new StandardMove(new Position(2, 4), new Position(5, 4)));
        blackRookLegalMoves.add(new StandardMove(new Position(2, 4), new Position(6, 4)));
        blackRookLegalMoves.add(new StandardMove(new Position(2, 4), new Position(7, 4)));
        List<Move> computedRookMoves = gameState.getLegalMovesForPieceAtPosition(new Position(2, 4));
        assertEquals(blackRookLegalMoves.size(), computedRookMoves.size(), "The computed number of legal moves should be 6 but it is: " + computedRookMoves.size());
        assertTrue(blackRookLegalMoves.containsAll(computedRookMoves) &&
                computedRookMoves.containsAll(blackRookLegalMoves), "The expected list of moves: " +
                blackRookLegalMoves + " is not the same as the produced one: " + computedRookMoves);
    }

    @Test
    void verticallyPinnedBlackRookAdjacentToKing() {
        ChessGame gameState = new ChessGame(FEN_BLACK_ROOK_ADJACENT_TO_KING_VERTICALLY_PINNED_BY_WHITE_ROOK);
        List<Move> blackRookLegalMoves = new ArrayList<>();
        blackRookLegalMoves.add(new StandardMove(new Position(1, 4), new Position(2, 4)));
        blackRookLegalMoves.add(new StandardMove(new Position(1, 4), new Position(3, 4)));
        blackRookLegalMoves.add(new StandardMove(new Position(1, 4), new Position(4, 4)));
        blackRookLegalMoves.add(new StandardMove(new Position(1, 4), new Position(5, 4)));
        blackRookLegalMoves.add(new StandardMove(new Position(1, 4), new Position(6, 4)));
        blackRookLegalMoves.add(new StandardMove(new Position(1, 4), new Position(7, 4)));
        List<Move> computedRookMoves = gameState.getLegalMovesForPieceAtPosition(new Position(1, 4));
        assertTrue(blackRookLegalMoves.size() == computedRookMoves.size() &&
                blackRookLegalMoves.containsAll(computedRookMoves) &&
                computedRookMoves.containsAll(blackRookLegalMoves), "The expected list of moves: " + blackRookLegalMoves +
                " is not the same as the produced one: " + computedRookMoves);
    }

    @Test
    void horizontallyPinnedBlackRookAdjacentToKing() {
        ChessGame gameState = new ChessGame(FEN_BLACK_ROOK_ADJACENT_TO_KING_HORIZONTALLY_PINNED_BY_WHITE_ROOK);
        List<Move> blackRookLegalMoves = new ArrayList<>();
        blackRookLegalMoves.add(new StandardMove(new Position(1, 2), new Position(1, 3)));
        blackRookLegalMoves.add(new StandardMove(new Position(1, 2), new Position(1, 4)));
        blackRookLegalMoves.add(new StandardMove(new Position(1, 2), new Position(1, 5)));
        blackRookLegalMoves.add(new StandardMove(new Position(1, 2), new Position(1, 6)));
        blackRookLegalMoves.add(new StandardMove(new Position(1, 2), new Position(1, 7)));
        List<Move> computedRookMoves = gameState.getLegalMovesForPieceAtPosition(new Position(1, 2));
        assertTrue(blackRookLegalMoves.size() == computedRookMoves.size() &&
                blackRookLegalMoves.containsAll(computedRookMoves) &&
                computedRookMoves.containsAll(blackRookLegalMoves), "The expected list of moves: " + blackRookLegalMoves +
                " is not the same as the produced one: " + computedRookMoves);
    }

    @Test
    void horizontallyPinnedBlackRookNonAdjacentToKing() {
        ChessGame gameState = new ChessGame(FEN_BLACK_ROOK_NON_ADJACENT_TO_KING_HORIZONTALLY_PINNED_BY_WHITE_ROOK);
        List<Move> blackRookLegalMoves = new ArrayList<>();
        blackRookLegalMoves.add(new StandardMove(new Position(1, 3), new Position(1, 2)));
        blackRookLegalMoves.add(new StandardMove(new Position(1, 3), new Position(1, 4)));
        blackRookLegalMoves.add(new StandardMove(new Position(1, 3), new Position(1, 5)));
        blackRookLegalMoves.add(new StandardMove(new Position(1, 3), new Position(1, 6)));
        blackRookLegalMoves.add(new StandardMove(new Position(1, 3), new Position(1, 7)));
        List<Move> computedRookMoves = gameState.getLegalMovesForPieceAtPosition(new Position(1, 3));
        assertTrue(blackRookLegalMoves.size() == computedRookMoves.size() &&
                blackRookLegalMoves.containsAll(computedRookMoves) &&
                computedRookMoves.containsAll(blackRookLegalMoves), "The expected list of moves: " + blackRookLegalMoves +
                " is not the same as the produced one: " + computedRookMoves);
    }

    @Test
    void blackBishopPinnedByWhiteBishop() {
        ChessGame gameState = new ChessGame(FEN_BLACK_BISHOP_PINNED_BY_WHITE_BISHOP);
        List<Move> blackBishopLegalMoves = new ArrayList<>();
        blackBishopLegalMoves.add(new StandardMove(new Position(2, 4), new Position(1, 3)));
        blackBishopLegalMoves.add(new StandardMove(new Position(2, 4), new Position(3, 5)));
        blackBishopLegalMoves.add(new StandardMove(new Position(2, 4), new Position(4, 6)));
        blackBishopLegalMoves.add(new StandardMove(new Position(2, 4), new Position(5, 7)));
        List<Move> computedBishopMoves = gameState.getLegalMovesForPieceAtPosition(new Position(2, 4));
        assertTrue(blackBishopLegalMoves.size() == computedBishopMoves.size() &&
                blackBishopLegalMoves.containsAll(computedBishopMoves) &&
                computedBishopMoves.containsAll(blackBishopLegalMoves), "The expected list of moves: " + blackBishopLegalMoves +
                " is not the same as the produced one: " + computedBishopMoves);
    }

    @Test
    void blackBishopNotPinnedByWhiteBishopBecauseOfInterposingKnight() {
        ChessGame gameState = new ChessGame(FEN_BLACK_BISHOP_NOT_PINNED_BY_WHITE_BISHOP_BECAUSE_OF_INTERPOSING_KNIGHT);
        List<Move> blackBishopLegalMoves = new ArrayList<>();
        blackBishopLegalMoves.add(new StandardMove(new Position(2, 4), new Position(1, 3)));
        blackBishopLegalMoves.add(new StandardMove(new Position(2, 4), new Position(3, 5)));
        blackBishopLegalMoves.add(new StandardMove(new Position(2, 4), new Position(1, 5)));
        blackBishopLegalMoves.add(new StandardMove(new Position(2, 4), new Position(0, 6)));
        blackBishopLegalMoves.add(new StandardMove(new Position(2, 4), new Position(3, 3)));
        blackBishopLegalMoves.add(new StandardMove(new Position(2, 4), new Position(4, 2)));
        blackBishopLegalMoves.add(new StandardMove(new Position(2, 4), new Position(5, 1)));
        blackBishopLegalMoves.add(new StandardMove(new Position(2, 4), new Position(6, 0)));

        List<Move> computedBishopMoves = gameState.getLegalMovesForPieceAtPosition(new Position(2, 4));
        assertTrue(blackBishopLegalMoves.size() == computedBishopMoves.size() &&
                blackBishopLegalMoves.containsAll(computedBishopMoves) &&
                computedBishopMoves.containsAll(blackBishopLegalMoves), "The expected list of moves: " + blackBishopLegalMoves +
                " is not the same as the produced one: " + computedBishopMoves);
    }

    @Test
    void whiteBishopPinnedByBlackBishop() {
        ChessGame gameState = new ChessGame(FEN_WHITE_BISHOP_PINNED_BY_BLACK_BISHOP);
        List<Move> whiteBishopLegalMoves = new ArrayList<>();
        whiteBishopLegalMoves.add(new StandardMove(new Position(5, 6), new Position(1, 2)));
        whiteBishopLegalMoves.add(new StandardMove(new Position(5, 6), new Position(2, 3)));
        whiteBishopLegalMoves.add(new StandardMove(new Position(5, 6), new Position(3, 4)));
        whiteBishopLegalMoves.add(new StandardMove(new Position(5, 6), new Position(4, 5)));
        List<Move> computedBishopMoves = gameState.getLegalMovesForPieceAtPosition(new Position(5, 6));
        assertTrue(whiteBishopLegalMoves.size() == computedBishopMoves.size() &&
                whiteBishopLegalMoves.containsAll(computedBishopMoves) &&
                computedBishopMoves.containsAll(whiteBishopLegalMoves), "The expected list of moves: " + whiteBishopLegalMoves +
                " is not the same as the produced one: " + computedBishopMoves);
    }

    @Test
    void whiteKnightPinnedByBlackRookOnRow() {
        ChessGame gameState = new ChessGame(FEN_WHITE_KNIGHT_PINNED_BY_BLACK_ROOK_ON_ROW);
        List<Move> computedKnightMoves = gameState.getLegalMovesForPieceAtPosition(new Position(3, 3));
        assertTrue(computedKnightMoves.isEmpty(), "Knight should have no moves.");
    }

    @Test
    void blackKnightPinnedByWhiteQueenOnColumn() {
        ChessGame gameState = new ChessGame(FEN_BLACK_KNIGHT_PINNED_BY_WHITE_QUEEN_ON_COLUMN);
        List<Move> computedKnightMoves = gameState.getLegalMovesForPieceAtPosition(new Position(3, 1));
        assertTrue(computedKnightMoves.isEmpty(), "Knight should have no moves.");
    }

    @Test
    void whiteKnightPinnedByBlackQueenOnDiagonal() {
        ChessGame gameState = new ChessGame(FEN_WHITE_KNIGHT_PINNED_BY_BLACK_QUEEN_ON_DIAGONAL);
        List<Move> computedKnightMoves = gameState.getLegalMovesForPieceAtPosition(new Position(3, 1));
        assertTrue(computedKnightMoves.isEmpty(), "Knight should have no moves.");
    }

    @Test
    void blackPawnPinnedByWhiteQueenOnDiagonal() {
        ChessGame gameState = new ChessGame(FEN_BLACK_PAWN_PINNED_BY_WHITE_QUEEN_ON_DIAGONAL);
        List<Move> computedPawnMoves = gameState.getLegalMovesForPieceAtPosition(new Position(1, 3));
        assertTrue(computedPawnMoves.isEmpty(), "Pawn should have no moves.");
    }

    @Test
    void blackPawnPinnedByWhiteQueenOnRow() {
        ChessGame gameState = new ChessGame(FEN_BLACK_PAWN_PINNED_BY_WHITE_QUEEN_ON_ROW);
        List<Move> computedPawnMoves = gameState.getLegalMovesForPieceAtPosition(new Position(1, 3));
        assertTrue(computedPawnMoves.isEmpty(), "Pawn should have no moves.");
    }

    @Test
    void whitePawnPinnedNoEnPassantCapture() {
        ChessGame gameState = new ChessGame(FEN_WHITE_PAWN_PINNED_NO_EN_PASSANT);
        log.debug("Initial position: {}", gameState.getChessboard());
        List<Move> whitePawnLegalMoves = new ArrayList<>();
        whitePawnLegalMoves.add(new StandardMove(new Position(3, 3), new Position(2, 3)));
        List<Move> computedPawnMoves = gameState.getLegalMovesForPieceAtPosition(new Position(3, 3));
        assertTrue(whitePawnLegalMoves.size() == computedPawnMoves.size() &&
                whitePawnLegalMoves.containsAll(computedPawnMoves) &&
                computedPawnMoves.containsAll(whitePawnLegalMoves), "The expected list of moves: " +
                whitePawnLegalMoves + " is not the same as the produced one: " + computedPawnMoves);
    }

    @Test
    void whitePawnsNotPinnedByBlackQueen() {
        ChessGame gameState = new ChessGame(FEN_WHITE_PAWNS_NOT_PINNED_BY_BLACK_QUEEN);
        log.error("Initial position: {}", gameState.getChessboard());
        List<Move> whitePawnC3LegalMoves = new ArrayList<>();
        whitePawnC3LegalMoves.add(new StandardMove(new Position(5, 2), new Position(4, 2)));
        List<Move> computedPawnC3Moves = gameState.getLegalMovesForPieceAtPosition(new Position(5, 2));
        assertTrue(whitePawnC3LegalMoves.size() == computedPawnC3Moves.size() &&
                whitePawnC3LegalMoves.containsAll(computedPawnC3Moves) &&
                computedPawnC3Moves.containsAll(whitePawnC3LegalMoves), "The expected list of moves for pawn in c3: "
                + whitePawnC3LegalMoves + " is not the same as the produced one: " + computedPawnC3Moves);
        List<Move> whitePawnD2LegalMoves = new ArrayList<>();
        whitePawnD2LegalMoves.add(new StandardMove(new Position(6, 3), new Position(5, 3)));
        whitePawnD2LegalMoves.add(new DoublePawnMove(new Position(6, 3), new Position(4, 3),
                gameState.getChessboard()));
        List<Move> computedPawnD2Moves = gameState.getLegalMovesForPieceAtPosition(new Position(6, 3));
        assertTrue(whitePawnD2LegalMoves.size() == computedPawnD2Moves.size() &&
                        whitePawnD2LegalMoves.containsAll(computedPawnD2Moves) &&
                        computedPawnD2Moves.containsAll(whitePawnD2LegalMoves),
                "The expected list of moves for pawn in d2: " + whitePawnD2LegalMoves +
                        " is not the same as the produced one: " + computedPawnD2Moves);
    }

    @Test
    void blackKingInDoubleCheckedWithDiscoveryAndMateByWhiteKnight() {
        ChessGame gameState = new ChessGame(FEN_KING_DOUBLE_CHECKED_AND_MATED_BY_WHITE_KNIGHT);
        log.debug("Position: {}", gameState.getChessboard());
        List<Move> computedKingMoves = gameState.getLegalMovesForPieceAtPosition(new Position(0, 7));
        assertTrue(computedKingMoves.isEmpty(), "King should have no moves.");
        List<Move> legalMovesforColor = gameState.getLegalMovesForColor(gameState.getChessboard().getCurrentPlayerColor());
        assertTrue(legalMovesforColor.isEmpty(), "Black should have no moves.");
        assertTrue(gameState.getChessboard().isPlayerInCheck(PlayerColor.BLACK), "Black should be in check.");
        assertTrue(gameState.getChessboard().isPlayerInDoubleCheck(PlayerColor.BLACK), "Black should be in double check.");
        gameState.checkGameEnd(legalMovesforColor);
        assertTrue(gameState.isGameOver(), "Game should be over.");
        assertEquals(GameEndReason.CHECKMATE, gameState.getGameResult().gameEndReason(), "A checkmate should be on the board.");
        assertEquals(PlayerColor.WHITE, gameState.getGameResult().winner(), "Winner should be white.");
    }

    @Test
    void whiteKnightTwoPossibleMovesOneIsDoubleCheckAndDiscovery() {
        ChessGame gameState = new ChessGame(FEN_KNIGHT_DOUBLE_CHECKS_AND_MATES_WHITE_KING);
        List<Move> computedKnightMoves = gameState.getLegalMovesForPieceAtPosition(new Position(0, 5));
        computedKnightMoves.add(new StandardMove(new Position(0, 5), new Position(1, 7)));
        computedKnightMoves.add(new StandardMove(new Position(0, 5), new Position(2, 6)));
        assertEquals(2, computedKnightMoves.size(), "Knight should have 2 moves.");
    }

    @Test
    void whiteRookInitialCheckAndWhiteMoves() {
        assertThrows(AssertionError.class, () -> {
            ChessGame gameState = new ChessGame(FEN_WHITE_ROOK_INITIAL_CHECKER);
            assertTrue(gameState.getChessboard().isPlayerInCheck(PlayerColor.WHITE), "White player should be in check");
        }, "A player cannot be in check and have the move at the same time.");
    }

    @Test
    void losingCastleRights_0b_BlackMovesDepth5() {
        ChessGame gameState = new ChessGame(FEN_NO_CHECK_LOST_CASTLE_RIGHTS_0B);
        gameState.getChessboard().setCurrentPlayerColor(PlayerColor.BLACK);
        King king = (King) gameState.getChessboard().getPiece(new Position(0, 4));
        assertFalse(king.isFlaggedAsHavingAlreadyMoved(), "Black king should be flagged as already moved");
        //ke8f8
        StandardMove move_1 = new StandardMove(new Position(0, 4), new Position(0, 5));
        if (move_1.isLegal(gameState.getChessboard().copy())) {
            move_1.execute(gameState.getChessboard());
            log.error("I have just executed move 1: {}.", move_1);
            assertTrue(king.isFlaggedAsHavingAlreadyMoved(), "Black king should be flagged as already moved");            log.error(gameState.getChessboard().toString());
        } else {
            log.error("Move 1 {} is not legal.", move_1);
        }
        //c2c3
        StandardMove move_2 = new StandardMove(new Position(6, 2), new Position(5, 2));
        if (move_2.isLegal(gameState.getChessboard().copy())) {
            move_2.execute(gameState.getChessboard());
            log.error("I have just executed move 2: {}.", move_2);
            assertTrue(king.isFlaggedAsHavingAlreadyMoved(), "Black king should be flagged as already moved");            log.error(gameState.getChessboard().toString());
        } else {
            log.error("Move 2 {} is not legal.", move_2);
        }
        //kf8e8
        StandardMove move_3 = new StandardMove(new Position(0, 5), new Position(0, 4));
        if (move_3.isLegal(gameState.getChessboard().copy())) {
            move_3.execute(gameState.getChessboard());
            log.error("I have just executed move 3: {}.", move_3);
            assertTrue(king.isFlaggedAsHavingAlreadyMoved(), "Black king should be flagged as already moved");            log.error(gameState.getChessboard().toString());
        } else {
            log.error("Move 3: {} is not legal.", move_3);
        }
        //c3c4
        StandardMove move_4 = new StandardMove(new Position(5, 2), new Position(4, 2));
        if (move_4.isLegal(gameState.getChessboard().copy())) {
            move_4.execute(gameState.getChessboard());
            log.error("I have just executed move 4: {}.", move_4);
            assertTrue(king.isFlaggedAsHavingAlreadyMoved(), "Black king should be flagged as already moved");
            log.error(gameState.getChessboard().toString());
        } else {
            log.error("Move 4: {} is not legal.", move_4);
        }
        //ke8g8 (kingside castle) is not even a pseudolegal move because the black king has moved already.
        CastlingMove move_5 = new CastlingMove(MoveNames.KINGSIDE_CASTLE, new Position(0, 4));
        List<Move> pseudoLegalMoves = king.getPseudoLegalMoves(new Position(0, 4),
                gameState.getChessboard());
        assertFalse (pseudoLegalMoves.contains(move_5), "Move 5 {} is not pseudolegal.");
    }

    @Test
    void effectOfExecuteAndUndoOnKingHavingAlreadyMovedFlagBlackMovesDepth3() {
        ChessGame gameState = new ChessGame(FEN_NO_CHECK_LOST_CASTLE_RIGHTS_0B);
        gameState.getChessboard().setCurrentPlayerColor(PlayerColor.BLACK);
        King king = (King) gameState.getChessboard().getPiece(new Position(0, 4));
        assertFalse(king.isFlaggedAsHavingAlreadyMoved(), "Black king should not be flagged as already moved.");
        StandardMove move_1 = new StandardMove(new Position(0, 4), new Position(0, 5));
        if (move_1.isLegal(gameState.getChessboard().copy())) {
            move_1.execute(gameState.getChessboard());
            log.error("After executing {}:", move_1);
            assertTrue(king.isFlaggedAsHavingAlreadyMoved(), "Black king should be flagged as already moved.");
            log.error(gameState.getChessboard().toString());
            move_1.undo(gameState.getChessboard());
            log.error("After undoing {}:", move_1);
            assertFalse(king.isFlaggedAsHavingAlreadyMoved(), "Black king should not be flagged as already moved.");
            log.error(gameState.getChessboard().toString());
        } else {
            log.error("Move 1 {} is not legal.", move_1);
        }
    }
}
