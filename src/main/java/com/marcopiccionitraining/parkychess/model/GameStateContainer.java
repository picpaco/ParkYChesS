package com.marcopiccionitraining.parkychess.model;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

/**
 * Incremental game state implemented as a Stack with a fixed array of known size, with O(1) operations.
 * Objects of this class are used by test and search functions to store game state incrementally before
 * making a move and to restore game state before undoing the same move.
 */
@Slf4j
public class GameStateContainer implements EssentialStack<FENStateString>{
    private int topIndex;
    private int maxSize;
    private ArrayList<FENStateString> moveStateStackImpl;

    public GameStateContainer(int maxSize) {
        this.maxSize = maxSize;
        topIndex = -1;
        moveStateStackImpl = new ArrayList<>(maxSize);
    }

    /**
     * @param moveStateItem element to push on top.
     */
    @Override
    public void push (FENStateString moveStateItem) {
        if (topIndex + 1 == maxSize) {
            log.trace("Stack overflow! Can't push elements when the stack is full.");
            assert false : "Stack overflow! Can't push elements when the stack is full.";
        } else {
            topIndex++;
            moveStateStackImpl.add(moveStateItem);
            log.trace("After pushing element '{}' into the stack, top index = {}", moveStateItem, topIndex);
        }
    }

    /**
     * @return top element, or null if stack underflowed.
     * Top element is removed from the stack.
     */
    @Override
    public FENStateString pop() {
        if (topIndex < 0) {
            log.trace("Stack underflow! Can't remove elements from an empty stack.");
            assert false : "Stack underflow! Can't remove elements from an empty stack.";
            return null;
        }
        FENStateString result = moveStateStackImpl.remove(topIndex);
        topIndex--;
        log.trace("After popping element {} from stack, top index = {}", result, topIndex);
        return result;
    }

    /**
     * @return top element, or null if stack is empty.
     * Top element is not removed from the stack.
     */
    @Override
    public FENStateString peek() {
        if (topIndex < 0) {
            log.trace("Stack is Empty");
            return null;
        }
        return moveStateStackImpl.get(topIndex);
    }

    public int size(){
        return topIndex + 1;
    }
    
    public boolean isEmpty() {
        return topIndex < 0;
    }

    /**
     * @return a deep copy of this GameStateContainer
     */
    public GameStateContainer copy() {
        GameStateContainer gameStateContainerCopy = new GameStateContainer(maxSize);
        gameStateContainerCopy.topIndex = topIndex;
        gameStateContainerCopy.maxSize = maxSize;
        gameStateContainerCopy.moveStateStackImpl = new ArrayList<>(maxSize);
        gameStateContainerCopy.moveStateStackImpl.addAll(moveStateStackImpl);
        return gameStateContainerCopy;
    }
}