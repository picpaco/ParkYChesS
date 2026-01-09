package com.marcopiccionitraining.parkychess.model;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

/**
 * Incremental game state implemented as a Stack with a fixed array of known size, with O(1) operations.
 * Objects of this class are used for test and search functions to store game state incrementally before making moves
 * and to restore game state when undoing moves.
 */
@Slf4j
public class GameStateContainer {
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
    public void push (FENStateString moveStateItem) {
        if (topIndex + 1 == maxSize) {
            //moveStateStackImpl.add(moveStateItem);
            log.debug("Stack overflow! Can't push elements when the stack is full.");
            assert false : "Stack overflow! Can't push elements when the stack is full.";
            //topIndex++;
        } else {
            topIndex++;
        //    if (moveStateStackImpl.size() > topIndex) {
        //        moveStateStackImpl.set(topIndex, moveStateItem);
        //    } else{
            moveStateStackImpl.add(moveStateItem);
            log.debug("Pushed element '{}' on stack.", moveStateItem);
   //         }
        }
    }

    /**
     * @return top element, or null if stack underflowed.
     * Top element is removed from the stack.
     */
    public FENStateString pop() {
        log.debug("Popping element from stack.");
        if (topIndex < 0) {
            log.debug("Stack underflow! Can't remove elements from an empty stack.");
       //     assert false : "Stack underflow! Can't remove elements from an empty stack.";
            return null;
        }
        return moveStateStackImpl.remove(topIndex--);
    }

    /**
     * @return top element, or null if stack is empty.
     * Top element is not removed from the stack.
     */
    public FENStateString peek() {
        if (topIndex < 0) {
            log.debug("Stack is Empty");
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
     *
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

