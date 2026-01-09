package com.marcopiccionitraining.parkychess;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import com.marcopiccionitraining.parkychess.model.moves.StandardMove;
import com.marcopiccionitraining.parkychess.model.pieces.PieceNames;
import com.marcopiccionitraining.parkychess.model.pieces.Rook;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static com.marcopiccionitraining.parkychess.model.FENPositions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Slf4j @SpringBootTest
class RookTests {
	private MoveGeneratorStatistics stats;
    private PerftUtils ptu;

	@BeforeEach
	void setup() {
		stats = new MoveGeneratorStatistics();
    }

	@Test
	void blackRookHasNotMovedAfterAnyMoveWithUndo(){
		ChessGame gameState = new ChessGame(FEN_BLACK_ROOK_H8_HAS_NOT_MOVED_FOR_GOOD);
        log.debug("After creating chessGame, before creating PerftUtils");
		ptu = new PerftUtils(gameState);
        log.debug("Current color after initializing gameState: {}", gameState.getChessboard().getCurrentPlayerColor());
        Rook blackRook = (Rook) gameState.getChessboard().getPiece (0,7);
        log.debug("Current color before invoking perft: {}", gameState.getChessboard().getCurrentPlayerColor());
        assertFalse(blackRook.isFlaggedAsHavingAlreadyMoved(),"Before the first move is made, " +
                "the rook should not be flagged as having already moved.");
        long result = ptu.perft(1, 1, stats);
		assertEquals(15, result, "There should be 15 legal moves (leaf nodes).");
		assertFalse(blackRook.isFlaggedAsHavingAlreadyMoved(),"After the first move is undone, " +
				"the rook should not be flagged as having already moved.");
	}

	@Test
	void blackRookHasNotMovedAfterThreeMovesWithUndoDepth_3(){
		ChessGame gameState = new ChessGame(FEN_BLACK_ROOK_H8_HAS_NOT_MOVED_FOR_GOOD);
        ptu = new PerftUtils(gameState);
        //FENStateString positionState = new FENStateString(gameState);
        //gameState.getChessboard().getGameStateContainer().push(positionState);
        Rook blackRook = (Rook) gameState.getChessboard().getPiece (0,7);
		assertFalse(blackRook.isFlaggedAsHavingAlreadyMoved(),"Initially, the rook should not be flagged as having already moved.");
		long result = ptu.perft(3, 3, stats);
		assertEquals(1197, result, "There should be 1197 legal moves (leaf nodes).");
		assertFalse(blackRook.isFlaggedAsHavingAlreadyMoved(),"After all the move have been undone, " +
				"the rook should not be flagged as having already moved.");
	}
	@Test
	void blackRookHasMovedForGoodDepth5(){
		ChessGame gameState = new ChessGame(FEN_BLACK_ROOK_H8_HAS_NOT_MOVED_FOR_GOOD);
		Rook blackRook = (Rook) gameState.getChessboard().getPiece (0,7);
		assertTrue(blackRook.isBlackKingsideRook(), "The rook should be a black kingside rook");
        StandardMove standardRookMove1 = new StandardMove(new Position(0, 7), new Position(1, 7));
		standardRookMove1.execute(gameState.getChessboard());
		StandardMove standardRookMove2 = new StandardMove(new Position(1, 7), new Position(2, 7));
		standardRookMove2.execute(gameState.getChessboard());
		StandardMove standardRookMove3 = new StandardMove(new Position(2, 7), new Position(1, 7));
		standardRookMove3.execute(gameState.getChessboard());
		StandardMove standardRookMove4 = new StandardMove(new Position(1, 7), new Position(0, 7));
		standardRookMove4.execute(gameState.getChessboard());
		assertTrue(blackRook.isBlackKingsideRookFlaggedAsHavingAlreadyMoved(),"The rook should be flagged as having already moved.");
	//	assertTrue(blackRook.isFlaggedAsHavingAlreadyMoved(), "The rook should be flagged as having already moved.");
	}

	@Test
	void whiteRookNameCheck(){
		ChessGame gameState = new ChessGame(FEN_INITIAL_POSITION);
		Rook whiteRook = (Rook) gameState.getChessboard().getPiece (7,7);
		assertEquals(PlayerColor.WHITE, whiteRook.getColor(), "Rook at square h1 should be white.");
		assertEquals(PieceNames.ROOK, whiteRook.getName(), "Rook's name should be 'ROOK'");
		assertEquals("Rh1", whiteRook.toString(), "Rook's name as string should be 'Rh1'");
	}

	@Test
	void blackRookNameCheck(){
		ChessGame gameState = new ChessGame(FEN_INITIAL_POSITION);
		Rook blackRook = (Rook) gameState.getChessboard().getPiece (0,7);
		assertEquals(PlayerColor.BLACK, blackRook.getColor(), "Rook at square h8 should be black.");
		assertEquals(PieceNames.ROOK, blackRook.getName(), "Rook's name should be 'ROOK'");
		assertEquals("rh8", blackRook.toString(), "Rook's name as string should be 'rh8'");
	}

