package com.marcopiccionitraining.parkychess;

import com.marcopiccionitraining.parkychess.model.*;
import com.marcopiccionitraining.parkychess.model.moves.Move;
import com.marcopiccionitraining.parkychess.model.moves.StandardMove;
import com.marcopiccionitraining.parkychess.model.pieces.Bishop;
import com.marcopiccionitraining.parkychess.model.pieces.PieceNames;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;

import static com.marcopiccionitraining.parkychess.model.FENPositions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j @SpringBootTest
public class GameStateContainerTests {
    private ChessGame gameState;
    private GameStateContainer gameStateContainer;
    private final int MAX_SIZE = 2;

    @BeforeEach
    void setUp() {
        gameState = new ChessGame(FEN_INITIAL_POSITION);
        gameStateContainer = new GameStateContainer(MAX_SIZE);
    }

    @Test
    void emptyStack(){
        assertTrue(gameStateContainer.isEmpty(), "Stack should be empty now.");
    }

    @Test
    void pushOneObject(){
        FENStateString fss = new FENStateString(gameState);
        gameStateContainer.push(fss);
        assertEquals(1, gameStateContainer.size(), "Stack should contain one element now.");
    }

    @Test
    void pushTwoEqualObjects(){
        FENStateString fss = new FENStateString(gameState);
        gameStateContainer.push(fss);
        gameStateContainer.push(fss);
        assertEquals(2, gameStateContainer.size(), "Stack should contain two elements now.");
    }

    @Test
    void pushTwoDifferentObjects(){
        FENStateString fss1 = new FENStateString(gameState);
        FENStateString fss2 = new FENStateString(gameState);
        gameStateContainer.push(fss1);
        gameStateContainer.push(fss2);
        assertEquals(2, gameStateContainer.size(), "Stack should contain two elements now.");
    }

    @Test
    void threeElementStackOverflows(){
        assertThrowsExactly(AssertionError.class,
                () -> {
                    FENStateString fss1 = new FENStateString(gameState);
                    FENStateString fss2 = new FENStateString(gameState);
                    FENStateString fss3 = new FENStateString(gameState);
                    gameStateContainer.push(fss1);
                    gameStateContainer.push(fss2);
                    gameStateContainer.push(fss3);
                },
                "Stack overflow! Can't push elements into an empty stack.");
    }

    @Test
    void poppingAnEmptyStackUnderflows(){
    }

    @Test
    void pushThenPopOneElement(){
        FENStateString fss = new FENStateString(gameState);
        gameStateContainer.push(fss);
        gameStateContainer.pop();
        assertTrue(gameStateContainer.isEmpty(), "Stack should be empty now.");
    }

    @Test
    void popReturnsCorrectElement(){
        FENStateString fss = new FENStateString(gameState);
        gameStateContainer.push(fss);
        FENStateString elem = gameStateContainer.pop();
        assertEquals(elem, fss, "pop should return the same element which was pushed in.");
    }

    @Test
    void pushTwoElementsThenPopOneElement(){
        FENStateString fss1 = new FENStateString(gameState);
        FENStateString fss2 = new FENStateString(gameState);
        gameStateContainer.push(fss1);
        gameStateContainer.push(fss2);
        gameStateContainer.pop();
        assertEquals(1, gameStateContainer.size(), "Stack should contain one element now.");
    }

    @Test
    void pushThenPeekOneElement(){
        FENStateString fss = new FENStateString(gameState);
        gameStateContainer.push(fss);
        FENStateString peekedElement = gameStateContainer.peek();
        assertFalse(gameStateContainer.isEmpty(), "Stack should not be empty now.");
        assertEquals(peekedElement, fss, "peeked element should be the same element in the stack.");
    }

    @Test
    void peekAnEmptyStack(){
        FENStateString peekedElement = gameStateContainer.peek();
        assertNull(peekedElement, "peeked element should be null.");
        assertTrue(gameStateContainer.isEmpty(), "Stack should be empty now.");
    }
}