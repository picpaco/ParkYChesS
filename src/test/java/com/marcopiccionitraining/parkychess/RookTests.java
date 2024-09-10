package com.marcopiccionitraining.parkychess;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import com.marcopiccionitraining.parkychess.model.moves.StandardMove;
import com.marcopiccionitraining.parkychess.model.pieces.Rook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;

import static com.marcopiccionitraining.parkychess.model.FENPositions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RookTests {
	private Board chessboard;

	@BeforeEach
	void setup(){
		chessboard = new Board();
	}
	@Test
	void whiteRookInitialPositionHasNotMovedYet(){
		ChessGame gameState = new ChessGame(chessboard, new FENString (FEN_INITIAL_POSITION));
		Rook whiteRook = (Rook) chessboard.getPiece (7,7);
		assertFalse(whiteRook.hasMoved(), "Rook should not have already moved at starting position.");
	}
	@Test
	void blackRookInitialPositionHasNotMovedYet(){
		ChessGame gameState = new ChessGame(chessboard, new FENString (FEN_INITIAL_POSITION));
		Rook blackRook = (Rook) chessboard.getPiece (0,7);
		assertFalse(blackRook.hasMoved(), "Rook should not have already moved at starting position.");
	}
	@Test
	void whiteRookNameCheck(){
		ChessGame gameState = new ChessGame(chessboard, new FENString (FEN_INITIAL_POSITION));
		Rook whiteRook = (Rook) chessboard.getPiece (7,7);
		assertEquals(PlayerColor.WHITE, whiteRook.getColor(), "Rook at square h1 should be white.");
		assertEquals(PieceName.ROOK, whiteRook.getName(), "Rook's name should be 'ROOK'");
		assertEquals("ROOK", whiteRook.toString(), "Rook's name as string should be 'ROOK'");
	}
	@Test
	void blackRookNameCheck(){
		ChessGame gameState = new ChessGame(chessboard, new FENString (FEN_INITIAL_POSITION));
		Rook blackRook = (Rook) chessboard.getPiece (0,7);
		assertEquals(PlayerColor.BLACK, blackRook.getColor(), "Rook at square h8 should be black.");
		assertEquals(PieceName.ROOK, blackRook.getName(), "Rook's name should be 'ROOK'");
		assertEquals("rook", blackRook.toString(), "Rook's name as string should be 'rook'");
	}
	@Test
	void blackRookAtA8CannotMoveAtStartingPosition() {
		ChessGame gameState = new ChessGame(chessboard, new FENString (FEN_INITIAL_POSITION));
		gameState.setCurrentColor(PlayerColor.BLACK);
		assertTrue(gameState.getLegalMovesForPieceAtPosition(new Position(0, 0)).isEmpty());
	}
	@Test
	void blackRookAtH8CannotMoveAtStartingPosition() {
		ChessGame gameState = new ChessGame(chessboard, new FENString (FEN_INITIAL_POSITION));
		gameState.setCurrentColor(PlayerColor.BLACK);
		assertTrue(gameState.getLegalMovesForPieceAtPosition(new Position(0, 7)).isEmpty());
	}
	@Test
	void whiteRookAtA1CannotMoveAtStartingPosition() {
		ChessGame gameState = new ChessGame(chessboard, new FENString (FEN_INITIAL_POSITION));
		assertTrue(gameState.getLegalMovesForPieceAtPosition(new Position(7, 0)).isEmpty());
	}
	@Test
	void whiteRookAtH1CannotMoveAtStartingPosition() {
		ChessGame gameState = new ChessGame(chessboard, new FENString (FEN_INITIAL_POSITION));
		assertTrue(gameState.getLegalMovesForPieceAtPosition(new Position(7, 7)).isEmpty());
	}
	@Test
	void blackRookAtA8LegalMovesNoCheck() {
		ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_BLACK_ROOK_A8_NO_CHECK_POSITION));
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
		ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_BLACK_ROOK_H8_NO_CHECK_POSITION));
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
		ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_WHITE_ROOK_A1_NO_CHECK_POSITION));
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
		ChessGame gameState = new ChessGame(chessboard, new FENString(FEN_WHITE_ROOK_H1_NO_CHECK_POSITION));
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
}
