package com.marcopiccionitraining.parkychess.model;

import lombok.NonNull;

import java.util.Objects;
import java.util.Optional;

/**
 * Position on the board, expressed as a pair of integers (row and column).
 * Conventions:
 * 1. upper left corner is the first position (position 0,0).
 * 2. rows increase from top (0) to bottom (7).
 * 3. columns increase from left (0) to right (7).
 */
//TODO check everywhere else if you can avoid creating Position objects
// TODO think about deleting this class altogether and refactor its code in the Board class.
public record Position(int row, int column) {

    public Position {
        assert row >= -1 && row <= 7 : "invalid row: " + row;
        assert column >= -1 && column <= 7 : "invalid column: " + column;
    }

    public PlayerColor getSquareColor() {
        assert row != -1 && column != -1 : "row and column must not be -1 to invoke this function.";
        if ((row + column) % 2 == 0) {
            return PlayerColor.WHITE;
        } else {
            return PlayerColor.BLACK;
        }
    }

    public Position stepTowardsDirection (Direction direction) {
        assert row != -1 && column != -1 : "row and column must not be -1 to invoke this function.";        int expectedRow = row + direction.rowDelta();
        int expectedColumn = column + direction.columnDelta();
        if (expectedRow >= 0 && expectedRow <= 7 && expectedColumn >= 0 && expectedColumn <= 7) {
            return new Position(expectedRow, expectedColumn);
        } else {
            return null;
        }
    }

    public @NonNull String toAlgebraic() {
        assert row != -1 && column != -1 : "row and column must not be -1 to invoke this function.";
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
        if (row == -1 && column == -1) {
            return "undetermined";
        }
        return toAlgebraic();
    }
}
