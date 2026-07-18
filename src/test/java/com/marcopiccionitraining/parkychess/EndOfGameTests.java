package com.marcopiccionitraining.parkychess;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import com.marcopiccionitraining.parkychess.model.moves.StandardMove;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
        assertEquals(PlayerColor.WHITE, (gameState.getGameResult().winner()), "Winner should be White.");
    }

    @Test
    void endOfGameWithCheckmateOnTheBoardForBlack(){
        ChessGame gameState = new ChessGame(FEN_MATE_KNIGHT_BISHOP_VS_KING);
        log.debug("Current board: {}", gameState.getChessboard());
        assertTrue(gameState.isGameOver(), "Game should be over in this position.");
        assertEquals(GameEndReason.CHECKMATE, gameState.getGameResult().gameEndReason(),
                "Game end reason should be checkmate");
        assertEquals(PlayerColor.BLACK, (gameState.getGameResult().winner()), "Winner should be Black.");
    }

    @Test
    void endOfGameWithStalemateOnTheBoardForBlack(){
        ChessGame gameState = new ChessGame(FEN_STALEMATE_WHITE_QUEEN_VS_KING);
        log.debug("Current board situation: {} Move to: {}", gameState.getChessboard(),
                gameState.getChessboard().getCurrentPlayerColor());
        assertTrue(gameState.isGameOver(), "Game should be over in this position.");
        assertEquals(GameEndReason.STALEMATE_DRAW, gameState.getGameResult().gameEndReason(),
                "Game end reason should be draw by stalemate");
        assertEquals(PlayerColor.UNDETERMINED, (gameState.getGameResult().winner()), "Winner should be undetermined.");
    }

    @Test
    void endOfGameWithStalemateOnTheBoardForWhite(){
        ChessGame gameState = new ChessGame(FEN_STALEMATE_BLACK_ROOK_KNIGHT_VS_KING);
        log.debug("Board: {} Move to: {}", gameState.getChessboard(), gameState.getChessboard().getCurrentPlayerColor());
        assertTrue(gameState.isGameOver(), "Game should be over in this position.");
        assertEquals(GameEndReason.STALEMATE_DRAW, gameState.getGameResult().gameEndReason(),
                "Game end reason should be draw by stalemate");
        assertEquals(PlayerColor.UNDETERMINED, (gameState.getGameResult().winner()), "Winner should be undetermined.");
    }

    @Test
    void endOfGameWithGameStateRepetitionWhiteStarts(){
        ChessGame gameState = new ChessGame(FEN_KING_ROOK_NO_CHECK_POSITION);
        for (int i = 0; i < 3; i++) {
            Move whiteRookFirstMove = new StandardMove(new Position(7,3), new Position(6,3));
            whiteRookFirstMove.execute(gameState.getChessboard());
            Move blackKingFirstMove = new StandardMove(new Position(0,4), new Position(0,5));
            blackKingFirstMove.execute(gameState.getChessboard());
            Move whiteRookSecondMove = new StandardMove(new Position(6,3), new Position(7,3));
            whiteRookSecondMove.execute(gameState.getChessboard());
            Move blackKingSecondMove = new StandardMove(new Position(0,5), new Position(0,4));
            blackKingSecondMove.execute(gameState.getChessboard());
        }
        //TODO: decide which counter to use and display for threefold repetition.
        assertTrue(gameState.isGameOver(), "Game should be over in this position.");
        assertEquals(GameEndReason.THREEFOLD_REPETITION_DRAW, gameState.getGameResult().gameEndReason(),
                "Game end reason should be draw by threefold repetition.");
        assertEquals(PlayerColor.UNDETERMINED, (gameState.getGameResult().winner()), "Winner should be undetermined.");
    }
}
