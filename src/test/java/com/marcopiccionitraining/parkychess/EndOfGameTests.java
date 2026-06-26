package com.marcopiccionitraining.parkychess;

import com.marcopiccionitraining.parkychess.model.ChessGame;
import com.marcopiccionitraining.parkychess.model.GameEndReason;
import com.marcopiccionitraining.parkychess.model.GameResult;
import com.marcopiccionitraining.parkychess.model.PlayerColor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.marcopiccionitraining.parkychess.model.FENPositions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
}
