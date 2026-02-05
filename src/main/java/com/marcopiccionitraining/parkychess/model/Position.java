package com.marcopiccionitraining.parkychess.model;

import lombok.Getter;
import lombok.NonNull;
import java.util.Objects;

/**
 * Position on the board, expressed as a pair of integers (row and column).
 * Conventions:
 * 1. upper left corner is the first position (position 0,0).
 * 2. rows increase from top (0) to bottom (7).
 * 3. columns increase from left (0) to right (7).
 */
public final class Position {
    /**
     * This position's row coordinate within the board.
     */
    @Getter
    private final int row;
    /**
     * This position's column coordinate within the board.
     */
    @Getter
    private final int column;
    /**
     * Is this position inside the board?
     */
    @Getter
    private boolean isInsideBoard;
    /**
     * Creates a position.
     * @param row this position's row.
     * @param column this position's column.
     */
    public Position (int row, int column) {
        if (row >= 0 && row <= 7 && column >= 0 && column <= 7) {
            isInsideBoard = true;
            this.row = row;
            this.column = column;
        } else {
            this.row = -1;
            this.column = -1;
        }
    }

    public Position stepTowardsDirection (Direction direction) {
        int expectedRow = row + direction.rowDelta();
        int expectedColumn = column + direction.columnDelta();
        Position expectedPosition = new Position(expectedRow, expectedColumn);
        if (expectedPosition.isInsideBoard){
            return expectedPosition;
        }
        return new Position(-1,-1);
    }

    private @NonNull String toAlgebraic() {
        if (row == -1 && column == -1) {
            return "--";
        }
        StringBuilder algebraicForm = new StringBuilder();
        switch (column) {
            case 0:
                algebraicForm.append("a");
                break;
            case 1:
                algebraicForm.append("b");
                break;
            case 2:
                algebraicForm.append("c");
                break;
            case 3:
                algebraicForm.append("d");
                break;
            case 4:
                algebraicForm.append("e");
                break;
            case 5:
                algebraicForm.append("f");
                break;
            case 6:
                algebraicForm.append("g");
                break;
            case 7:
                algebraicForm.append("h");
                break;
            default:
                throw new RuntimeException("Invalid column number: " + column);
        }
        algebraicForm.append(8 - row);
        return algebraicForm.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position position)) return false;
        return row == position.row && column == position.column;
    }

    @Override
    public @NonNull String toString() {
        return toAlgebraic();
    }
}