	@Test
	void blackRookAtA8CannotMoveAtStartingPosition() {
		ChessGame gameState = new ChessGame(FEN_INITIAL_POSITION);
        gameState.getChessboard().setCurrentPlayerColor(PlayerColor.BLACK);
		assertTrue(gameState.getLegalMovesForPieceAtPosition(new Position(0, 0)).isEmpty());
	}

	@Test
	void blackRookAtH8CannotMoveAtStartingPosition() {
		ChessGame gameState = new ChessGame(FEN_INITIAL_POSITION);
        gameState.getChessboard().setCurrentPlayerColor(PlayerColor.BLACK);
		assertTrue(gameState.getLegalMovesForPieceAtPosition(new Position(0, 7)).isEmpty());
	}

	@Test
	void whiteRookAtA1CannotMoveAtStartingPosition() {
		ChessGame gameState = new ChessGame(FEN_INITIAL_POSITION);
		assertTrue(gameState.getLegalMovesForPieceAtPosition(new Position(7, 0)).isEmpty());
	}

	@Test
	void whiteRookAtH1CannotMoveAtStartingPosition() {
		ChessGame gameState = new ChessGame(FEN_INITIAL_POSITION);
		assertTrue(gameState.getLegalMovesForPieceAtPosition(new Position(7, 7)).isEmpty());
	}

	@Test
	void blackRookAtA8LegalMovesNoCheck() {
		ChessGame gameState = new ChessGame(FEN_BLACK_ROOK_A8_NO_CHECK_POSITION);
		Collection<Move> rookLegalMoves = new ArrayList<>();
		rookLegalMoves.add(new StandardMove(new Position(0,0), new Position(0,1)));
		rookLegalMoves.add(new StandardMove(new Position(0,0), new Position(0,2)));
		rookLegalMoves.add(new StandardMove(new Position(0,0), new Position(0,3)));
		rookLegalMoves.add(new StandardMove(new Position(0,0), new Position(1,0)));
		rookLegalMoves.add(new StandardMove(new Position(0,0), new Position(2,0)));
		rookLegalMoves.add(new StandardMove(new Position(0,0), new Position(3,0)));
		rookLegalMoves.add(new StandardMove(new Position(0,0), new Position(4,0)));
		rookLegalMoves.add(new StandardMove(new Position(0,0), new Position(5,0)));
		rookLegalMoves.add(new StandardMove(new Position(0,0), new Position(6,0)));
		rookLegalMoves.add(new StandardMove(new Position(0,0), new Position(7,0)));
		Collection<Move> computedRookMoves = gameState.getLegalMovesForPieceAtPosition(new Position(0, 0));
		assertTrue(rookLegalMoves.size() == computedRookMoves.size() &&
				rookLegalMoves.containsAll(computedRookMoves) &&
				computedRookMoves.containsAll(rookLegalMoves), "The expected list of moves: " + rookLegalMoves +
				" is not the same as the produced one: " + computedRookMoves);
	}

	@Test
	void blackRookAtH8LegalMovesNoCheck() {
		ChessGame gameState = new ChessGame(FEN_BLACK_ROOK_H8_NO_CHECK_POSITION);
		Collection<Move> rookLegalMoves = new ArrayList<>();
		rookLegalMoves.add(new StandardMove(new Position(0,7), new Position(0,6)));
		rookLegalMoves.add(new StandardMove(new Position(0,7), new Position(0,5)));
		rookLegalMoves.add(new StandardMove(new Position(0,7), new Position(1,7)));
		rookLegalMoves.add(new StandardMove(new Position(0,7), new Position(2,7)));
		rookLegalMoves.add(new StandardMove(new Position(0,7), new Position(3,7)));
		rookLegalMoves.add(new StandardMove(new Position(0,7), new Position(4,7)));
		rookLegalMoves.add(new StandardMove(new Position(0,7), new Position(5,7)));
		rookLegalMoves.add(new StandardMove(new Position(0,7), new Position(6,7)));
		rookLegalMoves.add(new StandardMove(new Position(0,7), new Position(7,7)));
		Collection<Move> computedRookMoves = gameState.getLegalMovesForPieceAtPosition(new Position(0, 7));
		assertTrue(rookLegalMoves.size() == computedRookMoves.size() &&
				rookLegalMoves.containsAll(computedRookMoves) &&
				computedRookMoves.containsAll(rookLegalMoves), "The expected list of moves: " + rookLegalMoves +
				" is not the same as the produced one: " + computedRookMoves);
	}

