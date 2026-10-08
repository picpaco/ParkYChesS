package com.marcopiccionitraining.parkychess;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import com.marcopiccionitraining.parkychess.model.moves.StandardMove;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.marcopiccionitraining.parkychess.model.FENPositions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
public class EndOfGameTests {
    @Test
    void endOfGameWithCheckmateOnTheBoardForWhite(){
        ChessGame gameState = new ChessGame(FEN_MATE_ROOK_VS_KING_IN_CORRIDOR_OF_PAWNS);
        log.debug("Current board situation: {}", gameState.getChessboard());
        assertTrue(gameState.isGameOver(), "Game should be over in this position.");
        assertEquals(GameEndReason.CHECKMATE, gameState.getGameResult().gameEndReason(),
                "Game end reason should be checkmate");
        assertEquals(PlayerColor.WHITE, (gameState.getGameResult().winner()),
                "Winner should be White.");
    }

    @Test
    void endOfGameWithCheckmateOnTheBoardForBlack(){
        ChessGame gameState = new ChessGame(FEN_MATE_KNIGHT_BISHOP_VS_KING);
        log.debug("Current board: {}", gameState.getChessboard());
        assertTrue(gameState.isGameOver(), "Game should be over in this position.");
        assertEquals(GameEndReason.CHECKMATE, gameState.getGameResult().gameEndReason(),
                "Game end reason should be checkmate");
        assertEquals(PlayerColor.BLACK, (gameState.getGameResult().winner()),
                "Winner should be Black.");
    }

    @Test
    void endOfGameWithStalemateOnTheBoardForBlack(){
        ChessGame gameState = new ChessGame(FEN_STALEMATE_WHITE_QUEEN_VS_KING);
        log.debug("Current board situation: {} Move to: {}", gameState.getChessboard(),
            gameState.getChessboard().getCurrentPlayerColor());
        assertTrue(gameState.isGameOver(), "Game should be over in this position.");
        assertEquals(GameEndReason.STALEMATE_DRAW, gameState.getGameResult().gameEndReason(),
            "Game end reason should be draw by stalemate");
        assertEquals(PlayerColor.UNDETERMINED, (gameState.getGameResult().winner()),
                "Winner should be undetermined.");
    }

    @Test
    void endOfGameWithStalemateOnTheBoardForWhite() {
        ChessGame gameState = new ChessGame(FEN_STALEMATE_BLACK_ROOK_KNIGHT_VS_KING);
        log.debug("Board: {} Move to: {}", gameState.getChessboard(), gameState.getChessboard().getCurrentPlayerColor());
        assertTrue(gameState.isGameOver(), "Game should be over in this position.");
        assertEquals(GameEndReason.STALEMATE_DRAW, gameState.getGameResult().gameEndReason(),
                "Game end reason should be draw by stalemate");
        assertEquals(PlayerColor.UNDETERMINED, (gameState.getGameResult().winner()),
                "Winner should be undetermined.");;
    }

    @Test
    void endOfGameWithInsufficientMaterialKingVsKing() {
        ChessGame gameState = new ChessGame(FEN_KINGS_ONLY);
        assertTrue(gameState.isGameOver(), "Game should be over in this position.");
        String expectedMessage = "Draw (not enough pieces for a decisive result).";
        String actualMessage = gameState.getGameResult().gameEndReason().toString();
        assertEquals(expectedMessage, actualMessage,
                "Game end reason should be insufficient material.");
    }

    @Test
    void endGameWithInsufficientMaterialKingVsKingAndBishop() {
        ChessGame gameState = new ChessGame(FEN_WHITE_BISHOP_C1_NO_CHECK_POSITION);
        assertTrue(gameState.isGameOver(), "Game should be over in this position.");
        String expectedMessage = "Draw (not enough pieces for a decisive result).";
        String actualMessage = gameState.getGameResult().gameEndReason().toString();
        assertEquals(expectedMessage, actualMessage,
                "Game end reason should be insufficient material.");
    }

    @Test
    void endGameWithInsufficientMaterialKingVsKingAndKnight() {
        ChessGame gameState = new ChessGame(FEN_WHITE_KNIGHT_CHECK_POSITION);
        String expectedMessage = "Draw (not enough pieces for a decisive result).";
        String actualMessage = gameState.getGameResult().gameEndReason().toString();
        assertEquals(expectedMessage, actualMessage,
                "Game end reason should be insufficient material.");;
    }

    @Test
    void endGameWithInsufficientMaterialKingVsKingAnd2Knights() {
        ChessGame gameState = new ChessGame(FEN_2_BLACK_KNIGHTS_CHECK_POSITION);
        String expectedMessage = "Draw (not enough pieces for a decisive result).";
        String actualMessage = gameState.getGameResult().gameEndReason().toString();
        assertEquals(expectedMessage, actualMessage,
                "Game end reason should be insufficient material.");
        assertEquals(1, gameState.getChessboard().getCheckerPieces().size());
    }

    @Test
    void endGameWithInsufficientMaterialKingVsKingAnd2Bishops() {
        ChessGame gameState = new ChessGame(FEN_WHITE_BISHOP_F1_NO_CHECK_POSITION);
        List<Move> bishopLegalMoves = new ArrayList<>();
        bishopLegalMoves.add(new StandardMove(new Position(7, 5), new Position(6, 6)));
        bishopLegalMoves.add(new StandardMove(new Position(7, 5), new Position(5, 7)));
        bishopLegalMoves.add(new StandardMove(new Position(7, 5), new Position(6, 4)));
        bishopLegalMoves.add(new StandardMove(new Position(7, 5), new Position(5, 3)));
        bishopLegalMoves.add(new StandardMove(new Position(7, 5), new Position(4, 2)));
        bishopLegalMoves.add(new StandardMove(new Position(7, 5), new Position(3, 1)));
        bishopLegalMoves.add(new StandardMove(new Position(7, 5), new Position(2, 0)));
        List<Move> computedBishopMoves = gameState.getLegalMovesForPieceAtPosition(new Position(7, 5));
        assertTrue(bishopLegalMoves.size() == computedBishopMoves.size() &&
                        bishopLegalMoves.containsAll(computedBishopMoves) &&
                        computedBishopMoves.containsAll(bishopLegalMoves),
                "The expected list of moves is not equal to the produced one.");
    }

    @Test
    void endOfGameWithGameStateRepetitionWhiteStarts(){
        ChessGame gameState = new ChessGame(FEN_KING_ROOK_NO_CHECK_POSITION);
        for (int i = 0; i < 3; i++) {
            Move whiteRookFirstMove = new StandardMove(new Position(7,3), new Position(6,3));
            gameState.makeMove(whiteRookFirstMove);
            Move blackKingFirstMove = new StandardMove(new Position(0,4), new Position(0,5));
            gameState.makeMove(blackKingFirstMove);
            Move whiteRookSecondMove = new StandardMove(new Position(6,3), new Position(7,3));
            gameState.makeMove(whiteRookSecondMove);
            Move blackKingSecondMove = new StandardMove(new Position(0,5), new Position(0,4));
            gameState.makeMove(blackKingSecondMove);
        }
        assertTrue(gameState.isGameOver(), "Game should be over in this position.");
        assertEquals(GameEndReason.THREEFOLD_REPETITION_DRAW, gameState.getGameResult().gameEndReason(),
                "Game end reason should be draw by threefold repetition.");
        assertEquals(PlayerColor.UNDETERMINED, (gameState.getGameResult().winner()), "Winner should be undetermined.");
    }
}