	@Test
	void whiteRookAtA1LegalMovesNoCheck() {
		ChessGame gameState = new ChessGame(FEN_WHITE_ROOK_A1_NO_CHECK_POSITION);
		Collection<Move> rookLegalMoves = new ArrayList<>();
		rookLegalMoves.add(new StandardMove(new Position(7,0), new Position(7,1)));
		rookLegalMoves.add(new StandardMove(new Position(7,0), new Position(7,2)));
		rookLegalMoves.add(new StandardMove(new Position(7,0), new Position(7,3)));
		rookLegalMoves.add(new StandardMove(new Position(7,0), new Position(1,0)));
		rookLegalMoves.add(new StandardMove(new Position(7,0), new Position(2,0)));
		rookLegalMoves.add(new StandardMove(new Position(7,0), new Position(3,0)));
		rookLegalMoves.add(new StandardMove(new Position(7,0), new Position(4,0)));
		rookLegalMoves.add(new StandardMove(new Position(7,0), new Position(5,0)));
		rookLegalMoves.add(new StandardMove(new Position(7,0), new Position(6,0)));
		rookLegalMoves.add(new StandardMove(new Position(7,0), new Position(0,0)));
		Collection<Move> computedRookMoves = gameState.getLegalMovesForPieceAtPosition(new Position(7, 0));
		assertTrue(rookLegalMoves.size() == computedRookMoves.size() &&
				rookLegalMoves.containsAll(computedRookMoves) &&
				computedRookMoves.containsAll(rookLegalMoves), "The expected list of moves: " + rookLegalMoves +
				" is not the same as the produced one: " + computedRookMoves);
	}

	@Test
	void whiteRookAtH1LegalMovesNoCheck() {
		ChessGame gameState = new ChessGame(FEN_WHITE_ROOK_H1_NO_CHECK_POSITION);
		Collection<Move> rookLegalMoves = new ArrayList<>();
		rookLegalMoves.add(new StandardMove(new Position(7,7), new Position(7,6)));
		rookLegalMoves.add(new StandardMove(new Position(7,7), new Position(7,5)));
		rookLegalMoves.add(new StandardMove(new Position(7,7), new Position(1,7)));
		rookLegalMoves.add(new StandardMove(new Position(7,7), new Position(2,7)));
		rookLegalMoves.add(new StandardMove(new Position(7,7), new Position(3,7)));
		rookLegalMoves.add(new StandardMove(new Position(7,7), new Position(4,7)));
		rookLegalMoves.add(new StandardMove(new Position(7,7), new Position(5,7)));
		rookLegalMoves.add(new StandardMove(new Position(7,7), new Position(6,7)));
		rookLegalMoves.add(new StandardMove(new Position(7,7), new Position(0,7)));
		Collection<Move> computedRookMoves = gameState.getLegalMovesForPieceAtPosition(new Position(7, 7));
		assertTrue(rookLegalMoves.size() == computedRookMoves.size() &&
				rookLegalMoves.containsAll(computedRookMoves) &&
				computedRookMoves.containsAll(rookLegalMoves), "The expected list of moves: " + rookLegalMoves +
				" is not the same as the produced one: " + computedRookMoves);
	}

	@Test
	void rookA1PseudoLegalMoves() {
		ArrayList<Position> allPseudoLegalRookPositions = getPositions();
		ArrayList<ArrayList<Position>> computedPseudoLegalRookTrajectories =
				PseudoLegalPiecesPositionsInitializer.getRookPseudoLegalPositions(new Position(7,0));
		ArrayList<Position> allComputedPseudoLegalRookPositions = new ArrayList<>();
		for (ArrayList<Position> rookTrajectory : computedPseudoLegalRookTrajectories) {
            allComputedPseudoLegalRookPositions.addAll(rookTrajectory);
		}
		assertEquals(allPseudoLegalRookPositions.size(), allComputedPseudoLegalRookPositions.size(),
				"The expected sizes of allPseudoLegalRookPositions and allComputedPseudoLegalRookPositions should be equal.");
		assertTrue(allPseudoLegalRookPositions.containsAll(allComputedPseudoLegalRookPositions) &&
				allComputedPseudoLegalRookPositions.containsAll(allPseudoLegalRookPositions),
				"allPseudoLegalRookPositions and allComputedPseudoLegalRookPositions should contain the same elements.");
	}

	private ArrayList<Position> getPositions() {
		ChessGame gameState = new ChessGame(FEN_ROOK_A1_PSEUDO_LEGAL_MOVES);
		ArrayList<Position> rookPseudoLegalPositions1 = new ArrayList<>();
		rookPseudoLegalPositions1.add(new Position(7,1));
		rookPseudoLegalPositions1.add(new Position(7,2));
		rookPseudoLegalPositions1.add(new Position(7,3));
		rookPseudoLegalPositions1.add(new Position(7,4));
		rookPseudoLegalPositions1.add(new Position(7,5));
		rookPseudoLegalPositions1.add(new Position(7,6));
		rookPseudoLegalPositions1.add(new Position(7,7));
		ArrayList<Position> allPseudoLegalRookPositions = new ArrayList<>(rookPseudoLegalPositions1);
		ArrayList<Position> rookPseudoLegalPositions2 = new ArrayList<>();
		rookPseudoLegalPositions2.add(new Position(6,0));
		rookPseudoLegalPositions2.add(new Position(5,0));
		rookPseudoLegalPositions2.add(new Position(4,0));
		rookPseudoLegalPositions2.add(new Position(3,0));
		rookPseudoLegalPositions2.add(new Position(2,0));
		rookPseudoLegalPositions2.add(new Position(1,0));
		rookPseudoLegalPositions2.add(new Position(0,0));
		allPseudoLegalRookPositions.addAll(rookPseudoLegalPositions2);
		return allPseudoLegalRookPositions;
	}
}
